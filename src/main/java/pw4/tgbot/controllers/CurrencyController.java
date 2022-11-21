package pw4.tgbot.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pw4.tgbot.dto.GetCursOnDateXmlResponse;
import pw4.tgbot.dto.GetCursOnDateXmlResult;
import pw4.tgbot.dto.ValuteCursOnDate;
import pw4.tgbot.service.CentralRussianBankService;

import java.awt.*;
import java.util.List;

/**
 * Пункт 13 Необходимо создать REST контроллеры, которые будут отдавать наружу данные полученные от ЦБ РФ. для этого создаем
 * отдельный пакет controllers,в котором будут храниться наши контроллеры
 */

@RestController//аннотация для указания spring класса контроллера ломбок
@RequiredArgsConstructor//аннотация создания конструктора с аргументами ломбок
public class CurrencyController {
    private final CentralRussianBankService centralRussianBankService;

    @PostMapping("/getCurrencies")//аннотация указывающая, что при любой операции по указанному адресу приведен на это место
    //@ApiOperation(vlue="Получение курсов всех валют на текущий день")
    public List<ValuteCursOnDate> getValuteCursOnDate() throws Exception {
        return centralRussianBankService.getCurrenciesFromCbr();
    }
}
