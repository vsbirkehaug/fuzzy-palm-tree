package artrec.com.artrec.server;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;


import artrec.com.artrec.login.LoginActivity;
import artrec.com.artrec.login.ValidateUser;
import artrec.com.artrec.models.Journal;
import artrec.com.artrec.models.User;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

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

    protected String GETFORUSER(String url, int userId) {
        InputStream inputStream;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();

            HttpGet get = new HttpGet(url);

            get.addHeader("userid", String.valueOf(userId));
            get.setHeader("Content-Type", "application/x-www-form-urlencoded");

            HttpResponse httpResponse = httpclient.execute(get);

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


    private String intArrayToString(int[] array) {
        String stringIds = "";
        for(int id : array) {
            stringIds+= (String.valueOf(id)+",");
        }
        return stringIds;
    }

    protected String GETJOURNALS(String url, int[] ids) {
        InputStream inputStream;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();

            HttpGet get = new HttpGet(url);

            String stringIds = intArrayToString(ids);
            stringIds = stringIds.substring(0, stringIds.length()-2);
            Log.i("vilde", "sending string: " + stringIds);

            get.addHeader("ids", java.net.URLEncoder.encode(stringIds, "UTF-8"));
            get.setHeader("Content-Type", "application/x-www-form-urlencoded");

            HttpResponse httpResponse = httpclient.execute(get);

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

    protected String GETUSER(String username, String password) {
        InputStream inputStream;
        String result = "";
        try {

            String passwordHash = ValidateUser.computeSHAHash(password);

//            CookieStore cookieStore = new BasicCookieStore();

//            HttpContext localContext = new BasicHttpContext();
//            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);


           // HttpPost post = new HttpPost("http://192.168.0.13:8080/ArtRec/api/v1/user");
//            ArrayList<NameValuePair> params = new ArrayList<>();
//            params.add(new BasicNameValuePair("username", java.net.URLEncoder.encode(username, "UTF-8")));
//            params.add(new BasicNameValuePair("password",  java.net.URLEncoder.encode(passwordHash, "UTF-8")));
            //post.addHeader("username", username);
            //post.addHeader("password", passwordHash);
            //post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            //post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            //HttpResponse httpResponse = httpclient.execute(post);

            HttpClient httpclient = new DefaultHttpClient();

            HttpGet get = new HttpGet("http://192.168.0.13:8080/ArtRec/api/v1/user");

            get.addHeader("username", java.net.URLEncoder.encode(username, "UTF-8"));
            get.addHeader("password", java.net.URLEncoder.encode(passwordHash, "UTF-8"));
            get.setHeader("Content-Type", "application/x-www-form-urlencoded");

            HttpResponse httpResponse = httpclient.execute(get);

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

    protected String POSTUSER(String username, String password) {
        InputStream inputStream;
        String result = "";
        try {

            String passwordHash = ValidateUser.computeSHAHash(password);

//            CookieStore cookieStore = new BasicCookieStore();

//            HttpContext localContext = new BasicHttpContext();
//            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost post = new HttpPost("http://192.168.0.13:8080/ArtRec/api/v1/user");
            ArrayList<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username", java.net.URLEncoder.encode(username, "UTF-8")));
            params.add(new BasicNameValuePair("password",  java.net.URLEncoder.encode(passwordHash, "UTF-8")));
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpResponse httpResponse = httpclient.execute(post);

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

    protected String POSTUSERLINKS(String url, int userid, int[]ids) {
        InputStream inputStream;
        String result = "";
        try {

            String idString = intArrayToString(ids);

            HttpClient httpclient = new DefaultHttpClient();

            HttpPost post = new HttpPost(url);
            ArrayList<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("userid", java.net.URLEncoder.encode(String.valueOf(userid), "UTF-8")));
            params.add(new BasicNameValuePair("ids", java.net.URLEncoder.encode(idString, "UTF-8")));
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpResponse httpResponse = httpclient.execute(post);

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
