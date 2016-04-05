package hibertest;

import entity.Account;
import org.hibernate.Session;
import persistence.HibernateUtil;

import java.math.BigDecimal;

public class AccountTest {

    public static void main(String[] args) {
        System.out.println("Hibernate + MySQL Test");
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        Account account = new Account();

        account.setClientID(3);
        account.setAccTypeID(1);
        account.setCurrencyID(2);
        account.setBalance(new BigDecimal(7));

        session.save(account);
        session.getTransaction().commit();


    }
}