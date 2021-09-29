package com.stock.book.bookStock.service;

import com.stock.book.bookStock.builder.BookDTOBuilder;
import com.stock.book.bookStock.dto.BookDTO;
import com.stock.book.bookStock.entity.Book;
import com.stock.book.bookStock.exception.BookAlreadyRegisteredException;
import com.stock.book.bookStock.exception.BookNotFoundException;
import com.stock.book.bookStock.mapper.BookMapper;
import com.stock.book.bookStock.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private static final long INVALID_BOOK_ID = 1L;

    @Mock
    private BookRepository bookRepository;

    private BookMapper bookMapper = BookMapper.INSTANCE;

    @InjectMocks
    private BookService bookService;

    @Test
    void whenBookInformedThenItShouldBeCreated(){
        BookDTO expectedBookDTO = BookDTOBuilder.builder().build().toBookDTO();
        Book expectedSavedBook = bookMapper.toModel(expectedBookDTO);

        when(bookRepository.findByName(expectedBookDTO.getName())).thenReturn(empty());
        when(bookRepository.save(expectedSavedBook)).thenReturn(expectedSavedBook);

        BookDTO createdBookDTO = bookService.createBook(expectedBookDTO);

        assertThat(createdBookDTO.getId(),is(equalTo(expectedBookDTO.getId())));
        assertThat(createdBookDTO.getName(),is(equalTo(expectedBookDTO.getName())));
        assertThat(createdBookDTO.getQuantity(),is(equalTo(expectedBookDTO.getQuantity())));

    }

    @Test
    void whenAlreadyRegisteredBookThenAnExceptionWillBeThrown() {
        BookDTO expectedBookDTO = BookDTOBuilder.builder().build().toBookDTO();
        Book duplicatedBook = bookMapper.toModel(expectedBookDTO);

        when(bookRepository.findByName(expectedBookDTO.getName())).thenReturn(Optional.of(duplicatedBook));

        assertThrows(BookAlreadyRegisteredException.class, () -> bookService.createBook(expectedBookDTO));
    }

    @Test
    void whenValidBeerNameIsGivenThenReturnABeer() throws BookNotFoundException {
        // given
        BookDTO expectedFoundBookDTO = BookDTOBuilder.builder().build().toBookDTO();
        Book expectedFoundBook = bookMapper.toModel(expectedFoundBookDTO);

        // when
        when(bookRepository.findByName(expectedFoundBook.getName())).thenReturn(Optional.of(expectedFoundBook));

        // then
        BookDTO foundBeerDTO = bookService.findByName(expectedFoundBookDTO.getName());

        assertThat(foundBeerDTO, is(equalTo(expectedFoundBookDTO)));
    }

    @Test
    void whenNotRegisteredBeerNameIsGivenThenThrowAnException() {
        // given
        BookDTO expectedFoundBookDTO = BookDTOBuilder.builder().build().toBookDTO();

        // when
        when(bookRepository.findByName(expectedFoundBookDTO.getName())).thenReturn(empty());

        // then
        assertThrows(BookNotFoundException.class, () -> bookService.findByName(expectedFoundBookDTO.getName()));
    }

    /*
    @Test
    void whenListBeerIsCalledThenReturnAListOfBeers() {
        // given
        BookDTO expectedFoundBeerDTO = BookDTOBuilder.builder().build().toBookDTO();
        Book expectedFoundBeer = bookMapper.toModel(expectedFoundBeerDTO);

        //when
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundBeer));

        //then
        List<BookDTO> foundListBeersDTO = bookService.listBooks();

        assertThat(foundListBeersDTO, is(not(empty())));
        assertThat(foundListBeersDTO.get(0), is(equalTo(expectedFoundBeerDTO)));
    }

     */

    @Test
    void whenListBeerIsCalledThenReturnAnEmptyListOfBeers() {
        //when
        when(bookRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        List<BookDTO> foundListBeersDTO = bookService.listBooks();

        assertThat(foundListBeersDTO, is(empty()));
    }

}
