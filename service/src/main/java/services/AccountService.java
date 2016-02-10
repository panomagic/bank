package services;

import beans.Account;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public interface AccountService {
    Account addAccount(Account account);
    Account getAccountByID(Integer id);
    boolean updateAccount(Account account);
    boolean deleteAccount(Account account);
    List<Account> getAllAccounts();
}