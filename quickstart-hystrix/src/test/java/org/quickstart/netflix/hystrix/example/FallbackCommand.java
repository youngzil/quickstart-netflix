package org.quickstart.netflix.hystrix.example;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import java.util.concurrent.TimeUnit;

/**
 * @author youngzil@163.com
 * @description TODO
 * @createTime 2019/11/16 08:02
 */
//重载HystrixCommand的getFallback方法实现逻辑
public class FallbackCommand extends HystrixCommand<String> {

  private final String name;

  public FallbackCommand(String name) {
    super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
            .withExecutionIsolationThreadTimeoutInMilliseconds(500)));
    this.name = name;
  }

  @Override
  protected String getFallback() {
    return "exeucute Falled";
  }

  @Override
  protected String run() throws Exception {
    //sleep 1 秒,调用会超时
    TimeUnit.MILLISECONDS.sleep(1000);
    return "Hello " + name + " thread:" + Thread.currentThread().getName();
  }

  public static void main(String[] args) throws Exception {
    FallbackCommand command = new FallbackCommand("test-Fallback");
    String result = command.execute();
    System.out.println("result=" + result);
  }
}

