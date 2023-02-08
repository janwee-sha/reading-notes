> The following are references of [*Linux Command Line and Shell Scripting Bible, 4th Edition*](https://www.amazon.com/Linux-Command-Shell-Scripting-Bible-ebook/dp/B08Q7XV6JC).

# 1. Starting with Linux

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

# 2. Getting to the Shell

## 2.1 进入命令行

**命令行界面**（command line interface，CLI）：只能接收文本输入，只能显示文本和基本图形输出。

进入CLI的方式：

- 控制台终端
- 图形化终端

# 3. Basic Bash Shell Commands

## 3.1 Starting the Shell

The GNU Bash shell is a program that provides interactive access to the Linux system. It runs as a regular program and is normally started whenever a user logs into a terminal.

The shell that the system starts depends on your user ID configuration. The */etc/passwd* file contains a list of all the system user accounts, along with basic configuration information about each user. Here's a sample:

```
root:x:0:0:root:/root:/bin/bash
```

Every entry has seven data fields, separated by colons (:). The last field sepecifies the user's shell program, in this case it's the GNU Bash shell program.

## 3.4 Navigating the Filesystem

## 3.4.1 Looking at the Linux filesystem

Linux在路径名中不使用驱动器盘符。

在Windows中，PC上安装的物理驱动器决定了文件的路径名。Windows会为每个物理磁盘驱动器分配一个盘符，每个驱动器都有自己的目录结构。

Linux则将文件存储在单个目录结构中，这个目录称为**虚拟目录**。虚拟目录将安装在PC上的所有存储设备的文件路径纳入单个目录结构中。

Linux虚拟目录只包含一个称为**根**目录的基础目录

Linux使用正斜线（/）而非反斜线（\）在文件路径中划分目录。Linux用反斜线来标识转义字符。

Linux PC上安装的第一块硬盘称为**根驱动器**。根驱动器是虚拟目录的核心，其他目录都是从根驱动器开始构建的。

Linux会在根驱动器上创建一些特别的目录，即**挂载点**（mount point）。挂载点是虚拟目录中用于分配额外存储设备的目录。

**Linux文件结构**:

![image](https://github.com/janwee-sha/reading-notes/blob/main/Linux/images/L.C.L.a.S.S.B.Figure.3-3.jpg)

## 3.4.2 Traversing directories

切换到系统中的某个特定位置：

```
$ cd destination
```

#### Using absolute directory references

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

#### Using relative directory references

有两个特殊字符可用于相对路径中：

- 单点符（.），表示当前目录；
- 双点符（..），表示当前目录的父目录。

## 3.5 Listing Files and Directories

### 3.5.1 Displaying a basic listing

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

- `-F`：在目录名后加上`/`，在可执行文件后面加上`*`。
- `-a`: 显示包含**隐藏文件**在内的所有文件。
- `-R`：递归选项。

### 3.5.2 Displaying a long listing

`ls` 命令的 `-l` 参数可显示每个文件和目录的附加信息。

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

### 3.5.3 Filtering listing output

`ls` 命令能识别标准通配符，并在过滤器中对它们进行模式匹配：

- 问号（`?`）代表一个字符；
- 星号（`*`）代表零个或多个字符。
- ...

```
$ ls -l do*
-rw-r--r-- 1 root root 869 Feb  5 09:36 do_not_go_gentle.txt
```

## 3.6 Handling Files

### 3.6.1 Creating files

创建空文件、改变文件的修改时间：

```
Usage: touch [OPTION]... FILE...
Update the access and modification times of each FILE to the current time.

A FILE argument that does not exist is created empty, unless -c or -h
is supplied.

A FILE argument string of - is handled specially and causes touch to
change the times of the file associated with standard output.
```

### 3.6.2 Copying files

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

## 3.7 Managing Directories

### 3.7.1 Creating directories

创建目录的命令：

```
mkdir [OPTION]... DIRECTORY...
```

也可以使用 `mkdir` 命令地 `-p` 选项“批量”地创建目录和子目录。

### 3.7.2 Deleting directories

The basic command for removing a directory is:

```
rmdir [OPTION]... DIRECTORY...
```

By default, the `rmdir` command works only for removing *empty* directories.

The `rmdir` has no `-i` option to ask if you want to remove the directory.

You can also use the `rm` command on entire nonempty directories. Using the `-r` option allows the command to descend into the directory, remove the files, and then remove the directory itself.

The ultimate solution for quickly deleting a directory tree is the `rm -rf` command. It gives no warnings and no messages, and it just deletes the directory specified and all its contents.

## 3.8 Viewing File Contents

You can use serval commands for looking indide files without having to pull out a text editor utility.

### 3.8.1 Viewing the file type

The `file` command is a handy little utility. It can peek inside a file and determine just what kind of file it is.

The `file` command determined not only that the file contains text but also the character code format of the text file, ASCII:

```
$ file hello.txt 
hello.txt: ASCII text
```
It can give you another method to distinguish a directory:

```
$ file bin
bin: directory
```

The following example shows a file that is a symbolic link and which file it is symbolically linked:

```
$ file man
man: symbolic link to share/man
```

The example below shows what the command returns for a script file:
```
$ file my_script
my_script: Bourne-Again shell script, ASCII text executable
```

The final example is a binary executable program. The command determines the platform that the program was compiled for and what types of libraries it requires:

```
$ file kubelet
kubelet: ELF 64-bit LSB executable, x86-64, version 1 (SYSV), dynamically linked, interpreter /lib64/ld-linux-x86-64.so.2, BuildID[sha1]=2247cf948cf079f8ad8339a92cd443f38c4fc350, for GNU/Linux 3.2.0, stripped
```

### 3.8.2 Viewing the whole file

#### Using the `cat` command

Usage of the `cat` command:

```
cat [OPTION]... [FILE]...
```

The `-n` parameter numbers all the line for you.

```
$ cat -n 长相思.txt
     1	山一程，水一程，
     2	身向榆关那畔行，
     3	夜深千帐灯。
     4	风一更，雪一更，
     5	聒碎乡心梦不成，
     6	故园无此声。
```

The `-b` parameter let you number the lines that have text in them.

#### Using the more command

```
more [options] <file>...
```

When you are finished navigating through the file using `more`, type **q** to quit.

For more advanced features, try the `less` command.

#### Using less command.

One set of features is that the  `less` command recognize the up and down arrow keys as well as yje Page Up and Page Down keys.

### 3.8.3 Viewing part of a file

#### Using the `tail` command

The `tail` command displays the last lines in a file. By default, it shows the last 10 lines in the file.

```
Usage: tail [OPTION]... [FILE]...
Print the last 10 lines of each FILE to standard output.
With more than one FILE, precede each with a header giving the file name.
```

The `-f` parameter is a pretty cool feature of the `tail` command. It allows you to peek inside a file as the file is being used by other process. The `tail` command stays active and continues to display new lines as they appear in the text file. This is a great way to monitor the system log files in real-time mode.

#### Using the `head` command

```
Usage: head [OPTION]... [FILE]...
Print the first 10 lines of each FILE to standard output.
With more than one FILE, precede each with a header giving the file name.
```

# 4. More Bash Shell Commands

## 4.1 Monitoring Programs

### 4.1.1 Peeking at the processes

The basic `ps` command doesn't provide all that much imformation：

```
$ ps
    PID TTY          TIME CMD
   2688 pts/0    00:00:00 bash
   2697 pts/0    00:00:00 ps
```

By default the ps command shows only the processes that belongs to the current user and that are running on the current terminal.

The basic output shows the process ID ( `PID` ) of the programs, the terminal ( `TTY` ) that they are running from, and the CPU time the process has used.

The GNU `ps` command that's used in Linux systems supports three different types of command-line parameters:

- Unix-style parameters, which are preceded by a dash.
- BSD-style parameters, which are not preceded by a dash.
- GNU long parameters, which are preceded by a double dash.

#### Unix-style parameters


| Parameter | Description |
| --- | --- |
| `-A` | Show all processes. |
| `-N` | Show the oppsite of specified parameters. |
| `-a` | Show all processes except session headers and processes without a terminal. |
| `-d` | Show all processes except session headers. |
| `-e` | Show all processes. |
| `-C <cmdlist>` | Show processes contained in the list `cmdlist`. |
| `-G <grplist>` | Show processes with a group ID listed in `grplist`. |
| `-U <userlist>` | Show processes owned by a user ID listed in `userlist`. |
| `-g <grplist>` | Show processes by session or by group ID contained in `grplist`. |
| `-p <pidlist>` | Show processes with PIDs in the list `pidlist`. |
| `-f` | Display a full format listing. |
| `-l` | Display a long listing. |
| ... | ... |

Use the `-ef` parameter combination to see everything running on the system:

```
ps -ef
UID          PID    PPID  C STIME TTY          TIME CMD
root           1       0  0  2022 ?        00:46:56 /sbin/init
root           2       0  0  2022 ?        00:00:04 [kthreadd]
root           3       2  0  2022 ?        00:00:00 [rcu_gp]
...
root     3058187       2  0 03:00 ?        00:00:00 [kworker/u4:2-events_power_efficient]
root     3059439 3018866  0 03:02 pts/0    00:00:00 ps -ef
```
A few useful columns of informations above:

- `UID`: The user responsible for lauching the process
- `PID`: The process ID of the process
- `PPID`: The PID of the parent process
- `C`: Processor utilization over the lifetime of the process
- `STIME`: The system time when the process started
- `TTY`: The terminal device from which the process was launched
- `TIME`: The cumulative CPU time required to run the process
- `CMD`: The name of the program that was started

For even moreinformation, use the `-l` parameter.

#### BSD-style parameters

The Berkeley Software Distribution (BST) was a version of Unix developed at the University of California, Berkeley.


| Parameter | Description |
| --- | --- |
| `T`| Show all processes associated with this terminal. |
| `a` | Show all processes associated with any terminal. |
| `g` | Show all processes, including session headers. |
| `r` | Show only running processes. |
| ... | ... |

#### The GNU long parameters

| Parameter | Description |
| --- | --- |
| `--deselect` | Show all processes except those listed in the command line. |
| `--Group <grplist>` | Show processes whose group ID is listed in grplist. |
| `-User <userlist>` | Show processes whose user ID is listed in userlist. |
| ... | ... |

### 4.1.2 Real-time process monitoring

The `ps` command can display information for only a specific point in time. 

The `top` command displays process information similarly to the `ps` command, but it does so in real-time mode.

```
top - 03:23:13 up 69 days, 13:01,  1 user,  load average: 0.32, 0.36, 0.42
Tasks: 167 total,   1 running, 166 sleeping,   0 stopped,   0 zombie
%Cpu(s):  6.9 us,  4.2 sy,  0.0 ni, 88.6 id,  0.0 wa,  0.0 hi,  0.2 si,  0.2 st
MiB Mem :   2488.5 total,     82.8 free,    808.9 used,   1596.8 buff/cache
MiB Swap:   2048.0 total,   1589.7 free,    458.2 used.   1375.4 avail Mem 

    PID USER      PR  NI    VIRT    RES    SHR S  %CPU  %MEM     TIME+ COMMAND                                                                                                                        
 287678 root      20   0 1116868 229112  22316 S   4.7   9.0   2252:15 kube-apiserver                                                                                                                 
 299992 root      20   0 1937668  57192  18480 S   2.3   2.2   1538:50 kubelet
```

The first selction of the output shows general system  information. The next section shows a detailed list of the current running processes.

Some information columns of the `top` command:

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

### 4.1.3 Stopping process

In Linux, processes communicate with each other using *signals*, predifined messages that processes recognize and may choose to ignore or act on.

**TABLE: Linux Process Signals**


| Signal | Name | Description |
| --- | --- | --- |
| 1 | HUP | Hang up. |
| 2 | INT | Interrupt. |
| 3 | QUIT | Stop running. |
| 9 | KILL | Unconditionally terminate. |
| 11 | SEGV | Segment violation. |
| 15 | TERM | Terminate if possible. |
| 17 | STOP | Stop unconditionally but don't terminate. |
| 18 | TSTP | Stop or pause but continue to run in background. |
| 19 | CONT | Resume execution after STOP or TSTP. |

#### The `kill` command

Usage of `kill` command:

```
kill: kill [-s sigspec | -n signum | -sigspec] pid | jobspec ... or kill -l [sigspec]
```

By default, the `kill` command sends a *TERM* signal to all the PIDs listed on the command line. To send a process signal, you must either be the owner of the process or be logged in as the root user.

The **TERM** signal tells the process to stop running. A runaway process most likely will ignore the request. The `-s` parameter allows you to specify other signals.

No output is associated with the `kill` command:

```
$ kill -s HUP 807
```

To see if the command was effective, you'll have to perform another `ps` or `top` command.

#### The `pkill` command

The `pkill` command is a way to stop process by using their names rather than the PID numbers, it allows you to use wildcard characters as well:

Usage of `pkill`:
```
pkill [options] <pattern>
```

## 4.2 Monitoring Disk Space

### 4.2.1 Mounting media

The task which places a new media disk in the virtual directory is called *mounting*.

#### The `mount` command

Usage of `mount` command:

```
 mount [-lhV]
 mount -a [options]
 mount [options] [--source] <source> | [--target] <directory>
 mount [options] <source> <directory>
 mount <operation> <mountpoint> [<target>]
```

By default, the `mount` command displays a list of media devices currently mounted on the system.

The output of the `mount` command can be very cluttered and confusing because there would be lots of virtual filesystems for management purposes besides your standard storage devices.

You can use `-t` parameter to limit the set of filesystem types:

```
$ mount -t ext4
/dev/vda1 on / type ext4 (rw,relatime,errors=remount-ro)
```

The `mount` command provides four pieces of information:
- The device filename of the media
- The mount point in the virtual directory where the media is mountted
- The filesystem type
- The access status of the mounted media

The basic command for manually mounting a media device:

```
mount -t type device directory
```

#### The `umount` command

To remove a removable media device, *unmount* it first, then remove it from the system.

The command used to unmount devices is `umount` (not "unmount").

Usage of `umount` command:

```
umount [-hV]
umount -a [options]
umount [options] <source> | <directory>
```

The `umount` command gives you the choice of defining the media device by either its device location or its mounted directory name.

#### The `df` command

Usage of `df` command:

```
df [OPTION]... [FILE]...
```

Use `-t` parameter to filter the output by specifying the filesystem type.

```
df -t ext4
Filesystem     1K-blocks     Used Available Use% Mounted on
/dev/vda1       54694496 15081256  36818308  30% /
```

> 1K-blocks: How many 1024-byte blocks of data the media can hold
> Used: Used 1024-bytes blocks
> Available: Available 1024-bytes blocks

Use `-h` parameter to show the disk space in human-readable form:

```
# df -h
Filesystem      Size  Used Avail Use% Mounted on
udev            1.2G     0  1.2G   0% /dev
tmpfs           249M  660K  249M   1% /run
tmpfs           1.3G     0  1.3G   0% /sys/fs/cgroup
```

#### The `du` command

The `du` command shows the disk usage of a specific directory.

Usage:

```
Usage: du [OPTION]... [FILE]...
  or:  du [OPTION]... --files0-from=F
```

By default, the `du` command displays all the files, directories, and subdirectories under the current directory.

Use the following command-line parameters with the `du` command to make the output more legible:

- `-c`: produce a grand total
- `-h`: print sizes in human readable format
- `-s`: display only a total for each argument

## 4.3 Working with Data Files

### 4.3.1 Sorting data

Usage of `sort` command:

```
Usage: sort [OPTION]... [FILE]...
  or:  sort [OPTION]... --files0-from=F
Write sorted concatenation of all FILE(s) to standard output.
```

By default, the `sort` command sorts the data lines in a text file using standard sorting rules for the language you specify as the default for the session:

```
$ sort weekdays.txt
Friday
Monday
Saturday
Sunday
Thursday
Tuesday
Wednesday
```

Use the `-n` parameter to tell the `sort` commmand to recognize numbers and to sort them based on their numerical values:

```
$ sort -n numbers.txt
1
2
6
10
33
```

Use the `-M` parameter to do the month sort:

```
$ sort -M somelog.log 
Jan 1 01:01:01 Device opened
Jan 1 08:20:01 Device closed
Jan 1 09:08:15 Device shut down
Apr 13 07:10:09 Device opened
```

The `-k` and `-t` parameters are handy when sorting data that uses fields, such as the `/etc/passwd` file. Use the `-t` parameter to specify the field separator character, and use the `-k` parameter to specify which field to sort on.

For example:

```
$ sort -t ':' -k 5 /etc/passwd
backup:x:34:34:backup:/var/backups:/usr/sbin/nologin
bin:x:2:2:bin:/bin:/usr/sbin/nologin
daemon:x:1:1:daemon:/usr/sbin:/usr/sbin/nologin
...
www-data:x:33:33:www-data:/var/www:/usr/sbin/nologin
```

### 4.3.2 Searching for data

Usage of `grep` command:

```
grep [OPTION]... PATTERNS [FILE]...
```

The `grep` command searches either the input or the file specified:

```
$ ls | grep 3
textfile3.txt

$ grep three numbers.txt
three
```

Use the `-v` parameter to reverse the search:

```
$ grep -v three numbers.txt
one
two
```

Use the `-n` parameter to output the line numbers where the patterns are found:

```
$ grep -vn three numbers.txt
1:one
2:two
```

Use the `-c` parameter to count how many lines contain the matching pattern:

```
$ grep -vc three numbers.txt
2
```

Use the `-e` parameter to specify more than one matching pattern:

```
$ grep -e one -e three numbers.txt
one
three
```

By default, the `grep` command uses basic UNIX-style regular expressions, which uses special characters to define how to look for matching patterns, to match patterns. 

Here's a simple example:

```
$ grep [TF] weekdays.txt 
Tuesday
Thursday
Friday
```

The `egrep` command is an offshoot of `grep`, which alows you to specify POSIX extended regular expressions. The `fgrep` command is another version that allows you to specify matching patterns as a list of fixed-string values, separated by newline charaters. 

# 5. Understanding the Shell

## 5.1 Investing Shell Types

<To be continued>