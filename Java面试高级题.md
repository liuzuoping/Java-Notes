#### 高级题

##### rpc相关：如何设计一个rpc框架，从io模型 传输协议 序列化方式综合考虑

一个RPC调用的过程如下

1. 调用方发送请求后由代理类将调用的方法，参数组装成能进行网络传输的消息体
2. 调用方将消息体序列化后发送到提供方
3. 提供方将消息进行反序列化，得到调用的参数
4. 提供方反射执行相应的方法，并将结果返回

因为RPC一般用在高并发的场景下，因此我们选择IO多路复用这种模型，Netty的IO多路复用基于Reactor开发模式来实现

**Dubbo为什么要自定义协议，而不用现成的Http协议？**

**最主要的原因就是自定义协议可以提高性能**

1. Http协议的请求包比较大，有很多无用的内容。自定义协议可以精简很多内容
2. Http协议是无状态的，每次都要重新建立连接，响应完毕后将连接关闭

网络传输的数据必须是二进制数据，但调用方的入参和提供方的返回值都是对象，因此需要序列化和反序列化的过程

序列化的方式有如下几种

JDK原生序列化
JSON
Protobuf
Kryo
Hessian2
MessagePack
我们选择序列化的方式时，主要考虑如下几个因素

效率
空间开销
通用性和兼容性
安全性


##### StringBuff 和StringBuilder的实现，底层实现是通过byte数据，外加数组的拷贝来实现的

(Stringbuilder与stringbuffer相同底层原理相同只是stringbuffer加了锁,下面以stringbuilder为例)
1.StringBuffer和StringBuilder都继承了AbstractStringBuilder
2.new StringBuilder()对象时：
（1） 如果传入参数为空，默认造一个长度为16的char型数组
（2）如果传入的参数为int型整数，造一个整数大小的char型数组
（3）如果传入的参数为string类型的字符串，造一个（字符串长度+16）大小的char型数组，然后调用append()方法

##### 内存缓存和数据库的一致性同步实现


##### 微服务的优缺点

优点：

1.每个微服务都很小，这样能聚焦一个指定的业务功能或业务需求；
2.微服务能够被小团队单独开发；
3.微服务是松耦合的，是有功能意义的服务，无论是在开发阶段或部署阶段都是独立的；
4.微服务能使用不同的语言开发；
5.微服务易于被一个开发人员理解，修改和维护，这样小团队能够更关注自己的工作成果，无需通过合作才能体现价值；
6.微服务只是业务逻辑的代码，不会和HTML,CSS 或其他界面组件混合；
缺点：

* 1.运维要求较高；
* 2.分布式的复杂性；
* 3.接口调整成本高；
* 4.学习难度曲线加大：需要掌握一系列的微服务开发技术
* 5.处理分布式事务较棘手
* 6.多服务运维难度，随着服务的增加，运维的压力也在增大

##### 线程池的参数问题,线程池：参数，每个参数的作用

`ThreadPoolExecutor`的带的那些重要参数的构造器如下：

```java
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler) {
    ...
}
```

**1、corePoolSize: 核心线程数**

CPU密集型：corePoolSize = CPU核数 + 1

IO密集型：corePoolSize = CPU核数 * 2

2、maximumPoolSize：最大线程数

当线程数>=corePoolSize，且任务队列未满时。线程池会创建新线程来处理任务。
当线程数=maxPoolSize，且任务队列已满时，线程池会拒绝处理任务而抛出异常。
3、keepAliveTime：线程空闲时间

当线程空闲时间达到keepAliveTime时，线程会退出，直到线程数量=corePoolSize。
如果allowCoreThreadTimeout=true，则会直到线程数量=0。
4、queueCapacity：任务队列容量（阻塞队列）

当核心线程数达到最大时，新任务会放在队列中排队等待执行
5、allowCoreThreadTimeout：允许核心线程超时

6、rejectedExecutionHandler：任务拒绝处理器

##### ip问题 如何判断ip是否在多个ip段中

我们知道IP地址是由“网络号+子网号+主机号”组成，判断两个IP地址是否在同一个网段主要看“网络号”,如果网络号一样，那么他们就在同一个网段，否则就不在一个网段。

##### 判断数组两个中任意两个数之和是否为给定的值

```java
public static boolean solution1(int array[],int num) {
        for (int i = 0;i < array.length-1;i ++) {
            for (int j = i+1;j < array.length;j ++) {
                if (i != j && array[i] + array[j] == num) {
                    return true;
                }
            }
        }
        return false;
    }
```


