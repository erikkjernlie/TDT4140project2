package com.example.erikkjernlie.tdt4140project;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;
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
    private boolean odd = true; // boolean to make sure getAiResponse only return actual ai object half of the time.


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 2);

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
        config = new AIConfiguration("3e19cea342f04764b1665e37099f1e23",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.Speaktoit);

        aiDataService = new AIDataService(this, config);


        initTextButtons();

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(ChatBot.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void initTextButtons(){
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
                        .setMessage("uniBOT can help you with\n- Information about a specific study"+
                        "\n- Compare different studies\n- Give you information about a study's union\n- Print available studies\nand lots of other random things.\n"
                        + "\nDo you need more help or information about how to ask questions? Type help in the chat."
                        + "\n\nYou can also press the uniBOT button at the top for random questions.")
                        .setCancelable(false)
                        .setPositiveButton("I don't need any more help",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
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
    private void translationFromUserToAI () {
        sentencesToUnibot = new ArrayList<String>(Arrays.asList("Tell me about physics", "I want to compare some studies", "print"));
        getAiResponse(sentencesToUnibot.get(randomNumber));
    }

    //this is where the messages are received and sent
    private boolean sendChatMessage() {
        String messageFromUser = chatText.getText().toString();


        if (!messageFromUser.isEmpty()) { // sjekker at meldingen ikke er tom
            chatArrayAdapter.add(new ChatMessage(side, messageFromUser));
        }
        chatText.setText(""); //resets the chatbox
        //here comes the response

        //checks if the user wants to answer the random question.
        // Also checks if the last question uniBOT printed equals the question that was printed because the user pressed the uniBOT-button
        //This needs to be checked, or else the user can type "yes" whenever (s)he wants, and the answer to the last question will be printed
        if(messageFromUser.toLowerCase().equals("yes") && randomNumber > -1 && chatArrayAdapter.getItem(chatArrayAdapter.getCount()-2).toString().equals(sentencesOutput.get(randomNumber))) {
            System.out.println(chatArrayAdapter.getItem(chatArrayAdapter.getCount()-2).toString());
            translationFromUserToAI();
            return true;
        } else if (messageFromUser.toLowerCase().equals("no")) {
            addMessageToChatArray("You can always press the uniBOT-button to get more random questions. ");
            return true;
        }


       getAiResponse(messageFromUser);

        return true;
    }

    public AIResponse getAiResponse(String a) {



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
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(AIResponse aiResponse) {
                    if (aiResponse != null) {
                        addMessageToChatArray(aiResponse.getResult().getFulfillment().getSpeech()); // returnere svar når ferdig
                    }
                }
            }.execute(aiRequest);

        return null;
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

}