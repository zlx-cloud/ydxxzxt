package net.sinodata.business.rest;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sinodata.business.dao.FwzyffzcbDao;
import net.sinodata.business.entity.Fwcyfzcb;
import net.sinodata.business.entity.Fwqqzdb;
import net.sinodata.business.entity.Fwzyffzcb;
import net.sinodata.business.entity.FwzyffzcbKey;
import net.sinodata.business.entity.Fwzyqqbwcjb;
import net.sinodata.business.entity.Fwzyqqbwyccjb;
import net.sinodata.business.entity.Fwzysqb;
import net.sinodata.business.entity.Fwzyxybwcjb;
import net.sinodata.business.entity.Fwzyzcb;
import net.sinodata.business.service.FwqqzdbService;
import net.sinodata.business.service.FwzyffzcbService;
import net.sinodata.business.service.FwzysqbService;
import net.sinodata.business.service.ServeManageService;
import net.sinodata.business.service.UserTService;
import net.sinodata.business.util.DateUtil;
import net.sinodata.business.util.FastDFSClient;
import net.sinodata.business.util.HttpRequest;
import net.sinodata.business.util.JsonUtil;
import net.sinodata.business.util.MD5Utils;
import net.sinodata.business.util.MemcachedUtils;
import net.sinodata.business.util.RedisUtil;
import net.sinodata.business.util.StringUtil;
import net.sinodata.business.util.UUIDGeneratorUtil;
import net.sinodata.business.util.eventLogWrite.ExceptionLogWrite;
import net.sinodata.business.util.eventLogWrite.RequestLogWrite;
import net.sinodata.business.util.eventLogWrite.ResponseLogWrite;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
@Controller
@RequestMapping("/service")
public class GreetingController {
	Logger logger = LoggerFactory.getLogger(GreetingController.class);
//	@Autowired
//	private FwzyqqbwcjbService fwzyqqbwcjbService;
//	@Autowired
//	private FwzyxybwcjbService fwzyxybwcjbService;
	@Autowired
	UserTService UserTService;
	@Autowired
	ServeManageService serveManageService;
	@Autowired
	FwzysqbService fwzysqbService;
	@Autowired
	FwzyffzcbDao FwzyffzcbDao;
	@Autowired
	FwzyffzcbService fwzyffzcbService;
	@Autowired
	FwqqzdbService fwqqzdbService;
	
	@Autowired
	RedisUtil redisUtil;
	
