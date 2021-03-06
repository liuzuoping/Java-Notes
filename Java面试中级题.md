#### 中级题

##### 哪些集合类是线程安全的

1. Concurrenthashmap的实现，1.7和1.8的实现

##### Arrays.sort的实现

##### 什么时候使用CopyOnArrayList

##### CAS和AQS的实现原理

##### reentrantlock的实现和Synchronied的区别

##### 双亲委派模型

##### 反射机制：反射动态擦除泛型、反射动态调用方法等

##### 动态绑定：父类引用指向子类对象

##### JVM内存管理机制：有哪些区域，每个区域做了什么

##### JVM垃圾回收机制：垃圾回收算法 垃圾回收器 垃圾回收策略

##### jvm参数的设置和jvm调优

##### 什么情况产生年轻代内存溢出、什么情况产生年老代内存溢出

##### Redis和memcached

1. 什么时候选择redis，什么时候选择memcached
2. 内存模型和存储策略是什么样的 

##### java中bio nio aio的区别和联系

##### 为什么bio是阻塞的 nio是非阻塞的 nio是模型是什么样的

- BIO就是指IO，即传统的Blocking IO,即同步并阻塞的IO。依赖于ServerSocket实现，即一个请求对应一个线程，如果线程数不够连接则会等待空余线程或者拒绝连接。
- NIO，即New IO或者Non-Blocking IO,即同步不阻塞的IO。定义在java.nio包下面。相比于传统的BIO,NIO 提供了高速的面向快的I/O,它加入了Buffer、Channel、Selector等概念。它是基于事件驱动的，采用了Reactor模式，它使用一个线程管理所有的socket通道，即客户端发送的连接请求都会注册到多路复用器上，多路复用器轮询到连接有I/O请求时才启动一个线程进行处理。它的特点是要不断主动地去询问数据有没有处理完，一般只适用于连接数目较大但连接时间短的应用，如聊天应用等。
- AIO，新的IO2.0，即NIO2.0，jdk1.7开始应用，叫做异步不阻塞的IO。AIO引入异常通道的概念，采用了Proactor模式，简化了程序编写，一个有效的请求才启动一个线程，它的特点是先由操作系统完成后才通知服务端程序启动线程去处理，一般适用于连接数较多且连接时间长的应用。

几种IO的综合对比：

|               | BIO    | NIO      | AIO    |
| ------------- | ------ | -------- | ------ |
| 客户端:线程数 | 1:1    | M:1      | M:0    |
| 阻塞类型      | 阻塞   | 非阻塞   | 非阻塞 |
| 同步类型      | 同步   | 同步     | 异步   |
| 编程难度      | 简单   | 非常复杂 | 复杂   |
| 调试难度      | 简单   | 复杂     | 复杂   |
| 可靠性        | 非常差 | 高       | 高     |
| 吞吐量        | 低     | 高       | 高     |

##### ClassNotFoundException , NoClassDefFoundError , ClassCastException

- ClassNotFoundException : 找不到类异常,继承了Exception，即在当前classpath路径下找不到这个类,这个异常一般发生在显示加载类的时候
- NoClassDefFoundError : 虚拟机隐式加载类出现的异常, 异常继承了Error类，一般发生在引用的类不存在，即类、方法或者属性引用了某个类或者接口，如果目标引用不存在就会抛出这个异常。

```plain
//MyDomBuilder继承了DOMBuilder，
//如果把DOMBuilder所属的jar包范围设置为provided，
//即运行时找不到DOMBuilder类就会报错。
import org.jdom2.input.DOMBuilder;
public class MyDomBuilder extends DOMBuilder{
}
public static void main(String[] args){
    MyDomBuilder builder = new MyDomBuilder();
}
```

- ClassCastException： 继承了运行时异常RuntimeException， 类转换异常，这个错误一般发生在一个对象强制转换类型的时候，如将一个String强制转换成Integer就会报这个错。

##### Java io的整体架构和使用的设计模式

