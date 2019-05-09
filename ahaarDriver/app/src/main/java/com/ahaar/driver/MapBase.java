package com.ahaar.driver;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.ahaar.driver.abstractactivity.MyAbstractActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public abstract class MapBase extends MyAbstractActivity implements LocationListener, GoogleApiClient.OnConnectionFailedListener
        , GoogleApiClient.ConnectionCallbacks, OnMapReadyCallback {

    private static final String TAG = "mapbase";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 100;
    private static final int REQUEST_CHECK_SETTINGS = 121;

    private int mapId;

    //google map object
    protected GoogleMap mGoogleMap;


    public boolean zoom = true;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    LocationRequest mLocationRequest;

    //creating variable for googleapi client to connet
    public GoogleApiClient mGoogleApiClient;

    private static final int LOCATIONCODE = 1;

    //flag to check permission is granted or not
    boolean mLocationPermissionGranted = false;

    //list of permission to be ask at runtime for marshamallo and above versions
    String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};
    private View mapView;

    protected void showMyMap() {
        if (!checkPlayServices()) {
            return;
        }
        createLocationRequest();
        initGoogleClient();
      isUserGrantedPermission();


    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    /*check the user has granted the permission or not*/
    protected void isUserGrantedPermission() {
        if (hasPermissions(getApplicationContext(), PERMISSIONS)) {
            mLocationPermissionGranted = true;
            setUpMapIfNeeded();
        } else {
            mLocationPermissionGranted = false;
            ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATIONCODE);
        }

    }


    //initilizing the googlepai client object wiht our location update parameters
    private void initGoogleClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }


    @Override
    public void onLocationChanged(Location location) {

        if (location!=null) {
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, 17);
            if (zoom && mGoogleMap != null) {
                mGoogleMap.animateCamera(cameraUpdate);
                zoom = false;
            }

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        removeLocationUpdates();
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient != null)
            if (mGoogleApiClient.isConnected())
                startLocationUpdates();


    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

     /*   LocationSettingsRequest.Builder builder=new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());
        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

            }
        });*/


     /*   task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MapBase.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });*/



    }

    protected void setUpMapIfNeeded() {
        if (mGoogleMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
               mapView = mapFragment.getView();
               mapFragment.getMapAsync(this);

        }
    }

    public Location getcurrentloc() {
        Location currentLocation = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
        }
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        return currentLocation;

    }

    public Location getlocation(Context context) {
        Location location = null;
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT > 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //here req again for Permission
            }
            location = getcurrentloc();
        } else {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                location = getcurrentloc();
            } else {
                showGPSDisabledAlertToUser(context);
            }
        }
        return location;
    }

    private void showGPSDisabledAlertToUser(Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }




    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATIONCODE);
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
// Permissions ok, we get last location
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        onLocationChanged(lastLocation);

        Log.d(TAG, "Location update started ..............: ");

    }

    protected void removeLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATIONCODE);
            return;
        }
        if (mGoogleApiClient==null)
            return;
        else if (mGoogleApiClient.isConnected()){
            PendingResult<Status> statusPendingResult = LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            if (statusPendingResult.isCanceled())
                Log.d(TAG, "Location canceled");
            else
                Log.d(TAG, "Location not canceled");


            Log.d(TAG, "Location Update removed");
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATIONCODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    setUpMapIfNeeded();
                } else {
                    mLocationPermissionGranted = false;

                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeLocationUpdates();
    }

    public Marker addNewMarkerWithIcon(Location location, String title, String desscription, int iconID
    , int width, int height)  {
        if (mGoogleMap != null&&location!=null) {
            MarkerOptions options = new MarkerOptions();
            options.title(title);
            options.snippet(desscription);
        /*    int height = 250;
            int width = 150;*/
            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(iconID);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
            options.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            options.position(new LatLng(location.getLatitude(),location.getLongitude()));
            Marker marker = mGoogleMap.addMarker(options);
            return marker;
        } else return null;
    }

    public Marker addNewMarker(Location location, String title, String desscription)  {
        if (mGoogleMap != null&&location!=null) {
            MarkerOptions options = new MarkerOptions();
            options.title(title);
            options.snippet(desscription);
            options.position(new LatLng(location.getLatitude(),location.getLongitude()));
            Marker marker = mGoogleMap.addMarker(options);
            return marker;
        } else return null;
    }

    public void removeMarker(Marker marker) {
        if (mGoogleMap != null)
            marker.remove();
    }

    public void zoomMapToLocation(float zoomLevel, Location location) {
        if (location!=null&&mGoogleMap!=null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), zoomLevel);
            mGoogleMap.animateCamera(cameraUpdate);
        }
    }


    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                finish();
            }

            return false;
        }

        return true;
    }
}
