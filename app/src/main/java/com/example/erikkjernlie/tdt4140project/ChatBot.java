package com.example.erikkjernlie.tdt4140project;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonElement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.android.AIService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;


public class ChatBot extends AppCompatActivity {
    private static final String TAG = "ChatActivity";

    private int birthYear;
    private double calculatedGrade;
    private ArrayList<String> courses;
    private ArrayList<String> extraEducation;
    private char gender;
    private int R2Grade;

    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private boolean side = false;
    private AIService aiService;
    public Button listenButton;
    private TextView resultTextView;
    private AIConfiguration config;
    private AIDataService aiDataService;
    private TextView uniBot;
    private TextView back;
    private TextView help;
    final Context context = this;
    private int randomNumber = -1;
    private ArrayList<String> sentencesToUnibot;
    private ArrayList<String> sentencesOutput;

    FirebaseAuth firebaseAuth;
    Firebase mRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 2);

        setContentView(R.layout.chatbot);

        Firebase.setAndroidContext(ChatBot.this);

        firebaseAuth = firebaseAuth.getInstance();

        //firebaseAuth.signInWithEmailAndPassword("jaja@neinei.com", "123456");

        //mRef = new Firebase("https://tdt4140project2.firebaseio.com/" +
        //      firebaseAuth.getCurrentUser().getUid());

        mRef = new Firebase("https://tdt4140project2.firebaseio.com/" +
                firebaseAuth.getCurrentUser().getUid());


        buttonSend = (Button) findViewById(R.id.send);

        listView = (ListView) findViewById(R.id.msgview);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.right_chat);
        listView.setAdapter(chatArrayAdapter);

        chatText = (EditText) findViewById(R.id.msg);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
        config = new AIConfiguration("3e19cea342f04764b1665e37099f1e23", // denne burde egentlig være skjult
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.Speaktoit);

        aiDataService = new AIDataService(this, config);


        initTextButtons();
        getInfoDatabase();

    }

    public void getInfoDatabase() {
        mRef.child("BirthYear").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setBirthYear(dataSnapshot.getValue(Integer.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                firebaseError.getMessage();
            }
        });

        mRef.child("CalculatedGrade").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setCalculatedGrade(dataSnapshot.getValue(Double.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        mRef.child("Courses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setCourses(dataSnapshot.getValue(ArrayList.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        mRef.child("Extra education").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setExtraEducation(dataSnapshot.getValue(ArrayList.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        mRef.child("Gender").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setGender(dataSnapshot.getValue(Character.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        mRef.child("R2Grade").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setR2Grade(dataSnapshot.getValue(Integer.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public String displayUserInformation() {
        String userInfo = "";
        userInfo += "Your birth year: " + this.birthYear + "\n";
        userInfo += "Your calculated grade: " + this.calculatedGrade + "\n";
        userInfo += "Your courses: ";
        for (int i = 0; i < this.courses.size(); i++) {
            userInfo += this.courses.get(i) + ", ";
        }
        //Deletes the last comma
        userInfo = userInfo.substring(0, userInfo.length() - 2) + "\n";
        userInfo += "Your extra education: ";
        for (int i = 0; i < this.extraEducation.size(); i++) {
            userInfo += this.extraEducation.get(i) + ", ";
        }
        //Deletes the last comma
        userInfo = userInfo.substring(0, userInfo.length() - 2) + "\n";
        userInfo += "Your gender: " + this.gender + "\n";
        userInfo += "Your R2-grade: " + this.R2Grade;
        return userInfo;
    }

    private void initTextButtons() {
        uniBot = (TextView) findViewById(R.id.unibot);
        back = (TextView) findViewById(R.id.back);
        help = (TextView) findViewById(R.id.help);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatBot.this, Menu.class);
                startActivity(i);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title
                alertDialogBuilder.setTitle("Help with uniBOT");

                // set dialog message
                alertDialogBuilder
                        .setMessage("uniBOT can help you with\n- Information about a specific study" +
                                "\n- Compare different studies\n- Give you information about a study's union\n- Print available studies\nand lots of other random things.\n"
                                + "\nDo you need more help or information about how to ask questions? Type help in the chat."
                                + "\n\nYou can also press the uniBOT button at the top for random questions.")
                        .setCancelable(false)
                        .setPositiveButton("I don't need any more help", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
        uniBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sentencesOutput = new ArrayList<String>(Arrays.asList("Do you want to know about physics?", "Do you want to compare some studies?", "Do you want to see a list of studies that you can compare?"));

                Random rn = new Random();
                int range = sentencesOutput.size();


                randomNumber = rn.nextInt(range);
                addMessageToChatArray(sentencesOutput.get(randomNumber));

            }
        });
    }

    //translates the printed question to a format API.AI understands, so the user can answer directly
    private void translationFromUserToAI() {
        sentencesToUnibot = new ArrayList<String>(Arrays.asList("Tell me about physics", "I want to compare some studies", "print"));
        getAiResponse(sentencesToUnibot.get(randomNumber));
    }

    //this is where the messages are received and sent
    private boolean sendChatMessage() {
        String messageFromUser = chatText.getText().toString();

        //For displayUserInformation:
        if (messageFromUser.isEmpty()) {
            return false;
        }

        if (!messageFromUser.isEmpty()) { // sjekker at meldingen ikke er tom
            chatArrayAdapter.add(new ChatMessage(side, messageFromUser));
        }

        chatText.setText(""); //resets the chatbox
        //here comes the response

        //checks if the user wants to answer the random question.
        // Also checks if the last question uniBOT printed equals the question that was printed because the user pressed the uniBOT-button
        //This needs to be checked, or else the user can type "yes" whenever (s)he wants, and the answer to the last question will be printed
        System.out.println(messageFromUser.toString());
        if (messageFromUser.toLowerCase().equals("yes") && randomNumber > -1 && chatArrayAdapter.getItem(chatArrayAdapter.getCount() - 2).toString().equals(sentencesOutput.get(randomNumber))) {
            System.out.println(chatArrayAdapter.getItem(chatArrayAdapter.getCount() - 2).toString());
            translationFromUserToAI();
            return true;
        } else if (messageFromUser.toLowerCase().equals("no")  && (chatArrayAdapter.getCount() > 2) && chatArrayAdapter.getItem(chatArrayAdapter.getCount() - 2).toString().equals(sentencesOutput.get(randomNumber) )) {
            addMessageToChatArray("You can always press the uniBOT-button to get more random questions. ");
            return true;
        }


        getAiResponse(messageFromUser);

        return true;
    }

    private void getAiResponse(String a) {

        final AIRequest aiRequest = new AIRequest();
        if (!a.isEmpty()) {
            aiRequest.setQuery(a);
        }

        // Siden aiDataService må kjøres på en backgroundtråd bruker vi AsyncTask til å hente svaret
        new AsyncTask<AIRequest, Void, AIResponse>() {
            @Override
            protected AIResponse doInBackground(AIRequest... requests) {
                final AIRequest request = requests[0];
                try {
                    final AIResponse response = aiDataService.request(aiRequest); // Henter svar
                    return response;
                } catch (AIServiceException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(AIResponse aiResponse) {
                if (aiResponse != null) {
                    String response = processAiResponse(aiResponse);
                    addMessageToChatArray(response);
                    //addMessageToChatArray(aiResponse.getResult().getFulfillment().getSpeech()); // returnere svar når ferdig
                    System.out.println(aiResponse.getResult());
                    System.out.println(aiResponse.getResult().getParameters());

                    System.out.println("asdølaøsdl");

                }
            }
        }.execute(aiRequest);
    }

    private String processAiResponse(AIResponse response) {

        if(response.getResult().getAction().toString().contains("displayUserInformation")) {
            return displayUserInformation();
        }
        if(response.getResult().getAction().contains("getInformation")) {
            getInformation(response.getResult().getParameters().get("Studies").toString());
        }
        return response.getResult().getFulfillment().getSpeech();

    }

    //This method retrieves information about the study the user wants to know more about
    private void getInformation(String study) {
        //Sends a StudyProgramInfo-object to the database (TEST)
        Firebase infoRef = new Firebase("https://tdt4140project2.firebaseio.com/Studies");
    }

    public void setStudyInformations (String study) {
        //Sends a StudyProgramInfo-object to the database (TEST)
        GetInfo getInfo = new GetInfo();
        ArrayList<String> studySpecialization = new ArrayList<>(Arrays.asList("ICT and production development", "ICT and construction"));
        String miljo = "Line Association: hybrida line Society for Engineering and ICT called Hybrida. Perhaps the most important task is to contribute to the social environment in the program. Line Society has therefore responsible for events like the buddy program, entrance examination, matriculation party, sports, graduation parties and Christmas shutdown. Early in the New Year organizes Hybrida trip to Åre in Sweden. These are not alone, as almost all NTNU packages skiing and snowboarding and pulls eastward to create revel in one of Scandinavia's premier resorts. Hybrida is also an important link between students and faculty, industry and the other degree programs at the university. Hybridajentene ICT girls conducts activities of both the academic and social nature with a focus on girls, for, inter alia, strengthen solidarity on the line. Mentor Period One of the first you meet as a new student at the Engineering and ICT is a well-organized buddy organized by student association hybrida. The new students are divided into small groups and each group is assigned a mentor. The mentor is a student of higher classes, who want to help new students get to know the student town of Trondheim and NTNU. This is also a golden opportunity to meet their first new friends in Trondheim. During the buddy program includes a number of social activities like barbecues, football, guided tours through the city and all sorts of festivities. Ends with trials for admission to the bar association, and the infamous badekarpadlingen on Nidelva. After trials follows a solemn matriculation party, where you are warmly welcomed to the student community by Engineering and ICT. Integrated use of data in education for students of Engineering and ICT will use the data to be more integrated in the teaching than many of the other degree programs at the university. The study therefore put in place for the use of their own laptop. NTNU and study program prepares each year a student offer notebook and the software you need at the start of the study. This is a favorable alternative to computer labs. The first weeks after start getting some students from higher classes assigned to help the new students to get started using your own PC. Students will receive training and supervision in the use of PC and software. NTNU also has its own support to all students. Engineering and ICT is a demanding course of study there including programming and technical engineering skills are used together to create the future of technology. Be aware that the program uses software that makes heavy demands on the computer. Read more about this in technocrats start their sides. StudiebyEN Trondheim Trondheim has several times been voted the best student city. One of the reasons for this are the dozens of student organizations that exist. These are helping to contribute to a diverse and exciting study existence. Union, UKA, biannual, Tekna and NTNUI are all examples of gathering places for engagement, learning and well-being. Here the students who want it is also useful organizational and work experience.";
        // denne ble henta med jSoup og translata med google

        Firebase infoRef = new Firebase("https://tdt4140project2.firebaseio.com/Studies");
        StudyProgramInfo info = new StudyProgramInfo(
                getInfo.getBasicInformation("mting"),
                54.4, true, 2, "Hybrida", studySpecialization, miljo);


      //  infoRef.child(study).setValue(info);
    }

    private void addMessageToChatArray(String message) {
        chatArrayAdapter.add(new ChatMessage(true, message));
    }

    public void listenButtonOnClick(View view) {
        aiService.startListening();
    }

    public void onResult(final AIResponse response) {
        if (response.isError()) {

        }
        Result result = response.getResult();


        // Get parameters
        String parameterString = "";
        if (result.getParameters() != null && !result.getParameters().isEmpty()) {
            for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                parameterString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
            }
        }
        // Show results in TextView.
        resultTextView.setText("Query:" + result.getResolvedQuery() +
                "\nAction: " + result.getAction() +
                "\nParameters: " + parameterString);
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public double getCalculatedGrade() {
        return calculatedGrade;
    }

    public void setCalculatedGrade(double calculatedGrade) {
        this.calculatedGrade = calculatedGrade;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    public ArrayList<String> getExtraEducation() {
        return extraEducation;
    }

    public void setExtraEducation(ArrayList<String> extraEducation) {
        this.extraEducation = extraEducation;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getR2Grade() {
        return R2Grade;
    }

    public void setR2Grade(int r2Grade) {
        R2Grade = r2Grade;
    }

}