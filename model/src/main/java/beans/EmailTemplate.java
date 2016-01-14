package beans;


public class EmailTemplate implements Identified<Integer> {

    public EmailTemplate() {
        //no-args constructor
    }

    private Integer id;
    private String emailTemplateSubject;
    private String emailTemplateBody;
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