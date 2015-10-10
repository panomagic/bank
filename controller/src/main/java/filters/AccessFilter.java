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

    List<String> freeAccessUrls = new ArrayList<String>();
    List<String> clientAccessUrls = new ArrayList<String>();


    public void init(FilterConfig filterConfig) throws ServletException {
        freeAccessUrls.add("/");
        freeAccessUrls.add("/login");
        freeAccessUrls.add("/loginfailed");
        freeAccessUrls.add("/logout");
        freeAccessUrls.add("/accessdenied");
        freeAccessUrls.add("/css/bootstrap.min.css");
        freeAccessUrls.add("/css/bootstrap-theme.min.css");

        clientAccessUrls.add("/clientinfo");
        clientAccessUrls.add("/addtransaction");
        clientAccessUrls.add("/transactionshistorybyclient");
        clientAccessUrls.add("/transcurrencymismatch");
        clientAccessUrls.add("/transoverdraft");
        clientAccessUrls.add("/image");
        clientAccessUrls.add("/upload");
        clientAccessUrls.add("/js/bootstrap.min.js");
    }

    private static void redirectIfLoginFailed(User loggedUser, ServletResponse servletResponse) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if (loggedUser == null) {   //if auth failed redirect user to loginfailed page
            httpResponse.sendRedirect("/loginfailed");
        }
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
                httpResponse.sendRedirect("/accessdenied");
            }
        }

        redirectIfLoginFailed(loggedUser, servletResponse);

    }
    public void destroy() {
        //this method gives the filter an opportunity to clean up any resources that are being held
    }
}