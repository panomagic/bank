package entity;

import javax.persistence.*;

@Entity
@Table(name = "emailtemplates")
public class EmailTemplate {

    public EmailTemplate() {
        //no-args constructor
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "emailTemplateSubject")
    private String emailTemplateSubject;

    @Column(name = "emailTemplateBody")
    private String emailTemplateBody;

    @Column(name = "isEnabled")
    private int isEnabled;

    public Integer getid() {
        return id;
    }

    public void setid(Integer id) {
        this.id = id;
    }

    public String getEmailTemplateBody() {
        return emailTemplateBody;
    }

    public void setEmailTemplateBody(String emailTemplateBody) {
        this.emailTemplateBody = emailTemplateBody;
    }

    public String getEmailTemplateSubject() {
        return emailTemplateSubject;
    }

    public void setEmailTemplateSubject(String emailTemplateSubject) {
        this.emailTemplateSubject = emailTemplateSubject;
    }

    public int getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
    }
}