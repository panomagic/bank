package daos;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;

@Repository("abstractDAO")
@Scope("prototype")
public abstract class AbstractDAO extends AbstractJDBCDAO implements AccountDAO, ClientDAO,
    CurrencyDAO, TransactionDAO, UserDAO {
    public AbstractDAO(DataSource dataSource) {
        super(dataSource);
    }
}