```java
    public static  Map<Integer,Integer> method(int[] nums, int sum){
	sort(nums);
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        for(int i=0,j=nums.length-1;i!=j;){
            int result = nums[i]+nums[j];
            if(result == sum){
                map.put(i,j);
                i++;
                j--;
            }else  if(result < sum){
                i++;
            }else  if(result > sum){
                j--;
            }
        }
        return map;
    }
 
    //数组排序
    public static void sort(int[] d){
        int temp =0;
        for(int i=0;i<d.length-1;i++){
            for(int j = i;j<d.length-1-i;j++){
                if(d[j]>d[j+1]){
                    temp = d[j];
                    d[j] = d[j+1];
                    d[j+1] = temp;
                }
            }
        }
    }
```

##### synchronized实现原理

###### synchronized代码块底层原理

同步语句块的实现使用的是monitorenter 和 monitorexit 指令，其中monitorenter指令指向同步代码块的开始位置，monitorexit指令则指明同步代码块的结束位置，当执行monitorenter指令时，当前线程将试图获取 objectref(即对象锁) 所对应的 monitor 的持有权，当 objectref 的 monitor 的进入计数器为 0，那线程可以成功取得 monitor，并将计数器值设置为 1，取锁成功。如果当前线程已经拥有 objectref 的 monitor 的持有权，那它可以重入这个 monitor (关于重入性稍后会分析)，重入时计数器的值也会加 1。倘若其他线程已经拥有 objectref 的 monitor 的所有权，那当前线程将被阻塞，直到正在执行线程执行完毕，即monitorexit指令被执行，执行线程将释放 monitor(锁)并设置计数器值为0 ，其他线程将有机会持有 monitor 。值得注意的是编译器将会确保无论方法通过何种方式完成，方法中调用过的每条 monitorenter 指令都有执行其对应 monitorexit 指令，而无论这个方法是正常结束还是异常结束。为了保证在方法异常完成时 monitorenter 和 monitorexit 指令依然可以正确配对执行，编译器会自动产生一个异常处理器，这个异常处理器声明可处理所有的异常，它的目的就是用来执行 monitorexit 指令。从字节码中也可以看出多了一个monitorexit指令，它就是异常结束时被执行的释放monitor 的指令。

###### synchronized方法底层原理

synchronized修饰的方法并没有monitorenter指令和monitorexit指令，取得代之的确实是ACC_SYNCHRONIZED标识，该标识指明了该方法是一个同步方法，JVM通过该ACC_SYNCHRONIZED访问标志来辨别一个方法是否声明为同步方法，从而执行相应的同步调用。这便是synchronized锁在同步代码块和同步方法上实现的基本原理。同时我们还必须注意到的是在Java早期版本中，synchronized属于重量级锁，效率低下，因为监视器锁（monitor）是依赖于底层的操作系统的Mutex Lock来实现的，而操作系统实现线程之间的切换时需要从用户态转换到核心态，这个状态之间的转换需要相对比较长的时间，时间成本相对较高，这也是为什么早期的synchronized效率低的原因。庆幸的是在Java 6之后Java官方对从JVM层面对synchronized较大优化，所以现在的synchronized锁效率也优化得很不错了，Java 6之后，为了减少获得锁和释放锁所带来的性能消耗，引入了轻量级锁和偏向锁锁的状态总共有四种，无锁状态、偏向锁、轻量级锁和重量级锁。随着锁的竞争，锁可以从偏向锁升级到轻量级锁，再升级的重量级锁，但是锁的升级是单向的，也就是说只能从低到高升级，不会出现锁的降级

##### 消息队列广播模式和发布/订阅模式的区别

 **广播消费:** 当使用广播消费模式时，**消息队列**会将每条消息推送给集群内所有的消费者，保证消息至少被每个消费者消费一次。

**应用场景:** 适用于消费端集群化部署，每条消息需要被集群下的每个消费者处理的场景。

* 广播模式下，**消息队列**保证消息至少被客户端消费一次，但是并不会重投消费失败的消息，因此业务方需要关注消费失败的情况。
* 广播模式下，客户端每一次重启都会从最新消息消费。客户端在被停止期间发送至服务端的消息将会被自动跳过，请谨慎选择。
* 广播模式下，每条消息都会被大量的客户端重复处理，因此推荐尽可能使用集群模式。

"发布/订阅”模式包含两种角色，分别是发布者和订阅者。订阅者可以订阅一个或若干个频道（channel），而发布者可以向指定的频道发送消息，所有订阅此频道的订阅者都会收到此消息。
      对任务  ”发布/订阅“模式很容易想到可以 使用消息队列实现的，Redis中就包含发布订阅模式的命令。


##### 生产者消费者代码实现

