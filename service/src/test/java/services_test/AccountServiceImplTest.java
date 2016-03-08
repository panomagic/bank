package services_test;

import beans.Account;
import daos.AccountDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import services.AccountService;
import services.AccountServiceImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-dao-module.xml", "classpath:test-app-context.xml"})
@Transactional
/*@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})*/
public class AccountServiceImplTest {

    @Mock
    private AccountDAO accountDAO;

    @InjectMocks
    private AccountService accountService = new AccountServiceImpl(); //непр - ?

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddAccount() throws Exception {
        Account account = new Account();
        account.setClientID(17);
        account.setCurrencyID(2);
        account.setAccTypeID(1);

        // specify mock behavior when method is called
        when(accountDAO.persist(account)).thenReturn(account);

        Account addedAccount = accountService.addAccount(account);
        assertEquals(addedAccount.getClientID(), account.getClientID());
        assertEquals(addedAccount.getCurrencyID(), account.getCurrencyID());
        assertEquals(addedAccount.getAccTypeID(), account.getAccTypeID());
        assertEquals(addedAccount.getBalance(), account.getBalance());
    }

    @Test
    public void testGetAccountByID() throws Exception {
        Account account = new Account();
        int accountID = 55;
        account.setid(accountID);
        account.setClientID(17);
        account.setCurrencyID(2);
        account.setAccTypeID(1);
        account.setBalance(new BigDecimal(100));

        // specify mock behavior when method is called
        when(accountDAO.getByPK(accountID)).thenReturn(account);

        Account retrievedAccount = accountService.getAccountByID(accountID);
        assertEquals(retrievedAccount.getClientID(), account.getClientID());
        assertEquals(retrievedAccount.getCurrencyID(), account.getCurrencyID());
        assertEquals(retrievedAccount.getAccTypeID(), account.getAccTypeID());
        assertEquals(retrievedAccount.getBalance(), account.getBalance());
    }

    @Test
    public void testUpdateAccount() throws Exception {
        Account account = new Account();
        account.setid(10);
        account.setClientID(17);
        account.setCurrencyID(2);
        account.setAccTypeID(1);
        account.setBalance(new BigDecimal(100));

        // specify mock behavior when method is called
        //when(accountDAO.update(account)).thenReturn(true);
        //when(accountDAO.update(account)).thenReturn(30);
        assertTrue(accountService.updateAccount(account));
        assertEquals(accountDAO.getByPK(10).getClientID(), account.getClientID());
    }

    @Test
    public void testDeleteAccount() throws Exception {
        Account account1 = new Account();
        account1.setid(55);
        account1.setClientID(17);
        account1.setCurrencyID(2);
        account1.setAccTypeID(1);
        account1.setBalance(new BigDecimal(100));

        Account account2 = new Account();
        account1.setid(56);
        account1.setClientID(18);
        account1.setCurrencyID(1);
        account1.setAccTypeID(2);
        account1.setBalance(new BigDecimal(200));

        List<Account> accountList = new ArrayList<>();
        accountList.add(account1);
        accountList.add(account2);

        // specify mock behavior when method is called
        //when(accountDAO.dgetAll()).thenReturn(accountList);
        doReturn(accountList).when(accountDAO).delete(account2);

        accountList.remove(1);

        List<Account> retrievedAccountList = accountService.getAllAccounts();
        assertEquals(retrievedAccountList, accountList);
    }

    @Test
    public void testGetAllAccounts() throws Exception {
        Account account1 = new Account();
        account1.setid(55);
        account1.setClientID(17);
        account1.setCurrencyID(2);
        account1.setAccTypeID(1);
        account1.setBalance(new BigDecimal(100));

        Account account2 = new Account();
        account1.setid(56);
        account1.setClientID(18);
        account1.setCurrencyID(1);
        account1.setAccTypeID(2);
        account1.setBalance(new BigDecimal(200));

        List<Account> accountList = new ArrayList<>();
        accountList.add(account1);
        accountList.add(account2);

        // specify mock behavior when method is called
        when(accountDAO.getAll()).thenReturn(accountList);

        List<Account> retrievedAccountList = accountService.getAllAccounts();
        assertEquals(retrievedAccountList, accountList);
    }
}