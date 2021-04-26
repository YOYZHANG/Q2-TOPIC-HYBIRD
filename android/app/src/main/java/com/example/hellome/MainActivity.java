package com.example.hellome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        myWebView = (WebView) findViewById(R.id.webview);

        myWebView.setWebViewClient(new offLineWebClient(getApplicationContext()));
        myWebView.setWebChromeClient(new WebChromeClient());

        myWebView.loadUrl("https://injectionindex.html");
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        JsBridge.register("JsBridge", NativeMethods.class);
        myWebView.addJavascriptInterface(new JsBridge(myWebView), "_jsbridge");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR
        }, 0);
    }
}

