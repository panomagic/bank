package client.dao;

import beans.Client;
import beans.Gender;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper implements RowMapper<Client> {
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        Client client = new Client();
        client.setid(rs.getInt("id"));
        client.setFullName(rs.getString("fullName"));
        client.setGender(Gender.fromString(rs.getString("gender")));
        client.setDateOfBirth(rs.getDate("dateOfBirth"));
        client.setDateOfReg(rs.getDate("dateOfReg"));
        return client;
    }
}