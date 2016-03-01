package com.groupb.artrec.models;

/**
 * Created by VSB on 23/02/2016.
 */
public class DisciplineListItem {
    private String subject;
    private boolean checked;

    public DisciplineListItem(String subject, boolean checked) {
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
