package filters;

import beans.Role;
import beans.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AccessFilterTest extends Mockito {

    @Mock private HttpServletRequest httpRequest;
    @Mock private HttpServletResponse httpResponse;
    @Mock private FilterChain filterChain;
    @Mock private HttpSession session;
    @Mock private User loggedUser;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoFilterForAdmin() throws Exception {
        AccessFilter accessFilter = new AccessFilter();

        String path = "/admin";

        when(httpRequest.getSession()).thenReturn(session);
        when(httpRequest.getRequestURI()).thenReturn(path);
        when(httpRequest.getSession().getAttribute("LOGGED_USER")).thenReturn(loggedUser);
        when(loggedUser.getRole()).thenReturn(Role.ADMINISTRATOR);

        accessFilter.doFilter(httpRequest, httpResponse, filterChain);

        verify(httpResponse, never()).sendRedirect("/accessdenied");
        verify(httpResponse, never()).sendRedirect("/loginfailed");
        verify(filterChain).doFilter(httpRequest, httpResponse);
    }

    @Test
    public void testDoFilterForClientAvailableUrl() throws Exception {
        AccessFilter accessFilter = new AccessFilter();

        String path = "/clientinfo";
        accessFilter.clientAccessUrls.add(path);

        when(httpRequest.getSession()).thenReturn(session);
        when(httpRequest.getRequestURI()).thenReturn(path);
        when(httpRequest.getSession().getAttribute("LOGGED_USER")).thenReturn(loggedUser);
        when(loggedUser.getRole()).thenReturn(Role.CLIENT);

        accessFilter.doFilter(httpRequest, httpResponse, filterChain);

        verify(httpResponse, never()).sendRedirect("/accessdenied");
        verify(httpResponse, never()).sendRedirect("/loginfailed");
        verify(filterChain).doFilter(httpRequest, httpResponse);
    }

    @Test
    public void testDoFilterForClientFreeUrl() throws Exception {
        AccessFilter accessFilter = new AccessFilter();

        String path = "/login";
        accessFilter.freeAccessUrls.add(path);

        when(httpRequest.getSession()).thenReturn(session);
        when(httpRequest.getRequestURI()).thenReturn(path);
        when(httpRequest.getSession().getAttribute("LOGGED_USER")).thenReturn(loggedUser);
        when(loggedUser.getRole()).thenReturn(Role.CLIENT);

        accessFilter.doFilter(httpRequest, httpResponse, filterChain);

        verify(httpResponse, never()).sendRedirect("/accessdenied");
        verify(httpResponse, never()).sendRedirect("/loginfailed");
        verify(filterChain).doFilter(httpRequest, httpResponse);
    }

    @Test
    public void testDoFilterForClientAccessDeniedUrl() throws Exception {
        AccessFilter accessFilter = new AccessFilter();

        String path = "/admin";
        accessFilter.clientAccessUrls.add("/clientinfo");

        when(httpRequest.getSession()).thenReturn(session);
        when(httpRequest.getRequestURI()).thenReturn(path);
        when(httpRequest.getSession().getAttribute("LOGGED_USER")).thenReturn(loggedUser);
        when(loggedUser.getRole()).thenReturn(Role.CLIENT);

        accessFilter.doFilter(httpRequest, httpResponse, filterChain);

        verify(httpResponse).sendRedirect("/accessdenied");
        verify(httpResponse, never()).sendRedirect("/loginfailed");
        verify(filterChain, never()).doFilter(httpRequest, httpResponse);
    }

    @Test
    public void testDoFilterIfLoginFailed() throws Exception {
        AccessFilter accessFilter = new AccessFilter();

        String path = "/clientinfo";

        when(httpRequest.getSession()).thenReturn(session);
        when(httpRequest.getRequestURI()).thenReturn(path);

        accessFilter.doFilter(httpRequest, httpResponse, filterChain);

        verify(httpResponse, never()).sendRedirect("/accessdenied");
        verify(httpResponse).sendRedirect("/loginfailed");
        verify(filterChain, never()).doFilter(httpRequest, httpResponse);
    }
}