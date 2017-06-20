package dk.pfpressere.dtu_barfinder;

import android.app.Service;
import android.hardware.GeomagneticField;
import android.location.LocationListener;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class GPSTracker extends Service implements LocationListener {
    private final Context context;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    GeomagneticField geomagneticField;

    Location location;

    double latitude;
    double longitude;

    protected LocationManager mLocationManager;

    public GPSTracker(Context context) {
        this.context = context;
        getLocation();
    }

    public Location getLocation() {
        Location networkLocation = null;
        Location gpsLocation = null;
        try {
            mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled || isNetworkEnabled) {
                this.canGetLocation = true;

                if (isNetworkEnabled) {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            0,
                            0, this);

                    if (mLocationManager != null) {
                        networkLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                if(isGPSEnabled) {
                    if(location == null) {
                        mLocationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                0,
                                0, this);
                        if(mLocationManager != null) {
                            gpsLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if(location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(networkLocation == null) {
            if(gpsLocation != null) {
                location = gpsLocation;
            }
        }

        if(gpsLocation == null) {
            if(networkLocation != null) {
                location = networkLocation;
            }
        }

        if(gpsLocation != null && networkLocation != null) {
            if (networkLocation.getAccuracy() < gpsLocation.getAccuracy()) {
                location = networkLocation;
            } else {
                location = gpsLocation;
            }
        }

        latitude = location.getLatitude();
        longitude = getLongitude();
        return location;
    }



    public double getLatitude() {
        if(location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if(location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public GeomagneticField getGeomagneticField() {
        return geomagneticField;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("test","In inLocationChanced()");
        this.location = location;
        // Toast.makeText(context,"Location changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
