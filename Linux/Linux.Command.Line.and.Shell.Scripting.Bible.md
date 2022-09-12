# CHAPTER 1: Starting with Linux

## 1.1 What is Linux

There are four main parts that make up a Linux system：

- The Linux kernel
- The GNU kernel
- A graphical desktop environment
- Application softwares

The Linux system：

```mermaid
	graph TD
	A[Application Softwares] --- B[Windows Management software]
	B --- C[GNU System Utility] & D[Linux Kernel]
	C --- D
	D --- E[Computer Hardware]
```

### 1.1.1 Looking into the Linux Kernel

The core of Linux system is the **kernel**.The kernel controlls
Linux的核心是**内核**。内核控制着计算机系统上的所有硬件和软件，在必要时分配硬件，并根据需要执行软件。

内核主要负责以下四种功能：

- 系统内存管理
- 软件程序管理
- 硬件设备管理
- 文件系统管理

1. **系统内存管理**

内核不仅管理服务器上的可用物理内存，还可以创建和管理虚拟内存。

内核通过硬盘上的存储空间来实现虚拟内存，这块区域称为**交换空间**（swap space）。

内核存储单元按组划分成很多快，这些块称作**页面**。内核将每个内存页面放在物理内存或交换空间。内核维护者一个指明页面位于物理内存还是磁盘上的内存页面表。

内核自动把一段时间未访问的内存页面复制到交换空间区域（称为**换出**，swap out）。程序要访问一个已被换出的内存页面时，内核必须从物理内存换出另外一个内存页面给他让出空间，然后从交换区域换入请求的内存页面。

2. **软件程序管理**

Linux操作系统将运行中的程序称为**进程**。

内核创建了第一个进程（称为**init进程**）来启动系统上所有其他进程。内核启动时，它会将init进程加载到虚拟内存中。内核在启动其他进程时，都会在虚拟内存种给新进程分配一块专有区域来存储该进程用到的数据和代码。

3. **硬件设备管理**

任何Linux系统需要与之通信的设备，都需要在内核代码中加入其驱动程序代码。驱动程序允许内核与设备之间交换数据。

Linux内核用于插入设备驱动代码的两种方法：

- 编程进内核的设备驱动代码
- 可插入内核的设备驱动模块

Linux系统将硬件设备当成特殊的文件，称为**设备文件**。

设备文件的分类：

- 字符型设备文件
- 块设备文件
- 网络设备文件

字符型设备是指处理数据时每次只能处理一个字符的设备。多数调制解调器和终端都是作为字符型设备创建的。

块设备文件是指处理数据时每次能处理大块数据的设备，比如硬盘。

网络设备文件是指采用数据包发送和接收数据的设备，包括各种网卡和一个特殊的回环设备（允许Linux使用常见的网络编程协议同自身通信的设备）。

Linux为每个设备都创建一种称为**节点**的特殊文件。与设备的所有通信都通过设备节点完成。

4. **文件系统管理**

不同于其他一些操作系统，Linux内核支持通过不同类型的文件系统从硬盘读写数据。除自身诸多文件系统外，Linux还支持从其他操作系统采用的文件系统读写数据。

**Linux文件系统**：


| 文件系统 | 描述 |
| --- | --- |
| ext | Linux扩展文件系统，最早的Linux文件系统 |
| ext2 | 第二扩展文件系统，在ext的基础上提供了很多新功能 |
| ext3 | 第三扩展文件系统，支持日志功能 |
| ext4 | 第四扩展文件系统，支持高级日志功能 |
| hpfs | OS/2高性能文件系统 |
| jfs | IBM日志文件系统 |
| iso9660 | ISO 9660文件系统（CD-ROM） |
| minix | MINIX文件系统 |
| msdos | 微软的FAT16 |
| ncp | Netware文件系统 |
| nfs | 网络文件系统 |
| ntfs | 支持Microsoft NT文件系统 |
| proc | 访问系统信息 |
| ... | ... |

Linux内核采用虚拟文件系统（VFS）作为和每个文件系统交互的接口。当每个文件系统都被挂载和访问时，VFS将信息都缓存在内存中。

