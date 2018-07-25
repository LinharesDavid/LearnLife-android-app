package com.learnlife.learnlife.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.login.view.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Adama on 25/07/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private final String Tag = getClass().getSimpleName();
    public static final int ID_NOTIFICATION = 0;
    private final String CHANNEL_ID = "default";
    private final String CHANNEL_NAME = "LEARNLIFE_CHANNEL";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData().size() > 0){
            Log.d(Tag, "Notification from : "+remoteMessage.getFrom());
            Log.d(Tag, "Notification data : "+remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                buildJsonNotification(json);
            } catch (Exception e) {
                Log.e(Tag, "Exception: " + e.getMessage());
            }

        }
    }

    public void buildJsonNotification(JSONObject json){
        Log.e(Tag, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String title = data.getString("title");
            String message = data.getString("message");

            //creating an intent for the notification
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

            showNotification(title, message, intent);
        } catch (JSONException e) {
            Log.e(Tag, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(Tag, "Exception: " + e.getMessage());
        }
    }

    public void showNotification(String title, String message, Intent intent) {
        Context context = getApplicationContext();
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        ID_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        Notification notification;
        notification = mBuilder.setSmallIcon(R.drawable.ic_launcher).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher))
                .setContentText(message)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        if (notificationManager != null) {
            notificationManager.notify(ID_NOTIFICATION, notification);
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(Tag, "New Token : "+s);
    }
}