##### 泛型与类型擦除

- Java语言中的泛型机制其实就是一颗语法糖，相较与C++、C#相比，其泛型实现实在是不那么优雅。

```plain
/**
 * 在源代码中存在泛型
 */
public static void main(String[] args) {
    Map<String,String> map = new HashMap<String,String>();
    map.put("hello","你好");
    String hello = map.get("hello");
    System.out.println(hello);
}
// 当上述源代码被编译为class文件后，泛型被擦除且引入强制类型转换
public static void main(String[] args) {
    HashMap map = new HashMap();
    //类型擦除
    map.put("hello", "你好");
    String hello = (String)map.get("hello");
    //强制转换
    System.out.println(hello);
}
```

#### Spring相关

##### Spring容器的初始化过程

１、ResourceLoader从存储介质中加载Spring配置信息，并使用Resource表示这个配置文件的资源；

２、BeanDefinitionReader读取Resource所指向的配置文件资源，然后解析配置文件。配置文件中每一个解析成一个BeanDefinition对象，并保存到BeanDefinitionRegistry中；

３、容器扫描BeanDefinitionRegistry中的BeanDefinition，使用Java的反射机制自动识别出Bean工厂后处理后器（实现BeanFactoryPostProcessor接口）的Bean，然后调用这些Bean工厂后处理器对BeanDefinitionRegistry中的BeanDefinition进行加工处理。主要完成以下两项工作：

1）对使用到占位符的元素标签进行解析，得到最终的配置值，这意味对一些半成品式的BeanDefinition对象进行加工处理并得到成品的BeanDefinition对象；

2）对BeanDefinitionRegistry中的BeanDefinition进行扫描，通过Java反射机制找出所有属性编辑器的Bean（实现java.beans.PropertyEditor接口的Bean），并自动将它们注册到Spring容器的属性编辑器注册表中（PropertyEditorRegistry）；

4．Spring容器从BeanDefinitionRegistry中取出加工后的BeanDefinition，并调用InstantiationStrategy着手进行Bean实例化的工作；

5．在实例化Bean时，Spring容器使用BeanWrapper对Bean进行封装，BeanWrapper提供了很多以Java反射机制操作Bean的方法，它将结合该Bean的BeanDefinition以及容器中属性编辑器，完成Bean属性的设置工作；

6．利用容器中注册的Bean后处理器（实现BeanPostProcessor接口的Bean）对已经完成属性设置工作的Bean进行后续加工，直接装配出一个准备就绪的Bean。Spring框架中用到了哪些设计模式？

##### Spring框架在实现时运用的设计模式

1. 简单工厂

   Spring中的BeanFactory就是简单工厂模式的体现，根据传入一个唯一的标识来获得Bean对象，但是否是在传入参数后创建还是传入参数前创建这个要根据具体情况来定。

2. 工厂方法

   实现了FactoryBean接口的bean是一类叫做factory的bean。其特点是，spring会在使用getBean()调用获得该bean时，会自动调用该bean的getObject()方法，所以返回的不是factory这个bean，而是这个bean的getOjbect()方法的返回值。

3. 单例模式

   Spring依赖注入Bean实例默认是单例的。Spring的依赖注入（包括lazy-init方式）都是发生在AbstractBeanFactory的getBean里。getBean的doGetBean方法调用getSingleton进行bean的创建。

4. 适配器模式

   SpringMVC中的适配器HandlerAdatper，它会根据Handler规则执行不同的Handler。即DispatcherServlet根据HandlerMapping返回的handler，向HandlerAdatper发起请求处理Handler。HandlerAdapter根据规则找到对应的Handler并让其执行，执行完毕后Handler会向HandlerAdapter返回一个ModelAndView，最后由HandlerAdapter向DispatchServelet返回一个ModelAndView。

5. 装饰器模式

   Spring中用到的装饰器模式在类名上有两种表现：一种是类名中含有Wrapper，另一种是类名中含有Decorator。

