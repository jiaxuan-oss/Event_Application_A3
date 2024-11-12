package edu.monash.tehjiaxuan_assignment1;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "Event Category")
public class CategoryEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "columnCatId")
    private String catId;
    @ColumnInfo(name = "columnName")
    private String name;
    @ColumnInfo(name = "columnEventCount")
    private int eventCount;
    @ColumnInfo(name = "columnActive")
    private boolean active;

    @ColumnInfo(name = "columnEventLocation")
    private String eventLocation;



    public CategoryEntity(String catId, String name, int eventCount, boolean active, String eventLocation) {
        this.catId = catId;
        this.name = name;
        this.eventCount = eventCount;
        this.active = active;
        this.eventLocation = eventLocation;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getCatId() {
        return catId;
    }

    public String getName() {
        return name;
    }
    public int getEventCount() {
        return eventCount;
    }

    public boolean getActive() {
        return active;
    }

    public void setCatId(String id) {
        this.catId = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
