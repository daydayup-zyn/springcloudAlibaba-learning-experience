package com.zhbr.gs_sparklauncher.service.impl;

import com.zhbr.gs_sparklauncher.config.WebSocketServer;
import com.zhbr.gs_sparklauncher.service.SparkLauncherService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Service
public class SparkLauncherServiceImpl implements SparkLauncherService {

    @Value("${hadoop.conf.dir}")
    private String hadoop_conf_dir;

    @Value("${spark.home.dir}")
    private String spark_home_dir;

    @Value("${spark.driver.memory}")
    private String spark_driver_memory;

    @Value("${spark.executor.memory}")
    private String spark_executor_memory;

    @Value("${spark.executor.cores}")
    private String spark_executor_cores;

    @Value("${spark.executor.instances}")
    private String spark_executor_instances;

    @Value("${spark.default.parallelism}")
    private String spark_default_parallelism;

    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    public void test() {
        log.info("hadoop_conf_dir:"+hadoop_conf_dir);
        log.info("spark_driver_memory:"+spark_driver_memory);
        log.info("spark_executor_cores:"+spark_executor_cores);
        log.info("spark_default_parallelism:"+spark_default_parallelism);
        int i = 0;
        while (i<10){
            webSocketServer.sendMessage("sparkJobLog","spark job status：hello world");
            i++;
        }
    }

    @Override
    public int submit(String appName, String jarPath, String mainClass) throws IOException {
        int flag = 0;
        HashMap env = new HashMap();
        env.put("HADOOP_CONF_DIR", hadoop_conf_dir);

        SparkAppHandle handler = new SparkLauncher(env).setAppName(appName)
                .setSparkHome(spark_home_dir)
                .setMaster("yarn")
                .setConf("spark.driver.memory", spark_driver_memory)
                .setConf("spark.executor.memory", spark_executor_memory)
                .setConf("spark.executor.cores", spark_executor_cores)
                .setConf("spark.executor.instances", spark_executor_instances)
                .setConf("spark.default.parallelism", spark_default_parallelism)
                .setConf("spark.yarn.queue","default")
                .setConf("spark.driver.allowMultipleContexts", "true")
                .setAppResource(jarPath)
                .setMainClass(mainClass)
                .setDeployMode("cluster")
                .startApplication(new SparkAppHandle.Listener(){
                    @Override
                    public void stateChanged(SparkAppHandle handle) {
                        log.info("**********  state  changed  **********");
                        webSocketServer.sendMessage("sparkJobLog","spark job status："+handle.getState().toString());
                    }

                    @Override
                    public void infoChanged(SparkAppHandle handle) {
                        log.info("**********  info  changed  **********");
                        webSocketServer.sendMessage("sparkJobLog","spark job status："+handle.getState().toString());
                    }
                });

        while(!"FINISHED".equalsIgnoreCase(handler.getState().toString()) && !"FAILED".equalsIgnoreCase(handler.getState().toString())){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if ("FINISHED".equalsIgnoreCase(handler.getState().toString())){
            webSocketServer.sendMessage("sparkJobLog","spark app id："+handler.getAppId());
            webSocketServer.sendMessage("sparkJobLog","执行完成，请确认是否执行成功！");
            flag = 1;
        }else if ("FAILED".equalsIgnoreCase(handler.getState().toString())){
            flag = -1;
            webSocketServer.sendMessage("sparkJobLog","spark app id："+handler.getAppId());
            webSocketServer.sendMessage("sparkJobLog","执行失败，请去yarn查看具体运行日志！");
        }
        return flag;
    }
}
