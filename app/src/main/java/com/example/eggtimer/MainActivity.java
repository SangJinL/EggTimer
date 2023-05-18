package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
    int count = 0;

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription ("Channel description");
            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
    }
    public void onClick(View view)
    {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("계란 삶기")
                .setContentText("다 삶아졌어요");
        NotificationManager notificationManager = (NotificationManager)getSystemService(
                Context.NOTIFICATION_SERVICE);

        EditText et = (EditText)findViewById(R.id.time) ;
        TextView tv = (TextView)findViewById(R.id.textView);

        count = Integer.parseInt(et.getText().toString());

        Timer timer = new Timer();
        Handler mHandler = new Handler();
        TimerTask timerTask = new TimerTask()  {
            @Override
            public void run(){
                count--;
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        tv.setText(String.valueOf(count));
                    }
                }, 1);

                if(count <=0)
                {
                    notificationManager.notify(1, notificationBuilder.build());
                    timer.cancel();
                }
            }

        };
        timer.schedule(timerTask, 0, 1000);
    }
}