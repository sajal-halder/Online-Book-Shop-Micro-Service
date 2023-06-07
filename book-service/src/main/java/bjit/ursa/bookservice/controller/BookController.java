package bjit.ursa.bookservice.controller;

import bjit.ursa.bookservice.entity.BookEntity;
import bjit.ursa.bookservice.model.APIResponse;
import bjit.ursa.bookservice.model.BookModel;
import bjit.ursa.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book-service")
public class BookController {

    private final BookService bookService ;
    @PostMapping("/create")
    public ResponseEntity<APIResponse<?>> createBooks(@RequestBody BookModel bookModel){
        return bookService.addBooks(bookModel);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<APIResponse<?>> deleteBookById(@PathVariable Long bookId) {
        return bookService.deleteBookById(bookId);
    }

    @PutMapping("/update")
    public ResponseEntity<APIResponse<?>> updateBook( @RequestBody BookModel bookModel) {
        return bookService.updateBooks( bookModel);
    }

    @GetMapping("/book/all")
    public ResponseEntity<APIResponse<?>> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/book/id/{bookId}")
    public ResponseEntity<APIResponse<?>> getBookById(@PathVariable Long bookId) {
        return bookService.getBookById(bookId);
    }

    @PostMapping("/book/buy")
    public ResponseEntity<APIResponse<?>> buyBook(@RequestBody BookModel bookModel){
        return bookService.buyBook(bookModel.getBook_id() , bookModel.getQuantity());
    }
}
