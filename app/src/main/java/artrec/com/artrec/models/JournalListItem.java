package artrec.com.artrec.models;

/**
 * Created by Vilde on 23.04.2016.
 */
public class JournalListItem {
    private Journal journal;
    private boolean checked;

    public JournalListItem(Journal journal, boolean checked) {
        this.journal = journal;
        this.checked = checked;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public boolean getState() {
        return checked;
    }

    public void setState(boolean checked) {
        this.checked = checked;
    }
}
