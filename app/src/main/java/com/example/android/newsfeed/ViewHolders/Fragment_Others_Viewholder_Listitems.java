package com.example.android.newsfeed;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by HP PC on 15-02-2017.
 */

public class Fragment_Others_Viewholder_Listitems extends RecyclerView.ViewHolder {
    private Context context;
    private Activity activity;

    protected TextView title,author,date;
    protected ImageView imageView;
    Fragment_Others_Viewholder_Listitems(View view,Context cxt)
    {
        super(view);

        title = (TextView) view.findViewById(R.id.fragment_others_viewholder_listitems_title);
        author = (TextView) view.findViewById(R.id.fragment_others_viewholder_listitems_author);
        date = (TextView) view.findViewById(R.id.fragment_others_viewholder_listitems_date);
        imageView = (ImageView) view.findViewById(R.id.fragment_others_viewholder_listitems_image);
    }
}
