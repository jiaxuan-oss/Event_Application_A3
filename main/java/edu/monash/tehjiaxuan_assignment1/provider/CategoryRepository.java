package edu.monash.tehjiaxuan_assignment1.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.monash.tehjiaxuan_assignment1.CategoryEntity;
import edu.monash.tehjiaxuan_assignment1.EventEntity;

public class CategoryRepository {
    // private class variable to hold reference to DAO
    // private class variable to hold reference to DAO
    private EMADAO emaDAO;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<CategoryEntity>> allCategoryLiveData;

    // constructor to initialise the repository class
    CategoryRepository(Application application) {
        // get reference/instance of the database
        EMADatabase db = EMADatabase.getDatabase(application);

        // get reference to DAO, to perform CRUD operations
        emaDAO = db.EMADao();

        // once the class is initialised get all the items in the form of LiveData
        allCategoryLiveData = emaDAO.getAllCatItems();
    }

    /**
     * Repository method to get all cards
     * @return LiveData of type List<Item>
     */
    LiveData<List<CategoryEntity>> getAllCards() {
        return allCategoryLiveData;
    }


    void insert(CategoryEntity category) {
        EMADatabase.databaseWriteExecutor.execute(() -> emaDAO.addItem(category));
    }

    void deleteAll(){
        EMADatabase.databaseWriteExecutor.execute(() -> emaDAO.deleteAllCategory());

    }

    void update(CategoryEntity category){
        EMADatabase.databaseWriteExecutor.execute(() -> emaDAO.updateCategory(category));
    }
}
