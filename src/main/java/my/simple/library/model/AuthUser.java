package my.simple.library.model;

import lombok.*;
import my.simple.library.model.enums.UserStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUser {
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String password;
    private UserStatus status;
    private List<UserRole> roles;
}
