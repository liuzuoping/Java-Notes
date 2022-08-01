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
//若依项目使用实例
public class DynamicDataSourceContextHolder{
    public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    /**
     * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
     *  所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源的变量
     */
    public static void setDataSourceType(String dsType){
        log.info("切换到{}数据源", dsType);
        CONTEXT_HOLDER.set(dsType);
    }

    /**
     * 获得数据源的变量
     */
    public static String getDataSourceType(){
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清空数据源变量
     */
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

为了达到线程安全，又能提高代码执行效率，我们这里可以采用DCL的双检查锁机制来完成，这里在声明变量时使用了volatile关键字来保证其线程间的可见性；在同步代码块中使用二次检查，以保证其不被重复实例化。集合其二者，这种实现方式既保证了其高效性，也保证了其线程安全性。

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

#### Synchronized有哪几种用法

#### 解释以下名词：重排序，自旋锁，偏向锁，轻量级锁，可重入锁，公平锁，非公平锁，乐观锁，悲观锁

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

#### 如何控制某个方法允许并发访问线程的个数？

- 按并发控制，分布式场景
- 按时间控制

#### 多个线程同时读写，读线程的数量远远大于写线程，你认为应该如何解决并发的问题？你会选择加什么样的锁？

#### 线程池内的线程如果全部忙，提交一个新的任务，会发生什什么？队列全部塞满了之后，还是忙，再提交会发生什么？

#### 剖析Disruptor:为什么会这么快？

[剖析Disruptor:为什么会这么快？](http://ifeve.com/locks-are-bad/)

#### 怎么中断一个线程？如何保证中断业务不影响？

#### 怎么终止一个线程？如何优雅地终止线程？

- 终止线程的stop方法，通过stop方法可以很快速、方便地终止一个线程
- 优雅终止需要添加一个变量，判断这个变量在某个值的时候就退出循环，这时候每个循环为一个整合不被强行终止就不会影响单个业务的执行结果。

![img](https://cdn.nlark.com/yuque/0/2019/png/406432/1565342631777-789483a5-56fb-41f6-84d7-fe342ef84812.png)￼

#### 怎么控制同一时间只有3个线程运行？

#### Jdk中排查多线程问题用什么命令？

#### Java中用到了什么线程调度算法？

#### Thread.sleep(0)的作用是什么？

#### sleep和wait的区别

- 使用上

sleep是Thread线程类的方法，而wait是Object顶级类的方法,

sleep可以在任何地方使用，而wait只能在同步方法或者同步块中使用

- CPU及资源锁释放

sleep,wait调用后都会暂停当前线程并让出cpu的执行时间，但不同的是sleep不会释放当前持有的对象的锁资源，到时间后会继续执行，而wait会放弃所有锁并需要notify/notifyAll后重新获取到对象锁资源后才能继续执行。

- 异常捕获

sleep需要捕获或者抛出异常，而wait/notify/notifyAll不需要

#### Hashtable的size()方法为什么要做同步？

#### 为什么wait/notify/notifyAll这些方法不在thread类里面？

#### Runnable接口和Callable接口的区别

#### CyclicBarrier和CountDownLatch的区别

#### Java中如何获取到线程dump文件
