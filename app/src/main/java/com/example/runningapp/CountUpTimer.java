package com.example.runningapp;

import android.os.CountDownTimer;
import android.os.SystemClock;

public abstract class CountUpTimer extends CountDownTimer {
    private static final long INTERVAL_MS = 1000;
    private final long duration;
    private long base;

    protected CountUpTimer(long durationMs) {
        super(durationMs, INTERVAL_MS);
        this.duration = durationMs;
    }

    public abstract void onTick(int second);

    @Override
    public void onTick(long msUntilFinished) {
        int second = (int) ((duration - msUntilFinished) / 1000);
        onTick(second);
    }

    @Override
    public void onFinish() {
        onTick(duration / 1000);
    }
}