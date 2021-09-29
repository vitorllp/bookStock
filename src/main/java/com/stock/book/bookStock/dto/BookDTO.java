package com.stock.book.bookStock.dto;

import com.stock.book.bookStock.enums.BookType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private long id;

    @NotNull
    @Size(min = 1, max = 200)
    private String name;

    @NotNull
    @Size(min = 1, max = 200)
    private String author;

    @NotNull
    @Size(max = 100)
    private int quantity;

    @NotNull
    @Size(max = 100)
    private int max;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BookType type;

}
