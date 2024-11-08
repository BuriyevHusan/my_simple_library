package my.simple.library.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/app/admin/home")
    @PreAuthorize("hasRole('ADMIN')")
    public String home() {
        return "admin/home";
    }
}
