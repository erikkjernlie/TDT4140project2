/*  SplashScreen
 *
 *  SplashScreen that appears when the user opens the app.
 *  This is visible for SPLASH_TIME_OUT-seconds, which is set to 3000 milliseconds,
 *  and contains a simple background with our logo.
 *
 *  Created by Erik Kjernlie
 *  Copyright © uniBOT
 */

package com.example.erikkjernlie.tdt4140project;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    //tid visning, splashscreen
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //sender deg videre til homescreen
                Intent homeIntent = new Intent(SplashScreen.this, Sign_in.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
