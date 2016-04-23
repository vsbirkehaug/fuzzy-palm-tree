package artrec.com.artrec.register;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import artrec.com.artrec.main.MainActivity;
import artrec.com.artrec.models.Subject;
import artrec.com.artrec.server.APICall;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by VSB on 23/02/2016.
 */
public class PostUserSubjectsAsyncTask extends APICall {

    private int userId;
    private int[] links;
    private String username;

    public PostUserSubjectsAsyncTask(Activity parent) {
        super(parent);
    }

        public void setUserAndLinks(String username, int userId, int[] links) {
            this.username = username;
        this.userId = userId;
        this.links = links;
    }

    @Override
    protected String doInBackground(String... urls) {

        String thisResult = POSTUSERLINKS(urls[0], userId, links);

        Log.i("vilde", "Result: " + thisResult);

        return thisResult;
    }

    // onPostExecute displays the results of the AsyncTask.
    @SuppressWarnings("static-access")
    @Override
    protected void onPostExecute(String result) {

        if(MainActivity.setSubjectSaveDone(true)) {
            parent.startActivity(new Intent(parent, MainActivity.class).putExtra("userid", userId).putExtra("username", username).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
   /*     try {
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
*/
        Toast.makeText(parent.getBaseContext(), "Received!", Toast.LENGTH_SHORT).show();

    }

}
