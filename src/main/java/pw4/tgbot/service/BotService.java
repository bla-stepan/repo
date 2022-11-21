package pw4.tgbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pw4.tgbot.dto.ValuteCursOnDate;

import javax.annotation.PostConstruct;

@Service//Данный класс явялется сервисом
@Slf4j//подключаем логирование из Ломбока
@RequiredArgsConstructor//делается конструктор с аргументами
public class BotService extends TelegramLongPollingBot {

    private final CentralRussianBankService centralRussianBankService;//list с информацией о курсах валют

    @Value("${bot.api.key}")//сюда будет вставлено значение из application.properties, в котором будет указан api key, полученный от BotFather
    private String apiKey;

    @Value("${bot.name}")//как будут звать нашего бота
    private String name;

    //Это основной метод, который связан с обработкой сообщений
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();//Этой строчкой мы получаем сообщение от пользователя
        try {
            //Данный класс представляет собой реализацию команды отправки сообщения, которую за нас выполнит ранее подключенная библиотека
            SendMessage response = new SendMessage();
            Long chatId = message.getChatId();//ID чата в который необходимо отправить ответ

            //Устанавливаем ID, полученный из предыдущего этапа сюда, чтобы сообщить в какой част необходимо отправить сообещение
            response.setChatId(String.valueOf(chatId));
            //Тут мы сравниваем, что прислал пользователь, и какие команды мы можем обработать. Пока телько одна команда
            if ("/currentrates".equalsIgnoreCase(message.getText())) {
                //получаем все курсы валют на текущий момент и проходимся по ним в цикле
                for (ValuteCursOnDate valuteCursOnDate : centralRussianBankService.getCurrenciesFromCbr()) {
                    /*В данной строчке мы собираем наше текстовое сообщение
                     * StringUtils.defaultBank - это метод из библиотеки Apache Commons, который нам нужен для того, чтобы на
                     * первой итерации нашего цикла была вставлена пустая строка вместо null, а на следующих итерациях не
                     * перетерся текст, полученный из предыдущей итарации. Подключение библиотеки смю ниже*/
                    response.setText(StringUtils.defaultIfBlank(response.getText(), "") + valuteCursOnDate.getName() + " - " + valuteCursOnDate.getCourse() + "\n");
                }
            }
            //теперь мы сообщаем, что нужно отправлять ответ
            execute(response);
            //ниже пока примитивная обработка исключений, потом поправим
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Данный метод будет вызван сразу после того, как данный бин будет создан - это обеспечено аннотацией Spring PostConstruct
    @PostConstruct
    public void start() {
        log.info("username: {}, token: {}", name, apiKey);
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return apiKey;
    }
}
