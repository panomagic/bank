package banksecure.config;

import beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import services.UserService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        HttpSession session = httpServletRequest.getSession();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User loggedUser = userService.getUserByName(userDetails.getUsername());

        if (loggedUser != null)
            loggedUser.setPsw("");

        session.setAttribute("LOGGED_USER", loggedUser);

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        //redirect the user after successfull login to:
        httpServletResponse.sendRedirect("/");
    }
}