package com.example.erikkjernlie.tdt4140project;


import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonElement;

import java.util.Map;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;


public class ChatBot extends AppCompatActivity implements AIListener {
    private static final String TAG = "ChatActivity";

    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private boolean side = false;
    private AIService aiService;
    public Button listenButton;
    private TextView resultTextView;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestPermissions(new String[]{"android.permission.RECORD_AUDIO"},2);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chatbot);


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
        final AIConfiguration config = new AIConfiguration("3e19cea342f04764b1665e37099f1e23",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);
        aiService.setListener(this);

        listenButton = (Button) findViewById(R.id.listenButton2);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        System.out.println("hello");
    }

    //her er alt som skrives inn, og her er det vi svarer
    private boolean sendChatMessage() {
        ListenActivity listenActivity = new ListenActivity();
        String a = chatText.getText().toString();
        System.out.println(a);
        chatArrayAdapter.add(new ChatMessage(side, a));
        chatText.setText(""); //nullstiller chatboksen
        //her kommer responsen
        if (a.toLowerCase().equals("hi") || a.toLowerCase().equals("hello") || a.toLowerCase().equals("mårn")){
            chatArrayAdapter.add(new ChatMessage(true, "This is UniBOT. Hello"));
        } else if (a.toLowerCase().equals("who would jonas like to fuck?")){
            chatArrayAdapter.add(new ChatMessage(true, "Herman"));
        } else{
            chatArrayAdapter.add(new ChatMessage(true, "This is UniBOT. I don't understand"));
        }
        return true;
    }

    public void listenButtonOnClick(View view) {
        aiService.startListening();
    }

    public void onResult(final AIResponse response) {
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

    @Override
    public void onError(final AIError error) {
        resultTextView.setText(error.toString());
    }

    @Override
    public void onListeningStarted() {
        System.out.println("Started");
    }

    @Override
    public void onListeningCanceled() {
        System.out.println("Caceled");
    }

    @Override
    public void onListeningFinished() {
        System.out.println("Finished");
    }

    @Override
    public void onAudioLevel(final float level) {
        System.out.println(level);
    }
}