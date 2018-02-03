package eugene.com.newsrss.ui.interfaces;

import eugene.com.newsrss.db.entities.NewsStationView;

public interface RssPageChangeCallbacks {
    void setToolbarLogo(int position);

    void setToolbarLogoAlpha(float alpha);

    void onPageSelected(int position);

    void onPageColorChange(NewsStationView newsStationView);

    void expandAppBar();
}
