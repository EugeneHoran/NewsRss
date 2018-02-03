package eugene.com.newsrss.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;

import java.util.List;

import eugene.com.newsrss.R;
import eugene.com.newsrss.databinding.ActivityNewsBinding;
import eugene.com.newsrss.ui.common.NavigationController;
import eugene.com.newsrss.ui.interfaces.NavControllerCallbacks;
import eugene.com.newsrss.ui.interfaces.NewsCallbacks;
import eugene.com.newsrss.ui.interfaces.SelectorCallbacks;
import eugene.com.newsrss.util.Constants;

public class NewsActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        SelectorCallbacks,
        NewsCallbacks,
        NavControllerCallbacks {
    private NavigationController navController;
    private ActivityNewsBinding binding;
    private ActionBarDrawerToggle toggle;
    private SubMenu newsSubMenu;
    private Drawable filterIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news);
        navController = new NavigationController(getSupportFragmentManager(), this);
        binding.navigation.setNavigationItemSelectedListener(this);
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
        if (savedInstanceState == null) {
            navController.initStart(sp.getBoolean(Constants.SP_NEWS_INITIATED, false));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        filterIcon = menu.findItem(R.id.action_filter).getIcon();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            navController.navToSelectNewsSource();
        }
        return true;
    }

    @Override
    public void navToRss() {
        navController.navToRss();
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void lockDrawer(boolean lock) {
        if (lock) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
        } else {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.START);
        }
    }

    @Override
    public void initActionbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        setTitle(null);
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void initNavDrawerMenuItems(List<String> menuItems) {
        Menu menu = binding.navigation.getMenu();
        if (newsSubMenu != null) {
            newsSubMenu.clear();
        }
        newsSubMenu = menu.addSubMenu(R.string.news_sources);
        menu.setGroupCheckable(newsSubMenu.getItem().getGroupId(), true, true);
        if (menuItems != null) {
            for (int i = 0; i < menuItems.size(); i++) {
                newsSubMenu.add(i, Menu.FIRST + i, Menu.FIRST + 1, menuItems.get(i));
            }
        }
        menu.findItem(R.id.action_filter).setChecked(false).setCheckable(false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter) {
            navController.navToSelectNewsSource();
            return true;
        }
        for (int i = 0; i < newsSubMenu.size(); i++) {
            if (id == newsSubMenu.getItem(i).getItemId()) {
                newsSubMenu.getItem(i).setChecked(true);
                if (navController.getRssFragment() != null) {
                    navController.getRssFragment().changeNewsPage(i, true);
                }
            } else {
                newsSubMenu.getItem(i).setChecked(false);
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void navIconColor(int color) {
        toggle.getDrawerArrowDrawable().mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        if (filterIcon != null) {
            filterIcon.mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < newsSubMenu.size(); i++) {
            if (i == position) {
                newsSubMenu.getItem(i).setChecked(true);
            } else {
                newsSubMenu.getItem(i).setChecked(false);
            }
        }
    }
}
