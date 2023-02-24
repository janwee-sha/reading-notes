# Three ways to optimize paging MySQL

> Quoted from https://dzone.com/articles/3-ways-optimize-paging-mysql

## 1. Paging without discarding records

Ultimately we’re trying to avoid discarding records. After all if the server doesn’t fetch them, we save big. How else can we avoid this extra work.

How about remember the last name. For example:

```
SELECT id, name, address, phone
FROM customers
WHERE id > 990
ORDER BY id LIMIT 10;
```

Of course such a solution would only work if you were paging by ID. If you page by name, it might get messier as there may be more than one person with the same name. If ID doesn’t work for your application, perhaps returning paged users by USERNAME might work. Those would be unique:

```
SELECT id, username
FROM customers
WHERE username > 'shull@iheavy.com'
ORDER BY username LIMIT 10;
```

## 2. Try using a Deferred Join

MySQL is first scanning an index then retrieving rows in the table by primary key id. So it’s doing double lookups and so forth. Turns out you can make this faster with a tricky thing called a deferred join.

The inside piece just uses the primary key. An explain plan shows us `USING INDEX`.

```
SELECT id, name, address, phone
FROM customers
    INNER JOIN (
        SELECT id
        FROM customers
        ORDER BY name
        LIMIT 10 OFFSET 990)
        AS my_results USING(id);
```

## 3. Maintain a Page or Place column

Another way to trick the optimizer from retrieving rows it doesn’t need is to maintain a column for the page, place or position. Yes you need to update that column whenever you `INSERT`, `DELETE` a row or move a row by `UPDATE`. This could get messy with page, but a straight place or position might work easier.

```
SELECT id, name, address, phone
FROM customers
WHERE page = 100
ORDER BY name;
```

Or with place column something like this:

```
SELECT id, name, address, phone
FROM customers
WHERE place BETWEEN 990 AND 999
ORDER BY name;
```

# Hibernate caching

> The following is quoted from https://www.tutorialspoint.com/hibernate/hibernate_caching.htm#