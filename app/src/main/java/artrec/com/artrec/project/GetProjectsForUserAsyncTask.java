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
    ProjectFragment fragment;
    private int userId;

    public GetProjectsForUserAsyncTask(Activity parent, ProjectFragment fragment) {
        super(parent);
        this.fragment = fragment;
    }

    public void setUser(int userId) {
        this.userId = userId;
    }

    @Override
    protected String doInBackground(String... urls) {

        String thisResult = GETFORUSER(urls[0], userId);

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
            Keyword keyword;

            for (int i = 0; i < resultJsonArray.length(); i++) {

                keywords = new ArrayList<>();

                JSONArray kws = resultJsonArray.getJSONObject(i).getJSONArray("keywords");
                for(int j = 0; j < kws.length(); j++) {
                    keyword = new Keyword(kws.getJSONObject(j).getInt("idKeyword"), kws.getJSONObject(j).getString("keyword"));
                    keywords.add(keyword);
                }
                projects.add(new Project(
                        (resultJsonArray.getJSONObject(i).getInt("idProject")),
                        (resultJsonArray.getJSONObject(i).getString("title")),
                        (resultJsonArray.getJSONObject(i).getInt("idUser")),
                        keywords
                        ));
            }

            fragment.setProjectList(projects);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String replaceChars(String string) {
        return string.replace("[", "").replace("]", "").replace("\"", "").replace("\\n", "");
    }
}
