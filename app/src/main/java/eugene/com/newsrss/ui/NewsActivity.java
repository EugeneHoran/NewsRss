package eugene.com.newsrss.ui;

import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import java.util.List;

import eugene.com.newsrss.R;
import eugene.com.newsrss.databinding.ActivityNewsBinding;
import eugene.com.newsrss.ui.common.NavigationController;
import eugene.com.newsrss.ui.interfaces.NewsCallbacks;

public class NewsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NewsCallbacks {
    private NavigationController navController;
    private ActivityNewsBinding binding;
    private ActionBarDrawerToggle toggle;
    private SubMenu newsSubMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news);
        navController = new NavigationController(getSupportFragmentManager());
        binding.navigation.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            navController.navToRss();
        }
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
    public void initActionbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        setTitle(null);
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void navMenuItems(List<String> menuItems) {
        Menu menu = binding.navigation.getMenu();
        newsSubMenu = menu.addSubMenu("News Sources");
        menu.setGroupCheckable(newsSubMenu.getItem().getGroupId(), true, true);
        newsSubMenu.clear();
        if (menuItems != null) {
            for (int i = 0; i < menuItems.size(); i++) {
                newsSubMenu.add(i, Menu.FIRST + i, Menu.FIRST + 1, menuItems.get(i));
            }
        }
        newsSubMenu.getItem(0).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        for (int i = 0; i < newsSubMenu.size(); i++) {
            if (id == newsSubMenu.getItem(i).getItemId()) {
                newsSubMenu.getItem(i).setChecked(true);
                if (navController.getRssFragment() != null) {
                    navController.getRssFragment().changeNewsPage(i);
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
