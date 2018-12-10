package hr.foi.air1817.botanico.firebaseMessaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import hr.foi.air1817.botanico.MainActivity;
import hr.foi.air1817.botanico.PlantRoomDatabase;
import hr.foi.air1817.botanico.R;
import hr.foi.air1817.botanico.entities.Plant;

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
                        .setSmallIcon(R.drawable.ic_botanico)
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

    @Override
    public void subscribeToTopic(final String topic) {
        List<Plant> plants = PlantRoomDatabase.getPlantRoomDatabase(context).plantDao().getAllPlants();
        String topicName = "";

        for (Plant p : plants) {
            topicName = String.valueOf(p.getId()) + "_" + topic;

            FirebaseMessaging.getInstance().subscribeToTopic(topicName)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = "Uspješno ste se pretplatili na temu!";
                            if (!task.isSuccessful()) {
                                msg = "Greška!";
                            }
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void unsubscribeFromTopic(String topic) {
        List<Plant> plants = PlantRoomDatabase.getPlantRoomDatabase(context).plantDao().getAllPlants();
        String topicName = "";

        for (Plant p : plants) {
            topicName = String.valueOf(p.getId()) + "_" + topic;
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topicName)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = "Uspješno ste se odjavili s teme!";
                            if (!task.isSuccessful()) {
                                msg = "Greška!";
                            }
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
