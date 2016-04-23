package artrec.com.artrec.register;

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
public class GetJournalsForSubjectsAsyncTask extends APICall {

    private int[] ids;

    public GetJournalsForSubjectsAsyncTask(Activity parent) {
        super(parent);
    }

    public void setParameters(int[] ids) {
        this.ids = ids;
    }

    @Override
    protected String doInBackground(String... urls) {

        Log.i("vilde", "url: " + urls[0]);
        Log.i("vilde", "id length: " + ids.length);

        String thisResult = GETJOURNALS(urls[0], ids);

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
                journals.add(new Journal(resultJsonArray.getJSONObject(i).getInt("idJournal"), resultJsonArray.getJSONObject(i).getString("title")));
            }

            JournalPicker.setResults(journals);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(parent.getBaseContext(), "Received!", Toast.LENGTH_SHORT).show();

    }



}
