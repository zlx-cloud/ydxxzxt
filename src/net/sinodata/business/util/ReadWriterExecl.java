package net.sinodata.business.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadWriterExecl {

      
	public static void readRomateXlsFile(String romatefilename) throws IOException{
		URL urlfile;
		BufferedReader in;
		urlfile = new URL(romatefilename);
		InputStream ism=urlfile.openStream(); 
		String sname = romatefilename.substring(romatefilename.lastIndexOf("."));
		if(sname.equals("xlsx")){
			  XSSFWorkbook xssfWorkbook = new XSSFWorkbook(ism);
		        // 获取每一个工作薄
		        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
		            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
		            if (xssfSheet == null) {
		                continue;
		            }
		            // 获取当前工作薄的每一行
		            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
		                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
		                if (xssfRow != null) {
		                    XSSFCell one = xssfRow.getCell(0);
		                    //读取第一列数据
		                    XSSFCell two = xssfRow.getCell(1);
		                    //读取第二列数据
		                    XSSFCell three = xssfRow.getCell(2);
		                    //读取第三列数据

		                }
		            }
		        }
		}if(sname.equals("xls")){
			
		    HSSFWorkbook hssfWorkbook = new HSSFWorkbook(ism);
		    // 获取每一个工作薄
		    for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
		        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
		        if (hssfSheet == null) {
		            continue;
		        }
		        // 获取当前工作薄的每一行
		        for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
		            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
		            if (hssfRow != null) {
		                HSSFCell one = hssfRow.getCell(0);
		                //读取第一列数据
		                HSSFCell two = hssfRow.getCell(1);
		                //读取第二列数据
		                HSSFCell three = hssfRow.getCell(2);
		                //读取第三列数据
		            }
		        }
		    }
		}
	}
	
	
	
	
     public static void main(String[] args) throws IOException{  
    	 ReadWriterExecl re = new ReadWriterExecl();  
        /* File file = new File("D:/zkjc/gaj/a.xls");  */
         //ReadWriterExecl we = new ReadWriterExecl("D:/zkjc/gaj/organization.xls");  
         //List<String[][]> result = new ArrayList<String[][]>();  
       String str=  HttpRequest.sendGet("http://20.1.11.91/directory/getOrganizationInfo", "appId=CE1030467C9828278E43D5312CBE39CC");
       JSONObject jSONObject= JSONObject.fromObject(str);
      
       /*result =  re.getData((String)jSONObject.get("fileUrl"), 0);  
       //不忽略行 从0开始  
        // result =  re.getData(file, 0);  
         //有多少行  
         int row = result.get(0).length;  
   
          //写入  传入参数row   不传column  column是不确定的  
         we.writeEx(row,result.get(0));  */
         
         
         ReadWriterExecl.readRomateXlsFile((String)jSONObject.get("fileUrl"));
          
     }  
}
