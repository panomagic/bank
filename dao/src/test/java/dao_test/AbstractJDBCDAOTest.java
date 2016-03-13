package dao_test;

import beans.Account;
import beans.Client;
import beans.Gender;
import daos.AccountDAO;
import daos.ClientDAO;
import daos.PersistException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-dao-app-context.xml"})
@TransactionConfiguration(transactionManager = "txManagerTest", defaultRollback = true)
@Transactional
//@TestExecutionListeners({TransactionalTestExecutionListener.class})
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
//@Profile("test")
public class AbstractJDBCDAOTest {

    /*@Resource
    @Qualifier("dataSourceTest")
    DataSource dataSource;

    @Autowired
    public void setDataSource(@Qualifier("dataSourceTest") DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    ClientDAO clientDAO;

    /*private int insertAccount() {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        return keyHolder.getKey().intValue();
    }*/

    /*@Before
    public void setUp() {

    }*/



    @Test
    public void testGetByPK() throws Exception {

        Account account = new Account();
        account.setClientID(7);
        account.setCurrencyID(1);
        account.setAccTypeID(2);

        Account addedAccount = null;

        try {
            addedAccount = accountDAO.persist(account);
        } catch (PersistException e) {
            e.printStackTrace();
        }

        try {
            account = accountDAO.getByPK(addedAccount.getid());
        } catch (Exception e) {
            fail("Account was not correctly retrieved because of " + e.getMessage());
        }

        assertEquals(account.getClientID(), addedAccount.getClientID());
        assertEquals(account.getCurrencyID(), addedAccount.getCurrencyID());
        assertEquals(account.getAccTypeID(), addedAccount.getAccTypeID());
        assertEquals(account.getBalance(), addedAccount.getBalance());

        /*assertEquals(account.getClientID(), 31);
        assertEquals(account.getCurrencyID(), 2);
        assertEquals(account.getAccTypeID(), 1);
        assertEquals(account.getBalance(), new BigDecimal(6.00).setScale(2, RoundingMode.HALF_UP));*/

        Client client = null;
        try {
            client = clientDAO.getByPK(3);
        } catch (Exception e) {
            fail("Client was not correctly retrieved because of " + e.getMessage());
        }
        assertEquals(client.getFullName(), "Ivanov Ivan");
        assertEquals(client.getGender(), Gender.MALE);
        assertEquals(client.getDateOfBirth(), new SimpleDateFormat("yyyy-MM-dd").parse("1980-05-14"));
        assertEquals(client.getDateOfReg(), new SimpleDateFormat("yyyy-MM-dd").parse("2014-01-05"));
    }

    @Test
    public void testGetAll() throws Exception {
        List<Account> accountList = null;
        try {
            accountList = accountDAO.getAll();
        } catch (PersistException e) {
            fail("Account list was not correctly retrieved because of " + e.getMessage());
        }
        assertNotNull(accountList);
    }

    @Test
    public void testPersist() throws Exception {
        Account account = new Account();
        int initialNumberOfAccounts = accountDAO.getAll().size();
        account.setClientID(7);
        account.setCurrencyID(1);
        account.setAccTypeID(2);
        Account addedAccount = null;
        try {
            addedAccount = accountDAO.persist(account);
        } catch (Exception e) {
            if (initialNumberOfAccounts == accountDAO.getAll().size())
                fail("Account was not correctly added because of " + e.getMessage() +
                        ". The transaction was rolled back successfully");
            else fail("Check data consistency! Account was not correctly added because of " + e.getMessage());
        }
        assertEquals(account.getClientID(), addedAccount.getClientID());
    }

    @Test
    public void testUpdate() throws Exception {
        Account account = accountDAO.getByPK(10);
        account.setClientID(7);
        account.setCurrencyID(1);
        account.setAccTypeID(2);
        try {
            accountDAO.update(account);
        } catch (Exception e) {
            if (accountDAO.getByPK(10).getClientID() == account.getClientID())
                fail("Account was not correctly updated because of " + e.getMessage() +
                        ". The transaction was rolled back successfully");
            else fail("Check data consistency! Account was not correctly updated because of " + e.getMessage());
        }
    }

    @Test
    public void testDelete() throws Exception {
        Account account = accountDAO.getByPK(10);
        try {
            accountDAO.delete(account);
        } catch (Exception e) {
            if (accountDAO.getByPK(10).getid() == account.getid())
                fail("Account was not correctly deleted because of " + e.getMessage() +
                ". The transaction was rolled back successfully");
            else fail("Check data consistency! Account was not correctly deleted because of " + e.getMessage());
        }
    }
}