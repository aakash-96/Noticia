package com.example.android.newsfeed.Fragments;

import android.content.ContentValues;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.newsfeed.Article;
import com.example.android.newsfeed.Data.ArticleContract;
import com.example.android.newsfeed.Data.ArticleDbHelper;
import com.example.android.newsfeed.Fragment_Others_RecyclerViewAdapter;
import com.example.android.newsfeed.R;
import com.example.android.newsfeed.Utils.JsonUtils;
import com.example.android.newsfeed.Utils.NetworkUtils;
import com.example.android.newsfeed.Utils.RecyclerItemClickListener;
import com.example.android.newsfeed.WebViewActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;


public class Fragment_Others extends Fragment{
    private static final String LOG_TAG = Fragment_Others.class.getSimpleName();
    private Context context;
    private static int tabNo;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mLoadingIndicator;
    private static int count=1;
    public static String image_path = "/data/user/0/com.example.android.newsfeed/app_Entertainment_Images";
    RecyclerView recyclerView;
    ArrayList<Article> al_Article = new ArrayList<Article>();
    ArrayList<Article> top_al_Article = new ArrayList<Article>();
    ArrayList<Article> al_Image_Download= new ArrayList<Article>();
    SharedPreferences sharedPreferences;
    String category = "";
    private static String dir = "";


