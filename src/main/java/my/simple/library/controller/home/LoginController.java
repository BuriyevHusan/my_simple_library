package my.simple.library.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class LoginController {
    @GetMapping("/app/login")
    public String login(){
        return "home/sign_in";
    }
}
