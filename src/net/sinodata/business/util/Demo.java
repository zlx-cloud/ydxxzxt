package net.sinodata.business.util;
import java.util.Properties;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


public class Demo {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("Simple Application");
		conf.setMaster("local[2]");
		SparkSession spark = SparkSession.builder().config(conf).getOrCreate();

		// 读取mysql数据
//		readMySQLToParquet(spark);
		readParquet(spark);
//		readParquet2(spark);
//		停止SparkContext
		spark.close();
//		Fwzyqqbwcjb log=new Fwzyqqbwcjb();
//		log.setFfbs("FUN001");
//		Encoder<Fwzyqqbwcjb> fwzyqqbwcjbEncoder = Encoders.bean(Fwzyqqbwcjb.class);
//		Dataset<Fwzyqqbwcjb> javaBeanDS = spark.createDataset(
//		  Collections.singletonList(log),
//		  fwzyqqbwcjbEncoder
//		);
//		javaBeanDS.show();
//		javaBeanDS.write().parquet("F:/a");
	}

	private static void readMySQLToParquet(SparkSession spark) {
		// jdbc.url=jdbc:mysql://localhost:3306/database
//		String url = "jdbc:mysql://localhost:3306/notary?serverTimezone=GMT%2B8";
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		// 查找的表名
		String table = "FWZYTCB";
		// 增加数据库的用户名(user)密码(password),指定test数据库的驱动(driver)
		Properties connectionProperties = new Properties();
		connectionProperties.put("user", "ydxxzxt");
		connectionProperties.put("password", "ydxxzxt");
//		connectionProperties.put("driver", "com.mysql.jdbc.Driver");
		connectionProperties.put("driver", "oracle.jdbc.driver.OracleDriver");

		// 读取表中所有数据
		Dataset<Row> jdbcDF = spark.read().jdbc(url, table, connectionProperties);
		jdbcDF.show();
		jdbcDF.write().parquet("F:/a");
	}
	
	private static void readParquet(SparkSession spark) {
		Dataset<Row> sqlDF =spark.read().parquet("file:///u02/parquet/response/year=2019/month=08/day=07");
		sqlDF.show();
	}
	
	private static void readParquet2(SparkSession spark) {
		Dataset<Row> sqlDF =spark.sql("SELECT * FROM parquet.`log.parquet`");
		sqlDF.show();
	}
}
