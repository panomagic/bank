package servlets;

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

@WebServlet(name="viewemailtemplates", urlPatterns={"/viewemailtemplates"})
@Controller
public class ViewEmailTemplates extends HttpServlet {

    @Autowired
    EmailTemplateService emailTemplateService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("allEmailTemplates", emailTemplateService.getAllEmailTemplates());

        request.getRequestDispatcher("viewemailtemplates.jsp").forward(request, response);
    }
}