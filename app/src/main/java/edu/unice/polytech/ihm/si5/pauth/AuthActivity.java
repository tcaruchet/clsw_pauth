package edu.unice.polytech.ihm.si5.pauth;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import edu.unice.polytech.ihm.si5.pauth.data.WearAuthenticator;
import edu.unice.polytech.ihm.si5.pauth.tokens.TokenRetreiver;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AuthActivity extends Activity implements SensorEventListener {
    private static final String LOG_TAG = "Auth";

    private TextView mTextViewCode;
    private TextView mTextViewSeed;

    private ProgressBar mProgressCircleToken;
    private ProgressBar mProgressBarTokenExpiration;

    private Handler hdlr = new Handler();
    private int iProgressBarTokenExp;

    private WearAuthenticator authenticator;

    private String token;
    private LocalDateTime tokenExpiration;
    private int currentHR;
    private boolean HRSinit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        //get authenticator from intent
        authenticator = (WearAuthenticator) getIntent().getParcelableExtra("authenticator");
        if(authenticator == null) {
            Log.e(LOG_TAG, "Authenticator is null");
            finish();
        }

        //get views
        mTextViewCode = findViewById(R.id.textCode);
        mTextViewSeed = findViewById(R.id.textSeed);
        mProgressCircleToken = findViewById(R.id.pCircleToken);
        mProgressBarTokenExpiration = findViewById(R.id.pBarTimer);

        if (authenticator != null) {
            //bind authenticator to view
            findViewById(R.id.imageIcon).setBackgroundResource(authenticator.icon);
            ((TextView) findViewById(R.id.textIssuer)).setText(authenticator.issuer);

            // Check permission and get current heart rate
            if (ContextCompat.checkSelfPermission(AuthActivity.this, Manifest.permission.BODY_SENSORS) ==
                    PackageManager.PERMISSION_GRANTED) {
                //HR Sensor
                SensorManager sMgr = (SensorManager)this.getSystemService(SENSOR_SERVICE);
                Sensor hr = sMgr.getDefaultSensor(Sensor.TYPE_HEART_RATE);
                Log.e(LOG_TAG, "Heart rate sensor " + ((hr == null) ? "not" : "") + " found");
                sMgr.registerListener(AuthActivity.this, hr,SensorManager.SENSOR_DELAY_FASTEST);
                mTextViewSeed.setText(R.string.WaitHRSensorRead);
                HRSinit = true;
            } else {
                requestPermissions(new String[]{Manifest.permission.BODY_SENSORS}, 0);
            }
        } else{
            Log.e(LOG_TAG, "Authenticator is null. The intent is probably not well formed.");
            finish();
        }
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            currentHR = (int) event.values[0];
            String seedText = " HR: " + currentHR;
            mTextViewSeed.setText(seedText);

            getNewToken();
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        System.out.println("onAccuracyChanged - accuracy: " + accuracy);
    }




    private void getNewToken() {
        Log.i(LOG_TAG, "Getting new token");
        if(HRSinit == false) {
            if (ContextCompat.checkSelfPermission(AuthActivity.this, Manifest.permission.BODY_SENSORS) ==
                    PackageManager.PERMISSION_GRANTED) {
                //HR Sensor
                SensorManager sMgr = (SensorManager)this.getSystemService(SENSOR_SERVICE);
                Sensor hr = sMgr.getDefaultSensor(Sensor.TYPE_HEART_RATE);
                Log.e(LOG_TAG, "Heart rate sensor " + ((hr == null) ? "not" : "") + " found");
                sMgr.registerListener(AuthActivity.this, hr,SensorManager.SENSOR_DELAY_FASTEST);
                mTextViewSeed.setText(R.string.WaitHRSensorRead);
                HRSinit = true;
            } else {
                requestPermissions(new String[]{Manifest.permission.BODY_SENSORS}, 0);
            }
        }else if((tokenExpiration == null || tokenExpiration.isBefore(LocalDateTime.now())) && currentHR > 0) {
            setLoadingUI();
            // get latitude from GPS
            double latitude = 4;
            double longitude = 6;
            if (ContextCompat.checkSelfPermission(AuthActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                //GPS Sensor
                SensorManager sMgr = (SensorManager)this.getSystemService(SENSOR_SERVICE);
                Sensor gps = sMgr.getDefaultSensor(Sensor.TYPE_PRESSURE);
                Log.e(LOG_TAG, "GPS sensor " + ((gps == null) ? "not" : "") + " found");
                sMgr.registerListener(AuthActivity.this, gps,SensorManager.SENSOR_DELAY_FASTEST);
                latitude = gps.getMaximumRange();
                longitude = gps.getMaximumRange();
                //get token
                double finalLatitude = latitude;
                double finalLongitude = longitude;
                try {
                    token = TokenRetreiver.getToken(currentHR, finalLatitude, finalLongitude);
                    tokenExpiration = LocalDateTime.now().plusSeconds(30);
                    Log.i(LOG_TAG, "Token: " + token);
                    updateUIAfterTokenReceived();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        } else if (token == null) {
            setWaitingUI();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNewToken();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hdlr.removeCallbacksAndMessages(null);
        token = null;
        tokenExpiration = null;
    }


    //////////////////////////
    // UI
    //////////////////////////
    private void updateUIAfterTokenReceived() {
        if(token != null && !token.isEmpty()) {
            mTextViewCode.setText(token);
            mProgressCircleToken.setVisibility(ProgressBar.GONE);
            tokenExpiration = LocalDateTime.now().plusSeconds(30);
            startProgressBarTokenExpiration();
        } else {
            mTextViewCode.setText(R.string.ErrorText);
        }
        mTextViewCode.setVisibility(TextView.VISIBLE);
    }

    private void setWaitingUI() {
        mTextViewCode.setTextSize(22);
        mTextViewCode.setText(R.string.WaitSensorsRead);
        mProgressBarTokenExpiration.setVisibility(ProgressBar.GONE);
    }

    private void setLoadingUI() {
        mProgressCircleToken.setVisibility(ProgressBar.VISIBLE);
        mTextViewCode.setVisibility(TextView.INVISIBLE);
        mProgressBarTokenExpiration.setVisibility(ProgressBar.GONE);
    }

    private void startProgressBarTokenExpiration() {
        mProgressBarTokenExpiration.setVisibility(ProgressBar.VISIBLE);
        mProgressBarTokenExpiration.setProgress(100);
        iProgressBarTokenExp = 100;
        hdlr.postDelayed(new Runnable() {
            @Override
            public void run() {
                iProgressBarTokenExp -= 1;
                mProgressBarTokenExpiration.setProgress(iProgressBarTokenExp);
                if(iProgressBarTokenExp > 0) {
                    hdlr.postDelayed(this, 300);
                } else {
                    token = null;
                    tokenExpiration = null;
                    setLoadingUI();
                    Log.i(LOG_TAG, "Token expired, retrieving new one");
                    getNewToken();
                }
            }
        }, 300);



    }
}
