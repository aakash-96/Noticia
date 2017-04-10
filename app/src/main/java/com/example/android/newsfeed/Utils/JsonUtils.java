package com.example.android.newsfeed.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.android.newsfeed.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HP PC on 12-02-2017.
 */

public class JsonUtils {
    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    public static ArrayList<Article> extractFeaturesFromJson(String jsonResponse)
    {
        ArrayList<Article> al_articles = new ArrayList<Article>();
        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray jarray = root.getJSONArray("articles");

            int i;
            for(i=0;i<jarray.length();++i) {
                JSONObject article = jarray.getJSONObject(i);

                String author = article.getString("author");
                String title = article.getString("title");
                String description = article.getString("description");
                String url = article.getString("url");
                String urlToImage = article.getString("urlToImage");
                String date = article.getString("publishedAt");

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                Date dt = null;
                String temp = "";
                try
                {
                    dt = formatter.parse(date.replaceAll("Z$", "+0000"));
                    temp = dt.toString();
                    Log.i(LOG_TAG,dt.toString());
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                al_articles.add(new Article(author,title,description,url,urlToImage,temp));
            }
            return al_articles;

        } catch (JSONException e) {
            Log.e(LOG_TAG,"Error parsing JSON object: ",e);
            e.printStackTrace();
        }
        return null;
    }
}
