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

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CentralRussianBankService centralRussianBankService;

    @PostMapping("/getCurrencies")
    public List<ValuteCursOnDate> getValuteCursOnDate() throws Exception {
        return centralRussianBankService.getCurrenciesFromCbr();
    }
}
