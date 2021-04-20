package com.example.hellome;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.FileUtils;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.InputStream;

public class offLineWebClient extends WebViewClient {
    private Context myContext;

    public offLineWebClient(Context context) {
        this.myContext = context;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view,
                                                      WebResourceRequest request) {
        final String url = request.getUrl().toString();
        WebResourceResponse resourceResponse = getWebResourceResponse(url);

        if (resourceResponse == null) {
            return super.shouldInterceptRequest(view, request);
        }

        return resourceResponse;
    }

    private WebResourceResponse getWebResourceResponse(String url) {
        try {
            WebResourceResponse resourceResponse = getCssWebResourceResponseFromAsset();
            return resourceResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Return WebResourceResponse with CSS markup from an asset (e.g. "assets/style.css").
     */
    private WebResourceResponse getCssWebResourceResponseFromAsset() {
        try {
            return getUtf8EncodedCssWebResourceResponse(myContext.getAssets().open("index.html"));
        } catch (IOException e) {
            Log.e("Error", e.toString());
            return null;
        }
    }


    private WebResourceResponse getUtf8EncodedCssWebResourceResponse(InputStream data) {
        return new WebResourceResponse("text/html", "UTF-8", data);
    }
}