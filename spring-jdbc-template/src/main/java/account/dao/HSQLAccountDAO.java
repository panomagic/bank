package account.dao;

import beans.Account;
import java.util.List;

public interface HSQLAccountDAO {
    void persistAccount(Account account);

    void updateAccount(Account account);

    void deleteAccount(Account account);

    Account getAccountByPK(Integer id);

    List<Account> getAllAccounts();
}