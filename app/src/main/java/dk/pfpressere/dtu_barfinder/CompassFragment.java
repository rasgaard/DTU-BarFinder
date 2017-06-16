package dk.pfpressere.dtu_barfinder;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;


/**
 * Created by REC on 15-Jun-17.
 */

public class CompassFragment extends Fragment {
    // A class that controls which compass to draw.




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Addds the CompassFragmen to compas_frame
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.compass_frame, new CompassFragmentDrawing());
        fragmentTransaction.commit();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.compass_fragment, container, false);
    }

}
