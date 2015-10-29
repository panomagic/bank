package banksecure.config;

import banksecure.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("banksecure")
public class WebAppConfig extends WebMvcConfigurerAdapter { //Spring MVC settings

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
    }

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        //resolver.setPrefix("/pages/");
        //resolver.setPrefix("/WEB-INF/");
        //resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);

        return resolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login.jsp");
        registry.addViewController("/testadmin").setViewName("testadminaccess.jsp");
        registry.addViewController("/test").setViewName("testauthaccess.jsp");
        registry.addViewController("/testuser").setViewName("testuseraccess.jsp");
    }

    @Bean
    public UserDetailsService getUserDetailsService(){
        return new UserDetailsServiceImpl();
    }
}