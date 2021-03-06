package daos;

import beans.EmailTemplate;
import beans.User;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * List of special methods for EmailTemplateDAO
 */
public interface EmailTemplateDAO extends GenericDAO<EmailTemplate, Integer>  {
    void generateAndSendEmail(EmailTemplate emailTemplate, User user) throws AddressException, MessagingException;
}