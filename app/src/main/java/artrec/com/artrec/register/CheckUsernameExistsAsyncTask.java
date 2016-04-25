package artrec.com.artrec.register;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import artrec.com.artrec.R;
import artrec.com.artrec.models.User;
import artrec.com.artrec.server.APICall;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Vilde on 25.04.2016.
 */
public class CheckUsernameExistsAsyncTask extends APICall {
    private String authUsername;
    private String authPassword;


    public CheckUsernameExistsAsyncTask(Activity parent) {
        super(parent);
    }

    public void setCredentials(String username) {
        this.authUsername = username;
    }

    @Override
    protected String doInBackground(String... urls) {

        String result = String.valueOf(GET(this.authUsername));

        Log.i("vilde", "Result: " + result);

        return result;
    }


    // onPostExecute displays the results of the AsyncTask.
    @SuppressWarnings("static-access")
    @Override
    protected void onPostExecute(String result) {

        boolean exists = true;

        try {
            resultJsonArray = new JSONArray(result);

            if(result != null) {
                for (int i = 0; i < resultJsonArray.length(); i++) {
                    try {
                        if(resultJsonArray.getJSONObject(i).has("exists")) {
                            exists = resultJsonArray.getJSONObject(i).getBoolean("exists");
                        }
                    }catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }





}
