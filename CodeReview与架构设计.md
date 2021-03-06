# Code Review规范

## 目的

------

- 提高代码质量，及早发现潜在缺陷，降低修改/弥补缺陷的成本
- 促进团队知识共享，提高整体水平



## 前提

------

- Code Review 人员理解此环境的重要性
- 代码编译正确

- 符合阿里规约，并用阿里规约扫面通过
- sonar扫描阻断和严重等级问题清零重要

- Review 人员理解业务、代码基本架构、技术手段和工具
- 单元测试达到指标

- 工具能覆盖的用工具，Code Review 主要核查工具无法达到目的的场景



## Code Review需要做什么

------

#### 一致性

- 是否实现并满足了技术架构设计的全部内容
- 符合代码编写规范

- 注释准确合理，描述清晰
- 变量正确定义及使用

- 语义化，通俗易懂
- 统一格式化

- 静态配置是否符合标准
- 数据库语句是否符合标准重要

#### 坏味道

- 是否避免依赖于开发语言缺省提供的功能
- 代码是否无意中陷入了死循环重要

- 代码是否是否避免了无穷递归重要

#### 逻辑正确性

- 是否正确实现了业务需求

#### 健壮性

- 异常捕捉是否合理
- 是否采取措施避免运行时错误（如数组边界溢出、被零除、值越界、堆栈溢出等）

- 运行时异常是否都处理完毕(例如空指针检查、应该catch哪些异常)
- 是否对外部服务做了容错处理(例如请求超时、返回值不符合预期等)重要

- 变更是否具备兼容性，尤其是API变更重要
- 幂等重要

- 核心流程是否有对应的补偿机制重要

#### 结构

- 代码命名符合规范
- 包结构合理

- 程序结构合理
- 是否存在过度复杂或超长的类或函数或表达式重要

#### 重用

- 重复率不超过阈值，核心业务代码重复需考虑抽象、封装复用
- 考虑可重用的服务，功能和组件。不重复造轮子

- 考虑通用函数和类

#### 可扩展性

- 一个组件可以被更好的组件替换
- 对现有代码进行最小的更改

- 使用通用的技术组件
- 是否满足设计原则(开闭、里式替换、接口隔离、依赖倒置、单一职责、迪米特法则)

#### 安全性

- 进行身份验证，授权
- 数据格式正确性验证

- 避免诸如SQL注入
- 避免跨站脚本（XSS）等安全威胁

- 加密敏感数据（密码，用户身份信息等）重要
- 关键路径业务安全

#### 日志输出

- 核心业务日志输出内容完整、规范，日志级别合理重要
- 异常分支日志输出完整，满足问题定位需求重要

- 外部调用日志完整输出入参和返回值
- 不以Exception包装业务异常，避免无效ERROR日志

#### 性能

- 使用合适的数据类型
- 懒加载，异步和并行处理

- 缓存及使用场景适用性(分布式缓存、本地缓存；缓存TTL)



## 流程

------

- 采用upsource做Code Review重要
- 高效迅速完成Code Review

- 必须是项目开发人员提交Merge Request
- 每次Code Review 必须有反馈重要

- 没有经过Review 的项目不允许发布



## Code Review 人员

------

每一位同学都可以参与code review，可以拍砖也可以借鉴

- 项目组内同学可以重点关注业务逻辑实现是否正确
- 架构师可以重点关注代码是否具备足够的扩展性，是否符合设计原则，是否有可预见的性能问题

- 团队Leader可以重点关注代码规范、代码结构合理性



## 如何保障Code Review效果

------

- 项目主流程开发完成后可进行一次快速code review，及时发现问题及时修正
- 项目提测之前进行一次全面code review，确保提测质量

- 项目排期需为code review保留足够时间重要
- code review提的concern需在提交测试前全部fix

- 参与code review的同学需提前熟悉业务、工程结构重要





### **架构设计评审规范**

# 1. 范围标准



## 1) 业务分析

### (1) 项目背景

1. [推荐] 提供项目背景和PRD
2. [可选] 提供项目产品价值, 成本和收益 



