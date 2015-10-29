package banksecure.config;

import banksecure.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    //@Autowired
    //private DataSource dataSource;

    // регистрируем нашу реализацию UserDetailsService 
    // а также PasswordEncoder для приведения пароля в формат SHA1
  /*  @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService);
    }*/

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/resources/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // включаем защиту от CSRF атак
        http.csrf()
                .disable()
                        // указываем правила запросов
                        // по которым будет определятся доступ к ресурсам и остальным данным
                .authorizeRequests()
                .antMatchers("/index.jsp", "/login", "/test", "/css/**", "/fonts/**", "/js/**", "/image").permitAll()
                .antMatchers("/clientinfo", "/addtransaction", "/transactionshistorybyclient", "/transcurrencymismatch",
                            "/transoverdraft", "/upload").access("hasRole('CLIENT') or hasRole('ADMINISTRATOR')")
                .antMatchers("/testadmin", "/admin", "viewclients", "viewaccounts", "/transactionshistory", "/viewusers",
                        "/addclient", "/addaccount", "/adduser").access("hasRole('ADMINISTRATOR')")
                .antMatchers("/resources/**").permitAll()
                .anyRequest().authenticated()
                .and();

        http.formLogin()
                // указываем страницу с формой логина
                .loginPage("/login")
                        // указываем action с формы логина
                .loginProcessingUrl("/j_spring_security_check")
                        // указываем URL при неудачном логине
                .failureUrl("/loginfailed")
                        // Указываем параметры логина и пароля с формы логина
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                        // даем доступ к форме логина всем
                .permitAll();

        http.logout()
                // разрешаем делать логаут всем
                .permitAll()
                        // указываем URL логаута
                .logoutUrl("/logout")
                        // указываем URL при удачном логауте
                .logoutSuccessUrl("/afterlogout")
                        // делаем не валидной текущую сессию
                .invalidateHttpSession(true);

        //preventing from redirect to the previous page after login
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {

            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationException authException) throws IOException, ServletException {
                if (authException != null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().print("Unauthorizated....");
                }
            }
        });

    }

    // Указываем Spring контейнеру, что надо инициализировать <b></b>ShaPasswordEncoder
    // Это можно вынести в WebAppConfig либо сделать здесь
   /* @Bean
    public ShaPasswordEncoder getShaPasswordEncoder(){
        return new ShaPasswordEncoder();
    }*/
}