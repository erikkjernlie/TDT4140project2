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
import android.provider.ContactsContract;
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

    private UserInfo user;
    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private boolean side = false;
    private AIConfiguration config;
    private AIDataService aiDataService;
    private TextView uniBot;
    private TextView back;
    private TextView help;
    final Context context = this;
    private int randomNumber = -1;
    private ArrayList<String> sentencesToUnibot;
    private ArrayList<String> sentencesOutput;
    private ArrayList<String> usedInterests = new ArrayList<>();
    private int interviewNumber = 0;

    // fields for the interview
    private boolean interview = false; // if the user is doing the interview
    private String interest;


    // comment added to create mergeconflict

    //gets the required access from API.AI a
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 2); // not in use

        setContentView(R.layout.chatbot);

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

        initTextButtons();
        addMessageToChatArray("Hey! My name is uniBOT, and I'm here to help you with study- and student opportunities at NTNU Trondheim. \nYou can ask me almost anything related to our data-orientated studies. Perhaps you'd like to compare a couple studies? Or submit some interests and let me make a study recommendation?\n" +
                "\nIf you wish to see more examples, click the 'HELP'-button in the top right corner. You can also press 'UNIBOT' in the header to let me prompt you with some questions. I look forward to assisting you!");
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
                                "\n- Compare different studies\n- Give you information about a study's union\n- Print available studies\n- Study Recommendations\nand lots of other random things.\n"
                                + "\nWe recommend that you communicate with the bot using simple language and short sentences."
                                + "\n\nDo you need more help or information about how to ask questions? Type help in the chat."
                                + "\n\nYou can also press the uniBOT button at the top for random questions.\n")
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

                sentencesOutput = new ArrayList<String>(Arrays.asList("Do you want to know about a study?", "Do you want to compare some studies?", "Do you want to see a list of studies that we support?", "Do you want me to interview you?", "Do you want to" +
                        "learn about yourself?"));

                Random rn = new Random();
                int range = sentencesOutput.size();

                int newRandom = rn.nextInt(range);
                while (randomNumber == newRandom) {
                    newRandom = rn.nextInt(range);
                }
                randomNumber = newRandom;
                addMessageToChatArray(sentencesOutput.get(randomNumber));

            }
        });
    }

    //translates the printed question to a format API.AI understands, so the user can answer directly
    private void translationFromUserToAI() {
        sentencesToUnibot = new ArrayList<String>(Arrays.asList("Tell me about a study", "I want to compare some studies", "Show me a list of studies", "I want to be interviewed.", "tell me about me"));
        getAiResponse(sentencesToUnibot.get(randomNumber));
    }

    //this is where the messages are received and sent
    private boolean sendChatMessage() {
        String messageFromUser = chatText.getText().toString();

        ArrayList<String> positiveResponse = new ArrayList<>(Arrays.asList(new String[]{"yes", "mhm", "jepp", "yeh", "yes please", "please", "definitely", "absolutely"}));

        //For displayUserInformation:
        if (messageFromUser.isEmpty()) {
            return false;
        }

        if (!messageFromUser.isEmpty()) { // sjekker at meldingen ikke er tom
            chatArrayAdapter.add(new ChatMessage(side, messageFromUser));
        }

        if (interview) {

            if (messageFromUser.toLowerCase().equals("quit") || checkEnoughInterests() != null) {
                interview = false;
                chatText.setText("");
                user.updateFirebase();
                addMessageToChatArray("Thank you for the interview. We will now try to find you a suitable study.");
                getAiResponse("Can you recommend me a study?");
                return true;
            } else if (positiveResponse.contains(messageFromUser.toLowerCase())) {
                handleInterview();
            }
            usedInterests.add(interest);

        }

        chatText.setText(""); //resets the chatbox
        //here comes the response

        //checks if the user wants to answer the random question.
        // Also checks if the last question uniBOT printed equals the question that was printed because the user pressed the uniBOT-button
        //This needs to be checked, or else the user can type "yes" whenever (s)he wants, and the answer to the last question will be printed
        if (messageFromUser.toLowerCase().equals("yes") && randomNumber > -1 && chatArrayAdapter.getItem(chatArrayAdapter.getCount() - 2).toString().equals(sentencesOutput.get(randomNumber))) {
            translationFromUserToAI();
            return true;
        } else if (chatArrayAdapter != null && sentencesOutput != null && messageFromUser.toLowerCase().equals("no") && (chatArrayAdapter.getCount() > 2) && chatArrayAdapter.getItem(chatArrayAdapter.getCount() - 2).toString().equals(sentencesOutput.get(randomNumber))) {
            addMessageToChatArray("You can always press the uniBOT-button to get more random questions. ");
            return true;
        }

        getAiResponse(messageFromUser);

        return true;
    }

    private void handleInterview() {
        if (!user.getInterests().contains(interest)) {
            user.addInterests(interest);
            user.updateFirebase();
        }
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
                }
            }
        }.execute(aiRequest);
    }

    private String processAiResponse(AIResponse response) {

        // Denne metoden skal lage et objekt av ProcessAiResponse klassen, og kalle på en av dens metoder
        // klassen må ta inn infoen den trenger, dvs studyinfo listen

        String ut = null;


        ProcessAiResponse processAiResponse = new ProcessAiResponse(StudyProgramInfo.studyPrograms, UserInfo.userInfo, Union.unions);

        if (interview) {
            ArrayList<String> prompts = new ArrayList<>(Arrays.asList(new String[]{"Are you interested in ", "Do you like ", "Do you enjoy ", "Would you like to work with "})); // Denne kan gjerne økes.
            ArrayList<String> interests = new ArrayList<>();

            for (String study : StudyProgramInfo.studyPrograms.keySet()) {
                for (String interest : StudyProgramInfo.studyPrograms.get(study).getKeywords()) {
                    if (!interests.contains(interest) && !usedInterests.contains(interest)) {
                        interests.add(interest);
                    }
                    if (usedInterests.contains(interest)) {
                        System.out.println(interest);
                    }
                }
            }
            System.out.println();

            interest = interests.get(new Random().nextInt(interests.size())); // skal være random interest
            interviewNumber = (interviewNumber + 1) % 3;
            if (interviewNumber == 0) {
                return prompts.get(new Random().nextInt(prompts.size())) + interest + "? Remember, you can press 'quit' to stop the interview.";
            }

            return prompts.get(new Random().nextInt(prompts.size())) + interest + "?";
        } else {
            if (response.getResult().getFulfillment().getSpeech().equals("")) {
                ut = processAiResponse.processAiRespons(response);
            } else {
                ut = response.getResult().getFulfillment().getSpeech().toString();
            }

            if (ut.equals("startInterview")) {
                interview = true;
                addMessageToChatArray("We will now start an interview and try to find a study that matches your interests. Please write 'quit' to stop the interview. ");
                ArrayList<String> prompts = new ArrayList<>(Arrays.asList(new String[]{"Are you interested in ", "Do you like ", "Do you enjoy ", "Would you like to work with "})); // Denne kan gjerne økes.
                ArrayList<String> interests = new ArrayList<>();

                for (String study : StudyProgramInfo.studyPrograms.keySet()) {
                    for (String interest : StudyProgramInfo.studyPrograms.get(study).getKeywords()) {
                        if (!interests.contains(interest)) {
                            interests.add(interest);
                        }
                    }
                }
                Random random = new Random();

                int randomInt = random.nextInt(interests.size());

                // må legges til at man ikke velger en fra useInterests lista

                usedInterests.add(interests.get(randomInt));

                ut = prompts.get(new Random().nextInt(prompts.size())) + interests.get(randomInt) + "?"; // '0' må byttes ut med random tall
            }

        }


        return ut;


    }

    private String checkEnoughInterests() {
        // Method will check if there is a study that has a lead with 3 interests. In that case
        // we will stop the interview. In the other case the method will return null object

        HashMap<String, Integer> pointMap = new HashMap<>(); // hashmap som skal inneholder alle studienavnene, og koble det opp mot antall keywordstreff

        ArrayList<String> interests = user.getInterests(); // interessene til brukeren

        Iterator<String> iterator = StudyProgramInfo.studyPrograms.keySet().iterator(); // iterator som går gjennom alle studienavnene

        HashMap<String, ArrayList<String>> keyWords = new HashMap<>(); // hashmap som skal holde alle interessene til hvert studie

        HashMap<String, ArrayList<String>> matchedInterests = new HashMap<>(); // hashmap som skal holde på alle interessene

        //
        while (iterator.hasNext()) {
            String study = iterator.next();
            keyWords.put(study, StudyProgramInfo.studyPrograms.get(study).getKeywords());
            pointMap.put(study, 0);
            matchedInterests.put(study, new ArrayList<String>());
        }

        // går gjennom alle studiene, legger til poeng på pointsMap, om interessen er en av keywordsa
        for (String study : StudyProgramInfo.studyPrograms.keySet()) {
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

        // finds out what study is the best one
        Iterator<String> iterator1 = StudyProgramInfo.studyPrograms.keySet().iterator();
        if (iterator1.hasNext()) {
            String bestStudy = iterator1.next();

            while (iterator1.hasNext()) {
                String nextStudy = iterator1.next();
                if (pointMap.get(bestStudy) < pointMap.get(nextStudy)) {
                    bestStudy = nextStudy;
                }
            }

            iterator1 = StudyProgramInfo.studyPrograms.keySet().iterator(); // resets the iterator

            // while to check if the best study has at least 3 more points than all of the others
            while (iterator1.hasNext()) {
                String nexStudy = iterator1.next();
                if (pointMap.get(bestStudy) - 3 < pointMap.get(nexStudy) && bestStudy != nexStudy) { // if beststudy don't have at least 3 more points than all of the other, don't stop the interview
                    return null;
                }
            }


            return bestStudy; // return the study name.
        }

        return null; // to continue the interview


    }

    public void setStudyInformation(StudyProgramInfo info, String study) {
        //Reference to Firebase and the node Studies
        Firebase infoRef = new Firebase("https://tdt4140project2.firebaseio.com/Studies/");
        //Sets the StudyProgramInfo-object, info, as a value in the node study
        infoRef.child(study).setValue(info);
    }

    private void addMessageToChatArray(String message) {
        chatArrayAdapter.add(new ChatMessage(true, message));
    }

    // Not in use, kept only for later use
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

    }

}