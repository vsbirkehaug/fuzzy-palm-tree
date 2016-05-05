package artrec.com.artrec.project;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import artrec.com.artrec.main.MainActivity;
import artrec.com.artrec.models.Project;
import artrec.com.artrec.server.APICall;

import java.util.ArrayList;

/**
 * Created by VSB on 05/05/2016.
 */
public class PostNewProjectAsyncTask extends APICall{
    private final AddProjectFragment fragment;
    private final Project project;
    private final int[] keywords;
    private final int userId;

    public PostNewProjectAsyncTask(Activity activity, AddProjectFragment addProjectFragment, Project project, int[] keywords) {
        super(activity);
        this.fragment = addProjectFragment;
        this.userId = project.getUserId();
        this.project = project;
        this.keywords = keywords;
    }

    @Override
    protected String doInBackground(String... urls) {

        String thisResult = POSTPROJECTLINKS(urls[0], userId, project.getTitle(), keywords);

        Log.i("vilde", "Result: " + thisResult);

        return thisResult;
    }

    // onPostExecute displays the results of the AsyncTask.
    @SuppressWarnings("static-access")
    @Override
    protected void onPostExecute(String result) {

        fragment.doneCreating();
    }

}
