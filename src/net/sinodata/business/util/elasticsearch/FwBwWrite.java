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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FwBwWrite {
    private static Integer DELAY_MINUTE = 2;
    private static String DATA_TIME_FORMAT = "yyyyMMddHHmm";
    private static Integer BULK_NUM = 300;
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

        Map<String, Object> condition = new HashMap<>();

        String lastDataTime = fwzyqqbwcjbDao.selectMaxDataTime();
        Date today = null;
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        if (lastDataTime == null || "".equals(lastDataTime)) {
            today = new Date();
            startCal.setTime(today);
            startCal.add(Calendar.MINUTE, -(DELAY_MINUTE + 1));
            endCal.setTime(today);
            endCal.add(Calendar.MINUTE, -DELAY_MINUTE);
        } else {
            try {
                today = new SimpleDateFormat(DATA_TIME_FORMAT).parse(lastDataTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            startCal.setTime(today);
            startCal.add(Calendar.MINUTE, 1);
            endCal.setTime(today);
            endCal.add(Calendar.MINUTE, 2);
        }
        Date startDate = startCal.getTime();
        condition.put("startTime", DateUtil.formatDate(startDate, "yyyy-MM-dd HH:mm:00"));
        condition.put("endTime", DateUtil.formatDate(endCal.getTime(), "yyyy-MM-dd HH:mm:00"));
        String days = "DAY" + condition.get("startTime").toString().substring(8, 10);
        condition.put("days", days);

        String dataDate = DateUtil.formatDate(startDate, "yyyyMMdd");
        String dataTime = DateUtil.formatDate(startDate, DATA_TIME_FORMAT);
        String month = DateUtil.formatDate(startDate, "MM");
        String esTable = configInfo.getEsFwbw() + "_" + month;

        List<Fwzyqqbwcjb> bwcjbList = fwzyqqbwcjbDao.queryQuartzList(condition);
        Integer totalNum = bwcjbList.size();
        Integer successNum = 0;
        //上次请求key
        String lastQqkey = "";
        //上次响应key
        String lastXyKey = "";
        //上次请求内容
        String lastQqnr = "";
        //上次响应内容
        String lastXynr = "";
        //请求key
        String qqkey = "";
        //响应key
        String xykey = "";
        //请求内容
        String qqnr = "";
        //响应内容
        String xynr = "";
        Fwzyqqbwcjb fwzyqqbwcjb;
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; i < bwcjbList.size(); i++) {
            fwzyqqbwcjb = bwcjbList.get(i);

            qqkey = fwzyqqbwcjb.getQqbwbs() + fwzyqqbwcjb.getFwqqSjsjlx();
            xykey = fwzyqqbwcjb.getQqbwbs() + "service_response";
            try {

                //判断请求key是否和上次的相同(存在相同情况)
                if (!qqkey.equals(lastQqkey)) {
                    qqnr = redisUtil.get(qqkey);
                    lastQqkey = qqkey;
                    lastQqnr = qqnr;
                } else {
                    qqnr = lastQqnr;
                }

                //判断响应key是否和上次的相同(存在相同情况)
                if (!xykey.equals(lastXyKey)) {
                    xynr = redisUtil.get(xykey);
                    lastXyKey = xykey;
                    lastXynr = xynr;
                } else {
                    xynr = lastXynr;
                }

                fwzyqqbwcjb.setFwqqNr(qqnr);
                fwzyqqbwcjb.setFwtgNr(xynr);
                String jsonStr = JSON.toJSONString(fwzyqqbwcjb);
                Map<String, Object> map = JSON.parseObject(jsonStr, Map.class);
                String id = map.get("id") == null ? "" : map.get("id").toString();
                if (!StringUtil.isNotEmpty(id)) {
                    id = UUIDGeneratorUtil.getUUID();
                }
                String startTime=map.get("startTime")==null?"":map.get("startTime").toString();
                String endTime=map.get("endTime")==null?"":map.get("endTime").toString();
                map.put("startTimeStr",startTime
                        .replaceAll("-", "")
                        .replaceAll(" ", "")
                        .replaceAll(":", "")
                        .replaceAll(",", ""));
                map.put("endTimeStr",endTime
                        .replaceAll("-", "")
                        .replaceAll(" ", "")
                        .replaceAll(":", "")
                        .replaceAll(",", ""));
                map.put("id", id);
                map.put("loadDt", new Date());
                if (mapList.size() < BULK_NUM) {
                    mapList.add(map);
                }
                if (mapList.size() == BULK_NUM || i == bwcjbList.size() - 1) {
                    esDao.bulkIndex(esTable, mapList);
                    successNum += mapList.size();
                    mapList.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ":" + (i) + "-" + totalNum);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", UUIDGeneratorUtil.getUUID());
        map.put("dataDate", dataDate);
        map.put("dataTime", dataTime);
        map.put("esTable", esTable);
        map.put("totalNum", totalNum);
        map.put("successNum", successNum);
        map.put("loadDt", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        fwzyqqbwcjbDao.insertEsLog(map);

        //System.out.println("write to es log end,dataTime=" + dataTime + ",esTable=" + esTable + ",totalNum=" + totalNum + ",successNum=" + successNum);
    }
}
