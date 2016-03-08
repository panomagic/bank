package dao_test;

import beans.Account;
import daos.AccountDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-dao-module.xml", "classpath:test-dao-app-context.xml"})
//@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
//@TestExecutionListeners({TransactionalTestExecutionListener.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class AbstractJDBCDAOTest {

    @Autowired
    AccountDAO accountDAO;

    @Test
    public void testGetByPK() throws Exception {
        Account account = accountDAO.getByPK(17);
        assertEquals(account.getClientID(), 31);
        assertEquals(account.getCurrencyID(), 2);
        assertEquals(account.getAccTypeID(), 1);
        //assertEquals(account.getBalance(), new BigDecimal(6.00));
    }

    @Test
    public void testGetAll() throws Exception {
        List<Account> accountList = accountDAO.getAll();
        assertNotNull(accountList);
    }

    @Test
    public void testPersist() throws Exception {
        Account account = new Account();
        account.setClientID(7);
        account.setCurrencyID(1);
        account.setAccTypeID(2);
        Account addedAccount = accountDAO.persist(account);
        assertEquals(account.getClientID(), addedAccount.getClientID());
    }

    //@Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {
        Account account = accountDAO.getByPK(10);
        try {
            accountDAO.delete(account);
        } catch (Exception e) {
            fail("Account was not correctly deleted because of " + e.getMessage());
        }
    }
}