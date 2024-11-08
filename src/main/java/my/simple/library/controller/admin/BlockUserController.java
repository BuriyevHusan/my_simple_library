package my.simple.library.controller.admin;


import my.simple.library.model.AuthUser;
import my.simple.library.model.enums.UserStatus;
import my.simple.library.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class BlockUserController {

    private final UserRepository userRepository;

    public BlockUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/app/admin/user-list/block/{id}")
    public ModelAndView blockUserList(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<AuthUser> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            AuthUser user = userOptional.get();
            user.setStatus(UserStatus.BLOCK);

            userRepository.update(user);

            modelAndView.addObject("user_success", "Successfully blocked user");
        }else {
            modelAndView.addObject("user_error", "User not found");
        }

        modelAndView.setViewName("admin/block_user");
        return modelAndView;
    }
}
