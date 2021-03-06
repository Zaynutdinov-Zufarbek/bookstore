package com.example.MyBookShop.repository;

import com.example.MyBookShop.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findBookByAuthorFirstNameContaining(String firstName);

    List<Book> findBookByTitleContaining(String title);

    List<Book> findBookByPriceBetween(String min, String max);

    List<Book> findBookByPriceContaining(String price);

    Page<Book> findBookByTitleContaining(String title, Pageable nextPage);

    Page<Book> findBookByPubDateBetween(Date from, Date to, Pageable nextPage);

    @Query("from Book where isBestseller=true")
    List<Book> getBestsellers();

    @Query(value = "SELECT * FROM books WHERE discount=(SELECT MAX(discount) FROM books)", nativeQuery = true)
    List<Book> getBooksWithMaxDiscount();

}
