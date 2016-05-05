package artrec.com.artrec.data;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import artrec.com.artrec.models.Subject;
import artrec.com.artrec.register.SubjectPicker;
import artrec.com.artrec.server.APICall;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Vilde on 04.05.2016.
 */
public class GetSubjectsForUser extends APICall {

    private int userId;

    public GetSubjectsForUser(Activity parent) {
        super(parent);
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

            ArrayList<Subject> subjects = new ArrayList<>();

            for (int i = 0; i < resultJsonArray.length(); i++) {
                subjects.add(new Subject(resultJsonArray.getJSONObject(i).getInt("idSubject"), resultJsonArray.getJSONObject(i).getString("title")));
            }

            DataStore.getInstance().setSelectedSubjects(subjects);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(parent.getBaseContext(), "Received!", Toast.LENGTH_SHORT).show();

    }
}
