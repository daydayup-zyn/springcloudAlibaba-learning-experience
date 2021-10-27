package com.zhbr.gs_sparklauncher.controller;

import com.zhbr.gs_sparklauncher.service.SparkLauncherService;
import com.zhbr.gs_sparklauncher.utils.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping
public class SparkLauncherController {

    @Autowired
    private SparkLauncherService sparkLauncherService;

    /**
     * index页面
     * */
    @RequestMapping
    public String index(){
        return "index";
    }

    /**
     * 参数存在非法字符,get方法会报错
     * @param appName
     * @param jarPath
     * @param mainClass
     * @return
     * @throws IOException
     */
    @RequestMapping("submit.do")
    @ResponseBody
    public APIResponse run(String appName, String jarPath,String mainClass) throws IOException {
        APIResponse apiResponse = new APIResponse();
        int flag = sparkLauncherService.submit(appName, jarPath,mainClass);
        if (flag==1){
            apiResponse = APIResponse.success();
        }else if (flag==-1){
            apiResponse = APIResponse.fail("request failed");
        }
        return apiResponse;
    }

    @RequestMapping("test.do")
    @ResponseBody
    public APIResponse test(String appName,String jarPath,String mainClass){
        sparkLauncherService.test();
        return APIResponse.success();
    }
}
