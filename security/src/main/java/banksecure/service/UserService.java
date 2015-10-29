package banksecure.service;

import beans.User;

public interface UserService {
    User getUser(String username);
}