```java
/**
 * 商店类（Shop）:定义一个成员变量，表示第几个面包，提供生产面包和消费面包的操作；
 */
class Shop {
    private int bread = 0;
    /**
     * 生产面包
     */
    public synchronized void produceBread() {
        if (bread < 10) {
            bread++;
            System.out.println(Thread.currentThread().getName() + ":开始生产第" + bread + "个面包");
            notify(); // 唤醒消费者线程
        } else {
            try {
                wait(); // 告诉生产者等待一下
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
  
    /**
     * 消费面包
     */
    public synchronized void consumeBread() {
        if (bread > 0) {
            System.out.println(Thread.currentThread().getName() + ":开始消费第" + bread + "个面包");
            bread--;
            notify(); // 唤醒生产者线程
        } else {
            try {
                wait(); // 告诉消费者等待一下
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 生产者类（Producer）：继承Thread类，重写run()方法，调用生产面包的操作
 */
class Producer extends Thread {
    private Shop shop;
    public Producer(Shop shop) {
        this.shop = shop;
    }
    @Override
    public void run() {
        System.out.println(getName() + ":开始生产面包.....");
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            shop.produceBread();
        }
    }
}

/**
 * 消费者类（Consumer）：继承Thread类，重写run()方法，调用消费面包的操作
 */
class Consumer extends Thread {
    private Shop shop;
    public Consumer(Shop shop) {
        this.shop = shop;
    }
    @Override
    public void run() {
        System.out.println(getName() + ":开始消费面包.....");
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            shop.consumeBread();
        }
    }
}
public class BreadTest {
    public static void main(String[] args) {
        // 创建商店对象
        Shop shop = new Shop();
        // 创建生产者对象，把商店对象作为构造方法参数传入生产者对象中
        Producer p1 = new Producer(shop);
        p1.setName("生产者");
        // 创建消费者对象，把商店对象作为构造方法参数传入消费者对象中
        Consumer c1 = new Consumer(shop);
        c1.setName("消费者");
        p1.start();
        c1.start();  
    }
}
```

##### 死锁代码实现

```java
public class Main {
    static Object object1 = new Object(); //创建静态对象object1
    static Object object2 = new Object(); //创建静态对象object2

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            try {
                while (true) {
                    synchronized (object1) {
                        System.out.println(Thread.currentThread().getName() + "上锁了object1");
                        Thread.sleep(1000);
                        synchronized (object2) {
                            System.out.println(Thread.currentThread().getName() + "上锁了object2");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                while (true) {
                    synchronized (object2) {
                        System.out.println(Thread.currentThread().getName() + "锁住了object2");
                        Thread.sleep(1000);
                        synchronized (object1) {
                            System.out.println(Thread.currentThread().getName() + "锁住了object1");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
    }
}
```

##### 几种不同线程池的比较，阻塞队列的使用，拒绝策略

五种线程池：
    ExecutorService threadPool = null;
    threadPool = Executors.newCachedThreadPool();//有缓冲的线程池，线程数 JVM 控制
    threadPool = Executors.newFixedThreadPool(3);//固定大小的线程池
    threadPool = Executors.newScheduledThreadPool(2);
    threadPool = Executors.newSingleThreadExecutor();//单线程的线程池，只有一个线程在工作
    threadPool = new ThreadPoolExecutor();//默认线程池，可控制参数比较多


三类阻塞队列：
    //1 有界队列
    workQueue = new ArrayBlockingQueue<>(5);//基于数组的先进先出（FIFO）队列，支持公平锁和非公平锁，有界
    workQueue = new LinkedBlockingQueue<>();//基于链表的先进先出（FIFO）队列，默认长度为 Integer.MaxValue 有OOM危险，有界
    workQueue = new LinkedBlockingDeque(); //一个由链表结构组成的,双向阻塞队列，有界
    //2 无界队列
    workQueue = new PriorityBlockingQueue(); //支持优先级排序的无限队列，默认自然排序，可以实现 compareTo()方法指定排序规则，不能保证同优先级元素的顺序，无界。
    workQueue = new DelayQueue(); //一个使用优先级队列（PriorityQueue）实现的无界延时队列，在创建时可以指定多久才能从队列中获取当前元素。只有延时期满后才能从队列中获取元素。
    workQueue = new LinkedTransferQueue(); //一个由链表结构组成的,无界阻塞队列
    //3 同步移交队列
    workQueue = new SynchronousQueue<>();//无缓冲的等待队列，队列不存元素，每个put操作必须等待take操作，否则无法添加元素，支持公平非公平锁，无界
