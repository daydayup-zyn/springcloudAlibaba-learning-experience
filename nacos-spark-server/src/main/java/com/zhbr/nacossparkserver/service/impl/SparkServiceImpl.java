package com.zhbr.nacossparkserver.service.impl;

import com.zhbr.nacossparkserver.domain.WordCount;
import com.zhbr.nacossparkserver.service.SparkService;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Service
public class SparkServiceImpl implements SparkService, Serializable {

    @Autowired
    private SparkSession sparkSession;

    @Override
    public List<WordCount> doWordCount(String filePath) {
        JavaSparkContext sparkContext = new JavaSparkContext(sparkSession.sparkContext());

        // 获取本地文件 生成javaRDD
        JavaRDD<String> file = sparkContext.textFile(filePath);
        // 按空格分解为数组 生成新的javaRDD
        JavaRDD<String> words = file.flatMap(
                line -> Arrays.asList(line.split(" ")).iterator()
        );
        // 统计每个词出现的次数 生成新的javaRDD
        JavaRDD<WordCount> wordcount = words.map(
                word -> new WordCount(word, 1)
        );
        // 将词与数转换为 key-value形式
        JavaPairRDD<String, Integer> pair = wordcount.mapToPair(
                wordCount -> new Tuple2<>(wordCount.getWord(), wordCount.getCount())
        );
        // 根据key进行整合
        JavaPairRDD<String, Integer> wordcounts = pair.reduceByKey(
                (count1, count2) -> count1 + count2
        );
        // 将结果转换为 WordCount对象
        JavaRDD<WordCount> map = wordcounts.map(
                (tuple2) -> new WordCount(tuple2._1, tuple2._2)
        );
        // 将结果转换为 list并返回
        List<WordCount> result = map.collect();

        return result;
    }
}
