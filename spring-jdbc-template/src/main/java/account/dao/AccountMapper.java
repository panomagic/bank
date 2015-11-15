package account.dao;

import beans.Account;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setid(rs.getInt("id"));
        account.setClientID(rs.getInt("clients_clientID"));
        account.setCurrencyID(rs.getInt("currencies_currencyID"));
        account.setAccTypeID(rs.getInt("accountTypes_accTypeID"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
}