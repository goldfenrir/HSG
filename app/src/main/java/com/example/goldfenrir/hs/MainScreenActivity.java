package com.example.goldfenrir.hs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

public class MainScreenActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        NavigationDrawerFragment.NavigationDrawerCallbacks{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    //Menu variables
    private CharSequence mTitle;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private static final LatLng ORIGEN = new LatLng(-12.06955, -77.07978);
    private static final LatLng DESTINO = new LatLng(-12.07876, -77.06554);


    public static final String TAG = MainScreenActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    final Context context = this;

    private MobileServiceClient mClient;

    public List<HandMeService> services = new ArrayList<HandMeService>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_screen);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            mClient = new MobileServiceClient(
                    "https://hsg.azure-mobile.net/",
                    "PWDIdawYPyMwyLMOSNIIXggQZTdBba54",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //mClient.getTable(HandMeService.class).select();

        //Menu initialization
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Button btnRequest = (Button) findViewById(R.id.btnRequest);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RequestActivity.class);
                startActivity(intent);
            }
        });



        //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION")
//                != PackageManager.PERMISSION_GRANTED) {
//            // Check Permissions Now
//            ActivityCompat.requestPermissions(this,
//                    //new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    new String[]{"android.permission.ACCESS_FINE_LOCATION"},
//                    123);
//        } else {
//            // permission has been granted, continue as usual
//            mGoogleApiClient = new GoogleApiClient.Builder(this)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .addApi(LocationServices.API)
//                    .build();
//
//
//            // Create the LocationRequest object
//            mLocationRequest = LocationRequest.create()
//                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                    .setInterval(10 * 1000)        // 10 seconds, in milliseconds
//                    .setFastestInterval(1 * 1000); // 1 second, in milliseconds
//
//        }

        setUpMapIfNeeded();
    }



    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        //mGoogleApiClient.connect();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
            else{
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Setee mapa");
                builder1.setCancelable(true);
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */


    private void setUpMap() {
        mMap.setMyLocationEnabled(true);

        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Zoom in the Google Map

        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        MarkerOptions marker = new MarkerOptions();

        //marker.icon(BitmapDescriptorFactory.fromPath("C:/Users/Alexis/Dropbox/PUCP/2015-2/Programacion en Dispositivos Moviles/EX2/GoogleMapsTest/doge.png"));
        //mMap.addMarker(marker.position(ORIGEN).title("PUKE").snippet("Consider yourself located"));
        //mMap.addMarker(marker.position(DESTINO).title("SAN ISIDRO").snippet("Consider yourself located"));



        /*mMap.addMarker(new MarkerOptions()
                .position(DESTINO)
                .title("SAN ISIDRO")
                .snippet("Consider yourself located"));
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin))
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                    @Override
                    public boolean onMarkerClick(Marker arg0) {
                        //if (arg0.getTitle().equals("SAN ISIDRO")) { // if marker source is clicked
                            Intent intent = new Intent(context, OfferActivity.class);
                            startActivity(intent);
                        //}
                        return true;
                    }
                });


        mMap.addMarker(new MarkerOptions()
                .position(ORIGEN)
                .title("PUKE")
                .snippet("Consider yourself located"));
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin))
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                    @Override
                    public boolean onMarkerClick(Marker arg0) {
                        //if (arg0.getTitle().equals("PUKE")) { // if marker source is clicked
                            Intent intent = new Intent(context, OfferActivity.class);
                            startActivity(intent);
                        //}
                        return true;
                    }
                });*/

        Button btnRequest = (Button) findViewById(R.id.btnRefresh);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            MobileServiceTable<HandMeService> serviceTable = mClient.getTable(HandMeService.class);
                            MobileServiceList<HandMeService> result = serviceTable.execute().get();
                            for (HandMeService item : result) {
                                services.add(item);
                            }
                            System.out.println("Tamaño de la lista: "+services.size());
                            System.out.println("Titulo 1: "+services.get(0)._title);


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (MobileServiceException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute();



            }
        });

        Button btnShow = (Button) findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawMap(mMap, services);
            }
        });


        //MapThread mapThread = new MapThread();
        //mapThread.start();


        /*new Thread(new Runnable() {

            public void run(){

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        while(!Thread.currentThread().isInterrupted()) {
                            try{
                                try {

                                    MobileServiceTable<HandMeService> serviceTable = mClient.getTable(HandMeService.class);
                                    MobileServiceList<HandMeService> result = serviceTable.execute().get();
                                    for (HandMeService item : result) {
                                        services.add(item);
                                    }
                                    System.out.println("Tamaño de la lista: "+services.size());
                                    System.out.println("Titulo 1: "+services.get(0)._title);


                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (MobileServiceException e) {
                                    e.printStackTrace();
                                }

                                drawMap(mMap, services);


                                Thread.sleep(10000);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                });

            }
        }).start();*/

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ORIGEN, 14));

    }

    /*public class MapThread extends Thread{
        public void run(){
            while(!Thread.currentThread().isInterrupted()) {
                //List<Service> services = ServiceController.Refresh();
                drawMap(mMap, services);
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }*/


    public synchronized void drawMap(GoogleMap mMap, final List<HandMeService> services){
        for (int i=0;i<services.size();i++){
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(services.get(i)._latitude, services.get(i)._longitude))
                    .title(services.get(i)._title)
                    .snippet("Consider yourself located"));
            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin))
            final int finalI = i;
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                @Override
                public boolean onMarkerClick(Marker arg0) {
                    //if (arg0.getTitle().equals("SAN ISIDRO")) { // if marker source is clicked
                    Intent intent = new Intent(context, OfferActivity.class);
                    intent.putExtra("ServiceId",services.get(finalI).id);
                    startActivity(intent);
                    //}
                    return true;
                }
            });
        }
    }


    private void handleNewLocation(Location location) {
//        Log.d(TAG, location.toString());
//
//        double currentLatitude = location.getLatitude();
//        double currentLongitude = location.getLongitude();
//
//        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
//
//        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
//        MarkerOptions options = new MarkerOptions()
//                .position(latLng)
//                .title("I am here!");
//        mMap.addMarker(options);
//        double zoomLevel = 16.0; //This goes up to 21
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, (float) zoomLevel));
//
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
//        builder1.setMessage("Llegue");
//        builder1.setCancelable(true);
//
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
    }

    @Override
    public void onConnected(Bundle bundle) {
//        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//
//        if (location == null) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//        }
//        else {
//            handleNewLocation(location);
//        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
     * Google Play services can resolve some errors it detects.
     * If the error has a resolution, try sending an Intent to
     * start a Google Play services activity that can resolve
     * error.
     */
/*        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            *//*
             * Thrown if Google Play services canceled the original
             * PendingIntent
             *//*
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
        *//*
         * If no resolution is available, display a dialog to the
         * user with the error.
         *//*
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }*/
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    //Menu functions

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
                break;
            case 7:
                mTitle = getString(R.string.title_section7);
                break;
            case 8:
                mTitle = getString(R.string.title_section8);
                break;
            case 9:
                mTitle = getString(R.string.title_section9);
                break;
        }
    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainScreenActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
