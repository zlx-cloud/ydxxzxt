package net.sinodata.business.util;

import java.io.File;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkUtil {

	static SparkSession spark=null;
	
	public static SparkConf getSparkConf() {
		SparkConf conf = new SparkConf().setAppName("Simple Application");
		conf.setMaster("local[*]");
		conf.set("spark.executor.memory", "2g");     
		return conf;
	}

	public static SparkSession getSparkSession(SparkConf conf) {
		SparkSession spark = SparkSession.builder().config(conf).getOrCreate();
		return spark;
	}
	
	public static SparkSession getSparkSession(){
		SparkConf conf = SparkUtil.getSparkConf();
		if(spark==null)
			spark = SparkUtil.getSparkSession(conf); 
		return spark;
	}
	
	public static void read(String path) {
		SparkSession spark = SparkSession.builder().config(getSparkConf()).getOrCreate();
		Dataset<Row> df=spark.read().parquet("parquet"+File.separator+path);
		df.show();
	}
	
	public static void main(String[] args) {
		read(args[0]);
	}
}
