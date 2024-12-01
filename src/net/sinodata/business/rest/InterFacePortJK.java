package net.sinodata.business.rest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import net.sinodata.business.dao.quartzJob.SSJKDao;

import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class InterFacePortJK {
	@Autowired
	SSJKDao SSJKDao;
	

	public void execute() {
		TelnetClientJt();
	}

	public void TelnetClientJt() {
		String EXE_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());
		ExecutorService executor = Executors.newCachedThreadPool();  
		List<Future<Boolean>> tasks = new ArrayList<Future<Boolean>>();
		List<Map<String, String>> list=SSJKDao.queryIpPort();
		
		//List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
		/*Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "10.6.121.69:8090:cognos管理系统");
		map.put(2, "10.6.121.91:1521:91数据管理系统");
		map.put(3, "10.6.121.88:5555:trs数据管理系统");
		//map.put(4, "192.168.99.201:1521");
		list.add(map);*/
		/*if (executor.isShutdown()) {
			executor = Executors.newFixedThreadPool(200);
		}*/
		for(int i=0;i<list.size();i++){
			Map<String, String> map=list.get(i);
		/*}
		for (Map<String, String> map : list) {*/

			  //for (Entry<Integer, String> entry : map1.entrySet()) {
              //String[] arrayUrl = entry.getValue().split(":");
				String url=map.get("IP");
				Integer port = Integer.valueOf(map.get("PORT"));
				String name=map.get("NAME");
				ThreadTaskTelnet tt = new ThreadTaskTelnet(url, port);
				FutureTask<Boolean> task = new FutureTask<Boolean>(tt);
				tasks.add(task);

				(executor).submit(task);
				Date date1 = new Date();
				String FINISH_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS")
						.format(date1);
				
				try {
					insert(SSJKDao,name, url, port + "", "", EXE_TIME, FINISH_TIME,
							tasks.get(i).get() ? "0" : "1");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//}
		}

		executor.shutdown();
		/*
		 * for (Future<Boolean> taskStatus : tasks) { try { Boolean status =
		 * taskStatus.get();
		 * 
		 * 
		 * } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */

	}

	public void insert(SSJKDao SSJKDao,String name, String IP, String PORT,
			String INTEPORTJKLOG, String EXE_TIME, String FINISH_TIME,
			String STATUS) throws Exception {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("INTERNAME", name);
		maps.put("IP", IP);
		maps.put("PORT", PORT);
		maps.put("INTEPORTJKLOG", new javax.sql.rowset.serial.SerialClob(
				INTEPORTJKLOG.toCharArray()));
		maps.put("EXE_TIME", EXE_TIME);
		maps.put("FINISH_TIME", FINISH_TIME);
		maps.put("STATUS", STATUS);
		SSJKDao.addInPortSJ(maps);
	}
}

class ThreadTaskTelnet implements Callable<Boolean> {

	private String url;
	private Integer port;

	public ThreadTaskTelnet(String url, Integer port) {
		this.url = url;
		this.port = port;
	}

	@Override
	public Boolean call() throws Exception {
		boolean status = false;

		TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler(
				"VT100", false, false, true, false);
		EchoOptionHandler echoopt = new EchoOptionHandler(true, false, true,
				false);
		SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler(true, true,
				true, true);

		TelnetClient tc = new TelnetClient();
		try {
			tc.addOptionHandler(ttopt);
			tc.addOptionHandler(echoopt);
			tc.addOptionHandler(gaopt);

		} catch (Exception e) {
			System.err.println("Error registering option handlers: "
					+ e.getMessage());
		}

		try {

			tc.connect(url, port);
			
			status = true;

		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}

		try {
			tc.disconnect();
		} catch (IOException e) {
			System.err.println("Exception while connecting:" + e.getMessage());
		}

		return status;
	}

}
