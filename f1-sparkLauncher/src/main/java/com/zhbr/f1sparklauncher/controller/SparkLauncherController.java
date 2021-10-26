package com.zhbr.f1sparklauncher.controller;

import com.zhbr.f1sparklauncher.service.SparkLauncherService;
import com.zhbr.f1sparklauncher.util.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RequestMapping("/spark")
@RestController
public class SparkLauncherController {

    @Autowired
    private SparkLauncherService sparkLauncherService;

    /**
     * 参数存在非法字符,get方法会报错
     * @param appName
     * @param jarPath
     * @param queue
     * @param mainClass
     * @return
     * @throws IOException
     */
    @PostMapping("/submit")
    public APIResponse run(String appName, String jarPath, String queue, String mainClass) throws IOException {
        APIResponse apiResponse = new APIResponse();
        int flag = sparkLauncherService.submit(appName, jarPath,queue, mainClass);
        if (flag==1){
            apiResponse = APIResponse.success();
        }else if (flag==-1){
            apiResponse = APIResponse.fail("request failed");
        }
        return apiResponse;
    }

    @GetMapping("/test")
    public void test(){
        sparkLauncherService.test();
    }
}
