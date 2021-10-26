package com.zhbr.f1sparklauncher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class F1SparkLauncherApplication {

    public static void main(String[] args) {
        SpringApplication.run(F1SparkLauncherApplication.class, args);
    }

}
