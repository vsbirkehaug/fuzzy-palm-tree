package artrec.com.artrec.server;

import artrec.com.artrec.main.MainActivity;

import java.net.URI;

/**
 * Created by Vilde on 03.05.2016.
 */
public class APICallURLs {

    private static final String APIURL = "http://192.168.0.11:8080/ArtRec/api/v1/";

    public static String getUserExists() {
        return APIURL.concat("userexists");
    }

    public static String getArticlesForProject() {
        return APIURL.concat("getArticlesForProject");
    }

    public static String getArticlesForJournal(String issn) {
        return APIURL.concat("getAllArticlesForJournal?issn=".concat(issn));
    }

    public static String postUser() {
        return APIURL.concat("user");
    }

    public static String getJournalsForSubjects() {
        return APIURL.concat("getJournalsForSubjects");
    }

    public static String postUserSubject() {
        return APIURL.concat("userSubject");
    }

    public static String postUserJournal() {
        return APIURL.concat("userJournal");
    }

    public static String getProjectsForUser() {
        return APIURL.concat("getProjectsForUser");
    }

    public static String authenticateUser() {
        return APIURL.concat("user");
    }

    public static String getJournals() {
        return APIURL.concat("getJournals");
    }

    public static String getUser() {
        return APIURL.concat("user");
    }

    public static String getSubjects() {
        return APIURL.concat("getSubjects");
    }
}
