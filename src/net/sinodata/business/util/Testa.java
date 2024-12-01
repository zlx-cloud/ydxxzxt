package net.sinodata.business.util;

 
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.filter2.compat.FilterCompat;
import org.apache.parquet.filter2.predicate.FilterPredicate;
import org.apache.parquet.filter2.predicate.Operators.BinaryColumn;
import org.apache.parquet.hadoop.ParquetInputFormat;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetReader.Builder;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import static org.apache.parquet.filter2.predicate.FilterApi.eq;
import static org.apache.parquet.filter2.predicate.FilterApi.binaryColumn;
import static org.apache.parquet.filter2.predicate.FilterApi.longColumn;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.parquet.io.api.Binary;
public class Testa {
//	public static void main(String[] args) throws IllegalArgumentException, IOException {
//		  @SuppressWarnings({ "deprecation", "resource" })
//		AvroParquetReader<GenericRecord> reader = new AvroParquetReader<GenericRecord>(new Path("F:\\项目文件\\北京市局\\parquet\\parquet\\request\\year=2019\\month=08\\day=08"));
//
//	      GenericRecord record ;
//
//	      while ((record = reader.read())!= null){
//	          System.out.println(record);
//	      }
//	}
//	static Logger logger=Logger.getLogger(ReadParquet.class);
	  public static void main(String[] args) throws Exception {
		  
		  	Calendar c = Calendar.getInstance(); 
			Date date=null; 
			date = new SimpleDateFormat("yyyyMMdd").parse("20200301"); 
			c.setTime(date); 
			int day=c.get(Calendar.DATE); 
			c.set(Calendar.DATE,day-13); 

			String dayBefore=new SimpleDateFormat("yyyyMMdd").format(c.getTime()); 
			//System.out.println(dayBefore);
		  
//	        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
//			Date date = new Date(System.currentTimeMillis());
//			String today=formatter.format(date);
//			
//			Date strdate = new Date();
//			Calendar calendar= Calendar.getInstance();
//			calendar.setTime(date);
//			calendar.add(Calendar.DAY_OF_MONTH, +1);
//			strdate =calendar.getTime();
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			String tomorrow = format.format(strdate).toString();
//			
//			String partitionName="";
//			String maxPartition="";
//			String apiSql="";
//			String todaymonth=today.substring(5,7);
//			String tomorrowmonth=tomorrow.substring(5,7);
//			
//			System.out.println(today);
//			System.out.println(tomorrow);
//			System.out.println(todaymonth);
//			System.out.println(tomorrowmonth);
			
//		  String today="20200701";
//		  	if(today.substring(4,6).equals("01")||today.substring(4,6).equals("02")||today.substring(4,6).equals("03")) {
//				System.out.println("Q1");
//			}else if(today.substring(4,6).equals("04")||today.substring(4,6).equals("05")||today.substring(4,6).equals("06")) {
//				System.out.println("Q2");
//			}else if(today.substring(4,6).equals("07")||today.substring(4,6).equals("08")||today.substring(4,6).equals("09")) {
//				System.out.println("Q3");
//			}else if(today.substring(4,6).equals("10")||today.substring(4,6).equals("11")||today.substring(4,6).equals("12")) {
//				System.out.println("Q4");
//			}
		  
//		  	SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMdd");
//			Date date = new Date(System.currentTimeMillis());
//			String today=formatter.format(date);
//			
//			Date strdate = new Date();
//			Calendar calendar= Calendar.getInstance();
//			calendar.setTime(date);
//			calendar.add(Calendar.DAY_OF_MONTH, +1);
//			strdate =calendar.getTime();
//			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//			String tomorrow = format.format(strdate).toString();
//			System.out.println(tomorrow);
		  
//			DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
//			Date myDate1 = dateFormat1.parse("20200321");
//			Calendar calendar= Calendar.getInstance();
//			calendar.setTime(myDate1);
//			Date strdate = new Date();
//			calendar.setTime(strdate);
//			calendar.add(Calendar.DAY_OF_MONTH, -14);
//			strdate =calendar.getTime();
//			String yesdate = dateFormat1.format(strdate).toString();
//			System.out.println(yesdate);
		  
//		  SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMdd");
//		  Date date = new Date(System.currentTimeMillis());
//		  System.out.println(formatter.format(date));
			  
			  
//		  String a="20200326";
//		  String partitionName="CRJRY_"+a.substring(0,6);
//		  System.out.println(partitionName);
//		  String a="2020-02-01 13:09:09";
//		  String FLTDEPTTM=a.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
//		  System.out.println(FLTDEPTTM);
//		  Date date = new Date();
//			Date strdate = new Date();
//			Calendar calendar= Calendar.getInstance();
//			calendar.setTime(date);
//			calendar.add(Calendar.DAY_OF_MONTH, -15);
//			strdate =calendar.getTime();
//			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//			String patitionDay = format.format(strdate).toString().substring(6,8);
//			System.out.println(patitionDay);
			
		  
//			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
//			Date myDate1 = dateFormat1.parse("2020-03-05");
//			Calendar calendar= Calendar.getInstance();
//			calendar.setTime(myDate1);
//			calendar.add(Calendar.DAY_OF_MONTH, -15);
//			Date strdate = new Date();
//			strdate =calendar.getTime();
//			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//			String yesdate = format.format(strdate).toString();
//			System.out.println(yesdate);
// 
			
			
			
//		  Date date = new Date();
//		  int b=date.getDay();
//		  Date fdate ;
//		  List <Date> list = new ArrayList();
//		  Long fTime=date.getTime()-b*24*3600000;
//		  for(int a=0;a<14;a++){
//			  fdate= new Date();
//			  fdate.setTime(fTime-(a*24*3600000));
//			  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//			  String fmDate=simpleDateFormat.format(fdate);
//			  System.out.println(fmDate);
////			  System.out.println(fTime);
////			  list.add(a, fdate);
//		  }
		  
		  
		  
		  
		  
		  
		  
//		  Date date = new Date();
//			Date strdate = new Date();
//			Calendar calendar= Calendar.getInstance();
//			calendar.setTime(date);
//			calendar.add(Calendar.DAY_OF_MONTH, -1);
//			strdate =calendar.getTime();
//			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//			String yesdate = format.format(strdate).toString();
			
//		  String a="2020-03-23 23:11:22";
//		  System.out.println(a.substring(8,10));
		  
//		  String a="";
//		  String arr[]=a.split(",");
//		  System.out.println(arr.length);
//		  
//		  String brr[]= {};
//		  System.out.println(brr.length);
//		  long begin = System.currentTimeMillis(); 
//	    parquetWriter("test\\parquet-out2","input.txt");
//	    parquetReaderV2("F:\\项目文件\\北京市局\\parquet\\parquet\\request\\year=2019\\month=08\\day=12");
//		long end = System.currentTimeMillis();  
//		long scsc = end-begin;
//		System.out.println("scsc==============="+scsc);
	  }
	   
//	  public static List<Date> dateToWeek(Date mdate){
//		  int b=mdate.getDay();
//		  Date fdate ;
//		  List <Date> list = new ArrayList();
//		  Long fTime=mdate.getTime()-b*24*3600000;
//		  for(int a=0;a<8;a++){
//		  fdate= new Date();
//		  fdate.setTime(fTime+(a*24*3600000));
//		  list.add(a, fdate);
//		  }
//		  return list;
//		  }
	  
