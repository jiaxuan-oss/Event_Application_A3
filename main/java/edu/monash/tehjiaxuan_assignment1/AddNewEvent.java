package edu.monash.tehjiaxuan_assignment1;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.monash.tehjiaxuan_assignment1.provider.CategoryViewModel;
import edu.monash.tehjiaxuan_assignment1.provider.EventViewModel;

public class AddNewEvent extends AppCompatActivity {
    EditText eventIDET, eventNameET, categoryIDET, ticketET;
    String eventIDStr, categoryIDStr, eventNameStr, ticketAvailableStr;
    int ticketsAvailableInt;
    Boolean isActiveBool;
    Switch isActiveSwitch;
    MyBroadCastReceiver myBroadCastReceiver;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FloatingActionButton fab;
    FragmentManager fragmentManager;
    FragmentListCategory fragmentListCategory;
    CategoryViewModel categoryViewModel;
    EventViewModel eventViewModel;
    TextView tvGesture;
    List<CategoryEntity> categoryEntityArrayList = new ArrayList<>();

    // help detect basic gestures like scroll, single tap, double tap, etc
    private GestureDetectorCompat mDetector;

    // help detect multi touch gesture like pinch-in, pinch-out specifically used for zoom functionality
    private ScaleGestureDetector mScaleDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        navigationView = findViewById(R.id.nav_view);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingPointClick();
            }
        });
        fragmentManager = getSupportFragmentManager();
        fragmentListCategory = new FragmentListCategory();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new FragmentListCategory()).addToBackStack("f1").commit();

        navigationView.setNavigationItemSelectedListener(new MyNavitgationHandler());
        drawerLayout = findViewById(R.id.drawer_layout);

        eventIDET = findViewById(R.id.ETEventID);
        eventNameET = findViewById(R.id.ETEventName);
        categoryIDET = findViewById(R.id.ETAddCategoryID);
        ticketET = findViewById(R.id.ETTicketAvailable);
        isActiveSwitch = findViewById(R.id.SwitchAddIsActive);
        tvGesture = findViewById(R.id.tvGesture);
        // initialise new instance of CustomGestureDetector class
        CustomGestureDetector customGestureDetector = new CustomGestureDetector();

