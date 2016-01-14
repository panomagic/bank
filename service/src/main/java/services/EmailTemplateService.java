package services;

import beans.EmailTemplate;
import beans.User;
import java.util.List;

public interface EmailTemplateService {
    int addEmailTemplate(EmailTemplate emailTemplate);
    EmailTemplate getEmailTemplateByID(Integer id);
    boolean updateEmailTemplate(EmailTemplate emailTemplate);
    boolean deleteEmailTemplate(EmailTemplate emailTemplate);
    List<EmailTemplate> getAllEmailTemplates();
    EmailTemplate getEnabledEmailTemplate();
    void sendEmailToUser(EmailTemplate emailTemplate, User user);
}