package com.huangyueran.spark.sharedvariables;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.broadcast.Broadcast;

/**
 * @category 全局累加器
 * @author huangyueran
 * @time 2017-7-24 10:00:05
 */
public class AccumulatorValue {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("AccumulatorValue").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);

		// 创建累加器
		final Accumulator<Integer> accumulator = sc.accumulator(0, "My Accumulator");

		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		JavaRDD<Integer> listRDD = sc.parallelize(list);

		listRDD.foreach(new VoidFunction<Integer>() {
			@Override
			public void call(Integer n) throws Exception {
				accumulator.add(n);
				// 不能读取，会报异常 cannot read, you will report an exception
				// System.out.println(accumulator.value());
			}
		});

		// 只能在Driver读取
		System.out.println(accumulator.value());
		
		try {
			Thread.sleep(5000*5000*5000);
			// http://192.168.68.1:4040
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sc.close();
	}

}
