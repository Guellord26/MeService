package com.example.meservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

import java.util.Random;
import java.util.concurrent.CountedCompleter;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MetrageService extends Service {
    public MetrageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }
    private final IBinder binder = new MetrageBinder();
    class MetrageBinder extends Binder {
        MetrageService getMetrage(){
            return MetrageService.this;
        }
    }
    private  final Random random = new Random();
    public double getDistance(){
        return random.nextDouble();
    }

}