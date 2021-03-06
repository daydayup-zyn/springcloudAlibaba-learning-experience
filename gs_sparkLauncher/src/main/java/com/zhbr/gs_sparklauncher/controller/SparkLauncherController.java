package com.zhbr.gs_sparklauncher.controller;

import com.zhbr.gs_sparklauncher.service.SparkLauncherService;
import com.zhbr.gs_sparklauncher.utils.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping
public class SparkLauncherController {

    @Value("${server.address.ip}")
    private String app_ip;

    @Value("${server.port}")
    private String app_port;

    @Autowired
    private SparkLauncherService sparkLauncherService;

    /**
     * index页面
     * */
    @RequestMapping
    public String index(Model model){
        model.addAttribute("app_ip",app_ip);
        model.addAttribute("app_port",app_port);
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
