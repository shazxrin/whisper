package me.shazxrin.whisper.model;

import jakarta.persistence.*;

@Table(name = "telegram_info")
@Entity
public class TelegramInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Long chatId;

    public TelegramInfo() {
    }

    public TelegramInfo(String id, Long chatId) {
        this.id = id;
        this.chatId = chatId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
