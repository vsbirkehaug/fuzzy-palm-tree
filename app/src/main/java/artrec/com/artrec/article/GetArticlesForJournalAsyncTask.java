package artrec.com.artrec.article;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import artrec.com.artrec.journal.JournalFragment;
import artrec.com.artrec.models.Article;
import artrec.com.artrec.models.Journal;
import artrec.com.artrec.server.APICall;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Vilde on 23.04.2016.
 */
public class GetArticlesForJournalAsyncTask extends APICall {
    ArticleFragment fragment;

    public GetArticlesForJournalAsyncTask(Activity parent, ArticleFragment fragment) {
        super(parent);
        this.fragment = fragment;
    }

    @Override
    protected String doInBackground(String... urls) {

        String thisResult = GET(urls[0]);

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
}
