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

public class CompassController {

    public static double bearing(double currentLat, double currentLng, double targetLat, double targetLng) {
        double latitude1 = Math.toRadians(currentLat);
        double latitude2 = Math.toRadians(targetLat);

        double longitudeDiff = Math.toRadians(targetLng - currentLng);

        double x = Math.sin(longitudeDiff) * Math.cos(latitude2);
        double y = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longitudeDiff);

        return (Math.toDegrees(Math.atan2(y, x)) + 270) % 360;
    }
}


