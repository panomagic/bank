package servlet;

import beans.Role;
import beans.User;
import filters.AccessFilter;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AccessFilterTest extends Mockito {

    /*@Mock
    AccessFilter accessFilterMock;

    @InjectMocks
    AccessFilter accessFilter;*/

    /*@Test
    public void testInit() throws Exception {
        FilterConfig filterConfig = mock(FilterConfig.class);

        AccessFilter accessFilter3 = mock(AccessFilter.class);

        //accessFilterMock = new AccessFilter();

        List<String> freeAccessUrls = new ArrayList<String>();
        freeAccessUrls.add("/");
        freeAccessUrls.add("/login");
        freeAccessUrls.add("/loginfailed.jsp");
        freeAccessUrls.add("/logout");
        freeAccessUrls.add("/accessdenied.jsp");

        List<String> clientAccessUrls = new ArrayList<String>();
        //doNothing().when(accessFilter).init(filterConfig);

        //when(accessFilter.init(filterConfig)).thenReturn(freeAccessUrls);

        verify(accessFilter3).init(filterConfig);

        freeAccessUrls.add("/");

        //verify()

    }

    @Test
    public void testRedirectIfLoginFailed() {
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);
        User user = mock(User.class);
        AccessFilter accessFilter3 = mock(AccessFilter.class);
        //verify(accessFilter3).
    }
*/
    @Test
    public void testDoFilterIfAdmin() throws Exception {
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);

        String path = httpRequest.getRequestURI();

        User loggedUser = new User();
        loggedUser.setRole(Role.ADMINISTRATOR);

        when(httpRequest.getSession()).thenReturn(session);

        httpRequest.getSession().setAttribute("LOGGED_USER", loggedUser);

        when(httpRequest.getSession().getAttribute("LOGGED_USER")).thenReturn(loggedUser);

        List freeAccessUrls = mock(List.class);
        List clientAccessUrls = mock(List.class);

        AccessFilter accessFilter = new AccessFilter();

        when(freeAccessUrls.contains(path)).thenReturn(false);

        when(clientAccessUrls.contains(path)).thenReturn(false);

        accessFilter.doFilter(httpRequest, httpResponse, filterChain);

        verify(httpResponse, never()).sendRedirect("accessdenied.jsp");

        verify(httpResponse, never()).sendRedirect("loginfailed.jsp");

        verify(filterChain).doFilter(httpRequest, httpResponse);
    }


    @Test
    public void testDoFilterIfClient() throws Exception {
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);

        User loggedUser = mock(User.class);
        //when(loggedUser.equals(null)).thenReturn(false);
        when(loggedUser.getRole()).thenReturn(Role.CLIENT);
        //loggedUser.setRole(Role.CLIENT);

        when(httpRequest.getSession()).thenReturn(session);

        httpRequest.getSession().setAttribute("LOGGED_USER", loggedUser);

        when(httpRequest.getSession().getAttribute("LOGGED_USER")).thenReturn(loggedUser);

        List freeAccessUrls = mock(List.class);
        List clientAccessUrls = mock(List.class);

        AccessFilter accessFilter = new AccessFilter();

        String path = httpRequest.getRequestURI();

        accessFilter.doFilter(httpRequest, httpResponse, filterChain);

        when(freeAccessUrls.contains(path)).thenReturn(true);

        when(clientAccessUrls.contains(path)).thenReturn(true);


        verify(httpResponse, never()).sendRedirect("accessdenied.jsp");

        verify(filterChain).doFilter(httpRequest, httpResponse);

        //System.out.println(loggedUser = null);

    }

    @Test
    public void testDoFilterIfLoginFailed() throws Exception {
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);

        String path = httpRequest.getRequestURI();

        User loggedUser = null;

        when(httpRequest.getSession()).thenReturn(session);

        httpRequest.getSession().setAttribute("LOGGED_USER", loggedUser);

        when(httpRequest.getSession().getAttribute("LOGGED_USER")).thenReturn(loggedUser);

        List freeAccessUrls = mock(List.class);
        List clientAccessUrls = mock(List.class);

        AccessFilter accessFilter = new AccessFilter();

        when(freeAccessUrls.contains(path)).thenReturn(false);

        when(clientAccessUrls.contains(path)).thenReturn(false);

        when(httpRequest.getRequestURI()).thenReturn("/login");

        accessFilter.doFilter(httpRequest, httpResponse, filterChain);

        verify(httpResponse).sendRedirect("loginfailed.jsp");

        verify(filterChain, never()).doFilter(httpRequest, httpResponse);
    }
}