package me.shazxrin.whisper.telegram;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class TelegramStore {
    private Long chatId = null;
}
