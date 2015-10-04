package services;

import beans.Account;
import java.util.List;

public interface AccountService {
    Account addAccount(Account account);
    Account getAccountByID(Integer id);
    boolean updateAccount(Account account);
    boolean deleteAccount(Account account);
    List<Account> getAllAccounts();
}