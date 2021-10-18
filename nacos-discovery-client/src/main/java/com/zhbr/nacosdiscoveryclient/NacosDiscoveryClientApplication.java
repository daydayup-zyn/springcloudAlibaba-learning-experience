package com.zhbr.nacosdiscoveryclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class NacosDiscoveryClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosDiscoveryClientApplication.class, args);
    }

}
