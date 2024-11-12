package edu.monash.tehjiaxuan_assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class ListEvent extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentListEvent fragmentListEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_page_app_bar);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.EventAppBar);
        setSupportActionBar(myToolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.goback);
        fragmentManager = getSupportFragmentManager();
        fragmentListEvent = new FragmentListEvent();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView3, new FragmentListEvent()).addToBackStack("f1").commit();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        if(item.getItemId() == android.R.id.home){
            Intent intentNewEvent = new Intent(getApplicationContext(), AddNewEvent.class);
            startActivity(intentNewEvent);

        }
        return true;
    }
}