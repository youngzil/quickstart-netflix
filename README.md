原项目地址
https://gitee.com/youngzil/quickstart-all

https://github.com/Netflix
https://netflix.github.io/

https://github.com/spring-cloud/spring-cloud-netflix


https://github.com/Netflix/eureka
https://github.com/Netflix/Hystrix
https://github.com/Netflix/ribbon
https://github.com/Netflix/zuul
https://github.com/Netflix/Turbine
https://github.com/Netflix/archaius



Eureka：Netflix的云平台服务发现技术。
Hystrix：提供单一服务调用所不具备的可靠性，提供运行时的延迟隔离和容错。
Ribbon：弹性且智能化的进程处理和服务通信。
Zuul：提供云部署周边的动态路由、监控、安全和弹性扩展等服务。
Archaius：分布式配置工具。




Netflix 填补了Amazon Web Services的大缺口，发布了云端负载均衡工具Eureka。Netflix通过开源工具让亚马逊的服务变得更可靠。亚马逊提供了一个负载均衡工具Elastic Load Balancer，但针对的是终端用户Web流量服务器，而Eureka针对的是中间层服务器的负载均衡。

Hystrix
　　Hystrix是一种延迟和容错库，旨在隔离访问远程系统、服务和第三方库的访问点，终止连锁故障，并且在故障不可避免的复杂分布式系统中确保具有弹性。在分布式环境下，许多服务依赖项中有一些难免出现故障。Hystrix这个库可以帮助你添加延迟容忍和容错逻辑，从而控制这些分布式服务之间的交互。为此，Hystrix隔离了服务之间的访问点，终止连锁故障，并提供应急后备选项，这一切增强了系统的整体弹性。
Hystrix 供分布式系统使用，提供延迟和容错功能，隔离远程系统、访问和第三方程序库的访问点，防止级联失败，保证复杂的分布系统在面临不可避免的失败时，仍能有其弹性。
Netflix 称，在分布式环境中，不可避免会造成一些服务的失败。Hystrix 库旨在控制分布式服务中提供更大容限和服务失败之间的相互关系。Hystrix 通过隔离访问远程系统、服务和第三方库的点，阻止级联故障，从而使复杂的分布式系统更具弹性。


Zuul 是提供动态路由，监控，弹性，安全等的边缘服务。Zuul 相当于是设备和 Netflix 流应用的 Web 网站后端所有请求的前门。Zuul 可以适当的对多个 Amazon Auto Scaling Groups 进行路由请求。

Turbine
　　Turbine这个工具用来将多路服务器发送事件（SSE）JSON数据流汇聚成一路数据流。针对性的使用场合是，汇聚来自SOA中的实例的度量数据流，用于呈现在仪表板上。Netflix使用的Hystrix有一个实时仪表板，仪表板使用Turbine汇集来自上百个或上千个机器的数据。


