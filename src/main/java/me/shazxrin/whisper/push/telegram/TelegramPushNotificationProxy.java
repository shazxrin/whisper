package me.shazxrin.whisper.push.telegram;

import me.shazxrin.whisper.dto.NotificationMessageDto;
import me.shazxrin.whisper.push.PushNotificationProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class TelegramPushNotificationProxy implements PushNotificationProxy {
    private static final Logger log = LoggerFactory.getLogger(TelegramPushNotificationProxy.class);

    private final TelegramClient telegramClient;
    private final TelegramStore telegramStore;

    @Autowired
    public TelegramPushNotificationProxy(TelegramClient telegramClient, TelegramStore telegramStore) {
        this.telegramClient = telegramClient;
        this.telegramStore = telegramStore;
    }

    @Override
    public void sendNotification(String appId, String title, String content) {
        if (telegramStore.getChatId() == null) {
            log.warn("Telegram user not bound! Skipping sending notification message...");
            return;
        }

        SendMessage telegramMessage = SendMessage.builder()
            .chatId(telegramStore.getChatId())
            .text(String.format("<b>%s</b>\n\n<i>%s</i>\n\n%s", appId, title, content))
            .parseMode("HTML")
            .build();

        try {
            telegramClient.execute(telegramMessage);
        } catch (TelegramApiException e) {
            log.error("Error while sending Telegram notification message!", e);
        }
    }
}
