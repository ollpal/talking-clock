package com.ogp.clock;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

public class Say extends Service implements OnInitListener, OnUtteranceCompletedListener {

    public static final String SAY_TEXT = "say.text";

    private static final String TAG = "SayService";

    private TextToSpeech tts;

    private boolean initialized = false;

    private String text;

    private AudioManager am;

    private int audioFocus;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "OnCreate");

        tts = new TextToSpeech(this, this);
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "OnDestroy");

        am = null;
        tts.shutdown();
    }

    @Override
    public void onInit(int status) {
        Log.d(TAG, "OnInit");
        if (status == TextToSpeech.SUCCESS) {
            tts.setOnUtteranceCompletedListener(this);

            int result = tts.setLanguage(Locale.US);
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                if (text != null && !text.isEmpty()) {
                    say(text);
                }
                initialized = true;
            } else {
                if (result == TextToSpeech.LANG_MISSING_DATA) {
                    Log.e(TAG, "Language is missing data");
                } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e(TAG, "Language is not supported");
                }
            }
        } else {
            Log.e(TAG, "Could not initialize TextToSpeech.");
        }
    }

    private int say(String text) {
        int result = TextToSpeech.SUCCESS;
        if (!tts.isSpeaking()) {
            audioFocus = am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
            
            HashMap<String, String> speakSettings = new HashMap<String, String>();
            speakSettings.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));
            speakSettings.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "3770");

            result = tts.speak(text, TextToSpeech.QUEUE_FLUSH, speakSettings);
        }
        return result;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "OnStartCommand " + startId);

        text = intent.getStringExtra(SAY_TEXT);
        if (initialized) {
            say(text);
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onUtteranceCompleted(String utteranceId) {
        Log.d(TAG, "onUtteranceCompleted");

        if (audioFocus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            am.abandonAudioFocus(null);
        }

        stopSelf();
    }
}
