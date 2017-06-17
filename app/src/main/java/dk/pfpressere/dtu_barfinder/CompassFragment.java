package dk.pfpressere.dtu_barfinder;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.graphics.Rect;
import android.graphics.Matrix;

/**
 * Created by REC on 15-Jun-17.
 */

public class CompassFragment extends Fragment {
    // A class that controls which compass to draw.

    View view;
    private Button leftButton;
    private Button centerButton;
    private Button rightButton;
    private int barNummer;
    private Bar chosenBar = Bar.KB;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Addds the CompassFragment to compas_frame
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.compass_frame, new CompassFragmentDrawing());
        fragmentTransaction.commit();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.compass_fragment, container, false);

        leftButton = (Button) view.findViewById(R.id.left_bar_button);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                barNummer = barNummer - 1;
                centerButton.setText(barnavn(barNummer));

            }
        });

        centerButton = (Button) view.findViewById(R.id.center_bar_button);

        rightButton = (Button) view.findViewById(R.id.right_bar_button);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                barNummer++;
                centerButton.setText(barnavn(barNummer));

            }
        });
        return view;
    }

    public Bar getChosenBar() {
        return chosenBar;
    }

    public String barnavn (int x) {
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
