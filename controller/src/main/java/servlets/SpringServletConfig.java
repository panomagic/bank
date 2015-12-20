package servlets;

import banksecure.config.SecurityConfig;
import org.springframework.context.annotation.*;


@Configuration
@ComponentScan({"mysql", "services", "servlets"}) // search the package(s) for @Component classes
@ImportResource( {  //"classpath:spring-service-module.xml",
                    "classpath:spring-dao-module.xml",
                    //"classpath:abstractjdbcdao.xml",
                    //"classpath:mysql-dao-factory.xml",
                    //"classpath:spring-datasource.xml"
    } )
//@Import({ SecurityConfig.class, SpringServiceConfig.class })
@Import({ SecurityConfig.class })
public class SpringServletConfig {

}