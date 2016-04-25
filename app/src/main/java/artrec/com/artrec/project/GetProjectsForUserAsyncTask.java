package artrec.com.artrec.project;

import android.app.Activity;
import android.util.Log;
import artrec.com.artrec.models.Article;
import artrec.com.artrec.models.Keyword;
import artrec.com.artrec.models.Project;
import artrec.com.artrec.server.APICall;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;

/**
 * Created by Vilde on 23.04.2016.
 */
public class GetProjectsForUserAsyncTask extends APICall {
    private ProjectFragment fragment;

    public GetProjectsForUserAsyncTask(Activity parent, ProjectFragment fragment) {
        super(parent);
        this.fragment = fragment;
    }

    @Override
    protected String doInBackground(String... urls) {

        String thisResult = GETFORUSER(urls[0], Integer.parseInt(urls[1]));

        Log.i("vilde", "Result: " + thisResult);

        return thisResult;
    }


    // onPostExecute displays the results of the AsyncTask.
    @SuppressWarnings("static-access")
    @Override
    protected void onPostExecute(String result) {

        try {
            resultJsonArray = new JSONArray(result);

            ArrayList<Project> projects = new ArrayList<>();
            ArrayList<Keyword> keywords;

            keywords = new ArrayList<>();

            for (int i = 0; i < resultJsonArray.length(); i++) {

                if(projectWithId(resultJsonArray.getJSONObject(i).getInt("idProject"), projects) == null) {
                    keywords = new ArrayList<>();
                    projects.add(new Project(
                            (resultJsonArray.getJSONObject(i).getInt("idProject")),
                            (resultJsonArray.getJSONObject(i).getString("title")),
                            (resultJsonArray.getJSONObject(i).getInt("idUser")),
                            keywords
                    ));
                } else {
                    projectWithId(resultJsonArray.getJSONObject(i).getInt("idProject"), projects).getKeywords().add(new Keyword(resultJsonArray.getJSONObject(i).getInt("idKeyword"), resultJsonArray.getJSONObject(i).getString("keyword")));
                }
            }

            fragment.setProjectList(projects);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Project projectWithId(int id, ArrayList<Project> projects) {
        for(Project p : projects) {
            if(p.getId() == id)
                return p;
        }
        return null;
    }

    private boolean listHasProjectWithId(int id, ArrayList<Project> projects) {
        for(Project p : projects) {
            if(p.getId() == id)
                return true;
        }
        return false;
    }

    private String replaceChars(String string) {
        return string.replace("[", "").replace("]", "").replace("\"", "").replace("\\n", "");
    }
}