### 1.1.2 GNU工具

除了内核控制硬件设备外，操作系统还需要一些工具来执行一些标准操作，比如控制文件和程序。

1. **核心GNU工具**

GNU项目的主旨在于为Unix系统管理员设计出一套类似于Unix的环境，这促使了该项目移植了很多常见的Unix系统命令行工具。供Linux使用的这组核心工具被称为coreutils软件包。

GNU coreutils软件包由三部分构成：

- 用于处理文件的工具
- 用于操作文本的工具
- 用于管理进程的工具

2. **shell**

GNU/Linux shell是一种特殊的交互式工具。它为用户提供了启动程序、管理文件系统中的文件以及运行在Linux系统上的进程的途径。shell的核心时命令行提示符。命令行提示符是shell负责交互的部分。它允许你输入命令，然后解释命令，并在内核中运行。

shell包含了一组内部命令，使用它们可以完成诸如复制/移动/重命名文件、运行/显示/终止程序等操作。

**shell脚本**：容纳多个shell命令作为程序执行的文件。

Linux系统通常有好几种Linux shell可用。所有Linux发行版的默认shell都是bash shell。

### 1.1.3 Linux桌面环境

常见桌面环境：

- X Window系统
- KDE桌面
- GNOME桌面
- Unity桌面

# CHAPTER 2: Getting to the Shell

## 2.1 进入命令行

**命令行界面**（command line interface，CLI）：只能接收文本输入，只能显示文本和基本图形输出。

进入CLI的方式：

- 控制台终端
- 图形化终端

# CHAPTER 3: Basic Bash Shell Commands

## 3.4 浏览文件系统

## 3.4.1 Linux文件系统

Linux在路径名中不使用驱动器盘符。

在Windows中，PC上安装的物理驱动器决定了文件的路径名。Windows回味每个物理磁盘驱动器分配一个盘符，每个驱动器都有自己的目录结构。

Linux则将文件存储在单个目录结构中，这个目录称为**虚拟目录**。虚拟目录将安装在PC上的所有存储设备的文件路径纳入单个目录结构中。

Linux虚拟目录只包含一个称为**根**目录的基础目录

Linux使用正斜线（/）而非反斜线（\）在文件路径中划分目录。Linux用反斜线来标识转义字符。

Linux PC上安装的第一块硬盘称为**根驱动器**。根驱动器是虚拟目录的核心，其他目录都是从根驱动器开始构建的。

Linux会在根驱动器上创建一些特别的目录，即**挂载点**（mount point）。挂载点是虚拟目录中用于分配额外存储设备的目录。

**Linux文件结构**:
```
classDiagram
Disk1 o-- Disk2 : home
Disk1 : bin
Disk1 : etc
Disk1 : usr
Disk1 : var
Disk1 : home
Disk1 : ...
Disk2 : rich
Disk2 : janwee
Disk2 : ...
```

## 3.4.2 遍历目录


切换到系统中的某个特定位置：

```
$ cd destination
```

**1. 绝对文件路径**

绝对文件路径以正斜线（/）作为起始。

显示当前工作目录：

```
pwd: pwd [-LP]
    Print the name of the current working directory.
    
    Options:
      -L	print the value of $PWD if it names the current working
    		directory
      -P	print the physical directory, without any symbolic links
```
**2. 相对文件路径**

有两个特殊字符可用于相对路径中：

- 单点符（.），表示当前目录；
- 双点符（..），表示当前目录的父目录。

## 3.5 文件和目录列表

### 3.5.1 基本列表功能

```
Usage: ls [OPTION]... [FILE]...
List information about the FILEs (the current directory by default).
Sort entries alphabetically if none of -cftuvSUX nor --sort is specified.

Options:
  -a, --all                  do not ignore entries starting with .
  -A, --almost-all           do not list implied . and ..
      --author               with -l, print the author of each file
  -F, --classify             append indicator (one of */=>@|) to entries
      --file-type            likewise, except do not append '*'
      --format=WORD          across -x, commas -m, horizontal -x, long -l,
                               single-column -1, verbose -l, vertical -C
      --full-time            like -l --time-style=full-iso
  -l                         use a long listing format
  -r, --reverse              reverse order while sorting
  -R, --recursive            list subdirectories recursively
  -1                         list one file per line
```

