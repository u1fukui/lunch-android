package com.u1fukui.lunch.app.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.u1fukui.lunch.app.R;

public class TabelogActivity extends FragmentActivity {

  public static final String EXTRA_URL = "tabelog_url";

  private ProgressBar mProgressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tabelog);

    WebView webView = (WebView) findViewById(R.id.webview);
    mProgressBar = (ProgressBar) findViewById(R.id.progress);

    String url = getIntent().getStringExtra(EXTRA_URL);
    webView.setWebViewClient(new CustomWebViewClient());
    webView.loadUrl(url);
  }

  private class CustomWebViewClient extends WebViewClient {
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
      super.onPageStarted(view, url, favicon);
      mProgressBar.setVisibility(View.VISIBLE);
    }

    public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      mProgressBar.setVisibility(View.GONE);
    }
  }
}
