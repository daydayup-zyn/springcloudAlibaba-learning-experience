package com.zhbr.f1sparklauncher.service;

import java.io.IOException;

public interface SparkLauncherService {

    void test();

    int submit(String appName,String jarPath,String queue,String mainClass) throws IOException;
}
