package my.simple.library.controller.user;

import my.simple.library.model.Attachment;
import my.simple.library.model.Book;
import my.simple.library.repository.AttachmentRepository;
import my.simple.library.repository.BookRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UserBookController {
    private final BookRepository bookRepository;
    private final AttachmentRepository attachmentRepository;

    public UserBookController(BookRepository bookRepository, AttachmentRepository attachmentRepository) {
        this.bookRepository = bookRepository;
        this.attachmentRepository = attachmentRepository;
    }

    @GetMapping("/app/user/home/book/{id}")
    @PreAuthorize("hasRole('USER')")
    public ModelAndView userBook(@PathVariable(name = "id", required = false) String id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);

        ModelAndView modelAndView = new ModelAndView();

        if (optionalAttachment.isPresent() && optionalBook.isPresent()) {
            Book book = optionalBook.get();
            Attachment attachment = optionalAttachment.get();

            modelAndView.addObject("book", book);
            modelAndView.addObject("attachment", attachment);

            modelAndView.setViewName("user/user_home_book");
            return modelAndView;
        }else {
            modelAndView.setViewName("user/user_home_book");
        }

        return modelAndView;
    }
}
