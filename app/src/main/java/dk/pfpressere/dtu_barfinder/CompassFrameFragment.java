package dk.pfpressere.dtu_barfinder;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by REC on 16-Jun-17.
 */

public class CompassFrameFragment extends Fragment {
// This class draws the compass in the fragment_frame.

    static final float SCALING_FACTOR = 0.8f;

    private Bitmap compassBitmap;
    private float rotationDegrees = 90;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CompassView compassView = new CompassView(getActivity());
        return compassView;
    }

    private class CompassView extends View {

        public CompassView(Context context) {
            super(context);

            // Create and scaled bitmap.
            int scaling =  400; //TODO: Create scaling by view size.
            compassBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.compass_512);
            compassBitmap = Bitmap.createScaledBitmap(compassBitmap, scaling, scaling,false);

        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // Rotates the bitmap
            Matrix rotateMatrix = new Matrix();
            rotateMatrix.postRotate(rotationDegrees);
            compassBitmap = Bitmap.createBitmap(compassBitmap , 0, 0, compassBitmap.getWidth(),
                    compassBitmap.getHeight(), rotateMatrix, true);


            // Draws bitmap in center of canvas.
            canvas.drawBitmap(compassBitmap,canvas.getWidth()/2-compassBitmap.getWidth()/2,
                    canvas.getHeight()/2-compassBitmap.getWidth()/2, new Paint());

        }
    }
}
