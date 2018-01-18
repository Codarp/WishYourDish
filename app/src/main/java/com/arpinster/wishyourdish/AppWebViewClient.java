package com.arpinster.wishyourdish;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by Arpit on 6/11/2017.
 */
public class AppWebViewClient extends WebViewClient {

    private ProgressBar progressBar;

    public AppWebViewClient(ProgressBar progressBar) {
        this.progressBar=progressBar;
        progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // TODO Auto-generated method stub
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        // TODO Auto-generated method stub
        super.onPageFinished(view, url);
        progressBar.setVisibility(View.GONE);
    }
}
