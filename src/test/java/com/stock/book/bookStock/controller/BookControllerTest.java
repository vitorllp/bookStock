package com.stock.book.bookStock.controller;

import com.stock.book.bookStock.builder.BookDTOBuilder;
import com.stock.book.bookStock.dto.BookDTO;
import com.stock.book.bookStock.dto.QuantityDTO;
import com.stock.book.bookStock.exception.BookNotFoundException;
import com.stock.book.bookStock.service.BookService;
import com.stock.book.bookStock.utils.JsonConvertionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.stock.book.bookStock.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest {

    private static final String BOOK_API_URL_PATH = "/api/books";
    private static final long VALID_BOOK_ID = 1L;
    private static final long INVALID_BOOK_ID = 2l;
    private static final String BOOK_API_SUBPATH_INCREMENT_URL = "/increment";
    private static final String BOOK_API_SUBPATH_DECREMENT_URL = "/decrement";

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    /*
    @Test
    void whenPostIsCalledThenBookIsCreated() throws Exception {
        BookDTO bookDTO = BookDTOBuilder.builder().build().toBookDTO();
        when(bookService.createBook(bookDTO)).thenReturn(bookDTO);

        mockMvc.perform(post(BOOK_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(bookDTO)))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.name", is(bookDTO.getName())))
                .andExpect((ResultMatcher) jsonPath("$.author", is(bookDTO.getAuthor())))
                .andExpect((ResultMatcher) jsonPath("$.type", is(bookDTO.getType().toString())));


    }

     */

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        BookDTO bookDTO = BookDTOBuilder.builder().build().toBookDTO();
        bookDTO.setAuthor(null);

        // then
        mockMvc.perform(post(BOOK_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(bookDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception, BookNotFoundException {
        // given
        BookDTO bookDTO = BookDTOBuilder.builder().build().toBookDTO();

        //when
        when(bookService.findByName(bookDTO.getName())).thenThrow(BookNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(BOOK_API_URL_PATH + "/" + bookDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception, BookNotFoundException {
        //when
        doThrow(BookNotFoundException.class).when(bookService).deleteById(INVALID_BOOK_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(BOOK_API_URL_PATH + "/" + INVALID_BOOK_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /*
    @Test
    void whenPATCHIsCalledToIncrementDiscountThenOKstatusIsReturned() throws Exception, BookNotFoundException {
        QuantityDTO quantityDTO = QuantityDTO.builder()
                .quantity(10)
                .build();

        BookDTO bookDTO = BookDTOBuilder.builder().build().toBookDTO();
        bookDTO.setQuantity(bookDTO.getQuantity() + quantityDTO.getQuantity());

        when(bookService.increment(VALID_BOOK_ID, quantityDTO.getQuantity())).thenReturn(bookDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch(BOOK_API_URL_PATH + "/" + VALID_BOOK_ID + BOOK_API_SUBPATH_INCREMENT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(quantityDTO))).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(bookDTO.getName())))
                .andExpect(jsonPath("$.brand", is(bookDTO.getAuthor())))
                .andExpect(jsonPath("$.type", is(bookDTO.getType().toString())))
                .andExpect(jsonPath("$.quantity", is(bookDTO.getQuantity())));
    }
     */
}
