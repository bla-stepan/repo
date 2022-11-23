package pw4.tgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pw4.tgbot.dto.ValuteCursOnDate;
import pw4.tgbot.entity.ActiveChat;
import pw4.tgbot.repository.ActiveChatRepository;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
класс, который будет запрашивать каждые три часа запрашивать данные у ББ РФ и в случае, если курс валюты изменился более
чем на 10 рублей, будет уведомлять наших пользователей нашего бота об этом:
 */
@Component
//Указывает, что класс является "компонентом". Такие классы рассматриваются как кандидаты на автоматическое обнаружение при использовании конфигурации на основе аннотаций и сканирования путей к классам.
@RequiredArgsConstructor//конструктор
public class ScheduleService {

    private final ActiveChatRepository activeChatRepository;
    private final BotService botService;
    private final CentralRussianBankService centralRussianBankService;
    private final List<ValuteCursOnDate> previousRates = new ArrayList<>();

    @Scheduled(cron = "0 0 0/3 ? * *")
    public void notifyAboutChangesInCurrencyRate() {
        try {
            List<ValuteCursOnDate> currentRates = centralRussianBankService.getCurrenciesFromCbr();
            Set<Long> activeChatIds = activeChatRepository.findAll().stream().map(ActiveChat::getChatId).collect(Collectors.toSet());
            if (!previousRates.isEmpty()) {
                for (int index = 0; index < currentRates.size(); index++) {
                    if (currentRates.get(index).getCourse() - previousRates.get(index).getCourse() >= 10.0) {
                        botService.sendNotificationToAllActiveChats("Курс " + currentRates.get(index).getName() + " увеличился на 10 руб.", activeChatIds);
                    } else if (currentRates.get(index).getCourse() - previousRates.get(index).getCourse() <= 10.0) {
                        botService.sendNotificationToAllActiveChats("Курс "+currentRates.get(index).getName()+" уменьшился на 10 руб.", activeChatIds);
                    }
                }
            }else {
                previousRates.addAll(currentRates);
            }
        }catch (DatatypeConfigurationException e){
            e.printStackTrace();
        }
    }
}
