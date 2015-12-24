package banksecure.service;

import beans.User;

public interface UserSecureService {
    User getUser(String username);
}