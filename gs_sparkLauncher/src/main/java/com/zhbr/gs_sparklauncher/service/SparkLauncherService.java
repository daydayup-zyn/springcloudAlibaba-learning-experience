package com.zhbr.gs_sparklauncher.service;

import java.io.IOException;

public interface SparkLauncherService {

    void test();

    int submit(String appName,String jarPath,String mainClass) throws IOException;
}
