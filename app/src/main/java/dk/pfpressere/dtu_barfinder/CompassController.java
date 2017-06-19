package dk.pfpressere.dtu_barfinder;

import android.location.Location;


public class CompassController {
    private Location targetLocation;

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    public Location getTargetLocation() {
        return targetLocation;
    }

    public CompassController(Location targetLocation) {
        setTargetLocation(targetLocation);
    }

    public float getBearing(Location currenLocation, float heading) {
        return currenLocation.bearingTo(targetLocation) + heading;
    }
}


