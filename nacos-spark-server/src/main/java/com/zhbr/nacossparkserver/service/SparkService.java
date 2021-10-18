package com.zhbr.nacossparkserver.service;

import com.zhbr.nacossparkserver.domain.WordCount;

import java.util.List;

public interface SparkService {
    List<WordCount> doWordCount(String filePath);
}
