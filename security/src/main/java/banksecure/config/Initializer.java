package banksecure.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

//instead of <filter-name>springSecurityFilterChain</filter-name> in web.xml
public class Initializer extends AbstractSecurityWebApplicationInitializer {
    /*@Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        // регистрация конфигураций в Spring контексте
        ctx.register(SecurityConfig.class);
        //servletContext.addListener(new ContextLoaderListener(ctx));

        ctx.setServletContext(servletContext);
    }*/
}