### (2) 可行性分析

1. [强制] 外部依赖是否变更

1. 1. 提供技术预研报告
   2. 提供可行性DEMO报告

1. [推荐] 系统架构是否变更

1. 1. 提供变更评估报告

1. [推荐] 技术栈是否变更

1. 1. 提供技术预研报告
   2. 提供变更评估报告

1. [可选] 提供项目技术价值, 成本和收益



### (3) 用例设计

1. [推荐] 使用UML用例图(UseCase Diagram)表达结构化设计

1. 1. [可选] 提供核心业务功能脑图

1. [强制] User用户类型超过1个且User Case用户用例超过3个时提供





## 2) 系统设计

### (1) 系统架构

1. [强制] 提供系统间架构设计

1. 1. [强制] 包含系统边界
   2. [强制] 包含系统依赖

1. 1. [强制] 包含前后端依赖
   2. [可选] UML包图(Package Diagram)



### (2) 部署架构

1. [强制] 提供物理部署及依赖图

1. 1. [可选] UML部署图(Deployment Diagram)

1. [推荐] 提供物理环境及配置信息

1. 1. [推荐] 服务器信息
   2. [可选] 网络环境信息

1. 1. [可选] 容器环境信息

1. [推荐] 提供环境参数配置

1. 1. [推荐] JVM参数配置
   2. [可选] 提供参数测试报告及基线



### (3) 影响范围

1. [强制] 提供业务变更及影响范围

1. 1. [强制] 业务领域变更
   2. [推荐] 提供核心功能变更

1. [强制] 提供技术变更及影响范围

1. 1. [强制] 提供系统服务依赖变更
   2. [强制] 提供技术栈变更

1. 1. [强制] 基础中间件变更
   2. [强制] 存储变更

1. [强制] 提供端(PC, APP 等)变更及影响范围





## 3) 功能设计

### (1) 数据架构

1. [强制] 提供领域模型设计

1. 1. [推荐] 业务领域模型和技术模型映射关系
   2. [可选] 业务领域规划模型扩展性及兼容性

1. [强制] 提供实体联系图(ER Diagram)

1. 1. [强制] 无约束外键

1. [推荐] 数据存储选型

1. 1. [推荐] 业务领域与实例映射关系

1. [强制] 提供表设计

1. 1. [强制] 符合数据库应用规范
   2. [强制] 包含字段设计及索引设计

1. [强制] 提供配置设计

1. 1. [强制] 动态配置
   2. [强制] 静态配置

1. [推荐] 数据管理

1. 1. [强制] 数据量级评估
   2. [推荐] 数据初始化

1. 1. [可选] 数据归档
   2. [可选] 数据备份



### (2)  逻辑架构

1. [强制] 提供业务功能调用链路中的逻辑设计

1. 1. [强制] UML序列图(Sequence Diagram)
   2. [强制] 提供系统间功能输入输出

1. 1. [强制] 提供系统内调用输入输出

1. [推荐] 提供核心模块或复杂业务的逻辑设计

1. 1. [推荐] 系统内, 提供UML流程图(Flowchart Diagram)描述功能实现
   2. [推荐] 系统间, 提供UML序列图(Sequence Diagram)描述功能实现

1. [强制] 提供核心业务状态变更的设计

1. 1. [强制] 业务对象状态超过3个时, 提供UML状态图(Statechart Diagram)描述状态变更

1. [推荐] 提供核心模型依赖设计

1. 1. [推荐] 提供UML序列图(Sequence Diagram)描述功能实现



### (3) 接口设计

1. [强制] 单一职责原则(Single Responsibility Principle)

1. 1. [强制] 原子化接口, 结合业务制定颗粒度

1. [强制] 接口隔离原则(Interface Segregation Principle)
2. [强制] 依赖倒置原则(Dependence Inversion Principle)

1. [强制] 隐藏实现细节原则(Hide Implementation Details)

1. 1. [推荐] 接口命名和参数模型, 不要技术实现方式

1. [强制] 不返回内部异常

