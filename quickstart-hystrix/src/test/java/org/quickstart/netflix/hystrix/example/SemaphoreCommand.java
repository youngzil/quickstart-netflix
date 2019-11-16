package org.quickstart.netflix.hystrix.example;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * @author youngzil@163.com
 * @description TODO
 * @createTime 2019/11/16 08:06
 */

public class SemaphoreCommand extends HystrixCommand<String> {

  private final String name;

  public SemaphoreCommand(String name) {
    super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
        /* 配置信号量隔离方式,默认采用线程池隔离 */
        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
            .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
    this.name = name;
  }

  @Override
  protected String run() throws Exception {
    return "HystrixThread:" + Thread.currentThread().getName();
  }

  public static void main(String[] args) throws Exception {
    SemaphoreCommand command = new SemaphoreCommand("semaphore");
    String result = command.execute();
    System.out.println(result);
    System.out.println("MainThread:" + Thread.currentThread().getName());
  }
}

