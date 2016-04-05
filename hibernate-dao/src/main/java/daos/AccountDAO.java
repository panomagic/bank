package daos;

import entity.Account;

import java.util.List;

public interface AccountDAO {
    Account getAccountById(Integer id);
    List getAllAccounts();
    void addAccount(Account account);
    void updateAccount(Account account);
    void deleteAccount(Account account);

}