	@RequestMapping(value = "verify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object verify(@RequestBody String param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*ServletInputStream ris = request.getInputStream();  
        StringBuilder content = new StringBuilder();  
        byte[] b = new byte[1024];  
        int lens = -1;  
        while ((lens = ris.read(b)) > 0) {  
            content.append(new String(b, 0, lens));  
        }  
        String strcont = content.toString();// 内容  
*/		String str="";
		if (JsonUtil.validate(param)) {
			JSONObject jsonObject = JSONObject.fromObject(param);
					Iterator it = jsonObject.keys();
					List keyList = new ArrayList();
					while (it.hasNext()) {
						String key = (String) it.next();
						keyList.add(key);
						String value = jsonObject.getString(key);
						if(!StringUtil.isNotEmpty(value)){
							   str="0,"+key+"数据为空！";
							   return str;
						}
					}
					
					/**
					 * 根据请求来源号区分使用哪个验证
					 * FWQQ_BWBH:报文编号
					 * FWBS:服务标识
					 * FWQQZ_ZCXX:服务参与方标识(注册信息)
					 * BWLYIPDZ:报文来源IP
					 * BWLYDKH:报文来源端口号
					 * FFBS:方法标识
					 * FWQQ_RQSJ:请求时间
					 * FWQQ_NR:服务请求内容
					 * XXCZRY_XM:操作人员姓名
					 * XXCZRY_GMSFHM:操作人员公民身份号码
					 * XXCZRY_GAJGJGDM:操作人员公安机关机构代码
					 * FWQQSB_BH:服务请求设备编号
					 * FWQQ_SJSJLX:审计事件类型
					 * {jsonObject.getString("FWQQ_BWBH"),jsonObject.getString("FWBS"),jsonObject.getString("FWQQZ_ZCXX"),
							jsonObject.getString("BWLYIPDZ"),jsonObject.getString("BWLYDKH"),jsonObject.getString("FFBS"),
							jsonObject.getString("FWQQ_RQSJ"),jsonObject.getString("FWQQ_NR"),jsonObject.getString("XXCZRY_XM"),
							jsonObject.getString("XXCZRY_GMSFHM"),jsonObject.getString("XXCZRY_GAJGJGDM"),jsonObject.getString("FWQQSB_BH"),
							jsonObject.getString("FWQQ_SJSJLX")}
					 *{"FWQQ_BWBH","FWBS","FWQQZ_ZCXX","BWLYIPDZ","BWLYDKH","FFBS","FWQQ_RQSJ","FWQQ_NR","XXCZRY_XM",
							"XXCZRY_GMSFHM","XXCZRY_GAJGJGDM","FWQQSB_BH","FWQQ_SJSJLX"}
					 */
					String[] info = null;
					String[] num = null;
					List<Fwqqzdb> checkList = fwqqzdbService.getCheckList();
					if(checkList!=null){
						num = new String[checkList.size()];
						for(int c=0;c<checkList.size();c++){
							num[c] = checkList.get(c).getZdm();
						}
					}
					
					if(num!=null && num.length>0){
						info = new String[num.length];
						for(int n=0;n<num.length;n++){
							info[n] = jsonObject.getString(num[n]);
						}
					}
					
					boolean[] checks = UserTService.checkInfo(info, num);
					boolean isCheck = true;
					if(checks!=null){
						for(boolean b:checks){
							isCheck = (isCheck && b);
						}
					}
					
//					if (keyList.size()<100&&(
//							keyList.contains("FWQQ_BWBH")
//							&&keyList.contains("BWLYIPDZ") &&keyList.contains("BWLYDKH") 
//							&&keyList.contains("FWQQZ_ZCXX") && keyList.contains("FWBS")
//							&& keyList.contains("FFBS") && keyList.contains("FWQQ_RQSJ")
//							&& keyList.contains("FWQQ_NR")&& keyList.contains("XXCZRY_XM")
//							&& keyList.contains("XXCZRY_GMSFHM") && keyList.contains("XXCZRY_GAJGJGDM")
//							&& keyList.contains("FWQQSB_BH")&& keyList.contains("FWQQ_SJSJLX")
//							)) 
					if(keyList.size()<100 && isCheck){
						Fwcyfzcb fwcyfzcb=	UserTService.selectByPrimaryKey(jsonObject.getString("FWQQZ_ZCXX"));
						Fwzyzcb fwzyzcb=	serveManageService.selectByPrimaryKey(jsonObject.optString("FWBS"));
						List<Fwzysqb> list=fwzysqbService.queryFwSqByFwcyfYyxtbh(jsonObject.getString("FWQQZ_ZCXX"), jsonObject.getString("FWBS"));
						   if(fwcyfzcb!=null&&fwzyzcb!=null&&list.size()>0){
							   if("1".equals(fwzyzcb.getFwztdm())) {
								   str="1";
							   }else{
								   str="0,服务已停用！";
							   }
						   }else{
							   Fwzyqqbwyccjb fwzyqqbwyccjb = new Fwzyqqbwyccjb();
							   fwzyqqbwyccjb.setQqbwbs(jsonObject.getString("FWQQ_BWBH"));
							   fwzyqqbwyccjb.setFwbs(jsonObject.optString("FWBS"));
							   fwzyqqbwyccjb.setFwqqzZcxx(jsonObject.getString("FWQQZ_ZCXX"));
							   fwzyqqbwyccjb.setFwqqIp(jsonObject.getString("BWLYIPDZ"));
							   fwzyqqbwyccjb.setFwqqDk(jsonObject.getString("BWLYDKH"));
							   fwzyqqbwyccjb.setFfbs(jsonObject.getString("FFBS"));
							   fwzyqqbwyccjb.setFwqqRqsj(jsonObject.getString("FWQQ_RQSJ"));
//							   fwzyqqbwyccjb.setFwqqNr(jsonObject.getString("FWQQ_NR"));
							   redisUtil.set(jsonObject.getString("FWQQ_BWBH")+"request_qqnr", jsonObject.getString("FWQQ_NR"),28000);
							   fwzyqqbwyccjb.setXxczryXm(jsonObject.getString("XXCZRY_XM"));
							   fwzyqqbwyccjb.setXxczryGmsfhm(jsonObject.getString("XXCZRY_GMSFHM"));
							   fwzyqqbwyccjb.setXxczryGajgjgdm(jsonObject.getString("XXCZRY_GAJGJGDM"));
							   fwzyqqbwyccjb.setFwqqsbBh(jsonObject.getString("FWQQSB_BH"));
							   fwzyqqbwyccjb.setFwqqSjsjlx(jsonObject.getString("FWQQ_SJSJLX"));
							   redisUtil.set(jsonObject.getString("FWQQ_BWBH")+"request_qqqw", jsonObject.toString(),28000);
//							   fwzyqqbwyccjb.setQqqw(jsonObject.toString());
							   fwzyqqbwyccjb.setStartTime(DateUtil.timestamp(new Date()));
							   String exceptionCols = "";
							   if(num!=null && checks!=null && checks.length==num.length){
								   for(int i=0;i<info.length;i++){
									   if(!checks[i]){
										   if(exceptionCols!=""){
											   exceptionCols+=",";
										   }
										   exceptionCols += num[i];
									   }
								   }
							   }
							   fwzyqqbwyccjb.setExceptionCols(exceptionCols);
							   ExceptionLogWrite exceptionWrite = new ExceptionLogWrite();
							   exceptionWrite.addRequestLog(fwzyqqbwyccjb);
							   if(fwcyfzcb==null){
								   str="0,身份验证失败！";
							   }
							   if(list.size()==0){
								   str="0,无此服务授权！";
							   }
							   if(fwzyzcb==null){
								   str="0,无此服务！";
							   }
						    }
						
						} else {
							Fwzyqqbwyccjb fwzyqqbwyccjb = new Fwzyqqbwyccjb();
							fwzyqqbwyccjb.setQqbwbs(jsonObject.getString("FWQQ_BWBH"));
						   fwzyqqbwyccjb.setFwbs(jsonObject.optString("FWBS"));
						   fwzyqqbwyccjb.setFwqqzZcxx(jsonObject.getString("FWQQZ_ZCXX"));
						   fwzyqqbwyccjb.setFwqqIp(jsonObject.getString("BWLYIPDZ"));
						   fwzyqqbwyccjb.setFwqqDk(jsonObject.getString("BWLYDKH"));
						   fwzyqqbwyccjb.setFfbs(jsonObject.getString("FFBS"));
						   fwzyqqbwyccjb.setFwqqRqsj(jsonObject.getString("FWQQ_RQSJ"));
//						   fwzyqqbwyccjb.setFwqqNr(jsonObject.getString("FWQQ_NR"));
						   redisUtil.set(jsonObject.getString("FWQQ_BWBH")+"request_qqnr", jsonObject.getString("FWQQ_NR"),28000);
						   fwzyqqbwyccjb.setXxczryXm(jsonObject.getString("XXCZRY_XM"));
						   fwzyqqbwyccjb.setXxczryGmsfhm(jsonObject.getString("XXCZRY_GMSFHM"));
						   fwzyqqbwyccjb.setXxczryGajgjgdm(jsonObject.getString("XXCZRY_GAJGJGDM"));
						   fwzyqqbwyccjb.setFwqqsbBh(jsonObject.getString("FWQQSB_BH"));
						   fwzyqqbwyccjb.setFwqqSjsjlx(jsonObject.getString("FWQQ_SJSJLX"));
						   redisUtil.set(jsonObject.getString("FWQQ_BWBH")+"request_qqqw", jsonObject.toString(),28000);
//						   fwzyqqbwyccjb.setQqqw(jsonObject.toString());
						   fwzyqqbwyccjb.setStartTime(DateUtil.timestamp(new Date()));
						   String exceptionCols = "";
						   if(num!=null && checks!=null && checks.length==num.length){
							   for(int i=0;i<info.length;i++){
								   if(!checks[i]){
									   if(exceptionCols!=""){
										   exceptionCols+=",";
									   }
									   exceptionCols += num[i];
								   }
							   }
						   }
						   fwzyqqbwyccjb.setExceptionCols(exceptionCols);
						   ExceptionLogWrite exceptionWrite = new ExceptionLogWrite();
						   exceptionWrite.addRequestLog(fwzyqqbwyccjb);
						   str="0,报文校验异常";
					}
				
		} else {
			str="0,报文格式异常";
		}
//		System.out.println("verify-result-------------" + str);
				return str;
	}

	

