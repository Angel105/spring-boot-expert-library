package com.dap_studio.spring_boot_expert_library.controller;

import com.dap_studio.spring_boot_expert_library.entity.Book;
import com.dap_studio.spring_boot_expert_library.service.BookService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/secure/currentLoans/count")
    public int currentLoansCount() {
        String userEmail = "test_user";
        return bookService.currentLoansCount(userEmail);
    }

    @GetMapping("/secure/isCheckedOut/byUser")
    public Boolean checkedOutByUser(@RequestParam Long bookId) throws Exception {
        String userEmail = "test_user";
        return bookService.checkedOutByUser(userEmail, bookId);
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestParam Long bookId) throws Exception {
        String userEmail = "test_user";
        return bookService.checkoutBook(userEmail, bookId);
    }
}
