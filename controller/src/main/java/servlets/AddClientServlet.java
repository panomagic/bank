package servlets;

import beans.Client;
import beans.Gender;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import services.ClientServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class AddClientServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddClientServlet.class);

    @Autowired
    ClientServiceImpl clientService;
    
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("addclient.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        Client client = new Client();
        client.setFullName(request.getParameter("fullname"));
        client.setGender(Gender.fromString(request.getParameter("gender")));
        try {
            client.setDateOfBirth(new SimpleDateFormat("dd.MM.yyyy").parse(request.getParameter("dateofbirth")));
            client.setDateOfReg(new SimpleDateFormat("dd.MM.yyyy").parse(request.getParameter("dateofreg")));
        } catch (ParseException e) {
            logger.warn("Date parsing error", e);
        }

        //ApplicationContext appContext = new ClassPathXmlApplicationContext("spring-service-module.xml");
        //ClientServiceImpl clientService = (ClientServiceImpl) appContext.getBean("clientServiceImpl");

        clientService.addClient(client);
        logger.info("New client was added successfully");

        request.getRequestDispatcher("addclientresult.jsp").forward(request, response);
    }
}