package com.zhbr.nacossparkserver.service;

import java.io.IOException;

public interface SparkLauncherService {

    int submit(String appName,String jarPath,String queue,String mainClass,String[] appArgs) throws IOException;
}
