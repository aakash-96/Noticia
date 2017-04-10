package com.example.android.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by HP PC on 15-02-2017.
 */

public class Fragment_Others_ViewPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Context context;

    ArrayList<Article> top_al_Article;

    public static int[] image_resource_id = {R.drawable.sample_0,R.drawable.sample_1,R.drawable.sample_2,
            R.drawable.sample_3, R.drawable.sample_4,R.drawable.sample_5};


    public Fragment_Others_ViewPagerAdapter(Context cxt,ArrayList<Article> top_al_Article)
    {
        super();
        this.top_al_Article = top_al_Article;
        context = cxt;
    }

    @Override
    public int getCount() {
        return top_al_Article.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.from(context).inflate(R.layout.fragment_others_viewpager_layout,container,false);

        Article temp = top_al_Article.get(position);
        String title = temp.getTitle();
        String url_img = temp.getUrlToImage();
        ImageView imageView = (ImageView) view.findViewById(R.id.fragment_others_viewpager_layout_imageview);
//        imageView.setImageAlpha(200);
//        imageView.setImageResource(image_resource_id[position]);
        Picasso.with(context).load(url_img).into(imageView);

        TextView textView = (TextView) view.findViewById(R.id.fragment_others_viewpager_layout_title);
        textView.setText(title);

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, WebViewActivity.class);
//                intent.putExtra("url", top_al_Article.get(position - 1).getUrl());
//                startActivity(context,intent);
//            }
//        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return( view == (FrameLayout)object);
    }
}

