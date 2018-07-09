/**
 * 项目名称：quickstart-netflix-eureka 
 * 文件名：SampleEurekaService.java
 * 版本信息：
 * 日期：2018年5月19日
 * Copyright asiainfo Corporation 2018
 * 版权所有 *
 */
package org.quickstart.netflix.eureka.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryManager;

/**
 * SampleEurekaService
 * 
 * @author：yangzl@asiainfo.com
 * @2018年5月19日 下午4:10:14
 * @since 1.0
 */
public class SampleEurekaService {

    private static final DynamicPropertyFactory configInstance = com.netflix.config.DynamicPropertyFactory.getInstance();

    private static final Logger logger = LoggerFactory.getLogger(SampleEurekaService.class);

    @SuppressWarnings("deprecation")
    public void registerWithEureka() {
        // Register with Eureka
        DiscoveryManager.getInstance().initComponent(new MyDataCenterInstanceConfig(), new DefaultEurekaClientConfig());
        ApplicationInfoManager.getInstance().setInstanceStatus(InstanceStatus.UP);
        String vipAddress = configInstance.getStringProperty("eureka.vipAddress", "sampleservice.mydomain.net").get();
        InstanceInfo nextServerInfo = null;
        while (nextServerInfo == null) {
            try {
                nextServerInfo = DiscoveryManager.getInstance().getDiscoveryClient().getNextServerFromEureka(vipAddress, false);
            } catch (Throwable e) {
                System.out.println("Waiting for service to register with eureka..");

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        }
        System.out.println("Service started and ready to process requests..");

        try {
            ServerSocket serverSocket = new ServerSocket(configInstance.getIntProperty("eureka.port", 8010).get());
            final Socket s = serverSocket.accept();
            System.out.println("Client got connected..Processing request from the client");
            processRequest(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.unRegisterWithEureka();
        System.out.println("Shutting down server.Demo over.");

    }

    public void unRegisterWithEureka() {
        // Un register from eureka.
        DiscoveryManager.getInstance().shutdownComponent();
    }

    private void processRequest(final Socket s) {
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String line = rd.readLine();
            if (line != null) {
                System.out.println("Received the request from the client.");
            }
            PrintStream out = new PrintStream(s.getOutputStream());
            System.out.println("Sending the response to the client...");

            out.println("Reponse at " + new Date());

        } catch (Throwable e) {
            System.err.println("Error processing requests");
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String args[]) {
        SampleEurekaService sampleEurekaService = new SampleEurekaService();
        sampleEurekaService.registerWithEureka();
    }

}
