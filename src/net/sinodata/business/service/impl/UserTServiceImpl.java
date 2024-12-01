package net.sinodata.business.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import net.sinodata.business.dao.FwcyfzcbDao;
import net.sinodata.business.dao.JgdmbDao;
import net.sinodata.business.entity.Fwcyfzcb;
import net.sinodata.business.entity.Jgdmb;
import net.sinodata.business.service.UserTService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTServiceImpl implements UserTService {
	@Autowired
	private FwcyfzcbDao fwcyfzcbDao;
	@Autowired
	private JgdmbDao jgdmbDao;
	
	
	
	@Override
	public SearchResult userList(Page page, Map<String, Object> condition) {
		// TODO Auto-generated method stub
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Fwcyfzcb> data=	fwcyfzcbDao.getUserListByPage( condition);
		int  count=	fwcyfzcbDao.getUserCountByPage( condition);
		return new SearchResult(data,count);
	}



	@Override
	public List<Fwcyfzcb> findUserByLoginName(String userName) {
		// TODO Auto-generated method stub
		return fwcyfzcbDao.findByLoginName(userName);
	}



	@Override
	public int deleteByPrimaryKey(String fwcyfYyxtbh) {
		// TODO Auto-generated method stub
		return fwcyfzcbDao.deleteByPrimaryKey(fwcyfYyxtbh);
	}



	@Override
	public int insert(Fwcyfzcb record) {
		// TODO Auto-generated method stub
		return fwcyfzcbDao.insert(record);
	}



	@Override
	public int insertSelective(Fwcyfzcb record) {
		// TODO Auto-generated method stub
		return fwcyfzcbDao.insertSelective(record);
	}



	@Override
	public Fwcyfzcb selectByPrimaryKey(String fwcyfYyxtbh) {
		// TODO Auto-generated method stub
		return fwcyfzcbDao.selectByPrimaryKey(fwcyfYyxtbh);
	}



	@Override
	public int updateByPrimaryKeySelective(Fwcyfzcb record) {
		// TODO Auto-generated method stub
		return fwcyfzcbDao.updateByPrimaryKeySelective(record);
	}



	@Override
	public int updateByPrimaryKey(Fwcyfzcb record) {
		// TODO Auto-generated method stub
		return fwcyfzcbDao.updateByPrimaryKey(record);
	}
	

	public boolean[] checkInfo(String[] info,String[] num){
		if(info!=null && num!=null && info.length!=0 && info.length==num.length){
			boolean[] reBoolean = new boolean[info.length];
			for(int i=0;i<info.length;i++){
				if(num[i]!=null && !num[i].equals("")){
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
					 */
					
					//验证报文编号
					if("FWQQ_BWBH".equals(num[i])){
						reBoolean[i] = checkBwbh(info[i]);
					}
					//验证服务标识
					if("FWBS".equals(num[i])){
						reBoolean[i] = checkFwbs(info[i]);
					}
					//验证服务参与方标识
					if("FWQQZ_ZCXX".equals(num[i])){
						reBoolean[i] = checkFwcyfbs(info[i]);
					}
					//验证报文来源IP
					if("BWLYIPDZ".equals(num[i])){
						reBoolean[i] = checkBwlyip(info[i]);
					}
					//验证报文来源端口号
					if("BWLYDKH".equals(num[i])){
						reBoolean[i] = checkBwlydkh(info[i]);
					}
					//验证方法标识
					if("FFBS".equals(num[i])){
						reBoolean[i] = checkFfbs(info[i]);
					}
					//验证请求时间
					if("FWQQ_RQSJ".equals(num[i])){
						reBoolean[i] = checkQqsj(info[i]);
					}
					//验证服务请求内容
					if("FWQQ_NR".equals(num[i])){
						reBoolean[i] = checkFwqqnr(info[i]);
					}
					//验证操作人员姓名
					if("XXCZRY_XM".equals(num[i])){
						reBoolean[i] = checkCzryxm(info[i]);
					}
					//验证操作人员公民身份号码
					if("XXCZRY_GMSFHM".equals(num[i])){
						reBoolean[i] = checkCzrygmsfhm(info[i]);
					}
					//验证操作人员公安机关机构代码
					if("XXCZRY_GAJGJGDM".equals(num[i])){
						reBoolean[i] = checkCzrygajgjgdm(info[i]);
					}
					//验证服务请求设备编号
					if("FWQQSB_BH".equals(num[i])){
						reBoolean[i] = checkFwqqsbbh(info[i]);
					}
					//验证审计事件类型
					if("FWQQ_SJSJLX".equals(num[i])){
						reBoolean[i] = checkSjsjlx(info[i]);
					}
				}
			}
			return reBoolean;
		}
		return null;
	}
	
	//验证报文编号
	private boolean checkBwbh(String bwbh){
		//验证报文编号长度是否为35位
		if(bwbh!=null && bwbh.length()==35){
			
			//验证是否以SR开头
			if(!bwbh.substring(0, 2).equals("SR")){
				return false;
			}
			
			//验证是否为IP
			String ip = bwbh.substring(2, 14);
			String ip1 = ip.substring(0,3);
			String ip2 = ip.substring(3,6);
			String ip3 = ip.substring(6,9);
			String ip4 = ip.substring(9,12);
			try{
				if(Integer.parseInt(ip1)<0 && Integer.parseInt(ip1)>255){
					return false;
				}
				if(Integer.parseInt(ip2)<0 && Integer.parseInt(ip2)>255){
					return false;
				}
				if(Integer.parseInt(ip3)<0 && Integer.parseInt(ip3)>255){
					return false;
				}
				if(Integer.parseInt(ip4)<0 && Integer.parseInt(ip4)>255){
					return false;
				}
			}catch(Exception e){
				return false;
			}
			
			//验证是否为日期,且是否准确
			Calendar ca = Calendar.getInstance();
			String date = bwbh.substring(14,22);
			try{
				int year = Integer.parseInt(date.substring(0,4));
				int month = Integer.parseInt(date.substring(4,6));
				int day = Integer.parseInt(date.substring(6,8));
				
				if(day>31 || day<1){
					return false;
				}
				
				if(month==2){
					if(day==30 || day==31){
						return false;
					}
					if(year%4!=0 && day==29){
						return false;
					}
					if(year%100==0 && year%400!=0 && day==29){
						return false;
					}
				}
				if(month==4 || month==6 || month==9 || month==11){
					if(day==31){
						return false;
					}
				}
				ca.set(year, month-1, day);
			}catch(Exception e){
				return false;
			}
			
			//验证时间是否正确
			String time = bwbh.substring(22,31);
			try{
				int hour = Integer.parseInt(time.substring(0,2));
				if(hour<0 && hour>23){
					return false;
				}
				int minute = Integer.parseInt(time.substring(2,4));
				if(minute<0 && minute>59){
					return false;
				}
				int second = Integer.parseInt(time.substring(4,6));
				if(second<0 && second>59){
					return false;
				}
				int millisecond = Integer.parseInt(time.substring(6,9));
				if(millisecond<0 && millisecond>999){
					return false;
				}
				
				ca.set(ca.HOUR_OF_DAY, hour);
				ca.set(ca.MINUTE, minute);
				ca.set(ca.SECOND, second);
				ca.set(ca.MILLISECOND, millisecond);
			}catch(Exception e){
				return false;
			}
			
			//验证时间是否在有效间隔内
			Calendar now = Calendar.getInstance();
			if(Math.abs((now.getTimeInMillis()-ca.getTimeInMillis())/1000/60/60)>2){
				return false;
			}
			
			//验证流水号是否正确
			String streamId = bwbh.substring(31,35);
			try{
				Integer.parseInt(streamId);
			}catch(Exception e){
				return false;
			}
			
			return true;
		}
		return false;
	}
	
	//验证服务标识
	private boolean checkFwbs(String fwbs){
		//验证服务标识不为空且长度为21位
		if(fwbs!=null && fwbs.length()==21){
			//验证是否以S开头
			if(!fwbs.substring(0, 1).equals("S")){
				return false;
			}
			
			//验证行业分类代码是否正确
			String hyfldm = fwbs.substring(1, 4);
			
			//验证机构代码是否正确
			String jgdm = fwbs.substring(4,16);
			Jgdmb jgdmb = jgdmbDao.selectByPrimaryKey(jgdm);
			if(jgdmb==null){
				return false;
			}
			
			//验证流水号是否正确
			String streamId = fwbs.substring(16,21);
			try{
				Integer.parseInt(streamId);
			}catch(Exception e){
				return false;
			}
			return true;
		}
		return false;
	}
	
	//验证服务参与方标识
	private boolean checkFwcyfbs(String fwcyfbs){
		//验证服务参与方标识不为空且长度为21位
		if(fwcyfbs!=null && fwcyfbs.length()==21){
			//验证是否以A开头
			if(!fwcyfbs.startsWith("A")){
				return false;
			}
			
			//验证行业分类代码是否正确
			String hyfldm = fwcyfbs.substring(1, 4);
			
			//验证机构代码是否正确
			String jgdm = fwcyfbs.substring(4,16);
			Jgdmb jgdmb = jgdmbDao.selectByPrimaryKey(jgdm);
			if(jgdmb==null){
				return false;
			}
			
			//验证流水号是否正确
			String streamId = fwcyfbs.substring(16,21);
			try{
				Integer.parseInt(streamId);
			}catch(Exception e){
				return false;
			}
			return true;
		}
		return false;
	}
	
	//验证报文来源IP
	private boolean checkBwlyip(String bwlyip){
		if(bwlyip!=null && !bwlyip.equals("")){
			String[] ips = bwlyip.split("\\.");
			//验证IP是否为4段或6段，验证每段是否正确
			if(ips.length==4){
				for(String ip:ips){
					try{
						int i = Integer.parseInt(ip);
						if(i<0 || i>255){
							return false;
						}
					}catch(Exception e){
						return false;
					}
				}
			}else if(ips.length==6){
				for(String ip:ips){
					try{
						int i = Integer.parseInt(ip);
						if(i<0 || i>255){
							return false;
						}
					}catch(Exception e){
						return false;
					}
				}
			}else{
				return false;
			}
			return true;
		}
		return false;
	}
	
	//验证报文来源端口号
	private boolean checkBwlydkh(String bwlydkh){
		//验证端口号是否为空
		if(bwlydkh!=null && !bwlydkh.equals("")){
			try{
				//验证端口是否为数字
				Integer.parseInt(bwlydkh);
			}catch(Exception e){
				return false;
			}
			return true;
		}
		return false;
	}
	
	//验证方法标识
	private boolean checkFfbs(String ffbs){
		if(ffbs!=null && !ffbs.equals("")){
			
			return true;
		}
		return false;
	}
	
	
	//验证请求时间
	private boolean checkQqsj(String qqsj){
		if(qqsj!=null && qqsj.length()==14){
			try{
				int year = Integer.parseInt(qqsj.substring(0,4));
				int month = Integer.parseInt(qqsj.substring(4,6));
				int day = Integer.parseInt(qqsj.substring(6,8));
				
				if(day>31 || day<1){
					return false;
				}
				
				if(month==2){
					if(day==30 || day==31){
						return false;
					}
					if(year%4!=0 && day==29){
						return false;
					}
					if(year%100==0 && year%400!=0 && day==29){
						return false;
					}
				}
				if(month==4 || month==6 || month==9 || month==11){
					if(day==31){
						return false;
					}
				}
			}catch(Exception e){
				return false;
			}
			
			return true;
		}
		return false;
	}
	
	//验证服务请求内容
	private boolean checkFwqqnr(String fwqqnr){
		if(fwqqnr!=null && !fwqqnr.equals("")){
			
			return true;
		}
		return false;
	}
	
	//验证操作人员姓名
	private boolean checkCzryxm(String czryxm){
		if(czryxm!=null && !czryxm.equals("")){
			return true;
		}
		return false;
	}
	
	//验证操作人员公民身份号码
	private boolean checkCzrygmsfhm(String czrygmsfhm){
		if(czrygmsfhm!=null && !czrygmsfhm.equals("")){
			
			return true;
		}
		return false;
	}
	
	//验证操作人员公安机关机构代码
	private boolean checkCzrygajgjgdm(String czrygajgjgdm){
		if(czrygajgjgdm!=null && !czrygajgjgdm.equals("")){
			//验证公安机关机构代码是否存在
			Jgdmb jgdmb = jgdmbDao.selectByPrimaryKey(czrygajgjgdm);
			if(jgdmb==null){
				return false;
			}
			return true;
		}
		return false;
	}
	
	//验证服务请求设备编号
	private boolean checkFwqqsbbh(String fwqqsbbh){
		if(fwqqsbbh!=null && !fwqqsbbh.equals("")){
			
			return true;
		}
		return false;
	}
	
	//验证审计事件类型
	private boolean checkSjsjlx(String sjsjlx){
		if(sjsjlx!=null && !sjsjlx.equals("")){
			
			return true;
		}
		return false;
	}
	
}
