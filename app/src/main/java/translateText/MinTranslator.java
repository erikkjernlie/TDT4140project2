package translateText;

/**
 * Created by jonas on 05.03.2017.
 */

import android.os.AsyncTask;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import ai.api.model.AIResponse;


public class MinTranslator {


    public static void main(String... args) throws Exception {
       /* // Instantiates a client
        Translate translate = TranslateOptions.builder().apiKey("AIzaSyAE5HArx8-wXs0jM-Z6PZNFQFR0Bo_bJyo").build().service();


        // The text to translate
        String text = "Hello, world!";

        // Translates some text into Russian
        Translation translation = translate.translate(
                text,
                TranslateOption.sourceLanguage("en"),
                TranslateOption.targetLanguage("no")
        );

        System.out.printf("Text: %s%n", text);
        System.out.printf("Translation: %s%n", translation.translatedText());
        System.out.println("asd");

       */

       MinTranslator trans = new MinTranslator();
       trans.translate();



    }


    public void translate() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                TranslateOptions options = TranslateOptions.newBuilder()
                        .setApiKey("AIzaSyAE5HArx8-wXs0jM-Z6PZNFQFR0Bo_bJyo")
                        .build();
                Translate translate = options.getService();
                System.out.println("aksdj");
                final Translation translation =
                        translate.translate("Hello World",
                                TranslateOption.targetLanguage("de"));
                System.out.println("skejrdette?");


                return null;
            }
            // @Override
         //   protected void onPostExecute(Translate translate) {
         //      text(translate.toString());
          //  }

        }.execute();
    }

    private void text(String text) {

    }


}

