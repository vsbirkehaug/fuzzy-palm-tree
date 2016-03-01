package com.groupb.artrec.server.calls;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import com.groupb.artrec.server.APICall;
import com.groupb.artrec.views.setup_views.DisciplinePicker;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by VSB on 23/02/2016.
 */
public class GetSubjectsAsyncTask extends APICall {

    public GetSubjectsAsyncTask(Activity parent, DisciplinePicker disciplinePicker) {
        super(parent, disciplinePicker);
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

            ArrayList<String> titles = new ArrayList<>();
            String title;

            for (int i = 0; i < resultJsonArray.length(); i++) {
                title = resultJsonArray.getJSONObject(i).getString("title");
                titles.add(title);
            }

            for(String s : titles) {
                Log.i("vilde", s);
            }

            disciplinePicker.handleResults(titles);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(parent.getBaseContext(), "Received!", Toast.LENGTH_SHORT).show();

    }

}
