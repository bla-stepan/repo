package pw4.tgbot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pw4.tgbot.entity.ActiveChat;

import java.util.Optional;

@Repository//Указывает, что класс является "хранилищем", первоначально определенным Domain-Driven Design (Evans, 2003) как "механизм для инкапсуляции хранения, извлечения и поискового поведения, который эмулирует коллекцию объектов".
public interface ActiveChatRepository extends JpaRepository<ActiveChat, Long> {
    Optional<ActiveChat> findActiveChatByChatId(Long chatId);
}