	@RequestMapping(value = "postBw", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object postBw(@RequestBody String param, HttpServletRequest request,
			HttpServletResponse response) {
		//System.out.println("postBw--------------" + param);
		JSONObject jsonObject = null;
		try {
			//System.out.println("postBw--------------start");
			
			jsonObject = JSONObject.fromObject(param);
			Fwcyfzcb fwcyfzcb=	UserTService.selectByPrimaryKey(jsonObject.getString("FWQQZ_ZCXX"));
			StringBuffer log=new StringBuffer();
			log.append("src_sys_name:\""+fwcyfzcb.getFwcyfYyxtmc()+"\"");//服务请求者_注册信息
			log.append(",src_sys_id:\""+jsonObject.get("FWBS")+"\"");//服务标识
			log.append(",src_biz_user_id:\""+jsonObject.get("XXCZRY_GMSFHM")+"\"");//XXCZRY_GMSFHM信息操作人员_身份证号
			log.append(",src_device_imei:\"A10000005C50BFA\"");
		    log.append(",event_tracing_id:\""+jsonObject.get("FWQQ_BWBH")+"\"");
			log.append(",event_type:\"service:service_databus:service_request\"");
			log.append(",event_message:\"服务请求\"");
//			logger.info(log.toString());
			
//			FwEventLog eventLog = new FwEventLog();
//			eventLog.setSrc_sys_id((String)jsonObject.get("FWBS"));
//			eventLog.setSrc_sys_name(fwcyfzcb.getFwcyfYyxtmc());
//			eventLog.setSrc_biz_user_id((String)jsonObject.get("XXCZRY_GMSFHM"));
//			eventLog.setEvent_start_time(DateUtil.timestamp(new Date()));
//			eventLog.setSrc_device_imei("A10000005C50BFA");
//			eventLog.setEvent_tracing_id((String)jsonObject.get("FWQQ_BWBH"));
//			eventLog.setEvent_message("服务请求");
//			eventLog.setRequest_content(jsonObject.get("FWQQ_NR")+"");
//			LogWrite logWrite = new LogWrite();
//			logWrite.addLog(eventLog);
			
			Fwzyqqbwcjb fwzyqqbwcjb = new Fwzyqqbwcjb();
			fwzyqqbwcjb.setQqbwbs(jsonObject.getString("FWQQ_BWBH"));
			fwzyqqbwcjb.setFfbs(jsonObject.getString("FFBS"));
			fwzyqqbwcjb.setFwbs(jsonObject.getString("FWBS"));
			fwzyqqbwcjb.setFwqqzZcxx(jsonObject.getString("FWQQZ_ZCXX"));
			fwzyqqbwcjb.setFwqqIp(jsonObject.getString("BWLYIPDZ"));
			fwzyqqbwcjb.setFwqqDk(jsonObject.getString("BWLYDKH"));
			fwzyqqbwcjb.setFwqqRqsj(jsonObject.getString("FWQQ_RQSJ"));
			fwzyqqbwcjb.setFwqqNr(jsonObject.getString("FWQQ_NR"));
			fwzyqqbwcjb.setXxczryXm(jsonObject.getString("XXCZRY_XM"));
			fwzyqqbwcjb.setXxczryGmsfhm(jsonObject.getString("XXCZRY_GMSFHM"));
			fwzyqqbwcjb.setXxczryGajgjgdm(jsonObject.getString("XXCZRY_GAJGJGDM"));
			fwzyqqbwcjb.setFwqqsbBh(jsonObject.getString("FWQQSB_BH"));
			fwzyqqbwcjb.setFwqqSjsjlx(jsonObject.getString("FWQQ_SJSJLX"));
			fwzyqqbwcjb.setQqqw(jsonObject.toString());
			fwzyqqbwcjb.setStartTime(DateUtil.timestamp(new Date()));
			
			fwzyqqbwcjb.setYysbs(jsonObject.optString("YYSBS"));
			try {
				//System.out.println("redis--------------start");
				//System.out.println("FWQQ_BWBH===================================="+jsonObject.getString("FWQQ_BWBH"));
				//System.out.println("FWQQ_NR===================================="+jsonObject.getString("FWQQ_NR"));
				redisUtil.set(jsonObject.getString("FWQQ_BWBH")+"service_request", jsonObject.getString("FWQQ_NR"),28000);
				//System.out.println("redis--------------end");
			} catch (Exception e) {
				e.printStackTrace();
			}
			//System.out.println("db--------------start");
			RequestLogWrite requestWrite = new RequestLogWrite();
			requestWrite.addRequestLog(fwzyqqbwcjb);
			//System.out.println("db--------------end");
			//System.out.println("postBw--------------end");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return "请求内容JSON转换异常";
		}
		//System.out.println("postBw-result-------------" + jsonObject.get("FWQQ_NR").toString());
		return jsonObject.get("FWQQ_NR").toString();
	}

	@RequestMapping(value = "postDlOld", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object postDlOld(@RequestBody String param, HttpServletRequest request,
			HttpServletResponse response) {
//		System.out.println("postDl--------------" + param);
		String str="";
		try {
			JSONObject jsonObject = JSONObject.fromObject(param);
			
			Fwzyzcb Fwzyzcb=	serveManageService.selectByPrimaryKey(jsonObject.getString("FWBS"));
			 str=Fwzyzcb.getFwRkdzlb();
			/*JSONObject jsonObject1 =(JSONObject) jsonObject.get("FWQQ_NR");
			String method = (String) jsonObject1.get("method");
			JSONObject jsonObjectparams = (JSONObject) jsonObject1.get("params");
			Iterator it = jsonObjectparams.keys();
			jsonObjectResule = new JSONObject();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = jsonObjectparams.getString(key);
				jsonObjectResule.put(key, value);
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("postDl-result-------------" +str);
		return str;
	}
	
	@RequestMapping(value = "postDl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object postDl(@RequestBody String param, HttpServletRequest request,
			HttpServletResponse response) {
//		System.out.println("postDl--------------" + param);
		String str="";
		try {
			JSONObject jsonObject = JSONObject.fromObject(param);
			
			Fwzyzcb Fwzyzcb=serveManageService.selectByPrimaryKey(jsonObject.getString("FWBS"));
			String[] fwRkdzlbs = Fwzyzcb.getFwRkdzlb().split(";");
			String yysbs = jsonObject.optString("YYSBS");
			
			if(fwRkdzlbs.length==1){
				str = fwRkdzlbs[0];
			}else if(fwRkdzlbs.length>1){
				if(yysbs.equals("dx")){
					str = fwRkdzlbs[1];
				}else{
					str = fwRkdzlbs[0];
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("postDl-result-------------" +str);
		return str;
	}
	
	@RequestMapping(value = "postTestDl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object postTestDl(@RequestBody String param, HttpServletRequest request,
			HttpServletResponse response) {
//		System.out.println("postTestDl--------------" + param);
		JSONObject jsonObjectResule = new JSONObject();
		try {
			JSONObject jsonObject = JSONObject.fromObject(param);
			
			JSONObject jsonObject1 =(JSONObject) jsonObject.get("FWQQ_NR");
			String method = (String) jsonObject1.get("method");
			JSONObject jsonObjectparams = (JSONObject) jsonObject1.get("params");
			Iterator it = jsonObjectparams.keys();
			
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = jsonObjectparams.getString(key);
				jsonObjectResule.put(key, value);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("postTestDl-result-------------" +jsonObjectResule.toString());
		return jsonObjectResule.toString();
	}
	@RequestMapping(value = "queryCached", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object queryCached(@RequestBody String param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		    System.out.println("queryCached--------------" + param);
		    JSONObject jsonObject = JSONObject.fromObject(param);
			Fwzyzcb fwzyzcb=serveManageService.selectByPrimaryKey(jsonObject.get("FWBS").toString());
			Fwcyfzcb fwcyfzcb=	UserTService.selectByPrimaryKey(jsonObject.getString("FWQQZ_ZCXX"));//服务请求者_注册信息
			Fwcyfzcb fwcyfzcb1=	UserTService.selectByPrimaryKey(fwzyzcb.getFwtgzYyxtbh());//服务提供者_注册信息
			FwzyffzcbKey FwzyffzcbKey=new FwzyffzcbKey();
			FwzyffzcbKey.setFfbs(jsonObject.get("FFBS").toString());
			FwzyffzcbKey.setFwbs(jsonObject.get("FWBS").toString());
			Fwzyffzcb Fwzyffzcb=FwzyffzcbDao.selectByPrimaryKey(FwzyffzcbKey);
			String str = null;
			if(Fwzyffzcb!=null){
				StringBuffer b=new StringBuffer();
				if(Fwzyffzcb.getSfhcsj().equals("1")){
				JSONObject jsonstr=(JSONObject) jsonObject.get("FWQQ_NR");
				JSONObject jsonparams=(JSONObject) jsonstr.get("params");
				Iterator it = jsonparams.keys();
			    b.append(jsonObject.get("FWBS").toString());
				b.append(jsonObject.get("FFBS").toString());
				while (it.hasNext()) {
					String key = (String) it.next();
					b.append(jsonparams.get(key));
				}
				}
			if(Fwzyffzcb.getSfhcsj().equals("1")&&MemcachedUtils.get(b.toString())!=null){
				StringBuffer log=new StringBuffer();
				 log.append("src_sys_name:\""+fwcyfzcb.getFwcyfYyxtmc()+"\"");//服务请求者_注册信息
				        log.append(",src_sys_id:\""+jsonObject.get("FWBS")+"\"");//服务标识
				        log.append(",src_biz_user_id:\""+jsonObject.get("XXCZRY_GMSFHM")+"\"");//信息操作人员_身份证号
						log.append(",src_device_imei:\"A10000005C50BFA\"");
						log.append(",dest_sys_name:\""+fwcyfzcb1.getFwcyfYyxtmc()+"\"");
						log.append(",dest_sys_id:\""+jsonObject.get("FWBS")+"\"");
						log.append(",dest_sys_interface:\""+fwzyzcb.getFwRkdzlb()+"\"");
						//log.append(",dest_sys_interface_desc:\""+Fwzyffzcb.getFfms()+"\"");
						log.append(",dest_sys_interface_desc:\""+fwzyzcb.getFwMs()+"\"");
						log.append(",dest_device_addr:\""+fwzyzcb.getFwIpdz()+"\"");
						log.append(",dest_device_port:\""+fwzyzcb.getFwZxdkhm()+"\"");
						log.append(",event_tracing_id:\""+jsonObject.get("FWQQ_BWBH")+"\"");
						log.append(",event_type:\"service:service_databus:service_response\"");
						log.append(",event_result:\"200\"");
						log.append(",event_result_desc:\"查询成功！\"");
						log.append(",event_message:\"服务响应\"");
//						logger.info(log.toString());
						
				JSONObject json = new JSONObject();
				json.put("FWXX_BWBH", jsonObject.get("FWQQ_BWBH"));
				json.put("FWTGZ_ZCXX", jsonObject.get("FWQQZ_ZCXX"));
				json.put("FWBS", jsonObject.get("FWBS"));
				json.put("FFBS", jsonObject.get("FFBS"));
				json.put("FFMS", fwzyzcb.getFwMs());
				json.put("XXCZRY_XM", jsonObject.get("XXCZRY_XM"));
				json.put("XXCZRY_GMSFHM", jsonObject.get("XXCZRY_GMSFHM"));
				json.put("XXCZRY_GAJGJGDM", jsonObject.get("XXCZRY_GAJGJGDM"));
				json.put("FWQQSB_BH", jsonObject.get("FWQQSB_BH"));
				json.put("FWTG_RQSJ", jsonObject.get("FWQQ_RQSJ"));
				json.put("FW_IPDZ", fwzyzcb.getFwIpdz());
				json.put("FW_ZXDKHM", fwzyzcb.getFwZxdkhm());
				json.put("FWTGZTDM", "200");
				json.put("FWTGZTMS", "");
				
				
				
				json.put("FWTG_NR", MemcachedUtils.get(b.toString()));
				json.put("FWTG_SJSJLX", "service_response");
				
				Fwzyxybwcjb fwzyxybwcjb = new Fwzyxybwcjb();
				fwzyxybwcjb.setXybwbs(jsonObject.getString("FWQQ_BWBH"));
				fwzyxybwcjb.setFwbs(jsonObject.getString("FWBS"));
				fwzyxybwcjb.setFwtgzZcxx(jsonObject.getString("FWQQZ_ZCXX"));
				fwzyxybwcjb.setFfms(fwzyzcb.getFwMs());
				fwzyxybwcjb.setFwtgIp(fwzyzcb.getFwIpdz());
				fwzyxybwcjb.setFwtgDk(fwzyzcb.getFwZxdkhm());
				fwzyxybwcjb.setFfbs(jsonObject.getString("FFBS"));
				fwzyxybwcjb.setFwtgztdm("200");
				fwzyxybwcjb.setFwtgztms("");
				fwzyxybwcjb.setFwtgRqsj(DateUtil.timestamp(new Date()));
				fwzyxybwcjb.setFwtgNr((String)MemcachedUtils.get(b.toString()));
				fwzyxybwcjb.setXxczryXm(jsonObject.getString("XXCZRY_XM"));
				fwzyxybwcjb.setXxczryGmsfhm(jsonObject.getString("XXCZRY_GMSFHM"));
				fwzyxybwcjb.setXxczryGajgjgdm(jsonObject.getString("XXCZRY_GAJGJGDM"));
				fwzyxybwcjb.setFwqqsbBh(jsonObject.getString("FWQQSB_BH"));
				fwzyxybwcjb.setFwtgSjsjlx("service_response");
				fwzyxybwcjb.setEndTime(DateUtil.timestamp(new Date()));
				fwzyxybwcjb.setXyqw("{\"param\","+jsonObject.toString()+",\"param1\","+fwzyxybwcjb.getFwtgNr()+"}");
				ResponseLogWrite responseWrite = new ResponseLogWrite();
				responseWrite.addResponseLog(fwzyxybwcjb);
			
//				FwEventLog eventLog = new FwEventLog();
//	            eventLog.setSrc_sys_name(fwcyfzcb.getFwcyfYyxtmc());//服务请求者_注册信息
//				eventLog.setSrc_sys_id((String)jsonObject.get("FWBS"));//服务标识
//				eventLog.setSrc_biz_user_id((String)jsonObject.get("XXCZRY_GMSFHM"));//信息操作人员_身份证号
//				eventLog.setSrc_device_imei("A10000005C50BFA");
//				eventLog.setDest_sys_name(fwcyfzcb1.getFwcyfYyxtmc());
//				eventLog.setDest_sys_id((String)jsonObject.get("FWBS"));
//				eventLog.setDest_sys_interface(fwzyzcb.getFwRkdzlb());
//				eventLog.setDest_sys_interface_desc(fwzyzcb.getFwMs());
//				eventLog.setDest_device_addr(fwzyzcb.getFwIpdz());
//				eventLog.setDest_device_port(fwzyzcb.getFwZxdkhm());
//				eventLog.setEvent_tracing_id((String)jsonObject.get("FWQQ_BWBH"));
//				eventLog.setEvent_type("service(service_databus(service_response");
//				eventLog.setEvent_result("200");
//				eventLog.setEvent_result_desc("查询成功！");
//				eventLog.setEvent_message("服务响应");
//				eventLog.setEvent_end_time(DateUtil.timestamp(new Date()));
//				eventLog.setResponse_content(json.toString());
//				LogWrite logWrite = new LogWrite();
//				logWrite.addLog(eventLog);
				str=json.toString();
			}else{
				str="-1";
			}
			}else{
				str="-1";
			}
			
		
//		System.out.println("queryCached-result-------------" +str);
		return str;
	}
	@RequestMapping(value = "postResult", method = RequestMethod.POST)
	@ResponseBody
	public Object postResult(@RequestBody String param,
			HttpServletRequest request, HttpServletResponse response) {
//		System.out.println("postResult=======param==param===="+param);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = JSONObject.fromObject(param);
		} catch (Exception e) {
			jsonObject.put("FWTG_NR", e.getMessage());
			return jsonObject.toString();
		}
		
		//JSONObject jsonObjectparam = (JSONObject) jsonObject.get("param");
		//JSONObject jsonObjectparam1 = (JSONObject) jsonObject.get("param1");
		
		JSONObject jsonObjectparam = new JSONObject();
		try {
			jsonObjectparam = (JSONObject) jsonObject.get("param");
		} catch (Exception e) {
			jsonObjectparam.put("FWTG_NR", e.getMessage());
			return jsonObjectparam.toString();
		}
		
		JSONObject jsonObjectparam1 = new JSONObject();
		try {
			jsonObjectparam1 = (JSONObject) jsonObject.get("param1");
		} catch (Exception e) {
			jsonObjectparam1.put("FWTG_NR", e.getMessage());
			return jsonObjectparam1.toString();
		}
		
//		System.out.println("postResult=======jsonObject==param===="+jsonObject.toString());
//		System.out.println("postResult=======jsonObjectparam1==param1===="+jsonObjectparam1.toString());
		Fwzyzcb fwzyzcb=serveManageService.selectByPrimaryKey(jsonObjectparam.get("FWBS").toString());
		Fwcyfzcb fwcyfzcb=	UserTService.selectByPrimaryKey(jsonObjectparam.getString("FWQQZ_ZCXX"));//服务请求者_注册信息
		Fwcyfzcb fwcyfzcb1=	UserTService.selectByPrimaryKey(fwzyzcb.getFwtgzYyxtbh());//服务提供者_注册信息
		FwzyffzcbKey FwzyffzcbKey=new FwzyffzcbKey();
		FwzyffzcbKey.setFfbs(jsonObjectparam.get("FFBS").toString());
		FwzyffzcbKey.setFwbs(jsonObjectparam.get("FWBS").toString());
		Fwzyffzcb Fwzyffzcb=FwzyffzcbDao.selectByPrimaryKey(FwzyffzcbKey);
		if(Fwzyffzcb!=null){
		if(Fwzyffzcb.getSfhcsj()!=null){
			if(Fwzyffzcb.getSfhcsj().equals("1")){
				JSONObject json=(JSONObject) jsonObjectparam.get("FWQQ_NR");
				JSONObject jsonparams=(JSONObject) json.get("params");
				Iterator it = jsonparams.keys();
				StringBuffer b=new StringBuffer();
				b.append(jsonObjectparam.get("FWBS").toString());
				b.append(jsonObjectparam.get("FFBS").toString());
				while (it.hasNext()) {
					String key = (String) it.next();
					b.append(jsonparams.get(key));
				}
				MemcachedUtils.set(b.toString(), jsonObjectparam1.toString(), new Date(1000 * Fwzyffzcb.getSjyxsj().intValue()));
			}
		}
		}
		
		StringBuffer log=new StringBuffer();
		 log.append("src_sys_name:\""+fwcyfzcb.getFwcyfYyxtmc()+"\"");//服务请求者_注册信息
		        log.append(",src_sys_id:\""+jsonObjectparam.get("FWBS")+"\"");//服务标识
		        log.append(",src_biz_user_id:\""+jsonObjectparam.get("XXCZRY_GMSFHM")+"\"");//信息操作人员_身份证号
				log.append(",src_device_imei:\"A10000005C50BFA\"");
				log.append(",dest_sys_name:\""+fwcyfzcb1.getFwcyfYyxtmc()+"\"");
				log.append(",dest_sys_id:\""+jsonObjectparam.get("FWBS")+"\"");
				log.append(",dest_sys_interface:\""+fwzyzcb.getFwRkdzlb()+"\"");
				log.append(",dest_sys_interface_desc:\""+Fwzyffzcb==null?fwzyzcb.getFwMs():Fwzyffzcb.getFfms()+"\"");
				//log.append(",dest_sys_interface_desc:\""+fwzyzcb.getFwMs()+"\"");
				log.append(",dest_device_addr:\""+fwzyzcb.getFwIpdz()+"\"");
				log.append(",dest_device_port:\""+fwzyzcb.getFwZxdkhm()+"\"");
				log.append(",event_tracing_id:\""+jsonObjectparam.get("FWQQ_BWBH")+"\"");
				log.append(",event_type:\"service:service_databus:service_response\"");
				log.append(",event_result:\"200\"");
				log.append(",event_result_desc:\"查询成功！\"");
				log.append(",event_message:\"服务响应\"");
//				logger.info(log.toString());
				
		JSONObject json = new JSONObject();
		json.put("FWXX_BWBH", jsonObjectparam.get("FWQQ_BWBH"));
		json.put("FWTGZ_ZCXX", jsonObjectparam.get("FWQQZ_ZCXX"));
		json.put("FWBS", jsonObjectparam.get("FWBS"));
		json.put("FFBS", jsonObjectparam.get("FFBS"));
		json.put("FFMS", Fwzyffzcb==null?fwzyzcb.getFwMs():Fwzyffzcb.getFfms());
		//json.put("FFMS", fwzyzcb.getFwMs());
		json.put("XXCZRY_XM", jsonObjectparam.get("XXCZRY_XM"));
		json.put("XXCZRY_GMSFHM", jsonObjectparam.get("XXCZRY_GMSFHM"));
		json.put("XXCZRY_GAJGJGDM", jsonObjectparam.get("XXCZRY_GAJGJGDM"));
		json.put("FWQQSB_BH", jsonObjectparam.get("FWQQSB_BH"));
		json.put("FWTG_RQSJ", jsonObjectparam.get("FWQQ_RQSJ"));
		json.put("FW_IPDZ", fwzyzcb.getFwIpdz());
		json.put("FW_ZXDKHM", fwzyzcb.getFwZxdkhm());
		json.put("FWTGZTDM", "200");
		json.put("FWTGZTMS", "");
		json.put("FWTG_NR", jsonObjectparam1.toString());
		json.put("FWTG_SJSJLX", "service_response");
		try {
			redisUtil.set(jsonObjectparam.get("FWQQ_BWBH")+"service_response", jsonObjectparam1.toString(),28000);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("json.toString()=======postResult========" + json.toString());
		
		try {
			Fwzyxybwcjb fwzyxybwcjb = new Fwzyxybwcjb();
			fwzyxybwcjb.setXybwbs(jsonObjectparam.getString("FWQQ_BWBH"));
			fwzyxybwcjb.setFwbs(jsonObjectparam.getString("FWBS"));
			fwzyxybwcjb.setFwtgzZcxx(jsonObjectparam.getString("FWQQZ_ZCXX"));
			if(Fwzyffzcb!=null){
				fwzyxybwcjb.setFfms(Fwzyffzcb.getFfms());
				
			}
			/*fwzyxybwcjb.setFfms(jsonObjectparam.getString("FFMS"));*/
			fwzyxybwcjb.setFwtgIp(fwzyzcb.getFwIpdz());
			fwzyxybwcjb.setFwtgDk(fwzyzcb.getFwZxdkhm());
			fwzyxybwcjb.setFfbs(jsonObjectparam.getString("FFBS"));
			fwzyxybwcjb.setFwtgztdm("200");
			fwzyxybwcjb.setFwtgztms("");
			fwzyxybwcjb.setFwtgRqsj(DateUtil.timestamp(new Date()));
			fwzyxybwcjb.setFwtgNr(jsonObjectparam1.toString());
			fwzyxybwcjb.setXxczryXm(jsonObjectparam.getString("XXCZRY_XM"));
			fwzyxybwcjb.setXxczryGmsfhm(jsonObjectparam.getString("XXCZRY_GMSFHM"));
			fwzyxybwcjb.setXxczryGajgjgdm(jsonObjectparam.getString("XXCZRY_GAJGJGDM"));
			fwzyxybwcjb.setFwqqsbBh(jsonObjectparam.getString("FWQQSB_BH"));
			fwzyxybwcjb.setFwtgSjsjlx("service_response");
			fwzyxybwcjb.setEndTime(DateUtil.timestamp(new Date()));
//			fwzyxybwcjb.setXyqw(jsonObject.toString());
			fwzyxybwcjb.setXyqw(jsonObject.toString());
			ResponseLogWrite responseWrite = new ResponseLogWrite();
			responseWrite.addResponseLog(fwzyxybwcjb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
//		FwEventLog eventLog = new FwEventLog();
//
//		eventLog.setSrc_sys_name(fwcyfzcb.getFwcyfYyxtmc());//服务请求者_注册信息
//		eventLog.setSrc_sys_id((String)jsonObjectparam.get("FWBS"));//服务标识
//		eventLog.setSrc_biz_user_id((String)jsonObjectparam.get("XXCZRY_GMSFHM"));//信息操作人员_身份证号
//		eventLog.setSrc_device_imei("A10000005C50BFA");
//		eventLog.setDest_sys_name(fwcyfzcb1.getFwcyfYyxtmc());
//		eventLog.setDest_sys_id((String)jsonObjectparam.get("FWBS"));
//		eventLog.setDest_sys_interface(fwzyzcb.getFwRkdzlb());
//		eventLog.setDest_sys_interface_desc(fwzyzcb.getFwMs());
//		eventLog.setDest_device_addr(fwzyzcb.getFwIpdz());
//		eventLog.setDest_device_port(fwzyzcb.getFwZxdkhm());
//		eventLog.setEvent_tracing_id((String)jsonObjectparam.get("FWQQ_BWBH"));
//		eventLog.setEvent_type("service(service_databus(service_response");
//		eventLog.setEvent_result("200");
//		eventLog.setEvent_result_desc("查询成功！");
//		eventLog.setEvent_message("服务响应");
//		eventLog.setEvent_end_time(DateUtil.timestamp(new Date()));
//		eventLog.setResponse_content(json.toString());
//		LogWrite logWrite = new LogWrite();
//		logWrite.addLog(eventLog);
		return json.toString();
		
	}
	/**
	 * 获取接口类型
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "postType", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object postType(@RequestBody String param,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("postType--------------" + param);
		JSONObject jsonObject = JSONObject.fromObject(param);
//		JSONObject jsonObjectparam = (JSONObject) jsonObject.get("FWBS");
		String fwbs = jsonObject.getString("FWBS");
		String ffbs = jsonObject.getString("FFBS");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fwbs", fwbs);
		map.put("ffbs", ffbs);
//		System.out.println("fwbs--------------" + fwbs);
		String postType=fwzyffzcbService.queryFFLXbyFwbs(map);
//		System.out.println("postType=======jsonObject==JKLX===="+postType.toString());
		return postType;
		
	}
	
	/**
	 * 获取post参数
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "postPramas", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object postPramas(@RequestBody String param,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(param);
		JSONObject jsonstr=(JSONObject) jsonObject.get("FWQQ_NR");
		JSONObject jsonparams=(JSONObject) jsonstr.get("params");
//		System.out.println("postPramas=======jsonObject==params===="+jsonparams.toString());
		return jsonparams.toString();
	}
	/**
	 * 获取get参数
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getPramas", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getPramas(@RequestBody String param,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject jsonObject = JSONObject.fromObject(param);
		String fwbs = jsonObject.getString("FWBS");
		String ffbs = jsonObject.getString("FFBS");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fwbs", fwbs);
		map.put("ffbs", ffbs);
		
		String postUrl=fwzyffzcbService.queryURLbyFwbs(map);
//		System.out.println("getPramas=======jsonObject==URL===="+postUrl.toString());
		
		JSONObject jsonstr=(JSONObject) jsonObject.get("FWQQ_NR");
		JSONObject jsonparams=(JSONObject) jsonstr.get("params");
		String arrs=jsonparams.toString();
//		System.out.println("getPramas=======jsonObject==params===="+arrs);
		if(!arrs.trim().equals("")&&!arrs.trim().equals("{}")) {
			arrs=arrs.substring(1, arrs.length()-1);
			String[] allparams=arrs.toString().split(",");
			String pramas="";
			if(allparams.length!=0) {
				for (int i = 0; i < allparams.length; i++) {
					if(i==0) {
						String[] arr= allparams[i].split(":");
						pramas+="?"+arr[0].replace("\"", "")+"="+arr[1].replace("\"", "");
//						System.out.println("pramas=======pramas==pramas===="+pramas);
					}else {
						String[] arr= allparams[i].split(":");
						pramas+="&"+arr[0].replace("\"", "")+"="+arr[1].replace("\"", "");
//						System.out.println("pramas=======pramas==pramas===="+postUrl);
					}
				}
				postUrl=postUrl+pramas;
			}
		} 
		
//		System.out.println("postUrl=======jsonObject==postUrl===="+postUrl);
		return postUrl;
		
	}
	
	/**
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "postUrl", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object postUrl(@RequestBody String param,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(param);
		String fwbs = jsonObject.getString("FWBS");
		String ffbs = jsonObject.getString("FFBS");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fwbs", fwbs);
		map.put("ffbs", ffbs);
		String postUrl=fwzyffzcbService.queryURLbyFwbs(map);
//		System.out.println("postUrl=======jsonObject==URL===="+postUrl.toString());
		return postUrl;
	}
	
	@RequestMapping(value = "getResourceList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getResourceList(@RequestBody String param, HttpServletRequest request,
			HttpServletResponse response) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list =fwqqzdbService.getResourceList();
		return list;
	}
	
	/**
	 * fastdfs上传接口
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object uploadFile( @RequestParam("yyid") String yyid,
			@RequestParam("fileName") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fileName=file.getOriginalFilename();
//		System.out.println("fileName==============="+fileName);
		MultipartFile files = file; 

        CommonsMultipartFile cf= (CommonsMultipartFile)files; 
        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
        File file1 = fi.getStoreLocation(); 
		long begin = System.currentTimeMillis(); 
//		System.out.println("yyid==============="+yyid);
		String uuid =UUIDGeneratorUtil.getUUID();
//		System.out.println("uuid==============="+uuid);

		String md5 =MD5Utils.getFileMD5String(file1);
//		System.out.println("md5==============="+md5);
		byte[] datas= FileTobyte(file1);
		String path =FastDFSClient.uploadFile(datas,"jpg");
//		System.out.println("path==============="+path);
		long end = System.currentTimeMillis();  
		long scsc = end-begin;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sj=sdf.format(date);
		String scsj=sj.substring(0,8);
		String scmxsj=sj;
//		Upload upload = new Upload(uuid,md5,yyid,path,fileName,scsj,scmxsj,scsc);
//		uploadService.insertUpload(upload);
		
		return path;
	}
	
	
	/**
	 * fastdfs下载接口
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
//	@RequestMapping(value = "/downloadFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
	@RequestMapping(value = "/downloadFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    String filename = request.getParameter("fileName");
        DataInputStream in = null;
        OutputStream out = null;
        try{
        	response.reset();// 清空输出流
            
            String resultFileName = filename;
            resultFileName = URLEncoder.encode(resultFileName,"UTF-8");  
            response.setCharacterEncoding("UTF-8");  
            response.setHeader("Content-disposition", "attachment; filename=" + resultFileName);// 设定输出文件头
            //输入流：本地文件路径
            byte[] bytes =FastDFSClient.downloaBytes("group1/M00/00/00/FAELNl1Cej2AFrC8ABFyi1Ckm9Y426.jpg");
            //输出流
            out = response.getOutputStream();
            //输出文件
            out.write(bytes);
        } catch(Exception e){
            e.printStackTrace();
            response.reset();
            try {
                OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8");  
                String data = "<script language='javascript'>alert(\"\\u64cd\\u4f5c\\u5f02\\u5e38\\uff01\");</script>";
                writer.write(data); 
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	/**
	 * 将文件转换成byte数组
	 * 
	 * @param filePath
	 * @return
	 */
	public static byte[] FileTobyte(File tradeFile) {
		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(tradeFile);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	
	@RequestMapping(value = "setRedisData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object setRedisData(@RequestBody String param, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			redisUtil.set("aaaaaaaaa", "bbbbbbbbb",10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	@RequestMapping(value = "getRedisData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getRedisData(@RequestBody String param, HttpServletRequest request,
			HttpServletResponse response) {
		String result="";
		try {
			 result =redisUtil.get("aaaaaaaaa").toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	

	@RequestMapping(value = "sendPost", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object sendPost(@RequestBody String param, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObject = null;
		String result="";
		try {
			jsonObject = JSONObject.fromObject(param);
			Map<String,Object> map = new HashMap<String,Object>();
			String fwbs=jsonObject.getString("FWBS").toString();
			String ffbs=jsonObject.getString("FFBS").toString();
			map.put("fwbs", fwbs);
			map.put("ffbs", ffbs);
			Fwzyffzcb fwzyffzcb = FwzyffzcbDao.selectByFwbsFfbs(map);
			String url=fwzyffzcb.getUrl();
			String fflx=fwzyffzcb.getFflx();
			
			if(fflx.equals("GET")){
				String qqnr=jsonObject.getString("FWQQ_NR").toString();
				JSONObject nr=JSONObject.fromObject(qqnr);
				String paramsMap= nr.getString("params").trim(); 
				String arr[]=paramsMap.replace("{", "").replace("}", "").split(",");
				String params ="";
				if(arr.length>1) {
					for (int i = 0; i < arr.length; i++) {
						String brr[]=arr[i].split(":");
						if(i==0) {
							params+="?"+brr[0].replace("\"", "")+"="+brr[1].replace("\"", "");
						}else {
							params+="&"+brr[0].replace("\"", "")+"="+brr[1].replace("\"", "");
						}
					}
				}
				result=HttpRequest.sendGet(url, params);
			}else if(fflx.equals("POST")) {
//				String qqnr="{"+ "\"FWQQ_NR\""+":"+jsonObject.getString("FWQQ_NR").toString()+"}";
				String qqnr=jsonObject.getString("FWQQ_NR").toString();
				JSONObject nr=JSONObject.fromObject(qqnr);
				String params= nr.getString("params"); 
				result=HttpRequest.sendPost(url, params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@RequestMapping(value = "sendGet", method = RequestMethod.GET)
	@ResponseBody
	public Object sendGet(HttpServletRequest request,
			HttpServletResponse response) {
		String name=request.getParameter("name");
		String id=request.getParameter("id");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("id", id);
		return map;
	}
	
	@RequestMapping(value = "sendGet1", method = RequestMethod.GET)
	@ResponseBody
	public Object sendGet1(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", "success");
		return map;
	}
}