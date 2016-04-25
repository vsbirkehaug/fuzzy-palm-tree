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
public class GetJournalsForUserAsyncTask extends APICall {

    JournalFragment fragment;
    private int userId;

    public GetJournalsForUserAsyncTask(Activity parent, JournalFragment fragment) {
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

            ArrayList<Journal> journals = new ArrayList<>();

            for (int i = 0; i < resultJsonArray.length(); i++) {
                try {
                    if(resultJsonArray.getJSONObject(i).has("issn")) {
                        journals.add(new Journal(
                                resultJsonArray.getJSONObject(i).getInt("idJournal"),
                                resultJsonArray.getJSONObject(i).getString("issn"),
                                resultJsonArray.getJSONObject(i).getString("title"),
                                resultJsonArray.getJSONObject(i).getString("url"),
                                resultJsonArray.getJSONObject(i).getString("publisher"),
                                resultJsonArray.getJSONObject(i).getString("rights")));
                    }
//                    else {
//                        journals.add(new Journal(
//                                resultJsonArray.getJSONObject(i).getInt("id"),
//                                resultJsonArray.getJSONObject(i).getString("title"),
//                                resultJsonArray.getJSONObject(i).getString("url"),
//                                resultJsonArray.getJSONObject(i).getString("publisher"),
//                                resultJsonArray.getJSONObject(i).getString("rights")));
//                    }
                }catch (JSONException ex) {
                    ex.printStackTrace();
                }
                fragment.setJournalListView(journals);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(parent.getBaseContext(), "Received!", Toast.LENGTH_SHORT).show();


    }

}
