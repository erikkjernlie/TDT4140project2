/*  ChatBot
 *
 *  This is the class where the chatting happends. Connecting with API.AI and firebase.
 *  Sends the user's input to a format API.AI understands, and responds the user right away
 *
 *  Created by Erik Kjernlie
 *  Copyright © uniBOT
 */

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
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.JsonElement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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
    private UserInfo user;

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
    private HashMap<String, StudyProgramInfo> studyPrograms;

    FirebaseAuth firebaseAuth;
    Firebase mRef;
    //gets the required access from API.AI a
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 2);

        setContentView(R.layout.chatbot);

        Firebase.setAndroidContext(ChatBot.this);

        firebaseAuth = firebaseAuth.getInstance();

        firebaseAuth.signInWithEmailAndPassword("jaja@neinei.com", "123456");

        mRef = new Firebase("https://tdt4140project2.firebaseio.com/" +
                firebaseAuth.getCurrentUser().getUid());

        studyPrograms = new HashMap<>();

        buttonSend = (Button) findViewById(R.id.send);

        listView = (ListView) findViewById(R.id.msgview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                hideKeyboard(v);
            }
        });

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
        config = new AIConfiguration("97d4794875414bacb99fc54ba5de1086",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.Speaktoit);

        aiDataService = new AIDataService(this, config);


        getInformation();
        initTextButtons();
        getInfoDatabase();
    }

    //Retrieving information from the spezified fields from the firebase-database
    public void getInfoDatabase() {

        Firebase userInfoRef = new Firebase("https://tdt4140project2.firebaseio.com/Users/");

        userInfoRef.child(firebaseAuth.getCurrentUser().getUid()).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println("GGGGGG");
                        System.out.println(dataSnapshot.getValue(UserInfo.class));
                        setUser(dataSnapshot.getValue(UserInfo.class));
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {}
                });

        /*mRef.child("BirthYear").addListenerForSingleValueEvent(new ValueEventListener() {
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
        });*/
    }

    public String displayUserInformation() {
        String userInfo = this.user.toString();
        return userInfo;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(ChatBot.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
        // alertDialog if the user presses the help button
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

                sentencesOutput = new ArrayList<String>(Arrays.asList("Do you want to know about Engineering and ICT?", "Do you want to compare some studies?", "Do you want to see a list of studies that you can compare?"));

                Random rn = new Random();
                int range = sentencesOutput.size();


                randomNumber = rn.nextInt(range);
                addMessageToChatArray(sentencesOutput.get(randomNumber));

            }
        });
    }

    //translates the printed question to a format API.AI understands, so the user can answer directly
    private void translationFromUserToAI() {
        sentencesToUnibot = new ArrayList<String>(Arrays.asList("Tell me about engineering and ict", "I want to compare some studies", "print"));
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
        } else if (messageFromUser.toLowerCase().equals("no") && (chatArrayAdapter.getCount() > 2) && chatArrayAdapter.getItem(chatArrayAdapter.getCount() - 2).toString().equals(sentencesOutput.get(randomNumber))) {
            addMessageToChatArray("You can always press the uniBOT-button to get more random questions. ");
            return true;
        }


        getAiResponse(messageFromUser);

        return true;
    }

    private void getAiResponse(String a) {
        //IF YOU WANT TO INSERT THE 4 STUDIES INTO THE DATABASE
        /*if (a.equals("setInformatics")) {
            setStudyInformation(new StudyProgramInfo(51.2, false,
                    new ArrayList<String>(Arrays.asList("Data", "IKT", "IT")),
                    new ArrayList<String>(Arrays.asList("Data", "Consultant")), "Informatics is a bachelor",
                    "Very good environment", "Online",
                    new ArrayList<String>(Arrays.asList("TDT4100", "TDT4120"))), "Informatics");
        }
        if (a.equals("setICT")) {
            setStudyInformation(new StudyProgramInfo(54.4, true,
                    new ArrayList<String>(Arrays.asList("Data", "IKT", "Engineering")),
                    new ArrayList<String>(Arrays.asList("Consultant", "Engineer")), "ICT is a master",
                    "Amazing environment", "Hybrida",
                    new ArrayList<String>(Arrays.asList("TMA4100", "TDT4100", "TDT4120"))),
                    "Engineering and ICT");
        }
        if (a.equals("setIndok")) {
            setStudyInformation(new StudyProgramInfo(64.0, false,
                    new ArrayList<String>(Arrays.asList("Data", "IKT", "Economics")),
                    new ArrayList<String>(Arrays.asList("Data", "Consultant", "Leader")),
                    "Indok is very weird",
                    "Very bad environment", "Janus",
                    new ArrayList<String>(Arrays.asList("TDT4100", "TDT4120"))),
                    "Industrial Economics and Technology Management and ICT");
        }
        if (a.equals("setData")) {
            setStudyInformation(new StudyProgramInfo(57.0, true,
                    new ArrayList<String>(Arrays.asList("Data", "IKT", "IT")),
                    new ArrayList<String>(Arrays.asList("Data", "Consultant", "Programmer")),
                    "Computer science is a master",
                    "Okey environment", "Abakus",
                    new ArrayList<String>(Arrays.asList("TDT4100", "TDT4120"))),
                    "Computer Science");
        }*/


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


                }
            }
        }.execute(aiRequest);
    }


    private String processAiResponse(AIResponse response) {

        if (response.getResult().getAction().toString().contains("ExploreMyself")) {
            return displayUserInformation();
        }
        if(response.getResult().getAction().contains("getInfo")) {
            if ((response.getResult().getParameters().get("StudyProgram") != null)) {
                return studyPrograms.get(response.getResult().getParameters().get("StudyProgram").
                        toString().replace("\"", "")).toString();
            }
        }
        return response.getResult().getFulfillment().getSpeech();

    }

    //This method retrieves information about the study the user wants to know more about
    private void getInformation() {
        //Sends a StudyProgramInfo-object to the database (TEST)
        Firebase infoRef = new Firebase("https://tdt4140project2.firebaseio.com/Studies/");
        infoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    System.out.println("AAAAAA");
                    System.out.println(snapshot.getKey());
                    addStudyPrograms(snapshot.getKey(), snapshot.getValue(StudyProgramInfo.class));
                    System.out.println(snapshot.getValue(StudyProgramInfo.class).toString());
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void setStudyInformation(StudyProgramInfo info, String study) {
        Firebase infoRef = new Firebase("https://tdt4140project2.firebaseio.com/Studies/");
        infoRef.child(study).setValue(info);
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


    //getters and setters
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

    public void addStudyPrograms(String study, StudyProgramInfo info) {
        this.studyPrograms.put(study, info);
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

}