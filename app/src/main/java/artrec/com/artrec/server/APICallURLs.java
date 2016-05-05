package artrec.com.artrec.server;

import artrec.com.artrec.main.MainActivity;

import java.net.URI;

/**
 * Created by Vilde on 03.05.2016.
 */
public class APICallURLs {

    private static final String APIURL = "http://192.168.0.24:8080/ArtRec/api/v1/";

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

    public static String getJournalsForUser() {
        return APIURL.concat("getJournalsForUser");
    }

    public static String getUser() {
        return APIURL.concat("user");
    }

    public static String getSubjects() {
        return APIURL.concat("getSubjects");
    }

    public static String getSubjectsForUser() {
        return APIURL.concat("getSubjectsForUser");
    }

    public static String getKeywords() {
        return APIURL.concat("keywords");
    }

    public static String postProject() {
        return APIURL.concat("project");
    }
}
