package edu.monash.tehjiaxuan_assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

public class ListCategory extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentListCategory fragmentListCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_category_app_bar);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.ListCategoryAppBar);
        setSupportActionBar(myToolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.goback);
        fragmentManager = getSupportFragmentManager();
        fragmentListCategory = new FragmentListCategory();

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