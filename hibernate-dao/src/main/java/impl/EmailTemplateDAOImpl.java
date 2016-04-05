package impl;

import daos.ClientDAO;
import daos.EmailTemplateDAO;
import entity.Client;
import entity.EmailTemplate;
import org.hibernate.Session;
import persistence.HibernateUtil;

import java.util.List;

public class EmailTemplateDAOImpl implements EmailTemplateDAO {
    @Override
    public EmailTemplate getEmailTemplateById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        EmailTemplate emailTemplate = (EmailTemplate) session.get(EmailTemplate.class, id);
        return emailTemplate;
    }

    @Override
    public List<EmailTemplate> getAllEmailTemplates() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<EmailTemplate> emailTemplateList = (List<EmailTemplate>) session.createCriteria(EmailTemplate.class).list();
        session.close();
        return emailTemplateList;
    }

    @Override
    public void addEmailTemplate(EmailTemplate emailTemplate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(emailTemplate);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateEmailTemplate(EmailTemplate emailTemplate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(emailTemplate);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteEmailTemplate(EmailTemplate emailTemplate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(emailTemplate);
        session.getTransaction().commit();
        session.close();
    }
}