1. 1. [推荐] 通过切面捕获异常, 转换错误码

1. [强制] 接口兼容变更

1. 1. [强制] 禁止 删除字段, 重命名字段, 删除枚举
   2. [强制] 禁止 变更字段数据结构类型

1. 1. [强制] 禁止 删除接口, 重命名接口, 删除方法, 重命名方法
   2. [强制] 禁止 修改接口请求/返回类型

1. 1. [强制] 禁止 修改/删除rest接口path构成
   2. [强制] 禁止 修改/删除rest接口method类型



### (4) 幂等性设计

1. [强制] 幂等性范围确认

1. 1. 幂等性字段
   2. 幂等性接口

1. [强制] 幂等性策略

1. 1. 存储约束
   2. 分布式锁

1. 1. 内存锁





## 4) 非功能设计

### (1) 技术选型

1. [强制] 技术栈新增或变更时提供变更说明

1. 1. [推荐]技术现状分析报告

1. [强制] 技术预研候选方案

1. 1. [推荐]候选方案优缺点对比

1. [推荐] 候选方案DEMO工程及演示报告

1. 1. [推荐] 候选方案可行性报告
   2. [可选] 候选方案性能测试报告

1. 1. [可选] 候选方案稳定性测试报告

1. [推荐] 技术选型关注点

1. 1. 影响力
   2. 社区活跃度

1. 1. 稳定性
   2. 性能

1. 1. 扩展性
   2. 维护性

1. 1. 可用性
   2. 兼容性

1. 1. 安全性



### (2) 风险因素

1. [推荐] 潜在风险及隐患

1. 1. 业务
   2. 系统

1. 1. 可用性
   2. 性能

1. [推荐] 应急预案

1. 1. 触发条件
   2. 测试报告

1. 1. 影响范围

1. [可选] 优化方案



### (3) 性能指标

1. [推荐] 系统指标范围

1. 1. rest接口: 内部/外部
   2. rpc接口

1. 1. 数据库
   2. 缓存

1. [推荐] 指标类型

1. 1. 调用侧: RT, TPS, QPS
   2. 服务侧: CPU, 内存, IO, 网络, JVM

1. 1. TP指标: TP50, TP90, TP95

1. [推荐] 提供物理环境测试报告及基线

1. 1. 单机指标
   2. 链路指标



### (4) 安全规范

1. [强制] 评审人包含安全团队
2. [强制] 符合技术安全规范

1. [强制] 符合数据安全规范
2. [强制] 符合安全生产规范

1. [推荐] 检查安全清单报告





## 5) 交付设计

### (1) 测试范围

1. [推荐] 影响范围和测试范围
2. [推荐] 回归范围



### (2) 发布计划

1. [强制] 上线流程方案
2. [强制] 服务发布

1. 1. 发布顺序
   2. 应用分支

1. 1. 二方库版本

1. [强制] 发布变更

1. 1. 数据库: DDL, DML
   2. 缓存

1. 1. 配置: 静态, 动态
   2. 监控

1. [强制] 校验标准
2. [强制] 回滚方案

1. 1. 影响范围
   2. 校验标准





## 6) 反馈建议

### (1) 变更记录

1. [推荐] 架构设计讨论记录
2. [推荐] 架构设计变更记录

1. 1. 评审前变更
   2. 评审后变更



### (2) 评审意见

1. [推荐] 架构评审意见记录

1. 1. 意见状态变更



------

# 2. 参考流程

1. [强制] 业务需求及可行性分析
2. [参考] 技术预研

1. 1. 技术栈变更, 三方依赖时[强制]

1. [可选] 内部技术讨论
2. [强制] 设计文档撰写

1. [推荐] 内部review, 讨论, 修改
2. [强制] 团队内部评审

1. [可选] 团队外部评审或送审
2. [强制] 评审结果, 会议纪要, 设计变更记录.

1. [强制] 评审后变更需同步设计文档, 并变更记录.

1. 1. 功能变更需内部讨论
   2. 架构或核心功能变更需再次评审





------

# 3. 关注要素