option参数：

- -F：在目录名后加上/，在可执行文件后面加上*。
- -a: 显示包含**隐藏文件**在内的所有文件。
- -R：递归选项。

### 3.5.2 显示长列表

ls命令的-l参数可显示每个文件和目录的附加信息。

```
$ ls -l
total 2097232
lrwxrwxrwx   1 root root          7 Oct 21  2020 bin -> usr/bin
drwxr-xr-x   3 root root       4096 Oct 21  2020 home
-rw-------   1 root root 2147483648 Oct 21  2020 swapfile
```

附加信息的输出包括：

- 文件类型，如目录（d）、文件（-）、字符型文件（c）或块设备（b）等；
- 文件的权限；
- 文件的硬链接总数。
- 文件属主的用户名；
- 文件属主的组名；
- 文件的大小（以字节为单位）；
- 文件的上次修改时间；
- 文件名或目录名。

### 3.5.3 过滤输出列表

ls命令能识别标准通配符，并在过滤器中对它们进行模式匹配：

- 问号（?）代表一个字符；
- 星号（*）代表零个或多个字符。
- ...

## 3.6 处理文件

### 3.6.1 创建文件

创建空文件、改变文件的修改时间：

```
Usage: touch [OPTION]... FILE...
Update the access and modification times of each FILE to the current time.

A FILE argument that does not exist is created empty, unless -c or -h
is supplied.

A FILE argument string of - is handled specially and causes touch to
change the times of the file associated with standard output.
```

### 3.6.2 复制文件

```
Usage: cp [OPTION]... [-T] SOURCE DEST
  or:  cp [OPTION]... SOURCE... DIRECTORY
  or:  cp [OPTION]... -t DIRECTORY SOURCE...
Copy SOURCE to DEST, or multiple SOURCE(s) to DIRECTORY.

Mandatory arguments to long options are mandatory for short options too.
  -f, --force                  if an existing destination file cannot be
                                 opened, remove it and try again (this option
                                 is ignored when the -n option is also used)
  -i, --interactive            prompt before overwrite (overrides a previous -n
                                  option)
  -R, -r, --recursive          copy directories recursively
      --reflink[=WHEN]         control clone/CoW copies. See below
      --remove-destination     remove each existing destination file before
                                 attempting to open it (contrast with --force)
      --sparse=WHEN            control creation of sparse files. See below
      --strip-trailing-slashes  remove any trailing slashes from each SOURCE
                                 argument
  -s, --symbolic-link          make symbolic links instead of copying
```

cp命令中也可以使用模式匹配。

### 3.6.4 Linking files

Two types of file links are available in Linux：

- A symbolic link
- A hard link

**A symbolic link**, also called a soft link, is simply a physical file that points to another file somewhere in the virtual directory structure. The two symbolically linked together files do not share the same contents.

To create a symbolic link, the original file must already exist.

The hard link creates a separate file that contains information about the original file and where to locate it. When you reference the hard link file, it's just as if you're referencing the original file.

Create link：

```
用法：ln [选项]... [-T] 目标 链接名
　或：ln [选项]... 目标
　或：ln [选项]... 目标... 目录
　或：ln [选项]... -t 目录 目标...
在第一种格式中，创建具有指定<链接名>且指向指定<目标>的链接。
在第二种格式中，在当前目录创建指向<目标>位置的链接。
在第三、四种格式中，在指定<目录>中创建指向指定<目标>的链接。
默认创建硬链接，当使用--symbolic 时创建符号链接。
默认情况下，创建每个目标时不应存在与新链接的名称相同的文件。
创建硬链接时，每个指定的<目标>都必须存在。符号链接可以指向任意的位置；
当链接解析正常时，将其解析为一个相对于其父目录的相对链接。

必选参数对长短选项同时适用。
      --backup[=CONTROL]      为每个已存在的目标文件创建备份文件
  -b                          类似--backup，但不接受任何参数
  -d, -F, --directory         允许超级用户尝试创建指向目录的硬链接
                              （注意：此操作可能因系统限制而失败)
  -f, --force                 强行删除任何已存在的目标文件
  -i, --interactive           删除目标文件前进行确认
  -L, --logical               如目标为符号链接，本次创建链接时将其解引用
  -n, --no-dereference        如果给定<链接名>是一个链接至某目录的符号链接，
                                将其作为普通文件处理
  -P, --physical              创建直接指向符号链接文件的硬链接
  -r, --relative              创建相对于链接位置的符号链接
  -s, --symbolic              创建符号链接而非硬链接
  -S, --suffix=后缀           自行指定备份文件的后缀
  -t, --target-directory=目录  在指定<目录>中创建链接
  -T, --no-target-directory   总是将给定的<链接名>当作普通文件
  -v, --verbose               列出每个链接的文件名称
```

