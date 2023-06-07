package bjit.ursa.bookservice.controller;

import bjit.ursa.bookservice.entity.BookEntity;
import bjit.ursa.bookservice.model.APIResponse;
import bjit.ursa.bookservice.model.BookModel;
import bjit.ursa.bookservice.service.BookService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book-service")
public class BookController {

    private final BookService bookService ;
    @CircuitBreaker(name="book_inventory",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name="book_inventory")
    @Retry(name="book_inventory")
    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<APIResponse<?>> > createBooks(@RequestBody BookModel bookModel){
       return CompletableFuture.supplyAsync(()->bookService.addBooks(bookModel));
    }

//    @CircuitBreaker(name="book_inventory",fallbackMethod = "fallbackMethod")
//    @TimeLimiter(name="book_inventory")
//    @Retry(name="book_inventory")
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<APIResponse<?>> deleteBookById(@PathVariable Long bookId) {
        return bookService.deleteBookById(bookId);
    }
//    @CircuitBreaker(name="book_inventory",fallbackMethod = "fallbackMethod")
//    @TimeLimiter(name="book_inventory")
//    @Retry(name="book_inventory")
    @PutMapping("/update")
    public ResponseEntity<APIResponse<?>> updateBook( @RequestBody BookModel bookModel) {
        return bookService.updateBooks( bookModel);
    }
    @CircuitBreaker(name="book_inventory",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name="book_inventory")
    @Retry(name="book_inventory")
    @GetMapping("/book/all")
    public CompletableFuture<ResponseEntity<APIResponse<?>>> getAllBooks(){
        return CompletableFuture.supplyAsync(bookService::getAllBooks);
    }
//    @CircuitBreaker(name="book_inventory",fallbackMethod = "fallbackMethod")
//    @TimeLimiter(name="book_inventory")
//    @Retry(name="book_inventory")
    @GetMapping("/book/id/{bookId}")
    public ResponseEntity<APIResponse<?>> getBookById(@PathVariable Long bookId) {
        return bookService.getBookById(bookId);
    }
//    @CircuitBreaker(name="book_inventory",fallbackMethod = "fallbackMethod")
//    @TimeLimiter(name="book_inventory")
//    @Retry(name="book_inventory")
    @PostMapping("/book/buy")
    public ResponseEntity<APIResponse<?>> buyBook(@RequestBody BookModel bookModel){
        return bookService.buyBook(bookModel.getBook_id() , bookModel.getQuantity());
    }

    public CompletableFuture<ResponseEntity<APIResponse<?>>> fallbackMethod(RuntimeException e){
        return CompletableFuture.supplyAsync(()->ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(null,"Inventory Service is Unavailable")));
    }
}
