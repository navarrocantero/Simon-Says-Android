package com.naca.navarrocantero.simonSays;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

/**
 * Created by navarrocantero on 04/11/2017.
 */


public class PlayButton {

    private View view;
    private int color;

    public PlayButton(int color, View view) {
        this.view = view;
        this.color = color;
    }

    public View getView() {
        return view;
    }

    public void setView(int id) {
        this.view = view;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    static public void inMoving(View view) {

        final View viewAux = view;
        new CountDownTimer(5, 5) {
            private String TAG = "MainActivity";
            public void onTick(long millisUntilFinished) {
                viewAux.setVisibility(viewAux.INVISIBLE);
                Log.d(TAG, "onTick: INVISIBLE");
            }
            public void onFinish() {
                viewAux.setVisibility(viewAux.VISIBLE);
                Log.d(TAG, "onFinish: VISIBLE");
            }

        }.start();
    }
}