package artrec.com.artrec.models;

import java.util.ArrayList;

/**
 * Created by Vilde on 25.04.2016.
 */
public class Project {
    private int id;
    private String title;


    public String getKeywordsString() {
        if (keywords.size() > 0) {
            StringBuilder nameBuilder = new StringBuilder();

            for (Keyword kw : keywords) {
                nameBuilder.append(kw.getName()).append("',");
            }

            nameBuilder.deleteCharAt(nameBuilder.length() - 1);

            return nameBuilder.toString();
        } else {
            return "";
        }
    }
    public ArrayList<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<Keyword> keywords) {
        this.keywords = keywords;
    }

    private ArrayList<Keyword> keywords;

    public Project(int id, String title, int userId, ArrayList<Keyword> keywords) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.keywords = keywords;
    }

    public Project(String title, int userId) {

        this.title = title;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int userId;
}
