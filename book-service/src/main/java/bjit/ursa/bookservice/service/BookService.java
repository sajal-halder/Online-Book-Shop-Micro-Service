package bjit.ursa.bookservice.service;

import bjit.ursa.bookservice.entity.BookEntity;
import org.springframework.http.ResponseEntity;

public interface BookService {
    ResponseEntity<Object> addBooks(BookEntity bookEntity);

    ResponseEntity<Object> getAllBooks();

    ResponseEntity<Object> updateBooks(Integer bookId , BookEntity bookEntity);

    String deleteBookById(Integer bookId);

    ResponseEntity<Object> getBookById(Integer bookId);
}
