package com.example.erikkjernlie.tdt4140project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonElement;

import java.util.Map;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

/**
 * Created by jonas on 21.02.17.
 */

public class ListenActivity extends AppCompatActivity implements AIListener {

        private AIService aiService;
        public Button listenButton;
        private TextView resultTextView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        final AIConfiguration config = new AIConfiguration("3e19cea342f04764b1665e37099f1e23",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);
        aiService.setListener(this);

        listenButton = (Button) findViewById(R.id.listenButton2);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        System.out.println("hello");
        listenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                System.out.println("FUNKER DETTE");
                aiService.startListening();
            }
        });

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
        System.out.println("Hei1");
    }

    @Override
    public void onListeningCanceled() {
        System.out.println("Hei2");
    }

    @Override
    public void onListeningFinished() {
        System.out.println("Hei2");
    }

    @Override
    public void onAudioLevel(final float level) {
        System.out.println(level);
    }

}
