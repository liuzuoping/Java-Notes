### Spring面试相关

- [Spring面试相关](#toc_0)

- - [简述 AOP 和 IOC 概念](#toc_1)
  - [你有没有⽤用过Spring的AOP? 是用来干嘛的? 大概会怎么使用？](#toc_2)
  - [BeanFactory和ApplicationContext有什么区别？](#toc_3)
  - [列举ApplicationContext的实现方式](#toc_4)
  - [Spring有几种配置方式？](#toc_5)
  - [请解释Spring Bean的生命周期？](#toc_6)
  - [哪些是重要的bean生命周期方法？](#toc_7)
  - [Spring Bean的作用域之间有什么区别？](#toc_8)
  - [Spring框架中的单例Beans是线程安全的么？](#toc_9)
  - [请举例说明如何在Spring中注入一个Java Collection？](#toc_10)
  - [说出 Spring MVC 常用注解](#toc_11)
  - [如何使用 SpringMVC 完成 JSON 操作](#toc_12)
  - [如果⼀一个接口有2个不同的实现, 那么怎么来Autowire一个指定的实现？](#toc_13)
  - [BeanFactory – BeanFactory 实现举例](#toc_14)
  - [Spring支持的事务管理类型](#toc_15)
  - [AOP](#toc_16)
  - [什么是织入。什么是织入应用的不同点？](#toc_17)
  - [spring url request如何和controller映射起来](#toc_18)
  - [spring mvc rest api拦截方法](#toc_19)

#### 简述 AOP 和 IOC 概念

#### 你有没有⽤用过Spring的AOP? 是用来干嘛的? 大概会怎么使用？

#### BeanFactory和ApplicationContext有什么区别？

- BeanFactory 可以理解为含有bean集合的工厂类。BeanFactory 包含了种bean的定义，以便在接收到客户端请求时将对应的bean实例化。
- 从表面上看，application context如同bean factory一样具有bean定义、bean关联关系的设置，根据请求分发bean的功能。但applicationcontext在此基础上还提供了其他的功能。

1. 1. 提供了支持国际化的文本消息
   2. 统一的资源文件读取方式
   3. 在监听器中注册的bean的事件

#### 列举ApplicationContext的实现方式

以下是三种较常见的 ApplicationContext 实现方式：

- ClassPathXmlApplicationContext
  \> 从classpath的XML配置文件中读取上下文，并生成上下文定义。应用程序上下文从程序环境变量中

```plain
ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
```

- FileSystemXmlApplicationContext
  \> 由文件系统中的XML配置文件读取上下文。

```plain
ApplicationContext context = new FileSystemXmlApplicationContext("bean.xml");
```

- XmlWebApplicationContext：由Web应用的XML文件读取上下文。
- AnnotationConfigApplicationContext(基于Java配置启动容器)

#### Spring有几种配置方式？

- 基于XML的配置

```plain
<beans>    
    <bean id="myService" class="com.somnus.services.MyServiceImpl"/>    
</beans>
```

- 基于注解的配置

1. 1. @Required：该注解应用于设值方法。
   2. @Autowired：该注解应用于有值设值方法、非设值方法、构造方法和变量。
   3. @Qualifier：该注解和@Autowired注解搭配使用，用于消除特定bean自动装配的歧义。
   4. JSR-250 Annotations：Spring支持基于JSR-250 注解的以下注解 @Resource、@PostConstruct 和 @PreDestroy。

```plain
<beans>    
   <context:annotation-config/>    
   <!-- bean definitions go here -->    
</beans>
```

- 基于Java的配置

1. 1. @Configuration注解
   2. @Bean注解
   3. @ComponentScan

#### 请解释Spring Bean的生命周期？

Spring框架提供了以下四种方式来管理bean的生命周期事件：

1. Spring容器 从XML 文件中读取bean的定义，并实例化bean。
2. Spring根据bean的定义填充所有的属性。
3. 如果bean实现了BeanNameAware 接口，Spring 传递bean 的ID 到 setBeanName方法。
4. 如果Bean 实现了 BeanFactoryAware 接口， Spring传递beanfactory 给setBeanFactory 方法。
5. 如果有任何与bean相关联的BeanPostProcessors，Spring会在postProcesserBeforeInitialization()方法内调用它们。
6. 如果bean实现IntializingBean了，调用它的afterPropertySet方法，如果bean声明了初始化方法，调用此初始化方法。
7. 如果有BeanPostProcessors 和bean 关联，这些bean的postProcessAfterInitialization() 方法将被调用。
8. 如果bean实现了 DisposableBean，它将调用destroy()方法。

#### 哪些是重要的bean生命周期方法？

*. InitializingBean和DisposableBean回调接口

*. 针对特殊行为的其他Aware接口

*. Bean配置文件中的Custom init()方法和destroy()方法

*. @PostConstruct和@PreDestroy注解方式

#### Spring Bean的作用域之间有什么区别？

1. singleton：这种bean范围是默认的，这种范围确保不管接受到多少个请求，每个容器中只有一个bean的实例，单例的模式由bean factory自身来维护。
2. prototype：原形范围与单例范围相反，为每一个bean请求提供一个实例。
3. request：在请求bean范围内会每一个来自客户端的网络请求创建一个实例，在请求完成以后，bean会失效并被垃圾回收器回收。
4. session：与请求范围类似，确保每个session中有一个bean的实例，在session过期后，bean会随之失效。
5. global- session：global-session和Portlet应用相关。当你的应用部署在Portlet容器中工作时，它包含很多portlet。如果 你想要声明让所有的portlet共用全局的存储变量的话，那么这全局变量需要存储在global-session中。

#### Spring框架中的单例Beans是线程安全的么？

1. 关于单例bean的线程安全和并发问题需要开发者自行去搞定。
2. 但实际上，大部分的Spring bean并没有可变的状态(比如Serview类和DAO类).

#### 请举例说明如何在Spring中注入一个Java Collection？

1. <list> : 该标签用来装配可重复的list值。

2. <set> : 该标签用来装配没有重复的set值。

3. <map>: 该标签可用来注入键和值可以为任何类型的键值对。

4. <props> : 该标签支持注入键和值都是字符串类型的键值对。

#### 说出 Spring MVC 常用注解

1. @RequestMapping 
2. @PathVariable 
3. @RequestParam 
4. @RequestBoy 
5. @ResponseBody
6. @RestController (作用等于 @ResponseBody加在类上+@Controller)

#### 如何使用 SpringMVC 完成 JSON 操作

1. 配置 MappingJacksonHttpMessageConverter 
2. 使用 @RequestBody 注解或 ResponseEntity 作为返回值

#### 如果⼀一个接口有2个不同的实现, 那么怎么来Autowire一个指定的实现？

#### BeanFactory – BeanFactory 实现举例

#### Spring支持的事务管理类型

- 编程式事务管理：这意味你通过编程的方式管理事务，给你带来极大的灵活性，但是难维护。
- 声明式事务管理：这意味着你可以将业务代码和事务管理分离，你只需用注解和XML配置来管理事务。

#### AOP

- Aspect 切面
- 关注点 pointcut
- 连接点
- 通知, Spring切面可以应用五种类型的通知：

1. 1. before：前置通知，在一个方法执行前被调用
   2. after：在方法执行之后调用的通知，无论方法执行是否成功
   3. after-returning：仅当方法成功完成后执行的通知
   4. after-throwing：在方法抛出异常退出时执行的通知
   5. around：在方法执行之前和之后调用的通知

#### 什么是织入。什么是织入应用的不同点？

织入是将切面和到其他应用类型或对象连接或创建一个被通知对象的过程。

织入可以在编译时，加载时，或运行时完成。

#### spring url request如何和controller映射起来

[映射](https://www.jianshu.com/p/c92197e1c892)

当DispatcherServlet接受到客户端的请求后，SpringMVC 通过 HandlerMapping 找到请求的Controller。

HandlerMapping 在这里起到路由的作用，负责找到请求的Controller。

- org.springframework.web.servlet.handler.SimpleUrlHandlerMapping
  通过配置请求路径和Controller映射建立关系，找到相应的Controller
- org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping
  通过 Controller 的类名找到请求的Controller。
- org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping
  通过定义的 beanName 进行查找要请求的Controller
- org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping
  通过注解 @RequestMapping("/userlist") 来查找对应的Controller。

#### spring mvc rest api拦截方法

[RESTFul api拦截 ](https://blog.csdn.net/sky_jiangcheng/article/details/82631791)

- 过滤器（Filter）：可以拿到原始的Http请求和响应的信息，只能获得其请求和响应携带的参数，但是却拿不到真正处理请求的控制器和方法的信息。
- 拦截器（Interceptor）：既可以拿到原始的Http请求和响应的信息，也能拿到真正处理请求的方法信息，但是拿不到方法被调用时，真正调用的参数的值。
- 切片 （Aspect） : 可以拿到方法被调用时真正传进来的参数的值，但是却拿不到原始的Http请求和响应。

![img](https://cdn.nlark.com/yuque/0/2019/png/406432/1565342726317-85a6e8b5-a6da-433a-92d0-8b6d5d7a46c9.png)￼

### 