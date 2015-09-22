package services;

import beans.Account;
import daos.GenericDAO;
import daos.PersistException;

import java.util.List;

public interface AccountService {
    Account addAccount(GenericDAO accountDAO, Account account) throws PersistException;
    Account getAccountByID(GenericDAO accountDAO, Integer id) throws PersistException;
    void updateAccount(GenericDAO accountDAO, Account account) throws PersistException;
    void deleteAccount(GenericDAO accountDAO, Account account) throws PersistException;
    List<Account> getAllAccounts(GenericDAO accountDAO) throws PersistException;
}
