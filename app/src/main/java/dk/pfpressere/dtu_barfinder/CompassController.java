package dk.pfpressere.dtu_barfinder;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.UrlQuerySanitizer;
import android.support.v4.app.ActivityCompat;

/**
 * Created by REC on 14-Jun-17.
 */

/*public class CompassController {

    // The inner workings of a compass.
    // Must be able to keep track of location and calculate the direction of the compass.
    // Parts must work with activity-callback-methods.

    private static final long ONE_MIN = 60000;

    private Location currentLocation;
    private Location targetLocation;
    private LocationManager locationManager;

    /*CompassController(Location targetLocation, Context context) throws Exception {
        this.targetLocation = targetLocation;

        setup(context);


    }

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }


    public boolean setup(Context context) throws MissingPermissionException {
        // Method should be called when the compass is created first time.
        if (null == (locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE))) {
            return false;
        }


        // Checks if permissions are granted.
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            currentLocation = locationManager.getLastKnownLocation();
        } else {
            throw new MissingPermissionException("Missing location permissions");
        }


        return true;
    }
}*/


