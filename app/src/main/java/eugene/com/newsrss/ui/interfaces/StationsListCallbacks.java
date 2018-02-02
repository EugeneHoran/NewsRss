package eugene.com.newsrss.ui.interfaces;

import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.ui.common.BaseInterface;

public interface StationsListCallbacks extends BaseInterface {
    void stationSelected(NewsStation newsStation);
}
