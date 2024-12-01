package net.sinodata.business.entity;

import com.jcraft.jsch.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JschCommand {
    private Session session = null;
    private Channel channel = null;

//    private String sftpHost = "20.1.11.55";
//    private int sftpPort = 22;
//    private String sftpUserName = "weblogic";
//    private String sftpPassword = "weblogic";
    private int timeout = 30000;

    /**
     * 获取连接
     * @return
     */
    private ChannelExec getChannelExec(String Host,String UserName,String Password,int Port) {
        try {
            if (channel != null && channel.isConnected()) {
                return (ChannelExec) channel;
            }
            JSch jSch = new JSch();
            if (session == null || !session.isConnected()) {
                session = jSch.getSession(UserName, Host, Port);
                session.setPassword(Password);
                Properties config = new Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);
                session.setTimeout(timeout);
                session.connect();
            }
            channel = session.openChannel("exec");
        } catch (Exception e) {
            if (session != null) {
                session.disconnect();
                session = null;
            }
            channel = null;
        }
        return channel == null ? null : (ChannelExec) channel;
    }

    /**
     * 关闭连接
     */
    private void closeChannel() {
        try {
            if (channel != null) {
                channel.disconnect();
                channel = null;
            }
            if (session != null) {
                session.disconnect();
                session = null;
            }
        } catch (Exception e) {
            //System.out.println(e);
        }
    }

    /**
     * 执行服务器端命令
     */
    public  List<Map<String,String>> executeCommand(String Host,String UserName,String Password,int Port) {
//        boolean flag = false;
        String line = null;
//        String command = "cat /proc/cpuinfo;cat /proc/meminfo;";
        String command = "cat /proc/cpuinfo| grep 'processor'| wc -l; top -bn 1 -i -c; sar -d;";
       
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        
        Map<String,String> cpuMap = new HashMap<String,String>();
        Map<String,String> memMap = new HashMap<String,String>();
        Map<String,String> diskMap = new HashMap<String,String>();
        Map<String,String> netMap = new HashMap<String,String>();
        
//        if (channelExec == null) {
//            return false;
//        }
        try {
        	String[] arr=command.split(";");
        	for (int i = 0; i < arr.length; i++) {
        		//Cpu(s): 4.5%us, 3.8%sy, 0.0%ni, 91.0%id, 0.6%wa, 0.0%hi, 0.0%si, 0.0%st
            	ChannelExec channelExec = getChannelExec( Host, UserName, Password,Port);
                channelExec.setInputStream(null);
                channelExec.setErrStream(System.err);
                channelExec.setCommand(arr[i]);
                InputStream in = channelExec.getInputStream();
                channelExec.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                if(i==0) {
            		cpuMap.put("sysCpuCores", reader.readLine());
            		
            	}else if(i==arr.length-1) {
//            		cpuMap.put("sysCpuCores", reader.readLine());
            		 while ((line = reader.readLine()) != null) {
            			 String[] linearr=line.split(":");
            			 if(linearr[0].equals("平均时间")||linearr[0].equals("Average")){
            				 String netinfo=linearr[1].substring(linearr[1].length()-4, linearr[1].length());
            				 netMap.put("netUse", netinfo);
            			 }
            		 }
            		
            		
            	}else {
            		 while ((line = reader.readLine()) != null) {
                     	
                 		String[] linearr=line.split(":");
                     	if(linearr[0].equals("Cpu(s)")||linearr[0].equals("%Cpu(s)")){
                     		String[] cpuUse=linearr[1].split(",");
                     		for (int j = 0; j < cpuUse.length; j++) {
                     			if(cpuUse[j].contains("sy")){
                     				cpuMap.put("sysCpuUse", cpuUse[j].trim());
                         		}
     						}
                     	}
                     	
                     	if(linearr[0].equals("Mem")||linearr[0].equals("KiB Mem ")){
                     		String[] memUse=linearr[1].split(",");
                     		for (int j = 0; j < memUse.length; j++) {
                     			if(memUse[j].contains("total")){
                         			memMap.put("memTotal", memUse[j].trim());
                         		}else if(memUse[j].contains("used")){
                         			memMap.put("memUsed", memUse[j].trim());
                         		}else if(memUse[j].contains("free")){
                         			memMap.put("memFree", memUse[j].trim());
                         		}
     						}
                     		
                     	}
                     	
                     	if(linearr[0].equals("Swap")||linearr[0].equals("KiB Swap")){
                     		String[] swapUse=linearr[1].split(",");
                     		for (int j = 0; j < swapUse.length; j++) {
                     			if(swapUse[j].contains("total")){
                     				diskMap.put("swapTotal", swapUse[j].trim());
                         		}else if(swapUse[j].contains("used")){
                         			diskMap.put("swapUsed", swapUse[j].trim());
                         		}else if(swapUse[j].contains("free")){
                         			diskMap.put("swapFree", swapUse[j].trim());
                         		}
     						}
                     		
                     	}
                     }
            		  
            	}
            
                reader.close();
                closeChannel();
//                flag = true;
			}
        	list.add(cpuMap);
          	list.add(memMap);
          	list.add(diskMap);
          	list.add(netMap);
        	
        } catch (Exception e) {
//            flag = false;
            e.printStackTrace();
        }
        return list;
    }

}