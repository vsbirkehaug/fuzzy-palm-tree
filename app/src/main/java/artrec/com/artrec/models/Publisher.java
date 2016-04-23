package artrec.com.artrec.models;

import java.util.ArrayList;

/**
 * Created by Vilde on 23.04.2016.
 */
public class Publisher {
    private String firstName;
    private String lastName;
    private ArrayList<String> articles;

    public ArrayList<String> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<String> articles) {
        this.articles = articles;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
