package services;

import beans.Account;
import daos.AccountDAO;
import daos.PersistException;
import mysql.MySQLAccountDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("accountService")
@Scope("prototype")
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = Logger.getLogger(AccountServiceImpl.class);

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    public AccountServiceImpl(MySQLAccountDAOImpl mySQLAccountDAO) {
        this.accountDAO = mySQLAccountDAO;
    }

    public AccountServiceImpl() {
    }

    public void setMySQLAccountDAO(MySQLAccountDAOImpl mySQLAccountDAO) {
        this.accountDAO = mySQLAccountDAO;
    }

    @Override
    public Account addAccount(Account account) {
        try {
            return accountDAO.persist(account);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public Account getAccountByID(Integer id) {
        try {
            return accountDAO.getByPK(id);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public boolean updateAccount(Account account) {
        try {
            accountDAO.update(account);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public boolean deleteAccount(Account account) {
        try {
            accountDAO.delete(account);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        try {
            return accountDAO.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }
}