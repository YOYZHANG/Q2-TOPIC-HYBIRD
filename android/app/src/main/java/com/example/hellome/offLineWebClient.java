package com.example.hellome;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

import java.io.IOException;

public class offLineWebClient extends WebViewClient {
    private Context myContext;
    private static final String INJECTION_TOKEN = "injection";

    public offLineWebClient(Context context) {
        this.myContext = context;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        WebResourceResponse response = super.shouldInterceptRequest(view, request);
        final String url = request.getUrl().toString();
        if (url !=null && url.contains(INJECTION_TOKEN)) {
            int text = url.indexOf(INJECTION_TOKEN);
            String assetPath = url.substring(url.indexOf(INJECTION_TOKEN) + INJECTION_TOKEN.length(), url.length() - 1);
            Log.e("assetPath", assetPath);
            try {
                response = new WebResourceResponse(
                        getMimeType(url),
                        "UTF-8",
                        myContext.getAssets().open(assetPath)
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response;
    }

    private String getMimeType(String url) {
        String mimetype = "";
        if (url.contains(".js")) {
            mimetype = "application/javascript";
        }
        else if (url.contains(".html")) {
            mimetype = "text/html";
        }

        return mimetype;
    }
}