package artrec.com.artrec.login;
import android.util.Base64;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;

/**
 *
 * @author Vilde
 */
public class ValidateUser {
    public static String getUser(String username, String password)
    {
        int userId = 1;
        try{


/*            String formData = "username=<uname>&password=<pass>&grant_type=password";
            String header = "Basic " + Base64.encodeToString("<client_id>:<client_secret>".getBytes(), Base64.URL_SAFE);

            HttpURLConnection connection
                    = (HttpURLConnection) new URL(tokenUrl).openConnection();
            connection.setDoOutput(true);
            connection.addRequestProperty("Authorization", header);
            connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestMethod("POST");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", Integer.toString(formData.length()));

            OutputStream out = connection.getOutputStream();
            out.write(formData.getBytes(StandardCharsets.UTF_8));

            InputStream in = connection.getInputStream();
            AccessToken token = new ObjectMapper().readValue(in, AccessToken.class);
            System.out.println(token);

            out.close();
            in.close();*/

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return String.valueOf(userId);
    }
}
