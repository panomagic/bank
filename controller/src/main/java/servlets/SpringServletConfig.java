package servlets;

import banksecure.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@ComponentScan({"mysql", "services", "servlets"}) // search the package(s) for @Component classes
@ImportResource({"classpath:spring-dao-module.xml"} )
@Import({SecurityConfig.class})
@EnableTransactionManagement
public class SpringServletConfig {
    @Autowired
    DataSource dataSource;

    @Bean(name="txManager")
    public DataSourceTransactionManager txManager() throws IOException {
        DataSourceTransactionManager txManager= new DataSourceTransactionManager();
        txManager.setDataSource(dataSource);
        return txManager;
    }
}