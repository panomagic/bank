package servlets;

import beans.EmailTemplate;
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

@WebServlet(name="addemailtemplate", urlPatterns={"/addemailtemplate"})
@Controller
public class AddEmailTemplateServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddEmailTemplateServlet.class);

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

        request.getRequestDispatcher("addemailtemplate.jsp").forward(request, response);
    }

    public void forwardTo(HttpServletRequest request, HttpServletResponse response, int forwardType) throws ServletException, IOException {
        if (forwardType == 1)
            request.getRequestDispatcher("addemailtemplateresult.jsp").forward(request, response);

        if (forwardType == 0)
            request.getRequestDispatcher("noenabledemailtemplates.jsp").forward(request, response);

        if (forwardType == 2)
            request.getRequestDispatcher("toomuchemailtemplates.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setEmailTemplateSubject(request.getParameter("subject"));
        emailTemplate.setEmailTemplateBody(request.getParameter("body"));
        emailTemplate.setIsEnabled(Integer.parseInt(request.getParameter("isEnabled")));

        int forwardType = emailTemplateService.addEmailTemplate(emailTemplate);
        logger.info("New emailtemplate was added successfully");

        forwardTo(request, response, forwardType);
    }
}