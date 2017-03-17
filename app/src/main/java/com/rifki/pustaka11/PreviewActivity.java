package com.rifki.pustaka11;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class PreviewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        Intent intent = getIntent();
        String namaFile = intent.getExtras().getString("namaFile");
        WebView webview = (WebView) findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);
        //String buku = "http://rivkiey.com/pustaka/file_buku/"+namaFile;
        String url_preview = intent.getExtras().getString("url_preview");

        //webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + buku);
        webview.loadUrl(url_preview);

    }
}
