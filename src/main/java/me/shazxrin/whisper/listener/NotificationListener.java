package me.shazxrin.whisper.listener;

import me.shazxrin.whisper.dto.NotificationMessageDto;
import me.shazxrin.whisper.service.PushNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);

    private final PushNotificationService pushNotificationService;

    @Autowired
    public NotificationListener(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @RabbitListener(queues = "${whisper.notification-queue-name}")
    public void processNotificationMessage(NotificationMessageDto notificationMessageDto) {
        log.info("Received notification message.");

        this.pushNotificationService.sendNotification(
            notificationMessageDto.appId(),
            notificationMessageDto.title(),
            notificationMessageDto.content()
        );
    }
}
