package dk.pfpressere.dtu_barfinder;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;

/**
 * Created by REC on 15-Jun-17.
 */

public class CompassFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.compass_fragment,container,false);
    }
}
