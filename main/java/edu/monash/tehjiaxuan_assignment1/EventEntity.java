package edu.monash.tehjiaxuan_assignment1;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Event")
public class EventEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "columnID")
    private String eventID;
    @ColumnInfo(name = "columnName")
    private String eventName;
    @ColumnInfo(name = "columnCategoryID")
    private String categoryID;
    @ColumnInfo(name = "columnTicketsAvailable")
    private int ticketsAvailable;
    @ColumnInfo(name = "columnIsActive")
    private boolean isActive;

    public EventEntity(String eventID, String eventName, String categoryID, int ticketsAvailable, boolean isActive) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.categoryID = categoryID;
        this.ticketsAvailable = ticketsAvailable;
        this.isActive = isActive;
    }

    public String getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
