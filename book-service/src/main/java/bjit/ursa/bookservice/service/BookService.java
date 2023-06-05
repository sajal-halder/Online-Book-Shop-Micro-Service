package bjit.ursa.bookservice.service;

import bjit.ursa.bookservice.entity.BookEntity;
import bjit.ursa.bookservice.model.APIResponse;
import org.springframework.http.ResponseEntity;

public interface BookService {
    ResponseEntity<APIResponse> addBooks(BookEntity bookEntity);

    ResponseEntity<APIResponse> getAllBooks();

    ResponseEntity<APIResponse> updateBooks(Long bookId , BookEntity bookEntity);

    String deleteBookById(Long bookId);

    ResponseEntity<APIResponse> getBookById(Long bookId);
}
