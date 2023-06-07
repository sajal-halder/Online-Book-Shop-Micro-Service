package bjit.ursa.bookservice.service;

import bjit.ursa.bookservice.entity.BookEntity;
import bjit.ursa.bookservice.model.APIResponse;
import bjit.ursa.bookservice.model.BookModel;
import org.springframework.http.ResponseEntity;

public interface BookService {
    ResponseEntity<APIResponse<?>> addBooks(BookModel bookModel);

    ResponseEntity<APIResponse<?>> getAllBooks();

    ResponseEntity<APIResponse<?>> updateBooks(Long bookId , BookModel bookModel);

    ResponseEntity<APIResponse<?>> deleteBookById(Long bookId);

    ResponseEntity<APIResponse<?>> getBookById(Long bookId);

    ResponseEntity<APIResponse<?>> buyBook(Long bookId, Double price);
}