## 可伸缩性

- 每一个环节是否都是可以横向扩展的？
- 扩容需要怎么做手动还是自动？

- 数据库不能横向扩展怎么办？
- 纵向扩展有多少效果？

- 横向扩展是否是线性的？
- 扩展后是否可以提高响应速度？



## 性能

- 我们需求的TPS、QPS和RT是多少？
- 整体设计上会做到的TPS、QPS和RT是多少？

- 随着数据量的增大系统性能会不会出现明显问题？
- 系统哪个环节会是最大的瓶颈？

- 是否打算做压力测试，压力测试方案是怎么样的？
- 怎么提高前端用户的访问流畅性？



## 灵活性

- 是否有了解过产品层面以后会怎么发展？
- 模块A是否能拆分出去独立为其它业务服务？

- 模块B是否可以替换为另一种第三方数据源？
- 如果流程有变，需要多大的工作量来适应？

- 业务是否可以做到可配？



## 可扩展性

- 为什么A和B都有差不多的逻辑？
- 是否考虑到了A业务的实现以后还有B的可能性？

- 如果现在有两种策略以后扩展到了八种策略怎么做？
- 以后是否可以把这个业务的H5前端适配到PC？



## 可靠性

- 是否架构中有单点？
- 故障转移是怎么实现的？

- 集群内部故障转移需要多久？
- MQ或存储出现问题的时候系统会怎么样？

- MQ或存储出现问题又恢复了系统是否会自己恢复？
- 是否考虑过异地故障转移的方案？

- 是否考虑过多活的方案？
- 是否有数据丢失的可能性？

- 数据丢失后是否可以恢复？
- 系统彻底挂了对其它业务的影响是什么？

- 系统彻底挂了是否可以有线下的方式走业务？



## 安全性

- 是否彻底避免SQL注入和XSS？
- 是否做了风控策略？

- 是否有防刷保护机制？
- 数据库拖库了会怎么样？

- 是否有数据泄露的可能性？
- 数据的权限怎么控制的？

- 功能的权限是怎么控制的？
- 是否做了日志审计？

- 受到了DDOS攻击怎么办？
- 数据传输是否加密验签？



## 兼容性

- 老的系统打算怎么办？
- 怎么进行新老系统替换？

- 新老系统能否来回切换？
- 别的系统怎么连接你这套新服务？

- 上下游依赖是否梳理过，影响范围多大？
- 上下游改造的难度怎么样？

- 上下游改造有排期吗？
- 上下游改造的计划和通知时间确定了吗？

- 使用了新的数据源数据怎么迁移？
- 使用了新的技术老项目开发能否适应？



## 弹性处理

- 这个数据重复消费会怎么样？
- 这个接口重复调用会怎么样？

- 是否考虑了服务降级？哪些业务支持降级？
- 是否考虑了服务熔断？熔断后怎么处理？

- 是否考虑了服务限流？限流后客户端表现怎么样？
- 队列爆仓会怎么样？

- 是否考虑了隔离性？



## 事务性

- 这段业务由谁保证事务性？
- 数据库事务回滚后会怎么样？

- 服务调用了失败怎么办？
- 队列补偿怎么做的？

- 服务调用补偿怎么做的？
- 数据补偿实现最终一致需要多久？

- 在数据不完整的时候用户会感知到吗？



## 可测试性

- 测试环境和线上的差异多大？
- 是否支持部署多套隔离的测试环境？

- 是否打算做单元测试，覆盖率目标是多少？
- 测试黑盒白盒工作量的比例是怎么样的？

- 是否支持接口层面的自动化测试？
- 是否有可能做UI自动化测试？

- 压测怎么造数据？
- 是否可以在线上做压测？

- 线上压测怎么隔离测试数据？
- 是否有测试白名单功能？



## 可运维性

- 每一个组件对服务器哪方面的压力会最大？
- 重新搭建整套系统最快需要多少时间？

- 系统是否可以完全基于源代码构建？
- 系统是否有初始化或预热的环节？

