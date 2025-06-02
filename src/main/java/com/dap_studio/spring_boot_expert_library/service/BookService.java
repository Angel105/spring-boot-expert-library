package com.dap_studio.spring_boot_expert_library.service;

import com.dap_studio.spring_boot_expert_library.dao.BookRepository;
import com.dap_studio.spring_boot_expert_library.dao.CheckoutRepository;
import com.dap_studio.spring_boot_expert_library.dao.HistoryRepository;
import com.dap_studio.spring_boot_expert_library.dao.PaymentRepository;
import com.dap_studio.spring_boot_expert_library.entity.Book;
import com.dap_studio.spring_boot_expert_library.entity.Checkout;
import com.dap_studio.spring_boot_expert_library.entity.History;
import com.dap_studio.spring_boot_expert_library.entity.Payment;
import com.dap_studio.spring_boot_expert_library.response_models.ShelfCurrentLoansResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final CheckoutRepository checkoutRepository;
    private final HistoryRepository historyRepository;
    private final PaymentRepository paymentRepository;

    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository,
                       HistoryRepository historyRepository, PaymentRepository paymentRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
        this.historyRepository = historyRepository;
        this.paymentRepository = paymentRepository;
    }

    public Book checkoutBook (String userEmail, Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book does not exist or already checked out by user");
        }

        List<Checkout> currentBooksCheckedOut = checkoutRepository.findBooksByUserEmail(userEmail);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        boolean bookNeedsToBeReturned = false;

        for (Checkout checkout : currentBooksCheckedOut) {
            Date d1 = sdf.parse(checkout.getReturnDate());
            Date d2 = sdf.parse(LocalDate.now().toString());

            TimeUnit time = DAYS;

            double differenceInTime = time.convert(d1.getTime() - d2.getTime(), MILLISECONDS);

            if (differenceInTime < 0) {
                bookNeedsToBeReturned = true;
                break;
            }
        }

        Payment userPayment = paymentRepository.findByUserEmail(userEmail);
        if ((userPayment != null && userPayment.getAmount() > 0) || (userPayment != null && bookNeedsToBeReturned)) {
            throw new Exception("Outstanding fees exists or book needs to be returned");
        }

        if (userPayment == null) {
            Payment payment = new Payment();
            payment.setUserEmail(userEmail);
            payment.setAmount(0.00);
            paymentRepository.save(payment);
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());

        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                book.get().getId()
        );

        checkoutRepository.save(checkout);

        return book.get();
    }

    public boolean checkoutBookByUser(String userEmail, Long bookId) {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        if (validateCheckout != null) {
            return true;
        } else {
            return false;
        }
    }

    public int currentLoansCount(String userEmail) {
        return checkoutRepository.findBooksByUserEmail(userEmail).size();
    }

    public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception {
        List<ShelfCurrentLoansResponse> shelfCurrentLoansResponses = new ArrayList<>();

        List<Checkout> checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);
        
        List<Long> bookIdList = new ArrayList<>();

        for (Checkout checkout : checkoutList) {
            bookIdList.add(checkout.getBookId());
        }

//        List<Book> bookList = bookRepository.findAllById(bookIdList);
        List<Book> bookList = bookRepository.findBooksByBookIds(bookIdList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Book book : bookList) {
            Optional<Checkout> checkout = checkoutList.stream()
                    .filter(x -> x.getBookId() == book.getId()).findFirst();

            if (checkout.isPresent()) {
                Date d1 = sdf.parse(checkout.get().getReturnDate());
                Date d2 = sdf.parse(LocalDate.now().toString());

                TimeUnit time = DAYS;
                long diffInDays = time.convert(d1.getTime() - d2.getTime(), MILLISECONDS);

                shelfCurrentLoansResponses.add(new ShelfCurrentLoansResponse(book, (int) diffInDays));
            }
        }

        return shelfCurrentLoansResponses;
    }

    public void returnBook(String userEmail, Long bookId) throws Exception {

        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (!book.isPresent() || validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out by user");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);
        bookRepository.save(book.get());

        checkoutRepository.deleteById(validateCheckout.getId());

        History history = new History(
                userEmail,
                validateCheckout.getCheckoutDate(),
                LocalDate.now().toString(),
                book.get().getTitle(),
                book.get().getAuthor(),
                book.get().getDescription(),
                book.get().getImg()
        );

        historyRepository.save(history);
    }

    public void renewLoan(String userEmail, Long bookId) throws Exception {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out by user");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdf.parse(validateCheckout.getReturnDate());
        Date d2 = sdf.parse(LocalDate.now().toString());

        if (d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0) {
            validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
            checkoutRepository.save(validateCheckout);
        }
    }
}
