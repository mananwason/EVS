package com.manan.evs.iiitd.evs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {
    public GPSTracker gps;
    public LatLng currentLocation;

    public enum Category {
        POLLUTION(1),
        MAP(2);
        public final int id;

        private Category(int id) {
            this.id = id;
        }
    }

    public Drawer.Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        //GetLocation();

        result = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(R.layout.header)
                .addDrawerItems(

                        new PrimaryDrawerItem().withName(null),
                        new PrimaryDrawerItem().withName(R.string.pollution).withIdentifier(Category.POLLUTION.id).withIcon(GoogleMaterial.Icon.gmd_landscape),
                        new PrimaryDrawerItem().withName(R.string.map).withIdentifier(Category.MAP.id).withIcon(GoogleMaterial.Icon.gmd_map)

                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            if (drawerItem instanceof Nameable) {
                                toolbar.setTitle(((Nameable) drawerItem).getNameRes());
                                Log.d("mainactivity", "position: " + position);

                                result.closeDrawer();

                                switch (position) {
                                    case 1:
                                        Bundle args = new Bundle();

                                        Fragment pollution = new PollutionActivity();
                                        pollution.setArguments(args);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.frame,pollution).commit();
                                        break;
                                    case 2:
                                        Bundle argsmap = new Bundle();

                                        Fragment map = new MapFragment();
                                        argsmap.putParcelable("latlng",currentLocation);
                                        map.setArguments(argsmap);
                                        FragmentManager fragmentManager1 = getFragmentManager();
                                        fragmentManager1.beginTransaction().replace(R.id.frame,map).commit();
                                        break;
                                }
                            }

                        }
                    }
                })
                .build();
        result.getListView().setVerticalScrollBarEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.refresh).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_refresh).color(Color.WHITE).actionBarSize());
        menu.findItem(R.id.action_settings).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_settings).paddingDp(1).color(Color.WHITE).actionBarSize());
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
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            return true;

        } else if (id == R.id.refresh) {
            Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
    protected void GetLocation() {
        gps = new GPSTracker(MainActivity.this);

        double latitude = 0;
        double longitude = 0;
        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            currentLocation = new LatLng(latitude,longitude);
            List<Address> addresses = null;
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.ENGLISH);

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
    }
}
