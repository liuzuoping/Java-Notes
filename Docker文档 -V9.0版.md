![img](https://www.runoob.com/wp-content/uploads/2016/04/docker01.png       **Docker**

[TOC]

<!-- GFM-TOC -->

* [一、解决的问题](#一解决的问题)
* [二、与虚拟机的比较](#二与虚拟机的比较)
* [三、优势](#三优势)
* [四、使用场景](#四使用场景)
* [五、镜像与容器](#五镜像与容器)
* [参考资料](#参考资料)
  <!-- GFM-TOC -->


# 一、解决的问题

由于不同的机器有不同的操作系统，以及不同的库和组件，在将一个应用部署到多台机器上需要进行大量的环境配置操作。

Docker 主要解决环境配置问题，它是一种虚拟化技术，对进程进行隔离，被隔离的进程独立于宿主操作系统和其它隔离的进程。使用 Docker 可以不修改应用程序代码，不需要开发人员学习特定环境下的技术，就能够将现有的应用程序部署在其它机器上。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/011f3ef6-d824-4d43-8b2c-36dab8eaaa72-1.png" width="400px"/> </div><br>

# 二、与虚拟机的比较

虚拟机也是一种虚拟化技术，它与 Docker 最大的区别在于它是通过模拟硬件，并在硬件上安装操作系统来实现。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/be608a77-7b7f-4f8e-87cc-f2237270bf69.png" width="500"/> </div><br>

## 启动速度

启动虚拟机需要先启动虚拟机的操作系统，再启动应用，这个过程非常慢；

而启动 Docker 相当于启动宿主操作系统上的一个进程。

## 占用资源

虚拟机是一个完整的操作系统，需要占用大量的磁盘、内存和 CPU 资源，一台机器只能开启几十个的虚拟机。

而 Docker 只是一个进程，只需要将应用以及相关的组件打包，在运行时占用很少的资源，一台机器可以开启成千上万个 Docker。

# 三、优势

除了启动速度快以及占用资源少之外，Docker 具有以下优势：

## 更容易迁移

提供一致性的运行环境。已经打包好的应用可以在不同的机器上进行迁移，而不用担心环境变化导致无法运行。

## 更容易维护

使用分层技术和镜像，使得应用可以更容易复用重复的部分。复用程度越高，维护工作也越容易。

## 更容易扩展

可以使用基础镜像进一步扩展得到新的镜像，并且官方和开源社区提供了大量的镜像，通过扩展这些镜像可以非常容易得到我们想要的镜像。

# 四、使用场景

## 持续集成

持续集成指的是频繁地将代码集成到主干上，这样能够更快地发现错误。

Docker 具有轻量级以及隔离性的特点，在将代码集成到一个 Docker 中不会对其它 Docker 产生影响。

## 提供可伸缩的云服务

根据应用的负载情况，可以很容易地增加或者减少 Docker。

## 搭建微服务架构

Docker 轻量级的特点使得它很适合用于部署、维护、组合微服务。

# 五、镜像与容器

镜像是一种静态的结构，可以看成面向对象里面的类，而容器是镜像的一个实例。

镜像包含着容器运行时所需要的代码以及其它组件，它是一种分层结构，每一层都是只读的（read-only layers）。构建镜像时，会一层一层构建，前一层是后一层的基础。镜像的这种分层存储结构很适合镜像的复用以及定制。

构建容器时，通过在镜像的基础上添加一个可写层（writable layer），用来保存着容器运行过程中的修改。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/docker-filesystems-busyboxrw.png"/> </div><br>







# 一、引言

## 	1.1 什么是docker

> - Docker 使用 Google 公司推出的 [Go 语言](https://golang.org/) 进行开发实现，基于 Linux 内核的 [cgroup](https://zh.wikipedia.org/wiki/Cgroups)，[namespace](https://en.wikipedia.org/wiki/Linux_namespaces)，以及 [AUFS](https://en.wikipedia.org/wiki/Aufs) 类的 [Union FS](https://en.wikipedia.org/wiki/Union_mount) 等技术，对进程进行封装隔离，属于 [操作系统层面的虚拟化技术](https://en.wikipedia.org/wiki/Operating-system-level_virtualization)。
>
> - Docker 在容器的基础上，进行了进一步的封装，从文件系统、网络互联到进程隔离等等，极大的简化了容器的创建和维护。使得 Docker 技术比虚拟机技术更为轻便、快捷。

##     1.2 docker的优点

> 作为一种新兴的虚拟化方式，Docker 跟传统的虚拟化方式相比具有众多的优势。
>
> 1. **更高效的利用系统资源**
>
>    由于容器不需要进行硬件虚拟以及运行完整操作系统等额外开销，Docker 对系统资源的利用率更高。无论是应用执行速度、内存损耗或者文件存储速度，都要比传统虚拟机技术更高效。因此，相比虚拟机技术，一个相同配置的主机，往往可以运行更多数量的应用。
>
> 2. **更快速的启动时间**
>
>    传统的虚拟机技术启动应用服务往往需要数分钟，而 Docker 容器应用，由于直接运行于宿主内核，无需启动完整的操作系统，因此可以做到秒级、甚至毫秒级的启动时间。大大的节约了开发、测试、部署的时间。
>
> 3. **一致的运行环境**
>
>    开发过程中一个常见的问题是环境一致性问题。由于开发环境、测试环境、生产环境不一致，导致有些 bug 并未在开发过程中被发现。而 Docker 的镜像提供了除内核外完整的运行时环境，确保了应用运行环境一致性，从而不会再出现 *「这段代码在我机器上没问题啊」* 这类问题。
>
> 4. **持续交付和部署**
>
>    对开发和运维（[DevOps](https://zh.wikipedia.org/wiki/DevOps)）人员来说，最希望的就是一次创建或配置，可以在任意地方正常运行。
>
>    使用 Docker 可以通过定制应用镜像来实现持续集成、持续交付、部署。开发人员可以通过 `Dockerfile`来进行镜像构建，并结合 [持续集成(Continuous Integration)](https://en.wikipedia.org/wiki/Continuous_integration) 系统进行集成测试，而运维人员则可以直接在生产环境中快速部署该镜像，甚至结合 [持续部署(Continuous Delivery/Deployment)](https://en.wikipedia.org/wiki/Continuous_delivery) 系统进行自动部署。
>
>    而且使用 `Dockerfile` 使镜像构建透明化，不仅仅开发团队可以理解应用运行环境，也方便运维团队理解应用运行所需条件，帮助更好的生产环境中部署该镜像。
>
> 5. **更轻松的迁移**
>
>    由于 Docker 确保了执行环境的一致性，使得应用的迁移更加容易。Docker 可以在很多平台上运行，无论是物理机、虚拟机、公有云、私有云，甚至是笔记本，其运行结果是一致的。因此用户可以很轻易的将在一个平台上运行的应用，迁移到另一个平台上，而不用担心运行环境的变化导致应用无法正常运行的情况。
>
> 6. **更轻松的维护和扩展**
>
>    Docker 使用的分层存储以及镜像的技术，使得应用重复部分的复用更为容易，也使得应用的维护更新更加简单，基于基础镜像进一步扩展镜像也变得非常简单。此外，Docker 团队同各个开源项目团队一起维护了一大批高质量的 [官方镜像](https://store.docker.com/search?q=&source=verified&type=image)，既可以直接在生产环境使用，又可以作为基础进一步定制，大大的降低了应用服务的镜像制作成本。

## 1.3 对比传统虚拟机

| 特性       | Docker容器         | 虚拟机      |
| ---------- | ------------------ | ----------- |
| 启动       | 秒级               | 分钟级      |
| 硬盘使用   | 一般为 `MB`        | 一般为 `GB` |
| 性能       | 接近原生           | 弱于        |
| 系统支持量 | 单机支持上千个容器 | 一般几十个  |

# 二、docker引擎

> Docker 引擎是一个包含以下主要组件的客户端服务器应用程序。
>
> - 一种服务器，它是一种称为守护进程并且长时间运行的程序。
> - REST API用于指定程序可以用来与守护进程通信的接口，并指示它做什么。
> - 一个有命令行界面 (CLI) 工具的客户端。 cli  command line interface

Docker 引擎组件的流程如下图所示：

# 三、docker系统架构

>Docker 包括三个基本概念:
>
>- **镜像（Image）**：Docker 镜像（Image），就相当于是一个 root 文件系统。比如官方镜像 ubuntu:16.04 就包含了完整的一套 Ubuntu16.04 最小系统的 root 文件系统。
>- **容器（Container）**：镜像（Image）和容器（Container）的关系，就像是面向对象程序设计中的类和实例一样，镜像是静态的定义，容器是镜像运行时的实体。容器可以被创建、启动、停止、删除、暂停等。
>- **仓库（Repository）**：仓库可看着一个代码控制中心，用来保存镜像。
>
>Docker 使用客户端-服务器 (C/S) 架构模式，使用远程API来管理和创建Docker容器。
>
>Docker 容器通过 Docker 镜像来创建。
>
>容器与镜像的关系类似于面向对象编程中的对象与类。

| Docker | 面向对象 |
| ------ | -------- |
| 容器   | 对象     |
| 镜像   | 类       |

| 标题            | 说明                                                         |
| --------------- | ------------------------------------------------------------ |
| 镜像(Images)    | Docker 镜像是用于创建 Docker 容器的模板。                    |
| 容器(Container) | 容器是独立运行的一个或一组应用。                             |
| 客户端(Client)  | Docker 客户端通过命令行或者其他工具使用 Docker API (<https://docs.docker.com/reference/api/docker_remote_api>) 与 Docker 的守护进程通信。 |
| 主机(Host)      | 一个物理或者虚拟的机器用于执行 Docker 守护进程和容器。       |
| 仓库(Registry)  | Docker 仓库用来保存镜像，可以理解为代码控制中的代码仓库。Docker Hub([https://hub.docker.com](https://hub.docker.com/)) 提供了庞大的镜像集合供使用。 |
| Docker Machine  | Docker Machine是一个简化Docker安装的命令行工具，通过一个简单的命令行即可在相应的平台上安装Docker，比如VirtualBox、 Digital Ocean、Microsoft Azure。 |

# 四、Ubuntu 安装 Docker

## 4.1.准备工作

> 警告：切勿在没有配置 Docker APT 源的情况下直接使用 apt 命令安装 Docker.

### 1）系统要求

Docker CE 支持以下版本的 [Ubuntu](https://www.ubuntu.com/server) 操作系统：

- Artful 17.10 (Docker CE 17.11 Edge +)
- Xenial 16.04 (LTS)
- Trusty 14.04 (LTS)

Docker CE 可以安装在 64 位的 x86 平台或 ARM 平台上。Ubuntu 发行版中，LTS（Long-Term-Support）长期支持版本，会获得 5 年的升级维护支持，这样的版本会更稳定，因此在生产环境中推荐使用 LTS 版本,当前最新的 LTS 版本为 Ubuntu 16.04。

### 2）卸载旧版本

旧版本的 Docker 称为 `docker` 或者 `docker-engine`，使用以下命令卸载旧版本：

```
$ sudo apt-get remove docker \
               docker-engine \
               docker.io
```

### 3）Ubuntu 14.04 可选内核模块

从 Ubuntu 14.04 开始，一部分内核模块移到了可选内核模块包 (`linux-image-extra-*`) ，以减少内核软件包的体积。正常安装的系统应该会包含可选内核模块包，而一些裁剪后的系统可能会将其精简掉。`AUFS` 内核驱动属于可选内核模块的一部分，作为推荐的 Docker 存储层驱动，一般建议安装可选内核模块包以使用 `AUFS`。

如果系统没有安装可选内核模块的话，可以执行下面的命令来安装可选内核模块包：

```
$ sudo apt-get update

$ sudo apt-get install \
    linux-image-extra-$(uname -r) \
    linux-image-extra-virtual
```

### 4）Ubuntu 16.04 +

Ubuntu 16.04 + 上的 Docker CE 默认使用 `overlay2` 存储层驱动,无需手动配置。

## 4.2.使用 APT 安装

### 1）安装必要的一些系统工具

```
sudo apt-get update
sudo apt-get -y install apt-transport-https ca-certificates curl software-properties-common
```

### 2）安装 GPG 证书

```
curl -fsSL http://mirrors.aliyun.com/docker-ce/linux/ubuntu/gpg | sudo apt-key add -
```

### 3）写入软件源信息

```
sudo add-apt-repository "deb [arch=amd64] http://mirrors.aliyun.com/docker-ce/linux/ubuntu $(lsb_release -cs) stable"
```

### 4）更新并安装 Docker CE

```
sudo apt-get -y update
sudo apt-get -y install docker-ce
```

> 以上命令会添加稳定版本的 Docker CE APT 镜像源，如果需要最新或者测试版本的 Docker CE 请将 stable 改为 edge 或者 test。从 Docker 17.06 开始，edge test 版本的 APT 镜像源也会包含稳定版本的 Docker。

## 4.3.使用脚本自动安装

在测试或开发环境中 Docker 官方为了简化安装流程，提供了一套便捷的安装脚本，Ubuntu 系统上可以使用这套脚本安装：

```
$ curl -fsSL get.docker.com -o get-docker.sh
# 可能会出现 404 错误，请移步下面的特别说明
$ sudo sh get-docker.sh --mirror Aliyun
```

执行这个命令后，脚本就会自动的将一切准备工作做好，并且把 Docker CE 的 Edge 版本安装在系统中。

如果出现以下提示，是因为上次更新不完全导致。

```shell
E: Could not get lock /var/lib/dpkg/lock - open (11: Resource temporarily unavailable)
E: Unable to lock the administration directory (/var/lib/dpkg/), is another process using it?
```

使用如下命令删除不完整的更新：

```
sudo rm /var/cache/apt/archives/lock
sudo rm /var/lib/dpkg/lock
```

### 特别说明

2018 年 7 月 21 日，貌似阿里云这边在做调整，故导致 Docker 的 Aliyun 安装脚本不可用，是永久性还是临时性的尚不清除，如果你已经按照之前的操作安装 Docker，请按以下步骤进行修复并重新安装

- 如果已经使用了 Aliyun 脚本安装并成功的
  - 请先卸载 Docker，命令为：`apt-get autoremove docker-ce`
  - 删除 `/etc/apt/sources.list.d` 目录下的 `docker.list` 文件
- 使用 `AzureChinaCloud` 镜像脚本重新安装，命令为：`sudo sh get-docker.sh --mirror AzureChinaCloud`

## 4.4.启动 Docker CE

```
$ sudo systemctl enable docker
$ sudo systemctl start docker
```

Ubuntu 14.04 请使用以下命令启动：

```
$ sudo service docker start
```

## 4.5.建立 docker 用户组

默认情况下，`docker` 命令会使用 [Unix socket](https://en.wikipedia.org/wiki/Unix_domain_socket) 与 Docker 引擎通讯。而只有 `root` 用户和 `docker` 组的用户才可以访问 Docker 引擎的 Unix socket。出于安全考虑，一般 Linux 系统上不会直接使用 `root`用户。因此，更好地做法是将需要使用 `docker` 的用户加入 `docker` 用户组。

建立 `docker` 组：

```
$ sudo groupadd docker
```

将当前用户加入 `docker` 组：

```
$ sudo usermod -aG docker $USER
```

退出当前终端并重新登录，进行如下测试。

## 4.6.测试 Docker 是否安装正确

```
$ docker run hello-world

Unable to find image 'hello-world:latest' locally
latest: Pulling from library/hello-world
ca4f61b1923c: Pull complete
Digest: sha256:be0cd392e45be79ffeffa6b05338b98ebb16c87b255f48e297ec7f98e123905c
Status: Downloaded newer image for hello-world:latest

Hello from Docker!
This message shows that your installation appears to be working correctly.

To generate this message, Docker took the following steps:
 1. The Docker client contacted the Docker daemon.
 2. The Docker daemon pulled the "hello-world" image from the Docker Hub.
    (amd64)
 3. The Docker daemon created a new container from that image which runs the
    executable that produces the output you are currently reading.
 4. The Docker daemon streamed that output to the Docker client, which sent it
    to your terminal.

To try something more ambitious, you can run an Ubuntu container with:
 $ docker run -it ubuntu bash

Share images, automate workflows, and more with a free Docker ID:
 https://cloud.docker.com/

For more examples and ideas, visit:
 https://docs.docker.com/engine/userguide/
```

若能正常输出以上信息，则说明安装成功。

## 4.7.镜像加速

鉴于国内网络问题，后续拉取 Docker 镜像十分缓慢，强烈建议安装 Docker 之后配置 `国内镜像加速`。

## 4.8.参考文档

- [Docker 官方 Ubuntu 安装文档](https://docs.docker.com/engine/installation/linux/docker-ce/ubuntu/)

# 五、Docker 镜像加速器

## 5.1概述

国内从 Docker Hub 拉取镜像有时会遇到困难，此时可以配置镜像加速器。Docker 官方和国内很多云服务商都提供了国内加速器服务，例如：

- [Docker 官方提供的中国 registry mirror](https://docs.docker.com/registry/recipes/mirror/#use-case-the-china-registry-mirror)
- [阿里云加速器](https://cr.console.aliyun.com/#/accelerator)
- [DaoCloud 加速器](https://www.daocloud.io/mirror#accelerator-doc)

我们以 Docker 官方加速器为例进行介绍。

## 5.2Ubuntu 14.04、Debian 7 Wheezy

对于使用 [upstart](http://upstart.ubuntu.com/) 的系统而言，编辑 `/etc/default/docker` 文件，在其中的 `DOCKER_OPTS` 中配置加速器地址：

```
DOCKER_OPTS="--registry-mirror=https://registry.docker-cn.com"
```

重新启动服务。

```
$ sudo service docker restart
```

## 5.3Ubuntu 16.04+、Debian 8+、CentOS 7

对于使用 [systemd](https://www.freedesktop.org/wiki/Software/systemd/) 的系统，请在 `/etc/docker/daemon.json` 中写入如下内容（如果文件不存在请新建该文件）

```
{
  "registry-mirrors": [
    "https://registry.docker-cn.com"
  ]
}
```

> 注意，一定要保证该文件符合 json 规范，否则 Docker 将不能启动。

阿里云镜像加速器

```
{
  "registry-mirrors": ["https://ohfqk9xf.mirror.aliyuncs.com"]
}
```



之后重新启动服务。

```
$ sudo systemctl daemon-reload
$ sudo systemctl restart docker
```

> 注意：如果您之前查看旧教程，修改了 `docker.service` 文件内容，请去掉您添加的内容（`--registry-mirror=https://registry.docker-cn.com`），这里不再赘述。

## 5.4Windows 10

对于使用 Windows 10 的系统，在系统右下角托盘 Docker 图标内右键菜单选择 `Settings`，打开配置窗口后左侧导航菜单选择 `Daemon`。在 `Registry mirrors` 一栏中填写加速器地址 `https://registry.docker-cn.com`，之后点击 `Apply` 保存后 Docker 就会重启并应用配置的镜像地址了。

## 5.5macOS

对于使用 macOS 的用户，在任务栏点击 Docker for mac 应用图标 -> Perferences... -> Daemon -> Registry mirrors。在列表中填写加速器地址 `https://registry.docker-cn.com`。修改完成之后，点击 `Apply & Restart` 按钮，Docker 就会重启并应用配置的镜像地址了。

## 5.6检查加速器是否生效

配置加速器之后，如果拉取镜像仍然十分缓慢，请手动检查加速器配置是否生效，在命令行执行 `docker info`，如果从结果中看到了如下内容，说明配置成功。

```
Registry Mirrors:
 https://registry.docker-cn.com/
```

# 六、docker仓库

> 仓库（`Repository`）是集中存放镜像的地方。
>
> 一个容易混淆的概念是注册服务器（`Registry`）。实际上注册服务器是管理仓库的具体服务器，每个服务器上可以有多个仓库，而每个仓库下面有多个镜像。从这方面来说，仓库可以被认为是一个具体的项目或目录。例如对于仓库地址 `dl.dockerpool.com/ubuntu` 来说，`dl.dockerpool.com` 是注册服务器地址，`ubuntu` 是仓库名。
>
> 大部分时候，并不需要严格区分这两者的概念。

## 6.1 Docker Hub

> 目前 Docker 官方维护了一个公共仓库 [Docker Hub](https://hub.docker.com/)，其中已经包括了数量超过 15,000 的镜像。大部分需求都可以通过在 Docker Hub 中直接下载镜像来实现。

### 6.1.1 注册

> 在 https://cloud.docker.com 免费注册一个 Docker 账号。

### 6.1.2 登录

> 可以通过执行 `docker login` 命令交互式的输入用户名及密码来完成在命令行界面登录 Docker Hub。
>
> 可以通过 `docker logout` 退出登录。

### 6.1.3 拉取镜像

> 你可以通过 `docker search` 命令来查找官方仓库中的镜像，并利用 `docker pull` 命令来将它下载到本地。
>
> 例如以 `centos` 为关键词进行搜索：
>
> ```
> $ docker search centos
> NAME                                            DESCRIPTION                                     STARS     OFFICIAL   AUTOMATED
> centos                                          The official build of CentOS.                   465       [OK]
> tianon/centos                                   CentOS 5 and 6, created using rinse instea...   28
> blalor/centos                                   Bare-bones base CentOS 6.5 image                6                    [OK]
> saltstack/centos-6-minimal                                                                      6                    [OK]
> tutum/centos-6.4                                DEPRECATED. Use tutum/centos:6.4 instead. ...   5                    [OK]
> ```
>
> 可以看到返回了很多包含关键字的镜像，其中包括镜像名字、描述、收藏数（表示该镜像的受关注程度）、是否官方创建、是否自动创建。
>
> 官方的镜像说明是官方项目组创建和维护的，automated 资源允许用户验证镜像的来源和内容。
>
> 根据是否是官方提供，可将镜像资源分为两类。
>
> 一种是类似 `centos` 这样的镜像，被称为基础镜像或根镜像。这些基础镜像由 Docker 公司创建、验证、支持、提供。这样的镜像往往使用单个单词作为名字。
>
> 还有一种类型，比如 `tianon/centos` 镜像，它是由 Docker 的用户创建并维护的，往往带有用户名称前缀。可以通过前缀 `username/` 来指定使用某个用户提供的镜像，比如 tianon 用户。
>
> 另外，在查找的时候通过 `--filter=stars=N` 参数可以指定仅显示收藏数量为 `N` 以上的镜像。
>
> 下载官方 `centos` 镜像到本地。
>
> ```
> $ docker pull centos
> Pulling repository centos
> 0b443ba03958: Download complete
> 539c0211cd76: Download complete
> 511136ea3c5a: Download complete
> 7064731afe90: Download complete
> ```

### 6.1.4 推送镜像

> 用户也可以在登录后通过 `docker push` 命令来将自己的镜像推送到 Docker Hub。
>
> 以下命令中的 `username` 请替换为你的 Docker 账号用户名。
>
> ```
> $ docker tag ubuntu:17.10 username/ubuntu:17.10
> 
> $ docker image ls
> 
> REPOSITORY                                               TAG                    IMAGE ID            CREATED             SIZE
> ubuntu                                                   17.10                  275d79972a86        6 days ago          94.6MB
> username/ubuntu                                          17.10                  275d79972a86        6 days ago          94.6MB
> 
> $ docker push username/ubuntu:17.10
> 
> $ docker search username
> 
> NAME                      DESCRIPTION                                     STARS               OFFICIAL            AUTOMATED
> username/ubuntu
> ```

# 七、docker镜像操作

> Docker 运行容器前需要本地存在对应的镜像，如果本地不存在该镜像，Docker 会从镜像仓库下载该镜像。

## 7.1 获取镜像

> 之前提到过，[Docker Hub](https://hub.docker.com/explore/) 上有大量的高质量的镜像可以用，这里我们就说一下怎么获取这些镜像。

> 从 Docker 镜像仓库获取镜像的命令是 `docker pull`。其命令格式为：
>
> docker pull [选项] [Docker Registry 地址[:端口号]/]仓库名[:标签]
>
> 例如：
>
> docker pull http://localhost:8888/myregistry/tomcat:latest
> docker pull tomcat:latest

> 具体的选项可以通过 `docker pull --help` 命令看到，这里我们说一下镜像名称的格式。
>
> - Docker 镜像仓库地址：地址的格式一般是 `<域名/IP>[:端口号]`。默认地址是 Docker Hub。
>
> - 仓库名：如之前所说，这里的仓库名是两段式名称，即 `<用户名>/<软件名>`。对于 Docker Hub，如果不给出用户名，则默认为 `library`，也就是官方镜像。
>
>   比如：
>
>   ```
>   $ docker pull ubuntu:16.04
>   16.04: Pulling from library/ubuntu
>   bf5d46315322: Pull complete
>   9f13e0ac480c: Pull complete
>   e8988b5b3097: Pull complete
>   40af181810e7: Pull complete
>   e6f7c7e5c03e: Pull complete
>   Digest: sha256:147913621d9cdea08853f6ba9116c2e27a3ceffecf3b492983ae97c3d643fbbe
>   Status: Downloaded newer image for ubuntu:16.04
>   ```
>
>   上面的命令中没有给出 Docker 镜像仓库地址，因此将会从 Docker Hub 获取镜像。而镜像名称是 `ubuntu:16.04`，因此将会获取官方镜像 `library/ubuntu` 仓库中标签为 `16.04` 的镜像。
>
>   从下载过程中可以看到我们之前提及的分层存储的概念，镜像是由多层存储所构成。下载也是一层层的去下载，并非单一文件。下载过程中给出了每一层的 ID 的前 12 位。并且下载结束后，给出该镜像完整的 `sha256` 的摘要，以确保下载一致性。
>
>   在使用上面命令的时候，你可能会发现，你所看到的层 ID 以及 `sha256` 的摘要和这里的不一样。这是因为官方镜像是一直在维护的，有任何新的 bug，或者版本更新，都会进行修复再以原来的标签发布，这样可以确保任何使用这个标签的用户可以获得更安全、更稳定的镜像。
>
>   *如果从 Docker Hub 下载镜像非常缓慢，可以参照 镜像加速器 一节配置加速器。*

## 7.2 列出镜像

> 要想 已经下载下来的镜像，可以使用 `docker image ls` 命令。
>
> ```
> $ docker image ls
> REPOSITORY           TAG                 IMAGE ID            CREATED             SIZE
> redis                latest              5f515359c7f8        5 days ago          183 MB
> nginx                latest              05a60462f8ba        5 days ago          181 MB
> mongo                3.2                 fe9198c04d62        5 days ago          342 MB
> <none>               <none>              00285df0df87        5 days ago          342 MB
> ubuntu               16.04               f753707788c5        4 weeks ago         127 MB
> ubuntu               latest              f753707788c5        4 weeks ago         127 MB
> ubuntu               14.04               1e0c3dd64ccd        4 weeks ago         188 MB
> ```
>
> 列表包含了 `仓库名`、`标签`、`镜像 ID`、`创建时间` 以及 `所占用的空间`。
>
> 其中仓库名、标签在之前的基础概念章节已经介绍过了。**镜像 ID** 则是镜像的唯一标识，一个镜像可以对应多个**标签**。因此，在上面的例子中，我们可以看到 `ubuntu:16.04` 和 `ubuntu:latest` 拥有相同的 ID，因为它们对应的是同一个镜像。

### 7.2.1镜像体积

如果仔细观察，会注意到，这里标识的所占用空间和在 Docker Hub 上看到的镜像大小不同。比如，`ubuntu:16.04` 镜像大小，在这里是 `127 MB`，但是在 [Docker Hub](https://hub.docker.com/r/library/ubuntu/tags/) 显示的却是 `50 MB`。这是因为 Docker Hub 中显示的体积是压缩后的体积。在镜像下载和上传过程中镜像是保持着压缩状态的，因此 Docker Hub 所显示的大小是网络传输中更关心的流量大小。而 `docker image ls` 显示的是镜像下载到本地后，展开的大小，准确说，是展开后的各层所占空间的总和，因为镜像到本地后，查看空间的时候，更关心的是本地磁盘空间占用的大小。

另外一个需要注意的问题是，`docker image ls` 列表中的镜像体积总和并非是所有镜像实际硬盘消耗。由于 Docker 镜像是多层存储结构，并且可以继承、复用，因此不同镜像可能会因为使用相同的基础镜像，从而拥有共同的层。由于 Docker 使用 Union FS，相同的层只需要保存一份即可，因此实际镜像硬盘占用空间很可能要比这个列表镜像大小的总和要小的多。

你可以通过以下命令来便捷的查看镜像、容器、数据卷所占用的空间。

```
$ docker system df

TYPE                TOTAL               ACTIVE              SIZE                RECLAIMABLE
Images              24                  0                   1.992GB             1.992GB (100%)
Containers          1                   0                   62.82MB             62.82MB (100%)
Local Volumes       9                   0                   652.2MB             652.2MB (100%)
Build Cache                                                 0B                  0B
```

### 7.2.2虚悬镜像

上面的镜像列表中，还可以看到一个特殊的镜像，这个镜像既没有仓库名，也没有标签，均为 `<none>`。：

```
<none>               <none>              00285df0df87        5 days ago          342 MB
```

这个镜像原本是有镜像名和标签的，原来为 `mongo:3.2`，随着官方镜像维护，发布了新版本后，重新 `docker pull mongo:3.2` 时，`mongo:3.2` 这个镜像名被转移到了新下载的镜像身上，而旧的镜像上的这个名称则被取消，从而成为了 `<none>`。除了 `docker pull` 可能导致这种情况，`docker build` 也同样可以导致这种现象。由于新旧镜像同名，旧镜像名称被取消，从而出现仓库名、标签均为 `<none>` 的镜像。这类无标签镜像也被称为 **虚悬镜像(dangling image)** ，可以用下面的命令专门显示这类镜像：

```
$ docker image ls -f dangling=true
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
<none>              <none>              00285df0df87        5 days ago          342 MB
```

一般来说，虚悬镜像已经失去了存在的价值，是可以随意删除的，可以用下面的命令删除。

```
$ docker image prune
```

### 7.2.3中间层镜像

为了加速镜像构建、重复利用资源，Docker 会利用 **中间层镜像**。所以在使用一段时间后，可能会看到一些依赖的中间层镜像。默认的 `docker image ls` 列表中只会显示顶层镜像，如果希望显示包括中间层镜像在内的所有镜像的话，需要加 `-a` 参数。

```
$ docker image ls -a
```

这样会看到很多无标签的镜像，与之前的虚悬镜像不同，这些无标签的镜像很多都是中间层镜像，是其它镜像所依赖的镜像。这些无标签镜像不应该删除，否则会导致上层镜像因为依赖丢失而出错。实际上，这些镜像也没必要删除，因为之前说过，相同的层只会存一遍，而这些镜像是别的镜像的依赖，因此并不会因为它们被列出来而多存了一份，无论如何你也会需要它们。只要删除那些依赖它们的镜像后，这些依赖的中间层镜像也会被连带删除。

## 7.3 运行镜像

> 有了镜像后，我们就能够以这个镜像为基础启动并运行一个容器。以上面的 `ubuntu:16.04` 为例，如果我们打算启动里面的 `bash` 并且进行交互式操作的话，可以执行下面的命令。
>
> ```
> $ docker run -it --rm \
>     ubuntu:16.04 \
>     bash
> 
> root@e7009c6ce357:/# cat /etc/os-release
> NAME="Ubuntu"
> VERSION="16.04.4 LTS, Trusty Tahr"
> ID=ubuntu
> ID_LIKE=debian
> PRETTY_NAME="Ubuntu 16.04.4 LTS"
> VERSION_ID="16.04"
> HOME_URL="http://www.ubuntu.com/"
> SUPPORT_URL="http://help.ubuntu.com/"
> BUG_REPORT_URL="http://bugs.launchpad.net/ubuntu/"
> ```
>
> `docker run` 就是运行容器的命令，我们这里简要的说明一下上面用到的参数。
>
> - `-it`：这是两个参数，一个是 `-i`：交互式操作，一个是 `-t` 终端。我们这里打算进入 `bash` 执行一些命令并查看返回结果，因此我们需要交互式终端。
> - `--rm`：这个参数是说容器退出后随之将其删除。默认情况下，为了排障需求，退出的容器并不会立即删除，除非手动 `docker rm`。我们这里只是随便执行个命令，看看结果，不需要排障和保留结果，因此使用 `--rm` 可以避免浪费空间。
> - `ubuntu:16.04`：这是指用 `ubuntu:16.04` 镜像为基础来启动容器。
> - `bash`：放在镜像名后的是**命令**，这里我们希望有个交互式 Shell，因此用的是 `bash`。
>
> 进入容器后，我们可以在 Shell 下操作，执行任何所需的命令。这里，我们执行了 `cat /etc/os-release`，这是 Linux 常用的查看当前系统版本的命令，从返回的结果可以看到容器内是 `Ubuntu 16.04.4 LTS` 系统。
>
> 最后我们通过 `exit` 退出了这个容器。

## 7.4删除镜像

> 如果要删除本地的镜像，可以使用 `docker image rm` 命令，其格式为：
>
> ```
> $ docker image rm [选项] <镜像1> [<镜像2> ...]
> docker rmi 
> ```

> 其中，`<镜像>` 可以是 `镜像短 ID`、`镜像长 ID`、`镜像名` 或者 `镜像摘要`。
>
> 比如我们有这么一些镜像：
>
> ```
> $ docker image ls
> REPOSITORY                  TAG                 IMAGE ID            CREATED             SIZE
> centos                      latest              0584b3d2cf6d        3 weeks ago         196.5 MB
> redis                       alpine              501ad78535f0        3 weeks ago         21.03 MB
> docker                      latest              cf693ec9b5c7        3 weeks ago         105.1 MB
> nginx                       latest              e43d811ce2f4        5 weeks ago         181.5 MB
> ```
>
> 我们可以用镜像的完整 ID，也称为 `长 ID`，来删除镜像。使用脚本的时候可能会用长 ID，但是人工输入就太累了，所以更多的时候是用 `短 ID` 来删除镜像。`docker image ls` 默认列出的就已经是短 ID 了，一般取前3个字符以上，只要足够区分于别的镜像就可以了。
>
> 比如这里，如果我们要删除 `redis:alpine` 镜像，可以执行：
>
> ```
> $ docker image rm 501
> Untagged: redis:alpine
> Untagged: redis@sha256:f1ed3708f538b537eb9c2a7dd50dc90a706f7debd7e1196c9264edeea521a86d
> Deleted: sha256:501ad78535f015d88872e13fa87a828425117e3d28075d0c117932b05bf189b7
> Deleted: sha256:96167737e29ca8e9d74982ef2a0dda76ed7b430da55e321c071f0dbff8c2899b
> Deleted: sha256:32770d1dcf835f192cafd6b9263b7b597a1778a403a109e2cc2ee866f74adf23
> Deleted: sha256:127227698ad74a5846ff5153475e03439d96d4b1c7f2a449c7a826ef74a2d2fa
> Deleted: sha256:1333ecc582459bac54e1437335c0816bc17634e131ea0cc48daa27d32c75eab3
> Deleted: sha256:4fc455b921edf9c4aea207c51ab39b10b06540c8b4825ba57b3feed1668fa7c7
> ```
>
> 我们也可以用`镜像名`，也就是 `<仓库名>:<标签>`，来删除镜像。
>
> ```
> $ docker image rm centos
> Untagged: centos:latest
> Untagged: centos@sha256:b2f9d1c0ff5f87a4743104d099a3d561002ac500db1b9bfa02a783a46e0d366c
> Deleted: sha256:0584b3d2cf6d235ee310cf14b54667d889887b838d3f3d3033acd70fc3c48b8a
> Deleted: sha256:97ca462ad9eeae25941546209454496e1d66749d53dfa2ee32bf1faabd239d38
> ```
>
> 当然，更精确的是使用 `镜像摘要` 删除镜像。
>
> ```
> $ docker image ls --digests
> REPOSITORY                  TAG                 DIGEST                                                                    IMAGE ID            CREATED             SIZE
> node                        slim                sha256:b4f0e0bdeb578043c1ea6862f0d40cc4afe32a4a582f3be235a3b164422be228   6e0c4c8e3913        3 weeks ago         214 MB
> 
> $ docker image rm node@sha256:b4f0e0bdeb578043c1ea6862f0d40cc4afe32a4a582f3be235a3b164422be228
> Untagged: node@sha256:b4f0e0bdeb578043c1ea6862f0d40cc4afe32a4a582f3be235a3b164422be228
> ```

# 八、docker容器

> 容器是独立运行的一个或一组应用，以及它们的运行态环境。对应的，虚拟机可以理解为模拟运行的一整套操作系统（提供了运行态环境和其他系统环境）和跑在上面的应用。

## 8.1 启动容器

> 一种是基于镜像新建一个容器并启动，另外一个是将在终止状态（`stopped`）的容器重新启动。

### 8.1.1 新建并启动

> 所需要的命令主要为 `docker run`。
>
> 例如，下面的命令输出一个 “Hello World”，之后终止容器。
>
> ```
> $ docker run ubuntu:14.04 /bin/echo 'Hello world'
> Hello world
> ```
>
> 这跟在本地直接执行 `/bin/echo 'hello world'` 几乎感觉不出任何区别。
>
> 下面的命令则启动一个 bash 终端，允许用户进行交互。
>
> ```
> $ docker run -t -i ubuntu:14.04 /bin/bash
> root@af8bae53bdd3:/#
> ```
>
> 其中，`-t` 选项让Docker分配一个伪终端（pseudo-tty）并绑定到容器的标准输入上， `-i` 则让容器的标准输入保持打开。
>
> 在交互模式下，用户可以通过所创建的终端来输入命令，例如
>
> ```
> root@af8bae53bdd3:/# pwd
> /
> root@af8bae53bdd3:/# ls
> bin boot dev etc home lib lib64 media mnt opt proc root run sbin srv sys tmp usr var
> ```
>
> 当利用 `docker run` 来创建容器时，Docker 在后台运行的标准操作包括：
>
> - 检查本地是否存在指定的镜像，不存在就从公有仓库下载
> - 利用镜像创建并启动一个容器
> - 分配一个文件系统，并在只读的镜像层外面挂载一层可读写层
> - 从宿主主机配置的网桥接口中桥接一个虚拟接口到容器中去
> - 从地址池配置一个 ip 地址给容器
> - 执行用户指定的应用程序
> - 执行完毕后容器被终止

### 8.1.2 启动已终止的容器

> 可以利用 `docker container start` 命令，直接将一个已经终止的容器启动运行。
>
> 容器的核心为所执行的应用程序，所需要的资源都是应用程序运行所必需的。除此之外，并没有其它的资源。可以在伪终端中利用 `ps` 或 `top` 来查看进程信息。
>
> ```
> root@ba267838cc1b:/# ps
>   PID TTY          TIME CMD
>     1 ?        00:00:00 bash
>    11 ?        00:00:00 ps
> ```
>
> 可见，容器中仅运行了指定的 bash 应用。这种特点使得 Docker 对资源的利用率极高，是货真价实的轻量级虚拟化。

### 8.1.3 后台启动容器

> 更多的时候，需要让 Docker 在后台运行而不是直接把执行命令的结果输出在当前宿主机下。此时，可以通过添加 `-d` 参数来实现。
>
> 下面举两个例子来说明一下。
>
> 如果不使用 `-d` 参数运行容器。
>
> ```
> $ docker run ubuntu:17.10 /bin/sh -c "while true; do echo hello world; sleep 1; done"
> hello world
> hello world
> hello world
> hello world
> ```
>
> 容器会把输出的结果 (STDOUT) 打印到宿主机上面
>
> 如果使用了 `-d` 参数运行容器。
>
> ```
> $ docker run -d ubuntu:17.10 /bin/sh -c "while true; do echo hello world; sleep 1; done"
> 77b2dc01fe0f3f1265df143181e7b9af5e05279a884f4776ee75350ea9d8017a
> ```
>
> 此时容器会在后台运行并不会把输出的结果 (STDOUT) 打印到宿主机上面(输出结果可以用 `docker logs`查看)。

## 8.2 端口映射

> docker容器中的端口映射： 
>
> ### 语法：`-p`  系统端口：容器端口
>
> docker run **-p** 80:8080  tomcat:8.0.25

## 8.3 容器命名

> docker容器中的命名
>
> ###   语法：`--name`  容器名称
>
> docker run **-p** 80:8080 --name mytomcat  tomcat:8.0.25

## 8.4 终止容器

> 可以使用 `docker container stop` 来终止一个运行中的容器。
>
> 此外，当 Docker 容器中指定的应用终结时，容器也自动终止。

## 8.5 删除容器

> 删除容器使用 `docker rm`命令：
>
> ```
> $ docker rm -f 1e560fca3906
> ```

## 8.6 日志查看

> **docker logs :** 获取容器的日志
>
> ### 语法
>
> ```
> docker logs [OPTIONS] CONTAINER
> ```
>
> OPTIONS说明：
>
> - **-f :** 跟踪日志输出
>
> - **--since :**显示某个开始时间的所有日志
>
> - **-t :** 显示时间戳
>
> - **--tail :**仅列出最新N条容器日志
>
>   ### 案例
>
>   ```
>   docker logs -f mynginx
>   ```

## 8.7 进入容器

> 某些时候需要进入容器进行操作，包括使用 `docker attach` 命令或 `docker exec` 命令，推荐大家使用 `docker exec` 命令，原因会在下面说明。
>
> ## 1.`attach` 命令
>
> `docker attach` 是 Docker 自带的命令。下面示例如何使用该命令。
>
> ```
> $ docker run -dit ubuntu
> 243c32535da7d142fb0e6df616a3c3ada0b8ab417937c853a9e1c251f499f550
> 
> $ docker container ls
> CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
> 243c32535da7        ubuntu:latest       "/bin/bash"         18 seconds ago      Up 17 seconds                           nostalgic_hypatia
> 
> $ docker attach 243c
> root@243c32535da7:/#
> ```
>
> *注意：* 如果从这个 stdin 中 exit，会导致容器的停止。
>
> ## 2.`exec` 命令
>
> ### -i -t 参数
>
> `docker exec` 后边可以跟多个参数，这里主要说明 `-i` `-t` 参数。
>
> 只用 `-i` 参数时，由于没有分配伪终端，界面没有我们熟悉的 Linux 命令提示符，但命令执行结果仍然可以返回。
>
> 当 `-i` `-t` 参数一起使用时，则可以看到我们熟悉的 Linux 命令提示符。
>
> ```
> $ docker run -dit ubuntu
> 69d137adef7a8a689cbcb059e94da5489d3cddd240ff675c640c8d96e84fe1f6
> 
> $ docker container ls
> CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
> 69d137adef7a        ubuntu:latest       "/bin/bash"         18 seconds ago      Up 17 seconds                           zealous_swirles
> 
> $ docker exec -i 69d1 bash
> ls
> bin
> boot
> dev
> ...
> 
> $ docker exec -it 69d1 bash
> root@69d137adef7a:/#
> ```
>
> 如果从这个 stdin 中 exit，不会导致容器的停止。这就是为什么推荐大家使用 `docker exec` 的原因。
>
> 更多参数说明请使用 `docker exec --help` 查看。



# 九、数据卷volume

## 9.1.概述

> `数据卷` 是一个可供一个或多个容器使用的特殊目录，它绕过 UFS，可以提供很多有用的特性：
>
> - `数据卷` 可以在容器之间共享和重用
> - 对 `数据卷` 的修改会立马生效
> - 对 `数据卷` 的更新，不会影响镜像
> - `数据卷` 默认会一直存在，即使容器被删

> 注意：`数据卷` 的使用，类似于 Linux 下对目录或文件进行 mount，镜像中的被指定为挂载点的目录中的文件会隐藏掉，能显示看的是挂载的 `数据卷`。

## 9.2.创建一个数据卷

```
$ docker volume create my-vol
```

## 9.3 查看所有的 数据卷

```
$ docker volume ls

local               my-vol
```

## 9.4.启动一个挂载数据卷的容器

> 在用 `docker run` 命令的时候，使用 `--mount` 或 `-v` 标记来将 `数据卷` 挂载到容器里。在一次 `docker run`中可以挂载多个 `数据卷`。
>
> 下面创建一个名为 `web` 的容器，并加载一个 `数据卷` 到容器的 `/webapp` 目录。
>
> ```
> $ docker run -d -P \
>  --name web \
>  # -v my-vol:/wepapp \
>  --mount source=my-vol,target=/webapp \
>  training/webapp \
>  python app.py
> ```

## 9.5.查看数据卷的具体信息

> 在主机里使用以下命令可以查看 `web` 容器的信息
>
> ```
> $ docker inspect web
> ```
>
> `数据卷` 信息在 "Mounts" Key 下面
>
> ```
> "Mounts": [
>  {
>      "Type": "volume",
>      "Name": "my-vol",
>      "Source": "/var/lib/docker/volumes/my-vol/_data",
>      "Destination": "/app",
>      "Driver": "local",
>      "Mode": "",
>      "RW": true,
>      "Propagation": ""
>  }
> ],
> ```

## 9.6.删除数据卷

> ```
> $ docker volume rm my-vol
> ```
>
> `数据卷` 是被设计用来持久化数据的，它的生命周期独立于容器，Docker 不会在容器被删除后自动删除 `数据卷`，并且也不存在垃圾回收这样的机制来处理没有任何容器引用的 `数据卷`。如果需要在删除容器的同时移除数据卷。可以在删除容器的时候使用 `docker rm -v` 这个命令。
>
> 无主的数据卷可能会占据很多空间，要清理请使用以下命令
>
> ```
> $ docker volume prune
> ```

# 十、docker应用

## 10.1 安装mysql

>  可以访问 MySQL 镜像库地址：https://hub.docker.com/_/mysql?tab=tags 。
>
> 拉取latest版本mysql
>
> ```
> docker pull mysql
> ```
>
> 安装完成后，我们可以使用以下命令来运行 mysql 容器：
>
> ```
> docker run -itd --name mysql-test -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql
> ```
>
> 参数说明：
>
> - **-p 3306:3306** ：映射容器服务的 3306 端口到宿主机的 3306 端口，外部主机可以直接通过 **宿主机ip:3306** 访问到 MySQL 的服务。
> - **MYSQL_ROOT_PASSWORD=123456**：设置 MySQL 服务 root 用户的密码。

## 10.2安装tomcat

> 拉取latest版本tomcat
>
> ### docker pull tomcat

## 10.3部署之前的ssm工程

> 1.在当前路径下，新建test文件夹
>
> 2.将之前的ssm工程目录拷贝到test文件夹下
>
> 3.执行以下命令：启动tomcat
>
> ```
> docker run --name tomcat -p 8080:8080 -v $PWD/test:/usr/local/tomcat/webapps/test -d tomcat 
> ```

# 十一、 dockerFile 定制镜像部署

> 镜像的定制实际上就是定制每一层所添加的配置、文件。如果我们可以把每一层修改、安装、构建、操作的命令都写入一个脚本，用这个脚本来构建、定制镜像，那么之前提及的无法重复的问题、镜像构建透明性的问题、体积的问题就都会解决。这个脚本就是 Dockerfile。
>
> Dockerfile 是一个文本文件，其内包含了一条条的**指令(Instruction)**，每一条指令构建一层，因此每一条指令的内容，就是描述该层应当如何构建。

## 11.1 创建dockerfile

> 以之前定制 `nginx` 镜像为例，这次我们使用 Dockerfile 来定制。
>
> 在一个空白目录中，建立一个文本文件，并命名为 `Dockerfile`：
>
> ```
> $ mkdir mynginx
> $ cd mynginx
> $ touch Dockerfile
> ```
>
> 其内容为：
>
> ```
> FROM nginx
> RUN echo '<h1>Hello, Docker!</h1>' > /usr/share/nginx/html/index.html
> ```
>
> 这个 Dockerfile 很简单，一共就两行。涉及到了两条指令，`FROM` 和 `RUN`。

## 11.2构建镜像

> 在 `Dockerfile` 文件所在目录执行：
>
> ```
> $ docker build -t nginx:v3 .
> Sending build context to Docker daemon 2.048 kB
> Step 1 : FROM nginx
> ---> e43d811ce2f4
> Step 2 : RUN echo '<h1>Hello, Docker!</h1>' > /usr/share/nginx/html/index.html
> ---> Running in 9cdc27646c7b
> ---> 44aa4490ce2c
> Removing intermediate container 9cdc27646c7b
> Successfully built 44aa4490ce2c
> ```
>
> 从命令的输出结果中，我们可以清晰的看到镜像的构建过程。在 `Step 2` 中，如同我们之前所说的那样，`RUN` 指令启动了一个容器 `9cdc27646c7b`，执行了所要求的命令，并最后提交了这一层 `44aa4490ce2c`，随后删除了所用到的这个容器 `9cdc27646c7b`。
>
> 这里我们使用了 `docker build` 命令进行镜像构建。其格式为：
>
> ```
> docker build [选项] <上下文路径/URL/->
> ```
>
> 在这里我们指定了最终镜像的名称 `-t nginx:v3`，构建成功后，我们可以像之前运行 `nginx:v2` 那样来运行这个镜像，其结果会和 `nginx:v2` 一样。

### 11.2.1 镜像构建上下文

> ## 镜像构建上下文（Context）
>
> 如果注意，会看到 `docker build` 命令最后有一个 `.`。`.` 表示当前目录，而 `Dockerfile` 就在当前目录，因此不少初学者以为这个路径是在指定 `Dockerfile` 所在路径，这么理解其实是不准确的。如果对应上面的命令格式，你可能会发现，这是在指定**上下文路径**。那么什么是上下文呢？
>
> 首先我们要理解 `docker build` 的工作原理。Docker 在运行时分为 Docker 引擎（也就是服务端守护进程）和客户端工具。Docker 的引擎提供了一组 REST API，被称为 [Docker Remote API](https://docs.docker.com/engine/reference/api/docker_remote_api/)，而如 `docker`命令这样的客户端工具，则是通过这组 API 与 Docker 引擎交互，从而完成各种功能。因此，虽然表面上我们好像是在本机执行各种 `docker` 功能，但实际上，一切都是使用的远程调用形式在服务端（Docker 引擎）完成。也因为这种 C/S 设计，让我们操作远程服务器的 Docker 引擎变得轻而易举。
>
> 
>
> 当我们进行镜像构建的时候，并非所有定制都会通过 `RUN` 指令完成，经常会需要将一些本地文件复制进镜像，比如通过 `COPY` 指令、`ADD` 指令等。而 `docker build` 命令构建镜像，其实并非在本地构建，而是在服务端，也就是 Docker 引擎中构建的。那么在这种客户端/服务端的架构中，如何才能让服务端获得本地文件呢？ROOT.zip?
>
> docker build -t mytomcat .
>
> 这就引入了上下文的概念。当构建的时候，用户会指定构建镜像上下文的路径，`docker build` 命令得知这个路径后，**会将路径下的所有内容打包，然后上传给 Docker 引擎**。这样 Docker 引擎收到这个上下文包后，展开就会获得构建镜像所需的一切文件。
>
> 如果在 `Dockerfile` 中这么写：
>
> ```
> COPY ./package.json /app/
> ```
>
> 这并不是要复制执行 `docker build` 命令所在的目录下的 `package.json`，也不是复制 `Dockerfile` 所在目录下的 `package.json`，而是复制 **上下文（context）** 目录下的 `package.json`。
>
> 因此，`COPY` 这类指令中的源文件的路径都是*相对路径*。这也是初学者经常会问的为什么 `COPY ../package.json /app` 或者 `COPY /opt/xxxx /app` 无法工作的原因，因为这些路径已经超出了上下文的范围，Docker 引擎无法获得这些位置的文件。如果真的需要那些文件，应该将它们复制到上下文目录中去。
>
> 现在就可以理解刚才的命令 `docker build -t nginx:v3 .` 中的这个 `.`，实际上是在指定上下文的目录，`docker build` 命令会将该目录下的内容打包交给 Docker 引擎以帮助构建镜像。
>
> 如果观察 `docker build` 输出，我们其实已经看到了这个发送上下文的过程：
>
> ```
> $ docker build -t nginx:v3 .
> Sending build context to Docker daemon 2.048 kB
> ...
> ```
>
> 理解构建上下文对于镜像构建是很重要的，避免犯一些不应该的错误。比如有些初学者在发现 `COPY /opt/xxxx /app` 不工作后，于是干脆将 `Dockerfile` 放到了硬盘根目录去构建，结果发现 `docker build` 执行后，在发送一个几十 GB 的东西，极为缓慢而且很容易构建失败。那是因为这种做法是在让 `docker build` 打包整个硬盘，这显然是使用错误。
>
> 一般来说，应该会将 `Dockerfile` 置于一个空目录下，或者项目根目录下。如果该目录下没有所需文件，那么应该把所需文件复制一份过来。如果目录下有些东西确实不希望构建时传给 Docker 引擎，那么可以用 `.gitignore` 一样的语法写一个 `.dockerignore`，该文件是用于剔除不需要作为上下文传递给 Docker 引擎的。
>
> 那么为什么会有人误以为 `.` 是指定 `Dockerfile` 所在目录呢？这是因为在默认情况下，如果不额外指定 `Dockerfile` 的话，会将上下文目录下的名为 `Dockerfile` 的文件作为 Dockerfile。
>
> 这只是默认行为，实际上 `Dockerfile` 的文件名并不要求必须为 `Dockerfile`，而且并不要求必须位于上下文目录中，比如可以用 `-f ../Dockerfile.php` 参数指定某个文件作为 `Dockerfile`。
>
> 当然，一般大家习惯性的会使用默认的文件名 `Dockerfile`，以及会将其置于镜像构建上下文目录中。

## 11.3 利用Dockerfile构建SSM项目 

### 11.3.1.新建dockerfile文件，

### 11.3.2.编辑该DockerFile文件，内容如下

> FROM tomcat
>
> WORKDIR /usr/local/tomcat/webapps/ROOT
>
> RUN rm -rf *
>
> RUN echo “hello docker” > index.html

### 11.3.3.利用dockerfile生成自己的镜像

> docker build -t mytomcat **.**    

### 11.3.4.构建结果展示

![1585276964262](构建自定义镜像.png)

## 11.4 DockerFile相关指令

### 11.4.1 FROM指令： 指定基础镜像

> 所谓定制镜像，那一定是以一个镜像为基础，在其上进行定制。就像我们之前运行了一个 `nginx` 镜像的容器，再进行修改一样，基础镜像是必须指定的。而 `FROM` 就是指定**基础镜像**，因此一个 `Dockerfile`中 `FROM` 是必备的指令，并且必须是第一条指令。
>
> 在 [Docker Store](https://store.docker.com/) 上有非常多的高质量的官方镜像，有可以直接拿来使用的服务类的镜像，如 [`nginx`](https://store.docker.com/images/nginx/)、[`redis`](https://store.docker.com/images/redis/)、[`mongo`](https://store.docker.com/images/mongo/)、[`mysql`](https://store.docker.com/images/mysql/)、[`httpd`](https://store.docker.com/images/httpd/)、[`php`](https://store.docker.com/images/php/)、[`tomcat`](https://store.docker.com/images/tomcat/) 等；也有一些方便开发、构建、运行各种语言应用的镜像，如 [`node`](https://store.docker.com/images/node)、[`openjdk`](https://store.docker.com/images/openjdk/)、[`python`](https://store.docker.com/images/python/)、[`ruby`](https://store.docker.com/images/ruby/)、[`golang`](https://store.docker.com/images/golang/) 等。可以在其中寻找一个最符合我们最终目标的镜像为基础镜像进行定制。
>
> 如果没有找到对应服务的镜像，官方镜像中还提供了一些更为基础的操作系统镜像，如 [`ubuntu`](https://store.docker.com/images/ubuntu/)、[`debian`](https://store.docker.com/images/debian/)、[`centos`](https://store.docker.com/images/centos/)、[`fedora`](https://store.docker.com/images/fedora/)、[`alpine`](https://store.docker.com/images/alpine/) 等，这些操作系统的软件库为我们提供了更广阔的扩展空间。

### 11.4.2 RUN指令：执行命令

> ## RUN 执行命令
>
> `RUN` 指令是用来执行命令行命令的。由于命令行的强大能力，`RUN` 指令在定制镜像时是最常用的指令之一。其格式有两种：
>
> - *shell* 格式：`RUN <命令>`，就像直接在命令行中输入的命令一样。刚才写的 Dockerfile 中的 `RUN` 指令就是这种格式。
>
> ```
> RUN echo '<h1>Hello, Docker!</h1>' > /usr/share/nginx/html/index.html
> ```
>
> - *exec* 格式：`RUN ["可执行文件", "参数1", "参数2"]`，这更像是函数调用中的格式。
>
> 既然 `RUN` 就像 Shell 脚本一样可以执行命令，那么我们是否就可以像 Shell 脚本一样把每个命令对应一个 RUN 呢？比如这样：
>
> ```
> FROM debian:jessie
> 
> RUN apt-get update
> RUN apt-get install -y gcc libc6-dev make
> RUN wget -O redis.tar.gz "http://download.redis.io/releases/redis-3.2.5.tar.gz"
> RUN mkdir -p /usr/src/redis
> RUN tar -xzf redis.tar.gz -C /usr/src/redis --strip-components=1
> RUN make -C /usr/src/redis
> RUN make -C /usr/src/redis install
> ```
>
> 之前说过，Dockerfile 中每一个指令都会建立一层，`RUN` 也不例外。每一个 `RUN` 的行为，就和刚才我们手工建立镜像的过程一样：新建立一层，在其上执行这些命令，执行结束后，`commit` 这一层的修改，构成新的镜像。
>
> 而上面的这种写法，创建了 7 层镜像。这是完全没有意义的，而且很多运行时不需要的东西，都被装进了镜像里，比如编译环境、更新的软件包等等。结果就是产生非常臃肿、非常多层的镜像，不仅仅增加了构建部署的时间，也很容易出错。 这是很多初学 Docker 的人常犯的一个错误。
>
> 上面的 `Dockerfile` 正确的写法应该是这样：
>
> ```
> FROM debian:jessie
> 
> RUN buildDeps='gcc libc6-dev make' \
> && apt-get update \
> && apt-get install -y $buildDeps \
> && wget -O redis.tar.gz "http://download.redis.io/releases/redis-3.2.5.tar.gz" \
> && mkdir -p /usr/src/redis \
> && tar -xzf redis.tar.gz -C /usr/src/redis --strip-components=1 \
> && make -C /usr/src/redis \
> && make -C /usr/src/redis install \
> && rm -rf /var/lib/apt/lists/* \
> && rm redis.tar.gz \
> && rm -r /usr/src/redis \
> && apt-get purge -y --auto-remove $buildDeps
> ```
>
> 首先，之前所有的命令只有一个目的，就是编译、安装 redis 可执行文件。因此没有必要建立很多层，这只是一层的事情。因此，这里没有使用很多个 `RUN` 对一一对应不同的命令，而是仅仅使用一个 `RUN` 指令，并使用 `&&` 将各个所需命令串联起来。将之前的 7 层，简化为了 1 层。在撰写 Dockerfile 的时候，要经常提醒自己，这并不是在写 Shell 脚本，而是在定义每一层该如何构建。
>
> 并且，这里为了格式化还进行了换行。Dockerfile 支持 Shell 类的行尾添加 `\` 的命令换行方式，以及行首 `#` 进行注释的格式。良好的格式，比如换行、缩进、注释等，会让维护、排障更为容易，这是一个比较好的习惯。
>
> 此外，还可以看到这一组命令的最后添加了清理工作的命令，删除了为了编译构建所需要的软件，清理了所有下载、展开的文件，并且还清理了 `apt` 缓存文件。这是很重要的一步，我们之前说过，镜像是多层存储，每一层的东西并不会在下一层被删除，会一直跟随着镜像。因此镜像构建时，一定要确保每一层只添加真正需要添加的东西，任何无关的东西都应该清理掉。

### 11.4.3 COPY指令

> 格式：
>
> - `COPY <源路径>... <目标路径>`
> - `COPY ["<源路径1>",... "<目标路径>"]`
>
> 和 `RUN` 指令一样，也有两种格式，一种类似于命令行，一种类似于函数调用。
>
> `COPY` 指令将从构建上下文目录中 `<源路径>` 的文件/目录复制到新的一层的镜像内的 `<目标路径>` 位置。比如：
>
> ```
> COPY package.json /usr/src/app/
> ```
>
> `<源路径>` 可以是多个，甚至可以是通配符，其通配符规则要满足 Go 的 [`filepath.Match`](https://golang.org/pkg/path/filepath/#Match) 规则，如：
>
> ```
> COPY hom* /mydir/
> COPY hom?.txt /mydir/
> ```
>
> `<目标路径>` 可以是容器内的绝对路径，也可以是相对于工作目录的相对路径（工作目录可以用 `WORKDIR`指令来指定）。目标路径不需要事先创建，如果目录不存在会在复制文件前先行创建缺失目录。
>
> 此外，还需要注意一点，使用 `COPY` 指令，源文件的各种元数据都会保留。比如读、写、执行权限、文件变更时间等。这个特性对于镜像定制很有用。特别是构建相关文件都在使用 Git 进行管理的时候。

### 11.4.4 ADD指令

> `ADD` 指令和 `COPY` 的格式和性质基本一致。但是在 `COPY` 基础上增加了一些功能。
>
> 比如 `<源路径>` 可以是一个 `URL`，这种情况下，Docker 引擎会试图去下载这个链接的文件放到 `<目标路径>` 去。下载后的文件权限自动设置为 `600`，如果这并不是想要的权限，那么还需要增加额外的一层 `RUN` 进行权限调整，另外，如果下载的是个压缩包，需要解压缩，也一样还需要额外的一层 `RUN` 指令进行解压缩。所以不如直接使用 `RUN` 指令，然后使用 `wget` 或者 `curl` 工具下载，处理权限、解压缩、然后清理无用文件更合理。因此，这个功能其实并不实用，而且不推荐使用。
>
> 如果 `<源路径>` 为一个 `tar` 压缩文件的话，压缩格式为 `gzip`, `bzip2` 以及 `xz` 的情况下，`ADD` 指令将会自动解压缩这个压缩文件到 `<目标路径>` 去。
>
> 在某些情况下，这个自动解压缩的功能非常有用，比如官方镜像 `ubuntu` 中：
>
> ```
> FROM scratch
> ADD ubuntu-xenial-core-cloudimg-amd64-root.tar.gz /
> ...
> ```
>
> 但在某些情况下，如果我们真的是希望复制个压缩文件进去，而不解压缩，这时就不可以使用 `ADD` 命令了。
>
> 在 Docker 官方的 `Dockerfile 最佳实践文档` 中要求，尽可能的使用 `COPY`，因为 `COPY` 的语义很明确，就是复制文件而已，而 `ADD` 则包含了更复杂的功能，其行为也不一定很清晰。最适合使用 `ADD` 的场合，就是所提及的需要自动解压缩的场合。
>
> 另外需要注意的是，`ADD` 指令会令镜像构建缓存失效，从而可能会令镜像构建变得比较缓慢。
>
> 因此在 `COPY` 和 `ADD` 指令中选择的时候，可以遵循这样的原则，所有的文件复制均使用 `COPY` 指令，仅在需要自动解压缩的场合使用 `ADD`。

### 11.4.5 CMD指令

> `CMD` 指令的格式和 `RUN` 相似，也是两种格式：
>
> - `shell` 格式：`CMD <命令>`
> - `exec` 格式：`CMD ["可执行文件", "参数1", "参数2"...]`
> - 参数列表格式：`CMD ["参数1", "参数2"...]`。在指定了 `ENTRYPOINT` 指令后，用 `CMD` 指定具体的参数。
>
> 之前介绍容器的时候曾经说过，Docker 不是虚拟机，容器就是进程。既然是进程，那么在启动容器的时候，需要指定所运行的程序及参数。`CMD` 指令就是用于指定默认的容器主进程的启动命令的。
>
> 在运行时可以指定新的命令来替代镜像设置中的这个默认命令，比如，`ubuntu` 镜像默认的 `CMD` 是 `/bin/bash`，如果我们直接 `docker run -it ubuntu` 的话，会直接进入 `bash`。我们也可以在运行时指定运行别的命令，如 `docker run -it ubuntu cat /etc/os-release`。这就是用 `cat /etc/os-release` 命令替换了默认的 `/bin/bash` 命令了，输出了系统版本信息。
>
> 在指令格式上，一般推荐使用 `exec` 格式，这类格式在解析时会被解析为 JSON 数组，因此一定要使用双引号 `"`，而不要使用单引号。
>
> 如果使用 `shell` 格式的话，实际的命令会被包装为 `sh -c` 的参数的形式进行执行。比如：
>
> ```
> CMD echo $HOME
> ```
>
> 在实际执行中，会将其变更为：
>
> ```
> CMD [ "sh", "-c", "echo $HOME" ]
> ```
>
> 这就是为什么我们可以使用环境变量的原因，因为这些环境变量会被 shell 进行解析处理。
>
> 提到 `CMD` 就不得不提容器中应用在前台执行和后台执行的问题。这是初学者常出现的一个混淆。
>
> Docker 不是虚拟机，容器中的应用都应该以前台执行，而不是像虚拟机、物理机里面那样，用 upstart/systemd 去启动后台服务，容器内没有后台服务的概念。
>
> 一些初学者将 `CMD` 写为：
>
> ```
> CMD service nginx start
> ```
>
> 然后发现容器执行后就立即退出了。甚至在容器内去使用 `systemctl` 命令结果却发现根本执行不了。这就是因为没有搞明白前台、后台的概念，没有区分容器和虚拟机的差异，依旧在以传统虚拟机的角度去理解容器。
>
> 对于容器而言，其启动程序就是容器应用进程，容器就是为了主进程而存在的，主进程退出，容器就失去了存在的意义，从而退出，其它辅助进程不是它需要关心的东西。
>
> 而使用 `service nginx start` 命令，则是希望 upstart 来以后台守护进程形式启动 `nginx` 服务。而刚才说了 `CMD service nginx start` 会被理解为 `CMD [ "sh", "-c", "service nginx start"]`，因此主进程实际上是 `sh`。那么当 `service nginx start` 命令结束后，`sh` 也就结束了，`sh` 作为主进程退出了，自然就会令容器退出。
>
> 正确的做法是直接执行 `nginx` 可执行文件，并且要求以前台形式运行。比如：
>
> ```
> CMD ["nginx", "-g", "daemon off;"]
> ```

### 11.4.6 ENTRYPOINT指令

> ENTRYPOINT` 的格式和 `RUN` 指令格式一样，分为 `exec` 格式和 `shell` 格式。
>
> `ENTRYPOINT` 的目的和 `CMD` 一样，都是在指定容器启动程序及参数。`ENTRYPOINT` 在运行时也可以替代，不过比 `CMD` 要略显繁琐，需要通过 `docker run` 的参数 `--entrypoint` 来指定。
>
> 当指定了 `ENTRYPOINT` 后，`CMD` 的含义就发生了改变，不再是直接的运行其命令，而是将 `CMD` 的内容作为参数传给 `ENTRYPOINT` 指令，换句话说实际执行时，将变为：
>
> ```
> <ENTRYPOINT> "<CMD>"
> ```
>
> 那么有了 `CMD` 后，为什么还要有 `ENTRYPOINT` 呢？这种 `<ENTRYPOINT> "<CMD>"` 有什么好处么？让我们来看几个场景。
>
> ### 1）场景一：让镜像变成像命令一样使用
>
> 假设我们需要一个得知自己当前公网 IP 的镜像，那么可以先用 `CMD` 来实现：
>
> ```
> FROM ubuntu:16.04
> RUN apt-get update \
>  && apt-get install -y curl \
>  && rm -rf /var/lib/apt/lists/*
> CMD [ "curl", "-s", "http://ip.cn" ]
> ```
>
> 假如我们使用 `docker build -t myip .` 来构建镜像的话，如果我们需要查询当前公网 IP，只需要执行：
>
> ```
> $ docker run myip
> 当前 IP：61.148.226.66 来自：北京市 联通
> ```
>
> 嗯，这么看起来好像可以直接把镜像当做命令使用了，不过命令总有参数，如果我们希望加参数呢？比如从上面的 `CMD` 中可以看到实质的命令是 `curl`，那么如果我们希望显示 HTTP 头信息，就需要加上 `-i`参数。那么我们可以直接加 `-i` 参数给 `docker run myip` 么？
>
> ```
> $ docker run myip -i
> docker: Error response from daemon: invalid header field value "oci runtime error: container_linux.go:247: starting container process caused \"exec: \\\"-i\\\": executable file not found in $PATH\"\n".
> ```
>
> 我们可以看到可执行文件找不到的报错，`executable file not found`。之前我们说过，跟在镜像名后面的是 `command`，运行时会替换 `CMD` 的默认值。因此这里的 `-i` 替换了原来的 `CMD`，而不是添加在原来的 `curl -s http://ip.cn` 后面。而 `-i` 根本不是命令，所以自然找不到。
>
> 那么如果我们希望加入 `-i` 这参数，我们就必须重新完整的输入这个命令：
>
> ```
> $ docker run myip curl -s http://ip.cn -i
> ```
>
> 这显然不是很好的解决方案，而使用 `ENTRYPOINT` 就可以解决这个问题。现在我们重新用 `ENTRYPOINT`来实现这个镜像：
>
> ```
> FROM ubuntu:16.04
> RUN apt-get update \
>  && apt-get install -y curl \
>  && rm -rf /var/lib/apt/lists/*
> ENTRYPOINT [ "curl", "-s", "http://ip.cn" ]
> ```
>
> 这次我们再来尝试直接使用 `docker run myip -i`：
>
> ```
> $ docker run myip
> 当前 IP：61.148.226.66 来自：北京市 联通
> 
> $ docker run myip -i
> HTTP/1.1 200 OK
> Server: nginx/1.8.0
> Date: Tue, 22 Nov 2016 05:12:40 GMT
> Content-Type: text/html; charset=UTF-8
> Vary: Accept-Encoding
> X-Powered-By: PHP/5.6.24-1~dotdeb+7.1
> X-Cache: MISS from cache-2
> X-Cache-Lookup: MISS from cache-2:80
> X-Cache: MISS from proxy-2_6
> Transfer-Encoding: chunked
> Via: 1.1 cache-2:80, 1.1 proxy-2_6:8006
> Connection: keep-alive
> 
> 当前 IP：61.148.226.66 来自：北京市 联通
> ```
>
> 可以看到，这次成功了。这是因为当存在 `ENTRYPOINT` 后，`CMD` 的内容将会作为参数传给 `ENTRYPOINT`，而这里 `-i` 就是新的 `CMD`，因此会作为参数传给 `curl`，从而达到了我们预期的效果。
>
> ### 2）场景二：应用运行前的准备工作
>
> 启动容器就是启动主进程，但有些时候，启动主进程前，需要一些准备工作。
>
> 比如 `mysql` 类的数据库，可能需要一些数据库配置、初始化的工作，这些工作要在最终的 mysql 服务器运行之前解决。
>
> 此外，可能希望避免使用 `root` 用户去启动服务，从而提高安全性，而在启动服务前还需要以 `root` 身份执行一些必要的准备工作，最后切换到服务用户身份启动服务。或者除了服务外，其它命令依旧可以使用 `root` 身份执行，方便调试等。
>
> 这些准备工作是和容器 `CMD` 无关的，无论 `CMD` 为什么，都需要事先进行一个预处理的工作。这种情况下，可以写一个脚本，然后放入 `ENTRYPOINT` 中去执行，而这个脚本会将接到的参数（也就是 `<CMD>`）作为命令，在脚本最后执行。比如官方镜像 `redis` 中就是这么做的：
>
> ```
> FROM alpine:3.4
> ...
> RUN addgroup -S redis && adduser -S -G redis redis
> ...
> ENTRYPOINT ["docker-entrypoint.sh"]
> 
> EXPOSE 6379
> CMD [ "redis-server" ]
> ```
>
> 可以看到其中为了 redis 服务创建了 redis 用户，并在最后指定了 `ENTRYPOINT` 为 `docker-entrypoint.sh`脚本。
>
> ```
> #!/bin/sh
> ...
> # allow the container to be started with `--user`
> if [ "$1" = 'redis-server' -a "$(id -u)" = '0' ]; then
> 	chown -R redis .
> 	exec su-exec redis "$0" "$@"
> fi
> 
> exec "$@"
> ```
>
> 该脚本的内容就是根据 `CMD` 的内容来判断，如果是 `redis-server` 的话，则切换到 `redis` 用户身份启动服务器，否则依旧使用 `root` 身份执行。比如：
>
> ```
> $ docker run -it redis id
> uid=0(root) gid=0(root) groups=0(root)
> ```

### 11.4.7 ENV指令

> 格式有两种：
>
> - `ENV <key> <value>`
> - `ENV <key1>=<value1> <key2>=<value2>...`
>
> 这个指令很简单，就是设置环境变量而已，无论是后面的其它指令，如 `RUN`，还是运行时的应用，都可以直接使用这里定义的环境变量。
>
> ```
> ENV VERSION=1.0 DEBUG=on \
>  NAME="Happy Feet"
> ```
>
> 这个例子中演示了如何换行，以及对含有空格的值用双引号括起来的办法，这和 Shell 下的行为是一致的。
>
> 定义了环境变量，那么在后续的指令中，就可以使用这个环境变量。比如在官方 `node` 镜像 `Dockerfile` 中，就有类似这样的代码：
>
> ```
> ENV NODE_VERSION 7.2.0
> 
> RUN curl -SLO "https://nodejs.org/dist/v$NODE_VERSION/node-v$NODE_VERSION-linux-x64.tar.xz" \
> && curl -SLO "https://nodejs.org/dist/v$NODE_VERSION/SHASUMS256.txt.asc" \
> && gpg --batch --decrypt --output SHASUMS256.txt SHASUMS256.txt.asc \
> && grep " node-v$NODE_VERSION-linux-x64.tar.xz\$" SHASUMS256.txt | sha256sum -c - \
> && tar -xJf "node-v$NODE_VERSION-linux-x64.tar.xz" -C /usr/local --strip-components=1 \
> && rm "node-v$NODE_VERSION-linux-x64.tar.xz" SHASUMS256.txt.asc SHASUMS256.txt \
> && ln -s /usr/local/bin/node /usr/local/bin/nodejs
> ```
>
> 在这里先定义了环境变量 `NODE_VERSION`，其后的 `RUN` 这层里，多次使用 `$NODE_VERSION` 来进行操作定制。可以看到，将来升级镜像构建版本的时候，只需要更新 `7.2.0` 即可，`Dockerfile` 构建维护变得更轻松了。
>
> 下列指令可以支持环境变量展开： `ADD`、`COPY`、`ENV`、`EXPOSE`、`LABEL`、`USER`、`WORKDIR`、`VOLUME`、`STOPSIGNAL`、`ONBUILD`。
>
> 可以从这个指令列表里感觉到，环境变量可以使用的地方很多，很强大。通过环境变量，我们可以让一份 `Dockerfile` 制作更多的镜像，只需使用不同的环境变量即可。

### 11.4.8 VOLUME指令

> 格式为：
>
> - `VOLUME ["<路径1>", "<路径2>"...]`
> - `VOLUME <路径>`
>
> 之前我们说过，容器运行时应该尽量保持容器存储层不发生写操作，对于数据库类需要保存动态数据的应用，其数据库文件应该保存于卷(volume)中，后面的章节我们会进一步介绍 Docker 卷的概念。为了防止运行时用户忘记将动态文件所保存目录挂载为卷，在 `Dockerfile` 中，我们可以事先指定某些目录挂载为匿名卷，这样在运行时如果用户不指定挂载，其应用也可以正常运行，不会向容器存储层写入大量数据。
>
> ```
> VOLUME /data
> ```
>
> 这里的 `/data` 目录就会在运行时自动挂载为匿名卷，任何向 `/data` 中写入的信息都不会记录进容器存储层，从而保证了容器存储层的无状态化。当然，运行时可以覆盖这个挂载设置。比如：
>
> ```
> docker run -d -v mydata:/data xxxx
> ```
>
> 在这行命令中，就使用了 `mydata` 这个命名卷挂载到了 `/data` 这个位置，替代了 `Dockerfile` 中定义的匿名卷的挂载配置。

### 11.4.9 EXPOSE指令

> 格式为 `EXPOSE <端口1> [<端口2>...]`。
>
> `EXPOSE` 指令是声明运行时容器提供服务端口，这只是一个声明，在运行时并不会因为这个声明应用就会开启这个端口的服务。在 Dockerfile 中写入这样的声明有两个好处，一个是帮助镜像使用者理解这个镜像服务的守护端口，以方便配置映射；另一个用处则是在运行时使用随机端口映射时，也就是 `docker run -P` 时，会自动随机映射 `EXPOSE` 的端口。
>
> 此外，在早期 Docker 版本中还有一个特殊的用处。以前所有容器都运行于默认桥接网络中，因此所有容器互相之间都可以直接访问，这样存在一定的安全性问题。于是有了一个 Docker 引擎参数 `--icc=false`，当指定该参数后，容器间将默认无法互访，除非互相间使用了 `--links` 参数的容器才可以互通，并且只有镜像中 `EXPOSE` 所声明的端口才可以被访问。这个 `--icc=false` 的用法，在引入了 `docker network` 后已经基本不用了，通过自定义网络可以很轻松的实现容器间的互联与隔离。
>
> 要将 `EXPOSE` 和在运行时使用 `-p <宿主端口>:<容器端口>` 区分开来。`-p`，是映射宿主端口和容器端口，换句话说，就是将容器的对应端口服务公开给外界访问，而 `EXPOSE` 仅仅是声明容器打算使用什么端口而已，并不会自动在宿主进行端口映射。

### 11.4.10.WORKDIR指令

> 格式为 `WORKDIR <工作目录路径>`。
>
> 使用 `WORKDIR` 指令可以来指定工作目录（或者称为当前目录），以后各层的当前目录就被改为指定的目录，如该目录不存在，`WORKDIR` 会帮你建立目录。
>
> 之前提到一些初学者常犯的错误是把 `Dockerfile` 等同于 Shell 脚本来书写，这种错误的理解还可能会导致出现下面这样的错误：
>
> ```
> RUN cd /app
> RUN echo "hello" > world.txt
> ```
>
> 如果将这个 `Dockerfile` 进行构建镜像运行后，会发现找不到 `/app/world.txt` 文件，或者其内容不是 `hello`。原因其实很简单，在 Shell 中，连续两行是同一个进程执行环境，因此前一个命令修改的内存状态，会直接影响后一个命令；而在 `Dockerfile` 中，这两行 `RUN` 命令的执行环境根本不同，是两个完全不同的容器。这就是对 `Dockerfile` 构建分层存储的概念不了解所导致的错误。
>
> 之前说过每一个 `RUN` 都是启动一个容器、执行命令、然后提交存储层文件变更。第一层 `RUN cd /app`的执行仅仅是当前进程的工作目录变更，一个内存上的变化而已，其结果不会造成任何文件变更。而到第二层的时候，启动的是一个全新的容器，跟第一层的容器更完全没关系，自然不可能继承前一层构建过程中的内存变化。
>
> 因此如果需要改变以后各层的工作目录的位置，那么应该使用 `WORKDIR` 指令。

# 十二、自定义镜像管理

## 12.1 Docker 私服 registry 概述

> 有时候使用 Docker Hub 这样的公共仓库可能不方便，用户可以创建一个本地仓库供私人使用。
>
> [`docker-registry`](https://docs.docker.com/registry/) 是官方提供的工具，可以用于构建私有的镜像仓库。本文内容基于 [`docker-registry`](https://github.com/docker/distribution) v2.x 版本。

## 12.2 安装运行 registry

> 可以通过获取官方 `registry` 镜像来运行。
>
> ```
> $ docker run -d -p 5000:5000 --restart=always --name registry registry
> ```
>
> 这将使用官方的 `registry` 镜像来启动私有仓库。默认情况下，仓库会被创建在容器的 `/var/lib/registry` 目录下。你可以通过 `-v` 参数来将镜像文件存放在本地的指定路径。例如下面的例子将上传的镜像放到本地的 `/opt/data/registry` 目录。
>
> ```
> $ docker run -d \
> -p 5000:5000 \
> -v /opt/data/registry:/var/lib/registry \
> regisry
> ```

## 12.3 在私有仓库上传、搜索、下载镜像

> ## 上传镜像
>
> 创建好私有仓库之后，就可以使用 `docker tag` 来标记一个镜像，然后推送它到仓库。
>
> 例如私有仓库地址为 `127.0.0.1:5000`。
>
> 先在本机查看已有的镜像。
>
> ```
> $ docker image ls
> REPOSITORY                        TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
> ubuntu                            latest              ba5877dc9bec        6 weeks ago         192.7 MB
> ```
>
> 使用 `docker tag` 将 `ubuntu:latest` 这个镜像标记为 `127.0.0.1:5000/ubuntu:latest`。
>
> 格式为 `docker tag IMAGE[:TAG] [REGISTRY_HOST[:REGISTRY_PORT]/]REPOSITORY[:TAG]`。
>
> ```
> $ docker tag ubuntu:latest 127.0.0.1:5000/ubuntu:latest
> $ docker image ls
> REPOSITORY                        TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
> ubuntu                            latest              ba5877dc9bec        6 weeks ago         192.7 MB
> 127.0.0.1:5000/ubuntu:latest      latest              ba5877dc9bec        6 weeks ago         192.7 MB
> ```
>
> 使用 `docker push` 上传标记的镜像。
>
> ```
> $ docker push 127.0.0.1:5000/ubuntu:latest
> The push refers to repository [127.0.0.1:5000/ubuntu]
> 373a30c24545: Pushed
> a9148f5200b0: Pushed
> cdd3de0940ab: Pushed
> fc56279bbb33: Pushed
> b38367233d37: Pushed
> 2aebd096e0e2: Pushed
> latest: digest: sha256:fe4277621f10b5026266932ddf760f5a756d2facd505a94d2da12f4f52f71f5a size: 1568
> ```
>
> ## 查看镜像
>
> 用 `curl` 查看仓库中的镜像。
>
> ```
> $ curl 127.0.0.1:5000/v2/_catalog
> {"repositories":["ubuntu"]}
> ```
>
> 这里可以看到 `{"repositories":["ubuntu"]}`，表明镜像已经被成功上传了。
>
> ## 下载镜像
>
> 先删除已有镜像，再尝试从私有仓库中下载这个镜像。
>
> ```
> $ docker image rm 127.0.0.1:5000/ubuntu:latest
> 
> $ docker pull 127.0.0.1:5000/ubuntu:latest
> Pulling repository 127.0.0.1:5000/ubuntu:latest
> ba5877dc9bec: Download complete
> 511136ea3c5a: Download complete
> 9bad880da3d2: Download complete
> 25f11f5fb0cb: Download complete
> ebc34468f71d: Download complete
> 2318d26665ef: Download complete
> 
> $ docker image ls
> REPOSITORY                         TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
> 127.0.0.1:5000/ubuntu:latest       latest              ba5877dc9bec        6 weeks ago         192.7 MB
> ```



# 十三、Docker Compose

## 13.1 概述

> `Docker Compose` 是 Docker 官方编排（Orchestration）项目之一，负责快速的部署分布式应用。
>
> `Compose` 定位是 「定义和运行多个 Docker 容器的应用（Defining and running multi-container Docker applications）」
>
> `Compose` 中有两个重要的概念：
>
> - 服务 (`service`)：一个应用的容器，实际上可以包括若干运行相同镜像的容器实例。
> - 项目 (`project`)：由一组关联的应用容器组成的一个完整业务单元，在 `docker-compose.yml` 文件中定义。

## 13.2 安装

> 在 Linux 上安装十分简单，只要从 [官方 GitHub Release](https://github.com/docker/compose/releases) 处直接下载编译好的二进制文件即可。
>
> 在 Linux 64 位系统上直接下载对应的二进制包。
>
> ```
> $ sudo curl -L https://github.com/docker/compose/releases/download/1.24.0-rc1/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
> $ sudo chmod +x /usr/local/bin/docker-compos
> ```

## 13.3 使用

### 13.3.1.编写对应的docker-compose.yml文件 

在linux中，新建文件夹，并新建以下docker-compose.yml文件

内容如下

```yml
version: '3.1'
services:
  web:
    image: tomcat
    ports:
      - 81:8080
    restart: always
    volumes:
      - /usr/local/docker/my_volume/data:/usr/local/tomcat/webapps/ROOT
    container_name: tomcat4

  web2:
    image: tomcat
    ports:
      - 82:8080
    volumes:
      - /usr/local/docker/my_volume/data:/usr/local/tomcat/webapps/ROOT
    container_name: tomcat5

  mysql:
    image: mysql:5.7.25
container_name: mysql1
    ports:
      - 3308:3306
    environment:
      TZ: Asia/Shanghai
      MYSQL_ROOT_PASSWORD: 123456
    volumes:
      - /user/local/docker/mydata/mysqldata:/var/lib/mysql
```

### 13.3.2 运行docker-compose文件

 输入命令：

```
docker-compose up
```

## 13.4 常用命令

### 13.4.1.前台运行

```
docker-compose up
```

### 13.4.2.后台运行

```
docker-compose up -d
```

### 13.4.3.启动

```
docker-compose start
```

### 13.4.4.停止

```
docker-compose stop
```

### 13.4.5.停止并移除容器

```
docker-compose down
```

 

# 十三、docker CI/CD

## 13.1 CI/CD概念

> 在谈论软件开发时，经常会提到持续集成Continuous Integration（CI）和持续交付Continuous Delivery（CD）这几个术语。但它们真正的意思是什么呢？
>
> **持续集成（CI）**是在源代码变更后自动检测、拉取、构建和（在大多数情况下）进行单元测试的过程。持续集成是启动管道的环节（尽管某些预验证 —— 通常称为上线前检查pre-flight checks —— 有时会被归在持续集成之前）。
>
> 持续集成的目标是快速确保开发人员新提交的变更是好的，并且适合在代码库中进一步使用。
>
> **CI基本上包括了编码、构建、测试、打包、发版。**


> **持续交付（CD）**通常是指整个流程链（管道），它自动监测源代码变更并通过构建、测试、打包和相关操作运行它们以生成可部署的版本，基本上没有任何人为干预。
>
> 持续交付在软件开发过程中的目标是自动化、效率、可靠性、可重复性和质量保障（通过持续测试）。
>
> 持续交付包含持续集成（自动检测源代码变更、执行构建过程、运行单元测试以验证变更），持续测试（对代码运行各种测试以保障代码质量），和（可选）持续部署（通过管道发布版本自动提供给用户）。
>
> **CD基本上主要就是发布。**

下图是持续集成图：

![](https://img2018.cnblogs.com/blog/824470/201906/824470-20190623145256725-1620629251.png)

## **13.2 CI/CD和Docker结合**

> **结合Docker，我们可以快速实现CI/CD，当然有少不了版本管理和编译工具。**

 

