> Reference link: https://www.memurai.com/blog/redis-persistence-deep-dive

Redis supports two very different persistence models: the Redis Database Backup (RDB) model and the Append Only File (AOF) model.

# Redis RDB – Snapshots-based persistence

The frequency of the snapshots can be configured using the save configuration directive.

By default, Redis uses the following settings:

```
save 900 1     # every 15 minutes if at least one key changed
save 300 10    # every 5 minutes if at least 10 keys changed
save 60 10000  # every 60 seconds if at least 10000 keys changed
```

Schedule ad-hoc snapshots:

```
BGSAVE 60 10000  # every 60 seconds if at least 10000 keys changed
```

By default, the snapshot will be saved to dump.rdb. An alternative filename can be configured using the dbfilename directive:
```
dbfilename dump.rdb  # The filename where the DB must be dumped
```

Redis will not write to the configured filename directly. Instead, a temporary RDB file containing the server’s pid will be created and renamed upon success. This ensures the snapshot remains in a consistent state.

# Redis AOF – Append Only File AOF

Set the Append Only File name:
```
appendfilename "appendonly.aof"
```

Enable AOF persistence via the appendonly directive:

```
appendonly yes
```

Redis offers the option to “compact” AOF files. In newer versions of Redis, this is done automatically. Alternatively, one can issue the `BGREWRITEAOF` command to initiate a corresponding background job:

```
127.0.0.1:6379> BGREWRITEAOF
Background append only file rewriting started
```

# AOF with RDB-preamble

Enable the append-only file will be prefixed with an “RDB preamble.”
```
aof-use-rdb-preamble yes
```

This preamble will contain a point-in-time snapshot of what Redis stored when the log compaction background job was initiated. Successive commands will be appended to the AOF file as usual – until the next rewrite of the AOF file.

# No persistence

Diable data persistence:

```
appendonly no
save ""
```


