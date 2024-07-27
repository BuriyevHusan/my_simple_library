package my.simple.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/app/home")
    @ResponseBody
    public String home(){
        return "<h1>Hello World!</h1>";
    }
}