package account.dao;

import beans.Account;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HSQLAccountDAOImpl extends JdbcDaoSupport implements HSQLAccountDAO {

    @Override
    public void persistAccount(Account account) {
        String sqlQuery = "INSERT INTO accounts (clients_clientID, " +
                "currencies_currencyID, accountTypes_accTypeID) VALUES(?,?,?)";
        getJdbcTemplate().update(sqlQuery, new Object[] {account.getClientID(),
                                account.getCurrencyID(), account.getAccTypeID()});
    }

    @Override
    public void updateAccount(Account account) {
        String sqlQuery = "UPDATE accounts SET clients_clientID=?, " +
                "currencies_currencyID=?, accountTypes_accTypeID=? WHERE id=?";
        getJdbcTemplate().update(sqlQuery, new Object[] {account.getClientID(),
                account.getCurrencyID(), account.getAccTypeID(), account.getid()});
    }

    @Override
    public void deleteAccount(Account account) {
        String sqlQuery = "DELETE FROM accounts WHERE id=?";
        getJdbcTemplate().update(sqlQuery, new Object[] {account.getid()});
    }

    @Override
    public Account getAccountByPK(final Integer id) {
        String sqlQuery = "SELECT * FROM accounts WHERE id=?";
        return getJdbcTemplate().queryForObject(sqlQuery, new ParameterizedRowMapper<Account>() {
            @Override
            public Account mapRow(ResultSet rs, int i) throws SQLException {
                Account account = new Account();
                account.setid(rs.getInt("id"));
                account.setClientID(rs.getInt("clients_clientID"));
                account.setAccTypeID(rs.getInt("accountTypes_accTypeID"));
                account.setCurrencyID(rs.getInt("currencies_currencyID"));
                account.setBalance(rs.getBigDecimal("balance"));
                return account;
            }
        },
        id
        );
    }

    @Override
    public List<Account> getAllAccounts() {
        String sqlQuery = "SELECT * from accounts";
        return getJdbcTemplate().query(sqlQuery, new AccountMapper());
    }
}