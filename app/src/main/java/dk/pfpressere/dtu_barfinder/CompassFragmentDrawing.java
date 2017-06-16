package dk.pfpressere.dtu_barfinder;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by REC on 16-Jun-17.
 */

public class CompassFragmentDrawing extends Fragment {
// This class draws the compass in the fragment_frame.

    static final float SCALING_FACTOR = 0.8f;

    CompassView compassView;
    private Bitmap compassBitmapSrc;
    private float rotationDegrees = 90;
    private LayoutInflater inflater;
    private ViewGroup container;

    private static final String TAG = "compass_frag_draw";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;
        this.container = container;

        compassView = new CompassView(getActivity());
        compassBitmapSrc = BitmapFactory.decodeResource(getResources(),R.drawable.compass_512);

        return compassView;
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }

    private class CompassView extends SurfaceView implements SurfaceHolder.Callback {

        Thread drawingThread;
        SurfaceHolder surfaceHolder;
        boolean toogleRun = false;

        public CompassView(Context context) {
            super(context);

            surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);

        }

        public void surfaceCreated(SurfaceHolder holder) {
            drawingThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Canvas canvas = null;
                    while(!Thread.currentThread().isInterrupted()) {
                        canvas = surfaceHolder.lockCanvas();
                        if (canvas != null) {
                            updateCompass();
                            drawCompass(canvas);
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }

                }
            });
            drawingThread.start();
        }

        private void drawCompass(Canvas canvas) {

            // Draws a background rect for background.
            Paint whitePaint = new Paint();
            whitePaint.setColor(Color.WHITE);
            whitePaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), whitePaint);

            // Creates a scaled bitmap from the src, that scales to the view.
            int scaling = Math.round(Math.min(canvas.getWidth(),canvas.getHeight()) * SCALING_FACTOR);
            Bitmap compassBitmap = Bitmap.createScaledBitmap(compassBitmapSrc, scaling, scaling,false);

            // Rotates the bitmap
            Matrix rotateMatrix = new Matrix();
            rotateMatrix.postRotate(rotationDegrees);
            compassBitmap = Bitmap.createBitmap(compassBitmap , 0, 0, compassBitmap.getWidth(),
                    compassBitmap.getHeight(), rotateMatrix, true);


            // Draws bitmap in center of canvas.
            canvas.drawBitmap(compassBitmap,canvas.getWidth()/2-compassBitmap.getWidth()/2,
                    canvas.getHeight()/2-compassBitmap.getWidth()/2, new Paint());
        }

        private void updateCompass() {
            rotationDegrees += 1;
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (drawingThread != null) {
                drawingThread.interrupt();
            }

        }
    }
}
