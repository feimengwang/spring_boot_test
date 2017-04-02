package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.user.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, String> {

}
