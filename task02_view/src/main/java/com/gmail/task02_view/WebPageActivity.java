package com.gmail.task02_view;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class WebPageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private WebView webView;
    private Button buttonPreviousPage;
    private Button buttonOpenInBrowser;
    private String currentUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);

        webView = findViewById(R.id.web_view);
        WebViewClient webViewClient = new WebViewClient() {
            @SuppressWarnings("deprecation") @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                currentUrl = url;
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(currentUrl);
                }
                return true;
            }

            @TargetApi(Build.VERSION_CODES.N) @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                currentUrl = webView.getUrl();
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(currentUrl);
                }
                return true;
            }
        };
        webView.setWebViewClient(webViewClient);
        webView.loadUrl("https://google.com");


        toolbar = findViewById(R.id.toolbar_WebView);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }

        buttonPreviousPage = findViewById(R.id.buttonPreviousPage);
        buttonPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoBack()) {
                    webView.goBack();
                } else {
                    onBackPressed();
                }
            }
        });

        buttonOpenInBrowser = findViewById(R.id.buttonOpenInBrowser);
        buttonOpenInBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webPage = Uri.parse(currentUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}