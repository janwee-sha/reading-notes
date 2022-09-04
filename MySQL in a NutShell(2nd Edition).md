# Part II. SQL Statements and Functions

## Chapter 5. Database and Table Schema Statements

### 5.1 Statements and Clauses

#### **CREATE VIEW**

```
CREATE
    [OR REPLACE]
    [ALGORITHM = {MERGE|TEMPTABLE|UNDEFINED}]
    [DEFINER = {'user'@'host'|CURRENT_USER}]
    [SQL SECURITY {DEFINER|INVOKER}]
VIEW view [(column, ...)]
AS SELECT ...
[WITH [CASCADED|LOCAL] CHECK OPTION]
```

The content of a view are based on the `SELECT` statement given in the `AS` clause. Users can subsequently issue queries and updates to the view in place of a table; updates ultimately change the data in the tables that underlie the views.

The name of the view cannot be the same as a table in the database, because they share the same tablespace. A view can be based on other views, rather than directly based on a table.

A few parameters may appear between the `CREATE` and `VIEW` keywords. 
- The `OR PEPLACE` parameter will overwrite a view with the same name. 
- Another user can be specified with the `DEFINER` clause. (version 5.1.2 of MySQL)
- The `SQL SECURITY` clause instructs MySQL to authorize access to the view based on the privileges of either the user account of the view's creator (`DEFINER`, the default) or the user account of the user who is querying the view (`INVOKER`). 
- The `ALGORITHM` parameter selects one of the two types of algorithmic methods to use for processing a view: `MERGE` or `TEMPTABLE`. `TEMPTABLE` prevents a view from being updatable. The defaut of `UNDEFINED` leaves the choice to MySQL.
- The `WITH CHECK OPTION` clause restricts updates to rows in which the `WHERE` clause of the underlying `SELECT` statement returns true. For a view that is based on another view, if you include the `LOCAL` keyword, this restriction will be limited to the view in which it's given and not the underlying view. If you use the default choice of `CASCADED`, the `WHERE` clause of underlying views will be considered as well.

To see a list of existing views for the current database, run `SHOW FULL TABLES WHERE Table_type = 'VIEW';`.

## Chapter 6. Data Manipulation

### 6.2 Statements and Clauses

#### SELECT
##### **Grouping SELECT results**

```
SELECT [flags] {*|column|expression}[, ...]
FROM table[, ...]
[WHERE condition]
[GROUP BY {column|expression|position} [ASC|DESC], ...
    [WITH ROLLUP]]
[other clauses] [options]
```

A `SELECT` statement sometimes produces more meaningful results if you group together rows containing the same value for a particular column. The `GROUP BY` clause specifies one or more columns by which MySQL is to group the data retrieved. This is used with aggregate functions so that the values of numeric columns for the rows grouped will be aggregated.

You can specify multiple columns in the `GROUP BY` clause. Instead of stating a column's name, you can state its position in the table, where a value 1 represents the first column in the table. Expressions my be given as well.

For example, we count the number of cities of each provinces from table *province* and *city*:

```
> SELECT p.`id` AS p_id, p.`name` AS p_name, IFNULL(COUNT(c.id), 0) AS n_cities
	FROM city c RIGHT JOIN province p ON p.id = c.province_id
  GROUP BY 1;
+------+----------+----------+
| p_id | p_name   | n_cities |
+------+----------+----------+
|    1 | 北京市   |        1 |
|    2 | 天津市   |        1 |
|    3 | 上海市   |        1 |
|    4 | 重庆市   |        1 |
|    5 | 河北省   |       11 |
|    6 | 山西省   |       11 |
+------+----------+----------+
```

The `GROUP BY` clause does its own sorting and cannot be used with the `ORDER BY` clause. To set the sorting to ascending/descending order explicitly for a column, enter the `ASC`/`DESC` keyword after the column in the clause that is to be set. 

##### Having SELECT results

```
SELECT [flags] {*|column|expression}[, ...]
FROM table[, ...]
[WHERE condition]
[GROUP BY condition]
[HAVING condition]
[other clauses] [options]
```

The `HAVING` clause is similar to the `WHERE` clause, but it is used for conditions returned by aggregate functions (e.g., **AVG( )**, **MIN( )**, and **MAX( )**). For older visions of MySQL, you must use aliases for aggregate functions in the main clause of the `SELECT` statement.

## Chapter 10. Aggregate Clauses, Aggregate Functions, and Subqueries

### 10.1 Aggregate Functions
A few general aspects of aggregate functions include:

- Aggregate functions return NULL when they encounter an error.
- Most uses for aggregate functions include a `GROUP BY` clause, which is specified in each description. If an aggregate function is used without a `GROUP BY` clause it operates on all rows.

#### AVG()
```
AVG([DISTINCT] column)
```

#### GROUP_CONCAT()

```
GROUP_CONCAT([DISTINCT] expression[, ...])
    [ORDER BY {unsigned_integer|column|expression}
    [ASC|DESC] [,column...]]
    [SEPARATOR character]
```

As an example of this function, we list provinces and their cities. We could enter an SQL statement like this:

```
> SELECT p.`id` AS p_id,
	p.`name` AS p_name, 
	GROUP_CONCAT(c.`name` ORDER BY c.`name` ASC SEPARATOR '、') AS cities
 FROM city c RIGHT JOIN province p ON p.id = c.province_id
 GROUP BY 1;

+------+--------+-----------------------------------------------------------+
| p_id | p_name | cities                                                    |
+------+--------+-----------------------------------------------------------+
|    1 | 北京市    | 北京市                                                  |
|    2 | 河北省    | 保定市、沧州市、承德市、邯郸市、衡水市、廊坊市、秦皇岛市    |
|    3 | 山西省    | 长治市、大同市、晋城市、晋中市、临汾市、吕梁市、朔州市      |
+------+--------+-----------------------------------------------------------+
```