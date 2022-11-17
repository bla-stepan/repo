package pw4.tgbot.controllers;

import io.micrometer.core.instrument.binder.hystrix.MicrometerMetricsPublisherThreadPool;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pw4.tgbot.dto.ValuteCursOnDate;
import pw4.tgbot.service.CentralRussianBankService;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.List;

/**
 * Пункт 13 Необходимо создать REST контроллеры, которые будут отдавать наружу данные полученные от ЦБ РФ. для этого создаем
 * отдельный пакет controllers,в котором будут храниться наши контроллеры
 */

@RestController//аннотация для указания spring класса контроллера ломбок
@RequiredArgsConstructor//аннотация создания конструктора с аргументами ломбок
public class CurrencyController {
    private final CentralRussianBankService centralRussianBankService;

    @PostMapping("/getCurrencies")//аннотация указывающая что при любой опаерации по указанному адресу приведен на это место
    public List<ValuteCursOnDate> getValueCursOnDate() throws Exception {
        return centralRussianBankService.getCurrenciesFromCbr();
    }
}
