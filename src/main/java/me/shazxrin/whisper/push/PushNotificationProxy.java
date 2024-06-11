package me.shazxrin.whisper.push;

public interface PushNotificationProxy {
    void sendNotification(String appId, String title, String content);
}
