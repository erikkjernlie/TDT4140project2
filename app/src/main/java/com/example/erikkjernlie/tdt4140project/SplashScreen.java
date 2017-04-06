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

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    //tid visning, splashscreen
    public static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //sender deg videre til homescreen
                if (isNetworkAvailable()) {
                    Intent homeIntent = new Intent(SplashScreen.this, Sign_in.class);
                    startActivity(homeIntent);
                    finish();
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "No internet connection found. Please connect to a web host and reopen the app.";
                    int duration = Toast.LENGTH_SHORT;
                    final Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    new CountDownTimer(9000, 1000)
                    {

                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.show();}

                    }.start();
                }
            }
        }, SPLASH_TIME_OUT);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
