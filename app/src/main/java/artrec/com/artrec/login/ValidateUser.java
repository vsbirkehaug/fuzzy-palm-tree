package artrec.com.artrec.login;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 *
 * @author Vilde
 */
public class ValidateUser {

    public static int NO_OPTIONS=0;


    private static String convertToHex(byte[] data) throws java.io.IOException
    {
        StringBuffer sb = new StringBuffer();
        String hex=null;
        hex=Base64.encodeToString(data, 0, data.length, NO_OPTIONS);

        sb.append(hex);

        return sb.toString();
    }

    public static String computeSHAHash(String password)
    {
        String SHAHash = "";
        MessageDigest mdSha1 = null;
        try
        {
            mdSha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e1) {
            Log.e("myapp", "Error initializing SHA1 message digest");
        }
        try {
            mdSha1.update(password.getBytes("ASCII"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] data = mdSha1.digest();
        try {
            SHAHash=convertToHex(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return SHAHash;
    }

}
