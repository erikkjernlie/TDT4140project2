package translateText;

import android.os.AsyncTask;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import static java.lang.System.out;
import static java.lang.System.setOut;
import static translateText.MinTranslator2.createTranslateService;

/**
 * Created by jonas on 07.03.2017.
 */

public class RetrieveTranslation extends AsyncTask<String, Void, String> {


        private Exception exception;

        public String doInBackground(String... urls) {
            try {

                String text = urls[0];
                System.out.println("aølsdaøksdl");
                Translate translate = TranslateOptions.getDefaultInstance().getService();
                System.out.println("kad");
                Translation translation =
                        translate.translate(
                                text,
                                Translate.TranslateOption.sourceLanguage("en"),
                                Translate.TranslateOption.targetLanguage("no"));
                System.out.println("askdlaksd");
                return translation.toString();
            } catch (Exception e) {
                this.exception = e;

                return null;
            }
        }

        protected void onPostExecute(String text) {

            out.println(text);
            // TODO: check this.exception
            // TODO: do something with the feed
        }

}
