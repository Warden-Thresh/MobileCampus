package com.warden.mobilecampus.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.warden.mobilecampus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.wv_news_detail)
    WebView wvNewsDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("详情");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        String url = getIntent().getStringExtra("url");
        wvNewsDetail.getSettings().setDomStorageEnabled(true);
        wvNewsDetail.getSettings().setJavaScriptEnabled(true);
//        wv_web_news.getSettings().setBlockNetworkImage(true);
        wvNewsDetail.getSettings().setSupportZoom(true);  //支持缩放
        wvNewsDetail.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        wvNewsDetail.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        wvNewsDetail.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        wvNewsDetail.getSettings().setNeedInitialFocus(true);//当webview调用requestFocus时为webview设置节点
        wvNewsDetail.getSettings().setBuiltInZoomControls(true);
        wvNewsDetail.getSettings().setUseWideViewPort(true);
        wvNewsDetail.getSettings().setAppCacheEnabled(true);//是否使用缓存
        wvNewsDetail.setWebChromeClient(new WebChromeClient());
        wvNewsDetail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wvNewsDetail.loadUrl(url);
        Log.d("url:",url);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        if (wvNewsDetail.canGoBack()) {
            wvNewsDetail.goBack();
            return;
        }
        finish();
    }
}
