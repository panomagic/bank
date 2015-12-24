package mysql;


import beans.Currency;
import daos.AbstractJDBCDAO;
import daos.CurrencyDAO;
import daos.PersistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

//@Repository("mySQLCurrencyDAO")
//@Scope("prototype")
public class MySQLCurrencyDAOImpl extends AbstractJDBCDAO<Currency, Integer> implements CurrencyDAO {

    private class PersistCurrency extends Currency {
        public void setid(int id) {
            super.setid(id);
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //@Autowired
    public MySQLCurrencyDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM currencies";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO currencies (currency) VALUES(?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE currencies SET currency=? WHERE id=?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM currencies WHERE id=?";
    }

    public Currency create() throws PersistException {
        Currency currency = new Currency();
        return persist(currency);
    }

    public Currency getByPK(int key) throws SQLException {
        return null;
    }

    @Override
    protected List<Currency> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Currency> result = new LinkedList<Currency>();
        try {
            while (rs.next()) {
                PersistCurrency currency = new PersistCurrency();
                currency.setid(rs.getInt("id"));
                currency.setCurrencyName(rs.getString("currency"));
                result.add(currency);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    public void prepareStatementForInsert(PreparedStatement statement, Currency object) throws PersistException {
        try {
            statement.setString(1, object.getCurrencyName());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, Currency object) throws PersistException {
        try {
            statement.setString(1, object.getCurrencyName());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void prepareStatementForDelete(PreparedStatement statement, Currency object) throws PersistException {
        try {
            statement.setInt(1, object.getid());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}