package edu.monash.tehjiaxuan_assignment1.provider;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.metrics.Event;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.monash.tehjiaxuan_assignment1.CategoryEntity;
import edu.monash.tehjiaxuan_assignment1.EventEntity;

public class EventViewModel extends AndroidViewModel {
    // reference to CardRepository
    private EventRepository repository;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<EventEntity>> allEventLiveData;

    EventEntity lastInsert;

    public EventViewModel(@NonNull Application application) {
        super(application);

        // get reference to the repository class
        repository = new EventRepository(application);

        // get all items by calling method defined in repository class
        allEventLiveData = repository.getAllCards();
    }

    /**
     * ViewModel method to get all cards
     * @return LiveData of type List<Item>
     */
    public LiveData<List<EventEntity>> getEventCards() {
        return allEventLiveData;
    }


    public void insert(EventEntity event) {
        lastInsert = event;
        repository.insert(event);
    }

    public void deleteAll(){ repository.deleteAll();}

    public void updateEvent(EventEntity event){
        repository.update(event);
    }

    public void undoInsert(){
        if (lastInsert != null){
            repository.delete(lastInsert);
            lastInsert = null;
        }
    }

}
