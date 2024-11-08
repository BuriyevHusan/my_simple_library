package my.simple.library.controller.admin;

import my.simple.library.model.AuthUser;
import my.simple.library.model.enums.UserStatus;
import my.simple.library.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class UserListController {

    private final UserRepository userRepository;

    public UserListController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/app/admin/user-list")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getUserList() {
        ModelAndView modelAndView = new ModelAndView();

        List<AuthUser> users = userRepository.findAllUser();

        modelAndView.addObject("users", users);

        modelAndView.setViewName("admin/user_list");

        return modelAndView;
    }
}
