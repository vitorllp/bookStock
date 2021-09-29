package com.stock.book.bookStock.mapper;

import com.stock.book.bookStock.dto.BookDTO;
import com.stock.book.bookStock.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toModel(BookDTO bookTDO);

    BookDTO toDTO(Book book);
}
