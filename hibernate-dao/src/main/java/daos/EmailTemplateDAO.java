package daos;

import entity.EmailTemplate;
import java.util.List;

public interface EmailTemplateDAO {
    EmailTemplate getEmailTemplateById(Integer id);
    List getAllEmailTemplates();
    void addEmailTemplate(EmailTemplate emailTemplate);
    void updateEmailTemplate(EmailTemplate emailTemplate);
    void deleteEmailTemplate(EmailTemplate emailTemplate);
}