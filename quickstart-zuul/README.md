https://github.com/Netflix/zuul
https://github.com/Netflix/zuul/wiki



https://www.novatec-gmbh.de/en/blog/api-gateways-an-evaluation-of-zuul-2/
https://medium.com/netflix-techblog/zuul-2-the-netflix-journey-to-asynchronous-non-blocking-systems-45947377fb5c



参考
quickstart-gateway/quickstart-zuul


zuul2示例
http://springcloud.cn/view/344
https://thebackendguy.com/netflix-zuul-2-api-gateway-sample/
http://www.java-allandsundry.com/2018/06/zuul-2-sample-filter.html
https://github.com/bijukunjummen/boot2-load-demo/tree/master/applications/zuul2-sample
https://coe.gitbook.io/guide/gateway/zuul2.0

---------------------------------------------------------------------------------------------------------------------

Zuul 1建立在Servlet框架上。这样的系统是阻塞的和多线程的，这意味着它们通过每个连接使用一个线程来处理请求。
I / O操作是通过从线程池中选择一个工作线程来执行I / O来完成的，并且请求线程被阻塞，直到工作线程完成为止。
工作线程在其工作完成时通知请求线程。这对于现代的多核AWS实例非常适用，每个实例处理100个并发连接。
但是当出现问题时，例如后端延迟增加或由于错误而导致设备重试，活动连接和线程的数量就会增加。发生这种情况时，节点会陷入麻烦，并且可能陷入死亡螺旋，在此备份线程会激增服务器负载并淹没群集。为了抵消这些风险，我们内置了限制机制和库（例如，Hystrix），以在这些事件中帮助保持我们的阻塞系统稳定。


每个CPU内核一个线程处理，基于事件和回调
异步系统的运行方式不同，通常每个CPU内核一个线程处理所有请求和响应。

请求和响应的生命周期通过事件和回调进行处理。因为每个请求都没有线程，所以连接成本很便宜。这是文件描述符和添加侦听器的成本。而阻塞模型中的连接成本是线程，并且具有大量内存和系统开销。
由于数据保留在同一CPU上，从而可以更好地利用CPU级别的缓存，并且需要较少的上下文切换，因此可以提高效率。后端等待时间和“重试风暴”（客户和设备在发生问题时重试请求）的后果对系统的压力也较小，因为队列中的连接和增加的事件比堆积线程要便宜得多。

异步系统的优点听起来很光荣，但上述优点是以牺牲操作为代价的。阻塞系统易于调试和调试。

开源项目Reactive-Audit通过对服务器进行检测以发现代码块和库被阻塞的情况而有所帮助。



Zuul2流程：
过滤器前端和后端的 Netty 事件处理器（handler）主要负责处理网络协议、Web 服务器、连接管理和代理工作。
这些内部工作被抽象之后，所有主要的工作都会交给过滤器完成。
入站过滤器在代理请求之前运行，可用于验证、路由或装饰请求。
端点过滤器可用于返回静态响应，或将请求代理到后端服务。
出站过滤器在返回响应后运行，可用于诸如压缩（gzipping）、指标或增删自定义请求头之类的内容。





Zuul1建立在Servlet框架上。这样的系统是阻塞的和多线程的，这意味着它们通过每个连接使用一个线程来处理请求。
Zuul2使用的 Netty 框架依赖事件循环和回调函数。每个CPU内核一个线程处理，基于事件和回调



Zuul1和Zuul2对比
https://www.infoq.cn/article/2018/05/netflix-zuul2
http://ju.outofmemory.cn/entry/362416
https://www.cnblogs.com/davidwang456/p/6421025.html


---------------------------------------------------------------------------------------------------------------------

Zuul2 FilterChain 原理分析

zuul2 和1版本的核心区别是用netty 实现了接入端和后端服务的调用，全部是事件驱动，异步化了

requestFilterChain ---->  endPoint ---->  responseFilterChain

我们网关实现一般都会聚合，即等http request header 和request content 都到达后再经过处理统一发给后端服务。zuul2 的实现还是比较激进，即来一块content 就发一块，特别适合trunk 模式的请求，做到流试处理。

参考

https://www.jianshu.com/p/370a9ec0be5c
https://jaxenter.com/open-source-zuul2-144698.html
https://www.oschina.net/news/96378/zuul-2-1-2-released
https://mp.weixin.qq.com/s?__biz=MzI4MTY5NTk4Ng==&mid=2247489055&amp;idx=1&amp;sn=c56ad2dd80a242cb354d81ad5e5c722c&source=41#wechat_redirect
https://medium.com/netflix-techblog/open-sourcing-zuul-2-82ea476cb2b3

---------------------------------------------------------------------------------------------------------------------







