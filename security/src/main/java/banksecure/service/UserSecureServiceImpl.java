package banksecure.service;

import beans.*;
import org.springframework.stereotype.Service;
import services.UserServiceImpl;
import java.util.List;

@Service
public class UserSecureServiceImpl implements UserService {

    @Override
    public User getUser(String username) {
        User user = new User();

        UserServiceImpl userService = new services.UserServiceImpl();
        List<User> userList = userService.getAllUsers();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserName().equals(username))
                user = userList.get(i);
        }
        return user;
    }
}