package com.example.android.newsfeed;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.android.newsfeed.Utils.NetworkUtils;

/**
 * Created by HP PC on 09-04-2017.
 */

public class WebViewActivity extends Activity {
    private WebView webView;
    String url = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_content);

        url = getIntent().getStringExtra("url");

        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);

        if (NetworkUtils.haveNetworkConnection(getBaseContext())) {
            webView.getSettings().setAppCacheMaxSize( 10 * 1024 * 1024 ); // 10MB
            webView.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
            webView.getSettings().setAllowFileAccess( true );
            webView.getSettings().setAppCacheEnabled( true );
            webView.getSettings().setJavaScriptEnabled( true );
            webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT );
            webView.loadUrl(url);
        }
        else
        {
            webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        }

    }
}
