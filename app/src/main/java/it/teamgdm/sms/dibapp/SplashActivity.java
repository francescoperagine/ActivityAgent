package it.teamgdm.sms.dibapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);

        /* New Handler to start the MainActivity
         * and closes this Splash-Screen after 1 second.*/
        new Handler().postDelayed(() -> {
            /* Creates an Intent that will start the MainActivity. */
            Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
            SplashActivity.this.startActivity(mainIntent);
            SplashActivity.this.finish();
        }, SPLASH_DISPLAY_LENGTH);
    }
}
