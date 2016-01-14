package servlets;

import beans.EmailTemplate;
import beans.Role;
import beans.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.ClientService;
import services.EmailTemplateService;
import services.UserService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="adduser", urlPatterns={"/adduser"})
@Controller
public class AddUserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddUserServlet.class);

    @Autowired
    ClientService clientService;

    @Autowired
    UserService userService;

    @Autowired
    EmailTemplateService emailTemplateService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("allClients", clientService.getAllClients());

        request.getRequestDispatcher("adduser.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        User user = new User();
        user.setUserName(request.getParameter("userName"));
        user.setPsw(request.getParameter("psw"));
        user.setClientID(Integer.parseInt(request.getParameter("chooseclient")));
        user.setRole(Role.CLIENT);
        user.setEmailAddress(request.getParameter("email"));

        userService.addUser(user);
        logger.info("New user was added for client with id " + user.getClientID());

        EmailTemplate enabledEmailTemplate = emailTemplateService.getEnabledEmailTemplate();

        if (enabledEmailTemplate != null) {
            String templateBody = enabledEmailTemplate.getEmailTemplateBody();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(templateBody);

            if (templateBody.contains("%username%")) {
                stringBuilder.replace(stringBuilder.indexOf("%username%"),
                        stringBuilder.indexOf("%username%") + 10, user.getUserName());
            }
            if (templateBody.contains("%password%")) {
                stringBuilder.replace(stringBuilder.indexOf("%password%"),
                        stringBuilder.indexOf("%password%") + 10, user.getPsw());
            }
            enabledEmailTemplate.setEmailTemplateBody(stringBuilder.toString());

            emailTemplateService.sendEmailToUser(enabledEmailTemplate, user);

        } else {
            logger.debug("No email templates were enabled. Cannot send registration confirmation email!");
        }
        request.getRequestDispatcher("adduserresult.jsp").forward(request, response);
    }
}