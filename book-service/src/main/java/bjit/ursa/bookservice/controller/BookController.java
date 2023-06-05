package bjit.ursa.bookservice.controller;

import bjit.ursa.bookservice.entity.BookEntity;
import bjit.ursa.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book-service")
@RequiredArgsConstructor

public class BookController {

    private final BookService bookService ;
    @PostMapping("/create")
    public ResponseEntity<Object> createBooks(@RequestBody BookEntity bookEntity){
        return bookService.addBooks(bookEntity);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<Object> deleteBookById(@PathVariable long bookId) {
        return ResponseEntity.ok(bookService.deleteBookById(bookId));
    }

    @PutMapping("/update/bookId/{bookId}")
    public ResponseEntity<Object> updateBook(@RequestParam long bookId , @RequestBody BookEntity bookEntity) {
        return bookService.updateBooks(bookId , bookEntity);
    }

    @GetMapping("/book/all")
    public ResponseEntity<Object> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/id/{bookId}")
    public ResponseEntity<Object> getBookById(@PathVariable long bookId) {
        return bookService.getBookById(bookId);
    }
}
