package dk.pfpressere.dtu_barfinder;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    appState currentState;
    FragmentTransaction fragmentTransaction;
    CompassFragment compassFragment;
    MissionsFragment missionsFragment;

    private enum appState {
        COMPASS, MISSIONS
    }

    private static final String TAG = "main_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        missionsFragment = new MissionsFragment();
        compassFragment = new CompassFragment();
        fragmentTransaction.add(R.id.main_frame, compassFragment);
        fragmentTransaction.commit();

        currentState = appState.COMPASS;
        setNavigationItemChecked(currentState);
    }

    protected void onResume() {
        super.onResume();
        setNavigationItemChecked(currentState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(2).setCheckable(false);
        navigationView.getMenu().getItem(3).setCheckable(false);
        navigationView.getMenu().getItem(4).setCheckable(false);

        if (id == R.id.nav_compass) {
            // Handle the compass action
            if(currentState!=appState.COMPASS){
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, compassFragment);
                fragmentTransaction.commit();
                currentState = appState.COMPASS;
            }
        } else if (id == R.id.nav_missions) {
            if(currentState != appState.MISSIONS){
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, missionsFragment);
                fragmentTransaction.commit();
                currentState = appState.MISSIONS;
            }
        } else if (id == R.id.nav_beer_now) {
            setNavigationItemChecked(appState.COMPASS);
            if (currentState != appState.COMPASS) {
                onNavigationItemSelected(navigationView.getMenu().getItem(0));
                Toast.makeText(this,"Brug fra kompas-side.", Toast.LENGTH_LONG).show();
            } else {
                compassFragment.updateBarnummer(compassFragment.getBarIndex(compassFragment.getClosestBar()));
                Toast.makeText(this,"Tætteste bar: " + compassFragment.getCenterButton().getText(), Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_google_maps_route) {
            if(currentState == appState.COMPASS) {
                Location navigationLocation = compassFragment.getBarLocation(compassFragment.getChosenBar());

                // Start a goggle maps activity.
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://maps.google.dk/maps?=34.34&daddr=" + navigationLocation.getLatitude() +
                                ", " + navigationLocation.getLongitude())));
            } else {
                Toast.makeText(this,R.string.app_wrong_state, Toast.LENGTH_LONG).show();
                onNavigationItemSelected(navigationView.getMenu().getItem(0));
                setNavigationItemChecked(appState.COMPASS);
            }

        } else if (id == R.id.open_drunkify) {

            Intent drunkIntent = getPackageManager().getLaunchIntentForPackage("tech.radioactiveswordfish.drunkify");

            if (drunkIntent != null) {
                // Opens Drunkify if the app is already installed.
                drunkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(drunkIntent);

            } else {
                // Opens Play Store if Drunkify is not installed.
                PlayStoreDialogFragment playStoreFragment = new PlayStoreDialogFragment();
                playStoreFragment.show(getSupportFragmentManager(), "Play Store");
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationItemChecked(appState appState) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (appState == MainActivity.appState.COMPASS) {
            navigationView.getMenu().getItem(0).setChecked(true);
        } else if(appState == MainActivity.appState.MISSIONS) {
            navigationView.getMenu().getItem(1).setChecked(true);
        }
    }
}
