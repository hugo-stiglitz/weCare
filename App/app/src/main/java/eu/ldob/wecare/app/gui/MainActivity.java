package eu.ldob.wecare.app.gui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import eu.ldob.app.wecare.R;
import eu.ldob.wecare.app.gui.main.OperationsFragment;
import eu.ldob.wecare.app.gui.main.OperationsRecycledFragment;
import eu.ldob.wecare.app.service.Service;
import eu.ldob.wecare.app.service.ServiceHandler;
import eu.ldob.wecare.app.util.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private Service service;

    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = ServiceHandler.getService();

        initToolbar();
        initDrawer();
        initTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        if(id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "Setting1", Toast.LENGTH_LONG).show();
        }
        if(id == R.id.action_refresh){
            Toast.makeText(MainActivity.this, "Setting2", Toast.LENGTH_LONG).show();
        }
        if(id == R.id.action_new){
            Toast.makeText(MainActivity.this, "Setting3", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {

        setTitle(null);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.placeholder);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void initTabs() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        AWeCareFragment operationsFragment = new OperationsFragment();
        operationsFragment.setService(service);
        adapter.addFrag(operationsFragment, "Einsätze");

        AWeCareFragment operationsRecycledFragment = new OperationsRecycledFragment();
        operationsRecycledFragment.setService(service);
        adapter.addFrag(operationsRecycledFragment, "Einsätze");

        viewPager = (ViewPager) findViewById(R.id.tabs_viewpager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }
}