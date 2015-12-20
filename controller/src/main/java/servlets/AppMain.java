package servlets;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import services.AccountServiceImpl;

//@Controller
@Component
public class AppMain {
    public static void main(String[] args) {
        //ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-controller-module.xml");
        //AddAccountServlet accountServlet = (AddAccountServlet) applicationContext.getBean("addAccountServlet");
        //System.out.println(accountServlet.toString());
        //System.out.println(accountService.getAccountByID(10).getClientID());
        App app = new App();
        app.print();

    }
}