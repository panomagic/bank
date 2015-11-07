package banksecure.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Bean
    public UserDetailsService getUserDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // с помощью нашего сервиса UserService получаем UserSecure
        UserSecureServiceImpl userSecureServiceImpl = new UserSecureServiceImpl();
        beans.User user = userSecureServiceImpl.getUser(username);
        // указываем роли для этого пользователя
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        // на основании полученных даных формируем объект UserDetails
        // который позволит проверить введеный пользователем логин и пароль
        // и уже потом аутентифицировать пользователя
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPsw(), Arrays.asList(authority));

        if (user == null)
            throw new UsernameNotFoundException("service.UserSecure not found");

        return userDetails;
    }
}