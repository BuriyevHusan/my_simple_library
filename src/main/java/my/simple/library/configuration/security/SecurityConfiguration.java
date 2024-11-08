package my.simple.library.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfiguration {
    private final String[] WHITE_LIST = {
            "/app/home",
            "/app/login",
            "/app/register",
            "/app/logout",
    };

    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationHandler customAuthenticationHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    public SecurityConfiguration(CustomUserDetailsService userDetailsService, CustomAuthenticationHandler customAuthenticationHandler, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.customAuthenticationHandler = customAuthenticationHandler;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests()
                .requestMatchers(WHITE_LIST)
                .permitAll() // open Urls
//                .requestMatchers("/home/admin").hasRole("ADMIN")
//                .requestMatchers("/home/user").hasRole("USER")
                .anyRequest()
                .authenticated();

        http.formLogin()
                .loginPage("/app/login")
                .usernameParameter("username")
                .passwordParameter("password")
//                .loginProcessingUrl("/app/login") //
                .failureHandler(customAuthenticationHandler)
                .successHandler(customAuthenticationSuccessHandler);

        http.userDetailsService(userDetailsService);
        http.logout()
                .logoutUrl("/app/logout")
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/app/logout", HttpMethod.POST.name()))
                .logoutSuccessUrl("/app/login");

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
