# SQL HAVING子句

在SQL中增加HAVING子句的原因是，WHERE子句关键字无法与聚合函数一起使用，HAVING子句可以让我们筛选分组后的各组数据。

**SQL HAVING 语法**

```
SELECT col_name, aggregate_function(col_name)
FROM tbl_name
WHERE col_name operator value
GROUP BY col_name
HAVING aggregate_function(col_name) operator value;
```

# MySQL 字符串函数

- **ASCII(s)**，返回字符串s的第一个字符的ASCII码。
```
SELECT ASCII('Hello');
-- 72
```

- **CHAR_LENGTH(s)**、**CHARACTER_LENGTH(s)**、**LENGTH(s)**，返回字符串s的字符数。
```
SELECT CHAR_LENGTH('Hello');
-- 5
```

- **CONCAT(s1,s2...)**，合并字符串列表(s1,s2...)。
```
SELECT CONCAT('hello',' ','world');
-- hello world
```

- **CONCAT_WS(x,s1,s2...)**， 以x为分隔符合并字符串列表(s1,s2...)
```
SELECT CONCAT_WS(' ','hello','world');
-- hello world
```

- **FIELD(s,s1,s2...)**， 返回第一个字符串s在字符串列表(s1,s2...)中的位置。
```
SELECT FIELD('c','a','b','c','d');
-- 3
```

- **FIND_IN_SET(s1,s2)**， 返回字符串s2中与s1匹配的字符串的位置。
```
SELECT FIND_IN_SET('c','a,b,c,d,e');
-- 3
```

- **FORMAT(x,n)**，将数字x进行格式化，并保留到小数点后n位 。
```
SELECT FORMAT(PI()*10000,2);
-- 31,415.93
```

- **INSERT(s1,x,len,s2)**， 字符串s2替换s1的x位置开始长度为len的字符串。

- **LOCATE(s1,s2)**，从字符串s2中获取s1的开始位置。
```
SELECT LOCATE('christmas','merry christmas');
-- 7
```

- **LCASE(s)**、**LOWER(s)**，小写转换字符串s中的所有字母。 

- **LEFT(s,n)**，返回字符串s的前n个字符。

- **LPAD(s1,len,s2)**，在字符串s1的开始处填充字符串s2，使字符串长度达到len。
```
SELECT LPAD(' world',11,'hello');
-- hello world
```

- **LTRIM(s)**，去掉字符串s开始处的空格。 

-**MID(s,start,len)**、**SUBSTR(s,start,len)**、**SUBSTRING(s,start,len)**，截取子字符串。





# MySQL 日期函数

- **ADDDATE(d,INTERVAL expr type)**、**DATE_ADD(d,INTERVAL expr type)**，计算时间表达式d加上区间表达式后的时间。


```
SELECT ADDDATE('2022-02-22 22:22:22',INTERVAL 1 SECOND);
-- 2022-02-22 22:22:23
```

- **ADDTIME(t,n)**，计算时间表达式t加上时间表达式n后的时间。


```
SELECT ADDTIME('2022-02-22 22:22:22',1);
-- 2022-02-22 22:22:23
```

- **CURDATE()**、**CURRENT_DATE()**，返回当前日期。

- **CURRENT_TIME()**、**CURTIME()**，返回当前时间。

- **CURRENT_TIMESTAMP()**，返回当前日期和时间。

- **DATE()**，从日期或日期时间表达式中提起日期值。

- **DATEDIFF(d1，d2)**，计算日期d1->d2之间相隔的天数。
```
SELECT DATEDIFF('2022-1-8 22:15:00','2022-1-9');
-- -1
```

# SQL BETWEEN操作符

BETWEEN操作符用于选取介于两个值之间的数据范围内的值。

BETWEEN语法：
```
...col_name BETWEEN value1 AND value2;
```

# MySQL OLAP函数

开窗函数（OLAP函数）：为将要被操作的行的集合定义一个窗口，它对一组值进行操作，不需要使用GROUP BY子句对数据进行分组，能够在同一行中同时返回基础行的列和聚合列。

**OLAP的语法结构：**
```
OVER ([PARTITION BY <列清单>] ORDER BY <排序用列清单>)
```

**开窗函数一般分为两类：**

- 能够作为开窗函数的聚合函数，如SUM、AVG、COUNT、MIN。
- 专用开窗函数，如RANK、DENSE、ROW、ROW_NUMBER。

*聚合开窗函数只能使用PARTITION BY子句，ORDER BY不能与聚合开窗函数一同使用。*

# SQL FULL OUTER JOIN 关键字

MySQL不支持FULL OUTER JOIN。

# SQL SELECT INTO 语句

SELECT INTO 语句从一个表复制数据，然后把数据插入到另一个新表中。

MySQL数据库不支持SELECT...INTO语句但支持INSERT INTO...SELECT。

# SQL INSERT INTO SELECT语句

INSERT INTO SELECT 语句从一个表复制数据，然后把数据插入到一个已存在的表中。目标表中任何已存在的行都不会受影响。

