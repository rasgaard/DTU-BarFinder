package dk.pfpressere.dtu_barfinder;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.LinkedList;

public class CompassFragment extends Fragment implements SensorEventListener{
    // A class that controls which compass to draw.

    private final static String TAG = "compass_fragment";

    // Fields for buttons.
    private Button leftButton;
    private Button centerButton;
    private Button rightButton;
    private Destination chosenDestination;
    private GPSTracker gps;

    private DestinationManager destinationManager;

    // Fields for heading
    private SensorManager sensorManager;
    private Sensor sensorMagnetic;
    private Sensor sensorGravity;
    private float heading;

    private GeomagneticField geomagneticField;
    private float[] orientation = new float[3];
    private float[] rotation = new float[9];
    private float[] gravity = new float[3];
    private float[] geomagnetic = new float[3];

    // Views and fragments.
    private CompassController mainCompassController;
    private CompassFragmentDrawing compassFragmentDrawing;
    View view;

    public Button getCenterButton() {
        return centerButton;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        destinationManager = new DestinationManager();
        initStandardDestinations();

        gps = new GPSTracker(getActivity());
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        mainCompassController = new CompassController(destinationManager.getCurrentDestination().getLocation());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Addds the CompassFragment to compass_frame
        compassFragmentDrawing = new CompassFragmentDrawing();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.compass_frame, compassFragmentDrawing);
        fragmentTransaction.commit();

        view = inflater.inflate(R.layout.compass_fragment, container, false);
        centerButton = (Button) view.findViewById(R.id.center_bar_button);
        centerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Your Location is -\nLat: " + gps.getLatitude() + "\nLong: " + gps.getLongitude(), Toast.LENGTH_LONG).show();
            }
        });
        // Updates the center button.
        chosenDestination = destinationManager.getCurrentDestination();
        onDestinationChanged();

        leftButton = (Button) view.findViewById(R.id.left_bar_button);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenDestination = destinationManager.getPreviousDestination();
                onDestinationChanged();
            }
        });

        rightButton = (Button) view.findViewById(R.id.right_bar_button);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenDestination = destinationManager.getNextDestination();
                onDestinationChanged();
            }
        });
        return view;
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();

        sensorMagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this,sensorMagnetic,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorGravity,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onStop() {
        super.onStop();

        sensorManager.unregisterListener(this,sensorMagnetic);
        sensorManager.unregisterListener(this,sensorGravity);
    }

    public void updateDestination(Destination destination) {
        chosenDestination = destination;
        onDestinationChanged();
    }

    public Destination getChosenDestination() {
        return chosenDestination;
    }

    private void initStandardDestinations() {
        Location location;

        location = new Location("");
        location.setLatitude(Double.parseDouble(getActivity().getString(R.string.kb_latitude)));
        location.setLongitude(Double.parseDouble(getActivity().getString(R.string.kb_longitude)));
        destinationManager.addDestination(new Destination("KB", location));

        location = new Location("");
        location.setLatitude(Double.parseDouble(getActivity().getString(R.string.hegnet_latitude)));
        location.setLongitude(Double.parseDouble(getActivity().getString(R.string.hegnet_longitude)));
        destinationManager.addDestination(new Destination(getActivity().getString(R.string.hegnet_name), location));

        location = new Location("");
        location.setLatitude(Double.parseDouble(getActivity().getString(R.string.diamanten_latitude)));
        location.setLongitude(Double.parseDouble(getActivity().getString(R.string.diamanten_longitude)));
        destinationManager.addDestination(new Destination(getActivity().getString(R.string.diamanten_name), location));

        location = new Location("");
        location.setLatitude(Double.parseDouble(getActivity().getString(R.string.diagonalen_latitude)));
        location.setLongitude(Double.parseDouble(getActivity().getString(R.string.diagonalen_longitude)));
        destinationManager.addDestination(new Destination(getActivity().getString(R.string.diagonalen_name), location));

        location = new Location("");
        location.setLatitude(Double.parseDouble(getActivity().getString(R.string.etheren_latitude)));
        location.setLongitude(Double.parseDouble(getActivity().getString(R.string.etheren_longitude)));
        destinationManager.addDestination(new Destination(getActivity().getString(R.string.etheren_name), location));

    }

    private void onDestinationChanged() {
        centerButton.setText(chosenDestination.getName());
        mainCompassController.setTargetLocation(chosenDestination.getLocation());
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = sensorEvent.values;
        } else if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = sensorEvent.values;
        }

        SensorManager.getRotationMatrix(rotation, null, gravity, geomagnetic);
        SensorManager.getOrientation(rotation,orientation);
        heading = -(float) Math.toDegrees(orientation[0]);
        if(gps.getGeomagneticField() != null) {
            heading -= geomagneticField.getDeclination();
        }
        CompassFragmentDrawing.setCompassRotation(mainCompassController.getBearing(gps.getLocation(), heading));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}