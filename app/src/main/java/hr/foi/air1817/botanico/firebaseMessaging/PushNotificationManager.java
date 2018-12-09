package hr.foi.air1817.botanico.firebaseMessaging;

public interface PushNotificationManager {
    public void displayNotification(String title, String body);
    public void createChannel();
    public void subscribeToTopic(String topic);
    public void unsubscribeFromTopic(String topic);
}
