package artrec.com.artrec.models;

/**
 * Created by Vilde on 23.04.2016.
 */
public class SubjectListItem {
    private String subject;
    private boolean checked;

    public SubjectListItem(String subject, boolean checked) {
        this.subject = subject;
        this.checked = checked;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean getState() {
        return checked;
    }

    public void setState(boolean checked) {
        this.checked = checked;
    }
}
