package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharsetFilter implements Filter
{
    private String encoding;

    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("requestEncoding");

        if (encoding == null)
            encoding = "UTF-8";
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws IOException, ServletException
    {
        if (null == request.getCharacterEncoding()) // Respect the client-specified character encoding (HTTP specs section 3.4.1)
            request.setCharacterEncoding(encoding);

        /**
         * Set the default response content type and encoding
         */
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");


        next.doFilter(request, response);
    }

    public void destroy() {
        //this method gives the filter an opportunity to clean up any resources that are being held
    }
}