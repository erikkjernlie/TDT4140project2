package com.example.erikkjernlie.tdt4140project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import java.util.List;

public class Add_information extends AppCompatActivity{

    private ImageButton female;
    boolean isPressedFemale = false;
    boolean isPressedMan = false;
    private ImageButton man;
    private char gender;
    private int year;
    Button dropdown;
    List<String> selectedCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        initGenderButtons();
        numberPicker();

        dropdown = (Button) findViewById(R.id.dropdown);
        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings = { "Matematikk R1", "Matematikk R2", "Fysikk 1", "Fysikk 2", "Informasjonsteknologi" };
                MultiSelectSpinner mySpin = (MultiSelectSpinner)findViewById(R.id.my_spin);
                mySpin.setItems(strings);
                mySpin.performClick();
                selectedCourses = mySpin.getSelectedStrings();
            }
        });


    }


    public void initGenderButtons() {
        female = (ImageButton) findViewById(R.id.female);
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPressedFemale) {
                    female.setImageResource(R.drawable.female_notselected);
                } else {
                    //this is because you can't select male and female
                    female.setImageResource(R.drawable.female_selected);
                    if (isPressedMan) {
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
                if (isPressedMan) {

                    man.setImageResource(R.drawable.man_notselected);
                    //this is because you can't select male and female
                } else {
                    man.setImageResource(R.drawable.man_selected);
                    if (isPressedFemale) {
                        female.setImageResource(R.drawable.female_notselected);
                        isPressedFemale = false;
                    }

                }
                isPressedMan = !isPressedMan;
            }
        });
        if (isPressedMan) {
            gender = 'M';
        } else if (isPressedFemale) {
            gender = 'F';
        } //the user can select none of them, but then it will be an error at the end

    }

    private void numberPicker(){
        NumberPicker np = (NumberPicker) findViewById(R.id.np);


        //Set the minimum value of NumberPicker
        np.setMinValue(1985);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(2002);
        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);
        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                year = newVal;

            }
        });

    }




}
