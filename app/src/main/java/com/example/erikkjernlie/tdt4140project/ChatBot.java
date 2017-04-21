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
    private AIConfiguration config; // this could also have been removed, by making a class that handles all ai.api interaction
    private AIDataService aiDataService; // this could also have been removed, by making a class that handles all ai.api interaction
    private TextView uniBot;
    private TextView back;
    private TextView help;
    final Context context = this;
    private int randomNumber = -1; // should be removes by creating a class that handles this functionality
    private ArrayList<String> sentencesToUnibot; // should be removes by creating a class that handles this functionality
    private ArrayList<String> sentencesOutput;  // should be removes by creating a class that handles this functionality

    private Interview interview = new Interview();
    //gets the required access from API.AI a <-- ?

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
                "\nIf you wish to see more examples, click the 'HELP'-button in the top right corner, or ask for help in the chat. You can also press 'UNIBOT' in the header to let me prompt you with some questions. I look forward to assisting you!");
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
                                + "\n\nDo you need more help or information about how to ask questions? Type 'help' in the chat."
                                + "\n\nYou can also press the uniBOT button at the top to prompt questions.\n")
                        .setCancelable(false)
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
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

                if (!interview.isActive()) {
                sentencesOutput = new ArrayList<String>(Arrays.asList("Do you want to learn about a study?", "Do you want to compare some studies?", "Do you want to see a list of studies that we support?", "Do you want me to interview you?", "Do you want to " +
                        "learn about yourself?"));

                Random rn = new Random();
                int range = sentencesOutput.size();

                int newRandom = rn.nextInt(range);
                while (randomNumber == newRandom) {
                    newRandom = rn.nextInt(range);
                }
                randomNumber = newRandom;
                addMessageToChatArray(sentencesOutput.get(randomNumber));

                } else {
                    addMessageToChatArray("Please press quit to stop the interview");
                }
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
        //For displayUserInformation:
        if (messageFromUser.isEmpty()) {
            return false;
        }
        if (!messageFromUser.isEmpty()) { // checks that the message is not empty
            chatArrayAdapter.add(new ChatMessage(side, messageFromUser));
        }
        if (interview.isActive()) {
            String interviewResponse = interview.sendMessage(messageFromUser); // sends message to interview objekt
            chatText.setText("");

            if (!interview.isActive()) {
                addMessageToChatArray(interviewResponse);
                getAiResponse("Can you recommend me a study?"); // finish interview by finding a study
                return true; // users turn to answer
            }
                getAiResponse(interview.getQuestion());
            return true; // avoid using getAiResponse
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
        ProcessAiResponse processAiResponse = new ProcessAiResponse(UserInfo.studyPrograms, user, Union.unions);
        if (interview.isActive()) {
            return interview.getQuestion();
        } else {
            if (response.getResult().getFulfillment().getSpeech().equals("")) {
                ut = processAiResponse.processAiRespons(response);
            } else {
                ut = response.getResult().getFulfillment().getSpeech().toString();
            }

            if (ut.equals("startInterview")) {
                interview = new Interview();
                interview.setActive(true);
                addMessageToChatArray("We will now start an interview and try to find a study that matches your interests. Please write 'quit' to stop the interview.");
                ut = interview.getQuestion();
            }
        }
        return ut;
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

    public void setUser(UserInfo user) {
        this.user = user;
    }
}