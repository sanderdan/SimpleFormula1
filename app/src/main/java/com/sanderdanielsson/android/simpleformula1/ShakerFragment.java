package com.sanderdanielsson.android.simpleformula1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by sander on 2015-04-16.
 */
public class ShakerFragment extends android.support.v4.app.Fragment {

    private ShakeDetector mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private TextView randomNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                randomNumber = (TextView) getActivity().findViewById(R.id.random_number);
                MediaPlayer mp;
                int max = 6;
                int min = 1;
                Random rn = new Random();
                int randomInt = rn.nextInt((max - min) + min);

                switch (randomInt) {
                    case 1:
                        mp = MediaPlayer.create(getActivity(), R.raw.shotgun);
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                // TODO Auto-generated method stub
                                mp.release();
                            }

                        });
                        mp.start();
                        randomNumber.setText(String.valueOf(randomInt));
                        break;
                    case 2:
                        randomNumber.setText(String.valueOf(randomInt));
                        break;
                    case 3:
                        randomNumber.setText(String.valueOf(randomInt));
                        break;
                    case 4:
                        randomNumber.setText(String.valueOf(randomInt));
                        break;
                    case 5:
                        randomNumber.setText(String.valueOf(randomInt));
                        break;
                    case 6:
                        randomNumber.setText(String.valueOf(randomInt));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View shakerView;
        shakerView = inflater.inflate(R.layout.shaker, container, false);

        return shakerView;
    }

    @Override
    public void onResume() {
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}
