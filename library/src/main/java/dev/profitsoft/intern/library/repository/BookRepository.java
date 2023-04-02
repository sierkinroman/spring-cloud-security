package dev.profitsoft.intern.library.repository;

import dev.profitsoft.intern.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {



}
