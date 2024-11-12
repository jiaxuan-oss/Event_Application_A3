package edu.monash.tehjiaxuan_assignment1.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.monash.tehjiaxuan_assignment1.CategoryEntity;
import edu.monash.tehjiaxuan_assignment1.EventEntity;

@Dao
public interface EMADAO {
    @Query("select * from `Event Category`")
    LiveData<List<CategoryEntity>> getAllCatItems();

    @Query("SELECT * FROM Event")
    LiveData<List<EventEntity>> getAllEventItems();

    @Insert
    void addItem(CategoryEntity item);
    @Insert
    void addItem(EventEntity item);

    @Query("DELETE FROM `Event Category`")
    void deleteAllCategory();

    @Query("DELETE FROM Event")
    void deleteAllEvent();
    @Update
    void updateCategory(CategoryEntity category);

    @Update
    void updateEvent(EventEntity event);

    @Query("DELETE FROM Event WHERE columnID = :eventId")
    void deleteEvent(String eventId);

    @Query("DELETE FROM `Event Category` WHERE columnCatId = :catId")
    void deleteCategory(String catId);
}
