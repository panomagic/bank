package servlets;

import beans.EmailTemplate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.EmailTemplateService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="updatedeleteemailtemplate", urlPatterns={"/updatedeleteemailtemplate"})
@Controller
public class UpdateDeleteEmailTemplateServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UpdateDeleteEmailTemplateServlet.class);

    @Autowired
    EmailTemplateService emailTemplateService;

    EmailTemplate emailTemplate;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forwardPage = "";

        request.setAttribute("allEmailTemplates", emailTemplateService.getAllEmailTemplates());

        if ("update".equals(request.getParameter("action"))) {
            forwardPage = "updateemailtemplate.jsp";
            emailTemplate = emailTemplateService.getEmailTemplateByID(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("emailTemplate", emailTemplate);
        } else if ("delete".equals(request.getParameter("action"))) {
            forwardPage = "deleteemailtemplate.jsp";
            emailTemplate = new EmailTemplate();
            emailTemplate.setid(Integer.parseInt(request.getParameter("id")));
            emailTemplateService.deleteEmailTemplate(emailTemplate);
            logger.info("Email template with id " + emailTemplate.getid() + " was deleted");
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

    public void updateEmailTemplate(HttpServletRequest request) {
        emailTemplate.setEmailTemplateSubject(request.getParameter("subject"));
        emailTemplate.setEmailTemplateBody(request.getParameter("body"));
        if (request.getParameter("isEnabled").equals("1"))
            emailTemplate.setIsEnabled(1);
        else
            emailTemplate.setIsEnabled(0);

        emailTemplateService.updateEmailTemplate(emailTemplate);
        logger.info("Email template with id " + emailTemplate.getid() + " was updated");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        updateEmailTemplate(request);

        request.getRequestDispatcher("updateemailtemplateresult.jsp").forward(request, response);
        return;
    }
}