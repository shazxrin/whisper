package me.shazxrin.whisper.push.telegram;

import me.shazxrin.whisper.model.TelegramInfo;
import me.shazxrin.whisper.repository.TelegramInfoRepository;
import org.springframework.stereotype.Component;

@Component
public class TelegramStore {
    private final TelegramInfoRepository telegramInfoRepository;

    private Long chatId = null;

    public TelegramStore(TelegramInfoRepository telegramInfoRepository) {
        this.telegramInfoRepository = telegramInfoRepository;
    }

    public Long getChatId() {
        if (chatId == null) {
            TelegramInfo info = telegramInfoRepository.findFirstByIdNotNull();
            if (info != null) {
                chatId = info.getChatId();
            }
        }

        return chatId;
    }

    public void setChatId(Long chatId) {
        TelegramInfo info = telegramInfoRepository.findFirstByIdNotNull();
        if (info == null) {
            info = new TelegramInfo(null, chatId);
        } else {
            info.setChatId(chatId);
        }
        telegramInfoRepository.save(info);

        this.chatId = chatId;
    }
}
