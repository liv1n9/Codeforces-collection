package cfcollection.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class WebUtils {

    public static String documentFromUrl(String url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        BufferedReader in;
        if (urlConnection.getResponseCode() == 400) {
            in = new BufferedReader(new InputStreamReader((urlConnection.getErrorStream())));
        } else {
            in = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
        }
        StringBuilder result = new StringBuilder();
        String tmp;
        while ((tmp = in.readLine()) != null) {
            result.append(tmp).append("\n");
        }
        return result.toString();
    }
}
