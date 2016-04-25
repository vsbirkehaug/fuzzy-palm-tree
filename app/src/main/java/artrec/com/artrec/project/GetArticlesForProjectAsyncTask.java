package artrec.com.artrec.project;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import artrec.com.artrec.models.Article;
import artrec.com.artrec.models.Keyword;
import artrec.com.artrec.models.Project;
import artrec.com.artrec.server.APICall;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Vilde on 25.04.2016.
 */
public class GetArticlesForProjectAsyncTask extends APICall{
    private ProjectFragment fragment;
    private Project project;

    public GetArticlesForProjectAsyncTask(FragmentActivity activity, ProjectFragment fragment) {
        super(activity);
        this.fragment = fragment;
    }

    @Override
    protected String doInBackground(String... urls) {

        String thisResult = GETFORUSER(urls[0], project.getId());

        Log.i("vilde", "Result: " + thisResult);

        return thisResult;
    }


    // onPostExecute displays the results of the AsyncTask.
    @SuppressWarnings("static-access")
    @Override
    protected void onPostExecute(String result) {


        try {
            resultJsonArray = new JSONArray(result);

            ArrayList<Article> articles = new ArrayList<>();

            for (int i = 0; i < resultJsonArray.length(); i++) {
                articles.add(new Article(
                        replaceChars(resultJsonArray.getJSONObject(i).getString("title")),
                        replaceChars(resultJsonArray.getJSONObject(i).getString("link")),
                        replaceChars(resultJsonArray.getJSONObject(i).getString("publicationDate"))));
            }

            fragment.setArticleList(articles);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String replaceChars(String string) {
        return string.replace("[", "").replace("]", "").replace("\"", "").replace("\\n", "");
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