6. 代理模式

   AOP底层就是动态代理模式的实现。即：切面在应用运行的时刻被织入。一般情况下，在织入切面时，AOP容器会为目标对象创建动态的创建一个代理对象。SpringAOP就是以这种方式织入切面的。

7. 观察者模式

   Spring的事件驱动模型使用的是观察者模式，Spring中Observer模式常用的地方是listener的实现。

8. 策略模式

   Spring框架的资源访问Resource接口。该接口提供了更强的资源访问能力，Spring 框架本身大量使用了 Resource 接口来访问底层资源。Resource 接口是具体资源访问策略的抽象，也是所有资源访问类所实现的接口。

9. 模板方法模式

   Spring模板方法模式的实质，是模板方法模式和回调模式的结合，是Template Method不需要继承的另一种实现方式。Spring几乎所有的外接扩展都采用这种模式。

##### Spring MVC的父子容器

如果spring.xml和spring-mvc.xml定义了相同id的bean会怎样？假设id=test。

1.首先Spring 初始化，Spring IOC 容器中生成一个id为test bean实例。

2.Spring MVC开始初始化，生成一个id为test bean实例。

此时，两个容器分别有一个相同id的bean。那用起来会不会混淆？

答案是不会。

当你在Spring MVC业务逻辑中使用该bean时，Spring MVC会直接返回自己容器的bean。

当你在Spring业务逻辑中使用该bean时，因为子容器的bean对父亲是不可见的，因此会直接返回Spring容器中的bean。

虽然上面的写法不会造成问题。但是在实际使用过程中，建议大家都把bean定义都写在spring.xml文件中。

因为使用单例bean的初衷是在IOC容器中只存在一个实例，如果两个配置文件都定义，会产生两个相同的实例，造成资源的浪费，也容易在某些场景下引发缺陷。

1. **应用中可以包含多个IOC容器。**
2. **DispatcherServlet的创建的子容器主要包含Controller、view resolvers等和web相关的一些bean。**
3. **父容器root WebApplicationContex主要包含包含一些基础的bean，比如一些需要在多个servlet共享的dao、service等bean。**
4. **如果在子容器中找不到bean的时候可以去父容器查找bean。**

##### Cglib和jdk动态代理的区别？

1、Jdk动态代理：利用拦截器（必须实现InvocationHandler）加上反射机制生成一个代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理

2、 Cglib动态代理：利用ASM框架，对代理对象类生成的class文件加载进来，通过修改其字节码生成子类来处理

什么时候用cglib什么时候用jdk动态代理？

1、目标对象生成了接口 默认用JDK动态代理

2、如果目标对象使用了接口，可以强制使用cglib

3、如果目标对象没有实现接口，必须采用cglib库，Spring会自动在JDK动态代理和cglib之间转换

##### 谈谈自己对于 Spring IoC 的了解

**所谓IoC，对于spring框架来说，就是由spring来负责控制对象的生命周期和对象间的关系。**Spring所倡导的开发方式**就是如此，**所有的类都会在spring容器中登记，告诉spring你是个什么东西，你需要什么东西，然后spring会在系统运行到适当的时候，把你要的东西主动给你，同时也把你交给其他需要你的东西。所有的类的创建、销毁都由 spring来控制，也就是说控制对象生存周期的不再是引用它的对象，而是spring。对于某个具体的对象而言，以前是它控制其他对象，现在是所有对象都被spring控制，所以这叫控制反转。

##### 谈谈自己对于 AOP 的了解

