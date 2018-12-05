package hr.foi.air1817.botanico.firebaseMessaging;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import hr.foi.air1817.botanico.MainActivity;
import hr.foi.air1817.botanico.R;

public class BotanicoFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        BotanicoNotificationManager.getInstance(getApplicationContext()).displayNotification(title, body);
    }

}