# CHAPTER 4、More Bash Shell Commands

## 4.1 Monitoring Programs

### 4.1.1 Peeking at the processes

The basic ps command doesn't provide all that much imformation：

```
$ ps
    PID TTY          TIME CMD
   2688 pts/0    00:00:00 bash
   2697 pts/0    00:00:00 ps
```

By default the ps command shows only the processes that belongs to the current user and that are running on the current terminal.

The basic output shows the process ID (PID) of the programs, the terminal (TTY) that they are running from, and the CPU time the process has used.

The GNU ps command that's used in Linux systems supports three different types of command-line parameters:

- Unix-style parameters, which are preceded by a dash.
- BSD-style parameters, which are not preceded by a dash.
- GNU long parameters, which are preceded by a double dash.

**Unix-style parameters**


| Parameter | Description |
| --- | --- |
| -A | Show all processes. |
| -N | Show the oppsite of specified parameters. |
| -a | Show all processes except session headers and processes without a terminal. |
| -d | Show all processes except session headers. |
| -e | Show all processes. |
| -C cmdlist | Show processes contained in the list cmdlist. |
| -G grplist | Show processes with a group ID listed in grplist. |
| -U userlist | Show processes owned by a user ID listed in userlist. |
| -g grplist | Show processes by session or by group ID contained in grplist. |
| -p pidlist | Show processes with PIDs in the list pidlist. |
| ... | ... |

A few useful columns of informations:

- **UID**: The user responsible for lauching the process
- **PID**: The process ID of the process
- **PPID**: The PID of the parent process
- **C**: Processor utilization over the lifetime of the process
- **STIME**: The system time when the process started
- **TTY**: The terminal device from which the process was launched
- **TIME**: The cumulative CPU time required to run the process
- **CMD**: The name of the program that was started

**BSD-style parameters**

The Berkeley Software Distribution (BST) was a version of Unix developed at the University of California, Berkeley.


| Parameter | Description |
| --- | --- |
| T | Show all processes associated with this terminal. |
| a | Show all processes associated with any terminal. |
| g | Show all processes, including session headers. |
| r | Show only running processes. |
| ... | ... |

**The GNU long parameters**

| Parameter | Description |
| --- | --- |
| --deselect | Show all processes except those listed in the command line. |
| --Group grplist | Show processes whose group ID is listed in grplist. |
| -User userlist | Show processes whose user ID is listed in userlist. |
| ... | ... |

**Real-time process monitoring**

The ps command can display information for only a specific point in time. If you're trying to find trends about processes that are frequently swapped in and out of memory, it's hard to do that with the ps command.

Instead, the top command can solve this problem. The top command displays process information similarly to the ps command, but it does so in real-time mode.

Some information columns of the top command:

- **PID**
- **USER**
- **PR**: The priority of the process
- **NI**: The nice value of the process
- **VIRT**: The total amount of virtual memory used by the process
- **RES**: The amount of physical memory the process is using
- **SHR**: The amount of memory the process is sharing with other processes
- **S**: The process status (D = interruptible sleep, R = running, S = sleeping, T = traced or stopped, or Z = zombie)
- **%CPU**: The share of CPU time that the process is using
- **%MEM**: The share of available physical memory the process is using
- **TIME+**: The total CPU time the process has used since starting
- **COMMAND**: The command-line name of the process (program started)


