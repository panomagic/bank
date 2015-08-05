package servlets;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class ForEachTag extends SimpleTagSupport {

    private Object[] items;
    private String attributeName;
    public void setItems(Object[] items) {
        this.items = items;
    }
    public void setVar(String attributeName) {
        this.attributeName = attributeName;
    }

    public void doTag() throws JspException, IOException {
        for(Object item: items) {
            getJspContext().setAttribute(attributeName, item);
            getJspBody().invoke(null);
        }
    }
}