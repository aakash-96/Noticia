package com.example.android.newsfeed.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by HP PC on 12-02-2017.
 */

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_URL = "https://newsapi.org/v1/articles?";
    private static final String API_KEY = "e054143538da488cab4e3d3f3e9c9733";

    public static URL buildUrl(String source,String sort_By)
    {
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("source",source)
                .appendQueryParameter("sortBy",sort_By)
                .appendQueryParameter("apiKey",API_KEY)
                .build();
        URL final_Url = null;
        try{
            final_Url = new URL(uri.toString());
        }
        catch(MalformedURLException e){
            Log.e(LOG_TAG,"Error forming URL");
            e.printStackTrace();
        }
        return final_Url;
    }

    public static String getResponseFromHttp(URL url) throws IOException {
        if(url == null)
            return null;
        InputStream inputStream = null;
        String jsonResponse = null;
        HttpURLConnection urlConnection = null;

        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Content-Type","application/jason");
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(150000);

        try{
            if(urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else
                Log.e(LOG_TAG,"Error Response Code: " + urlConnection.getResponseCode());

            if(urlConnection!=null)
                urlConnection.disconnect();
            if(inputStream!=null)
                inputStream.close();
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG,"IOException in getResponseFromHttp");
            e.printStackTrace();
        } finally {
            return jsonResponse;
        }
    }

    public static String readFromStream(InputStream inputStream)
    {
        StringBuilder jsonResponse = new StringBuilder();
        if(inputStream==null)
            return null;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line =null;
        try{
            line = bufferedReader.readLine();
        }
        catch(IOException e){
            Log.e(LOG_TAG,"inputsream error");
            e.printStackTrace();
        }
        while(line!=null)
        {
            jsonResponse.append(line);
            try{
                line = bufferedReader.readLine();
            }
            catch(IOException e){
                Log.e(LOG_TAG,"inputsream error");
                e.printStackTrace();
            }
        }
        return jsonResponse.toString();
    }

    public static boolean haveNetworkConnection(Context context)
    {
        boolean haveConnectedWifi = false;
        boolean haveConnectedData = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork!=null)
        {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return true;
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }

        return false;
    }
}
