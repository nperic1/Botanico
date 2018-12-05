package hr.foi.air1817.botanico.firebaseMessaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import hr.foi.air1817.botanico.MainActivity;
import hr.foi.air1817.botanico.R;

public class BotanicoNotificationManager implements PushNotificationManager {
    private Context context;
    private static  BotanicoNotificationManager instance;

    public static final String CHANNEL_ID = "botanico_channel_01";
    public static final String CHANNEL_NAME = "Botanico Channel";
    public static final String CHANNEL_DESCRIPTION = "default channel";

    private BotanicoNotificationManager(Context context){
        this.context = context;
    }

    public static BotanicoNotificationManager getInstance(Context context){
        if(instance == null){
            instance = new BotanicoNotificationManager(context);
        }
        return instance;
    }

    @Override
    public void displayNotification(String title, String body) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_add_new)
                        .setContentTitle(title)
                        .setContentText(body);

        Intent resultIntent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);

        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        if (mNotifyMgr != null) {
            mNotifyMgr.notify(2, mBuilder.build());
        }
    }

    @Override
    public void createChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            mChannel.setDescription(CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mNotificationManager.createNotificationChannel(mChannel);
        }
    }
}
