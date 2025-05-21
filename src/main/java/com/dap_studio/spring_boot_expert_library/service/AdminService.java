package com.dap_studio.spring_boot_expert_library.service;

import com.dap_studio.spring_boot_expert_library.dao.BookRepository;
import com.dap_studio.spring_boot_expert_library.entity.Book;
import com.dap_studio.spring_boot_expert_library.request_models.AddBookRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AdminService {

    private final BookRepository bookRepository;


    public AdminService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void increaseBookQuantity(Long bookId) throws Exception{
        Optional<Book> book = bookRepository.findById(bookId);
        if (!book.isPresent()) {
            throw new Exception("Book does not exist");
        }
        final int increment = 1;
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + increment);
        book.get().setCopies(book.get().getCopies() + increment);

        bookRepository.save(book.get());
    }

    public void decreaseBookQuantity(Long bookId) throws Exception{
        Optional<Book> book = bookRepository.findById(bookId);
        if (!book.isPresent() || book.get().getCopiesAvailable() <= 0 || book.get().getCopies() <= 0) {
            throw new Exception("Book does not exist or quantity is zero");
        }
        final int decrement = 1;
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - decrement);
        book.get().setCopies(book.get().getCopies() - decrement);

        bookRepository.save(book.get());
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
