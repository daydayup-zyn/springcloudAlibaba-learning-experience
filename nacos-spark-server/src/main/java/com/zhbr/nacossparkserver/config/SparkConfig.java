package com.zhbr.nacossparkserver.config;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * spark 配置类
 * @Author Lion Li
 * @Date 2020/4/8
 */
@Configuration
public class SparkConfig {

    @Bean
    public SparkConf sparkConf() {
        return new SparkConf()
                // 设置模式为本地模式 [*] 为使用本机核数
                .setMaster("local[*]")
                // 设置应用名
                .setAppName("springboot-spark-demo");
    }

    @Bean
    public SparkSession sparkSession() {
        return SparkSession.builder().config(sparkConf()).getOrCreate();
    }
}
