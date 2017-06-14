package dk.pfpressere.dtu_barfinder;

import android.content.Intent;
import android.net.Uri;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mapsbutton = (Button) findViewById(R.id.vknapid);
        mapsbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.dk/maps?=34.34&daddr=55.7865880, 12.5253610"));
                startActivity(intent);
            }
        });

        Button startMainCompassActivityButton = (Button) findViewById(R.id.start_main_compass_activity_button_id);
        startMainCompassActivityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainCompassActivity.class);
                startActivity(intent);
            }
        });
        // Christensen lol amiright
    }
}
