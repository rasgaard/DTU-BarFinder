package dk.pfpressere.dtu_barfinder;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by REC on 19-Jun-17.
 */

public class MissionsFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       return inflater.inflate(R.layout.missions_fragment, container, false);
    }

}
