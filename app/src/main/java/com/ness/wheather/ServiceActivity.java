package com.ness.wheather;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {
    String msg = "Android : ";
    WheatherService mService;
    IBinder binder;
    boolean mBound = false;
    TextView text = null;
    Button btnStart = null;
    Button btnStop = null;
    Button btnBind = null;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_manager);
        text = findViewById(R.id.text);
        Log.d(msg, "The onCreate() event");
        btnStart = findViewById(R.id.start);
        btnStop = findViewById(R.id.stop);
        btnBind = findViewById(R.id.bind);


        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnBind.setOnClickListener(this);


    }

    public void startService(View view) {
        Intent sIntent = new Intent(getBaseContext(), WheatherService.class);
        sIntent.putExtra("delay", 30L);
        sIntent.putExtra("city", "Jerusalem");

        Log.d("service", "Started");
        startService(sIntent);
        startActivity(new Intent(this,WheatherDisplay.class));
    }

    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), WheatherService.class));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start) startService(view);
        if (view.getId() == R.id.stop) stopService(view);
        if (view.getId() == R.id.bind) bind();

    }

    @Override
    public void onStop() {
        Toast.makeText(this, "onStop application", Toast.LENGTH_SHORT).show();
        //stopService(new Intent(getBaseContext(), WheatherService.class));
        super.onStop();
        finish();
    }

    // Bind to LocalService
    public void bind() {

        Intent intent = new Intent(this, WheatherService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to WheatherService, cast the IBinder and get WheatherService instance
            mService = ((WheatherService.LocalBinder)service).getService();
            Toast.makeText(mService, "onServiceConnected", Toast.LENGTH_SHORT).show();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

}