AOP(Aspect-Oriented Programming，面向切面编程)：是一种新的方法论，是对传统 OOP(Object-Oriented Programming，面向对象编程)的补充。面向对象是纵向继承，面向切面是横向抽取。
AOP编程操作的主要对象是切面(aspect)，而切面用于模块化横切关注点（公共功能）。
面向切面编程，就是将交叉业务逻辑封装成切面，利用AOP的功能将切面织入到主业务逻辑中。所谓交叉业务逻辑是指，通用的，与主业务逻辑无关的代码，如安全检查，事物，日志等。若不使用AOP，则会出现代码纠缠，即交叉业务逻辑与主业务逻辑混合在一起。这样，会使业务逻辑变得混杂不清。
在应用AOP编程时，仍然需要定义公共功能，但可以明确的定义这个功能应用在哪里，以什么方式应用，并且不必修改受影响的类。这样一来横切关注点就被模块化到特殊的类里——这样的类我们通常称之为“切面”。
AOP的好处：
每个事物逻辑位于一个位置，代码不分散，便于维护和升级
业务模块更简洁，只包含核心业务代码

##### 将一个类声明为 bean 的注解有哪些?

我们一般使用 @Autowired 注解自动装配 bean，要想把类标识成可用于 @Autowired注解自动装配的 bean 的类,采用以下注解可实现：

@Component ：通用的注解，可标注任意类为 Spring 组件。如果一个Bean不知道属于哪个层，可以使用

@Repository : 对应持久层即 Dao 层，主要用于数据库相关操作。

@Service :对应服务层，主要涉及一些复杂的逻辑，需要用到 Dao层。

@Controller : 对应 Spring MVC控制层，主要用户接受用户请求并调用 Service 层返回数据给前端页面。

##### MyBatis中#{}和${}的作用与区别

