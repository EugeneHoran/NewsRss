package eugene.com.newsrss.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

import eugene.com.newsrss.db.entities.NewsStation;

@Dao
public abstract class NewsStationDao {


    @Query("SELECT * FROM news_stations")
    public abstract LiveData<List<NewsStation>> getNewsStations();

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<NewsStation> newsStationList);

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void update(List<NewsStation> newsStationList);
}
