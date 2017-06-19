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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;

public class CompassFragment extends Fragment implements SensorEventListener{
    // A class that controls which compass to draw.

    //TODO: brug compassFragmentDrawing.setCompassRotation() et sted.

    View view;
    private final static String TAG = "compass_fragment";
    private Button leftButton;
    private Button centerButton;
    private Button rightButton;
    private int barNummer;
    private Bar chosenBar;
    private CompassFragmentDrawing compassFragmentDrawing;

    private SensorManager sensorManager;
    private Sensor sensorMagnetic;
    private Sensor sensorGravity;
    // TODO: Set this value in onLocationChanged().
    private GeomagneticField geomagneticField;

    private CompassController mainCompassController;
    private float[] orientation = new float[3];
    private float[] rotation = new float[9];
    private float[] gravity = new float[3];
    private float[] geomagnetic = new float[3];


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barNummer = 0;

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        mainCompassController = new CompassController(getLocation(Bar.HEGNET),getLocation(Bar.ETHEREN));
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

        centerButton.setText(findBarByIndex(barNummer));

        leftButton = (Button) view.findViewById(R.id.left_bar_button);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                barNummer = barNummer - 1;
                centerButton.setText(findBarByIndex(barNummer));

            }
        });

        rightButton = (Button) view.findViewById(R.id.right_bar_button);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                barNummer++;
                centerButton.setText(findBarByIndex(barNummer));

            }
        });
        return view;
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

    public Bar getChosenBar() {
        return chosenBar;
    }

    // Finds and returns name of a bar. Also sets a chosenBar.
    public String findBarByIndex (int x) {
        if (x % 5 == 0) {
            chosenBar = Bar.KB;
            mainCompassController.setTargetLocation(getLocation(chosenBar));
            return "Kælderbaren";
        }
        else if (x % 5 == 1 || x % 5 == -4) {
            chosenBar = Bar.HEGNET;
            mainCompassController.setTargetLocation(getLocation(chosenBar));
            return "Hegnet";
        }
        else if (x % 5 == 2 || x % 5 == -3) {
            chosenBar = Bar.DIAMANTEN;
            mainCompassController.setTargetLocation(getLocation(chosenBar));
            return "Diamanten";
        }
        else if (x % 5 == 3 || x % 5 == -2) {
            chosenBar = Bar.DIAGONALEN;
            mainCompassController.setTargetLocation(getLocation(chosenBar));
            return "Diagonalen";
        }
        else if (x % 5 == 4 || x % 5 == -1) {
            chosenBar = Bar.ETHEREN;
            mainCompassController.setTargetLocation(getLocation(chosenBar));
            return "Etheren";
        }
        else return null;
    }

    public Location getLocation(Bar bar) {
        Location location = new Location("");
        switch (bar) {
            case KB:
                location.setLatitude(Double.parseDouble(getActivity().getString(R.string.kb_latitude)));
                location.setLongitude(Double.parseDouble(getActivity().getString(R.string.kb_longitude)));
                break;
            case HEGNET:
                location.setLatitude(Double.parseDouble(getActivity().getString(R.string.hegnet_latitude)));
                location.setLongitude(Double.parseDouble(getActivity().getString(R.string.hegnet_longitude)));
                break;
            case DIAMANTEN:
                location.setLatitude(Double.parseDouble(getActivity().getString(R.string.diamanten_latitude)));
                location.setLongitude(Double.parseDouble(getActivity().getString(R.string.diamanten_longitude)));
                break;
            case DIAGONALEN:
                location.setLatitude(Double.parseDouble(getActivity().getString(R.string.diagonalen_latitude)));
                location.setLongitude(Double.parseDouble(getActivity().getString(R.string.diagonalen_longitude)));
                break;
            case ETHEREN:
                location.setLatitude(Double.parseDouble(getActivity().getString(R.string.etheren_latitude)));
                location.setLongitude(Double.parseDouble(getActivity().getString(R.string.etheren_longitude)));
                break;
        }
        return location;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = sensorEvent.values;
        } else if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = sensorEvent.values;
        }

        sensorManager.getRotationMatrix(rotation, null, gravity, geomagnetic);
        sensorManager.getOrientation(rotation,orientation);
        mainCompassController.setHeading(-(float) Math.toDegrees(orientation[0]));
        if(geomagneticField != null) {
            mainCompassController.setHeading(geomagneticField.getDeclination());
        }
        compassFragmentDrawing.setCompassRotation(mainCompassController.getHeading());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
