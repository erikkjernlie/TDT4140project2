package com.example.erikkjernlie.tdt4140project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Recommendation extends AppCompatActivity {

    private TextView study;
    private TextView about_study;
    private TextView job_opportunities;
    private TextView social_environement;
    private TextView because;
    private ImageView picture;
    private HashMap<String, StudyProgramInfo> studyPrograms;
    private UserInfo userInfo;
    private String beststudy;
    private ArrayList<String> beststudy_interests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        Firebase fb = new Firebase("https://tdt4140project2.firebaseio.com/");
//        fb.child("StudyProgram").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    studyPrograms.put(snapshot.getKey(), snapshot.getValue(StudyProgramInfo.class));
//                }
//            }
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//        fb.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                userInfo = dataSnapshot.getValue(UserInfo.class);
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });

        this.userInfo = UserInfo.userInfo;
        this.studyPrograms = UserInfo.studyPrograms;
        recommendStudy();

        System.out.println(this.beststudy);
        System.out.println(this.beststudy_interests);

        initialization();
        setContentView(R.layout.activity_recommendation);

    }
    private void initialization(){
        // study, job_opportunities osv. blir alltid null....
        picture = (ImageView) findViewById(R.id.linjeforening_rec);
        study = (TextView) findViewById(R.id.linje_rec);
        job_opportunities = (TextView) findViewById(R.id.job_opportunities_rec);
        social_environement = (TextView) findViewById(R.id.social_environment_rec);
        because = (TextView) findViewById(R.id.why_rec);

        if (beststudy != null && beststudy_interests != null) {
            job_opportunities.setText(studyPrograms.get(beststudy).getCommonWorkFields().toString());
            social_environement.setText(studyPrograms.get(beststudy).getStudyEnvironment());
            study.setText(beststudy);
            String b = "";
            for (String interest : beststudy_interests) {
                b += interest + ", ";
            }
            b = b.substring(0, b.length() - 2) + ".";
            because.setText("We think you would like " + beststudy + " because of your following interests:\n" + beststudy_interests);
            if (beststudy.toLowerCase().equals("engineering and ict")) {
                picture.setImageResource(R.drawable.hybrida_logo);
            } else if (beststudy.toLowerCase().equals("industrial economics and technology management and ict")) {
                picture.setImageResource(R.drawable.janus);
            } else if (beststudy.toLowerCase().equals("computer science")) {
                picture.setImageResource(R.drawable.abakus);
            } else if (beststudy.toLowerCase().equals("informatics")) {
                picture.setImageResource(R.drawable.onlinelogo);
            }
        } else {
            setContentView(R.layout.no_recommendation);
        }
    }
    private void recommendStudy() {
        // Henter alle interessene til brukeren, og sammenligner med keywordene til alle studiene.
        // Legger til en int til hvert studie, det studiet med høyest ints, blir anbefalt.
        
        HashMap<String, Integer> pointMap = new HashMap<>(); // hashmap som skal inneholder alle studienavnene, og koble det opp mot antall keywordstreff

        ArrayList<String> interests = UserInfo.userInfo.getInterests(); // interessene til brukeren

        Iterator<String> iterator = UserInfo.studyPrograms.keySet().iterator(); // iterator som går gjennom alle studienavnene

        HashMap<String, ArrayList<String>> keyWords = new HashMap<>(); // hashmap som skal holde alle interessene til hvert studie

        HashMap<String, ArrayList<String>> matchedInterests = new HashMap<>(); // hashmap som skal holde på alle interessene

        if (interests == null || interests.size() == 1) {
            beststudy = null;
            interests = null;
        }

        while (iterator.hasNext()) {
            String study = iterator.next();
            keyWords.put(study, studyPrograms.get(study).getKeywords());
            pointMap.put(study, 0);
            matchedInterests.put(study, new ArrayList<String>());
        }

        // går gjennom alle studiene, legger til poeng på pointsMap, om interessen er en av keywordsa
        for (String study : studyPrograms.keySet()) {
            System.out.println(study);
            for (String interest : interests) {
                if (interest != null) {
                    interest = interest.toLowerCase();
                }

                if (keyWords.get(study).contains(interest)) {
                    pointMap.put(study, pointMap.get(study) + 1); // legger til 1 verdi på det gitte studiet
                    matchedInterests.get(study).add(interest);  // legger til interessen til studiet
                }
            }
        }

        Iterator<String> iterator1 = studyPrograms.keySet().iterator();
        if (iterator1.hasNext()) {
            String bestStudy1 = iterator1.next();

            while (iterator1.hasNext()) {
                String nextStudy = iterator1.next();
                if (pointMap.get(bestStudy1) < pointMap.get(nextStudy)) {
                    bestStudy1 = nextStudy;
                }
            }

            if (matchedInterests.get(bestStudy1).size() == 0) {
                beststudy = null;
                beststudy_interests = null;
            }
            beststudy_interests = matchedInterests.get(bestStudy1);
            this.beststudy = bestStudy1;

        } else{
            beststudy = null;
            beststudy_interests = null;
        }



    }

}
