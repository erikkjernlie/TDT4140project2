package com.example.erikkjernlie.tdt4140project;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

public class SendToDatabase extends AppCompatActivity {

    private Firebase mRef;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to_database);

        System.out.println("alsdøladsølaøsdla");
        Firebase.setAndroidContext(SendToDatabase.this);
        System.out.println("aksdlkalsd");

        auth = auth.getInstance();

        String url = "https://tdt4140project2.firebaseio.com/";

        String infoLinje = "infoLinje";

        url = url + infoLinje;

        mRef = new Firebase(url);

        auth.signInWithEmailAndPassword("jaja@neinei.com", "123456");

        System.out.println("asdøasdlø");

        send();

        retrieve();

    }


    private void send() {

        String navnPaVariabele = "indokData";

        Firebase mRefChild = mRef.child(navnPaVariabele);

        mRefChild.setValue("indok e homoo");

    }

    private void retrieve() {

        mRef.child("ict").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("12345678");
                System.out.println(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
    }

    public void finnesDenne() {
    }

      /*  (new AsyncTask<String, String, String>() {

        @Override
        protected String doInBackground(String... params) {
            String url = "https://tdt4140project2.firebaseio.com/";

            String infoLinje = "infoLinje/";

            url = url + infoLinje;

            mRef = new Firebase(url);

            String navnPaVariabele = "ict";


            Firebase mRefChild = mRef.child(navnPaVariabele);

            mRefChild.setValue("IKT ER GØY");
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println("DETTE FUNKET");
            System.out.println(s);
            super.onPostExecute(s);
        }
    }).execute();
*/

}