# SQL约束

- **NOT NULL**
- **UNIQUE**

    创建表时创建UNIQUE约束：
    ```
    CRAETE TABLE tbl_name(
        col_name type UNIQUE,
        ...);
    ```
    ```
    CRAETE TABLE tbl_name(
        ...,
        UNIQUE(col_name));
    ```
    修改表时在某列上创建UNIQUE约束；
    ```
    ALTER TABLE tbl_name
        ADD UNIQUE (col_name);
    ```
    修改表时定义多个列的UNIQUE约束：
    ```
    ALTER TABLE tbl_name
        ADD CONSTRAINT constraint_name UNIQUE (col_name1,col_name2);
    ```
    撤销UNIQUE约束：
    **MySQL:**
    ```
    ALTER TABLE tbl_name
        DROP INDEX col_name;
    ```
    **SQL Server/Oracle/MS Access:**
    ```
    ALTER TABLE tbl_name
        DROP CONSTRAINT constraint_name;
    ```
    

- **PRIMARY KEY**
    PRIMARY KEY约束唯一标识数据库表中的每条记录。

    主键必须包含唯一的值。
    
    主键不能包含NULL值。
    
    每个表都应该有一个主键，且每个表只能由一个主键。
    
    定义多个列的PRIMARY KEY约束：
    
    ```
    CREATE TABLE tbl_name(
        ...
        CONSTRAINT constraint_name PRIMARY KEY (col_name1,col_name2));
    ```
    撤销PRIMARY KRY约束：
    **MySQL:**
    ```
    ALTER TABLE tbl_name
        DROP PRIMARY KEY;
    ```
    **SQL Server/Oracle/MS Access:**
    ```
    ALTER TABLE tbl_name
        DROP CONSTRAINT constraint_name;
    ```

- **FOREIGN KEY**

    一个表的FOREIGN KEY指向另一个表的UNIQUE KEY。
    
- **CHECK**
    
    CHECK约束用于限制列中的值的范围。

    如果对单个列定义CHECK约束，那么该列只允许特定的值。
    
    如果对一个表定义CHECK约束，那么此约束会给予行中其他列的值在特定的列中对值进行限制。
    
- **DEFAULT**
    
    DEFAULT约束用于向列中插入默认值。
    
    如果没有规定其他的值，那么会将默认值添加到所有的新纪录。

    **修改表时的创建DEFAULT约束：**
    
    MySQL:
    ```
    ALTER TABLE table_name
        ALTER column_name SET DEFAULT default_value;
    ```
    SQL Server/MS Access:
    ```
    ALTER TABLE table_name
        ADD CONSTRAINT constraint_name DEFAULT default_value FOR column_name;
    ```
    
    **撤销DEFAULT约束：**
    
    MySQL：  
    ```
    ALTER TABLE table_name
        ALTER column_name DROP DEFAULT
    ```
    SQL Server/Oracle/MS Access:
    ```
    ALTER TABLE table_name
        ALTER COLUMN column_name DROP DEFAULT
    ```

# SQL CREATE INDEX语句

CREATE INDEX用于在标准创建索引。

在不读取整个表的情况下，索引使数据库应用程序更快地查找数据。

## 创建索引

更新一个包含索引地表需要比更新一个没有索引地表花费更多的时间，这是由于索引本身也需要更新。建议尽在常被搜索的列上创建索引。

使用**CREATE INDEX**创建的索引允许使用重复的值。

使用**CREATE UNIQUE INDEX**创建唯一索引，不允许使用重复的值。

## 撤销索引

**MySQL**
```
ALTER TABLE table_name
    DROP INDEX index_name
```

**DB2/Oracle**

```
DROP INDEX index_name
```

# TRUNCATE TABLE

用于仅删除表内的数据。
    
# SQL AUTO INCREMENT 字段

- **MySQL**

```
CREATE TABLE table_name(
    column_name type AUTO_INCREMENT,
    ...
)
```

- **SQL Server**

```
CREATE TABLE table_name(
    column_name type IDENTITY(start_value,increment_value),
    ...
)
```

- **Access**

```
CREATE TABLE table_name(
    column_name type AUTOINCREMENT,
    ...
)
```

- **Oracle**

Oracle中实现递增必须通过sequence对象。

使用下面的语句创建序列：
```
CREATE SEQUENCE sequence_name
MINVALUE 最小值
START WITH 起始值
INCREMENT BY 递增值
CACHE 缓存值的数量
```

然后在插入新数据时使用nextval函数：
```
INSERT INTO 表名(列名)
    VALUES (序列名.nextval)
```

# SQL 视图

视图是基于SQL语句的结果集的可视化的表。视图包含行和列。视图中的字段来自一个或多个数据库表。

可以向视图添加SQL函数、WHERE以及JOIN语句。

## 创建视图

```
CREATE VIEW 视图名 AS
    SELECT语句
```

## 更新视图

```
CARETE OR REPLACE VIEW 视图名 AS
    SELECT语句
```
