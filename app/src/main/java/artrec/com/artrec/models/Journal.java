package artrec.com.artrec.models;

/**
 * Created by Vilde on 23.04.2016.
 */
public class Journal {

    public Journal(int id, String issn, String title, String url, String publisher, String rights) {
        this.id = id;
        this.issn = issn;
        this.title = title;
        this.url = url;
        this.publisher = publisher;
        this.rights = rights;
    }

    public Journal(int id, String title, String url, String publisher, String rights) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.publisher = publisher;
        this.rights = rights;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String issn;
    private String title;
    private String url;
    private String publisher;
    private String rights;


    public Journal(int idJournal, String title) {
        this.id = idJournal;
        this.title = title;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }
}
