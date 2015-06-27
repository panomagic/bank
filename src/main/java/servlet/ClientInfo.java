package servlet;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ClientInfo extends TagSupport {
    private int number;

    public void setNumber(int number) {
        this.number = number;
    }

    public int doStartTag() throws JspException {
        JspWriter out=pageContext.getOut();
        try{
            out.print(number*number*number);
        }catch(Exception e){e.printStackTrace();}

        return SKIP_BODY;
    }

}
