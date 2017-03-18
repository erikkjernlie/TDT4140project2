package test;

import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.erikkjernlie.tdt4140project.Add_information;
import com.firebase.client.Firebase;
import com.firebase.client.core.Context;

/**
 * Created by jonas on 17.03.2017.
 */

public class SendToDatabase {

    Context context;

    private Firebase mRef;

    public SendToDatabase() {

        Add_information add_information = new Add_information();


        Firebase.setAndroidContext(add_information.getApplicationContext());

        // TEST uten AsyncTask
        String url = "https://tdt4140project2.firebaseio.com/";

        String infoLinje = "infoLinje/";

        url = url + infoLinje;

        mRef = new Firebase(url);

        String navnPaVariabele = "ict";


        Firebase mRefChild = mRef.child(navnPaVariabele);

        mRefChild.setValue("IKT ER GØY");






        // SLUTT TEST



        (new AsyncTask<String, String, String>() {

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


    }




}
