package artrec.com.artrec.models;

/**
 * Created by Vilde on 23.04.2016.
 */
public class Subject {
    private int id;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Subject(int id, String title) {

        this.id = id;
        this.title = title;
    }
}
