package artrec.com.artrec.project;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import artrec.com.artrec.models.Keyword;
import artrec.com.artrec.models.Subject;
import artrec.com.artrec.register.SubjectPicker;
import artrec.com.artrec.server.APICall;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by VSB on 05/05/2016.
 */
public class GetKeywordsAsyncTask extends APICall {

    private AddProjectFragment fragment;

    public GetKeywordsAsyncTask(Activity parent) {
        super(parent);
    }

    public GetKeywordsAsyncTask(Activity parent, AddProjectFragment fragment) {
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

            ArrayList<Keyword> keywords = new ArrayList<>();

            for (int i = 0; i < resultJsonArray.length(); i++) {
                keywords.add(new Keyword(resultJsonArray.getJSONObject(i).getInt("idKeyword"), resultJsonArray.getJSONObject(i).getString("keyword")));
            }

            fragment.setKeywords(keywords);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(parent.getBaseContext(), "Received!", Toast.LENGTH_SHORT).show();

    }
}
