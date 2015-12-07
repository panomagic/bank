package banksecure.service;

import beans.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import services.UserServiceImpl;
import java.util.List;

@Service
public class UserSecureServiceImpl implements UserService {

    @Override
    public User getUser(String username) {
        User user = new User();

        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "spring-service-module.xml");
        UserServiceImpl userService = (UserServiceImpl) appContext.getBean("userServiceImpl");

        List<User> userList = userService.getAllUsers();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserName().equals(username))
                user = userList.get(i);
        }
        return user;
    }
}