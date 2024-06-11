package me.shazxrin.whisper.push.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
public class TelegramBotListener implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    private final TelegramStore telegramStore;
    private final String botToken;
    private final String username;

    @Autowired
    public TelegramBotListener(
        @Value("${whisper.telegram-bot-token}") String botToken,
        @Value("${whisper.telegram-username}") String username,
        TelegramStore telegramStore
    ) {
        this.botToken = botToken;
        this.username = username;
        this.telegramStore = telegramStore;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (!update.hasMessage()) {
            return;
        }
        Message message = update.getMessage();

        if (update.getMessage().isCommand()) {
            String command = message.getText();
            if (command.equals("/start")) {
                User user = message.getFrom();
                if (user.getUserName().equals(this.username)) {
                    telegramStore.setChatId(message.getChat().getId());
                }
            }
        }
    }
}
