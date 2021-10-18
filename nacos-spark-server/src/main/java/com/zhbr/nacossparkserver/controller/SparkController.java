package com.zhbr.nacossparkserver.controller;

import com.zhbr.nacossparkserver.domain.WordCount;
import com.zhbr.nacossparkserver.service.SparkService;
import com.zhbr.nacossparkserver.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SparkController {

    @Autowired
    private SparkService sparkService;

    //参数存在非法字符,get方法会报错
    @PostMapping("/wordCount")
    public APIResponse wordCount(String filePath) {
        List<WordCount> list = sparkService.doWordCount(filePath);
        return APIResponse.success(list);
    }
}
