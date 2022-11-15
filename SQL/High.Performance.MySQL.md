> The following contents are a reference of [High Performance MySQL, 3rd Edition](https://learning.oreilly.com/library/view/high-performance-mysql/9781449332471/)

# Chapter 4. Optimizing Schema and Data Types

## 4.1 选择优化的数据类型

*   尽量使用可以正确存储数据的最小数据类型。
*   简单数据类型的操作通常需要更少的CPU周期。
*   尽量避免NULL。

### 4.1.1 整数类型

整数类型有：TINYINT、SMALLINT、MEDIUMINT、INT和BIGINT，分别使用8、16、24、32、64位存储空间。

### 4.1.2 实数类型

FLOAT和DOUBLE类型支持使用标准的浮点运算进行近似计算。

DECIMAL类型用于存储精确的小数。

浮点类型再存储同样范围的值时，通常比DECIMAL使用更少的空间。FLOAT使用4个字节存储。DOUBLE占用8个字节。

因为需要额外的空间和计算开销，索引应尽量旨在对小鼠进行精确计算时才使用DECIMAL。但在数据量较大时，可以考虑使用BIGINT代替DECIMAL，将需要存储的数据根据小数的位数乘以相应的倍数即可。

### 4.1.3 字符串类型

**VARCHAR和CHAR列**

VARCHAR用于存储可变长字符串。它比定长类型更节省空间。但当MySQL表使用ROW\_FORMAT=FIXED创建的话，每一行都会使用定长存储。

VARCHAR需要使用1或2个额外字节（列长小于等于255字节为1，否则为2）记录字符串的长度。

VARCHAR的适用场景：

*   字符串列最大长度比平均长度大很多；
*   列的更新很少，碎片不是问题；
*   使用了像UTF-8这样复杂的字符集，每个字符都是用不同的字节数进行存储。

CHAR是定长的。当存储CHAR值时，MySQL会删除所有的末尾空格，更具需要采用空格进行填充以方便比较。

CHAR的适用场景：

*   很短的字符串，或者所有值都接军同一个长度；
*   经常变更的数据。

**BLOB和TEXT类型**

BLOB和TEXT都是为了存储很大的数据而设计的字符串数据类型，分别采用二进制和字符方式存储。

MySQL把每个BLOB和TEXT值当作一个独立的对象处理。当BLOB和TEXT值太大时，InnoDB会使用专门的“外部”存储区域来进行存储，此时每个值在行内需要1\~4个字节存储一个指针，然后再外部区域存储实际的值。

MySQL根据max\_sort\_length的配置对BLOB和TEXT的前max\_sort\_length个字符进行排序。

**使用枚举代替字符串类型**

MySQL会根据列表值的数量将枚举值压缩到一两个字节中，MySQL会在内部将每个值在列表中的位置保存为整数，并且在表的.frm文件中保存“数字-字符串”映射关系的“查找表”。

枚举的缺点：

*   字符串列表是固定的，添加或删除字符串必须重建整个表。
*   枚举值必须经过查找才能转换成字符串，有一定开销。

### 4.1.4 日期和时间类型

MySQL可以使用许多类型来保存日期和时间值。MySQL支持的最小时间粒度为秒，但是MySQL也可以使用微秒级的粒度进行临时计算。

DATETIME
该类型能保存大范围的值，从1001年到9999年，精度为秒。它将日期和时间封装到格式为yyyyMMddHHmmss的整数中，与时区无关。使用8个字节的存储空间。
TIMESTAMP
TIMESTAMP类型保存了从1970年1月1日（格林尼治时间）以来的秒数，和UNIX时间戳相同。TIMESTAMP只使用4个字节的存储空间，范围比DATETIME小得多：只能表示从1970年到2038年。MySQL提供了FROM\_UNIXTIME()、UNIX\_TIMESTAMP()函数分别进行UNIX时间戳和日期的互相转换。

### 4.1.5 位数据类型

MySQL有少数几种存储类型使用紧凑的位存储数据。所有这些为类型，不管底层存储格式和处理方式如何，从技术上来说都是字符串类型。

# Chapter 5. Indexing for High Performance

## 5.1 Indexing Basics

在MySQL中，存储引擎先在索引中找到对应值，然后根据匹配的索引记录找到对应的数据行。

### 5.1.1 Type of Indexes

在MySQL中，索引是在存储引擎层而不是服务器层实现的。所以并没有统一的索引标准：不同的存储引擎的索引工作方式并不一样，也不是所有引擎都支持所有类型的索引。

#### B-Tree indexes

The *B-Tree* index of MySQL typically uses a B-Tree data structure to store its data.

> Many storae engines actually use a B+Tree index, in which each leaf node contains a link to the next for fast range traversals through nodes.

MySQL在CREATE TABLE和其他语句中使用的是"BTREE"关键字，但存储引擎也可能使用不同的存储结构，如，NDB集群存储引擎内部实际上使用了T-Tree结构存储这种索引；InnoDB则使用的是B+Tree。

The general idea of a B-Tree is that all the values are stored in order and each leaf page is the same distance from the root.

![image](https://github.com/janwee-sha/reading-notes/blob/main/SQL/High.Performance.MySQL.Graph.5-1.png)

叶子节点比较特别，它们的指针指向的是被索引的数据，而不是其他的节点页。

B-Tree对索引列是顺序组织存储的，所以很适合查找范围数据。

B-Tree索引适用于全键值、键值范围或键前缀查找。

**Types of queries that can use a B-Tree index:**

*   全值匹配：和索引中的所有列进行匹配。
*   匹配最左前缀：和索引中靠前的列进行匹配。
*   匹配最左列前缀：匹配某一列的值的开头部分。
*   匹配范围值：对索引中的列进行范围匹配。
*   精确匹配某一列并范围匹配另外一列：对索引中靠前的列进行全匹配，靠后的列进行范围匹配。
*   只访问索引的查询：查询只访问索引，无须访问数据行。

**Here are some limitations of B-Tree indexes:**

*   如非按照索引的最左列开始查找，则无法使用索引。
*   不能跳过索引中的列。
*   如果查询中有某个列的范围查询，则其右边所有列都无法使用索引优化查找。

#### Hash indexes

**哈希索引**基于哈希表实现，只有精确匹配索引所有列的查询才有效。对于每一行数据，存储引擎都会对所有的索引列计算一个哈希码，哈希码是一个较小的值，并且不同键值的行计算出来的哈希码不一样。哈希索引将所有的哈希码存储在索引中，同时在哈希表中保存指向每个数据行的指针。

在MySQL中，只有Memory引擎显式支持哈希索引。这也是Memory引擎的默认索引类型，Memory引擎同时也支持B-Tree索引。

存储引擎使用特定的哈希函数将数据值计算为哈希码，该哈希码用以表示槽（Slot）的索引，每一个槽的指针指向数据行。

**Hash indexes has some limitations:**

*   哈希索引只包含哈希值和行指针，而不存储字段值，所以不能使用索引中的值来避免读取行。
*   哈希索引数据并不是按照索引值顺序存储的，无法用于排序。
*   哈希索引不支持部分索引列匹配查找，因为哈希索引始终使用所有索引列的全部内容来计算索引值。
*   哈希索引只支持等值比较查询，包括=、IN()、<=>（安全的等值匹配），不支持范围查询。
*   河西冲突很多时，存储引擎必须遍历链表中所有的行指针，查询代价高昂。
*   哈希冲突很多时，一些索引维护操作的代价会很高。

因为上述限制，哈希索引只适用于某些特定的场合。而一旦适合哈希索引，则它带来的性能提升将会非常显著，如“星型”schema。

InnoDB引擎有一个特殊的功能叫“自适应哈希索引”（adaptive hash index）。当InnoDB注意到某些索引值被使用得非常频繁时，它会在内存中基于B-Tree索引之上再创建一个哈希索引。这是一个完全自动的、内部的行为，用户无法控制或者配置，不过如果有必要，完全可以关闭该功能。

**Building your own hash indexes.** 如果存储引擎不支持哈希索引，则可以模拟向InnoDB一样创建哈希索引，还可以享受一些哈希索引的遍历，例如只需很小的索引就可以为超长的键创建索引。

这样实现的缺陷是需要维护哈希值。可以手动维护，也可以使用触发器实现。

记住必要使用SHA1()和MD5()作为哈希函数。因为这两个函数计算出来的哈希值是非常长的字符串，会浪费大量的空间，比较时也会更慢。

#### Spatial (R-Tree) indexes

MyISAM表支持空间索引，可以用作地理数据存储。和B-Tree索引不同，这类索引无须前缀查询。空间索引会从所有维度来索引数据，可以有效地使用任意维度来组合查询。MySQL的GIS支持并不完善。

#### Full-text indexes

全文索引是一种特殊类型的索引，它查找的是文本中的关键词，而不是直接比较索引中的值。全文索引和其他积累索引类型的匹配方式完全不一样。它有许多需要注意的细节，如停用词、词干和负数、布尔搜索等。

在相同的列上同时创建全文索引和基于值的B-Tree索引不会有冲突，全文索引适用于MATCH AGAINST操作，而不是普通的WHERE条件操作。关于全文索引的详细讨论在第7章。

#### Other types of index

TokuDB的分形树索引是较新开发的数据结构，既有B-Tree的很多优点，也避免了B-Tree的一些缺点。

ScaleDB的Patricia tries。

## 5.2 Benefits of Indexes

索引可以让服务器快速地定位到表的指定位置。但是这并不是索引的唯一作用，根据创建索引的数据结构不同，索引也有一些其他的附加作用。

B-Tree索引由于按照顺序存储数据，所以MySQL可以用来做ORDER BY和GROUP BY操作。最后，因为索引中存储了实际的列值，所以某些查询只是用索引就能够完成全部查询。

总结下来，索引有如下三个优点：

*   大大减少了服务器需要扫描的数据量。
*   可以帮助服务器避免排序和临时表。
*   可以将随机I/O变成顺序I/O。

> **Is an Index the Best Solution?**
> 索引并不总是最好的工具。只有当索引帮助存储引擎快速查找到记录带来的好处大于其带来的额外工作时，索引才是有效的。
> 对于非常小的表，大部分情况下简单的全表扫描更高效。
> 对于中大型表，索引就非常有效。
> 对于特大型表，建立和使用索引的代价将随之增长。此时需要一种技术可以直接区分出查询需要的一组数据，而非一条一条记录地匹配。如分区技术。

## 5.3 Indexing Strategies for High Performance

### 5.3.1 Isolating the Column

如果查询的列不是独立的（索引列是表达式的一部分或函数的参数），则MySQL就不会使用索引。

如下索引就无法使用actor\_id列的索引：

    SELECT actor_id FROM sakila.actor WHERE actor_id + 1 =5;

### 5.3.2 Prefix indexes and Index Selectivity

索引很长的字符列会让索引变得大且慢，一个策略是模拟哈希索引。另一策略是索引字符的前缀，这样可大大节约索引空间，从而提高索引效率。后者会降低索引的选择性（不重复的索引值和数据表的记录总数）。唯一索引的选择性是1，这是最好的索引选择性，性能也是最好的。

前缀索引是一种能使索引更小、更快的有效方法，但也有其缺点：MySQL无法使用前缀索引做`ORDER BY`和`GROUP BY`。

对于BLOB、TEXT或很长的VARCHAR类型的列，必须使用前缀索引，因为MySQL不允许索引这些列的完整长度。

有时候**后缀索引**（suffix index）也有其用途。MySQL并不原生地支持反向索引，但可以把字符串反转后存储，并基于此建立前缀索引。

### 5.3.3 Multicolumn Indexes

在多个列上建立独立的单列索引大部分情况下并不能提高MySQL的查询性能。

MySQL5.0和更新版本引入了一种“索引合并”的策略，一定程度上可以使用多个单列索引来定位指定的行，但这种情况下没有哪个独立的单列索引是非常有效的。

索引合并策略的缺点：

*   不适用于相交操作。
*   联合操作需要耗费大量的CPU和内存在算法的缓存、排序和合并操作上。
*   优化器不会把额外工作计算到“查询成本”上，成本被低估，导致该执行计划还不如直接进行全表扫描。

### 5.3.4 Choosing a Good (B-Tree Index) Column Order

当不需要考虑排序和分组时，将选择性最高的列放在前面通常是很好的。

### 5.3.5 Clustered Indexes

**聚族索引**并不是一种单独的索引类型，而是一种数据的存储方式。具体的细节依赖于其实现方式，但InnoDB的聚族索引实际上在同一结构中保存了B-Tree索引和数据行。当表有聚族索引时，它的数据行实际上存放在索引的叶子页（leaf page）中。术语“聚族”表示数据行和相邻的键值紧凑地存储在一起。因为无法同时把数据行存放在两个不同的地方，所以一个表只能有一个聚族索引。

因为时存储引擎负责实现索引，因此不是所有的存储引擎都支持聚族索引。

以下主要关注InnoDB引擎的聚族索引。

在InnoDB中，有一列值，专门地被设定为簇族索引，这列值就是主键，当不存在主键时，InnoDB会选择一个唯一非空的列，当后者也不存在时，InnoDB会自己创建一个簇族索引。

簇族索引的优点：

*   可以把相关数据保存在一起。
*   数据访问更快。簇族索引将索引的数据保存在同一个B-Tree中，因此比非簇族索引更快。
*   使用覆盖索引扫描的查询可以直接使用节点中的主键值。

聚族索引的缺点：

*   聚族数据最大限度地提高了I/O密集型应用的性能。但如果数据全部都放在内存中，则访问的顺序就没那么重要了，聚族索引也就没什么优势了。
*   插入速度严重依赖于插入顺序。按照主键的顺序插入是加载数据到InnoDB表中速度最快的方式。那么在加载完成后最好使用OPTMIZE TABLE命令重新组织一下表。
*   更新剧组索引列的代价很高，因为会强制InnoDB将每个被更新的行移动到新的位置。
*   基于聚族索引的表在插入新行，或者主键被更新导致需要移动行的时候，可能面临“页分裂”的问题。当行的主键值要求必须将这一行插入到某个已满的页中时，存储引擎会将该页分裂成两个页面来容纳该行，这就是一次页分裂操作。页分裂会导致表占用更多的磁盘空间。
*   簇族索引可能导致全表扫描变慢，尤其是是行比较稀疏，或者由于也分裂导致数据存储不连续的时候。
*   二级索引（非簇族索引）可能比想象的要更大，因为在二级索引的叶子节点包含了引用行的主键列。
*   二级索引访问需要两次索引查找，而不是一次。

> 注：**二级索引**，叶子节点中存储主键值，每次查找数据时，根据索引找到叶子节点中的主键值，根据主键值再到聚族索引中得到完整的一行记录。相比于叶子节点中存储行指针，二级索引存储主键值会占用更多的空间。
>
> 1.  那为什么要这样设计呢？InnoDB在移动行时，无需维护二级索引，因为叶子节点中存储的是主键值，而不是指针。
> 2.  那么InnoDB有了聚族索引，为什么还要由二级索引呢？聚族索引的叶子节点存储了一行完整的数据，而二级索引只存储了主键值，相比于聚族索引，占用的内存空间要少。当我们需要为表建立索引时，如果都是聚族索引，将占用大量内存空间，索引InnoDB中主键所建立的是聚族索引，而唯一索引、普通索引、前缀索引等都是二级索引。
> 3.  为什么一般情况下,我们建表时都会使用一个自增的ID来作为主键？InnoDB中表中的数据是直接存储在主键聚族索引的叶子节点中的，每插入一条记录，其实都是增加一个叶子节点，如果主键是顺序的，只需要把新增的一条记录存储在上一条记录的后面，当页达到最大填充因子的时候，下一条记录就会写入新的野种，这种情况下，主键页就会近似于被顺序的记录填满。若表的主键非顺序ID，而是无规律数据，InnoDB则无法简单地把一行记录插入到索引的最后，而是需要一个合适的位置，甚至产生大量的页分裂并且移动大量数据，在寻找合适位置进行插入时，目标也可能不再内存中，这就导致了大量的随机IO操作，影响插入效率。此外，大量的也分裂会导致大量的内存碎片。

**InnoDB和MyISAM的数据分布对比**

MyISAM中主键索引和其他索引在结构上没有什么不同。主键索引就是一个名为PRIMARY的唯一非空索引。

### 5.3.6 Covering Indexes

索引确实是一种查找数据的高效方式，但MySQL也可以使用索引来直接获取列的数据，这样就不再需要读取数据行。如果一个索引包含所有需要查询的字段的值，我们称之为“覆盖索引”。

覆盖索引是非常有用的工具，能够极大地提高性能。考虑一下如果查询只需要扫描索引而无须回表，会带来多少好处：

*   索引条目通常远小于数据行大小，极大减少了数据访问量。
*   索引按列值顺序存储，对于I/O密集型的范围查询会比随机从磁盘读取每一行数据的I/O要少得多。
*   某些存储引擎（如MyISAM）在内存中缓存索引，数据则依赖于操作系统来缓存，减少系统调用会极大提升性能。
*   由于InnoDB的聚族索引，覆盖索引对InnoDB表特别有用。InnoDB的二级索引在叶子节点中保存了行的主键值，所以如果二级主键能够覆盖查询，则可以避免对主键索引的二级查询。

不是所有类型的索引都可以成为覆盖索引。覆盖索引必须要存储索引列的值，而哈希索引、空间索引和全文索引等都不存储索引列的值，所以MySQL只能使用B-Tree索引做覆盖索引。另外，不同的存储引擎对覆盖索引的实现方式也不同，而且不是所有引擎都支持覆盖索引。

### 5.3.7 Using Index Scans for Sorts

MySQL有两种方式可以生成有序的结果：通过排序操作；按索引顺序扫描。

扫描索引本身是很快的，但如果索引不能覆盖查询所需的全部列，那就不得不每扫描一条索引都回表查询一次对应的行。

使用索引排序的要求：

*   索引的列顺序和`ORDER BY`子句的顺序完全一致
*   所有列的排序方向（倒序或正序）都一样
*   关联多表时，`ORDER BY`子句引用的字段全部为第一张表
*   满足最左前缀

### 5.3.8 Packed (Prefix-Compressed) Indexes

MyISAM使用前缀压缩来减少索引的大小，从而让更多的索引可以放入内存中，这在某些情况下能提高性能。默认只压缩字符串，但通过参数设置也可以对整数做压缩。MyISAM压缩每个索引块的方法是，先完全保存索引块中的第一个值，然后将其他值和第一个值进行比较得到相同前缀的字节数和剩余的不同后缀部分，把这部分存储起来即可。

测试表明，对于CPU密集型应用，因为扫描需要随机查找，压缩索引使得MyISAM在索引上要慢好几倍。压缩索引的倒序扫描就更慢了。压缩索引需要在CPU内存资源与磁盘之间做权衡。压缩索引可能只需要十分之一大小的磁盘空间，如果是I/O密集型应用，对某些查询带来的好处会比成本多很多。

可以在CREATE TABLE语句中指定PACK\_KEYS参数来控制索引压缩的方式。

### 5.3.9 Redundant and Duplicate Indexes

MySQL允许在相同列上创建多个索引。MySQL需要单独维护重复的索引，并且优化器在优化查询的时候也需要逐个地进行考虑，这会影响性能。

重复索引是指在相同的列上按照相同的顺序创建相同类型的索引。

如先创建一个主键，先加上唯一限制，然后再加上索引以供查询使用。事实上，MySQL的唯一限制和主键限制都是通过索引实现的，因此，此时实际上在相同的列上创建了三个重复的索引。

冗余索引和重复索引有一些不同。如创建了索引（A，B），再创建索引（A）就是冗余索引，因为这只是前一个索引的前缀索引（这种冗余只是对B-Tree索引来说的）。另外，其他不同类型的索引也不会是B-Tree索引的冗余索引。

### 5.3.11 Indexes and Locking

索引可以让查询锁定更少的行。从两方面看这这对性能都有好处。首先，虽然InnoDB的行锁效率很高，内存使用也很少，但是锁定行的时候仍然会带来额外开销；其次，锁定超过需要的行会增加锁争用并减少并发性。

InnoDB只有在访问行的时候才会对其进行枷锁，而索引能够减少InnoDB访问的行数，从而减少锁的数量。但这只有当InnoDB在存储引擎层能够过滤掉所有不需要的行时才有效。如果索引无法过滤掉无效的行，那么在InnoDB检索到数据并返回给服务器层以后，MySQL服务器才能应用WHERE子句。这时已经无法避免锁定行了：InnoDB已经锁定了这些行，到适当的时候才释放。在MySQL 5.1和更新的版本中，InnoDB可以在服务器段过滤掉行后就释放锁，但是在早期的MySQL版本中，InnoDB只有在事务提交后才能释放锁。

如：

    SET AUTOCOMMIT;
    BEGIN;
    SELECT id FROM `province` WHERE id < 5 AND id <> 1 FOR UPDATE;

这条查询仅仅会返回2-4之间的行，但实际上获取了1-4之间的排他锁。InnoDB会锁柱第一行，这是因为MySQL为该查询选择的执行计划是索引范围扫描：

    EXPLAIN SELECT id FROM `province` WHERE id < 5 AND id <> 1 FOR UPDATE;

    +----+-------------+----------+------------+-------+---------------+---------+---------+------+------+----------+--------------------------+
    | id | select_type | table    | partitions | type  | possible_keys | key     | key_len | ref  | rows | filtered | Extra                    |
    +----+-------------+----------+------------+-------+---------------+---------+---------+------+------+----------+--------------------------+
    |  1 | SIMPLE      | province | NULL       | range | PRIMARY       | PRIMARY | 4       | NULL |    4 |   100.00 | Using where; Using index |
    +----+-------------+----------+------------+-------+---------------+---------+---------+------+------+----------+--------------------------+

注意到EXPLAIN的Extra列出现了“Using where”，这表示MySQL服务器将存储引擎返回行以后再应用WHERE过滤条件。

## 5.4 An Indexing Case Study

### 5.4.1 Supporting Many Kinds of Filtering

尽可能重用索引而不是建立大量的组合索引，当有不需要用于`WHERE`查询条件的前缀索引时可以使用`IN()`的技巧来避免。

尽可能将范围条件列放到索引的最后面。

### 5.4.2 Avoiding Multiple Range Conditions

范围查询和多个等值条件查询的访问效率是不同的。对于范围条件查询，MySQL无法再使用范围列后面的其他索引列了，但是对于“多个等值条件查询”则没有这样的限制。

对于多个范围条件查询，优化器的特性是影响索引策略的一个很重要的因素。如果未来版本的MySQL能够实现松散索引扫描，就能在一个索引上使用多个范围条件，那就不需要为上面考虑的这类查询使用`IN()`列表了。

### 5.4.3 Optimizing Sorts

Sorting small sets with filesorts is fast, but what if millions of rows match a query?

We can add special indexes for sorting these low-selectivity cases.

One of the good strategies for optimizing such queries is to use a deferred join, which again is our term for using a covering index to rerieve just the primary key columns of the rows you'll eventually retrieve. Then join this back to the table to retrieve all desired aolumns. This helps minimize the amount of work MySQL must do gathering data that it will only throw away. Here is an example:

```
SELECT * FROM person INNER JOIN (
    SELECT id FROM person
    WHERE sex = 'M' ORDER BY birth LIMIT 100000, 10
) AS p USING (id);
```

> 注：**MySQL延迟关联策略**，指“延迟了对列的访问”，不直接获取所有需要的列。在查询的第一阶段MySQL使用覆盖索引，再通过覆盖索引查询到的结果到外层匹配需要的所有列值。（引用自 [腾讯云专栏文章](https://cloud.tencent.com/developer/article/1693976#:~:text=%E5%BB%B6%E8%BF%9F%E5%85%B3%E8%81%94%EF%BC%88%20deferred%20join,%EF%BC%89%E6%8C%87%E3%80%8C%E5%BB%B6%E8%BF%9F%E4%BA%86%E5%AF%B9%E5%88%97%E7%9A%84%E8%AE%BF%E9%97%AE%E3%80%8D%EF%BC%8C%E4%B8%8D%E7%9B%B4%E6%8E%A5%E8%8E%B7%E5%8F%96%E6%89%80%E6%9C%89%E9%9C%80%E8%A6%81%E7%9A%84%E5%88%97%E3%80%82%20%E5%9C%A8%E6%9F%A5%E8%AF%A2%E7%9A%84%E7%AC%AC%E4%B8%80%E9%98%B6%E6%AE%B5%20MySQL%20%E4%BD%BF%E7%94%A8%E8%A6%86%E7%9B%96%E7%B4%A2%E5%BC%95%EF%BC%8C%E5%86%8D%E9%80%9A%E8%BF%87%E8%AF%A5%E8%A6%86%E7%9B%96%E7%B4%A2%E5%BC%95%E6%9F%A5%E8%AF%A2%E5%88%B0%E7%9A%84%E7%BB%93%E6%9E%9C%E5%88%B0%E5%A4%96%E5%B1%82%E6%9F%A5%E8%AF%A2%E5%8C%B9%E9%85%8D%E9%9C%80%E8%A6%81%E7%9A%84%E6%89%80%E6%9C%89%E5%88%97%E5%80%BC%E3%80%82)） 

## 5.5 Index and Table Maintaince

The three goals of table maintenance are finding and fixing corruption, maintaining accurate index statistics, and reducing fragmentation.

### 5.5.1 Finding and Repairing Table Corruption

The worst thing that can happen to a table is corruption. With the MyISAM storage engine, this often happens due to crashes. However, all storage engines can experience index corruption due to hardware problems or internal bugs in MySQL or the operating system.

Corrupted indexes can cause queries to return incorrect results, raise duplicate-key errors when there is no duplicated value, or even cause lockups and crashes. `CHECK TABLE` usually catches most table and index errors.

You can fix corrupt tables with the `REPAIR TABLE` command, but again, not all storage engines support this. In this case you can do a "no-op" `ALTER`, such as altering a table to use the same storage engine it currently use. Alternatively, you can either use an offline engine-specific repair utility, or dump the data and roload it.

### 5.5.3 Reducing Index and Data Fragmentation

B-tree indexes can become fragmented, which might reduce performance. Fragmented indexes can be poorly filled and/or nonsequential on disk.

By design B-Tree indexes require random disk access to "dive" to the leaf pages, so random access is the rule, not the exception. However, the leaf pages can still perform better if they are physically sequential and tightly packed. If they are not, we say they are fragmented, and range scans or full index scans can be many times slower. This is especially true for index-covered queries.

The table's data storage can also become fragmented. However, data storage fragmentation is more complex than index fragmentation. There are three types of data fragmentation:

**Row fragmentation**

This type of fragmentation occurs when the row is stored in multiple pieces in multiple locations. Row fragmentation reduces performance even if the query needs only a single row from the index.

**Intra-row fragmentation**

This kind of fragmentation occurs when logically sequential pages or rows are not stored sequentially on disk. It affects operations such as full table scans and clustered index range scans, which normally benefit from a sequential data layout on disk.

**Free space fragmentation**

This type of fragmentation occurs when there is a lot of empty space in data pages. It causes the server to read a lot of data it doesn't need, which is wasteful.

Solutions for fixing fragmentation:

- run `OPTIMIZE TABLE`
- dump and reload data
- rebuild table with a no-op `ALTER TABLE` 


# Chapter 6 Query Performance Optimization

## 6.1 Why are Queries Slow?

通常来说，查询的生命周期包括：

1. 从客户端到服务器
2. 服务器进行解析
3. 生成执行计划
4. 执行（包括存储引擎的调用以及调用后的数据处理）
5. 返回给客户端

## 6.2 Slow Query Basics: Optimize Data Access

### 6.2.1 Are You Asking the Database for Data You Don't Need?

Here are a few typical mistakes:

- Fetching more rows than needed
- Fetching all columns from a multitable join
- Fetching all columns
- Fetching the same data repeatedly

### 6.2.2 Is MySQL Examining Too Much Data?

The simplest query cost metric are:

- Response time
- Number of rows examined
- Number of rows returned

## 6.3 重构查询的方式

### 6.3.1 一个复杂查询还是多个简单查询

MySQL内部每秒能够扫描内存中上百万行数据，相比之下，MySQL响应数据给客户端就慢得多了。在其他条件都相同的时候，使用尽可能少的查询当然是更好的。但有时候，将一个大查询分解为多个小查询是很有必要的。

### 6.3.2 切分查询

有时候对于一个大查询需要“分而治之”，将大查询切分成小查询，每个查询功能完全一样，只完成一小部分，每次只返回一小部分查询结果。

这样可以将服务器上原本一次性的压力分散到一个很长的时间段中，就可以大大降低对服务器的影响，还可以大大减少锁的持有时间。

### 6.3.3 Join Decomposition

Many high-performance applications use join decomposition.

For example, instead of this simple query:

```
SELECT * FROM tag
    JOIN tag_post ON tag_post.tag_id = tag.id
    JOIN post ON tag_post.post_id = post.id
WHERE tag.tag = 'mysql';
```

You might run these queries:

```
SELECT * FROM tag WHERE tag = 'mysql';
SELECT * FROM tag_post WHERE tag_id = ...;
SELCT * FROM post WHERE post.id IN (...);
```

Such restructuring can actually give significant performance advantages:

- Caching can be more efficient. Many applications cache "objects" that map directly to tables. If only one of the tables changes frequently,decomposing a join can reduce the number of cache invalidations.
- Executing the queries individually can sometimes reduce lock contention.
- Dosing joins in the application makes it easier to scale the database by placing tables on different servers.
- The queries themselves can be more efficient.
- You can reduce redundant row accesses.
- You can view this technique as manually implementing a hash join instead of nested loops algorithms MySQL uses to execute a join.

## 6.4 查询执行的基础

MySQL执行查询的过程：

1.  客户端发送一条请求给服务器。
2.  服务器先检查缓存查询。若命中，立即返回存储在缓存中的结果；否则进入下一步。
3.  服务器端进行SQL解析、预处理，再由优化器生成对应的执行计划。
4.  MySQL根据优化器生成的执行计划，调用存储引擎API来执行查询。
5.  将结果返回给客户端。

### 6.4.1 MySQL客户端/服务器通信协议

MySQL客户端和服务器之间的通信协议是“半双工”的。任意时刻要么是客户端向服务器发送数据，要么是服务器向客户端发送数据，两个动作不会同时发生。

这种协议让MySQL通信简单快速，但是也从很多地方限制了MySQL。一个明显的限制是，没法进行流量控制。一旦一段开始发送消息，另一端要接收完整个消息才能响应它。

客户端用一个单独的数据包将查询结果传给服务器。因此当查询语句很长的时候，参数`max_allowed_packet`就特别重要了。

相反的，一般服务器相应给用户的数据由多个数据包组成。这也是在必要的时候一定要在查询中加上`LIMIT`限制的原因。

多数连接MySQL的库函数都可以获得全部结果集并缓存到内存里，还可以逐行获取需要的数据。

**Query states**

The states and their explainations:

- Sleep, the thread is waiting for a new query from the client.
- Query, the thread is either executing the query or sending the result back to the client.
- Locked, the thread is waiting for a table lock to be granted at the server level.
- Analyzing and statistics, the thread is checking storage engine statistics and optimizing the query.
- Copying to tmp table [on disk], the thread is processing the query and copying results to a temporary table, probably for a `GROUP BY`, for a filesort, or to satisfy a `UNION`.
- Sorting result, the thread is sorting a result set.
- Sending data, this can mean several things: the thead might be sending data between stages of the query, gnerating the result set, or returning the result set to the client.

### 6.4.2 The Query Cache

Before even parsing a query, MySQL checks for it in the query cache, if the cache is enabled. This operation is a case-sensitive hash lookup. If it won't match, and the query processing will go to the next stage.

If MySQL does find a match in the query cache it must check privileges before returning the cached query. This is possible without parsing the query, because MySQL stores table information with the cached query. If the privileges are OK, MySQL retrieves the stored result from the query cache and sends it to the client, bypassing every other stage in query execution. The query is never parsed, optimized, or executed.

### 6.4.3 The Query Optimization Process

This step turns a SQL into a execution plan for the query engine. It has several substeps: parsing, processing, and optimization. Errors (for example, syntax errors) can be raised at any point in the process.

**The parser and the preprocessor**

To begin, MySQL's *parser* breaks the query into tokens and builds a "parse tree" from them. The parser uses MySQL's SQL grammar to interpret and validate the query. For instance, it ensure that the tokens in the query are valid and in the proper order, and it checks for mistakes such as quoted strings that aren't terminated.

The *preprocessor* then checks the resulting parse tree for additional semantics that the parser can't resolve. For example, it checks that tables and columns exist, and it resolves names and aliases to ensure that column references aren't ambiguous.

Next, the preprocessor checks privileges.

**The query optimizer**

The parse tree is now valid and ready for the *optimizer* to turn it into a query execution plan. A query can often executed many different ways and produce the same result. The optimizer's job is to find the best option.

MySQL使用基于成本的优化器，它将尝试预测一个查询使用某种执行计划时的成本，并选择其中成本最小的一个。最初，成本的最小单位是随机读取一个4K数据页的成本，后来变得更加复杂，且引入了一些“因子”来估算某些操作的代价，如当执行一次WHERE条件比较的成本。可以通过查询当前会话的`Last_query_cost`的值来得知MySQL计算的当前查询的成本。

```
mysql> SELECT SQL_NO_CACHE COUNT(*) FROM person;
+----------+
| COUNT(*) |
+----------+
|     5462 |
+----------+

mysql> SHOW STATUS LIKE 'Last_query_cost';
+-----------------+------------+
| Variable_name   | Value      |
+-----------------+------------+
| Last_query_cost |1040.399000 |
+-----------------+------------+
```

这个结果表明大概需要做1040个数据页的随机查找才能完成上面的查询。这是根据一系列统计信息得来的：每个表或者索引的页面个数、索引的基数（不同索引值的数量）、索引和数据行的长度、索引分布情况。优化器在评估成本的时候并不考虑任何层面的缓存，它假设读取任何数据都需要一次磁盘I/O。

有很多原因会导致MySQL优化器选择错误的执行计划，如：

- 统计信息不准确
- 执行计划中的成本估算不等同于实际执行的成本
- MySQL的最优可能和你想的最优不一样
- MySQL从不考虑其他并发执行的查询，这可能影响到当前查询的速度
- MySQL也并不是任何时候都是基于成本的优化
- MySQL不会考虑不受其控制的操作的成本，如执行存储过程或用户自定义函数的成本
- 优化器有时候无法去估算所有可能的执行计划，所以它可能错过实际上最优的执行计划

MySQL优化策略可以简单分为两种：

- 静态优化，可以直接对解析树进行分析，并完成优化
- 动态优化，与查询上下文有关，也可能和很多其他因素有关，如WHERE条件中的取值、索引中条目对应的数据行数等。

MySQL能够处理的优化器类型：

- 重新定义关联表的顺序。数据表的关联并不总是按照查询中执行的顺序进行。
- 将外连接转化为内连接。并不是所有的`OUTER JOIN`语句都必须以外连接的方式执行。
- 使用等价变换规则。MySQL可以使用一些等价变换来简化并规范表达式。
- 优化`COUNT()`、`MIN()`和`MAX()`。索引和列是否可为空通常可以帮助MySQL优化这类表达式。
- 预估并转化为常数表达式。
- 覆盖索引扫描。当索引中的列包含所有查询中需要使用的列的时候，MySQL就可以使用索引返回需要的数据，而无须查询对应的数据行。
- 子查询优化。MySQL在某些情况下可以将子查询转换一种效率更高的形式，从而减少多个查询多次对数据的访问。
- 提前终止查询。在发现已经满足查询需求的时候，MySQL总是能够立刻终止查询。
- 等值传播。若两个列的值通过等式关联，那么MySQL能够把其中一个列的WHERE条件传递到另一个列上。
- 列表`IN()`的比较。在很多数据库系统中，`IN()`完全等同于多个`OR`条件的子句，因为这两者是完全等价的。MySQL则将`IN()`列表中的数据先进行排序，然后通过二分查找的方式来确定列表中的值是否满足条件，这是一个O(log n)复杂度的操作，前者复杂度为O（n）。

# Chapter 7. Advanced MySQL Features

## Full-Text Searching

通过数值比较、范围过滤等就可以完成绝大多数我们需要的查询了。但是，如果你希望通过关键字的匹配来进行查询过滤，那么就需要基于相似度的查询，而不是原来的精确数值比较。全文索引就是为这种场景设计的。

全文索引有着自己独特的语法。没有索引也可以工作，如果有索引效率会更高。

全文索引可以支持各种字符内容的搜索（包括CHAR、VARCHAR和TEXT类型），也支持自然语言搜索和布尔搜索。

### 7.10.1 Natural-Language Full-Text Searches

自然语言搜索引擎将计算每一个文档对象和查询的相关度。这里，相关度是基于匹配的关键词个数，以及关键词在文档中出现的次数。在整个索引中出现次数越少的词语，匹配时的相关度就越高。相反，非常常见的单词将不会搜索，即使不再停用词列表中出现，如果一个词语在超过50%的记录中都出现了，那么自然语言搜索将不会搜索这类词语。

全文索引的语法和普通查询略有不同。可以根据`WHERE`子句中的`MATCH AGAINST`来区分查询是否使用全文索引。

我们来看一个示例：

    CREATE TABLE articles (
              id INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
              title VARCHAR(200),
              body TEXT,
              FULLTEXT (title,body)
            ) ENGINE=InnoDB;
    				
    INSERT INTO articles (title,body) VALUES
            ('MySQL Tutorial','DBMS stands for DataBase ...'),
            ('How To Use MySQL Well','After you went through a ...'),
            ('Optimizing MySQL','In this tutorial we will show ...'),
            ('1001 MySQL Tricks','1. Never run mysqld as root. 2. ...'),
            ('MySQL vs. YourSQL','In the following database comparison ...'),
            ('MySQL Security','When configured properly, MySQL ...');

    SELECT * FROM articles
            WHERE MATCH (title,body)
            AGAINST ('dbms database' IN NATURAL LANGUAGE MODE);

    +----+-------------------+------------------------------------------+
    | id | title             | body                                     |
    +----+-------------------+------------------------------------------+
    |  1 | MySQL Tutorial    | DBMS stands for DataBase ...             |
    |  5 | MySQL vs. YourSQL | In the following database comparison ... |
    +----+-------------------+------------------------------------------+

和普通查询不同，这类查询自动按照相似度进行排序。在使用全文索引进行排序的时候，MySQL无法再使用索引排序。所以如果不想使用文件排序的话，那么就不要在查询中使用`ORDER BY`子句。

### 7.10.2 Boolean Full-Text Searches

在布尔搜索中，用户可以在查询中自定义某个被搜索的词语的相关性。布尔搜索通过停用词列表过滤掉那些“噪声”词，此外，布尔搜索还要求搜索关键词长度必须大于*ft\_min\_word\_len*，同时小于*ft\_max\_word\_len*。搜索返回的结果是未经排序的。

当编写一个布尔搜索查询时，可以通过一些前缀修饰符来定制搜索。

**Common modifiers for Boolean full-text searches**

| Example    | Meaning                 |
| ---------- | ----------------------- |
| dinosaur   | 包含“dinosaur”的行rank值更高   |
| \~dinosaur | 包含“dinosaur”的行rank值更低   |
| +dinosaur  | 行记录必须包含“dinosaur”       |
| -dinosaur  | 行记录可以不包含“dinosaur”      |
| dino\*     | 包含以“dino”开头的单词的行rank值更高 |

还可以使用其他的操作，例如使用括号分组。基于此，就可以构造出一些复杂的搜索查询。

继续使用articles表作为示例：

    SELECT * FROM articles
        WHERE MATCH (title,body)
        AGAINST ('+dbms database' IN BOOLEAN MODE);

    +----+----------------+------------------------------+
    | id | title          | body                         |
    +----+----------------+------------------------------+
    |  1 | MySQL Tutorial | DBMS stands for DataBase ... |
    +----+----------------+------------------------------+

### 7.10.5 Full-Text Configuration and Optimization

全文索引的日常维护通常能够大大提升性能。“双B-Tree”的特殊结构、在某些文档中比其他文档要包含多得多的关键字，这都使得全文索引比起普通索引有更多的碎片问题。所以经常使用OPTIMIZE TABLE来减少碎片。如果应用是I/O密集型的，那么定期地进行全文索引重建可以让性能提升很多。

如果希望全文索引能够高效地工作，还需要保证索引缓存足够大，从而保证所有的全文索引都能够缓存在内存中。通常，可以为全文索引设置单独的键缓存（Key Cache），保证不会被其他的索引缓存挤出内存。

提供一个好的停用词表也很重要。默认的停用词表对常用英语来说可能还不错，但如果是其他语言或者某些专业文档就不合适了，例如技术文档。

忽略一些太短的单词也可以提升全文索引的效率。但同时也会降低搜索的精确度。

# Appendix D. Using EXPLAIN

We can use `EXPLAIN` to get information about the query execution plan, and how to interpret the output. The `EXPLAIN` command is the main way to find out how the query optimizer decides to execute queries. This feature has limitations and doesn't always tell the truth, but its output is the best information available.

## Invoking EXPLAIN

To use `EXPLAIN`, simply add the word `EXPLAIN` just before the `SELECT` keyword in your query.

Here's the simplest possible `EXPLAIN` result:

    mysql> EXPLAIN SELECT 1;

    +----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------+
    | id | select_type | table | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra          |
    +----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------+
    |  1 | SIMPLE      | NULL  | NULL       | NULL | NULL          | NULL | NULL    | NULL | NULL |     NULL | No tables used |
    +----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------+

There's one row in the output per table in the query. The meaning of "table" is fairly broad here: it can mean a subquery, a `UNION` result, and so on.

There is a important variations on `EXPLAIN`:

*   `EXPLAIN PARTITION` shows the partitions the query will access, if applicable.
    If the query contains a subquery in the `FROM` clause, MySQL actually executes the subquery, places its result into a temporary table, and then finishes optimizing the outer query. It has to process such subqueries before it can optimize the outer query fully, which it must do for `EXPLAIN`.

## Rewriting Non-SELECT queries

MySQL explains only `SELECT` queries, not stored routine calls or `INSERT`,`UPDATE`,`DELETE`, or any other statements. However, you can rewrite some non-`SELECT` queries to be `EXPLAIN`-able.

For example, here is a `UPDATE` statement:

    mysql> UPDATE actor
        -> JOIN film_actor USING (actor_id)
        -> SET actor.last_update = film_actor.last_update;

We can convert it into a `SELECT` query like this:

    mysql> EXPLAIN SELECT film_actor.last_update, actor.last_update
        -> FROM actor
        ->  JOIN film_actor USING (actor_id);

It's often good enough to help you understand what a query will do.
