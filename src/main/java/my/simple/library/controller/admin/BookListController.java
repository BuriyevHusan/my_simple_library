package my.simple.library.controller.admin;

import my.simple.library.model.Book;
import my.simple.library.repository.BookRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BookListController {
    private final BookRepository bookRepository;

    public BookListController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/app/admin/book-list")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crudBook() {
        ModelAndView modelAndView = new ModelAndView();

        List<Book> books = bookRepository.findAll();

        modelAndView.addObject("books", books);
        modelAndView.setViewName("admin/book_list");
        return modelAndView;
    }
}
