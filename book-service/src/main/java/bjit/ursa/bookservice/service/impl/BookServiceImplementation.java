package bjit.ursa.bookservice.service.impl;

import bjit.ursa.bookservice.entity.BookEntity;
import bjit.ursa.bookservice.entity.InventoryEntity;
import bjit.ursa.bookservice.exception.BookServiceException;
import bjit.ursa.bookservice.model.APIResponse;
import bjit.ursa.bookservice.repository.BookRepository;
import bjit.ursa.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImplementation implements BookService {

   // private RestTemplate restTemplate;

    private final BookRepository bookRepository;
    @Override
    @Transactional
    public ResponseEntity<APIResponse> addBooks(BookEntity bookEntity) {


//        String genre = bookEntity.getGenre();
//        double price = bookEntity.getPrice();
//        int quantity = bookEntity.getQuantity();

//        ResponseEntity<InventoryEntity> responseEntity = restTemplate
//                .getForEntity("http://localhost:8083/book-inventory/update/{bookId}" + bookEntity.getBook_id(),
//                        InventoryEntity.class);


     ///   InventoryEntity inventoryEntity = responseEntity.getBody();


        String bookName = bookEntity.getBookName();
        String genre = bookEntity.getAuthorName();

        BookEntity book;

        // Check if the book already exists
        if (bookRepository.existsByBookNameAndAuthorName(bookName, genre)) {
            throw new BookServiceException("A book with the same name and genre already exists.");
        }

        try {
            book = BookEntity.builder()
                    .bookName(bookEntity.getBookName())
                    .authorName(bookEntity.getAuthorName())
                    .genre(bookEntity.getGenre())
                    .build();
            bookRepository.save(book);

            if(book != null){
                // Prepare the APIResponse object
                APIResponse apiResponse = APIResponse.builder()
                        .data(book)
                        .build();

                // Return the ResponseEntity with the APIResponse
                return ResponseEntity.ok(apiResponse);

            }else{
                throw new BookServiceException("Failed to added the book.");
            }
        }catch (Exception e) {
            throw new BookServiceException("An error occurred while adding the book.");
        }

    }


    @Override
    @Transactional
    public ResponseEntity<APIResponse> getAllBooks() {
        List<BookEntity> books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new BookServiceException("There is no book available in the stock");
        }
        List<BookEntity> bookResponses = new ArrayList<>();
        books.forEach(bookEntity -> bookResponses.add(
                BookEntity.builder()
                        .book_id(bookEntity.getBook_id())
                        .bookName(bookEntity.getBookName())
                        .authorName(bookEntity.getAuthorName())
                        .genre(bookEntity.getGenre())
                        .build()
        ));

        APIResponse apiResponse = APIResponse.builder()
                .data(bookResponses)
                .build();

         //Return the ResponseEntity with the APIResponse
        return ResponseEntity.ok((APIResponse) apiResponse);


       // return ResponseEntity.ok(bookResponses);
    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse> updateBooks(Long bookId , BookEntity bookEntity) {

        try {
            Optional<BookEntity> optionalBook = bookRepository.findById(bookId);

            if (optionalBook.isPresent()) {
                BookEntity book = optionalBook.get();
                // Update the book entity with the new values from the request model
                book.setBookName(bookEntity.getBookName());
                book.setAuthorName(bookEntity.getAuthorName());
                book.setGenre(bookEntity.getGenre());

                // Save the updated book entity
                BookEntity updatedBook = bookRepository.save(book);

                // Commit the transaction before returning the response
                // This ensures that the changes are persisted in the database
                TransactionAspectSupport.currentTransactionStatus().flush();

                APIResponse apiResponse = APIResponse.builder()
                        .data(updatedBook)
                        .build();

                // Return the ResponseEntity with the APIResponse
                return ResponseEntity.ok(apiResponse);


               // return new ResponseEntity<>(updatedBook, HttpStatus.OK);
            } else {
                throw new BookServiceException("Book not found");
            }
        }catch (Exception e){

            throw  new BookServiceException("An error occurred while retrieving the book by ID");
        }
    }
    @Override
    @Transactional
    public ResponseEntity<APIResponse> deleteBookById(Long bookId) {
        try {
            if (bookRepository.existsById(bookId)) {
                bookRepository.deleteById(bookId);
                //return "Book is deleted successfully";

                List<String> list = new ArrayList<>();
                String message ="Book is deleted successfully";
                list.add(message);
                APIResponse apiResponse = APIResponse.builder()
                        .data(list)
                        .build();

                // Return the ResponseEntity with the APIResponse
                return ResponseEntity.ok(apiResponse);
            } else {
                throw new BookServiceException("Book not found");
            }
        }catch (Exception e){
            throw new BookServiceException("An error occurred while retrieving the book by ID");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse> getBookById(Long bookId) {
        try {
            Optional<BookEntity> optionalBook = bookRepository.findById(bookId);
            if (optionalBook.isPresent()) {

                APIResponse<BookEntity> apiResponse = APIResponse.<BookEntity>builder()
                        .data(optionalBook.get())
                        .build();

                // Return the ResponseEntity with the APIResponse
                return ResponseEntity.ok(apiResponse);

                //return ResponseEntity.ok(optionalBook.get());
            } else {
                throw new BookServiceException("Book not found");
            }
        } catch (Exception e) {
            throw new BookServiceException("An error occurred while retrieving the book by ID");
        }
    }

}
