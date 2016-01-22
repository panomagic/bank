package services;

import beans.EmailTemplate;
import beans.User;
import daos.EmailTemplateDAO;
import daos.PersistException;
import mysql.MySQLEmailTemplateDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.List;

@Service("emailTemplateService")
@Scope("prototype")
@Transactional
public class EmailTemplateServiceImpl implements EmailTemplateService {
    private static final Logger logger = Logger.getLogger(EmailTemplateServiceImpl.class);

    @Autowired
    EmailTemplateDAO emailTemplateDAO;

    @Autowired
    public EmailTemplateServiceImpl(MySQLEmailTemplateDAOImpl mySQLEmailTemplateDAO) {
        this.emailTemplateDAO = mySQLEmailTemplateDAO;
    }

    public EmailTemplateServiceImpl() {
    }

    @Override
    public int addEmailTemplate(EmailTemplate emailTemplate) {
        try {
            List<EmailTemplate> emailTemplateList = emailTemplateDAO.getAll();
            int countEnabledTemplates = 0;
            for (int i = 0; i < emailTemplateList.size(); i++) {
                if (emailTemplateList.get(i).getIsEnabled() == 1)   //1 - TRUE, 0 - FALSE
                    countEnabledTemplates ++;
            }

            if (countEnabledTemplates == 0 && emailTemplate.getIsEnabled() == 0)
                return 0;

            if (countEnabledTemplates == 1 && emailTemplate.getIsEnabled() == 1)
                return 2;
            emailTemplateDAO.persist(emailTemplate);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
        return 1;
    }

    @Override
    public EmailTemplate getEmailTemplateByID(Integer id) {
        try {
            return emailTemplateDAO.getByPK(id);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public List<EmailTemplate> getAllEmailTemplates() {
        try {
            return emailTemplateDAO.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public boolean updateEmailTemplate(EmailTemplate emailTemplate) {
        try {
            emailTemplateDAO.update(emailTemplate);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public boolean deleteEmailTemplate(EmailTemplate emailTemplate) {
        try {
            emailTemplateDAO.delete(emailTemplate);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public EmailTemplate getEnabledEmailTemplate() {
        try {
            List<EmailTemplate> emailTemplateList = emailTemplateDAO.getAll();
            EmailTemplate enabledEmailTemplate = null;
            int countTempates = 0;
            for (int i = 0; i < emailTemplateList.size(); i++) {
                if (emailTemplateList.get(i).getIsEnabled() == 1) { //1 - TRUE, 0 - FALSE
                    countTempates++;
                    enabledEmailTemplate = emailTemplateList.get(i);
                }
            }
            if (countTempates == 0) {
                logger.debug("There is no enabled email templates. Confirmation emails WILL NOT be sent to users!");
                return null;
            } else if (countTempates == 1) {
                return enabledEmailTemplate;
            } else if (countTempates > 1) {
                logger.debug("More than 1 email templates were enabled. Confirmation emails will be sent " +
                        "using the last one of them!");
                return enabledEmailTemplate;
            }
            return enabledEmailTemplate;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    public void sendEmailToUser(EmailTemplate emailTemplate, User user) {
        try {
            emailTemplateDAO.generateAndSendEmail(emailTemplate, user);
        } catch (MessagingException e) {
            logger.error("Error during sending email", e);
        }
    }
}