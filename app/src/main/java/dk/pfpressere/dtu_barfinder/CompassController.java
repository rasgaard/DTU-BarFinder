package dk.pfpressere.dtu_barfinder;

import android.location.Location;


public class CompassController {
    private Location currentLocation;
    private Location targetLocation;
    private float heading;

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    public Location getTargetLocation() {
        return targetLocation;
    }

    public float getHeading() {
        return heading;
    }

    public void setHeading(float heading) {
        this.heading = heading;
    }

    public CompassController(Location currentLocation, Location targetLocation) {
        setTargetLocation(targetLocation);
        setCurrentLocation(currentLocation);
        heading = 0;
    }
}