在[mybatis](https://so.csdn.net/so/search?q=mybatis&spm=1001.2101.3001.7020)中#和$的主要区别是：#传入的参数在SQL中显示为字符串，#方式能够很大程度防止SQL注入；$传入的参数在SQL中直接显示为传入的值，$方式无法防止SQL注入。

**1、传入的参数在SQL中显示不同**

\#{} 将传入的参数（数据）在SQL中显示为字符串，会对自动传入的数据加一个双引号。
「对自动传入的数据加一个双引号」

2、#{}可以防止SQL注入的风险（语句的拼接）；但${}无法防止SQL注入。

3、${}方式一般用于传入数据库对象，例如：表名用参数传递进SQL。

4、大多数情况下还是经常使用#{}，一般能用#{}的就别用${}；但有些情况下必须使用${}，例：MyBatis排序时使用ORDER BY动态参数时需要注意，得用${}而不是#{}。

5、#{} 的参数替换是发生在 DBMS 中，而 ${} 则发生在动态解析过程中。

##### 说说bean 的生命周期?

1. 通过构造方法实例化 Bean 对象。
2. 通过 setter 方法设置对象的属性。
3. 通过Aware，也就是他的子类BeanNameAware，调用Bean的setBeanName()方法传递Bean的ID(XML里面注册的ID)，setBeanName方法是在bean初始化时调用的，通过这个方法可以得到BeanFactory和 Bean 在 XML 里面注册的ID。
4. 如果说 Bean 实现了 BeanFactoryAware,那么工厂调用setBeanFactory(BeanFactory var1) 传入的参数也是自身。
5. 把 Bean 实例传递给 BeanPostProcessor 中的 postProcessBeforeInitialization 前置方法。
6. 完成 Bean 的初始化
7. 把 Bean 实例传递给 BeanPostProcessor 中的 postProcessAfterInitialization 后置方法。
8. 此时 Bean 已经能够正常时候，在最后的时候调用 DisposableBean 中的 destroy 方法进行销毁处理。

##### SpringMVC的工作原理有了解嘛

1. 客户端发起请求（http）通过web.xml找到 DispatchServlet（前端控制器）；
2. 由DispatchServlet控制器通过配置文件（servletName-servlet.xml）寻找到一个或多个HandlerMapping（映射处理器），找到用于处理请求的controller（后端控制器）；
3. DispatchServlet将请求提交到controller；
4. Controller处理业务逻辑后，
5. controller返回数据 ModelAndVIew给DispatchServlet；
6. DispatchServlet寻找到一个或多个ViewResolver（视图解析器），找到ModelAndVIew指定的视图；
7. DispatchServle负责将结果返给View（客户端JSP页面），封装Http；
8. view响应页面的HTTP请求，返回响应数据，浏览器绘制页面。

##### Spring 框架中用到了哪些设计模式：

- **工厂设计模式** : Spring使用工厂模式通过 `BeanFactory`、`ApplicationContext` 创建 bean 对象。
- **代理设计模式** : Spring AOP 功能的实现。
- **单例设计模式** : Spring 中的 Bean 默认都是单例的。
- **模板方法模式** : Spring 中 `jdbcTemplate`、`hibernateTemplate` 等以 Template 结尾的对数据库操作的类，它们就使用到了模板模式。
- **包装器设计模式** : 我们的项目需要连接多个数据库，而且不同的客户在每次访问中根据需要会去访问不同的数据库。这种模式让我们可以根据客户的需求能够动态切换不同的数据源。
- **观察者模式:** Spring 事件驱动模型就是观察者模式很经典的一个应用。
- **适配器模式** :Spring AOP 的增强或通知(Advice)使用到了适配器模式、spring MVC 中也是用到了适配器模式适配`Controller`。

##### Spring 事务中有哪几种事务传播行为?

1、PROPAGATION_REQUIRED：如果当前没有事务，就创建一个新事务，如果当前存在事务，就加入该事务，该设置是最常用的设置。

2、PROPAGATION_SUPPORTS：支持当前事务，如果当前存在事务，就加入该事务，如果当前不存在事务，就以非事务执行。‘

3、PROPAGATION_MANDATORY：支持当前事务，如果当前存在事务，就加入该事务，如果当前不存在事务，就抛出异常。

4、PROPAGATION_REQUIRES_NEW：创建新事务，无论当前存不存在事务，都创建新事务。

5、PROPAGATION_NOT_SUPPORTED：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。

6、PROPAGATION_NEVER：以非事务方式执行，如果当前存在事务，则抛出异常。

7、PROPAGATION_NESTED：如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作。

##### springbootstarter的工作原理

利用starter实现自动化配置只需要两个条件——maven依赖、配置文件，这里简单介绍下starter实现自动化配置的流程。
引入maven实质上就是导入jar包，spring-boot启动的时候会找到starter jar包中的resources/META-INF/spring.factories文件，根据spring.factories文件中的配置，找到需要自动配置的类

##### bootstrap.yml和application.yml的区别

在 Spring Boot 中有两种上下文，一种是 bootstrap，另外一种是 application，下面列举这两种配置文件的区别

1.加载顺序
若application.yml 和bootstrap.yml 在同一目录下：bootstrap.yml 先加载 application.yml后加载

bootstrap.yml 用于应用程序上下文的引导阶段。bootstrap.yml 由父Spring ApplicationContext加载。

2.配置区别
bootstrap.yml 和 application.yml 都可以用来配置参数。

bootstrap.yml 用来程序引导时执行，应用于更加早期配置信息读取。可以理解成系统级别的一些参数配置，这些参数一般是不会变动的。一旦bootStrap.yml 被加载，则内容不会被覆盖。

application.yml 可以用来定义应用级别的， 应用程序特有配置信息，可以用来配置后续各个模块中需使用的公共参数等。

3.属性覆盖问题
启动上下文时，Spring Cloud 会创建一个 Bootstrap Context，作为 Spring 应用的 Application Context 的父上下文。

初始化的时候，Bootstrap Context 负责从外部源加载配置属性并解析配置。这两个上下文共享一个从外部获取的 Environment。Bootstrap 属性有高优先级，默认情况下，它们不会被本地配置覆盖。

也就是说如果加载的 application.yml 的内容标签与 bootstrap 的标签一致，application 也不会覆盖 bootstrap，而 application.yml 里面的内容可以动态替换。

##### springboot的监视器actuator

Spring boot actuator 是 spring 启动框架中的重要功能之一。
Spring boot 监视器可帮助您访 问生产环境中正在运行的应用程序的当前状态。有几个指标必须在生产环境中进行检查和 监控。即使一些外部应用程序可能正在使用这些服务来向相关人员触发警报消息。监视器 模块公开了一组可直接作为 HTTP URL 访问的 REST 端点来检查状态。

##### SpringBoot的自动配置原理

我们可以发现，在使用`main()`启动SpringBoot的时候，只有一个注解`@SpringBootApplication`，我们可以点击进去`@SpringBootApplication`注解中看看，可以发现有**三个注解**是比较重要的：

- `@SpringBootConfiguration`：我们点进去以后可以发现底层是**Configuration**注解，说白了就是支持**JavaConfig**的方式来进行配置(**使用Configuration配置类等同于XML文件**)。

- `@EnableAutoConfiguration`：开启**自动配置**功能

- `@ComponentScan`：这个注解，学过Spring的同学应该对它不会陌生，就是**扫描**注解，默认是扫描**当前类下**的package。将`@Controller/@Service/@Component/@Repository`等注解加载到IOC容器中。

  `@SpringBootApplication`等同于下面三个注解：

  - `@SpringBootConfiguration`
  - `@EnableAutoConfiguration`
  - `@ComponentScan`

  其中`@EnableAutoConfiguration`是关键(启用自动配置)，内部实际上就去加载`META-INF/spring.factories`文件的信息，然后筛选出以`EnableAutoConfiguration`为key的数据，加载到IOC容器中，实现自动配置功能！

##### springboot的全局异常处理

SpringBoot的项目已经对有一定的异常处理了，但是对于我们开发者而言可能就不太合适了，因此我们需要对这些异常进行统一的捕获并处理。SpringBoot中有一个`ControllerAdvice`的注解，使用该注解表示开启了全局异常的捕获，我们只需在自定义一个方法使用`ExceptionHandler`注解然后定义捕获异常的类型即可对这些捕获的异常进行统一的处理。

```java
@ControllerAdvice
public class MyExceptionHandler {

  @ExceptionHandler(value =Exception.class)
	public String exceptionHandler(Exception e){
		System.out.println("未知异常！原因是:"+e);
       	return e.getMessage();
    }
}
```

怎么去捕获异常

在 Spring Boot 应用中，系统中共存在以下 3 种异常情况：

- **`Controller`抛出的异常**：可以采用`@ControllerAdvice`进行全局捕获
- **其他异常**：会被全局异常处理器（比如：`BasicErrorController`）进行捕获
- **全局异常处理器抛出的异常**：对于非`/error`页面的异常，会被内置容器转发到`/error`进行捕获处理，而对于`/error`页面的异常，会先由`@ControllerAdvice`进行捕获，捕获不成功则交由 Spring Boot 内置的 Web 应用容器（比如：Tomcat）进行捕获。

##### java多线程同步的方法

（1）同步方法：即有synchronized关键字修饰的方法。 由于java的每个对象都有一个内置锁，当用此关键字修饰方法时，内置锁会保护整个方法。在调用该方法前，需要获得内置锁，否则就处于阻塞状态。

（2）同步代码块：即有synchronized关键字修饰的语句块。被该关键字修饰的语句块会自动被加上内置锁，从而实现同步

（3）使用特殊域变量(volatile)实现线程同步

  a.volatile关键字为域变量的访问提供了一种免锁机制 
  b.使用volatile修饰域相当于告诉虚拟机该域可能会被其他线程更新 
  c.因此每次使用该域就要重新计算，而不是使用寄存器中的值 
  d.volatile不会提供任何原子操作，它也不能用来修饰final类型的变量

（4）使用重入锁实现线程同步

  在JavaSE5.0中新增了一个java.util.concurrent包来支持同步。ReentrantLock类是可重入、互斥、实现了Lock接口的锁， 它与使用synchronized方法和快具有相同的基本行为和语义，并且扩展了其能力。
   ReenreantLock类的常用方法有：
     ReentrantLock() : 创建一个ReentrantLock实例 
     lock() : 获得锁 
     unlock() : 释放锁 
  注：ReentrantLock()还有一个可以创建公平锁的构造方法，但由于能大幅度降低程序运行效率，不推荐使用 

如果synchronized关键字能满足用户的需求，就用synchronized，因为它能简化代码 。如果需要更高级的功能，就用ReentrantLock类，此时要注意及时释放锁，否则会出现死锁，通常在finally代码释放锁 

（5）使用局部变量实现线程同步

如果使用ThreadLocal管理变量，则每一个使用该变量的线程都获得该变量的副本，副本之间相互独立，这样每一个线程都可以随意修改自己的变量副本，而不会对其他线程产生影响。

（7）使用阻塞队列实现线程同步

（8）使用原子变量实现线程同步

##### ConcurrentHashMap是如何保证线程安全的

印象中一直以为ConcurrentHashMap是基于Segment分段锁来实现的JDK1.8中的ConcurrentHashMap中仍然存在Segment这个类，而这个类的声明则是为了兼容之前的版本序列化而存在的。
JDK1.8中的ConcurrentHashMap不再使用Segment分段锁，而是以table数组的头结点作为synchronized的锁。和JDK1.8中的HashMap类似，对于hashCode相同的时候，在Node节点的数量少于8个时，这时的Node存储结构是链表形式，时间复杂度为O(N)，当Node节点的个数超过8个时，则会转换为红黑树，此时访问的时间复杂度为O(long(N))。
其实ConcurrentHashMap保证线程安全主要有三个地方。

一、使用volatile保证当Node中的值变化时对于其他线程是可见的
二、使用table数组的头结点作为synchronized的锁来保证写操作的安全
三、当头结点为null时，使用CAS操作来保证数据能正确的写入。

##### ConcurrentHashMap是怎么分段分组的？

get操作：

Segment的get操作实现非常简单和高效，先经过一次再散列，然后使用这个散列值通过散列运算定位到 Segment，再通过散列算法定位到元素。get操作的高效之处在于整个get过程都不需要加锁，除非读到空的值才会加锁重读。原因就是将使用的共享变量定义成 volatile 类型。

put操作：

当执行put操作时，会经历两个步骤：

1. 判断是否需要扩容；
2. 定位到添加元素的位置，将其放入 HashEntry 数组中。

插入过程会进行第一次 key 的 hash 来定位 Segment 的位置，如果该 Segment 还没有初始化，即通过 CAS 操作进行赋值，然后进行第二次 hash 操作，找到相应的 HashEntry 的位置，这里会利用继承过来的锁的特性，在将数据插入指定的 HashEntry 位置时（尾插法），会通过继承 ReentrantLock 的 tryLock() 方法尝试去获取锁，如果获取成功就直接插入相应的位置，如果已经有线程获取该Segment的锁，那当前线程会以自旋的方式去继续的调用 tryLock() 方法去获取锁，超过指定次数就挂起，等待唤醒。

##### 线程池工作原理

线程池是一种基于池化技术思想来管理线程的工具。在线程池中维护了多个线程，由线程池统一的管理调配线程来执行任务。通过线程复用，减少了频繁创建和销毁线程的开销。

##### threadlocal工作原理

`ThreadLocal`   即：线程 的 局部变量，主要是存放每个线程的私有数据。每当你创建一个  `ThreadLocal`  变量，那么访问这个变量的每个线程都会在当前线程存一份这个变量的本地副本，只有自身线程能够访问，和其他线程是不共享的，这样可以避免线程资源共享变量冲突的问题

##### threadlocal应用场景

（1）Spring采用Threadlocal的方式，来保证单个线程中的数据库操作使用的是同一个数据库连接，同时，采用这种方式可以使业务层使用事务时不需要感知并管理connection对象，通过传播级别，巧妙地管理多个事务配置之间的切换，挂起和恢复。

（2）一些需要单线程横跨若干方法的调用，也就是上下文（Context），它是一种状态，经常就是是用户身份、任务信息等，就会存在过渡传参的问题。

##### ThreadLocal是如何解决hash冲突

答：采用的开放地址法，不是hashmap的链地址法！

ThreadLocal是采用数组来存储的。ThreadLocalMap在存储的时候会给每一个ThreadLocal对象一个threadLocalHashCode，在插入过程中，根据ThreadLocal对象的hash值，定位到table中的位置i，**int i = key.threadLocalHashCode & (len-1)**。

##### 父子进程之间如何共享ThreadLocal中的值

使用inheritThreadLocals。在主线程中创建一个InheritableThreadLocal的实例，然后在子线程中得到这个InheritableThreadLocal实例设置的值。

##### **threadlocal内存泄露**

> 程序在申请内存后，无法释放已申请的内存空间，一次内存泄露危害可以忽略，但内存泄露堆积后果很严重，无论多少内存,迟早会被占光

Thread引用会引用一个ThreadLocalMap对象，这个map中的key是ThreadLocal对象（使用WeakReference包装），value是业务上变量的值。内存泄露 是由于 `ThreadLocalMap`  的 `key` 为弱引用导致的，弱引用对象，在没有被外部引用时，当发生GC 是 key 被回收为 null, 但是 value 还存在强引用，可能会存在 内存泄露问题

但其实，由于 Thread -> ThreadLocalMap -> Entry -> value 存在这样一条引用链 只要 `Thread` 不被退出，`ThreadLocalMap` 的生命周期将是一样长的，如果不进行手动删除，必然会出现内存泄露。更何况我们大多数是以线程池的方式去操作线程。

总结:
 threadLocal` 内存泄漏的根源是：由于 `ThreadLocalMap` 的生命周期跟 `Thread`一样长，如果没有手动删除对应 `key 就会导致内存泄漏，而不是因为弱引用。

**建议：在使用ThreadLocal的时候要养成及时 `remove()` 的习惯**

源码中分析防止内存泄露的清除操作

`ThreadLocal`  中有两种清除方式:

- expungeStaleEntry()  探测式清理
- cleanSomeSlots() 启发式清除

##### 为什么使用弱引用而不是强引用

key 使用强引用

当hreadLocalMap的key为强引用回收ThreadLocal时，因为ThreadLocalMap还持有ThreadLocal的强引用，如果没有手动删除，ThreadLocal不会被回收，导致Entry内存泄漏。

key 使用弱引用

当ThreadLocalMap的key为弱引用回收ThreadLocal时，由于ThreadLocalMap持有ThreadLocal的弱引用，即使没有手动删除，ThreadLocal也会被回收。当key为null，在下一次ThreadLocalMap调用set(),get()，remove()方法的时候会被清除value值。

由于Thread中包含变量ThreadLocalMap，因此ThreadLocalMap与Thread的生命周期是一样长，如果都没有手动删除对应key，都会导致内存泄漏。

但是使用**弱引用**可以多一层保障：弱引用ThreadLocal不会内存泄漏，对应的value在下一次ThreadLocalMap调用set(),get(),remove()的时候会被清除。

因此，ThreadLocal内存泄漏的根源是：由于ThreadLocalMap的生命周期跟Thread一样长，如果没有手动删除对应key就会导致内存泄漏，而不是因为弱引用。

##### ThreadLocal内存泄漏解决方案

- 每次使用完ThreadLocal，都调用它的remove()方法，清除数据。
- 将ThreadLocal变量定义成private static，这样就一直存在ThreadLocal的强引用，也就能保证任何时候都能通过ThreadLocal的弱引用访问到Entry的value值，进而清除掉 。
- 在使用线程池的情况下，没有及时清理ThreadLocal，不仅是内存泄漏的问题，更严重的是可能导致业务逻辑出现问题。所以，使用ThreadLocal就跟加锁完要解锁一样，用完就清理。