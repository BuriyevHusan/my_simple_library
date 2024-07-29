package my.simple.library.model;

import lombok.*;

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
}
