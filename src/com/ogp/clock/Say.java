
package com.ogp.clock;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

public class Say extends Service implements OnInitListener {

    public static final String SAY_TEXT = "say.text";

    private static final String TAG = "SayTask";

    private TextToSpeech tts;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "OnCreate");
        tts = new TextToSpeech(this, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "OnDestroy");
        tts.stop();
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
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "OnStart");

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (am.isMusicActive()) {
            Intent i = new Intent("com.android.music.musicservicecommand");
            i.putExtra("command", "pause");
            sendBroadcast(i);
        }

        HashMap<String, String> settings = new HashMap<String, String>();
        settings.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                String.valueOf(AudioManager.STREAM_MUSIC));

        int result = tts.speak(intent.getStringExtra(SAY_TEXT), TextToSpeech.QUEUE_ADD, settings);
        if (result == TextToSpeech.ERROR) {
            Log.e(TAG, "Failed to speak");
        }
    }
}
