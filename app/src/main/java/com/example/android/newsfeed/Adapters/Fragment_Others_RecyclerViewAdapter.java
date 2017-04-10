package com.example.android.newsfeed;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.newsfeed.Article;
import com.example.android.newsfeed.Data.ArticleContract;
import com.example.android.newsfeed.Data.ArticleDbHelper;
import com.example.android.newsfeed.Fragments.Fragment_Others;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by HP PC on 15-02-2017.
 */

public class Fragment_Others_RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    Activity activity;
    ArrayList<Article> al_Article;
    ArrayList<Article> top_al_Article;
    String path = "/data/user/0/com.example.android.newsfeed/app_Entertainment_Images";
    private static int tabNo;
    String category = "";
    private static String dir = "";

    public Fragment_Others_RecyclerViewAdapter(Context cxt, ArrayList<Article> al_Article,ArrayList<Article> top_al_Article,int tabNo)
    {
        this.tabNo = tabNo;
        this.al_Article = al_Article;
        this.top_al_Article = top_al_Article;
        context = cxt;
        activity = (Activity) cxt;
        switch (tabNo)
        {
            case 0: //Home
            case 1: //Entertainment
                category = "ENTERTAINMENT";
                dir = "Entertainment_Images";
                break;
            case 2: //Technology
                category = "TECHNOLOGY";
                dir = "Technology_Images";
                break;
            case 3: //Sports
                category = "SPORTS";
                dir = "Sports_Images";
                break;
            case 4: //Business
                category = "BUSINESS";
                dir = "Business_Images";
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return 0;    //for viewPager
        else
            return 1;    //for other listitems
    }

    @Override
    public int getItemCount() {
        return al_Article.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        switch (viewType)
        {
            case 0:     // for viewPager
                Log.i("checking","Recycler VP: size- "+top_al_Article.size());
                Log.i("checking","Recycler VP: "+top_al_Article.get(1).getTitle());
                View view_pager = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_others_viewholder_viewpager,parent,false);
                viewHolder = new com.example.android.newsfeed.Fragment_Others_Viewholder_Viewpager(view_pager,context,top_al_Article);
                break;
            default:
                View list_items = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_others_viewholder_listitems,parent,false);
                viewHolder = new com.example.android.newsfeed.Fragment_Others_Viewholder_Listitems(list_items,context);
//                parent.addView(v1);
        }
        return viewHolder;
    }

    public static String saveToInternalStorage(Bitmap bitmapImage, Context context, long id){

        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(dir, Context.MODE_PRIVATE);
        // Create imageDir
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

    public class Image_Download_AsyncTask extends AsyncTask<Article, Void, Void>
    {
        @Override
        protected Void doInBackground(Article... article)
        {
            try
            {
                URL temp_url = new URL(article[0].getUrlToImage());

                HttpURLConnection connection = (HttpURLConnection) temp_url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap image = BitmapFactory.decodeStream(input);

                saveToInternalStorage(image, context, article[0].getId());
                article[0].set_Image_Downloaded(true);

                Log.i("Rec","Image downloaded");
                        ArticleDbHelper articleDbHelper = new ArticleDbHelper(context);
                        SQLiteDatabase db = articleDbHelper.getReadableDatabase();
                        db.execSQL("UPDATE " + category +" SET " +
                                ArticleContract.EntertainmentEntry.COLUMN_ISIMAGEDOWNLOADED + " = 1 WHERE " +
                                ArticleContract.EntertainmentEntry._ID + " = " + article[0].getId() + ";");
            }
            catch(IOException e) {
                System.out.println(e);
            }
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType())
        {
            case 0:
                com.example.android.newsfeed.Fragment_Others_Viewholder_Viewpager viewHolder_viewPager
                        = (com.example.android.newsfeed.Fragment_Others_Viewholder_Viewpager) holder;
//                ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(context);
//                if(vh2==null)
//                    Log.i("adapter","null");
//
//                vh2.viewPager.setAdapter(pagerAdapter);
                break;

            default:
                Article current_Article = al_Article.get(position-1);
                com.example.android.newsfeed.Fragment_Others_Viewholder_Listitems viewholder_listitems
                        = (com.example.android.newsfeed.Fragment_Others_Viewholder_Listitems) holder;

                viewholder_listitems.title.setText(current_Article.getTitle());
                viewholder_listitems.author.setText(current_Article.getAuthor());
                viewholder_listitems.date.setText(current_Article.getDate());


                Picasso.with(context).load(current_Article.getUrlToImage()).into(viewholder_listitems.imageView);

//                //for the imageview
//                String sqlQuery = "";
//                switch (tabNo)
//                {
//                    case 0: //Home
//                    case 1: //Entertainment
//                        sqlQuery = "SELECT " + ArticleContract.EntertainmentEntry.COLUMN_ISIMAGEDOWNLOADED + " FROM " +
//                                ArticleContract.EntertainmentEntry.TABLE_NAME + " WHERE " + ArticleContract.EntertainmentEntry._ID +
//                                " = " + current_Article.getId() + ";";
//                        break;
//                    case 2: //Technology
//                        sqlQuery = "SELECT " + ArticleContract.TechnologyEntry.COLUMN_ISIMAGEDOWNLOADED + " FROM " +
//                                ArticleContract.TechnologyEntry.TABLE_NAME + " WHERE " + ArticleContract.TechnologyEntry._ID +
//                                " = " + current_Article.getId() + ";";
//                        break;
//                    case 3: //Sports
//                        sqlQuery = "SELECT " + ArticleContract.SportsEntry.COLUMN_ISIMAGEDOWNLOADED + " FROM " +
//                                ArticleContract.SportsEntry.TABLE_NAME + " WHERE " + ArticleContract.SportsEntry._ID +
//                                " = " + current_Article.getId() + ";";
//                        break;
//                    case 4: //Business
//                        sqlQuery = "SELECT " + ArticleContract.BusinessEntry.COLUMN_ISIMAGEDOWNLOADED + " FROM " +
//                                ArticleContract.BusinessEntry.TABLE_NAME + " WHERE " + ArticleContract.BusinessEntry._ID +
//                                " = " + current_Article.getId() + ";";
//                }
//
////                if(current_Article.is_Image_Downloaded == false)
////                {
////                    Article temp[] = new Article[2];
////                    temp[0] = current_Article;
////                    Image_Download_AsyncTask temp_task = new Image_Download_AsyncTask();
////                    temp_task.execute(temp);
////                }
//
//                ArticleDbHelper articleDbHelper = new ArticleDbHelper(context);
//                SQLiteDatabase db = articleDbHelper.getReadableDatabase();
//                Cursor cursor = db.rawQuery(sqlQuery,null);
//                cursor.moveToFirst();
//                String temp= "";
//                while (cursor.isAfterLast() == false) {
//                    temp = cursor.getString(0);
//                    cursor.moveToNext();
//                }
//                cursor.close();
//
//                if(temp.endsWith("1"))
//                {
//                    Log.i("Rec","Loading the saved images");
//                    //Loading the saved images
//                    File f = new File(path, "" + current_Article.getId());
//                    Bitmap bm = null;
//                    try {
//                        bm = BitmapFactory.decodeStream(new FileInputStream(f));
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//
//                    viewholder_listitems.imageView.setImageBitmap(bm);
//                }
////                else{
////                    Log.i("Rec","image was not downloaded.");
////                    Article temps[] = new Article[2];
////                    temps[0] = current_Article;
////                    Image_Download_AsyncTask temp_task = new Image_Download_AsyncTask();
////                    temp_task.execute(temps);
////                }
//                Picasso.with(context).load(current_Article.getUrlToImage()).into(viewholder_listitems.imageView);
        }
    }
}

