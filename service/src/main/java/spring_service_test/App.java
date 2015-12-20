package spring_service_test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import services.*;

@Controller
public class App {
    public static void main(String[] args) {
        /*ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "spring-service-module.xml");

        AccountServiceImpl accountService = (AccountServiceImpl) appContext.getBean("accountServiceImpl");

        UserServiceImpl userService = (UserServiceImpl) appContext.getBean("userServiceImpl");

        ClientServiceImpl clientService = (ClientServiceImpl) appContext.getBean("clientServiceImpl");

        CurrencyServiceImpl currencyService = (CurrencyServiceImpl) appContext.getBean("currencyServiceImpl");

        TransactionServiceImpl transactionService = (TransactionServiceImpl) appContext.getBean("transactionServiceImpl");

        System.out.println(accountService.getAccountByID(10).getClientID());

        System.out.println(userService.getUserByID(2).getClientID());

        System.out.println(clientService.getClientByID(5).getFullName());

        System.out.println(currencyService.getCurrencyByID(2).getCurrencyName());

        System.out.println(transactionService.getTransactionByID(70).getPayerID());*/

    }

}