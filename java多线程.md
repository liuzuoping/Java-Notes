### java多线程

- [java多线程](#toc_0)
- [如何评估一台机器多少线程比较合适？ 理论指导和压测手段？](#toc_1)
- - [ThreadLocal的作用， 副作用？](#toc_2)
  - [什么叫线程安全和非安全， 对我们编程有什么影响？](#toc_3)
  - [如何在高并发场景下，安全的创建一个单例模式](#toc_4)
  - [Java中如何实现多线程?](#toc_5)
  - [volatile的原理，作用，能代替锁么](#toc_6)
  - [Lock与Synchronized的区别](#toc_7)
  - [synchronized是java中的一个关键字，也就是说是Java语言内置的特性。那么为什么会出现Lock呢？](#toc_8)
  - [Synchronized有哪几种用法](#toc_9)
  - [解释以下名词：重排序，自旋锁，偏向锁，轻量级锁，可重入锁，公平锁，非公平锁，乐观锁，悲观锁](#toc_10)
  - [锁有哪几种？](#toc_11)
  - [为什么要使用线程池](#toc_12)
  - [Java中如何获取到线程dump文件](#toc_13)
  - [一个线程如果出现了运行时异常会怎么样](#toc_14)
  - [如何在两个线程之间共享数据（线程同步）](#toc_15)
  - [如何查找哪个线程使用CPU最长](#toc_16)
  - [什么是CAS什么是AQS](#toc_17)
  - [ConcurrentHashMap的并发度是什么](#toc_18)
  - [run()和start()方法区别](#toc_19)
  - [如何控制某个方法允许并发访问线程的个数？](#toc_20)
  - [多个线程同时读写，读线程的数量远远大于写线程，你认为应该如何解决并发的问题？你会选择加什么样的锁？](#toc_21)
  - [线程池内的线程如果全部忙，提交一个新的任务，会发生什什么？队列全部塞满了之后，还是忙，再提交会发生什么？](#toc_22)
  - [剖析Disruptor:为什么会这么快？](#toc_23)
  - [怎么中断一个线程？如何保证中断业务不影响？](#toc_24)
  - [怎么终止一个线程？如何优雅地终止线程？](#toc_25)
  - [怎么控制同一时间只有3个线程运行？](#toc_26)
  - [Jdk中排查多线程问题用什么命令？](#toc_27)
  - [Java中用到了什么线程调度算法？](#toc_28)
  - [Thread.sleep(0)的作用是什么？](#toc_29)
  - [sleep和wait的区别](#toc_30)
  - [Hashtable的size()方法为什么要做同步？](#toc_31)
  - [为什么wait/notify/notifyAll这些方法不在thread类里面？](#toc_32)
  - [Runnable接口和Callable接口的区别](#toc_33)
  - [CyclicBarrier和CountDownLatch的区别](#toc_34)
  - [Java中如何获取到线程dump文件](#toc_35)

#### 如何评估一台机器多少线程比较合适？ 理论指导和压测手段？

线程的执行是由CPU进行调度的，一个CPU在同一时刻只会执行一个线程。使用多线程的主要目的：**降低延迟，提高吞吐量**

最佳线程数目 = （（线程等待时间+线程CPU时间）/线程CPU时间 ）* CPU数目

对于CPU密集型的计算场景，理论上线程的数量=CPU核数就是最合适的。不过在工程上，线程的数量一般会设置为CPU核数+1，这样的话，当线程因为偶尔的内存页失效或其他原因导致阻塞时，这个额外的线程可以顶上，从而保证CPU的利用率

对于I/O密集型的计算场景，最佳线程数=1+(I/O耗时/CPU耗时)，针对多核CPU，我目前见过两种比较合理的公式：

最佳线程数=CPU核数×[1+(I/O耗时/CPU耗时)]

线程数=CPU核数×目标CPU利用率×(1+平均等待时间/平均工作时间)

#### ThreadLocal的作用， 副作用？

+ [ThreadLocal主要用来存储当前上下文的变量信息，他可以保障存储进去的信息只能被当前的线程读取到，并且线程之间不会受到影响。ThreadLocal为变量在每个线程都创建了一个副本，那么每个线程可以访问自己的内部的副本变量。](#toc_0)

- [ThreadLocal中的set（）、get（）方法都是调用了ThreadLocalMap中的set（）、get（）方法。最终的变量是放在了当前线程的 ThreadLocalMap 中，并不是存在 ThreadLocal 上，ThreadLocal 可以理解为只是ThreadLocalMap的封装，传递了变量值。ThreadLocalMap的map,这个map就是用来存储与这个线程绑定的变量,map的key就是ThreadLocal对象,value就是线程正在执行的任务中的某个变量的包装类Entry。](#toc_1)
- [内存泄露问题](#toc_0)——[ThreadLocalMap中的key是弱引用，而value是强引用。所以，如果ThreadLocal没有被外部强引用的情况下，在垃圾回收时，key会被清空掉，而value不会。ThreadLocal中就会出现key为null的Entry。假如我们不采取措施，value永远不会被GC回收，这个时候就可能会产生内存泄漏问题。](#toc_0)
- [在使用ThreadLocal对象,尽量使用static,不然会使线程的ThreadLocalMap产生太多Entry,从而造成内存泄露。](#toc_1)

```java
//项目使用实例
public class DynamicDataSourceContextHolder{
    /**
     * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
     *  所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDataSourceType(String dsType){
        log.info("切换到{}数据源", dsType);
        CONTEXT_HOLDER.set(dsType);
    }
    public static String getDataSourceType(){
        return CONTEXT_HOLDER.get();
    }
    public static void clearDataSourceType(){
        CONTEXT_HOLDER.remove();
    }
}
```

#### 什么叫线程安全和非安全， 对我们编程有什么影响？

- [线程安全：是多线程访问时，采用加锁机制，当一个线程访问该类的某个数据时，进行保护，其他线程不能进行访问直到该线程读取完，其他线程才可使用。不会出现数据不一致或者数据污染。](#toc_0)
- [非线程安全：是多线程访问时，不提供数据访问保护，有可能出现多个线程先后更改数据造成所得到的数据是脏数据。所得数据可能不一致](#toc_1)
- [区别：非线程安全是指多线程操作同一个对象可能会出现问题。而线程安全则是多线程操作同一个对象不会有问题](#toc_2)

#### HashMap 和 ConcurrentHashMap的区别

- [HashMap不支持并发操作，没有同步方法，ConcurrentHashMap支持并发操作，通过继承 ReentrantLock（JDK1.7重入锁）/CAS和synchronized(JDK1.8内置锁)来进行加锁（分段锁），每次需要加锁的操作锁住的是一个 segment，这样只要保证每个 Segment 是线程安全的，也就实现了全局的线程安全。](#toc_0)
- [JDK1.8之前HashMap的结构为数组+链表，JDK1.8之后HashMap的结构为数组+链表+红黑树；JDK1.8之前ConcurrentHashMap的结构为segment数组+数组+链表，JDK1.8之后ConcurrentHashMap的结构为数组+链表+红黑树。](#toc_0)
- [区别：非线程安全是指多线程操作同一个对象可能会出现问题。而线程安全则是多线程操作同一个对象不会有问题](#toc_2)

#### 如何在高并发场景下，安全的创建一个单例模式

- [为了达到线程安全，又能提高代码执行效率，我们这里可以采用DCL的双检查锁机制来完成，这里在声明变量时使用了volatile关键字来保证其线程间的可见性；在同步代码块中使用二次检查，以保证其不被重复实例化。集合其二者，这种实现方式既保证了其高效性，也保证了其线程安全性。](#toc_2)

```java
public class MySingleton {
	//使用volatile关键字保其可见性
	volatile private static MySingleton instance = null;
	private MySingleton(){} 
	public static MySingleton getInstance() {
		try {  
		    if(instance != null){//懒汉式 
		    }else{
			//创建实例之前可能会有一些准备性的耗时工作 
			Thread.sleep(300);
			synchronized (MySingleton.class) {
				if(instance == null){//二次检查
					instance = new MySingleton();
				}
			 }
		    } 
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
		return instance;
	}
}
```

#### Java中如何实现多线程?

- 继承Thread类
- 实现Runnable接口
- 实现Callable接口通过FutureTask包装器来创建Thread线程
- 使用ExecutorService、Callable、Future实现有返回结果的多线程

如果想让线程池执行任务的话需要实现的Runnable接口或Callable接口。 Runnable接口或Callable接口实现类都可以被ThreadPoolExecutor或ScheduledThreadPoolExecutor执行。两者的区别在于 Runnable 接口不会返回结果但是 Callable 接口可以返回结果。

#### volatile的原理，作用，能代替锁么

1. volatile 关键字的作用 保证内存的可见性 防止指令重排. 即每次读取到volatile变量，一定是最新的数据
2. volatile 并不能保证线程安全性。而 synchronized 则可实现线程的安全性

#### Lock与Synchronized的区别

1. 首先synchronized是java内置关键字，在jvm层面，Lock是个java类；
2. synchronized无法判断是否获取锁的状态，Lock可以判断是否获取到锁；
3. synchronized会自动释放锁(a 线程执行完同步代码会释放锁 ；b 线程执行过程中发生异常会释放锁)，Lock需在finally中手工释放锁（unlock()方法释放锁），否则容易造成线程死锁；
4. 用synchronized关键字的两个线程1和线程2，如果当前线程1获得锁，线程2线程等待。如果线程1阻塞，线程2则会一直等待下去，而Lock锁就不一定会等待下去，如果尝试获取不到锁，线程可以不用一直等待就结束了；
5. synchronized的锁可重入、不可中断、非公平，而Lock锁可重入、可判断、可公平（两者皆可）
6. Lock锁适合大量同步的代码的同步问题，synchronized锁适合代码少量的同步问题。

#### synchronized是java中的一个关键字，也就是说是Java语言内置的特性。那么为什么会出现Lock呢？

- [因为synchronized获取不到锁的时候会阻塞，并且阻塞不可被打断的特性会导致可能会产生死锁的问题，为了解决这个问题，Java就提供了Lock锁的实现，从主动放弃获取锁或者被动放弃获取锁的方式，解决一直阻塞可能产生的死锁问题。](#toc_2)

```java
void lockInterruptibly() throws InterruptedException;
boolean tryLock();
boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
```

- [第一个方法阻塞可以被打断的加锁方法，这是一个被动放弃获取锁的方法。就是说其它线程主动当调用阻塞线程的interrupt方法之后，该阻塞线程就会放弃继续获取锁，然后抛出InterruptedException 异常，所以对于使用方来说，只要捕获这个异常，就能保证线程的代码继续执行了。](#toc_2)
- [第二个方法是尝试加锁，加锁失败后就放弃加锁，不会阻塞，直接返回false。](#toc_0)
- [第三个方法就是尝试加锁失败后在阻塞的一定时间之后，如果还没有获取到锁，那么就放弃获取锁。](#toc_0)
- [Lock接口的实现有很多，但基本上都是基于Java的AQS的实现来完成的。AQS其实主要是维护了一个锁的状态字段state和一个双向链表。当线程获取锁失败之后，就会加入到双向链表中，然后阻塞或者不阻塞，这得看具体的方法实现。](#toc_0)

#### Synchronized有哪几种用法

[synchronized 用 3 种用法](#toc_0):

- [用它可以来修饰普通方法、](#toc_0)
- [用它可以来修饰](#toc_0)静态方法
- [用它可以来修饰](#toc_0)代码块
- [其中最常用的是修饰代码块，而修饰代码块时需要指定一个加锁对象，这个加锁对象通常使用 this 或 xxx.class 来表示，当使用 this 时，表示使用当前对象来加锁，而使用 class 时，表示表示使用某个类（非类对象实例）来加锁，它是全局生效的。](#toc_0)

#### 解释以下名词：重排序，自旋锁，偏向锁，轻量级锁，可重入锁，公平锁，非公平锁，乐观锁，悲观锁

- [https://zhuanlan.zhihu.com/p/71156910](#toc_0)

#### 锁有哪几种？

- 公平锁/非公平锁

公平锁是指多个线程按照申请锁的顺序来获取锁; 非公平锁是指多个线程获取锁的顺序并不是按照申请锁的顺序,有可能，会造成优先级反转或者饥饿现象。

对于Java ReentrantLock而言，通过构造函数指定该锁是否是公平锁，默认是非公平锁。非公平锁的优点在于吞吐量比公平锁大。对于Synchronized而言，也是一种非公平锁。

- 可重入锁

可重入锁又名递归锁，是指在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁。

- 独享锁/共享锁

独享锁：一次只能被一个线程所访问

共享锁：线程可以被多个线程所持有。

ReadWriteLock 读锁是共享锁，写锁是独享锁。对于Java ReentrantLock而言，其是独享锁。对于Synchronized而言，当然是独享锁。

- 互斥锁/读写锁
- 乐观锁/悲观锁

乐观锁：对于一个数据的操作并发，是不会发生修改的。在更新数据的时候，会尝试采用更新，不断重入的方式，更新数据。

悲观锁：对于同一个数据的并发操作，是一定会发生修改的。因此对于同一个数据的并发操作，悲观锁采用加锁的形式。悲观锁认为，不加锁的操作一定会出问题，

- 分段锁

分段锁，其思想就是让锁的粒度变小。分段锁其实是一种锁的设计，并不是具体的一种锁，对于ConcurrentHashMap而言，其并发的实现就是通过分段锁的形式来实现高效的并发操作。

- 偏向锁/轻量级锁/重量级锁
  这三种锁是指锁的状态，并且是针对Synchronized,这三种锁的状态是通过对象监视器在对象头中的字段来表明的。

偏向锁是指一段同步代码一直被一个线程所访问，那么该线程会自动获取锁。降低获取锁的代价。在Java 5通过引入锁升级的机制来实现高效Synchronized。

轻量级锁 是指当锁是偏向锁的时候，被另一个线程所访问，偏向锁就会升级为轻量级锁，其他线程会通过自旋的形式尝试获取锁，不会阻塞，提高性能。

重量级锁是指当锁为轻量级锁的时候，另一个线程虽然是自旋，但自旋不会一直持续下去，当自旋一定次数的时候，还没有获取到锁，就会进入阻塞，该锁膨胀为重量级锁。重量级锁会让其他申请的线程进入阻塞，性能降低。

- 自旋锁

在Java中，自旋锁是指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁，这样的好处是减少线程上下文切换的消耗，缺点是循环会消耗CPU。

#### 为什么要使用线程池

避免频繁地创建和销毁线程，达到线程对象的重用。另外，使用线程池还可以灵活地控制并发的数目。

#### Java中如何获取到线程dump文件

1. 获取到线程的pid，可以通过使用jps命令，在Linux环境下还可以使用ps -ef | grep java
2. 通过使用jstack pid命令

#### 一个线程如果出现了运行时异常会怎么样

如果异常没捕获的话，线程就停止执行了。如果这个线程持有某个某个对象的监视器，那么这个对象监视器会被立即释放

#### 如何在两个线程之间共享数据（线程同步）

通过在线程之间共享对象就可以了，然后通过wait/notify/notifyAll、await/signal/signalAll进行唤起和等待

阻塞队列BlockingQueue就是为线程之间共享数据而设计的

#### 如何查找哪个线程使用CPU最长

1. 获取项目的pid，jps或者ps -ef | grep java
2. top -H -p pid，顺序不能改变

#### 什么是CAS什么是AQS

- CAS，全称为Compare and Swap，即比较-替换。

假设有三个操作数：内存值V、旧的预期值A、要修改的值B，当且仅当预期值A和内存值V相同时，才会将内存值修改为B并返回true，否则什么都不做并返回false。当然CAS一定要volatile变量配合，这样才能保证每次拿到的变量是主内存中最新的那个值，否则旧的预期值A对某条线程来说，永远是一个不会变的值A，只要某次CAS操作失败，永远都不可能成功。

- AQS是一个用来构建锁和同步器的框架，使用AQS能简单且高效地构造出应用广泛的大量的同步器。

如果说java.util.concurrent的基础是CAS的话，那么AQS就是整个Java并发包的核心了，ReentrantLock、CountDownLatch、Semaphore等等都用到了它。

AQS核心思想是，如果被请求的共享资源空闲，则将当前请求资源的线程设置为有效的工作线程，并且将共享资源设置为锁定状态。如果被请求的共享资源被占用，那么就需要一套线程阻塞等待以及被唤醒时锁分配的机制，这个机制AQS是用CLH队列锁实现的，即将暂时获取不到锁的线程加入到队列中。

AQS定义了对双向队列所有的操作，而只开放了tryLock和tryRelease方法给开发者使用，开发者可以根据自己的实现重写tryLock和tryRelease方法，以实现自己的并发功能。

#### ConcurrentHashMap的并发度是什么

ConcurrentHashMap的并发度就是segment的大小，默认为16，这意味着最多同时可以有16条线程操作ConcurrentHashMap

#### run()和start()方法区别

- [通过调用Thread类的start()方法来启动一个线程，这时此线程是处于就绪状态，并没有运行。start是真正调用底层的代码来创建thread，并从底层调用run（）方法，执行我们自定义的任务。](#toc_0)
- [而如果直接用Run方法，这只是调用一个方法而已，程序中依然只有主线程--这一个线程，其程序执行路径还是只有一条，这样就没有达到写线程的目的。](#toc_0)
- [run()只是在当前线程中执行任务，而start才是真正生成thread，并放在cpu中调度。](#toc_0)

#### 如何控制某个方法允许并发访问线程的个数？

- [Semaphore类是一个计数信号量，必须由获取它的线程释放，通常可以用于限制并发访问的线程数目。](#toc_0)
- [获得Semaphore对象](#toc_0),public Semaphore(int permits, [boolean](https://so.csdn.net/so/search?q=boolean&spm=1001.2101.3001.7020) fair),permits：初始化可用的许可个数,fair：若该信号量保证在使用时按[FIFO](https://so.csdn.net/so/search?q=FIFO&spm=1001.2101.3001.7020)(先进先出)的顺序，则为true，否则为false

#### 多个线程同时读写，读线程的数量远远大于写线程，你认为应该如何解决并发的问题？你会选择加什么样的锁？

- [使用volatile关键字（一写多读）](#toc_0)
- [使用ReadWriteLock读写锁](#toc_0)[（多写多读）](#toc_0)
- [使用写时复制容器CopyOnWrite系列](#toc_0)

#### 线程池内的线程如果全部忙，提交一个新的任务，会发生什什么？队列全部塞满了之后，还是忙，再提交会发生什么？

- [如果使用的是无界队列 LinkedBlockingQueue，也就是无界队列的话，没关系，继续添加任务到阻塞队列中等待执行，因为 LinkedBlockingQueue 可以近乎认为是一个无穷大的队列，可以无限存放任务](#toc_0)
- [如果使用的是有界队列比如 ArrayBlockingQueue，任务首先会被添加到ArrayBlockingQueue中，ArrayBlockingQueue 满了，会根据maximumPoolSize 的值增加线程数量，如果增加了线程数量还是处理不过来，ArrayBlockingQueue 继续满，那么则会使用拒绝策略RejectedExecutionHandler处理满了的任务，默认是 AbortPolicy](#toc_0)

#### 剖析Disruptor:为什么会这么快？

[Disruptor相对于传统方式的优点](http://ifeve.com/locks-are-bad/)：

- [没有竞争=没有锁=非常快。在生产者/消费者模式下，disruptor号称“无锁并行框架”（要知道BlockingQueue是利用了Lock锁机制来实现的）](#toc_0)
- [所有访问者都记录自己的序号的实现方式，允许多个生产者与多个消费者共享相同的数据结构。](#toc_0)
- [在每个对象中都能跟踪序列号（ring buffer，claim Strategy，生产者和消费者），加上神奇的](#toc_0)[cache line padding](http://code.google.com/p/disruptor/source/browse/trunk/code/src/main/com/lmax/disruptor/RingBuffer.java)，就意味着没有为伪共享和非预期的竞争。

#### 怎么中断一个线程？如何保证中断业务不影响？

- [每个线程都有一个与之相关联的 Boolean 属性，用于表示线程的 *中断状态（interrupted status）* 。中断状态初始时为 false；当另一个线程通过调用 `Thread.interrupt()` 中断一个线程](#toc_0)
- [可以用 Java 平台提供的协作中断机制来构造灵活的取消策略。各活动可以自行决定它们是可取消的还是不可取消的，以及如何对中断作出响应，如果立即返回会危害应用程序完整性的话，它们还可以推迟中断。即使您想在代码中完全忽略中断，也应该确保在捕捉到 InterruptedException但是没有重新抛出它的情况下，恢复中断状态，以免调用它的代码无法获知中断的发生。](#toc_0)

#### 怎么终止一个线程？如何优雅地终止线程？

- 终止线程的stop方法，通过stop方法可以很快速、方便地终止一个线程
- 优雅终止需要添加一个变量，判断这个变量在某个值的时候就退出循环，这时候每个循环为一个整合不被强行终止就不会影响单个业务的执行结果。

![img](https://cdn.nlark.com/yuque/0/2019/png/406432/1565342631777-789483a5-56fb-41f6-84d7-fe342ef84812.png)￼

#### 怎么控制同一时间只有3个线程运行？

- [同时控制两个线程进入临界区，一种方式可以考虑用信号量。](#toc_0)
- [考虑生产者、消费者模型。想要进入临界区的线程先在一个等待队列中等待，然后由消费者每次消费两个。这种实现方式，类似于实现一个线程池，所以也可以考虑实现一个 ThreadPool类，然后再实现一个调度器类，最后实现一个每次选择三个线程执行的调度算法。](#toc_0)

#### Jdk中排查多线程问题用什么命令？

jstack-作用：生成VM当前时刻线程的快照(threaddump,即当前进程中所有线程的信息)
目的：帮助定位程序问题出现的原因，如长时间停顿、CPU占用率过高等

#### Java中用到了什么线程调度算法？

- [一、优先调度算法——1、先来先服务调度算法（FCFS），2、短作业(进程)优先调度算法](#toc_0)
- [二、高优先权优先调度算法——1、非抢占式优先权算法，2、抢占式优先权调度算法，3、高响应比优先调度算法](#toc_0)
- [三、基于时间片的轮转调度算法——1、时间片轮转法，2、多级反馈队列调度算法](#toc_0)

#### Thread.sleep(0)的作用是什么？

Thread.Sleep(0)作用，就是“触发操作系统立刻重新进行一次CPU竞争”。竞争的结果也许是当前线程仍然获得CPU控制权，也许会换成别的线程获得CPU控制权。这也是我们在大循环里面经常会写一句Thread.Sleep(0) ，因为这样就给了其他线程比如Paint线程获得CPU控制权的权力，这样界面就不会假死在那里。

#### sleep和wait的区别

- 使用上

sleep是Thread线程类的方法，而wait是Object顶级类的方法,

sleep可以在任何地方使用，而wait只能在同步方法或者同步块中使用

- CPU及资源锁释放

sleep,wait调用后都会暂停当前线程并让出cpu的执行时间，但不同的是sleep不会释放当前持有的对象的锁资源，到时间后会继续执行，而wait会放弃所有锁并需要notify/notifyAll后重新获取到对象锁资源后才能继续执行。

- 异常捕获

sleep需要捕获或者抛出异常，而wait/notify/notifyAll不需要

#### Hashtable的size()方法为什么要做同步？

同一时间只能有一条线程执行固定类的同步方法，但是对于类的非同步方法，可以多条线程同时访问。所以，这样就有问题了，可能线程 A 在执行 Hashtable 的 put方法添加数据，线程 B 则可以正常调用 size()方法读取 Hashtable 中当前元素的个数，那读取到的值可能不是最新的，可能线程 A 添加了完了数据，但是没有对size++，线程 B 就已经读取 size了，那么对于线程 B 来说读取到的 size 一定是不准确的。而给 size()方法加了同步之后，意味着线程 B 调用 size()方法只有在线程 A调用 put 方法完毕之后才可以调用，这样就保证了线程安全性CPU 执行代码，执行的不是 Java 代码，这点很关键。

#### 为什么wait/notify/notifyAll这些方法不在thread类里面？

- [wait、notify、notifyAll方法只能在同步代码块中使用，在非同步代码块中会导致异常，因为只有在同步代码块中才会涉及到锁的概念，在非并发环境下尝试操作锁会导致失败。可以使用wait、notify来控制当前占用资源的线程进入阻塞队列与唤醒进入就绪队列，也就是说，这两个方法实际上实现的时线程之间的通信机制，用来通知线程的阻塞与唤醒。](#toc_0)
- [线程为了进入临界区（也就是同步块内），需要获得锁并等待锁可用，它们并不知道也不需要知道哪些线程持有锁，它们只需要知道当前资源是否被占用，是否可以获得锁，所以锁的持有状态应该由同步监视器来获取，而不是线程本身](#toc_0)。

#### Runnable接口和Callable接口的区别

- [最大的区别，runnable没有返回值，Callable接口支持返回执行结果，此时需要调用FutureTask.get()方法实现，此方法会阻塞主线程直到获取‘将来’结果；当不调用此方法时，主线程不会阻塞！](#toc_0)
- [callable接口实现类中的run方法允许异常向上抛出，可以在内部处理，try catch，但是runnable接口实现类中run方法的异常必须在内部处理，不能抛出](#toc_0)。

#### CyclicBarrier和CountDownLatch的区别

CountDownLatch													CyclicBarrier
减计数方式														加计数方式
计算为0时释放所有等待的线程											计数达到指定值时释放所有等待线程
计数为0时，无法重置												计数达到指定值时，计数置为0重新开始
调用countDown()方法计数减一，调用await()方法只进行阻塞，对计数没任何影响	调用await()方法计数加1，若加1后的值不等于构造方法的值，则线程阻塞
不可重复利用														可重复利用
