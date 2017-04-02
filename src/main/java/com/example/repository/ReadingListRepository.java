package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domin.Book;


@Repository
public interface ReadingListRepository extends JpaRepository<Book, Long> {
	 List<Book> findByReader(String reader);
}
