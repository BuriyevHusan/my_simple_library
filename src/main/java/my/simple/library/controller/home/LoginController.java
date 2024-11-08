package my.simple.library.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class LoginController {
    @GetMapping("/app/login")
    public ModelAndView login(@RequestParam(required = false) String error){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home/sign_in");
        modelAndView.addObject("error", error);
        return modelAndView;
    }
}
