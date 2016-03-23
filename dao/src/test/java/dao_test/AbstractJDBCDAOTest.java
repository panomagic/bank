package dao_test;

import beans.Account;
import beans.Client;
import beans.Gender;
import daos.AccountDAO;
import daos.ClientDAO;
import daos.PersistException;
import mysql.MySQLAccountDAOImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-dao-app-context.xml"})
@TransactionConfiguration(transactionManager = "txManagerTest", defaultRollback = true)
//@TransactionConfiguration(transactionManager = "txManagerTest", defaultRollback = false)
//@Profile("test")
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
//@Transactional
//@Transactional(propagation= Propagation.REQUIRES_NEW)
//@TestExecutionListeners({TransactionalTestExecutionListener.class})
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class AbstractJDBCDAOTest extends NamedParameterJdbcDaoSupport {

    /*@Resource
    @Qualifier("dataSourceTest")
    DataSource dataSource;*/

    /*@Autowired
    public void setDataSource(@Qualifier("dataSourceTest") DataSource dataSource) {
        this.dataSource = dataSource;
    }*/
    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    AccountDAO accountDAO;

    /*@Autowired
    AccountDAO accountDAO2;*/

    @Autowired
    ClientDAO clientDAO;


    int addedAccBefore = 0;

    @Before
    //@BeforeTransaction
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void setUp() {
        addedAccBefore = insertAccount(7, 1 ,2);
    }

    NamedParameterJdbcTemplate jdbcTemplate;

    /*@Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }*/

   /* @Autowired
    public AbstractJDBCDAOTest(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
*/
   /* @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }*/

    //private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //@BeforeTransaction
    //@Rollback(false)
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    private int insertAccount(int clientID, int currencyID, int accTypeID) {
        Account account = new Account();
        account.setClientID(7);
        account.setCurrencyID(1);
        account.setAccTypeID(2);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sqlQuery = "INSERT INTO accounts (clients_clientID, currencies_currencyID, accountTypes_accTypeID) VALUES " +
                "(:clientID, :currencyID, :accTypeID) ";

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(account);

        getNamedParameterJdbcTemplate().update(sqlQuery, namedParameters, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Test
    //@Commit
    //@Rollback(true)
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    //@Transactional(propagation=Propagation.REQUIRED)
    public void testGetByPK() throws Exception {

        Account account = new Account();
        account.setClientID(7);
        account.setCurrencyID(1);
        account.setAccTypeID(2);

        Account addedAccount = null;

       /* try {
            addedAccount = accountDAO.persist(account);
        } catch (PersistException e) {
            e.printStackTrace();
        }*/

        //int addedAccId = insertAccount(7, 1, 2);

        //int addedAccId = addedAccount.getid();

        //System.out.println("addedAccId " + addedAccId);
        System.out.println("addedAccId " + addedAccBefore);

        //AccountDAO accountDAO2 = new MySQLAccountDAOImpl(dataSource);

        Thread.sleep(5000);
        System.out.println("sleeping 1.5 s");

        try {
            List<Account> accountList = accountDAO.getAll();
            System.out.println("Кол-во счетов " + accountList.size());
            addedAccount = accountDAO.getByPK(addedAccBefore);
            //addedAccount = accountDAO2.getByPK(addedAccId);

            System.out.println("addedAccount " + addedAccount);
            //System.out.println("addedAccountID " + addedAccount.getid());

            System.out.println("account " + account);

        } catch (Exception e) {
            fail("Account was not correctly retrieved because of " + e.getMessage());
        }


        assertEquals(account.getClientID(), addedAccount.getClientID());
        assertEquals(account.getCurrencyID(), addedAccount.getCurrencyID());
        assertEquals(account.getAccTypeID(), addedAccount.getAccTypeID());
        //assertEquals(account.getBalance(), addedAccount.getBalance());

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

        for (Account acc : accountList) {
            System.out.println(acc.getid());
        }
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