> Refernce link: <https://redis.io/docs/management/sentinel/>

# 哨兵模式的功能

- **监控**。监控节点实例是否正常。
- **通知**：通过API通知管理员或其他程序节点错误事件。
- **自动故障转移**。主节点故障时开始故障转移进程，将副本节点晋升为主节点。
- **提供配置**。充当客户端服务发现的中心：客户端连接到哨兵来获取主节点地址。

# 作为分布式系统的哨兵模式

拥有多个哨兵进程的好处：

1. 多个哨兵都发现给定主节点不可用时，就会启动故障检测。这降低了误报的概率。
2. 哨兵模式不需要所有哨兵进程都正常才能工作，这有助于抵抗系统故障。

# 快速开始

## 运行哨兵

```
redis-sentinel sentinel.conf
```

或者：

```
redis-server sentinel.conf --sentinel
```

运行Sentinel必须使用配置文件，因为系统将使用此文件来保存当前状态，以便在重新启动时重新加载。若没有给出配置文件或配置文件路径不可写入，Sentinel将直接拒绝启动。

Sentinel默认监听连接至TCP端口 `26379`的连接。

## 配置Sentinel

一个典型的最小配置如下：

```
sentinel monitor mymaster 127.0.0.1 6379 2
sentinel down-after-milliseconds mymaster 60000
sentinel failover-timeout mymaster 180000
sentinel parallel-syncs mymaster 1

sentinel monitor resque 192.168.1.3 6380 4
sentinel down-after-milliseconds resque 10000
sentinel failover-timeout resque 180000
sentinel parallel-syncs resque 5
```

配置文件中只需指定需要监控的主节点，为每一个主节点指定一个不同的名称。无需指定可以自动发现的副本节点。每次故障转移期间将副本节点晋升为主节点会重写配置。

`sentinel monitor`语句格式如下：

```
sentinel monitor <主节点名称> <IP> <端口> <法定人数>
```

法定人数指同意主节点失败所需最小哨兵数目。

其他配置几乎都具有以下格式：

```
sentinel <选项名称> <主节点名称> <选项值>
```

选项：

- `down-after-milliseconds`