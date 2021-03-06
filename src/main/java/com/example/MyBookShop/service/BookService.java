package com.example.MyBookShop.service;

import com.example.MyBookShop.model.Book;
import com.example.MyBookShop.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Spring REST Controller Methods

    public List<Book> getBooksByAuthorName(String authorName){
        return bookRepository.findBookByAuthorFirstNameContaining(authorName);
    }

    public List<Book> getBooksByTitle(String title){
        return bookRepository.findBookByTitleContaining(title);
    }

    public List<Book> getBooksByPriceBetween(String min, String max){
        return bookRepository.findBookByPriceBetween(min,max);
    }

    public List<Book> getBooksByPrice(String price){
        return bookRepository.findBookByPriceContaining(price);
    }

    public List<Book> getBestsellers(){
        return bookRepository.getBestsellers();
    }

    public List<Book> getBooksByMaxDiscount(){
        return bookRepository.getBooksWithMaxDiscount();
    }

    // Spring Pagination Methods

    public Page<Book> getPageOfRecommended(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findAll(nextPage);
    }

    public Page<Book> getPageOfRecent(Integer offset, Integer limit){
        Pageable next = PageRequest.of(offset,limit);
        return bookRepository.findAll(next);
    }

    public Page<Book> getPageOfPopular(Integer offset, Integer limit){
        Pageable next = PageRequest.of(offset,limit);
        return bookRepository.findAll(next);
    }

    //Spring Paginated Search Method

    public Page<Book> getPageOfBookSearch(String searchWord, Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findBookByTitleContaining(searchWord, nextPage);
    }

    //Method for Filtering Recent Books By Publication Date

    public Page<Book> getPageOfFilteredBooks(Integer offset, Integer limit, String fromStr, String toStr) {
        Pageable next = PageRequest.of(offset,limit);
        SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        Date from = new Date();
        Date to = new Date();
        try {
            from = format.parse(fromStr);
            to = format.parse(toStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return bookRepository.findBookByPubDateBetween(from, to, next);
    }
}
