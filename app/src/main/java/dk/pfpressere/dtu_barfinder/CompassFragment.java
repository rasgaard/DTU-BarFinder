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
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.graphics.Rect;
import android.graphics.Matrix;

/**
 * Created by REC on 15-Jun-17.
 */

public class CompassFragment extends Fragment {
    // A class that controls which compass to draw.




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Addds the CompassFrameFragment to compas_frame
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.compass_frame, new CompassFrameFragment());
        fragmentTransaction.commit();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.compass_fragment, container, false);
    }

}
