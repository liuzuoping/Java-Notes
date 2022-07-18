### linux

- [linux](#toc_0)

------

日志特别大只想看最后100行怎么弄弄? 如果想一直看日志的持续输出，用什么命令?

2.如果日志一边输出，一边想实时看到有没有某个关键字应该怎么弄？

3.grep如果忽略大小写应该怎么弄? 正则表达式呢？

4.vim往下一行是什么键？往下30行呢? 跳到文件末尾一行是什么? 跳回来是什么? 向后搜索是什么?

5.如果有个文本文件，按空格作为列的分隔符，如果想统计第三列里面的每个单词的出现次数应该怎么弄？

6.如果把上面的出现次数排个序应该怎么弄? 想按照数字本身的顺序而不是字符串的顺序排列怎么弄？

7.Linux环境变量是以什么作为分隔符的？环境变量通过什么命令设置？

8.给某个文件权设置限比如设置为64 是用什么命令？这个6是什么意思？

9.Linux下面如果想看某个进程的资源占用情况是怎么看的？系统load大概指的什么意思？你们线上系统load一般多少？如果一个4核机器，你认为多少load是比较正常的？top命令里面按一下1会发生什么?

10.top命令里面，有时候所有进程的CPU使用率加起来超过100%是怎么回事？

11.还有哪些查看系统性能或者供你发现问题的命令？你一般是看哪个参数？

12.想看某个进程打开了哪些网络连接是什么命令？里面连接的状态你比较关心哪几种？ -- 偏题

有没有做过Linux系统参数方面的优化，大概优化过什么？

13.系统参数里面有个叫做backlog的可以用来干什么？

14.查看网络连接发现好多TIMEWAIT 可能是什么原因？对你的应用会有什么影响？你会选择什么样的方式来减少这些TIMEWAIT

15.可否介绍一下TCP三次握手的过程，如果现在有个网络程序，你用第三方的library来发送数据，你怀疑这个library发送的数据有问题，那么怎么来验证？tcpdump导出的文件你一般是怎么分析的？

16.KeepAlive是用来干什么的？这样的好处是什么？

##### 常见Linux指令

+ 常用指令
  ls　　        显示文件或目录

     -l           列出文件详细信息l(list)

     -a          列出当前目录下所有文件及目录，包括隐藏的a(all)

mkdir         创建目录

     -p           创建目录，若无父目录，则创建p(parent)

cd               切换目录

touch          创建空文件

echo            创建带有内容的文件。

cat              查看文件内容

cp                拷贝

mv               移动或重命名

rm               删除文件

     -r            递归删除，可删除子目录及文件
    
     -f            强制删除

find              在文件系统中搜索某文件

wc                统计文本中行数、字数、字符数

grep             在文本文件中查找某个字符串

rmdir           删除空目录

tree             树形结构显示目录，需要安装tree包

pwd              显示当前目录

ln                  创建链接文件

more、less  分页显示文本文件内容

head、tail    显示文件头、尾内容

ctrl+alt+F1  命令行全屏模式

+ 系统管理命令

  stat              显示指定文件的详细信息，比ls更详细

who               显示在线登陆用户

whoami          显示当前操作用户

hostname      显示主机名

uname           显示系统信息

top                动态显示当前耗费资源最多进程信息

ps                  显示瞬间进程状态 ps -aux

du                  查看目录大小 du -h /home带有单位显示目录信息

df                  查看磁盘大小 df -h 带有单位显示磁盘信息

ifconfig          查看网络情况

ping                测试网络连通

netstat          显示网络状态信息

man                命令不会用了，找男人  如：man ls

clear              清屏

alias               对命令重命名 如：alias showmeit="ps -aux" ，另外解除使用unaliax showmeit

kill                 杀死进程，可以先用ps 或 top命令查看进程的id，然后再用kill命令杀死进程。


+ 打包压缩相关命令
  gzip：

bzip2：

tar:                打包压缩

     -c              归档文件
    
     -x              压缩文件
    
     -z              gzip压缩文件
    
     -j              bzip2压缩文件
    
     -v              显示压缩或解压缩过程 v(view)
    
     -f              使用档名

例：

tar -cvf /home/abc.tar /home/abc              只打包，不压缩

tar -zcvf /home/abc.tar.gz /home/abc        打包，并用gzip压缩

tar -jcvf /home/abc.tar.bz2 /home/abc      打包，并用bzip2压缩

当然，如果想解压缩，就直接替换上面的命令  tar -cvf  / tar -zcvf  / tar -jcvf 中的“c” 换成“x” 就可以了。

+ 关机/重启机器
  shutdown

     -r             关机重启

     -h             关机不重启

     now          立刻关机

halt               关机

reboot          重启


+ Linux管道
  将一个命令的标准输出作为另一个命令的标准输入。也就是把几个命令组合起来使用，后一个命令除以前一个命令的结果。

例：grep -r "close" /home/* | more       在home目录下所有文件中查找，包括close的文件，并分页输出。

+ Linux软件包管理
  dpkg (Debian Package)管理工具，软件包名以.deb后缀。这种方法适合系统不能联网的情况下。

比如安装tree命令的安装包，先将tree.deb传到Linux系统中。再使用如下命令安装。

sudo dpkg -i tree_1.5.3-1_i386.deb         安装软件

sudo dpkg -r tree                                     卸载软件

注：将tree.deb传到Linux系统中，有多种方式。VMwareTool，使用挂载方式；使用winSCP工具等；

APT（Advanced Packaging Tool）高级软件工具。这种方法适合系统能够连接互联网的情况。

依然以tree为例

sudo apt-get install tree                         安装tree

sudo apt-get remove tree                       卸载tree

sudo apt-get update                                 更新软件

sudo apt-get upgrade        

将.rpm文件转为.deb文件

.rpm为RedHat使用的软件格式。在Ubuntu下不能直接使用，所以需要转换一下。

sudo alien abc.rpm

+ vim使用
  vim三种模式：命令模式、插入模式、编辑模式。使用ESC或i或：来切换模式。

命令模式下：

:q                      退出

:q!                     强制退出

:wq                   保存并退出

:set number     显示行号

:set nonumber  隐藏行号

/apache            在文档中查找apache 按n跳到下一个，shift+n上一个

yyp                   复制光标所在行，并粘贴

h(左移一个字符←)、j(下一行↓)、k(上一行↑)、l(右移一个字符→)

+ 用户及用户组管理
  /etc/passwd    存储用户账号

/etc/group       存储组账号

/etc/shadow    存储用户账号的密码

/etc/gshadow  存储用户组账号的密码

useradd 用户名

userdel 用户名

adduser 用户名

groupadd 组名

groupdel 组名

passwd root     给root设置密码

su root

su - root 

/etc/profile     系统环境变量

bash_profile     用户环境变量

.bashrc              用户环境变量

su user              切换用户，加载配置文件.bashrc

su - user            切换用户，加载配置文件/etc/profile ，加载bash_profile

更改文件的用户及用户组
sudo chown [-R] owner[:group] {File|Directory}

例如：还以jdk-7u21-linux-i586.tar.gz为例。属于用户hadoop，组hadoop

要想切换此文件所属的用户及组。可以使用命令。

sudo chown root:root jdk-7u21-linux-i586.tar.gz