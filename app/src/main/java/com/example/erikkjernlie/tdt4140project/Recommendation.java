package com.example.erikkjernlie.tdt4140project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class Recommendation extends AppCompatActivity {

    private TextView study;
    private TextView about_study;
    private TextView job_opportunities;
    private TextView social_environement;
    private TextView because;
    private ImageView picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String st = "informatics";
        setContentView(R.layout.activity_recommendation);
        picture = (ImageView) findViewById(R.id.linjeforening_rec);
        study = (TextView) findViewById(R.id.linje_rec);
        job_opportunities = (TextView) findViewById(R.id.job_opportunities_rec);
        social_environement = (TextView) findViewById(R.id.social_environment_rec);
        because = (TextView) findViewById(R.id.why_rec);

        if (st.equals("engineering and ict")) {
            picture.setImageResource(R.drawable.hybrida_logo);
            study.setText("Engineering and ICT");
            job_opportunities.setText("This is very interesting");
        } else if (st.equals("indok")){
            picture.setImageResource(R.drawable.janus);
            study.setText("Industrial Economics");
        } else if (st.equals("data")){
            picture.setImageResource(R.drawable.abakus);
            study.setText("DATA Technology");
        } else if (st.equals("informatics")){
            picture.setImageResource(R.drawable.onlinelogo);
            study.setText("Informatics");
        } else {
            setContentView(R.layout.no_recommendation);
        }




    }
}