- 系统里哪些环节需要人工参与？
- 数据是否需要定期归档处理？

- 会不会有突发的数据量业务量增大？
- 随着时间的推移如果压力保持不变的话系统需要怎么来巡检和维护？

- 怎么在容器里进行部署？



## 监控

- 业务层面哪些指标需要监控和报警？
- 应用层面系统内部是否有暴露了一些指标作监控和报警？

- 系统层面使用的中间件和存储是否有监控报警？
- 是否所有环节都接入了全链路跟踪？

- 出现报警的时候应该由谁来处理？
- 每一个模块是否有固定的主要和次要负责人？

- 有没有可能系统出了问题无法通过监控指标体现？
- 哪些指标需要上大屏由监控进行7*24监控？



### 工程规范

**此工程用于承载既定业务领域中的面向外部用户的业务功能实现。它包括业务模块、模型模块、数据访问模块、外部访问模块、单元测试模块以及业务容器模块等。**

# 一、业务模块（service）

它是用于定义承载业务功能实现的主要模块。

# 二、模型模块（bizobject）

它是用于**隔离**业务模块**与**业务容器模块**，消除它们之间的**依赖**也是中间转换和运转的介质。它定义了业务模块**对业务容器模块暴露的接口**以及**内部使用的业务模型，区别于数据库实体和接口模块模型。

# 三、数据访问模块（dao）

它是用于定义数据访问和存储功能实现的主要模块，无复杂逻辑。

# 四、外部访问模块（meshside）

它是用于定义对于其它领域服务的依赖的封装或外部api依赖和中间件依赖，无复杂逻辑，隔离外部变化，管理外部关系。它们包括但不限于dubbo服务、web服务、ESB等。

# 五、单元测试模块（basetest）

它是用于承载整个业务工程的单元测试基类，基本配置。各模块以 scope=test打包进去。

# 六、业务容器模块（web）

它是用于定义**应用程序入口**、**全局配置**以及**对外提供服务的集合**，包括dubbo-api、web-api、task-api等。以系统能力视角展现，可以从这个模块顺藤摸瓜熟悉所有业务。



# 一、apis（应用程序接口）

1. [强制] 通过包名区分前后台api。例如：com.qxwz.xx.api，com.qxwz.xx.`backend`.api。
2. [强制] 接口名以api结尾。例如：xxxxxxApi。

3. [推荐] 集成swagger。
4. [强制] 集成easy-rest。

# 二、models（模型）

1. [强制] 实现Serializable接口。
2. [推荐] 集成swagger。

3. [强制] 使用dubbo参数验证。

```java
@NotBlank(message = ValidateMessage.NOT_BLANK)
private String voucherNo;
```



1. [推荐] 模型区分请求与返回。例如：com.cykg.xx.model.request.*，com.cykg.xx.model.response.*。
2. [推荐] 集成lombok。请参考

# 三、contants（常量/枚举）

1. [强制] 使用接口定义常量的方式来定义常量与枚举。

```java
public interface ApplyType {
    String CREATE = "create";
    String USE = "use";
    String TRANSFER = "transfer";
    Set<String> SCOPE = CollectionUtil.newHashSet(CREATE, USE, TRANSFER);
}
```

# 四、excpetions（异常）

1. [强制] 统一使用`ApiException`。
2. [推荐] 使用`galaxy-api`提供的通用异常码。

3. [强制] 使用接口定义常量的方式来定义异常码。
4. [强制] 定义异常码时，不要使用数字，请使用英文单词或词组，使其更加语义化。



```java
public interface ExceptionCode {
    String INTERNAL_ERROR = "INTERNAL_ERROR";
    String ILLEGAL_PARAMETER_ERROR = "ILLEGAL_PARAMETER";
    String THREAD_CONTEXT_PARAM_ERROR = "THREAD_CONTEXT_PARAM_ERROR";
    String RESUBMIT_ERROR = "RESUBMIT_ERROR";
    String DATABASE_ERROR = "DATABASE_ERROR";
    String CONFIG_ERROR = "CONFIG_ERROR";
}
```

