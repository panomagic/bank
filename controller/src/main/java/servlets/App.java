package servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import services.AccountServiceImpl;

//@Controller
@Component
public class App {

    @Autowired
    private AccountServiceImpl accountService;

    void print() {
        System.out.println(accountService);
        System.out.println(accountService.getAccountByID(10).getClientID());
    }


}
