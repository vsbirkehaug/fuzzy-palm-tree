package artrec.com.artrec.models;

/**
 * Created by Vilde on 23.04.2016.
 */
public class User {
    private int id;
    private String username;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
