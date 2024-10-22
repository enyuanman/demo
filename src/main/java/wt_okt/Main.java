package wt_okt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
@RestController

public class Main {

    @Autowired
    private BookRepository bookRepository;



    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/greet")
    public String greet(){
        return "Hello World";
    }

    @GetMapping("/getBooks")
    public List<Book> getBooks() {
        return (List<Book>)bookRepository.findAll();
    }

    @PostMapping("/addBook")
    public ResponseEntity<String> addNewBook(@RequestBody Book book){
        bookRepository.save(book);
        return ResponseEntity.ok().body(book.getId()+book.getTitel()+book.getAantalExemplaren()+book.getAfbeeldingURL());
    }

    @PostMapping("/addBooks")
    public String addNewBooks(@RequestBody List<Book> bookList){
        bookRepository.saveAll(bookList);
        return "Saved all books";
    }

    // updates title, name might need a few more
    @PutMapping("/update/{id}")
    public String updateUser (@PathVariable int id, @RequestBody Book book) {
        Book updatedBook = bookRepository.findById(id).get();
        updatedBook.setTitel(book.getTitel());
        bookRepository.save(updatedBook);
        return "Book id: " + id + " updated";
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteBook(@PathVariable int id){
        Book deleteBook = bookRepository.findById(id).get();
        bookRepository.delete(deleteBook);
        return "book with id: " + id + " deleted.";
    }


}