// register GestureDetector and set listener as CustomGestureDetector
        mDetector = new GestureDetectorCompat(this, customGestureDetector);

        mDetector.setOnDoubleTapListener(customGestureDetector);

        //request user permission to send sms
        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS
        }, 0);
        //create broadcast receiver object
        myBroadCastReceiver = new MyBroadCastReceiver();
        /*
         * Register the broadcast handler with the intent filter that is declared in
         * */
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        categoryViewModel.getCategoryCards().observe(this, newData -> {
            categoryEntityArrayList = newData;
        });
        View touchpad = findViewById(R.id.touchpad);
        touchpad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);

                float rawX = event.getRawX();
                float rawY = event.getRawY();
                return true;
            }
        });

    }

    public void floatingPointClick() {
        categoryIDStr = categoryIDET.getText().toString();
        boolean saveSuccess = saveEvent();
        if (saveSuccess) {
            String messageSuccess = String.format("Event saved: %s to %s", eventIDET.getText().toString(), categoryIDET.getText().toString());
            Snackbar.make(this.getCurrentFocus(), messageSuccess, Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            eventViewModel.undoInsert();
                            updateCategoryEventCount(categoryIDStr, false);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new FragmentListCategory()).addToBackStack("f1").commit();

                        }
                    }).show();
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        unregisterReceiver(this.myBroadCastReceiver);
    }

    @Override
    public void onResume(){
        super.onResume();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);
    }

    public boolean saveEvent(){
        categoryViewModel.getCategoryCards().observe(this, newData -> {
            categoryEntityArrayList = newData;
        });
        //get user input
        categoryIDStr = categoryIDET.getText().toString();
        eventNameStr = eventNameET.getText().toString();
        isActiveBool = isActiveSwitch.isChecked();
        ticketAvailableStr = ticketET.getText().toString();

        if (ticketAvailableStr.equals("")){ //to avoid error like converting null to int
            ticketET.setText("0");
            ticketAvailableStr = "0";
            ticketsAvailableInt = 0;
            Toast.makeText(this, "Invalid Ticket Number, set to 0", Toast.LENGTH_SHORT).show();
        }

        else if(!ticketAvailableStr.equals("")) {
            ticketsAvailableInt = Integer.parseInt(ticketAvailableStr);
            if (ticketsAvailableInt < 0) {
                ticketsAvailableInt = 0;
                ticketET.setText("0");
                ticketAvailableStr = "0";
                Toast.makeText(this, "Invalid Ticket Number, set to 0", Toast.LENGTH_SHORT).show();
            }
        }


        String alphanumeric = "^(?=.*[A-Za-z])[A-Za-z\\d ]+$";


        //avoid empty input
        if(categoryIDStr.isEmpty() || eventNameStr.isEmpty()){
            Toast.makeText(getApplicationContext(), "Event Name and Category ID is required!", Toast.LENGTH_SHORT).show();
        }

        else if(!eventNameStr.matches(alphanumeric)){
            Toast.makeText(this, "Invalid event name", Toast.LENGTH_SHORT).show();
        }

        else if(!checkCategoryId(categoryIDStr)){
            Toast.makeText(this, "Category does not exist", Toast.LENGTH_SHORT).show();
        }


        else{
            //get random event ID
            eventIDStr = randomEventID();
            eventIDET.setText(eventIDStr); //set
            //giving success toast
            addItem(eventIDStr, categoryIDStr, eventNameStr, ticketsAvailableInt, isActiveBool);
            updateCategoryEventCount(categoryIDStr, true); //true for increment
            return true;
        }
        return false;
    }

    public void addItem(String eventID, String categoryID, String eventName, int ticketAvailable, Boolean isActive) {
        EventEntity eventEntity = new EventEntity(eventID,eventName, categoryID,ticketAvailable, isActive);
        eventViewModel.insert(eventEntity);
    }

    public boolean checkCategoryId(String categoryId) {
        for (CategoryEntity categoryEntity : categoryEntityArrayList) {
            if (categoryEntity.getCatId().equals(categoryId)) {
                return true;
            }
        }
        return false;
    }

    public void updateCategoryEventCount(String categoryId, boolean increOrDecre) { //true for increment false for decrement
        for (CategoryEntity categoryEntity : categoryEntityArrayList) {
            if (categoryEntity.getCatId().equals(categoryId) && increOrDecre) {
                categoryEntity.setEventCount(categoryEntity.getEventCount() + 1);
                categoryViewModel.updateCategory(categoryEntity);
            }else if (categoryEntity.getCatId().equals(categoryId) && !increOrDecre) {
                categoryEntity.setEventCount(categoryEntity.getEventCount() - 1);
                categoryViewModel.updateCategory(categoryEntity);
            }
        }
    }
    public char randomChar(){
        //getting random char
        Random random = new Random();
        int randomNumber = random.nextInt(26); //getting random number between 0 to 26
        //65 is A in ascii, randomly pick any alphabet
        randomNumber += 65;
        return (char)randomNumber;
    }

    public String randomEventID(){
        //random event starts with E
        String randomStr = "E" + randomChar() + randomChar() + "-";
        Random random = new Random();
        //10000-99999 random number
        String randigit = String.valueOf(random.nextInt(90000) + 10000);
        randomStr += randigit;
        return randomStr;
    }
