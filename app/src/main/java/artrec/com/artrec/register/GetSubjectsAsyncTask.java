package artrec.com.artrec.register;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import artrec.com.artrec.models.Subject;
import artrec.com.artrec.server.APICall;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by VSB on 23/02/2016.
 */
public class GetSubjectsAsyncTask extends APICall {

    public GetSubjectsAsyncTask(Activity parent) {
        super(parent);
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

            ArrayList<Subject> subjects = new ArrayList<>();
            String title;

            for (int i = 0; i < resultJsonArray.length(); i++) {
                subjects.add(new Subject(resultJsonArray.getJSONObject(i).getInt("idSubject"), resultJsonArray.getJSONObject(i).getString("title")));
            }

            SubjectPicker.setResults(subjects);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(parent.getBaseContext(), "Received!", Toast.LENGTH_SHORT).show();

    }

}
