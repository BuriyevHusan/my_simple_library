package my.simple.library.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan("my.simple.library")
@Configuration
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {

}
