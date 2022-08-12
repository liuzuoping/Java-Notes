### 消息MQ

- [消息MQ](#toc_0)
- - [什么是消息中间件](#toc_1)
- - - [谈谈消息中间件 rocketmq,kafka,activemq,rabbitmq从架构设计，再到实现，以及应用场景区别？](#toc_2)
- - [什么是JMS](#toc_3)
- - - [JMS定义五种不同的消息格式，其中（1）、（2）用的比较多](#toc_4)
    - [JMS的消息传递类型](#toc_5)
- - [kafka](#toc_6)
- - - [Kafka 消息是采用 Pull 模式，还是 Push 模式？](#toc_7)
    - [Kafka consumer 是否可以消费指定分区消息？](#toc_8)
    - [Kafka 与传统消息系统之间区别](#toc_9)
    - [Kafka将以下消息保存至Zookeeper中](#toc_10)
    - [副本同步队列ISR(in-sync replicas)](#toc_11)

---

#### 什么是消息中间件

**消息中间件** ：是利用高效可靠的消息传递机制进行异步的数据传输，并基于[数据通信](https://so.csdn.net/so/search?q=%E6%95%B0%E6%8D%AE%E9%80%9A%E4%BF%A1&spm=1001.2101.3001.7020)进行分布式系统的集成。通过提供消息队列模型和消息传递机制，可以在分布式环境下扩展进程间的通信。

#### 谈谈消息中间件 rocketmq,kafka,activemq,rabbitmq从架构设计，再到实现，以及应用场景区别？

1、ActiveMQ：更新比较慢、java开发的、万级吞吐量
2、RabbitMQ:相对ActiveMQ来说更新较快、erlang语言开发（erlang语言天生具有高并发的特效，但是熟悉erlang的人相对较少，好在社区比较活跃）
3、RocketMQ：支持分布式架构、Java语言开发可以定制化开发
4、Kafka：支持分布式架构、吞吐量十万级

#### 什么是JMS

JMS（Java Messaging Service）是Java平台上有关面向消息中间件的技术规范，它便于消息系统中的Java应用程序进行消息交换,并且通过提供标准的产生、发送、接收消息的接口简化企业应用的开发。

#### JMS定义五种不同的消息格式，其中（1）、（2）用的比较多

- TextMessage,一个字符串对象
- MapMessage一套键值对
- ObjectMessage一个序列化的Java对象
- ByteMessage一个字节的数据流
- StreamMessageJava原始值的数据流

#### JMS的消息传递类型

- 一种是点对点，消息生产者与消息消费者一一对应
  ![img](https://cdn.nlark.com/yuque/0/2019/png/406432/1565343098352-92159ac2-8d70-4710-a87b-c26809852c69.png)
- 一种是发布/订阅模式，消费者订阅后，生产者产生一条消息可能会有多个消费者接收
  ![img](https://cdn.nlark.com/yuque/0/2019/png/406432/1565343107755-fca2a24d-f98c-4f5f-afdc-e57d5ae48e64.png)

#### kafka

#### Kafka 消息是采用 Pull 模式，还是 Push 模式？

Kafka 遵循了一种大部分消息系统共同的传统的设计：producer 将消息推送到 broker，consumer 从 broker 拉取消息

#### Kafka consumer 是否可以消费指定分区消息？

Kafaconsumer 消费消息时，向 broker 发出"fetch"请求去消费特定分区的消息，consumer 指定消息在日志中的偏移量（offset），就可以消费从这个位置开始的消息，customer 拥有了 offset 的控制权，可以向后回滚去重新消费之前的消息。

#### Kafka 与传统消息系统之间区别

- Kafka 持久化日志，这些日志可以被重复读取和无限期保留
- Kafka 是一个分布式系统：它以集群的方式运行，可以灵活伸缩，在内部通过复制数据提升容错能力和高可用性
- Kafka 支持实时的流式处理

#### Kafka将以下消息保存至Zookeeper中

- 消费者组的每个分区的偏移量，不过后来Kafka将其保存至内部主题__consumer_offsets中
- 访问权限列表
- 生产者和消费者速率限定额度
- 分区leader信息和它们的健康状态

![img](https://cdn.nlark.com/yuque/0/2019/png/406432/1565343143890-8fbde426-be29-4f98-8036-98123d62b925.png)

- Controller控制器的作用
  控制器负责决定集群如何做出反应并指示节点做某事，它是功能不能过于复杂的Broker节点，最主要的职责是负责节点下线和重新加入时重平衡和分配新的分区leader

#### 副本同步队列ISR(in-sync replicas)

副本同步队列ISR(in-sync replicas)，它是由leader维护的，follower从leader同步数据是有延迟的，任意一个超过阈值都会被剔除出ISR列表, 存入OSR(Outof-Sync Replicas)列表中，新加入的follower也会先存放在OSR中

一个follower想被选举成leader，它必须在ISR队列中才有资格,不过，在没有同步副本存在并且已有leader都下线的边缘情况下，可以选择可用性而不是一致性

ISR列表维护标准如下：

- 它在过去的X秒内有完整同步leader消息，通过replica.lag.time.max.ms配置约定
- 它在过去的X秒内向Zookeeper发送了一个心跳，通过zookeeper.session.timeout.ms配置约定

#### Kafka

##### Zookeeper 对于 Kafka 的作用是什么？

Zookeeper 是一个开放源码的、高性能的协调服务，它用于 Kafka 的分布式应用。
Zookeeper 主要用于在集群中不同节点之间进行通信
在 Kafka 中，它被用于提交偏移量，因此如果节点在任何情况下都失败了，它都可以从之前提交的偏移量中获取除此之外，它还执行其他活动，如: leader 检测、分布式同步、配置管理、识别新节点何时离开或连接、集群、节点实时状态等等。

1、Broker注册与消费者注册以及Topic注册

**Broker是分布式部署并且相互之间相互独立，但是需要有一个注册系统能够将整个集群中的Broker管理起来**，此时就使用到了Zookeeper。在Zookeeper上会有一个专门**用来进行Broker服务器列表记录**的节点：

/brokers/ids

每个Broker在启动时，都会到Zookeeper上进行注册，即到/brokers/ids下创建属于自己的节点，如/brokers/ids/[0...N]。

Kafka使用了全局唯一的数字来指代每个Broker服务器，不同的Broker必须使用不同的Broker ID进行注册，创建完节点后，**每个Broker就会将自己的IP地址和端口信息记录**到该节点中去。其中，Broker创建的节点类型是临时节点，一旦Broker宕机，则对应的临时节点也会被自动删除。

每个消费者服务器启动时，都会到Zookeeper的指定节点下创建一个属于自己的消费者节点，例如/consumers/[group_id]/ids/[consumer_id]，完成节点创建后，消费者就会将自己订阅的Topic信息写入该临时节点。

**对 消费者分组 中的 消费者 的变化注册监听**

在Kafka中，同一个**Topic的消息会被分成多个分区**并将其分布在多个Broker上，**这些分区信息及与Broker的对应关系**也都是由Zookeeper在维护

2、生产者与消费者负载均衡

由于同一个Topic消息会被分区并将其分布在多个Broker上，因此，**生产者需要将消息合理地发送到这些分布式的Broker上**，那么如何实现生产者的负载均衡，Kafka支持传统的四层负载均衡，也支持Zookeeper方式实现负载均衡。

与生产者类似，Kafka中的消费者同样需要进行负载均衡来实现多个消费者合理地从对应的Broker服务器上接收消息，每个消费者分组包含若干消费者，**每条消息都只会发送给分组中的一个消费者**，不同的消费者分组消费自己特定的Topic下面的消息，互不干扰。

##### Kafka角色

kafka是一个分布式的基于发布/订阅模式的消息队列

学习kafka的架构前，我们Kafka每个角色：

　　（1）consumer group 消费者组：这是kafka消息队列特有的角色，它是一堆消费组组合成的。消费者组中的每个消费者负责消费不同分区的数据，一个分区只能一个消费者消费，它们互不影响。

　　（2）broker：一台kafka服务器就是一个broker，一个broker可以有多个topic。

　　（3）topic：可以看做是一个队列。

　　（4）partition：为了实现扩展性，一个非常大的topic可以有分成多个partition，它们可以分布在多个broker上。

##### 为什么要使用 kafka？

1. 缓冲和削峰：上游数据时有突发流量，下游可能扛不住，或者下游没有足够多的机器来保证冗余，kafka在中间可以起到一个缓冲的作用，把消息暂存在kafka中，下游服务就可以按照自己的节奏进行慢慢处理。
2. 解耦和扩展性：项目开始的时候，并不能确定具体需求。消息队列可以作为一个接口层，解耦重要的业务流程。只需要遵守约定，针对数据编程即可获取扩展能力。
3. 冗余：可以采用一对多的方式，一个生产者发布消息，可以被多个订阅topic的服务消费到，供多个毫无关联的业务使用。
4. 健壮性：消息队列可以堆积请求，所以消费端业务即使短时间死掉，也不会影响主要业务的正常进行。
5. 异步通信：很多时候，用户不想也不需要立即处理消息。消息队列提供了异步处理机制，允许用户把一个消息放入队列，但并不立即处理它。想向队列中放入多少消息就放多少，然后在需要的时候再去处理它们。

##### Kafka 与传统 MQ 消息系统之间有三个关键区别

(1).Kafka 持久化日志，这些日志可以被重复读取和无限期保留
(2).Kafka 是一个分布式系统：它以集群的方式运行，可以灵活伸缩，在内部通过复制数据提升容错能力和高可用性
(3).Kafka 支持实时的流式处理

##### Kafka 判断一个节点是否还活着的两个条件

（1）节点必须可以维护和 ZooKeeper 的连接，Zookeeper 通过心跳机制检查每个节点的连接
（2）如果节点是个 follower,他必须能及时的同步 leader 的写操作，延时不能太久

##### partition的数据文件（offffset，MessageSize，data）

partition中的每条Message包含了以下三个属性： offset，MessageSize，data，其中offset表示Message在这个partition中的偏移量，offset不是该Message在partition数据文件中的实际存储位置，而是逻辑上一个值，它唯一确定了partition中的一条Message，可以认为offset是partition中Message的 id； MessageSize表示消息内容data的大小；data为Message的具体内容。

##### 如何保证 Kafka 消息不重复消费？

1.保存并且查询 每个消息一个唯一key 消费国记录下来，每次消费查询是否消费过，没有消费过才进行消费

2.利用幂等

将消费的业务设计成幂等性，比如利用数据库的唯一约束条件进行实现（比如转账操作 在转账流水记录中相同的账单id和相同的账户id视为唯一约束条件 重复操作直接失败）

3.设置前置条件的形式

比如通过数据加一个版本号的属性，每次更新数据前比较当前版本是否和消息中版本一致 如果不一致可以拒绝操作

##### Kafka怎么保证消息不丢失（存入和读取）

从 Kafka 整体架构图我们可以得出有三次消息传递的过程：

**1）Producer 端发送消息给 Kafka Broker 端。**

解决方法

（1）网络抖动导致消息丢失，Producer 端可以进行重试。

（2）消息大小不合格，可以进行适当调整，符合 Broker 承受范围再发送。

 （3） **request.required.acks设置为 -1/ all**，-1/all 表示有多少个副本 Broker 全部收到消息，才认为是消息提交成功的标识。

kafka 的 ack 的三种机制

request.required.acks 有三个值 0 1 -1(all)
0:生产者不会等待 broker 的 ack，这个延迟最低但是存储的保证最弱当 server 挂掉的时候就会丢数据。
1：服务端会等待 ack 值 leader 副本确认接收到消息后发送 ack 但是如果 leader挂掉后他不确保是否复制完成新 leader 也会导致数据丢失。
-1(all)：服务端会等所有的 follower 的副本受到数据后才会受到 leader 发出的ack，这样数据不会丢失

**2）Kafka Broker 将消息进行同步并持久化数据。**

既然 Broker 端消息存储是通过异步批量刷盘的，那么这里就可能会丢数据的

- 由于 Kafka 中并没有提供「**同步刷盘**」的方式，所以说从单个 Broker 来看还是很有可能丢失数据的。
- kafka 通过「多 Partition （分区）多 Replica（副本）机制」已经可以最大限度的保证数据不丢失，如果数据已经写入 PageCache 中但是还没来得及刷写到磁盘，此时如果所在 Broker 突然宕机挂掉或者停电，极端情况还是会造成数据丢失。

3）Kafka Broker 将消息拉取并进行消费。

- 在剖析 Consumer 端丢失场景的时候，我们得出其拉取完消息后是需要提交 Offset 位移信息的，因此为了不丢数据，正确的做法是：**拉取数据、**业务逻辑处理、提交消费 Offset 位移信息。我们还需要设置参数**enable.auto.commit = false, 采用手动提交位移的方式。**

消费者如何不自动提交偏移量，由应用提交？

将 auto.commit.offset 设为 false，然后在处理一批消息后 commitSync() 或者异步提交 commitAsync()
即：

```java
ConsumerRecords<> records = consumer.poll();
for (ConsumerRecord<> record : records){
    。。。
    try{
        consumer.commitSync()
    }
    。。。
}
```

另外对于消费消息重复的情况，业务自己保证幂等性, **保证只成功消费一次即可**。

##### Kafka怎么处理消息堆积

+ 增加分区同时增加消费实例
+ 单个消费者线程使用异步消费(线程池)

##### kafka的isr机制

先来看几个概念

1、AR（Assigned Repllicas）一个partition的所有副本（就是replica，不区分leader或follower）

2、ISR（In-Sync Replicas）能够和 leader 保持同步的 follower + leader本身 组成的集合。

3、OSR（Out-Sync Relipcas）不能和 leader 保持同步的 follower 集合

4、公式：AR = ISR + OSR
Kafka采用的就是一种完全同步的方案，而ISR是基于完全同步的一种优化机制。Kafka只保证对ISR集合中的所有副本保证完全同步。处于ISR内部的follower都是可以和leader进行同步的，一旦出现故障或延迟，就会被踢出ISR。

##### Kafka如何保证顺序消费

如何保证Kafka消息的顺序执行

kafka只能保证同一个partition内的消息是顺序性的，但是整个topic下并不能保证是顺序的

1.因为kafka 天然存在是属性在同一个partion中消息是有顺序的 可以利用这种机制 同一类型的消息分配到一个分区partion中

2.消费者内部创建内存队列，对于需要顺序消费的业务数据，根据key或者业务数据放入同一个队列中，然后线程从对应的队列中取出数据

关于顺序消费的几点说明：

①、kafka的顺序消息仅仅是通过partitionKey，将某类消息写入同一个partition，一个partition只能对应一个消费线程，以保证数据有序。

②、除了发送消息需要指定partitionKey外，producer和consumer实例化无区别。

③、kafka broker宕机，kafka会有自选择，所以宕机不会减少partition数量，也就不会影响partitionKey的sharding。

那么问题来了：在1个topic中，有3个partition，那么如何保证数据的消费？

1、如顺序消费中的第①点说明，生产者在写的时候，可以指定一个 key，比如说我们指定了某个订单 id 作为 key，那么这个订单相关的数据，一定会被分发到同一个 partition 中去，而且这个 partition 中的数据一定是有顺序的。

2、消费者从 partition 中取出来数据的时候，也一定是有顺序的。到这里，顺序还是 ok 的，没有错乱。

3、但是消费者里可能会有多个线程来并发来处理消息。因为如果消费者是单线程消费数据，那么这个吞吐量太低了。而多个线程并发的话，顺序可能就乱掉了。K所以对于 Kafka 的消息顺序性保证，其实我们只需要保证同一个订单号的消息只被同一个线程处理的就可以了。由此我们可以在线程处理前增加个内存队列，每个线程只负责处理其中一个内存队列的消息，同一个订单号的消息发送到同一个内存队列中即可。

##### kafka 分布式（不是单机）的情况下，如何保证消息的顺序消费?

Kafka 分布式的单位是 partition，同一个 partition 用一个 write ahead log 组织，所以可以保证 FIFO 的顺序。不同 partition 之间不能保证顺序。但是绝大多数用户都可以通过 message key 来定义，因为同一个 key 的 message 可以保证只发送到同一个 partition。
Kafka 中发送 1 条消息的时候，可以指定(topic, partition, key) 3 个参数。partiton 和 key 是可选的。如果你指定了 partition，那就是所有消息发往同 1个 partition，就是有序的。并且在消费端，Kafka 保证，1 个 partition 只能被1 个 consumer 消费。或者你指定 key（ 比如 order id），具有同 1 个 key 的所有消息，会发往同 1 个 partition。
