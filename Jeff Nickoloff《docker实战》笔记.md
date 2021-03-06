# 1.欢迎来到Docker世界

## 1.1 什么是Docker

Docker包含一个命令行程序、一个后台守护进程，以及一组远程服务。它解决了常见软件问题，并简化了安装、运行、发布和删除软件。这一切能够实现是通过使用了容器的UNIX技术。

### 1.1.2 容器不是虚拟化

不同于虚拟机，Docker容器不使用硬件虚拟化。运行在Docker容器中的程序接口和主机的Linux内核直接打交道。

### 1.1.3 在隔离的容器中运行软件

运行Docker可以认为实在用户空间运行着两个程序。首先是Docker守护进程。另一个是Docker CLI，它是与用户交互的Docker程序。每个Docker容器以Docker守护程序的子进程运行，封装在容器中，并授权其在自身用户空间的存储子空间中运行。在容器中运行的程序只能访问属于自己的该容器审定过的内存空间和资源。

Docker构建的容器隔离包括8个方面：

- PID命名空间，进程标识符和能力。
- UTS命名空间，主机名和域名。
- MNT命名空间，文件系统访问和结构。
- IPC命名空间，通过共享内存的进程间通信。
- NET命名空间，网络访问与结构。
- USER命名空间，用户名和标识。
- chroot()，控制文件系统根目录的位置。
- cgroups，资源保护。

## 1.2 Docker解决了什么问题

- 组织有序
- 提高可移植性
- 保护你的机器

# 2.在容器中运行软件

- **docker run**

启动容器命令。--interactive（-i）选项告诉Docker保持标准输入流对容器开放。--tty（-t）告诉Docker为容器分配一个虚拟终端，这将允许你发信号给容器。
- **docker ps**

列举正在运行的容器的命令。

- **docker restart**

重启容器命令。

- **docker logs**

日志查看命令。--follow（-f）选项，显示整个日志并继续监视和更新日志的显示。

- **docker stop**

停止容器命令。

- **docker rename**

重命名容器。

- **docker exec**

执行容器中的命令。

- **docker restart**

重启容器。

# 3.软件安装的简化

- **docker pull**

拉取镜像。

- **docker save**

导出镜像。

- **docker rmi**

删除镜像。

- **docker load**

再次加载镜像。

- **docker build**

使用Dockerfile构建镜像。

### 3.3.2 分层关系

镜像维护着父/子依赖关系。容器中的文件是镜像所创建容器的所有层合集。

### 3.3.3 容器文件系统抽象和隔离

从容器的角度看，容器具有镜像所提供文件的独占副本。

# 4.持久化存储和卷间状态共享

## 4.1 存储卷的简介

### 4.1.1 存储卷提供容器无关的数据管理方式

从语义上说，存储卷是一个数据分割和共享的工具，有一个与容器无关的范围或生命周期。

镜像适合打包和分发相对静态的文件，如程序；存储卷则持有动态或专门数据。

通过docker run --volume [文件路径]指定存储卷的挂载点。

# 5.网络访问

TODO （待读）

# 6.隔离——限制危险

TODO（待读）

# 7.在镜像中打包软件

## 7.1 从容器中构建镜像

### 7.1.1 打包Hello World

从容器构建镜像的三个步骤：

- 从已存在的镜像创建一个容器。
- 修改这个容器的文件系统。
- 提交改动，并从新镜像创建新容器。

