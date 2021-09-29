package com.stock.book.bookStock.controller;

import com.stock.book.bookStock.dto.BookDTO;
import com.stock.book.bookStock.dto.QuantityDTO;
import com.stock.book.bookStock.exception.BookNotFoundException;
import com.stock.book.bookStock.exception.BookStockExceededException;
import com.stock.book.bookStock.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {
  private final BookService bookService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookDTO createBook(@RequestBody @Valid BookDTO bookDTO){
    return bookService.createBook(bookDTO);
  }

  @GetMapping("/{name}")
  public BookDTO findByName(@PathVariable String name) throws BookNotFoundException {
    return bookService.findByName(name);
  }

  @GetMapping
  public List<BookDTO> listBeers() {
    return bookService.listBooks();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id) throws BookNotFoundException {
    bookService.deleteById(id);
  }

  @PatchMapping("/{id}/increment")
  public BookDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws BookNotFoundException, BookStockExceededException {
    return bookService.increment(id, quantityDTO.getQuantity());
  }

}
