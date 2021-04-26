package com.example.hellome;

import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

public class CallBack {
    private String cbName;
    private WebView webview;

    public CallBack(String cbName, WebView webview) {
        this.cbName = cbName;
        this.webview = webview;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void apply(JSONObject jsonObject) {
        if (webview != null) {
            webview.post(() -> {
                webview.evaluateJavascript("javascript:" + cbName + "(" + jsonObject.toString() + ")", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        return;
                    }
                });
            });
        }
    }
}
