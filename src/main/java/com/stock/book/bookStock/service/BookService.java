package com.stock.book.bookStock.service;

import com.stock.book.bookStock.dto.BookDTO;
import com.stock.book.bookStock.entity.Book;
import com.stock.book.bookStock.exception.BookNotFoundException;
import com.stock.book.bookStock.exception.BookStockExceededException;
import com.stock.book.bookStock.mapper.BookMapper;
import com.stock.book.bookStock.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

     private final BookRepository bookRepository;
     private final BookMapper bookMapper;

     public BookDTO createBook(BookDTO bookDTO) {
          verifyIfIsAlreadyRegistered(bookDTO.getName());
          Book book = bookMapper.toModel(bookDTO);
          Book savedBook = bookRepository.save(book);
          return bookMapper.toDTO(savedBook);
     }

     public  BookDTO findByName(String name) throws BookNotFoundException {
          Book bookFound = bookRepository.findByName(name)
                  .orElseThrow(()->new BookNotFoundException(name));
          return bookMapper.toDTO(bookFound);
     }

     public void deleteById(Long id) throws BookNotFoundException {
          verifyIfExists(id);
          bookRepository.deleteById(id);
     }

     public List<BookDTO> listBooks(){
          return bookRepository.findAll()
                  .stream()
                  .map(bookMapper::toDTO)
                  .collect(Collectors.toList());
     }

     private Book verifyIfExists(Long id) throws BookNotFoundException {
          return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
     }

     private void verifyIfIsAlreadyRegistered(String name)   {
          Optional<Book> optSavedBook = bookRepository.findByName(name);
     }

     public BookDTO increment(Long id, int quantityToIncrement) throws BookNotFoundException, BookStockExceededException {
          Book bookToIncrementStock = verifyIfExists(id);
          int quantityAfterIncrement = quantityToIncrement + bookToIncrementStock.getQuantity();
          if (quantityAfterIncrement <= bookToIncrementStock.getMax()) {
               bookToIncrementStock.setQuantity(bookToIncrementStock.getQuantity() + quantityToIncrement);
               Book incrementedBeerStock = bookRepository.save(bookToIncrementStock);
               return bookMapper.toDTO(incrementedBeerStock);
          }
          throw new BookStockExceededException(id, quantityToIncrement);
     }
}
