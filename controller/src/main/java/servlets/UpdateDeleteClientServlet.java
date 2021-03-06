package servlets;

import beans.Client;
import beans.Gender;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.ClientService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class UpdateDeleteClientServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UpdateDeleteClientServlet.class);

    @Autowired
    ClientService clientService;

    Client client;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forwardPage = "";

        if ("update".equals(request.getParameter("action"))) {
            forwardPage = "updateclient.jsp";
            client = clientService.getClientByID(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("client", client);
        } else if ("delete".equals(request.getParameter("action"))) {
            forwardPage = "deleteclient.jsp";
            client = new Client();
            client.setid(Integer.parseInt(request.getParameter("id")));
            clientService.deleteClient(client);
            logger.info("Client with id " + client.getid() + " was deleted");
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

    public void updateClient(HttpServletRequest request) {
        client.setFullName(request.getParameter("fullname"));
        client.setGender(Gender.fromString(request.getParameter("gender")));
        try {
            client.setDateOfBirth(new SimpleDateFormat("dd.MM.yyyy").parse(request.getParameter("dateofbirth")));
            client.setDateOfReg(new SimpleDateFormat("dd.MM.yyyy").parse(request.getParameter("dateofreg")));
        } catch (ParseException e) {
            logger.warn("Date parsing error", e);
        }

        clientService.updateClient(client);
        logger.info("Client with id " + client.getid() + " was updated");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        updateClient(request);

        request.getRequestDispatcher("updateclientresult.jsp").forward(request, response);
        return;
    }
}