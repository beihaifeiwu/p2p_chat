1.概述
	 p2p_chat是一个局域网内的聊天系统,通过UDP进行网络通信，局域网内的每一个p2p_chat应用实例称为一个节点。
 
 每一个p2p应用程序既是一个客户端也是一个服务端，客户端负责与用户交互，服务端负责与其他的节点通信。
 
当前的实现通过命令窗口进行操作，实现类为/p2p_chat/src/com/p2p/chat/main/ConsoleImpl.java

整体应用的架构是基于事件响应处理的，即程序中有一个事件队列和多种类型的event以及他们的Processor,每一个Processor

和一种event相对应 例如:LoginEvent <===> LoginProcessor

2.事件
	事件的超类是/p2p_chat/src/com/p2p/chat/event/Event.java 同时还有一个工具类EventBuilder方便

创造事件。已经实现的事件有LoginEvent、ExitEvent、LogoutEvent。事件可以由远方节点产生并通过UDP传输到本节点，由

本节点的服务器监听到并将其放入事件队列。事件也可以由本节点产生，如果需要通过网络传播到其它节点，则需要将其传递给服务器。因而

事件的传播具有方向性，可以从Client流向Server，也可以相反。因此每个事件处理器必须提供两个处理流程，分别对应不同流向的事件。

3.事件处理器

Processor的超类是/p2p_chat/src/com/p2p/chat/processor/AbstractProcessor.java，其中定义了分别用于

处理两个方向上的event。除了这一类继承自AbstractProcessor的普通处理器外，还有两个特殊但又是必须的处理器，分别是：

/p2p_chat/src/com/p2p/chat/processor/DispatchEventProcessor.java 事件分发处理器，内部封装有一个

线程池，用于执行相应的事件处理器，同时还有一个静态的map用于映射事件类型和它的处理器实例。事件分发处理器通过从事件队列中取得

事件，根据它的类型从map中取得处理器，然后将其放入线程池中运行。在这个处理器运行前必须将事件和它的处理器的映射通过静态方法注册进map中。

事件分发处理器是整个架构的核心，用来驱动整个应用的有序运行。

/p2p_chat/src/com/p2p/chat/processor/ReceiveEventProcessor.java 服务端事件监听处理器，这个处理器不断

从Server中取得通过网络传输过来的事件，并将其放入事件队列

这两个处理器必须通过手动加载，即在main函数或者main调用的函数中放入线程池

4.容器

现阶段主要有两个容器分别是事件队列和用户容器，其文件及作用是：

/p2p_chat/src/com/p2p/chat/container/EventContainer.java 内部封装一个事件队列，事件产生时将事件压入

队列尾部，处理时从头部开始处理。每个事件的处理是通过单独的线程进行处理的（可能会出现并发问题，暂时还没有遇到）。

/p2p_chat/src/com/p2p/chat/container/UserContainer.java 内部封装一个并发map，其key为用户的ip地址

其value为User实例，所有在线的user（包括自己）都会被放入容器中，每当收到用户下线事件后，都会将相应用户从容器中删除，收到上线事件

后将其加入容器。

5.服务器

由于使用的是UDP所以服务器相对简单一些，Server（/p2p_chat/src/com/p2p/chat/Server/Server.java）中拥有两个

数据报套接字，一个用于发送数据报，另一个用于接收数据报，发送数据报的端口和接受数据报的端口不相同（避免发送信息的时候，有信息被监听到

抛出异常）。还有一个工具类ServerUtils（建议使用它来与服务器通信），其封装了大量的实用方法，包括广播和发送事件等。

6.日志

本应用使用log4j来进行日志输出，有序并有意义的日志输出有助于排查问题和观察执行流程。例如应用的启动过程，首先向事件分发处理器

中注册事件和它的处理器的映射关系，然后，需要初始化好相应的容器，接着启动服务器开始事件监听等，这些通过log都可以比较清晰的被观察到。

现阶段应用启动过程中log打印如下：

21:21:57,491 TRACE DispatchEventProcessor:46 - 成功向事件分发处理器中注册关系：LoginEvent<===>LoginProcessor
21:21:57,495 TRACE DispatchEventProcessor:46 - 成功向事件分发处理器中注册关系：LogoutEvent<===>LogoutProcessor
21:21:57,495 TRACE DispatchEventProcessor:46 - 成功向事件分发处理器中注册关系：ExitEvent<===>ExitProcessor
21:21:57,501 TRACE EventContainer:25 - 建立事件容器
21:21:57,521  INFO DispatchEventProcessor:89 - 事件分发处理器开始执行
21:21:57,551  INFO ServerUtils:41 - 服务器建立成功
21:21:57,559  INFO ReceiveEventProcessor:19 - 端口事件监听处理器开始执行

7.任务

三人合作实现一对一聊天和群聊

要求：（1）.要有冲突的解决方案，例如正在与某人通信时收到另一个人发来的通信请求。

	（2）.要有良好的日志输出，通过log能清晰的看到你的处理流程
	
	（3）.如果可以的话希望能够查看消息记录（不做强制要求）


	