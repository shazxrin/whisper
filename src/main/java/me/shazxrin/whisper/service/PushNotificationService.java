package me.shazxrin.whisper.service;

import me.shazxrin.whisper.dto.NotificationMessageDto;
import me.shazxrin.whisper.push.PushNotificationProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {
    private final PushNotificationProxy pushNotificationProxy;

    @Autowired
    public PushNotificationService(PushNotificationProxy pushNotificationProxy) {
        this.pushNotificationProxy = pushNotificationProxy;
    }

    public void sendNotification(String appId, String title, String content) {
        this.pushNotificationProxy.sendNotification(appId, title, content);
    }
}
