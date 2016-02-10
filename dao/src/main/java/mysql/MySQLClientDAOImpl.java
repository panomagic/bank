package mysql;

import beans.Client;
import beans.Gender;
import daos.AbstractJDBCDAO;
import daos.ClientDAO;
import daos.PersistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Transactional
public class MySQLClientDAOImpl extends AbstractJDBCDAO<Client, Integer> implements ClientDAO {

    private class PersistClient extends Client {
        public void setid(int id) {
            super.setid(id);
        }
    }

    @Autowired
    public MySQLClientDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM clients";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO clients (fullName, gender, dateOfBirth, dateOfReg) VALUES(?,?,?,?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE clients SET fullName=?, gender=?, dateOfBirth=?, dateOfReg=? WHERE id=?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM clients WHERE id=?";
    }

    public Client create() throws PersistException {
        Client client = new Client();
        return persist(client);
    }

    public Client getByPK(int key) throws SQLException {
        return null;
    }

    @Override
    protected List<Client> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Client> result = new LinkedList<Client>();
        try {
            while (rs.next()) {
                PersistClient client = new PersistClient();
                client.setid(rs.getInt("id"));
                client.setFullName(rs.getString("fullName"));
                client.setGender(Gender.fromString(rs.getString("gender")));
                client.setDateOfBirth(rs.getDate("dateOfBirth"));
                client.setDateOfReg(rs.getDate("dateOfReg"));
                result.add(client);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    public void prepareStatementForInsert(PreparedStatement statement, Client object) throws PersistException {
        try {
            statement.setString(1, object.getFullName());
            statement.setString(2, object.getGender().genderAsChar());
            statement.setDate(3, new Date(object.getDateOfBirth().getTime()));
            statement.setDate(4, new Date(object.getDateOfReg().getTime()));
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, Client object) throws PersistException {
        try {
            statement.setString(1, object.getFullName());
            statement.setString(2, object.getGender().genderAsChar());
            statement.setDate(3, new Date(object.getDateOfBirth().getTime()));
            statement.setDate(4, new Date(object.getDateOfReg().getTime()));
            statement.setInt(5, object.getid());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void prepareStatementForDelete(PreparedStatement statement, Client object) throws PersistException {
        try {
            statement.setInt(1, object.getid());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}