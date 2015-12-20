package services;

import beans.Account;
import daos.PersistException;
import mysql.MySQLAccountDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("accountService")
@Scope("prototype")
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = Logger.getLogger(AccountServiceImpl.class);

    @Autowired
    MySQLAccountDAOImpl mySQLAccountDAO;

    @Autowired
    public AccountServiceImpl(MySQLAccountDAOImpl mySQLAccountDAO) {
        this.mySQLAccountDAO = mySQLAccountDAO;
    }

    public AccountServiceImpl() {
    }

    public void setMySQLAccountDAO(MySQLAccountDAOImpl mySQLAccountDAO) {
        this.mySQLAccountDAO = mySQLAccountDAO;
    }

    @Override
    public Account addAccount(Account account) {
        try {
            return mySQLAccountDAO.persist(account);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public Account getAccountByID(Integer id) {
        try {
            return mySQLAccountDAO.getByPK(id);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public boolean updateAccount(Account account) {
        try {
            mySQLAccountDAO.update(account);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public boolean deleteAccount(Account account) {
        try {
            mySQLAccountDAO.delete(account);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        try {
            return mySQLAccountDAO.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }
}