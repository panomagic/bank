package client.dao;

import beans.Client;
import beans.Gender;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HSQLClientDAOImpl extends JdbcDaoSupport implements HSQLClientDAO {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public HSQLClientDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void persistClient(final Client client) {
        String sqlQuery = "INSERT INTO clients (fullName, gender, dateOfBirth, dateOfReg) VALUES(?,?,?,?)";
        getJdbcTemplate().update(sqlQuery, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, client.getFullName());
                ps.setString(2, client.getGender().genderAsChar());
                ps.setDate(3, new Date(client.getDateOfBirth().getTime()));
                ps.setDate(4, new Date(client.getDateOfReg().getTime()));

            }
        });
    }

    @Override
    public Client getClientByPK(Integer id) {
        String sqlQuery = "SELECT * FROM clients WHERE id=?";
        return getJdbcTemplate().queryForObject(sqlQuery, new ParameterizedRowMapper<Client>() {
            @Override
            public Client mapRow(ResultSet rs, int i) throws SQLException {
                Client client = new Client();
                client.setid(rs.getInt("id"));
                client.setFullName(rs.getString("fullName"));
                client.setGender(Gender.fromString(rs.getString("gender")));
                client.setDateOfBirth(rs.getDate("dateOfBirth"));
                client.setDateOfReg(rs.getDate("dateOfReg"));
                return client;
            }
        },
        id
        );
    }

    @Override
    public void updateClient(Client client) {
        String sqlQuery = "UPDATE clients SET fullName = :fullName, gender = :gender, dateOfBirth = :dateOfBirth, " +
                "dateOfReg = :dateOfReg WHERE id = :id";
        Map namedParameters = new HashMap();
        namedParameters.put("fullName", client.getFullName());
        namedParameters.put("gender", client.getGender().genderAsChar());
        namedParameters.put("dateOfBirth", client.getDateOfBirth());
        namedParameters.put("dateOfReg", client.getDateOfReg());
        namedParameters.put("id", client.getid());

        namedParameterJdbcTemplate.update(sqlQuery, namedParameters);
    }

    @Override
    public void deleteClient(Client client) {
        String sqlQuery = "DELETE FROM clients WHERE id=?";
        getJdbcTemplate().update(sqlQuery, new Object[] {client.getid()});
    }

    @Override
    public List<Client> getAllClients() {
        String sqlQuery = "SELECT * from clients";
        return getJdbcTemplate().query(sqlQuery, new ClientMapper());
    }
}