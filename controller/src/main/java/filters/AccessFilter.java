package filters;

import beans.Role;
import beans.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccessFilter implements Filter {

    private List<String> freeAccessUrls = new ArrayList<String>();
    private List<String> clientAccessUrls = new ArrayList<String>();


    public void init(FilterConfig filterConfig) throws ServletException {
        freeAccessUrls.add("/");
        freeAccessUrls.add("/login");
        freeAccessUrls.add("/loginfailed.jsp");
        freeAccessUrls.add("/logout");
        freeAccessUrls.add("/accessdenied.jsp");

        clientAccessUrls.add("/clientinfo");
        clientAccessUrls.add("/client.jsp");
        clientAccessUrls.add("/addtransaction");
        clientAccessUrls.add("/transcurrencymismatch.jsp");
        clientAccessUrls.add("/transactionshistorybyclient");
        //clientAccessUrls.add("/images/*");
        clientAccessUrls.add("/upload");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String path = httpRequest.getRequestURI();

        if (freeAccessUrls.contains(path)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        User loggedUser = (User) httpRequest.getSession().getAttribute("LOGGED_USER");

        if (loggedUser != null && Role.ADMINISTRATOR == loggedUser.getRole()) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (loggedUser != null && Role.CLIENT == loggedUser.getRole()) {
            if (clientAccessUrls.contains(path)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            else {
                httpResponse.sendRedirect("accessdenied.jsp");
            }
        }

        if (loggedUser == null) {   //if auth failed redirect user to loginfailed page
            httpResponse.sendRedirect("loginfailed.jsp");
        }

    }
    public void destroy() {

    }
}
