package mysql;

import beans.EmailTemplate;
import beans.User;
import daos.AbstractJDBCDAO;
import daos.EmailTemplateDAO;
import daos.PersistException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

@Repository("emailTemplateDAO")
@Scope("prototype")
public class MySQLEmailTemplateDAOImpl extends AbstractJDBCDAO<EmailTemplate, Integer> implements EmailTemplateDAO {
    private static final Logger logger = Logger.getLogger(MySQLEmailTemplateDAOImpl.class);

    private class PersistEmailTemplate extends EmailTemplate {
        public void setid(int id) {
            super.setid(id);
        }
    }

    @Autowired
    public MySQLEmailTemplateDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM emailtemplates";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO emailtemplates (emailTemplateSubject, emailTemplateBody, isEnabled) VALUES(?,?,?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE emailtemplates SET emailTemplateSubject=?, emailTemplateBody=?, isEnabled=? WHERE id=?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM emailtemplates WHERE id=?";
    }

    public EmailTemplate create() throws PersistException {
        EmailTemplate emailTemplate = new EmailTemplate();
        return persist(emailTemplate);
    }

    public EmailTemplate getByPK(int key) throws SQLException {
        return null;
    }

    @Override
    protected List<EmailTemplate> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<EmailTemplate> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistEmailTemplate emailTemplate = new PersistEmailTemplate();
                emailTemplate.setid(rs.getInt("id"));
                emailTemplate.setEmailTemplateSubject(rs.getString("emailTemplateSubject"));
                emailTemplate.setEmailTemplateBody(rs.getString("emailTemplateBody"));
                emailTemplate.setIsEnabled(rs.getInt("isEnabled"));
                result.add(emailTemplate);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    public void prepareStatementForInsert(PreparedStatement statement, EmailTemplate object) throws PersistException {
        try {
            statement.setString(1, object.getEmailTemplateSubject());
            statement.setString(2, object.getEmailTemplateBody());
            statement.setInt(3, object.getIsEnabled());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, EmailTemplate object) throws PersistException {
        try {
            statement.setString(1, object.getEmailTemplateSubject());
            statement.setString(2, object.getEmailTemplateBody());
            statement.setInt(3, object.getIsEnabled());
            statement.setInt(4, object.getid());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void prepareStatementForDelete(PreparedStatement statement, EmailTemplate object) throws PersistException {
        try {
            statement.setInt(1, object.getid());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    public void generateAndSendEmail(EmailTemplate emailTemplate, User user) throws AddressException, MessagingException {
        logger.info("Sending email to user 1st step - Setup Mail Server Properties..");
        Properties mailConfig = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream("mailconfig.properties");
            mailConfig.load(inputStream);
        } catch (IOException e) {
            logger.error("Cannot load mail configuration properties file!", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("Cannot close InputStream!", e);
                }
            }
        }
        logger.info("Mail Server Properties have been setup successfully..");

        logger.info("Sending email to user 2nd step - Get Mail Session..");
        Session getMailSession = Session.getDefaultInstance(mailConfig, null);
        MimeMessage generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmailAddress()));
        generateMailMessage.setSubject(emailTemplate.getEmailTemplateSubject());

        generateMailMessage.setContent(emailTemplate.getEmailTemplateBody(), "text/html");
        logger.info("Mail Session has been created successfully..");

        logger.info("Sending email to user 3rd step - Get Session and Send mail");
        Transport transport = getMailSession.getTransport("smtp");

        transport.connect(mailConfig.getProperty("host"), mailConfig.getProperty("user"),
                mailConfig.getProperty("psw"));
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
        logger.info("Confirmation email has been sent successfully to " + user.getEmailAddress() );
    }
}