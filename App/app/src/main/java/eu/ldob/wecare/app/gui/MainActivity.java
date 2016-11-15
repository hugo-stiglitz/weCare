package eu.ldob.wecare.app.gui;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

import eu.ldob.wecare.app.R;
import eu.ldob.wecare.app.gui.main.CurrentFragment;
import eu.ldob.wecare.app.gui.main.HistoryFragment;
import eu.ldob.wecare.service.logic.Service;
import eu.ldob.wecare.service.logic.ServiceHandler;
import eu.ldob.wecare.app.util.ViewPagerAdapter;
import eu.ldob.wecare.entity.operation.GPSCoordinates;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private Service service;

    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = ServiceHandler.getService();

        initToolbar();
        initDrawer();
        initTabs();

        startLocationTracking();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopLocationTracking();
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
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void initTabs() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        AWeCareFragment currentFragment = new CurrentFragment();
        currentFragment.setService(service);
        adapter.addFrag(currentFragment, "Aktuell");

        AWeCareFragment historyFragment = new HistoryFragment();
        historyFragment.setService(service);
        adapter.addFrag(historyFragment, "Abgeschlossen");

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

    private void startLocationTracking() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        //criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        //criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, false);

        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            onLocationChanged(location);
        }

        locationManager.requestLocationUpdates(provider, 2000, 10, this);
    }

    private void stopLocationTracking() {

        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {

        // TODO bugfix!
        service.setCurrentLocation(new GPSCoordinates(location.getLatitude(), location.getLongitude()), location.getAccuracy());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO
    }
}