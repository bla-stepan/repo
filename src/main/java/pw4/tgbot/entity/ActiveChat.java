package pw4.tgbot.entity;

import lombok.Data;

import javax.persistence.*;

@Data//для того чтобы не писать геттеры и сеттеры ломбоковская штука
@Entity//Данный класс является JPA сущностью
@Table(name="ACTIVE_CHAT")//Хранится в таблице ACTIVE_CHAT
public class ActiveChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //уникальный идентификатор в системе нашего бота

    @Column(name="chat_id")
    private Long chatId; //уникальный идентификатор в системе телеги
}
