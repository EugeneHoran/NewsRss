package eugene.com.newsrss.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import eugene.com.livelib.AppExecutors;
import eugene.com.newsrss.db.dao.NewsStationDao;
import eugene.com.newsrss.db.entities.NewsStation;

@Database(
        entities = {
                NewsStation.class
        },
        version = 1,
        exportSchema = false)
@TypeConverters({NewsTypeConverters.class})
public abstract class NewsDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "news-stations-db";
    private static NewsDatabase instance;

    public abstract NewsStationDao getNewsStationDao();


    public static NewsDatabase getInstance(final Context context, final AppExecutors executors) {
        if (instance == null) {
            synchronized (NewsDatabase.class) {
                if (instance == null) {
                    instance = buildDatabase(context.getApplicationContext(), executors);
                }
            }
        }
        return instance;
    }

    private static NewsDatabase buildDatabase(final Context context, final AppExecutors executors) {
        return Room.databaseBuilder(context, NewsDatabase.class, DATABASE_NAME).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                executors.diskIO().execute(() -> {
                    NewsDatabase database = NewsDatabase.getInstance(context, executors);
                    database.getNewsStationDao().insert(new NewsStationDataGenerator().getInitNewsStationList());
                });
            }
        }).build();
    }
}
