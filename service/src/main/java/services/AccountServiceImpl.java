package services;

import beans.Account;
import daos.GenericDAO;
import daos.PersistException;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    @Override
    public Account addAccount(GenericDAO accountDAO, Account account) throws PersistException {
        return (Account) accountDAO.persist(account);
    }

    @Override
    public Account getAccountByID(GenericDAO accountDAO, Integer id) throws PersistException {
        return (Account) accountDAO.getByPK(id);
    }

    @Override
    public void updateAccount(GenericDAO accountDAO, Account account) throws PersistException {
        accountDAO.update(account);
    }

    @Override
    public void deleteAccount(GenericDAO accountDAO, Account account) throws PersistException {
        accountDAO.delete(account);
    }

    @Override
    public List<Account> getAllAccounts(GenericDAO accountDAO) throws PersistException {
        return accountDAO.getAll();
    }
}
