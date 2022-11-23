package pw4.tgbot.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity//данный класс является JPA сущностью
@Table(name="incomes")//хранится в таблице incomes
@Data//геттеры и сеттеры
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//уникальный идентификатор в системе нашего бота

    @Column(name="chat_id")
    private Long chatId;//уникальный идентификатор в системе телеграм

    @Column(name="income")
    private BigDecimal income;//Неизменяемые десятичные числа со знаком произвольной точности. BigDecimal
}
