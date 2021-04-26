package com.example.hellome;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;

public class JsBridge {
    private WebView myWebview;
    public JsBridge(WebView webview) {
        this.myWebview = webview;
    }

    private static Map<String, HashMap<String, Method>> exposedMethods = new HashMap<>();

    public static void register(String exposedName, Class<?> classz) {
        if (!exposedMethods.containsKey(exposedName)) {
            Log.e("nativeMethods", classz.toString());
            exposedMethods.put(exposedName, getAllMethods(classz));
        }
    }

    private static HashMap<String, Method> getAllMethods(Class injectedCls) {
        HashMap<String, Method> methodHashMap = new HashMap<>();
        Method[] methods = injectedCls.getDeclaredMethods();
        Log.e("declared methods", methods.toString());
        for (Method method: methods) {
            if (method.getModifiers() != (Modifier.PUBLIC | Modifier.STATIC) || method.getName() == null) {
                continue;
            }
            Class[] parameters = method.getParameterTypes();
            if (parameters != null && parameters.length == 3) {
                if (parameters[0] == WebView.class || parameters[1] == JSONObject.class || parameters[2] == Callback.class) {
                    methodHashMap.put(method.getName(), method);
                }
            }
        }

        return methodHashMap;
    }

    @JavascriptInterface
    public String call(String methodName, String args) {
        try {
            JSONObject _args = new JSONObject(args);
            JSONObject arg = new JSONObject(_args.getString("data"));
            String cbName = _args.getString("cbName");

            if (exposedMethods.containsKey("JsBridge")) {
                HashMap<String, Method> methodHashMap = exposedMethods.get("JsBridge");
                if (methodHashMap != null && methodHashMap.size() != 0 && methodHashMap.containsKey(methodName)){
                    Method method = methodHashMap.get(methodName);
                    method.invoke(null, myWebview, arg, new CallBack(cbName, myWebview));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
