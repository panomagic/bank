package banksecure.service;

import beans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.UserService;

import java.util.List;


@Service
public class UserSecureServiceImpl implements UserSecureService {

    @Autowired
    UserService userService;

    @Override
    public User getUser(String username) {
        User user = new User();

        /*ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "spring-service-module.xml");
        UserServiceImpl userService = (UserServiceImpl) appContext.getBean("userServiceImpl");*/

        List<User> userList = userService.getAllUsers();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserName().equals(username))
                user = userList.get(i);
        }
        return user;
    }
}