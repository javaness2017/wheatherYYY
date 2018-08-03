package com.ness.wheather;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import structure.WheatherStructure;
import structure.rWheather;

public class WheatherDisplay extends AppCompatActivity implements View.OnClickListener {
    final static double KELVIN=273.15;
    String msg = "Android : ";
    WheatherService mService;
    IBinder binder;
    boolean mBound = false;
    TextView text = null;
    Button btnStart = null;
    Button btnStop = null;
    Button btnBind = null;
    ArrayList<rWheather> wheatherForecast = new ArrayList();
    private RecyclerView mRecyclerView;
    private WheatherAdapter adapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        Intent intent = new Intent(this, WheatherService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        Log.d(msg, "The onCreate() event");
    }


    @Override
    public void onClick(View view) {
/*
        if (view.getId() == R.id.start) startService(view);
        if (view.getId() == R.id.stop) stopService(view);
        if (view.getId() == R.id.bind) bind();
*/

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to WheatherService, cast the IBinder and get WheatherService instance
            mService = ((WheatherService.LocalBinder) service).getService();
            Toast.makeText(mService, "onServiceConnected", Toast.LENGTH_SHORT).show();
            mBound = true;
            loadData();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public void loadData() {
        WheatherStructure ws = mService.getWheather();
        if (ws != null)
            Toast.makeText(WheatherDisplay.this, "temp=" + ws.getList().get(0).getMain().getTemp(), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(WheatherDisplay.this, "can not connect to service", Toast.LENGTH_SHORT).show();
        for (structure.List temp : ws.getList()) {
            //rWheather(double temp, double temp_min, double temp_max, double pressure, double sea_level, double grnd_level, double humidity, double temp_kf, String dt_txt, String icon) {

            rWheather rw = new rWheather(temp.getMain().getTemp()-KELVIN, temp.getMain().getTempMin()-KELVIN, temp.getMain().getTempMax()-KELVIN,
                    temp.getMain().getPressure(), temp.getMain().getSeaLevel(), temp.getMain().getGrndLevel(),
                    temp.getMain().getHumidity(), temp.getMain().getTempKf(), temp.getDtTxt(), temp.getWeather().get(0).getIcon());
            wheatherForecast.add(rw);
        }
        adapter = new WheatherAdapter(WheatherDisplay.this, wheatherForecast);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(new WheatherAdapter(mRecyclerView.getContext(), wheatherForecast));


    }
}
