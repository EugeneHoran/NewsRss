package eugene.com.newsrss.ui.interfaces;


import android.support.v7.widget.Toolbar;

import java.util.List;

public interface NewsCallbacks {
    void initActionbar(Toolbar toolbar);

    void navIconColor(int color);

    void navMenuItems(List<String> menuItems);
}
