package artrec.com.artrec.data;

import artrec.com.artrec.journal.GetJournalsForUserAsyncTask;
import artrec.com.artrec.main.MainActivity;
import artrec.com.artrec.models.Journal;
import artrec.com.artrec.models.Subject;
import artrec.com.artrec.server.APICall;
import artrec.com.artrec.server.APICallURLs;

import java.util.ArrayList;

/**
 * Created by Vilde on 04.05.2016.
 */
public class DataStore {
    private static DataStore instance;
    private ArrayList<Subject> subjects;
    private ArrayList<Journal> journals;

    public static DataStore getInstance() {
        if(instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    public void setSelectedSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setSelectedJournals(ArrayList<Journal> journals) {
        this.journals = journals;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public ArrayList<Journal> getJournals() {
        return journals;
    }

    public void loadDataForUser(int userId) {
        GetJournalsForUserAsyncTask task = new GetJournalsForUserAsyncTask(MainActivity.INSTANCE);
        task.setUser(userId);
        task.execute(APICallURLs.getJournalsForUser());

        GetSubjectsForUser task2 = new GetSubjectsForUser(MainActivity.INSTANCE);
        task2.setUser(userId);
        task.execute(APICallURLs.getSubjectsForUser());
    }
}
