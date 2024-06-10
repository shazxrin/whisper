package me.shazxrin.whisper.listener;

import me.shazxrin.whisper.dto.NotificationMessage;
import me.shazxrin.whisper.telegram.TelegramStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class NotificationListener {
    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);

    private final TelegramClient telegramClient;
    private final TelegramStore telegramStore;

    @Autowired
    public NotificationListener(TelegramClient telegramClient, TelegramStore telegramStore) {
        this.telegramClient = telegramClient;
        this.telegramStore = telegramStore;
    }

    @RabbitListener(queues = "${whisper.notification-queue-name}")
    public void processNotificationMessage(NotificationMessage notificationMessage) {
        log.info("Received notification message.");

        if (telegramStore.getChatId() == null) {
            log.warn("Telegram user not bound! Skipping sending notification message...");
            return;
        }

        SendMessage telegramMessage = SendMessage.builder()
            .chatId(telegramStore.getChatId())
            .text(String.format("From %s: <%s: %s>", notificationMessage.appId(), notificationMessage.title(), notificationMessage.content()))
            .build();

        try {
            telegramClient.execute(telegramMessage);
        } catch (TelegramApiException e) {
            log.error("Error while sending Telegram notification message!", e);
        }
    }
}
