package translateText;


import com.google.cloud.RetryParams;
import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.LanguageListOption;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import java.io.PrintStream;
import java.util.List;


public class MinTranslator2 {

    public static void translateText(String text){
        Translate translate = createTranslateService();
        Translation translation = translate.translate(text);
        System.out.println(translation.toString());

    }

    public static Translate createTranslateService() {
        TranslateOptions translateOption = TranslateOptions.newBuilder()
                .setRetryParams(retryParams())
                .setConnectTimeout(60000)
                .setReadTimeout(60000)
                .build();
        return translateOption.getService();
    }


    private static RetryParams retryParams() {
        return RetryParams.newBuilder()
                .setRetryMaxAttempts(3)
                .setMaxRetryDelayMillis(30000)
                .setTotalRetryPeriodMillis(120000)
                .setInitialRetryDelayMillis(250)
                .build();
    }

}
