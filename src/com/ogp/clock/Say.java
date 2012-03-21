
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
import java.util.concurrent.LinkedBlockingQueue;

public class Say extends Service implements OnInitListener {

    private class QueueThread extends Thread {

        QueueThread() {
            super("QueueThread");
        }

        @Override
        public void run() {
            while (true) {
                Runnable r;
                try {
                    r = queue.take();
                    r.run();
                } catch (InterruptedException e) {
                    Log.i(TAG, e.toString());
                }
            }
        }
    }

    public static final String SAY_TEXT = "say.text";

    private static final String TAG = "SayTask";

    private Object lock = new Object();

    private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();

    private QueueThread queueThread;

    private TextToSpeech tts;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "OnCreate");

        if (queueThread == null) {
            queueThread = new QueueThread();
            queueThread.start();
          }

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

        final String text = intent.getStringExtra(SAY_TEXT);

        addToQueue(new Runnable() {

            @Override
            public void run() {
                HashMap<String, String> settings = new HashMap<String, String>();
                settings.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                        String.valueOf(AudioManager.STREAM_MUSIC));

                int result = tts.speak(text, TextToSpeech.QUEUE_ADD, settings);
                if (result == TextToSpeech.ERROR) {
                    Log.e(TAG, "Failed to speak");
                }
            }
        });
    }

    private void addToQueue(Runnable runnable) {
        synchronized (lock) {

        }
    }
}
