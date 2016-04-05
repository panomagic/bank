package impl;

import daos.AccountDAO;
import entity.Account;
import org.hibernate.Session;
import persistence.HibernateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {
    @Override
    public Account getAccountById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Account account = (Account) session.get(Account.class, id);
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Account> accountList = (List<Account>) session.createCriteria(Account.class).list();
        session.close();
        return accountList;
    }

    @Override
    public void addAccount(Account account) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(account);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateAccount(Account account) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(account);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteAccount(Account account) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(account);
        session.getTransaction().commit();
        session.close();
    }
}