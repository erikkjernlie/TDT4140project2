package com.example.erikkjernlie.tdt4140project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class Add_information extends AppCompatActivity{

    private ImageButton female;
    boolean isPressedFemale = false;
    boolean isPressedMan = false;
    private ImageButton man;
    private char gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        initGenderButtons();
    }


    public void initGenderButtons(){
        female = (ImageButton) findViewById(R.id.female);
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPressedFemale) {
                    female.setImageResource(R.drawable.female_notselected);
                } else{
                    //this is because you can't select male and female
                    female.setImageResource(R.drawable.female_selected);
                    if (isPressedMan){
                        man.setImageResource(R.drawable.man_notselected);
                        isPressedMan = false;
                    }


                }
                isPressedFemale = !isPressedFemale;
            }
        });
        man = (ImageButton) findViewById(R.id.man);
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPressedMan) {

                    man.setImageResource(R.drawable.man_notselected);
                    //this is because you can't select male and female
                } else {
                    man.setImageResource(R.drawable.man_selected);
                    if (isPressedFemale){
                        female.setImageResource(R.drawable.female_notselected);
                        isPressedFemale = false;
                    }

                }
                isPressedMan = !isPressedMan;
            }
        });
        if (isPressedMan){
            gender = 'M';
        } else if (isPressedFemale){
            gender = 'F';
        } //the user can select none of them, but then it will be an error at the end
    }

}
