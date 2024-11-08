package my.simple.library.controller.user;

import my.simple.library.model.Book;
import my.simple.library.repository.BookRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final BookRepository bookRepository;

    public UserController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/app/user/home")
    @PreAuthorize("hasRole('USER')")
    public ModelAndView home() {
        List<Book> books = bookRepository.findAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("books", books);
        modelAndView.setViewName("user/user_home");

        return modelAndView;
    }

    @PostMapping("/app/user/home")
    public ModelAndView home(@RequestParam(name = "id") String id) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        ModelAndView modelAndView = new ModelAndView();

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            modelAndView.setViewName("/app/user/home/book/{" + book.getId() + "}");

        } else {
            modelAndView.addObject("book_error", "Book not found");
            modelAndView.setViewName("user/user_home");
        }

        return modelAndView;
    }
}
