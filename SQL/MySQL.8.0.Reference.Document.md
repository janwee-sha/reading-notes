> The following is a reference of [MySQL 8.0 Reference Document](https://dev.mysql.com/doc/refman/8.0/en/).

### 5.4.3 The General Query Log

**The general query log** is a general record of what **mysqld** (AKA MySQL Server) is doing. The server writes information to this log when clients connect or disconnect, and it logs each SQL statement received from clients.

Each line that shows when a client connects also includes **using** *connection_type* to indicate the protocol used to establish the connection.

Options of *connection_type*:

- **TCP/IP**: TCP/IP connection established without SSL
- **SSL/TLS**: TCP/IP connection established with SSL
- **Socket**: Unix socket file connection
- **Named Pipe**: Windows named pipe connection
- **Shared Memory**: Windows shared memory connection

By default, the general query log is disabled. To specify the initial general query log state explicitly, use `--general_log[={0|1}]`. With no argument or an argument of `1`, the general log will be enabled. With an argument of `0`, this option will disable the general log. To specify a log file name, use `--general_log_file=<filename>`. To specify the log destination, use the `--log_output=<name>`.

To disable or enable general query logging for the current session, set the session `sql_log_off` variable to `ON` or `OFF`.

### 5.4.4 The Binary Log

The binary log contains "events" that describe changes such as table creations or changes to table data, events for statements that potentially could have made changes (for example, a `DELETE` which matched no rows), information about how long each statement took that updated data.

The binary has two important purposes:

- For replication, the binary log on a replication source server provides a record of the data changes to be sent to replicas.
- Certain data recovery operations require use of the binary log.

Running a server with binary logging enabled makes performance slighty slower.

Only complete events or transactions are logged or read back.

From MySQL 8.0.14, binary log files and relay log files can be encrypted. We can enable encryption on a MySQL server by setting the `binlog_encryption` system variable to `ON`.

Binary logging is enabled by default (the `log_bin` system variable is set to `ON`).

To disable binary logging, you can specify the `--skip-log-bin` or `--disable-log-bin` option at startup. If either of these options is specified and `--log-bin` is also specified, the option specified later takes precedence. 

The `--log-bin[=<basename>]` option is used to specify the base name for binary log files.

The server creates a new file un the series each time any of the following events occurs:

- The server is started or restarted.
- The server flushes the logs.
- The size of the current log file reaches `max_binlog_size`.

A binary log file may become larger than `max_binlog_size` if you are using large transactions because a transaction never split between files.

*mysqld* also creates a binary log index file to keep track of which binary log files have been used. By default, this has the same base name as the binary log file, with the extension '.index'.

You can display the contents of binary log files with the *mysqlbinlog* utility. This can be useful when you want to reprocess statements in the log for a recovery operation. For example, you can update a MySQL server from the binary log as follows:

```
$> mysqlbinlog log_file | mysql -h server_name
```

#### 查看二进制日志文件列表
可以使用如下命令查看 MySQL 中有哪些二进制日志文件：
```
mysql> SHOW BINARY LOGS;
+----------------------------+-----------+
| Log_name                   | File_size |
+----------------------------+-----------+
| LAPTOP-UHQ6V8KP-bin.000001 |       177 |
| LAPTOP-UHQ6V8KP-bin.000002 |       154 |
+----------------------------+-----------+
2 rows in set (0.00 sec)
```

#### 查看当前正在写入的二进制日志文件
可以使用以下命令查看当前 MySQL 中正在写入的二进制日志文件。
```
mysql> SHOW master status;
+----------------------------+----------+--------------+------------------+-------------------+
| File                       | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+----------------------------+----------+--------------+------------------+-------------------+
| LAPTOP-UHQ6V8KP-bin.000002 |      154 |              |                  |                   |
+----------------------------+----------+--------------+------------------+-------------------+
1 row in set (0.00 sec)
```

#### 删除二进制日志
二进制日志中记录着大量的信息，如果很长时间不清理二进制日志，将会浪费很多的磁盘空间。删除二进制日志的方法很多，下面介绍几种删除二进制日志的方法。

1. 删除所有二进制日志
使用 RESET MASTER 语句可以删除的所有二进制日志，该语句的形式如下：
```
RESET MASTER;
```

2. 根据编号删除二进制日志
每个二进制日志文件后面有一个 6 位数的编号，如 000001。使用 PURGE MASTER LOGS TO 语句，可以删除指定二进制日志的编号之前的日志。例如：

```
PURGE MASTER LOGS TO 'mylog.000004';
```

3. 根据创建时间删除二进制日志
使用 PURGE MASTER LOGS TO 语句，可以删除指定时间之前创建的二进制日志。例如：

```
PURGE MASTER LOGS TO '2019-12-20 15:00:00";
```

#### 使用二进制日志还原数据库

二进制日志还原数据库的命令如下：

```
mysqlbinlog <file> | mysql -u root -p
```
以上命令可以理解成，先使用 `mysqlbinlog` 命令来读取 <file> 中的内容，再使用 `mysql` 命令将这些内容还原到数据库中。

### 17.4.1 Replication Solutions

#### 17.4.1.3 Backing Up a Source or Replica by Making It Read Only

We can back up servers in a replication setup by acquiring a global read lock and manipulating the `read_only` system variable to change the *read-only* state of the server to be backed up:

1. Make the server *read-only*, so that it processes only retrievals and blocks updates.

2. Perform the backup.

3. Change the server back to its normal read/write state.

**Scenario 1: Backup with a Read-Only Source**

Put the source in a *read-only* state by executing these statements on it:

```
mysql> FLUSH TABLES WITH READ LOCK;
mysql> SET GLOBAL read_only = ON;
```

After the backup operation on the source server completes, restore the source server to its normal operational state by executing these statements:

```
mysql> SET GLOBAL read_only = OFF;
mysql> UNLOCK TABLES;
```

Although performing the backup on the source server is safe (as far as the backup is concerned), it is not optimal for performance because clients of the source server are blocked from executing updates.

This strategy applies to backing up a source in a replication setup, but can also be used for a single server in a nonreplication setting.

**Scenario 2: Backup with a Read-Only Replica**

While a replica is in a read-only state, the following properties are true:

- The source of the replica continues to operate, so making a backup on the source is not safe.
- The replica is stopped, so making a backup on the replica is safe.

After the replica is restored to normal operation, it again synchronizes to the source by catching up with any outstanding updates from the source's binary log.