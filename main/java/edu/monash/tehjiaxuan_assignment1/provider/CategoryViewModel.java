package edu.monash.tehjiaxuan_assignment1.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.monash.tehjiaxuan_assignment1.CategoryEntity;
import edu.monash.tehjiaxuan_assignment1.EventEntity;

public class CategoryViewModel extends AndroidViewModel {
    // reference to CardRepository
    private CategoryRepository repository;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<CategoryEntity>> allCategoryLiveData;

    public CategoryViewModel (@NonNull Application application) {
        super(application);

        // get reference to the repository class
        repository = new CategoryRepository(application);

        // get all items by calling method defined in repository class
        allCategoryLiveData = repository.getAllCards();
    }

    /**
     * ViewModel method to get all cards
     * @return LiveData of type List<Item>
     */
    public LiveData<List<CategoryEntity>> getCategoryCards() {
        return allCategoryLiveData;
    }

    public void setEventCards(LiveData<List<CategoryEntity>> data){allCategoryLiveData = data;}


    public void deleteAll(){
        repository.deleteAll();
    }
    public void insert(CategoryEntity category) {
        repository.insert(category);
    }

    public void updateCategory(CategoryEntity category){
        repository.update(category);
    }
}
