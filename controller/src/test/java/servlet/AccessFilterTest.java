package servlet;

import beans.Role;
import beans.User;
import filters.AccessFilter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.hamcrest.*;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class AccessFilterTest extends Mockito {

    @Mock private HttpServletRequest httpRequest;
    @Mock private HttpServletResponse httpResponse;
    @Mock private FilterChain filterChain;
    @Mock private HttpSession session;



    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoFilterForAdmin() throws Exception {
        AccessFilter accessFilter = new AccessFilter();

        String path = httpRequest.getRequestURI();

        User loggedUser = spy(User.class);
        loggedUser.setRole(Role.ADMINISTRATOR);

        List freeAccessUrls = spy(List.class);
        List clientAccessUrls = spy(List.class);
        clientAccessUrls.add(path);

        when(httpRequest.getSession()).thenReturn(session);

        httpRequest.getSession().setAttribute("LOGGED_USER", loggedUser);

        when(httpRequest.getSession().getAttribute("LOGGED_USER")).thenReturn(loggedUser);

        when(freeAccessUrls.contains(path)).thenReturn(false);

        when(clientAccessUrls.contains(path)).thenReturn(false);

        accessFilter.doFilter(httpRequest, httpResponse, filterChain);

        verify(httpResponse, never()).sendRedirect("accessdenied.jsp");

        verify(httpResponse, never()).sendRedirect("loginfailed.jsp");

        verify(filterChain).doFilter(httpRequest, httpResponse);
    }

    private ArgumentMatcher isValid() {
        return new ArgumentMatcher() {

            @Override
            public boolean matches(Object element) {
                return element.getClass().equals(String.class);
            }
        };
    }

    @Test
    public void testDoFilterForClient() throws Exception {
        AccessFilter accessFilter = new AccessFilter();

        String path = "/login";

        User loggedUser = spy(User.class);
        loggedUser.setRole(Role.CLIENT);

        List freeAccessUrls = spy(List.class);
        List clientAccessUrls = spy(List.class);
        clientAccessUrls.add(path);

        when(httpRequest.getSession()).thenReturn(session);

        httpRequest.getSession().setAttribute("LOGGED_USER", loggedUser);

        when(httpRequest.getSession().getAttribute("LOGGED_USER")).thenReturn(loggedUser);

        when(loggedUser.getRole()).thenReturn(Role.CLIENT);

        when(freeAccessUrls.contains(path)).thenReturn(true);

        //when(clientAccessUrls.contains(path)).thenReturn(true);

        accessFilter.doFilter(httpRequest, httpResponse, filterChain);


        when(clientAccessUrls.contains(argThat(isValid()))).thenReturn(true);

        verify(httpResponse, never()).sendRedirect("accessdenied.jsp");

        verify(httpResponse, never()).sendRedirect("loginfailed.jsp");

        verify(filterChain).doFilter(httpRequest, httpResponse);
    }

    @Test
    public void testDoFilterIfLoginFailed() throws Exception {

        AccessFilter accessFilter = new AccessFilter();

        String path = httpRequest.getRequestURI();

        User loggedUser = null;

        List freeAccessUrls = spy(List.class);
        List clientAccessUrls = spy(List.class);
        clientAccessUrls.add(path);

        when(httpRequest.getSession()).thenReturn(session);

        httpRequest.getSession().setAttribute("LOGGED_USER", loggedUser);

        when(httpRequest.getSession().getAttribute("LOGGED_USER")).thenReturn(null);


        when(freeAccessUrls.contains(path)).thenReturn(false);

        when(clientAccessUrls.contains(path)).thenReturn(false);

        when(httpRequest.getRequestURI()).thenReturn("/login");

        accessFilter.doFilter(httpRequest, httpResponse, filterChain);

        verify(httpResponse).sendRedirect("loginfailed.jsp");

        verify(filterChain, never()).doFilter(httpRequest, httpResponse);
    }
}