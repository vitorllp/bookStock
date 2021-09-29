package com.stock.book.bookStock.builder;

import com.stock.book.bookStock.dto.BookDTO;
import com.stock.book.bookStock.enums.BookType;
import lombok.Builder;

@Builder
public class BookDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Halo";

    @Builder.Default
    private String author = "Vitor";

    @Builder.Default
    private int max = 50;

    @Builder.Default
    private int quantity = 10;

    @Builder.Default
    private BookType type = BookType.FANTASY;

    public BookDTO toBookDTO() {
        return new BookDTO(id,
                name,
                author,
                max,
                quantity,
                type);
    }
}
