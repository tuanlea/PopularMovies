package com.example.tle.popularmovies;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    final static String BASE_URL =
            "http://api.themoviedb.org/3/discover/movie/";

    final static String KEY_QUERY = "api_key";
    final static String SORT_QUERY = "sort_by";

    /**
     *
     */
    public static URL buildUrl(String sort) throws MalformedURLException {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(KEY_QUERY, "")
                .appendQueryParameter(SORT_QUERY, sort)
                .build();

        return new URL(builtUri.toString());
    }

    /**
     *
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestMethod("GET");
        urlConnection.setInstanceFollowRedirects(true);
        urlConnection.connect();

        try {
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = urlConnection.getInputStream();
                return readStream(inputStream);
            }
        } finally {
            urlConnection.disconnect();
        }
        return "";
    }

    /**
     *
     */
    private static String readStream(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append('\n');
        }
        bufferedReader.close();

        return stringBuilder.toString();
    }

}
