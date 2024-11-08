package my.simple.library.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookDeleteController {
    @PostMapping("/app/admin/book-list/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBook(@RequestParam(name = "id") String id) {


        return "redirect:/app/admin/book-list";
    }
}
