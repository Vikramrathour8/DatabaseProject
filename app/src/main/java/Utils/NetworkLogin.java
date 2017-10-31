package Utils;

import android.content.Intent;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by user on 07-10-2017.
 */

public class NetworkLogin {

    final static String LOG_TABLE_URL= "http://localhost/myFolder/login.php";
    final static String PARAM_QUERY="q";
    public static URL buildUrl(String login_id, String login_password)
    {
        Uri  builtUri=Uri.parse(LOG_TABLE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY,login_id)
                .appendQueryParameter(PARAM_QUERY,login_password)
                .build();
       URL url=null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;

    }

    public static String getResponseFromHttpUrl(URL url) throws IOException
    {
        HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();

            } else
                return null;
        }
        finally
        {
            urlConnection.disconnect();
        }
    }
}
