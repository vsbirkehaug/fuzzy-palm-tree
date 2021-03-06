package artrec.com.artrec.models;

/**
 * Created by Vilde on 23.04.2016.
 */
public class SubjectListItem {
    private Subject subject;
    private boolean checked;

    public SubjectListItem(Subject subject, boolean checked) {
        this.subject = subject;
        this.checked = checked;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public boolean getState() {
        return checked;
    }

    public void setState(boolean checked) {
        this.checked = checked;
    }
}
