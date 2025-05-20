package com.dap_studio.spring_boot_expert_library.service;

import com.dap_studio.spring_boot_expert_library.dao.BookRepository;
import com.dap_studio.spring_boot_expert_library.entity.Book;
import com.dap_studio.spring_boot_expert_library.request_models.AddBookRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AdminService {

    private final BookRepository bookRepository;


    public AdminService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void postBook(AddBookRequest addBookRequest) {
        Book book = new Book();

        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setDescription(addBookRequest.getDescription());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setCategory(addBookRequest.getCategory());
        book.setImg(addBookRequest.getImg());

        bookRepository.save(book);
    }
}
