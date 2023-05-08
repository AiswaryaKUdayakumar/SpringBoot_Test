package com.example.test.controller;

import com.example.test.exception.IdNotFoundException;
import com.example.test.model.Book;
import com.example.test.model.User;
import com.example.test.repository.BookRepository;
import com.example.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping
public class Librarycontroller {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/addbook")
    public ResponseEntity<Book> create(@RequestBody Book book) {
        bookRepository.save(book);
        return ResponseEntity.ok().body(book);
    }

    @PostMapping("/adduser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id)throws IdNotFoundException {
        Optional<Book> bk = bookRepository.findById(id);
        if (bk.isPresent()) {
            return ResponseEntity.ok().body(bookRepository.findById(id).orElse(null));
        } else {
            //return ResponseEntity.noContent().build();
            throw new IdNotFoundException("No Id");
        }

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> usrr = userRepository.findById(id);
        if (usrr.isPresent()) {
            return ResponseEntity.ok().body(userRepository.findById(id).orElse(null));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateDetails(@RequestBody Book book, @PathVariable long id) {
        Book bbk = null;
        Optional<Book> bkk = bookRepository.findById(id);
        if (bkk.isPresent()) {
            bbk = bkk.get();
            bbk.setId(id);
            bbk.setName(book.getName());
            return ResponseEntity.ok().body(bookRepository.save(bbk));

        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable long id) {
        Optional<Book> bbbk = bookRepository.findById(id);
//        if (bbbk.isPresent()) {
//            bookRepository.deleteById(id);
//            return ResponseEntity.ok().body("deleted..");
//
//       } else {
//            return ResponseEntity.noContent().build();
//        }
        if(bbbk.isEmpty()){
            return ResponseEntity.ok(false);
        }
        else{
            bookRepository.deleteById(id);
            return ResponseEntity.ok(true);
        }
    }
}

