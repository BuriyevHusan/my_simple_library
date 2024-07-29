package my.simple.library.controller.home;

import my.simple.library.model.AuthUser;
import my.simple.library.repository.AuthRepository;
import my.simple.library.utls.CheckEmail;
import my.simple.library.utls.CheckPassword;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Optional;

@Controller
public class RegisterController {

    private final AuthRepository authRepository;

    public RegisterController(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @GetMapping("/app/register")
    public String register() {
        return "home/sign_up";
    }

    @PostMapping("/app/register")
    public ModelAndView registerSubmit(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "username") String username,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password) {

        ModelAndView modelAndView = new ModelAndView();

        HashMap<String, String> errors = new HashMap<>();

        if (name == null || name.isEmpty()) {
            errors.put("name_error", "Name is required");
        }
        if (username == null || username.isEmpty()) {
            Optional<AuthUser> byUsername = authRepository.findByUsername(username);
            if (byUsername.isPresent()) {
                errors.put("username_error", "Username already exists");
            }
        }
        if ((email == null || email.isEmpty()) && !CheckEmail.checkEmail(email)) {
            Optional<AuthUser> byEmail = authRepository.findByEmail(email);
            if (byEmail.isPresent()) {
                errors.put("email_error", "Please enter a valid email");
            }
        }
        if ((password == null || password.isEmpty()) && !CheckPassword.checkPassword(password)) {
            errors.put("password_error", "Password does not match");
        }
        if (!errors.isEmpty()) {
            errors.forEach(modelAndView::addObject);
            modelAndView.setViewName("home/sign_up");
        } else {
            AuthUser authUser = new AuthUser();
            authUser.setName(name);
            authUser.setUsername(username);
            authUser.setEmail(email);
            authUser.setPassword(password);

            authRepository.save(authUser);
            modelAndView.setViewName("home/sign_in");
        }

        return modelAndView;
    }
}
