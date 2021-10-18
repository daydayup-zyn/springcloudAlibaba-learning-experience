package com.zhbr.nacosdiscoveryclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
public class DiscoveryClientController {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @GetMapping("/test")
    public String test() {
        // 通过spring cloud common中的负载均衡接口选取服务提供节点实现接口调用
        ServiceInstance serviceInstance = loadBalancerClient.choose("alibaba-nacos-discovery-server");
        String url = serviceInstance.getUri() + "/hello?name={name}";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class,"hello");
        return result;
    }

    @GetMapping("/spark")
    public String spark() {
        // 通过spring cloud common中的负载均衡接口选取服务提供节点实现接口调用
        ServiceInstance serviceInstance = loadBalancerClient.choose("alibaba-nacos-spark-server");
        String url = serviceInstance.getUri() + "/wordCount?filePath={filePath}";
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("filePath","D:\\IdeaProjects\\springboot-learning-experience\\spring-boot-spark\\wordcount.txt");
        //String result = restTemplate.getForObject(url,String.class,stringStringHashMap);
        String result = restTemplate.postForObject(url,null,String.class,stringStringHashMap);
        return result;
    }
}
