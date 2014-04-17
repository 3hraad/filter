package com.example.filter;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Gary on 17/04/14.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.widget.Toast;

public class MinigameBlow extends Activity {
    /* constants */
    private static final String LOG_TAG = "NoiseAlert";
    private static final int POLL_INTERVAL = 300;
    private static final int NO_NUM_DIALOG_ID=1;
    private static final String[] REMOTE_CMDS = {"start", "stop", "panic"};

    /** running state **/
    private boolean mAutoResume = false;
    private boolean mRunning = false;
    private boolean mTestMode = false;
    private int mTickCount = 0;
    private int mHitCount =0;

    /** config state **/
    private int mThreshold =12;
    private int mPollDelay=0;


    private PowerManager.WakeLock mWakeLock;

    private Handler mHandler = new Handler();

    /* References to view elements */
    private TextView mStatusView;
    private ImageView mActivityLed;
    //private SoundLevelView mDisplay;

    /* data source */
    private SoundMeter mSensor;


    private Runnable mSleepTask = new Runnable() {
        public void run() {
            start();
        }
    };
    private Runnable mPollTask = new Runnable() {
        public void run() {
            double amp = mSensor.getAmplitude();
            if (mHitCount==0){
                updateDisplay("Start Blowing", 0.0);
            }
            if ((amp > mThreshold) && !mTestMode) {

                mHitCount++;
                if (mHitCount > 30){
                    targetAchieved();
                    return;
                }
                if (mHitCount==1){
                    updateDisplay("Keep Blowing", amp);
                }else if (mHitCount==5){
                    updateDisplay("It's Working",amp);
                }else if (mHitCount==15){
                    updateDisplay("Halfway There",amp);
                }else if (mHitCount==20){
                    updateDisplay("Almost",amp);
                }
            }

            mTickCount++;
            setActivityLed(mTickCount% 2 == 0);

            if ((mTestMode || mPollDelay > 0) && mTickCount > 100) {
                if (mTestMode) {
                    stop();
                } else {
                    sleep();
                }
            } else {
                mHandler.postDelayed(mPollTask, POLL_INTERVAL);
            }
        }
    };
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        setContentView(R.layout.blow_minigame);
        mStatusView = (TextView) findViewById(R.id.status);
        mActivityLed = (ImageView) findViewById(R.id.activity_led);

        mSensor = new SoundMeter();
        //mDisplay = (SoundLevelView) findViewById(R.id.volume);


        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "NoiseAlert");

        mAutoResume = true;
        mRunning = true;
        mTestMode = false;
        start();
    }


    @Override
    public void onResume() {
        super.onResume();

        //mDisplay.setLevel(0, mThreshold);
        if (mAutoResume) {
            start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stop();
    }



    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == NO_NUM_DIALOG_ID) {
            return new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_launcher)
                    .setTitle(R.string.no_num_title)
                    .setMessage(R.string.no_num_msg)
                    .setNeutralButton(R.string.ok, null)
                    .create();
        }
        else return null;
    }

    private void start() {
        mTickCount = 0;
        mHitCount = 0;
        mSensor.start();
        setActivityLed(true);
        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
        }
        mHandler.postDelayed(mPollTask, POLL_INTERVAL);
    }

    private void stop() {
        if (mWakeLock.isHeld()) {
            mWakeLock.release();
        }
        mHandler.removeCallbacks(mSleepTask);
        mHandler.removeCallbacks(mPollTask);
        mSensor.stop();
        //mDisplay.setLevel(0,0);
        updateDisplay("stopped...", 0.0);
        setActivityLed(false);
        mRunning = false;
        mTestMode = false;
    }

    private void sleep() {
        mSensor.stop();
        updateDisplay("paused...", 0.0);
        setActivityLed(false);
        mHandler.postDelayed(mSleepTask, 1000*mPollDelay);
    }

    private void readApplicationPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        /*mThreshold = Integer.parseInt(prefs.getString("threshold", null));
        Log.i(LOG_TAG, "threshold=" + mThreshold);
        mPollDelay = Integer.parseInt(prefs.getString("sleep", null));
        Log.i(LOG_TAG, "sleep=" + mPollDelay);*/
    }

    private void updateDisplay(String status, double signalEMA) {
        mStatusView.setText(status);

        //mDisplay.setLevel((int)signalEMA, mThreshold);
    }

    private void setActivityLed(boolean on) {
        mActivityLed.setVisibility( on ? View.VISIBLE : View.INVISIBLE);
    }

    private void targetAchieved() {

        mAutoResume = false;
        stop();
        Toast.makeText(getApplicationContext(),"target acheived",Toast.LENGTH_LONG).show();
    }

};