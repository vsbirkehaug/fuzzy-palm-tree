package artrec.com.artrec.project;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.widget.Toast;
import artrec.com.artrec.article.ArticleFragment;
import artrec.com.artrec.models.Article;
import artrec.com.artrec.models.Keyword;
import artrec.com.artrec.models.Project;
import artrec.com.artrec.server.APICall;
import com.sun.jna.platform.win32.WinDef;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Vilde on 25.04.2016.
 */
public class GetArticlesForProjectAsyncTask extends APICall{
    private ArticleFragment fragment;
    private int projectId;

    public GetArticlesForProjectAsyncTask(FragmentActivity activity, ArticleFragment fragment) {
        super(activity);
        this.fragment = fragment;
    }

    @Override
    protected String doInBackground(String... urls) {

        Log.i("vilde", "Project ID: " + projectId);
        String thisResult = GETFORUSER(urls[0], projectId);

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
                if(resultJsonArray.getJSONObject(i).has("date")) {
                    articles.add(new Article(
                            resultJsonArray.getJSONObject(i).getInt("idArticle"),
                            replaceChars(resultJsonArray.getJSONObject(i).getString("title")),
                            replaceChars(resultJsonArray.getJSONObject(i).getString("date"))));
                } else {
                    articles.add(new Article(
                            resultJsonArray.getJSONObject(i).getInt("idArticle"),
                            replaceChars(resultJsonArray.getJSONObject(i).getString("title"))));
                }
            }

            fragment.setArticleList(articles);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(fragment.getContext(), "Error fetching results.", Toast.LENGTH_SHORT).show();
        }

    }

    private String replaceChars(String string) {
        return string.replace("[", "").replace("]", "").replace("\"", "").replace("\\n", "");
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
