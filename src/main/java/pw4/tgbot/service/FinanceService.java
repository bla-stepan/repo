package pw4.tgbot.service;
/*
создадим класс, который будет добавлять расходов и доходов.
 */

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pw4.tgbot.entity.Income;
import pw4.tgbot.entity.Spend;
import pw4.tgbot.repository.IncomeRepository;
import pw4.tgbot.repository.SpendRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FinanceService {

    private static final String ADD_INCOME = "/addincome";
    private final IncomeRepository incomeRepository;
    private final SpendRepository spendRepository;

    public String addFinanceOperation(String operationType, String price, Long chatId) {
        String message;//строковая переменная сообщения
        if (ADD_INCOME.equalsIgnoreCase(operationType)) {//Сравнивает эту строку с другой строкой, игнорируя регистровые соображения.
            Income income = new Income();//создается объект класса
            income.setChatId(chatId);//передаем ид чата
            income.setIncome(new BigDecimal(price));//передаем неизменяемые десятичные числа со знаком произвольной точности. BigDecimal
            incomeRepository.save(income);
            message = "Доход в размере " + price + " был успешно добавлен";
        } else {
            Spend spend = new Spend();
            spend.setChatId(chatId);
            spend.setSpend(new BigDecimal(price));
            spendRepository.save(spend);
            message = "Расход в размере " + price + " был успешно добавлен";
        }
        return message;
    }
}
