package edu.monash.tehjiaxuan_assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class EventGoogleResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_search_result_appbar);
        // using the ID set in previous step, get reference to the WebView
        WebView webView = findViewById(R.id.webView);

// get country name from Intent
        String name = getIntent().getExtras().getString("Name");


// compile the Wikipedia URL, which will be used to load into WebView
        String wikiPageURL = "https://www.google.com/search?q=" + name;

// set new WebView Client for the WebView
// This gives the WebView ability to be load the URL in the current WebView
// instead of navigating to default web browser of the device
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(wikiPageURL);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.searchresulttoolbar);
        setSupportActionBar(myToolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.goback);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            Intent intentNewEvent = new Intent(getApplicationContext(), ListEvent.class);
            startActivity(intentNewEvent);

        }
        return true;
    }
}