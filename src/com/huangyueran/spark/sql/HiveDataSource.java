package com.huangyueran.spark.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.derby.tools.sysinfo;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.hive.HiveContext;

/**
 * @category 读取Hive数据源
 * @author huangyueran
 * @time 2017-7-24 13:58:59
 */
public class HiveDataSource {
	public static void main(String[] args) {
		
		/*
		 * 0.把hive里面的hive-site.xml放到spark/conf目录下
		 * 1.启动Mysql
		 * 2.启动HDFS
		 * 3.启动Hive ./hive
		 * 4.初始化HiveContext
		 * 5.打包运行
		 * 
		 * ./bin/spark-submit --master yarn-cluster --class com.huangyueran.spark.sql.HiveDataSource /root/spark_hive_datasource.jar
		 * ./bin/spark-submit --master yarn-client --class com.huangyueran.spark.sql.HiveDataSource /root/spark_hive_datasource.jar 
		 */
		
		SparkConf conf = new SparkConf().setAppName("HiveDataSource");
		JavaSparkContext sc = new JavaSparkContext(conf);

        // 创建HiveContext，注意，这里，它接收的是SparkContext作为参数，不是JavaSparkContext，其实也可以使用JavaSparkContext，只不过内部也是做了sc.sc()的操作
        HiveContext hiveContext = new HiveContext(sc.sc());
        DataFrame person = hiveContext.sql("select * from psn1");
        person.show();
        
        List<Row> list = person.javaRDD().collect();
        System.out.println("=============================================================");
        for(Row r:list){
        	System.out.println(r);
        }
        System.out.println("=============================================================");
        
		sc.close();
	}

}
