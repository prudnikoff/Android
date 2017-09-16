package com.worlds.prudnikoff.worlds;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

class TextPronunciation {

    private static TextToSpeech textToSpeech;

    static void prepare(Context context) {
        textToSpeech = new TextToSpeech(context,
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                    }
                }, "com.google.android.tts");
        textToSpeech.setLanguage(Locale.ENGLISH);
    }

    static void pronounce(String textToPronounce) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(textToPronounce, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            textToSpeech.speak(textToPronounce, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    static void destroyTextToSpeech () {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
