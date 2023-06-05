package bjit.ursa.bookservice.service.impl;

import bjit.ursa.bookservice.entity.BookEntity;
import bjit.ursa.bookservice.exception.BookServiceException;
import bjit.ursa.bookservice.repository.BookRepository;
import bjit.ursa.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImplementation implements BookService {

    private final BookRepository bookRepository;
    @Override
    @Transactional
    public ResponseEntity<Object> addBooks(BookEntity bookEntity) {

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
                return new ResponseEntity<>("Book is created successfully", HttpStatus.CREATED);
            }else{
                throw new BookServiceException("Failed to added the book.");
            }
        }catch (Exception e) {
            throw new BookServiceException("An error occurred while adding the book.");
        }

    }

    @Override
    @Transactional
    public ResponseEntity<Object> getAllBooks() {
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

        return ResponseEntity.ok(bookResponses);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> updateBooks(Integer bookId , BookEntity bookEntity) {

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

                return new ResponseEntity<>(updatedBook, HttpStatus.OK);
            } else {
                throw new BookServiceException("Book not found");
            }
        }catch (Exception e){
            throw  new BookServiceException("An error occurred while retrieving the book by ID");
        }
    }
    @Override
    @Transactional
    public String deleteBookById(Integer bookId) {
        try {
            if (bookRepository.existsById(bookId)) {
                bookRepository.deleteById(bookId);
                return "Book is deleted successfully";
            } else {
                throw new BookServiceException("Book not found");
            }
        }catch (Exception e){
            throw new BookServiceException("An error occurred while retrieving the book by ID");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Object> getBookById(Integer bookId) {
        try {
            Optional<BookEntity> optionalBook = bookRepository.findById(bookId);
            if (optionalBook.isPresent()) {
                return ResponseEntity.ok(optionalBook.get());
            } else {
                throw new BookServiceException("Book not found");
            }
        } catch (Exception e) {
            throw new BookServiceException("An error occurred while retrieving the book by ID");
        }
    }

}
