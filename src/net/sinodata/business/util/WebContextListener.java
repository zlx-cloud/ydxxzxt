package net.sinodata.business.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.spark.sql.SparkSession;

public class WebContextListener implements ServletContextListener {
	SparkSession spark;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub 这里可以放你要执行的代码或方法    
		 spark =SparkUtil.getSparkSession();
	}

}
