package services_test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.AccountService;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-dao-module.xml", "classpath:test-app-context.xml"})
public class AccountServiceImplTest {

    @Autowired
    private AccountService accountService;

    //@Test
    public void testAddAccount() throws Exception {
    }

    @Test
    public void testGetAccountByID() throws Exception {
        assertNotNull(accountService.getAccountByID(8));
    }

    //@Test
    public void testUpdateAccount() throws Exception {

    }

    //@Test
    public void testDeleteAccount() throws Exception {

    }

    //@Test
    public void testGetAllAccounts() throws Exception {
        assertNotNull(accountService.getAllAccounts());
    }
}