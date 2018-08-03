package com.ness.wheather;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import structure.WheatherStructure;


public class WheatherService extends Service {
    private final IBinder mBinder = new LocalBinder();
    WheatherStructure wheather = null;
    RequestQueue mRequestQueue = null;
    StringRequest stringRequest;
    String city = null;
    Long delay = -1L;
    /**
     * indicates how to behave if the service is killed
     */
    int mStartMode;

    /**
     * indicates whether onRebind should be used
     */
    @Nullable
    /**
     * The service is starting, due to a call to startService()
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("onStartCommand", "Service started");

        delay = intent.getLongExtra("delay", -1L);
        if (delay == -1L) delay = 1000L;
        city = intent.getStringExtra("city");
        if (city == null || city.equals("")) city = "jerusalem";
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started delay=" + delay + " city=" + city, Toast.LENGTH_LONG).show();
        loadWheather(city);
        return START_REDELIVER_INTENT;
    }

    public class LocalBinder extends Binder {
        WheatherService getService() {
            // Return this instance of LocalService so clients can call public methods
            return WheatherService.this;
        }
    }

    /**
     * A client is binding to the service with bindService()
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("onBind", "binded");

        return mBinder;
    }

    public void loadWheather(String location) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + location +
                "&mode=json&appid=bc8a1499d47e8c2f9eb7e88b15930418";
        mRequestQueue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

// Request a string response from the provided URL.
        stringRequest = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        JsonObject jsonObj = gson.fromJson(response, JsonObject.class);

                        Log.d("wheather", gson.toJson(jsonObj));
                        wheather = gson.fromJson(jsonObj, WheatherStructure.class);

                        Toast.makeText(WheatherService.this, "temp=" + wheather.getList().get(0).getMain().getTemp(), Toast.LENGTH_SHORT).show();
//                        adapter = new MyRecyclerViewAdapter(ServiceActivity.this, pics);
                        //                      mRecyclerView.setAdapter(adapter);

                        //adapter.setOnItemClickListener((OnItemClickListener)ServiceActivity.this);
                        //progressBar.invalidate();
                        sendNotification("new wheather info available");
               /*         try {
                            Thread.sleep(delay * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mRequestQueue.add(WheatherService.this.stringRequest);*/

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                        //                  mTextView.setText("That didn't work!");
                    }
                });

// Add the request to the RequestQueue.
        stringRequest.setTag("NESS-Wheather");
        mRequestQueue.add(stringRequest);
    }

    public WheatherStructure getWheather() {
        Toast.makeText(this, "getWheather is here", Toast.LENGTH_SHORT).show();
        return wheather;
    }

    public void sendNotification(String str) {

        createNotificationChannel();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "JAVANESS-2017");
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.raw.il));
        builder.setContentTitle("Notifications Title");
        builder.setContentText(str);
        builder.setSubText("Tap to view the website.");
//        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          /*  notification = new Notification.Builder(this, "JAVANESS-2017")
                    .setContentTitle("Notifications Title")
                    .setContentText(str)
                    .setSmallIcon(android.R.drawable.ic_menu_help)
                    .build();*/


            // Will display the notification in the notification bar
            notificationManager.notify(1, builder.build());
        } else {
/*
            notification = new Notification.Builder(this)
                    .setContentTitle("Some Message")
                    .setContentText(str)
                    .setSmallIcon(android.R.drawable.ic_menu_help)
                    .build();
*/
            notificationManager.notify(1, builder.build());
        }
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("JAVANESS-2017", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
