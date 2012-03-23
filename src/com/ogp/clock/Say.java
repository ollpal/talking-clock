
package com.ogp.clock;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

public class Say extends Service implements TextToSpeech.OnInitListener {

    public static final String SAY_TEXT = "say.text";

    private static final String TAG = "SayService";

    private TextToSpeech tts;

    private boolean initialized = false;

    private String text;

    private HashMap<String, String> speakSettings;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "OnCreate");

        speakSettings = new HashMap<String, String>();
        speakSettings.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));

        tts = new TextToSpeech(this, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "OnDestroy");

        tts.shutdown();
    }

    @Override
    public void onInit(int status) {
        Log.d(TAG, "OnInit");
        if (status != TextToSpeech.SUCCESS) {
            Log.e(TAG, "Could not initialize TextToSpeech.");
            return;
        }

        int result = tts.setLanguage(Locale.US);
        if (result == TextToSpeech.LANG_MISSING_DATA) {
            Log.e(TAG, "Language is missing data");
            return;
        } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.e(TAG, "Language is not supported");
            return;
        }

        result = tts.speak(text, TextToSpeech.QUEUE_FLUSH, speakSettings);
        if (result == TextToSpeech.ERROR) {
            Log.e(TAG, "Failed to speak");
        }
        initialized = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "OnStartCommand " + startId);

        text = intent.getStringExtra(SAY_TEXT);
        if (initialized) {
            int result = tts.speak(text, TextToSpeech.QUEUE_FLUSH, speakSettings);
            if (result == TextToSpeech.ERROR) {
                Log.e(TAG, "Failed to speak");
            }
        }

        return START_NOT_STICKY;
    }
}
