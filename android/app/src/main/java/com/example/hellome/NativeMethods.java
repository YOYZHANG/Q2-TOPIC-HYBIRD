package com.example.hellome;

import android.os.Build;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

public class NativeMethods {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void showToast(WebView webview, JSONObject arg, CallBack callBack) {
        String message = arg.optString("msg");
        Toast.makeText(webview.getContext(), message, Toast.LENGTH_SHORT).show();

        if (callBack != null) {
            try {
                JSONObject result = new JSONObject();
                result.put("msg", "js 调用 native 成功!");
                callBack.apply(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
