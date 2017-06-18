package dk.pfpressere.dtu_barfinder;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;


/**
 * Created by REC on 15-Jun-17.
 */

public class CompassFragment extends Fragment {
    // A class that controls which compass to draw.

    //TODO: brug compassFragmentDrawing.setCompassRotation() et sted.

    View view;
    private Button leftButton;
    private Button centerButton;
    private Button rightButton;
    private int barNummer;
    private Bar chosenBar;
    private CompassFragmentDrawing compassFragmentDrawing;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barNummer = 0;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Addds the CompassFragment to compas_frame
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

    public Bar getChosenBar() {
        return chosenBar;
    }

    // Finds and returns name of a bar. Also sets a chosenBar.
    public String findBarByIndex (int x) {
        if (x % 5 == 0) {
            chosenBar = Bar.KB;
            return "Kælderbaren";
        }
        if (x % 5 == 1 || x % 5 == -4) {
            chosenBar = Bar.HEGNET;
            return "Hegnet";
        }
        if (x % 5 == 2 || x % 5 == -3) {
            chosenBar = Bar.DIAMANTEN;
            return "Diamanten";
        }
        if (x % 5 == 3 || x % 5 == -2) {
            chosenBar = Bar.DIAGONALEN;
            return "Diagonalen";
        }
        if (x % 5 == 4 || x % 5 == -1) {
            chosenBar = Bar.ETHEREN;
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

}
