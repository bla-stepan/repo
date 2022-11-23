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
import pw4.tgbot.entity.ActiveChat;
import pw4.tgbot.repository.ActiveChatRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service//Данный класс явялется сервисом
@Slf4j//подключаем логирование из Ломбока
@RequiredArgsConstructor//делается конструктор с аргументами
public class BotService extends TelegramLongPollingBot {

    private static final String CURRENT_RATES = "/curs";
    private static final String ADD_INCOME = "/addincome";
    private static final String ADD_SPEND = "/addspend";

    private final CentralRussianBankService centralRussianBankService;//list с информацией о курсах валют
    private final ActiveChatRepository activeChatRepository;//этого в модуле нет
    private final FinanceService financeService;

    @Value("${bot.api.key}")
//сюда будет вставлено значение из application.properties, в котором будет указан api key, полученный от BotFather
    private String apiKey;

    @Value("${bot.name}")//как будут звать нашего бота
    private String name;

    //чтобы хранить предыдущие сообщения от пользователя, чтобы знать точно на какую именно команду пользователь отправляет
    // сумму. для этого заводим Мар в котором будут храниться история сообщений
    private Map<Long, List<String>> previousCommands = new ConcurrentHashMap<>();

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
            if (CURRENT_RATES.equalsIgnoreCase(message.getText())) {
                //получаем все курсы валют на текущий момент и проходимся по ним в цикле
                for (ValuteCursOnDate valuteCursOnDate : centralRussianBankService.getCurrenciesFromCbr()) {
                    /*В данной строчке мы собираем наше текстовое сообщение
                     * StringUtils.defaultBank - это метод из библиотеки Apache Commons, который нам нужен для того, чтобы на
                     * первой итерации нашего цикла была вставлена пустая строка вместо null, а на следующих итерациях не
                     * перетерся текст, полученный из предыдущей итарации. Подключение библиотеки смю ниже*/
                    response.setText(StringUtils.defaultIfBlank(response.getText(), "") + valuteCursOnDate.getName() + " - " + valuteCursOnDate.getCourse() + "\n");
                }
            }else if (ADD_INCOME.equalsIgnoreCase(message.getText())){
                response.setText("Отправьте мне сумму полученного дохода");
            }else if (ADD_SPEND.equalsIgnoreCase(message.getText())){
                response.setText("Отправьте мне сумму расходов");
            }else {
                response.setText(financeService.addFinanceOperation(getPreviousCommand(message.getChatId()), message.getText(), message.getChatId()));
            }

            putPreviousCommand(message.getChatId(), message.getText());
            //теперь мы сообщаем, что нужно отправлять ответ
            execute(response);
            //проверяем, есть ли у нас такой chatIDв базе, если нет, то добавим, если есть, то пропускаем данный шаг
            if (activeChatRepository.findActiveChatByChatId(chatId).isEmpty()) {
                ActiveChat activeChat = new ActiveChat();
                activeChat.setChatId(chatId);
                activeChatRepository.save(activeChat);
            }
            //пока примитивная обработка исключений
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Доработаем наш BotService методом для отправления данных множественному количеству людей.
    public void sendNotificationToAllActiveChats(String message, Set<Long> chatId) {
        for (Long id : chatId) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(id));
            sendMessage.setText(message);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
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

    //метод записи истории команд пользователя
    private void putPreviousCommand(Long chatId, String command) {
        if (previousCommands.get(chatId) == null) {
            List<String> commands = new ArrayList<>();
            commands.add(command);
            previousCommands.put(chatId, commands);
        } else {
            previousCommands.get(chatId).add(command);
        }
    }
    //метод получения предыдущей команды пользователя
    private String getPreviousCommand(Long chatId) {
        return previousCommands.get(chatId).get(previousCommands.get(chatId).size() - 1);
    }
}