    public Fragment_Others(Context cxt,int tabNo) {
        context = cxt;
        this.tabNo = tabNo-1;
        Log.i("checking","tabno. = "+tabNo);
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_others,container,false);
        mLoadingIndicator = (ProgressBar) view.findViewById(R.id.fragment_others_progressbar);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_others_recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (position==0)
                        {
                            return;
                        }
                        else {
                            Intent intent = new Intent(context, WebViewActivity.class);
                            intent.putExtra("url", al_Article.get(position - 1).getUrl());
                            startActivity(intent);
                        }
                    }
                }) {
                    @Override
                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                    }
                }
        );

        Calendar c = Calendar.getInstance();
        sharedPreferences = getContext().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long window = (long) 1;
        long prev = 0;

        URL[] temp = new URL[20];
        switch (tabNo)
        {
            case 0: //Home
            case 1: //Entertainment
                temp[0] = NetworkUtils.buildUrl("buzzfeed","latest");
                temp[1] = NetworkUtils.buildUrl("mashable","latest");
                temp[2] = NetworkUtils.buildUrl("the-lad-bible","latest");
                temp[3] = NetworkUtils.buildUrl("daily-mail","latest");
                count=4;
                temp[4] = NetworkUtils.buildUrl("entertainment-weekly","top");
                temp[5] = NetworkUtils.buildUrl("the-lad-bible","top");
                category = "ENTERTAINMENT";
                prev = sharedPreferences.getLong("ENTERTAINMENT",1);
                Log.i(LOG_TAG,"ENTERTAINMENT");
                dir = "Entertainment_Images";
                break;
            case 2: //Technology
                temp[0] = NetworkUtils.buildUrl("engadget","latest");
                temp[1] = NetworkUtils.buildUrl("techradar","latest");
                temp[2] = NetworkUtils.buildUrl("techcrunch","latest");
                temp[3] = NetworkUtils.buildUrl("ars-technica","latest");
                count=4;
                category = "TECHNOLOGY";
                prev = sharedPreferences.getLong("TECHNOLOGY",1);
                Log.i(LOG_TAG,"TECHNOLOGY");
                dir = "Technology_Images";
                break;
            case 3: //Sports
                temp[0] = NetworkUtils.buildUrl("espn-cric-info","latest");
                temp[1] = NetworkUtils.buildUrl("four-four-two","latest");
                temp[2] = NetworkUtils.buildUrl("nfl-news","latest");
                count=3;
                category = "SPORTS";
                prev = sharedPreferences.getLong("SPORTS",1);
                Log.i(LOG_TAG,"SPORTS");
                dir = "Sports_Images";
                break;
            case 4: //Business
                temp[0] = NetworkUtils.buildUrl("business-insider","latest");
                temp[1] = NetworkUtils.buildUrl("financial-times","latest");
                temp[2] = NetworkUtils.buildUrl("fortune","latest");
                count=3;
                category = "BUSINESS";
                prev = sharedPreferences.getLong("BUSINESS",1);
                Log.i(LOG_TAG,"BUSINESS");
                dir = "Business_Images";
        }


        Log.i(LOG_TAG,"shared Preference initial: " + prev);
        long current_time = c.getTimeInMillis();


        if(current_time - prev > window && NetworkUtils.haveNetworkConnection(context))
        {
            Log.i(LOG_TAG,"fetching new data");
            editor.remove(category);
            editor.putLong(category,current_time);
            editor.commit();
            Fragment_Others_AsyncTask task = new Fragment_Others_AsyncTask();
            task.execute(temp);
        }
        else
        {
            Log.i(LOG_TAG,"loading from database");

            long minutes = (((current_time - prev)/1000)/60);
            Log.i(LOG_TAG,"fetched " + minutes + " min before");
            loadFromDatabase();
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            Fragment_Others_RecyclerViewAdapter adapter = new Fragment_Others_RecyclerViewAdapter(context,al_Article,top_al_Article,tabNo);

            recyclerView.setAdapter(adapter);
        }

        return view;
    }


    private void informUser()
    {
        Log.i(LOG_TAG,"informing the user");
        Toast.makeText(context, "Check your Network Connection", Toast.LENGTH_SHORT).show();
    }

    public void loadFromDatabase()
    {
        ArticleDbHelper articleDbHelper = new ArticleDbHelper(context);
        SQLiteDatabase db = articleDbHelper.getReadableDatabase();
        String sqlQuery = "";
        Log.i(LOG_TAG,"Load from Database");
        switch (tabNo)
        {
            case 0://Home
            case 1://Entertainment
                sqlQuery = "SELECT * FROM " + ArticleContract.EntertainmentEntry.TABLE_NAME + ";";
                break;
            case 2://Technology
                sqlQuery = "SELECT * FROM " + ArticleContract.TechnologyEntry.TABLE_NAME + ";";
                break;
            case 3://Sports
                sqlQuery = "SELECT * FROM " + ArticleContract.SportsEntry.TABLE_NAME + ";";
                break;
            case 4://Business
                sqlQuery = "SELECT * FROM " + ArticleContract.BusinessEntry.TABLE_NAME + ";";
        }

        Log.i(LOG_TAG,sqlQuery);


        Cursor cursor = db.rawQuery(sqlQuery,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            long id = cursor.getLong(cursor.getColumnIndex(ArticleContract.EntertainmentEntry._ID));
            String title = cursor.getString(1);
            String author = cursor.getString(2);
            String description = cursor.getString(cursor.getColumnIndex(ArticleContract.EntertainmentEntry.COLUMN_DESCRIPTION));
            String url = cursor.getString(4);
            String date = cursor.getString(6);
            String url_image = cursor.getString(5);
            String temp = cursor.getString(7);
            boolean is_Image_Downloaded = false;
            if(temp.endsWith("1"))
               is_Image_Downloaded = true;

            Article temp_article = new Article(author,title,description,url,url_image,date);
            temp_article.setId(id);
            temp_article.set_Image_Downloaded(is_Image_Downloaded);

            al_Article.add(temp_article);
            cursor.moveToNext();
        }

        sqlQuery = "SELECT * FROM " + ArticleContract.topEntertainmentEntry.TABLE_NAME + ";";
        cursor = db.rawQuery(sqlQuery,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            long id = cursor.getLong(cursor.getColumnIndex(ArticleContract.EntertainmentEntry._ID));
            String title = cursor.getString(1);
            String author = cursor.getString(2);
            String description = cursor.getString(cursor.getColumnIndex(ArticleContract.EntertainmentEntry.COLUMN_DESCRIPTION));
            String url = cursor.getString(4);
            String date = cursor.getString(6);
            String url_image = cursor.getString(5);
            String temp = cursor.getString(7);
            boolean is_Image_Downloaded = false;
            if(temp.endsWith("1"))
                is_Image_Downloaded = true;

            Article temp_article = new Article(author,title,description,url,url_image,date);
            temp_article.setId(id);
            temp_article.set_Image_Downloaded(is_Image_Downloaded);

            top_al_Article.add(temp_article);
            cursor.moveToNext();
        }

        cursor.close();

        Log.i(LOG_TAG,"Load from Database ended");
    }

    public static String saveToInternalStorage(Bitmap bitmapImage, Context context, long id){
        Log.i(LOG_TAG,"saving to internal storage");

        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(dir, Context.MODE_PRIVATE);
        // Create imageDir
        image_path = directory.toString();
        Log.i(LOG_TAG,directory.toString());
        File mypath=new File(directory,"" + id);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public class Fragment_Others_AsyncTask extends AsyncTask<URL, Void, String[]>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(URL... urls)
        {
            String jsonResponse = "";
            String[] arjson = new String[1];

            if(NetworkUtils.haveNetworkConnection(context))
            {
                for (int i=0;i<count+2;++i)
                {
                    try {
                        jsonResponse = NetworkUtils.getResponseFromHttp(urls[i]);
                        ArrayList<Article> temp = JsonUtils.extractFeaturesFromJson(jsonResponse);
                        if(i<count)
                            al_Article.addAll(temp);
                        else top_al_Article.addAll(temp);
                    } catch (IOException e) {
                        Log.e(LOG_TAG,"Background HTTP error: ",e);
                        e.printStackTrace();
                    }
                }
                return arjson;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] ar_result)
        {
            Log.i(LOG_TAG,"OnPost");
            if(ar_result==null) {
                informUser();
                return;
            }

            //Deleting the data from the table
            ArticleDbHelper articleDbHelper = new ArticleDbHelper(context);
            SQLiteDatabase db = articleDbHelper.getReadableDatabase();
            String sqlQuery = "Drop table if exists " + category + ";";
            db.execSQL(sqlQuery);

            sqlQuery = "Drop table if exists " + ArticleContract.topEntertainmentEntry.TABLE_NAME + ";";
            db.execSQL(sqlQuery);

            String SQL_CREATE_TABLE = "CREATE TABLE " + category + "(" +
                    ArticleContract.EntertainmentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ArticleContract.EntertainmentEntry.COLUMN_TITLE + " TEXT, " +
                    ArticleContract.EntertainmentEntry.COLUMN_AUTHOR + " TEXT, " +
                    ArticleContract.EntertainmentEntry.COLUMN_DESCRIPTION + " TEXT, " +
                    ArticleContract.EntertainmentEntry.COLUMN_URL + " TEXT, " +
                    ArticleContract.EntertainmentEntry.COLUMN_URLTOIMAGE + " TEXT, " +
                    ArticleContract.EntertainmentEntry.COLUMN_DATE + " TEXT, " +
                    ArticleContract.EntertainmentEntry.COLUMN_ISIMAGEDOWNLOADED + " TEXT" + ");";

            db.execSQL(SQL_CREATE_TABLE);

            SQL_CREATE_TABLE = "CREATE TABLE " + ArticleContract.topEntertainmentEntry.TABLE_NAME + "(" +
                    ArticleContract.EntertainmentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ArticleContract.EntertainmentEntry.COLUMN_TITLE + " TEXT, " +
                    ArticleContract.EntertainmentEntry.COLUMN_AUTHOR + " TEXT, " +
                    ArticleContract.EntertainmentEntry.COLUMN_DESCRIPTION + " TEXT, " +
                    ArticleContract.EntertainmentEntry.COLUMN_URL + " TEXT, " +
                    ArticleContract.EntertainmentEntry.COLUMN_URLTOIMAGE + " TEXT, " +
                    ArticleContract.EntertainmentEntry.COLUMN_DATE + " TEXT, " +
                    ArticleContract.EntertainmentEntry.COLUMN_ISIMAGEDOWNLOADED + " TEXT" + ");";

            db.execSQL(SQL_CREATE_TABLE);
            articleDbHelper.close();

            //Updating the database
            articleDbHelper = new ArticleDbHelper(context);
            db = articleDbHelper.getWritableDatabase();
            for (int i=0;i<al_Article.size(); ++i)
            {
                ContentValues values = new ContentValues();
                values.put(ArticleContract.EntertainmentEntry.COLUMN_TITLE, al_Article.get(i).getTitle());
                values.put(ArticleContract.EntertainmentEntry.COLUMN_AUTHOR, al_Article.get(i).getAuthor());
                values.put(ArticleContract.EntertainmentEntry.COLUMN_DESCRIPTION, al_Article.get(i).getDescription());
                values.put(ArticleContract.EntertainmentEntry.COLUMN_URL, al_Article.get(i).getUrl());
                values.put(ArticleContract.EntertainmentEntry.COLUMN_URLTOIMAGE, al_Article.get(i).getUrlToImage());
                values.put(ArticleContract.EntertainmentEntry.COLUMN_DATE, al_Article.get(i).getDate());
                values.put(ArticleContract.EntertainmentEntry.COLUMN_ISIMAGEDOWNLOADED,"0");

                long newrowid = db.insert(category, null, values);
            }

            Log.i("checking","Entertainment: VPsize- "+top_al_Article.size());

            for (int i=0;i<top_al_Article.size(); ++i)
            {
                ContentValues values = new ContentValues();
                values.put(ArticleContract.EntertainmentEntry.COLUMN_TITLE, top_al_Article.get(i).getTitle());
                values.put(ArticleContract.EntertainmentEntry.COLUMN_AUTHOR, top_al_Article.get(i).getAuthor());
                values.put(ArticleContract.EntertainmentEntry.COLUMN_DESCRIPTION, top_al_Article.get(i).getDescription());
                values.put(ArticleContract.EntertainmentEntry.COLUMN_URL, top_al_Article.get(i).getUrl());
                values.put(ArticleContract.EntertainmentEntry.COLUMN_URLTOIMAGE, top_al_Article.get(i).getUrlToImage());
                values.put(ArticleContract.EntertainmentEntry.COLUMN_DATE, top_al_Article.get(i).getDate());
                values.put(ArticleContract.EntertainmentEntry.COLUMN_ISIMAGEDOWNLOADED,"0");

                long newrowid = db.insert(ArticleContract.topEntertainmentEntry.TABLE_NAME, null, values);
            }
            articleDbHelper.close();

            mLoadingIndicator.setVisibility(View.INVISIBLE);

            al_Article.clear();
            top_al_Article.clear();
            loadFromDatabase();
            Fragment_Others_RecyclerViewAdapter adapter = new Fragment_Others_RecyclerViewAdapter(context,al_Article,top_al_Article,tabNo);

            recyclerView.setAdapter(adapter);

//            Image_Download_AsyncTask temp_task = new Image_Download_AsyncTask();
//            temp_task.execute();
        }
    }

    public class Image_Download_AsyncTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {

            ArticleDbHelper articleDbHelper = new ArticleDbHelper(context);
            SQLiteDatabase db = articleDbHelper.getReadableDatabase();
            String sqlQuery = "SELECT * FROM " + category + ";";

            Cursor cursor = db.rawQuery(sqlQuery,null);
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                long id = cursor.getLong(0);
                String url_image = cursor.getString(5);

                Article temp_article = new Article(null,null,null,null,url_image,null);
                temp_article.setId(id);
                al_Image_Download.add(temp_article);
                cursor.moveToNext();
            }
            cursor.close();

            for (int i=0;i<al_Image_Download.size();++i)
            {
                try {
                    Log.i(LOG_TAG,"in try block");
//                    URL temp_url = new URL(al_Article.get(i).getUrlToImage());
//                        Bitmap image = BitmapFactory.decodeStream(temp_url.openConnection().getInputStream());
                    URL temp_url = new URL(al_Image_Download.get(i).getUrlToImage());

                    HttpURLConnection connection = (HttpURLConnection) temp_url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap image = BitmapFactory.decodeStream(input);

                    Log.i(LOG_TAG,"calling function");
                    saveToInternalStorage(image, context, al_Article.get(i).getId());


                    SQLiteDatabase dbs = articleDbHelper.getWritableDatabase();
                    dbs.execSQL("UPDATE " + category +" SET " +
                        ArticleContract.EntertainmentEntry.COLUMN_ISIMAGEDOWNLOADED + " = 1 WHERE " +
                            ArticleContract.EntertainmentEntry._ID + " = " + al_Article.get(i).getId() + ";");
                }
                catch(IOException e) {
                    System.out.println(e);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Fragment_Others_RecyclerViewAdapter adapter = new Fragment_Others_RecyclerViewAdapter(context,al_Article,top_al_Article,tabNo);

            recyclerView.setAdapter(adapter);
        }
    }
}
