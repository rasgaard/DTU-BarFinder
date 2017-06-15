package dk.pfpressere.dtu_barfinder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class PlayStoreFragment extends DialogFragment {
    private Intent drunkIntent;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.open_play_store)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener(){
                    public void onClick (DialogInterface dialog, int id){

                        // fail safe try/catch, in case play store is not available.

                        try {
                            drunkIntent = new Intent(Intent.ACTION_VIEW);
                            drunkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            drunkIntent.setData(Uri.parse("market://details?id=" + "tech.radioactiveswordfish.drunkify"));
                            startActivity(drunkIntent);
                        } catch (Exception e) {
                            System.err.println("Hov! Noget gik galt.");

                        }
                    }})

                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                    dismiss();
                    }
                });

        return builder.create();
    }
}