	  static void parquetReaderV2(String inPath) throws Exception{
	 
			Configuration qqconfiguration = new Configuration();
		    GroupReadSupport qqreadSupport = new GroupReadSupport();
			Group qqline=null;
			
////			ParquetInputFormat.setFilterPredicate(qqconfiguration, eq(binaryColumn("qqbwbs"),Binary.fromString("")));
////	        FilterCompat.Filter qqfilter = ParquetInputFormat.getFilter(qqconfiguration);
//		    Builder<Group> qqreader= ParquetReader.builder(qqreadSupport, new Path("F:\\项目文件\\北京市局\\parquet\\parquet\\request\\year=2019\\month=08\\day=08"));
////		    		withConf(qqconfiguration).withFilter(qqfilter);
//		    ParquetReader<Group> qqbuild=qqreader.build();
//		    
//		    while((qqline=qqbuild.read())!=null){	
//		    	String fwqqNr= qqline.getString("qqbwbs", 0);
//		    	System.out.println("fwqqNr========"+fwqqNr);
//		    	break;
//		    }
		    
		    Configuration xyconfiguration = new Configuration();
		    GroupReadSupport xyreadSupport = new GroupReadSupport();
			Group xyline=null;
		    ParquetInputFormat.setFilterPredicate(xyconfiguration, eq(binaryColumn("xybwbs"),Binary.fromString("")));
	        FilterCompat.Filter xyfilter = ParquetInputFormat.getFilter(xyconfiguration);
		    Builder<Group> xyreader= ParquetReader.builder(xyreadSupport, new Path("F:\\项目文件\\北京市局\\parquet\\parquet\\response\\year=2019\\month=08\\day=13"));
//		    		withConf(xyconfiguration).withFilter(xyfilter);
		    ParquetReader<Group> xybuild=xyreader.build();
		    
		    while((xyline=xybuild.read())!=null){	
		    	String fwtgNr= xyline.getString("xybwbs", 0);
		    	//System.out.println("fwtgNr========"+fwtgNr);
		    	break;
		    }
	  } 
	  //新版本中new ParquetReader()所有构造方法好像都弃用了,用上面的builder去构造对象
	  static void parquetReader(String inPath) throws Exception{
	    GroupReadSupport readSupport = new GroupReadSupport();
	    ParquetReader<Group> reader = new ParquetReader<Group>(new Path(inPath),readSupport);
	    Group line=null;
	    while((line=reader.read())!=null){
	     //System.out.println(line.toString());
	    }
	    //System.out.println("读取结束");
	     
	  }
	  /**
	   * 
	   * @param outPath　　输出Parquet格式
	   * @param inPath 输入普通文本文件
	   * @throws IOException
	   */
//	  static void parquetWriter(String outPath,String inPath) throws IOException{
//	    MessageType schema = MessageTypeParser.parseMessageType("message Pair {\n" +
//	        " required binary city (UTF8);\n" +
//	        " required binary ip (UTF8);\n" +
//	        " repeated group time {\n"+
//	       	" required int32 ttl;\n"+
//	        " required binary ttl2;\n"+
//	        "}\n"+
//	       "}");
//	    GroupFactory factory = new SimpleGroupFactory(schema);
//	    Path path = new Path(outPath);
//	    Configuration configuration = new Configuration();
//	    GroupWriteSupport writeSupport = new GroupWriteSupport();
//	    writeSupport.setSchema(schema,configuration);
//	    ParquetWriter<Group> writer = new ParquetWriter<Group>(path,configuration,writeSupport);
//	//把本地文件读取进去，用来生成parquet格式文件
//	    BufferedReader br =new BufferedReader(new FileReader(new File(inPath)));
//	    String line="";
//	    Random r=new Random();
//	    while((line=br.readLine())!=null){
//	      String[] strs=line.split("\\s+");
//	      if(strs.length==2) {
//	        Group group = factory.newGroup()
//	            .append("city",strs[0])
//	            .append("ip",strs[1]);
//	        Group tmpG =group.addGroup("time");
//	        tmpG.append("ttl", r.nextInt(9)+1);
//	        tmpG.append("ttl2", r.nextInt(9)+"_a");
//	        writer.write(group);
//	      }
//	    }
//	    System.out.println("write end");
//	    writer.close();
//	  }
	  
	
}
