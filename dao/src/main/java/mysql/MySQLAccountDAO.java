package mysql;

import beans.*;
import daos.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MySQLAccountDAO extends AbstractJDBCDAO<Account, Integer> {

    private class PersistAccount extends Account {
        public void setid(int id) {
            super.setid(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM accounts";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO accounts (clients_clientID, " +
                "currencies_currencyID, accountTypes_accTypeID) VALUES(?,?,?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE accounts SET clients_clientID=?, " +
                "currencies_currencyID=?, accountTypes_accTypeID=? WHERE id=?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM accounts WHERE id=?";
    }

    public Account create() throws PersistException {
        Account account = new Account();
        return persist(account);
    }

    public Account getByPK(int key) throws SQLException {
        return null;
    }

    public MySQLAccountDAO(Connection connection) {
        super(connection);
    }

    @Override
    protected List<Account> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Account> result = new LinkedList<Account>();
        try {
            while (rs.next()) {
                PersistAccount account = new PersistAccount();
                account.setid(rs.getInt("id"));
                account.setClientID(rs.getInt("clients_clientID"));
                account.setCurrencyID(rs.getInt("currencies_currencyID"));
                account.setAccTypeID(rs.getInt("accountTypes_accTypeID"));
                account.setBalance(rs.getBigDecimal("balance"));
                result.add(account);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    public void prepareStatementForInsert(PreparedStatement statement, Account object) throws PersistException {
        try {
            statement.setInt(1, object.getClientID());
            statement.setInt(2, object.getCurrencyID());
            statement.setInt(3, object.getAccTypeID());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, Account object) throws PersistException {
        try {
            statement.setInt(1, object.getClientID());
            statement.setInt(2, object.getCurrencyID());
            statement.setInt(3, object.getAccTypeID());
            statement.setInt(4, object.getid());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void prepareStatementForDelete(PreparedStatement statement, Account object) throws PersistException {
        try {
            statement.setInt(1, object.getid());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}