//    private void saveDataToSharedPreference(String eventID, String categoryID, String eventName, int ticketAvai, Boolean isActive){//double confirm
//        Gson gson = new Gson();
//        ArrayList<EventEntity> eventEntityArrayList = new ArrayList<>();
//        SharedPreferences sharedPreferences = getSharedPreferences("EVENT_LIST", MODE_PRIVATE);
//        String eventEntities = sharedPreferences.getString("SAVE_EVENT_LIST", "[]");
//
//        if (!eventEntities.equals("\"[]\"")){
//            Type type = new TypeToken<ArrayList<EventEntity>>(){}.getType();
//            eventEntityArrayList =gson.fromJson(eventEntities, type);
//
//        }
//        EventEntity eventEntity = new EventEntity(eventID,eventName, categoryID,ticketAvai, isActive);
//        eventEntityArrayList.add(eventEntity);
//        String eventCategoryList = gson.toJson(eventEntityArrayList);
//
//        // use .edit function to access file using Editor variable
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("SAVE_EVENT_LIST", eventCategoryList);
//
//        // use editor.apply() to save data to the file asynchronously (in background without freezing the UI)
//        // doing in background is very common practice for any File Input/Output operations
//        editor.apply();
//
//    }

    public boolean checkValidForm(String msg) {
        //split the string by ; include the empty string
        String[] semicolon = msg.split(";",-1);

        //if it doesnt have 4 parts return false
        if (semicolon.length != 4) {
            return false;
        }

        else {
            //create attribute from the 4 parts
            String eventNameCheck = semicolon[0];
            String categoryIDCheck = semicolon[1];
            String ticketCheck = semicolon[2];
            String isActiveCheck = semicolon[3].toUpperCase();

            //if is empty then reject
            if(eventNameCheck.isEmpty()){
                return false;
            }
            //if it is not true not false and empty then reject
            if (!isActiveCheck.equals("TRUE") && !isActiveCheck.equals("FALSE") && !isActiveCheck.isEmpty()) {
                return false;
            }
            //if ticket check is not integer or is empty then reject
            if (!isPosInteger(ticketCheck) && !ticketCheck.isEmpty()) {
                return false;
            }
            //if categoryID is empty or it is not start with C then reject
            if (categoryIDCheck.isEmpty() || !(categoryIDCheck.charAt(0) == 67)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPosInteger(String str) {
        //if is null then return false
        if (str == null || str.equals("0") || str.isEmpty()) {
            return false;
        }
        //loop through all the char check if it is int  only
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.ClearEventForm){
            clearFields();
        }
        else if (item.getItemId() == R.id.Refresh){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new FragmentListCategory()).addToBackStack("f1").commit();

        }

        else if (item.getItemId() == R.id.DeleteAllCategories){
            categoryViewModel.deleteAll();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new FragmentListCategory()).addToBackStack("f1").commit();

        }

        else if (item.getItemId() == R.id.DeleteAllEvents){
            eventViewModel.deleteAll();
        }

        else if(item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);

        }
        return true;
    }

    public void clearFields(){
        eventIDET.setText("");
        eventNameET.setText("");
        categoryIDET.setText("");
        ticketET.setText("");
        isActiveSwitch.setChecked(false);
    }

    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            tvGesture.setText("onLongPress");
            clearFields();
            super.onLongPress(e);
        }
        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            tvGesture.setText("onDoubleTap");
            floatingPointClick();
            return super.onDoubleTap(e);
        }

    }

    class MyNavitgationHandler implements NavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();

            if (id == R.id.NewEventCategory) {
                Intent intentNewEvent = new Intent(getApplicationContext(), NewEventCategory.class);
                startActivity(intentNewEvent);

            } else if (id == R.id.ListEvent) {
                Intent intentNewEvent = new Intent(getApplicationContext(), ListEvent.class);
                startActivity(intentNewEvent);
            }
            else if (id == R.id.LogOut) {
                Intent intentNewEvent = new Intent(getApplicationContext(), LoginView.class);
                intentNewEvent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentNewEvent);
            }
            else if (id == R.id.ListCategoryActivity) {
                Intent intentNewEvent = new Intent(getApplicationContext(), ListCategory.class);
                startActivity(intentNewEvent);
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            // tell the OS
            return true;
        }
    }


    class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            /*
             * Retrieve the message from the intent
             * */
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            //split it with :
            String[] removeColon = msg.split(":");
            //copy all the tokens only, to avoid edge cases like "category:Melbourne:20:TRUE;CME-1084;;";
            String[] preprocessRemoveColon = Arrays.copyOfRange(removeColon, 1, removeColon.length);
            //join back all the tokens with :
            String allPartsContain = String.join(":", preprocessRemoveColon);

            //check user input and the command is correct
            if (checkValidForm(allPartsContain) && removeColon[0].equals("event")) {
                //create an array to store all the token after checking
                String[] token = allPartsContain.split(";",-1);

                String nameCheck = token[0];
                eventNameET.setText(nameCheck);

                String categoryIDCheck = token[1];
                categoryIDET.setText(categoryIDCheck);

                String ticketCheck = token[2];
                ticketET.setText(ticketCheck);

                String isActiveCheck = token[3].toUpperCase();
                if (!isActiveCheck.isEmpty()) {
                    if (isActiveCheck.equals("TRUE")) {
                        isActiveBool = true;
                    } else {
                        isActiveBool = false;
                    }
                    isActiveSwitch.setChecked(isActiveBool);
                }
                else {
                    isActiveSwitch.setChecked(false);
                }

            }
            else {
                Toast.makeText(getApplicationContext(), "Invalid Command", Toast.LENGTH_SHORT).show();
            }
        }
    }

}



