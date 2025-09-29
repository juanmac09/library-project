package com.ibero.diana.library_proyect.services.book.impl;

import com.ibero.diana.library_proyect.dtos.book.ResponseBookDto;
import com.ibero.diana.library_proyect.entities.Book;
import com.ibero.diana.library_proyect.mapping.IMapper;
import com.ibero.diana.library_proyect.repositories.book.BookRepository;
import com.ibero.diana.library_proyect.services.book.IReadBookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReadBookService implements IReadBookService {


    private final BookRepository bookRepository;
    private final IMapper<Book, ResponseBookDto> bookMapper;

    public ReadBookService(BookRepository bookRepository,
                           IMapper<Book, ResponseBookDto> bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }


    @Override
    public Page<ResponseBookDto> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::map);
    }

    @Override
    public ResponseBookDto getBookById(int id) {
        return bookRepository.findById(id)
                .map(bookMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con id: " + id));
    }

    @Override
    public Page<ResponseBookDto> getBooksByAvailability(boolean available, Pageable pageable) {
        return bookRepository.findByAvailable(available, pageable).map(bookMapper::map);
    }

    @Override
    public Page<ResponseBookDto> getBooksByAuthor(int authorId, Pageable pageable) {
        return bookRepository.findByAuthor_Id(authorId, pageable).map(bookMapper::map);
    }

    @Override
    public Page<ResponseBookDto> getBooksByGenre(int genreId, Pageable pageable) {
        return bookRepository.findByGenre_Id(genreId, pageable).map(bookMapper::map);
    }

    @Override
    public Page<ResponseBookDto> getBooksByPriceRange(int minPrice, int maxPrice, Pageable pageable) {
        return bookRepository.findByPriceBetween(minPrice, maxPrice, pageable).map(bookMapper::map);
    }

    @Override
    public Page<ResponseBookDto> getBooksByPublishDateRange(Date startDate, Date endDate, Pageable pageable) {
        return bookRepository.findByPublishDateBetween(startDate, endDate, pageable).map(bookMapper::map);
    }

    @Override
    public Page<ResponseBookDto> searchBooksByName(String name, Pageable pageable) {
        return bookRepository.findByNameContainingIgnoreCase(name, pageable).map(bookMapper::map);
    }
}
