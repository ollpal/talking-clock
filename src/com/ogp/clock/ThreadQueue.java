
package com.ogp.clock;

import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

public class ThreadQueue {

    private static final String TAG = "ThreadQueue";

    private class ConsumerThread extends Thread {

        ConsumerThread() {
            super("ConsumerThread");
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

    private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();

    private ConsumerThread consumer;

    public void add(Runnable runnable) {
        synchronized (this) {
            queue.add(runnable);
        }
    }

    public void start() {
        if (consumer == null) {
            consumer = new ConsumerThread();
            consumer.start();
        }
    }

    public void clear() {
        synchronized (this) {
            queue.clear();
        }
    }
}
