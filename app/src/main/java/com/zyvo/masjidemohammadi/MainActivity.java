package com.zyvo.masjidemohammadi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView web;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        web = new WebView(this);
        setContentView(web);

        // runtime location permission (Android 6+) — without it WebView geolocation fails
        if (Build.VERSION.SDK_INT >= 23) {
            String[] perms = {"android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION"};
            boolean need = false;
            for (String p : perms)
                if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) need = true;
            if (need) requestPermissions(perms, 1);
        }

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setGeolocationEnabled(true);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);

        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest req) {
                Uri u = req.getUrl();
                String sc = u.getScheme();
                if ("tel".equals(sc)) {
                    startActivity(new Intent(Intent.ACTION_DIAL, u));
                    return true;
                }
                if ("mailto".equals(sc)) {
                    startActivity(new Intent(Intent.ACTION_SENDTO, u));
                    return true;
                }
                if ("http".equals(sc) || "https".equals(sc)) {
                    // open in the in-app browser so the main app stays as-is
                    Intent i = new Intent(MainActivity.this, WebActivity.class);
                    i.putExtra("url", u.toString());
                    startActivity(i);
                    return true;
                }
                return false; // stay inside the app
            }
        });

        // geolocation permission prompt → auto-grant (user sees Android prompt too)
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback cb) {
                cb.invoke(origin, true, false);
            }
        });
        web.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()) web.goBack();
        else super.onBackPressed();
    }
}
