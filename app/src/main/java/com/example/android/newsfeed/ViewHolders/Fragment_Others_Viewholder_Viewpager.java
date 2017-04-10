package com.example.android.newsfeed;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.android.newsfeed.Fragment_Others_ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by HP PC on 15-02-2017.
 */

public class Fragment_Others_Viewholder_Viewpager extends RecyclerView.ViewHolder {
    private Context context;
    private Activity activity;
    ArrayList<Article> top_al_Article;

    protected ViewPager viewPager;

    Timer timer;
    int page=0;
    int current_page=-1;

    public Fragment_Others_Viewholder_Viewpager(View view, Context cxt, ArrayList<Article> top_al_Article)
    {
        super(view);
        context = cxt;
        activity = (Activity) cxt;
        this.top_al_Article = top_al_Article;
        viewPager = (ViewPager) view.findViewById(R.id.fragment_others_viewholder_viewpager);

        Fragment_Others_ViewPagerAdapter adapter = new Fragment_Others_ViewPagerAdapter(context,top_al_Article);
        viewPager.setAdapter(adapter);

        pageSwitcher(4);
    }

    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
        // in
        // milliseconds
    }

    // this is an inner class...
    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.


            activity.runOnUiThread(new Runnable() {
                public void run() {
                    current_page = viewPager.getCurrentItem();
                    page = (current_page + 1) % top_al_Article.size();//viewPagerAdapter.image_resource_id.length;
                    viewPager.setCurrentItem(page,true);
                }
            });

        }
    }
}
