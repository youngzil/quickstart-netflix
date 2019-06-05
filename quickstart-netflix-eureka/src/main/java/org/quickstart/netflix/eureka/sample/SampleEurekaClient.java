/**
 * 项目名称：quickstart-netflix-eureka 
 * 文件名：SampleEurekaClient.java
 * 版本信息：
 * 日期：2018年5月19日
 * Copyright yangzl Corporation 2018
 * 版权所有 *
 */
package org.quickstart.netflix.eureka.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
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
 * SampleEurekaClient 
 *  
 * @author：youngzil@163.com
 * @2018年5月19日 下午4:13:06 
 * @since 1.0
 */
public class SampleEurekaClient {  
    private static final DynamicPropertyFactory configInstance = com.netflix.config.DynamicPropertyFactory  
            .getInstance();  
      
    private static final Logger logger = LoggerFactory  
    .getLogger(SampleEurekaClient.class);  
  
  
    public void sendRequestToServiceUsingEureka() {  
        // Register with Eureka  
        DiscoveryManager.getInstance().initComponent(  
                new MyDataCenterInstanceConfig(),  
                new DefaultEurekaClientConfig());  
        ApplicationInfoManager.getInstance().setInstanceStatus(  
                InstanceStatus.UP);  
        String vipAddress = configInstance.getStringProperty(  
                "eureka.vipAddress", "sampleservice.mydomain.net").get();  
        InstanceInfo nextServerInfo = DiscoveryManager.getInstance()  
                .getDiscoveryClient()  
                .getNextServerFromEureka(vipAddress, false);  
  
  
        Socket s = new Socket();  
        int serverPort = nextServerInfo.getPort();  
        try {  
            s.connect(new InetSocketAddress(nextServerInfo.getHostName(),  
                    serverPort));  
        } catch (IOException e) {  
            System.err.println("Could not connect to the server :"  
                    + nextServerInfo.getHostName() + " at port " + serverPort);  
        }  
        try {  
            System.out.println("Connected to server. Sending a sample request");  
            PrintStream out = new PrintStream(s.getOutputStream());  
            out.println("Sample request " + new Date());  
            String str = null;  
            System.out.println("Waiting for server response..");  
            BufferedReader rd = new BufferedReader(new InputStreamReader(  
                    s.getInputStream()));  
            str = rd.readLine();  
            if (str != null) {  
                System.out  
                        .println("Received response from server. Communication all fine using Eureka :");  
                System.out.println("Exiting the client. Demo over..");  
            }  
            rd.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        this.unRegisterWithEureka();  
    }  
  
  
    public void unRegisterWithEureka() {  
        // Un register from eureka.  
        DiscoveryManager.getInstance().shutdownComponent();  
    }  
  
  
    public static void main(String[] args) {  
        SampleEurekaClient sampleEurekaService = new SampleEurekaClient();  
        sampleEurekaService.sendRequestToServiceUsingEureka();  
  
  
    }  
  
  
}  