四种拒绝策略：
    RejectedExecutionHandler rejected = null;
    rejected = new ThreadPoolExecutor.AbortPolicy();//默认，队列满了丢任务抛出异常
    rejected = new ThreadPoolExecutor.DiscardPolicy();//队列满了丢任务不异常
    rejected = new ThreadPoolExecutor.DiscardOldestPolicy();//将最早进入队列的任务删，之后再尝试加入队列
    rejected = new ThreadPoolExecutor.CallerRunsPolicy();//如果添加到线程池失败，那么主线程会自己去执行该任务


##### Future和ListenableFuture 异步回调相关

   Future 具有局限性。在实际应用中，当需要下载大量图片或视频时，可以使用多线程去下载，提交任务下载后，可以从多个Future中获取下载结果，由于Future获取任务结果是阻塞的，所以将会依次调用Future.get()方法，这样的效率会很低。很可能第一个下载速度很慢，则会拖累整个下载速度。

    Future主要功能在于获取任务执行结果和对异步任务的控制。但如果要获取批量任务的执行结果，从上面的例子我们已经可以看到，单使用 Future 是很不方便的。其主要原因在于：一方面是没有好的方法去判断第一个完成的任务；另一方面是 Future的get方法 是阻塞的，使用不当会造成线程的浪费。第一个问题可以用 CompletionService 解决，CompletionService 提供了一个 take() 阻塞方法，用以依次获取所有已完成的任务。第二个问题可以用 Google Guava 库所提供的 ListeningExecutorService 和 ListenableFuture 来解决。除了获取批量任务执行结果时不便，Future另外一个不能做的事便是防止任务的重复提交。要做到这件事就需要 Future 最常见的一个实现类 FutureTask 了。Future只实现了异步，而没有实现回调，主线程get时会阻塞，可以轮询以便获取异步调用是否完成。

    在实际的使用中建议使用Guava ListenableFuture来实现异步非阻塞，目的就是多任务异步执行，通过回调的方方式来获取执行结果而不需轮询任务状态。


##### 在Java中wait和seelp方法的不同

对于sleep()方法，我们首先要知道该方法是属于Thread类中的。而wait()方法，则是属于[Object类](https://so.csdn.net/so/search?q=Object%E7%B1%BB&spm=1001.2101.3001.7020)中的。

sleep()方法导致了程序暂停执行指定的时间，让出cpu该其他线程，但是他的监控状态依然保持者，当指定的时间到了又会自动恢复运行状态。

在调用sleep()方法的过程中，线程不会释放对象锁。

而当调用wait()方法的时候，线程会放弃对象锁，进入等待此对象的等待锁定池，只有针对此对象调用notify()方法后本线程才进入对象锁定池准备获取对象锁进入运行状态。


##### 什么时候需要加volatile关键字？它能保证线程安全吗？

##### concurrentHashMap怎么实现？concurrenthashmap在1.8和1.7里面有什么区别

##### CountDownLatch、LinkedHashMap、AQS实现原理

##### OOM说一下？怎么排查？哪些会导致OOM?

##### 说说一致性 Hash 原理

##### 分布式一致性协议，二段、三段、TCC，优缺点

##### Java的内存模型，Java8做了什么修改

##### 如何进行JVM调优？有哪些方法？

##### 怎么理解强一致性、单调一致性和最终一致性？

##### 谈一谈一致性哈希算法。

##### **序列化方案都有哪些，说说它们的优缺点？**

序列化有对象序列化，JSON序列化，XML序列化等，像java自带序列化、kryo、protostuff、GSON、jackson、fastjson等

##### Java对象引用四个级别（强、软、弱、虚）

- 强引用（StrongReference）， 就是我们平常最基本的对象引用，如果是强引用，那回收器不会回收带有强引用的对象。即使内存不足抛出OutOfMemoryError异常也不会回收强引用对象
- 软引用（SoftReference），一个对象只有软引用，如果内存空间足够情况下垃圾回收器就不会回收它，如果内存空间不够了就会对这些只有软引用的对象进行回收。只要垃圾回收器没有回收，该软引用对象就可以继续被程序使用。
- 弱引用（WeakReference），弱引用的对象具有更短暂的生命周期，在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存。不过，由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象。
- 虚引用（PhantomReference），虚引用顾名思义就是形同虚设，虚引用并不决定对象的生命周期，如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收。

| 引用类型 | 回收时间     | 用途         |
| -------- | ------------ | ------------ |
| 强引用   | 永不回收     | 普通对象引用 |
| 软引用   | 内在不足回收 | 缓存对象     |
| 弱引用   | 垃圾回收时   | 缓存对象     |
| 虚引用   | 不确定       | 不确定       |

##### 解析XML的几种方式的原理与特点：DOM、SAX
