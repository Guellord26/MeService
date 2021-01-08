package com.example.meservice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button2 = (Button)findViewById(R.id.Sortir);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
        displayDistance();
    }
    public void openActivity2() {
       System.exit(0);
    }
    private MetrageService metrage;
    private boolean bound = false;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MetrageService.MetrageBinder metrageBinder = (MetrageService.MetrageBinder) iBinder;
            metrage = metrageBinder.getMetrage();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentNameame) {
            bound = false;
        }

    };
    @Override
    public void onStart() {
        super.onStart();
        Intent i = new Intent(this, MetrageService.class);
        bindService(i, connection, Context.BIND_AUTO_CREATE);
    }
    @Override
    public void onStop() {
        super.onStop();
        if(bound){
            unbindService(connection);
        }
    }
    public void onClickStart(View v) {
        onStart();
    }

    public void onClickStop(View v) {
       onStop();
    }
    private void displayDistance(){
        final TextView distanceView = findViewById(R.id.distance);
        final Handler handler = new Handler();
        handler.post(new Runnable(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public  void run() {
                double distance = 1.0;
                if(bound && metrage != null){
                    distance = metrage.getDistance();

                }
                String strDistance = String.format(Locale.getDefault(),"%1$, .2f", distance);
                distanceView.setText(strDistance);
                handler.postDelayed(this, 1000);
            }
        });
    }
}