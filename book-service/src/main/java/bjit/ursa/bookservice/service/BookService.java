package bjit.ursa.bookservice.service;

import bjit.ursa.bookservice.entity.BookEntity;
import org.springframework.http.ResponseEntity;

public interface BookService {
    ResponseEntity<Object> addBooks(BookEntity bookEntity);

    ResponseEntity<Object> getAllBooks();

    ResponseEntity<Object> updateBooks(long bookId , BookEntity bookEntity);

    String deleteBookById(long bookId);

    ResponseEntity<Object> getBookById(long bookId);
}
