package my.simple.library.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {
    @GetMapping("/app/logout")
    public String logout() {
        return "home/logout";
    }
}
