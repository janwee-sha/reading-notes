# Spring Transaction Propagation

> Quoted from https://dzone.com/articles/spring-boot-transactions-tutorial-understanding-tr

## What Is Transaction Propagation?

Any application involves a number of services or components making a call to other services or components. Transaction propagation indicates if any component or service will or will not participate in a transaction and how will it behave if the calling component/service already has or does not have a transaction created already.

## Transacton Propagation -- REQUIRED (By default)

The method will meake use of the calling service transaction if it exists else create its own. 

For example, `AuthorService.throwIfAuthorNotFound()` method is called by `BookService.edit()` method:

The `edit()` method in `BookService`:

```
@Transactional(propagation = Propagation.REQUIRED)
public void edit(Book book) {
    log.info("Modifying book.");
    try {
        remoteAuthorService.throwIfAuthorNotFound(book.getAuthorId());
    } catch (RuntimeException e) {
        System.err.println("Exception caught.");
    }
    bookRepo.save(book);
}
```

The `throwIfAuthorNotFound()` method will be shown as below:

```
@Transactional(propagation = Propagation.REQUIRED)
public void throwIfAuthorNotFound(Long authorId) {
    if (true) {
        throw new RuntimeException("Author not found");
    }
}
```

Here is a part of the output:
```
Transaction silently rolled back because it has been marked as rollback-only
```

## Transaction Propagation — SUPPORTS

The method will make use of the calling service transaction if it exists. 

## Transaction Propagation — NOT_SUPPORTED

In this case the mothod never run in transaction.

## Transaction Propagation — REQUIRES_NEW

The method always creates a new transaction.

## Transaction Propagation — NEVER

The method never uses transaction and if the calling service has a transaction then the  method throws an exception.

For example:

The `edit()` method in `BookService`:

```
@Transactional(propagation = Propagation.REQUIRED)
public void edit(Book book) {
    log.info("Modifying book.");
    remoteAuthorService.throwIfAuthorNotFound(book.getAuthorId());
    bookRepo.save(book);
}
```

The `throwIfAuthorNotFound()` method in `AuthorService` will be shown as below:

```
@Transactional(propagation = Propagation.NEVER)
public void throwIfAuthorNotFound(Long authorId) {
    if (true) {
        throw new RuntimeException("Author not found");
    }
}
```

Part of the output may looks like the following:

```
org.springframework.transaction.IllegalTransactionStateException: Existing transaction found for transaction marked with propagation 'never'
```

## Transaction Propagation — MANDATORY

In this case the method always needs the calling service to have a transaction else exception is thrown.

If the mathod is called directly or its calling service does not have a transaction, the output would be like the following:

```
IllegalTransactionStateException: No existing transaction found for transaction marked with propagation 'mandatory'
```