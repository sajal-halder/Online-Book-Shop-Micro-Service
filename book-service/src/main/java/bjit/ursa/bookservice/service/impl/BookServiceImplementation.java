package bjit.ursa.bookservice.service.impl;

import bjit.ursa.bookservice.entity.BookEntity;
import bjit.ursa.bookservice.exception.BookServiceException;
import bjit.ursa.bookservice.model.APIResponse;
import bjit.ursa.bookservice.model.BookModel;
import bjit.ursa.bookservice.model.InventoryModel;
import bjit.ursa.bookservice.repository.BookRepository;
import bjit.ursa.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImplementation implements BookService {

   // private RestTemplate restTemplate;

    private final BookRepository bookRepository;
    private final RestTemplate restTemplate;
    @Override
    @Transactional
    public ResponseEntity<APIResponse<?>> addBooks(BookModel bookModel) {

        String authorName = bookModel.getAuthorName();
        String bookName = bookModel.getBookName();
        String genre = bookModel.getGenre();
        InventoryModel inventoryModel = InventoryModel.builder()
                .bookPrice(bookModel.getPrice())
                .bookQuantity(bookModel.getQuantity())
                .build();


        // Check if the book already exists


        try {
            if (bookRepository.findByBookNameAndAuthorName(bookName, authorName).isPresent()) {
                throw new BookServiceException("A book with the same name and author already exists.");
            }
            BookEntity book = BookEntity.builder()
                    .bookName(bookName)
                    .authorName(authorName)
                    .genre(genre)
                    .build();
            //saving to book db
            bookRepository.save(book);
            //api call to inventory service
            APIResponse<InventoryModel> response =restTemplate.postForObject("http://localhost:9094/update/" + book.getBook_id(),
                            inventoryModel,
                            APIResponse.class);
            if(response.getData() == null){
                throw new BookServiceException("from inventory "+response.getError_message());
            }
             bookModel.setBook_id(book.getBook_id());
            APIResponse<BookModel> apiResponse = APIResponse.<BookModel>builder()
                    .data(bookModel)
                    .build();

            // Return the ResponseEntity with the APIResponse
            return ResponseEntity.ok(apiResponse);

        }catch (Exception e) {
            throw new BookServiceException(e.getMessage());
        }

    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse<?>> updateBooks(Long bookId , BookModel model) {

        try {
            Optional<BookEntity> optionalBook = bookRepository.findById(bookId);

            if (optionalBook.isPresent()) {
                BookEntity book = optionalBook.get();
                // Update the book entity with the new values from the request model
                book.setBookName(model.getBookName());
                book.setAuthorName(model.getAuthorName());
                book.setGenre(model.getGenre());

                // Save the updated book entity
                BookEntity updatedBook = bookRepository.save(book);

                InventoryModel inventoryModel = InventoryModel.builder()
                        .bookId(updatedBook.getBook_id())
                        .bookQuantity(model.getQuantity())
                        .bookPrice(model.getPrice()).build();
                APIResponse<InventoryModel> response =restTemplate.postForObject("http://localhost:9094/update/" + bookId,
                        inventoryModel,
                        APIResponse.class);
                if(response.getData() == null){
                    throw new BookServiceException("from inventory "+response.getError_message());
                }
                model.setBook_id(bookId);

                APIResponse<BookModel> apiResponse = APIResponse.<BookModel>builder()
                        .data(model)
                        .build();
                // Return the ResponseEntity with the APIResponse
                return ResponseEntity.ok(apiResponse);

               // return new ResponseEntity<>(updatedBook, HttpStatus.OK);
            } else {
                throw new BookServiceException("Book not found");
            }
        }catch (Exception e){

            throw  new BookServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse<?>> getAllBooks() {
        try {
            List<BookEntity> books = bookRepository.findAll();
            if (books.isEmpty()) {
                throw new BookServiceException("There is no book available in the stock");
            }

            List<Long> bookIds = books.stream().map(BookEntity::getBook_id).toList();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<List<Long>> requestEntity = new HttpEntity<>(bookIds,headers);

            ResponseEntity<APIResponse> response =restTemplate.exchange(
                    "http://localhost:9094/",HttpMethod.POST,
                    requestEntity,APIResponse.class);
            APIResponse<List<InventoryModel>> listAPIResponse = response.getBody();
            if(listAPIResponse.getData()==null){
                throw  new BookServiceException(listAPIResponse.getError_message());
            }

            List<InventoryModel> inventoryModelList = listAPIResponse.getData();
            List<BookModel> modelList = new ArrayList<>();

            books.forEach(bookEntity -> {
                modelList.add(
                        BookModel.builder()
                                .book_id(bookEntity.getBook_id())
                                .bookName(bookEntity.getBookName())
                                .authorName(bookEntity.getAuthorName())
                                .genre(bookEntity.getGenre())
                                .price(inventoryModelList.stream().filter(i->i.getBookId().equals(bookEntity.getBook_id())).findAny().get().getBookPrice())
                                .quantity(inventoryModelList.stream().filter(i->i.getBookId().equals(bookEntity.getBook_id())).findAny().get().getBookQuantity())
                                .build()
                );
            });

           APIResponse<List<BookModel>> apiResponse = APIResponse.<List<BookModel>>builder().data(modelList).build();
           return ResponseEntity.ok(apiResponse);


        }catch (Exception e){
            throw  new BookServiceException(e.getMessage());
        }



        // return ResponseEntity.ok(bookResponses);
    }
    @Override
    @Transactional
    public ResponseEntity<APIResponse<?>> deleteBookById(Long bookId) {
        try {
            if (bookRepository.existsById(bookId)) {
                bookRepository.deleteById(bookId);

                String message ="Book is deleted successfully";

                APIResponse response =restTemplate.exchange(
                        "http://localhost:9094/delete/" +bookId,HttpMethod.DELETE,
                        new HttpEntity<>(new HttpHeaders()),APIResponse.class
                ).getBody();
                if(response.getData()==null){
                    throw  new BookServiceException(response.getError_message());
                }
                // Return the ResponseEntity with the APIResponse
                return ResponseEntity.ok(response);
            } else {
                throw new BookServiceException("Book not found");
            }
        }catch (Exception e){
            throw new BookServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse<?>> getBookById(Long bookId) {
        try {
            Optional<BookEntity> optionalBook = bookRepository.findById(bookId);
            if (optionalBook.isPresent()) {



                APIResponse response = restTemplate.getForObject(
                        "http://localhost:9094/"+bookId,APIResponse.class);
                if(response.getData() == null){
                    throw new BookServiceException("from inventory "+response.getError_message());
                }
                LinkedHashMap<String,?> data = (LinkedHashMap<String, ?>) response.getData();
                BookModel bookModel = BookModel.builder()
                        .book_id(bookId)
                        .bookName(optionalBook.get().getBookName())
                        .authorName(optionalBook.get().getAuthorName())
                        .genre(optionalBook.get().getGenre())
                        .price((Double) data.get("bookPrice"))
                        .quantity((Integer) data.get("bookQuantity"))
                        .build();

                APIResponse apiResponse = APIResponse.builder()
                        .data(bookModel)
                        .build();

                // Return the ResponseEntity with the APIResponse
                return ResponseEntity.ok(apiResponse);

                //return ResponseEntity.ok(optionalBook.get());
            } else {
                throw new BookServiceException("Book not found");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BookServiceException(e.getMessage());
        }
    }

}
