package com.stock.book.bookStock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookAlreadyRegisteredException extends Exception{
    public BookAlreadyRegisteredException(String bookName) {
        super(String.format("Book with name %s already registered in the system.", bookName));
    }
}
