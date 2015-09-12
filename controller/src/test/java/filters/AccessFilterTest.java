package filters;

import beans.Role;
import beans.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class AccessFilterTest extends Mockito {

    @Mock private HttpServletRequest httpRequest;
    @Mock private HttpServletResponse httpResponse;
    @Mock private FilterChain filterChain;
    @Mock private FilterConfig filterConfig;
    @Mock private HttpSession session;
    @Mock private User loggedUser;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    static User client = new User();
    static User admin = new User();

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"/", admin, true},
            {"/", client, true},
            {"/", null, true},
            {"/accessdenied", admin, true},
            {"/accessdenied", client, true},
            {"/accessdenied", null, true},
            {"/addaccount", admin, true},
            {"/addaccount", client, false},
            {"/addaccount", null, false},
            {"/addtransaction", admin, true},
            {"/addtransaction", client, true},
            {"/addtransaction", null, false},
            {"/admin", admin, true},
            {"/admin", client, false},
            {"/admin", null, false},
            {"/clientinfo", admin, true},
            {"/clientinfo", client, true},
            {"/clientinfo", null, false},
            {"/image", admin, true},
            {"/image", client, true},
            {"/image", null, false},
            {"/loginfailed", admin, true},
            {"/loginfailed", client, true},
            {"/loginfailed", null, true},
            {"/login", admin, true},
            {"/login", client, true},
            {"/login", null, true},
            {"/logout", admin, true},
            {"/logout", client, true},
            {"/logout", null, true},
            {"/transactionshistorybyclient", admin, true},
            {"/transactionshistorybyclient", client, true},
            {"/transactionshistorybyclient", null, false},
            {"/transactionshistory", admin, true},
            {"/transactionshistory", client, false},
            {"/transactionshistory", null, false},
            {"/transcurrencymismatch", admin, true},
            {"/transcurrencymismatch", client, true},
            {"/transcurrencymismatch", null, false},
            {"/transoverdraft", admin, true},
            {"/transoverdraft", client, true},
            {"/transoverdraft", null, false},
            {"/updatedeleteaccount", admin, true},
            {"/updatedeleteaccount", client, false},
            {"/updatedeleteaccount", null, false},
            {"/upload", admin, true},
            {"/upload", client, true},
            {"/upload", null, false},
        });
    }

    private String path;
    private User user;
    private boolean access;

    public AccessFilterTest(String path, User user, boolean access) {
        this.path = path;
        this.user = user;
        this.access = access;
    }

    @Test
    public void testDoFilterAll() throws Exception {
        AccessFilter accessFilter = new AccessFilter();

        when(httpRequest.getSession()).thenReturn(session);
        when(httpRequest.getRequestURI()).thenReturn(path);

        accessFilter.init(filterConfig);

        if ((user == admin) && access) {
            when(httpRequest.getSession().getAttribute("LOGGED_USER")).thenReturn(loggedUser);
            when(loggedUser.getRole()).thenReturn(Role.ADMINISTRATOR);
            accessFilter.doFilter(httpRequest, httpResponse, filterChain);
            verify(httpResponse, never()).sendRedirect("/accessdenied");
            verify(httpResponse, never()).sendRedirect("/loginfailed");
            verify(filterChain).doFilter(httpRequest, httpResponse);
        }
        if ((user == client) && access) {
            when(httpRequest.getSession().getAttribute("LOGGED_USER")).thenReturn(loggedUser);
            when(loggedUser.getRole()).thenReturn(Role.CLIENT);
            accessFilter.doFilter(httpRequest, httpResponse, filterChain);
            verify(httpResponse, never()).sendRedirect("/accessdenied");
            verify(httpResponse, never()).sendRedirect("/loginfailed");
            verify(filterChain).doFilter(httpRequest, httpResponse);
        }
        if ((user == client) && !access) {
            when(httpRequest.getSession().getAttribute("LOGGED_USER")).thenReturn(loggedUser);
            when(loggedUser.getRole()).thenReturn(Role.CLIENT);
            accessFilter.doFilter(httpRequest, httpResponse, filterChain);
            verify(httpResponse).sendRedirect("/accessdenied");
            verify(httpResponse, never()).sendRedirect("/loginfailed");
            verify(filterChain, never()).doFilter(httpRequest, httpResponse);
        }
        if ((user == null) && access) {
            accessFilter.doFilter(httpRequest, httpResponse, filterChain);
            verify(httpResponse, never()).sendRedirect("/accessdenied");
            verify(httpResponse, never()).sendRedirect("/loginfailed");
            verify(filterChain).doFilter(httpRequest, httpResponse);
        }
        if ((user == null) && !access) {
            accessFilter.doFilter(httpRequest, httpResponse, filterChain);
            verify(httpResponse, never()).sendRedirect("/accessdenied");
            verify(httpResponse).sendRedirect("/loginfailed");
            verify(filterChain, never()).doFilter(httpRequest, httpResponse);
        }
    }
}