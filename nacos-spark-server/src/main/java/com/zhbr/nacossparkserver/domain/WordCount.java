package com.zhbr.nacossparkserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * spark 配置类
 * @Author Lion Li
 * @Date 2020/4/8
 */
@Data
@ToString
@AllArgsConstructor
public class WordCount implements Serializable {

    private String word;
    private Integer count;

}
