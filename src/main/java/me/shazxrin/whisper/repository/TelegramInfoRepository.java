package me.shazxrin.whisper.repository;

import me.shazxrin.whisper.model.TelegramInfo;
import org.springframework.data.repository.CrudRepository;

public interface TelegramInfoRepository extends CrudRepository<TelegramInfo, String> {
    TelegramInfo findFirstByIdNotNull();
}
