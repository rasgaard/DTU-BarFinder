package dk.pfpressere.dtu_barfinder;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;
import android.widget.RelativeLayout;

/**
 * Created by REC on 15-Jun-17.
 */

public class CompassFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout compassFrame = (RelativeLayout) getActivity().findViewById(R.id.compass_frame);
        CompassView compassView = new CompassView(getActivity().getApplicationContext(),
                BitmapFactory.decodeResource(getResources(),R.drawable.c_512));

        //compassFrame.addView(compassView);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.compass_fragment,container,false);
    }

    private class CompassView extends SurfaceView implements SurfaceHolder.Callback {


        public CompassView(Context context, Bitmap bitmap) {
            super(context);
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    }
}
