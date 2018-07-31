package com.example.tle.popularmovies.util;

import android.net.Uri;

import com.example.tle.popularmovies.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    final private static String BASE_URL = "http://api.themoviedb.org/3/movie/";

    final private static String KEY_QUERY = "api_key";

    /**
     *
     */
    public static URL buildUrl(String sort) throws MalformedURLException {
        String url = BASE_URL + sort;
        Uri builtUri = Uri.parse(url).buildUpon()
                .appendQueryParameter(KEY_QUERY, BuildConfig.MOVIES_API_KEY)
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
