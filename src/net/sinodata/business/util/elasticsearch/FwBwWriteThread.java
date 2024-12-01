package net.sinodata.business.util.elasticsearch;

import com.alibaba.fastjson.JSON;
import net.sinodata.business.dao.FwzyqqbwcjbDao;
import net.sinodata.business.dao.elasticsearch.EsDao;
import net.sinodata.business.entity.ConfigInfo;
import net.sinodata.business.entity.Fwzyqqbwcjb;
import net.sinodata.business.util.DateUtil;
import net.sinodata.business.util.RedisUtil;
import net.sinodata.business.util.StringUtil;
import net.sinodata.business.util.UUIDGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class FwBwWriteThread {
    /**
     * 延时时间
     */
    private static Integer DELAY_MINUTE = 30;
    /**
     * 定时器时间范围
     */
    private static Integer QUARTZ_TIME_RANGE = 1;
    private static String DATA_TIME_FORMAT = "yyyyMMddHHmm";
    private static Integer BULK_NUM = 500;
    private static Date startDate;
    private static ExecutorService exec;
    static{
         exec = new ThreadPoolExecutor(30,
                40,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }


    @Autowired
    private FwzyqqbwcjbDao fwzyqqbwcjbDao;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private EsDao esDao;
    @Autowired(required = false)
    private ConfigInfo configInfo;

    public void autoWriteFwBw() {
        //System.out.println("write to es log start");

        //查询任务上次执行时间
        String lastDataTime = fwzyqqbwcjbDao.selectMaxDataTime();

        //构建查询条件
        Map<String, Object> condition = buildCondition(lastDataTime);
        //查询数据
        List<Fwzyqqbwcjb> dataList = fwzyqqbwcjbDao.queryQuartzList(condition);
        //总数量
        Integer totalNum = dataList.size();

        //获取处理过的数据列表
        List<Map<String, Object>> dealDataMapList = getDealDataMapList(dataList, totalNum);

        //es表名
        String month = DateUtil.formatDate(startDate, "MM");
        String esTable = configInfo.getEsFwbw() + "_" + month;
        //批量存储到es
        Integer successNum = bulkInsertToEs(dealDataMapList, esTable);

        //添加日志
        insertLog(esTable, startDate, totalNum, successNum);

        //System.out.println("write to es log end,dataTime=" + new SimpleDateFormat("yyyyMMddHHmms").format(new Date()) + ",esTable=" + esTable + ",totalNum=" + totalNum + ",successNum=" + successNum);
    }

    /**
     * 计算查询条件
     *
     * @param lastDataTime
     * @return
     */
    private Map<String, Object> buildCondition(String lastDataTime) {
        Map<String, Object> condition = new HashMap<>();
        Date today = null;
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        if (lastDataTime == null || "".equals(lastDataTime)) {
            today = new Date();
            startCal.setTime(today);
            startCal.add(Calendar.MINUTE, -(DELAY_MINUTE + QUARTZ_TIME_RANGE));
            endCal.setTime(today);
            endCal.add(Calendar.MINUTE, -DELAY_MINUTE);
        } else {
            try {
                today = new SimpleDateFormat(DATA_TIME_FORMAT).parse(lastDataTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            startCal.setTime(today);
            startCal.add(Calendar.MINUTE, QUARTZ_TIME_RANGE);
            endCal.setTime(today);
            endCal.add(Calendar.MINUTE, QUARTZ_TIME_RANGE * 2);
        }
        startDate = startCal.getTime();
        condition.put("startTime", DateUtil.formatDate(startDate, "yyyy-MM-dd HH:mm:00"));
        condition.put("endTime", DateUtil.formatDate(endCal.getTime(), "yyyy-MM-dd HH:mm:00"));
        condition.put("days", "DAY" + condition.get("startTime").toString().substring(8, 10));
        return condition;
    }

    private List<Map<String, Object>> getDealDataMapList(List<Fwzyqqbwcjb> toDealDataList, Integer totalNum) {
//        ExecutorService exec = new ThreadPoolExecutor(30,
//                40,
//                0L,
//                TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<>(),
//                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.AbortPolicy());

        List<Map<String, Object>> allDealDataMapList = new ArrayList<>();
        while (true) {
            //添加线程任务
            List<Future<Map<String, Object>>> threadList =  getThreadPool(toDealDataList, totalNum, exec);

            //获取线程任务执行结果
            List<Map<String, Object>> dataMapList = getThreadResult(threadList);

            //获取现在未计算完成数据
            toDealDataList = toDealDataList.stream().filter(m -> {
                String id = m.getId();
                return dataMapList.stream().filter(mp -> mp != null && mp.size() > 0).noneMatch(mp -> mp.get("id").toString().equals(id));
            }).collect(Collectors.toList());

            //获取处理完成数据
            for (Map<String, Object> map : dataMapList) {
                if(map!=null&&map.size()>0){
                    allDealDataMapList.add(map);
                }
            }
            //未计算数据为0跳出循环
            if (toDealDataList.size() == 0) {
                break;
            }
        }
        //线程不再进任务
        //exec.shutdown();
        return allDealDataMapList;
    }

    private List<Future<Map<String, Object>>> getThreadPool(List<Fwzyqqbwcjb> bwcjbList, Integer totalNum, ExecutorService exec) {
        List<Future<Map<String, Object>>> futureList = new ArrayList<>();
        for (int i = 0; i < bwcjbList.size(); i++) {
            Task task = new Task(bwcjbList.get(i), totalNum, i + 1);
            Future<Map<String, Object>> future = exec.submit(task);
            futureList.add(future);
        }
        return futureList;
    }

    private List<Map<String, Object>> getThreadResult(List<Future<Map<String, Object>>> futureList) {
        List<Map<String, Object>> mapList;
        //遍历线程直到线程全部执行完成
        while (true) {
            mapList = futureList.stream().map(m -> {
                try {
                    return m.get(2, TimeUnit.SECONDS);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();

                    return null;
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    return new HashMap<String, Object>();
                }
            }).collect(Collectors.toList());
            int a1 = 0, a2 = 0, a3 = 0;
            for (Map<String, Object> map : mapList) {
                if (map == null) {
                    a2++;
                } else if (map.size() == 0) {
                    a3++;
                } else {
                    a1++;
                }
            }
            if (a1 + a2 + a3 == futureList.size()) {
                break;
            }
        }
        return mapList;
    }


    /**
     * 批量存储到es
     *
     * @param dealDataMapList
     * @param esTable
     */
    private Integer bulkInsertToEs(List<Map<String, Object>> dealDataMapList, String esTable) {
        Integer successNum = 0;
        if (dealDataMapList.size() > 0) {
            Integer num = dealDataMapList.size() / BULK_NUM + 1;
            for (int i = 0; i < num; i++) {
                List<Map<String, Object>> list;
                if (i == num - 1) {
                    list = dealDataMapList.subList(i * BULK_NUM, dealDataMapList.size());

                } else {
                    list = dealDataMapList.subList(i * BULK_NUM, (i + 1) * BULK_NUM);
                }
                try {
                    esDao.bulkIndex(esTable, list);
                    successNum += list.size();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return successNum;
    }

    /**
     * 存储日志
     *
     * @param esTable
     * @param startDate
     * @param totalNum
     * @param successNum
     */
    private void insertLog(String esTable, Date startDate, Integer totalNum, Integer successNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", UUIDGeneratorUtil.getUUID());
        String dataDate = DateUtil.formatDate(startDate, "yyyyMMdd");
        map.put("dataDate", dataDate);
        String dataTime = DateUtil.formatDate(startDate, DATA_TIME_FORMAT);
        map.put("dataTime", dataTime);
        map.put("esTable", esTable);
        map.put("totalNum", totalNum);
        map.put("successNum", successNum);
        map.put("loadDt", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        fwzyqqbwcjbDao.insertEsLog(map);
    }

    class Task implements Callable<Map<String, Object>>, Serializable {
        private static final long serialVersionUID = 1L;
        private Fwzyqqbwcjb fwzyqqbwcjb;
        private Integer sort;
        private Integer total;

        public Task(Fwzyqqbwcjb fwzyqqbwcjb, Integer total, Integer sort) {
            this.fwzyqqbwcjb = fwzyqqbwcjb;
            this.total = total;
            this.sort = sort;
        }

        @Override
        public Map<String, Object> call()  {
            Map<String, Object> map = new HashMap<>();
            String qqkey;
            String xykey;
            String qqnr;
            String xynr;
            qqkey = fwzyqqbwcjb.getQqbwbs() + fwzyqqbwcjb.getFwqqSjsjlx();
            xykey = fwzyqqbwcjb.getQqbwbs() + "service_response";

            qqnr = redisUtil.get(qqkey);
            fwzyqqbwcjb.setFwqqNr(qqnr);
            xynr = redisUtil.get(xykey);
            fwzyqqbwcjb.setFwtgNr(xynr);

            String jsonStr = JSON.toJSONString(fwzyqqbwcjb);

            map = JSON.parseObject(jsonStr, Map.class);
            String id = map.get("id") == null ? "" : map.get("id").toString();
            if (!StringUtil.isNotEmpty(id)) {
                id = UUIDGeneratorUtil.getUUID();
            }
            map.put("id", id);
            String startTime = map.get("startTime") == null ? "" : map.get("startTime").toString();
            String endTime = map.get("endTime") == null ? "" : map.get("endTime").toString();
            map.put("startTimeStr", startTime
                    .replaceAll("-", "")
                    .replaceAll(" ", "")
                    .replaceAll(":", "")
                    .replaceAll(",", ""));
            map.put("endTimeStr", endTime
                    .replaceAll("-", "")
                    .replaceAll(" ", "")
                    .replaceAll(":", "")
                    .replaceAll(",", ""));
            map.put("loadDt", new Date());

//            System.out.println("thread:" + Thread.currentThread().getName() + ",num:" + sort + ",totalNum:" + total + ",date:" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

            return map;
        }
    }
}
