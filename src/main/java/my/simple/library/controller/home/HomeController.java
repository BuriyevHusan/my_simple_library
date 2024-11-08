package my.simple.library.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/app/home")
    public String home(){
        return "home/home";
    }

}
