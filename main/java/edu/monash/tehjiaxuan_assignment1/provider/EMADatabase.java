package edu.monash.tehjiaxuan_assignment1.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.monash.tehjiaxuan_assignment1.CategoryEntity;
import edu.monash.tehjiaxuan_assignment1.EventEntity;
import edu.monash.tehjiaxuan_assignment1.NewEventCategory;

@Database(entities = {EventEntity.class, CategoryEntity.class}, version = 1)
public abstract class EMADatabase extends RoomDatabase {

    // database name, this is important as data is contained inside a file named "card_database"
    public static final String EMA_DATABASE = "EMADatabase";

    public abstract EMADAO EMADao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile EMADatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Since this class is an absract class, to get the database reference we would need
     * to implement a way to get reference to the database.
     *
     * @param context Application of Activity Context
     * @return a reference to the database for read and write operation
     */
    static EMADatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EMADatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EMADatabase.class, EMA_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
