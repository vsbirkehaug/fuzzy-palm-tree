package artrec.com.artrec.journal;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import artrec.com.artrec.models.Journal;
import artrec.com.artrec.server.APICall;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Vilde on 23.04.2016.
 */
public class GetJournalsAsyncTask extends APICall {

    JournalFragment fragment;

    public GetJournalsAsyncTask(Activity parent, JournalFragment fragment) {
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

            ArrayList<Journal> journals = new ArrayList<>();


            for (int i = 0; i < resultJsonArray.length(); i++) {
                journals.add(new Journal(
                        resultJsonArray.getJSONObject(i).getString("issn"),
                        resultJsonArray.getJSONObject(i).getString("title"),
                        resultJsonArray.getJSONObject(i).getString("url"),
                        resultJsonArray.getJSONObject(i).getString("publisher"),
                        resultJsonArray.getJSONObject(i).getString("rights")));
            }

            fragment.setJournalList(journals);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(parent.getBaseContext(), "Received!", Toast.LENGTH_SHORT).show();


    }

}
