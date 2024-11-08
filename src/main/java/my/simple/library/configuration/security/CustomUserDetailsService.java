package my.simple.library.configuration.security;

import lombok.RequiredArgsConstructor;
import my.simple.library.model.AuthUser;
import my.simple.library.model.UserRole;
import my.simple.library.repository.RoleRepository;
import my.simple.library.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthUser> optionalAuthUser = userRepository.findByUsername(username);
        AuthUser authUser = optionalAuthUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<UserRole> roles = roleRepository.findByUserId(authUser.getId());

        authUser.setRoles(roles);

        return new CustomUserDetails(authUser);
    }
}
