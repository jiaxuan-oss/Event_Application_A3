package edu.monash.tehjiaxuan_assignment1.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.monash.tehjiaxuan_assignment1.CategoryEntity;
import edu.monash.tehjiaxuan_assignment1.EventEntity;

public class EventRepository {
    // private class variable to hold reference to DAO
    private EMADAO emaDAO;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<EventEntity>> allEventsLiveData;

    // constructor to initialise the repository class
    EventRepository(Application application) {
        // get reference/instance of the database
        EMADatabase db = EMADatabase.getDatabase(application);

        // get reference to DAO, to perform CRUD operations
        emaDAO = db.EMADao();

        // once the class is initialised get all the items in the form of LiveData
        allEventsLiveData = emaDAO.getAllEventItems();
    }

    /**
     * Repository method to get all cards
     * @return LiveData of type List<Item>
     */
    LiveData<List<EventEntity>> getAllCards() {
        return allEventsLiveData;
    }


    void insert(EventEntity event) {
        EMADatabase.databaseWriteExecutor.execute(() -> emaDAO.addItem(event));
    }
    void deleteAll(){
        EMADatabase.databaseWriteExecutor.execute(() -> emaDAO.deleteAllEvent());
    }

    void update(EventEntity event){
        EMADatabase.databaseWriteExecutor.execute(() -> emaDAO.updateEvent(event));
    }

    void delete(EventEntity event){
        EMADatabase.databaseWriteExecutor.execute(() -> emaDAO.deleteEvent(event.getEventID()));
    }
}
