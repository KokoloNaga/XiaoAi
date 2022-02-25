package com.example.xiaoai.main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.xiaoai.R;

public class GraphActivity extends AppCompatActivity {
    private WebView mWv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");

        SharedPreferences sp = getSharedPreferences("now", Context.MODE_PRIVATE);
        String saveId = sp.getString("id","");

        url = url + saveId + "/";

        mWv = findViewById(R.id.wv_knowledge);
        mWv.getSettings().setJavaScriptEnabled(true);
        mWv.loadUrl(url);
        mWv.setWebViewClient(new GraphActivity.MyWebViewClient());
        mWv.setWebChromeClient(new GraphActivity.MyWebChromeClient());

    }

    class MyWebViewClient extends WebViewClient {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }
    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK)&&(mWv.canGoBack())){
            mWv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}