package artrec.com.artrec.server;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by VSB on 23/02/2016.
 */
public abstract class APICall extends AsyncTask<String, Void, String>{

    protected Activity parent;
    protected JSONArray resultJsonArray;
    protected String url;

    public APICall(Activity parent) {
        this.parent = parent;
    }

    protected String GET(String url) {
        InputStream inputStream;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();

            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
                Log.i("vilde", "The json result is: " + result);
            } else {
                //TODO error handling
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    protected String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        inputStream.close();
        return result;
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) parent.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

}
