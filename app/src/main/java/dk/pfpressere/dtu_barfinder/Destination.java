package dk.pfpressere.dtu_barfinder;

import android.location.Location;

public class Destination {
    private Location location;
    private String name;

    public Destination(String name, Location location) {
        setLocation(location);
        setName(name);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
