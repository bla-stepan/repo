package pw4.tgbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import pw4.tgbot.dto.GetCursOnDateXML;
import pw4.tgbot.dto.GetCursOnDateXmlResponse;
import pw4.tgbot.dto.GetCursOnDateXmlResult;
import pw4.tgbot.dto.ValuteCursOnDate;
import pw4.tgbot.service.CentralRussianBankService;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import java.nio.charset.StandardCharsets;

/**
 * Пункт 12. Добавляем еще один пакет config. В этом пакете мы будем хранить бины конфигурации, их задача конфигурировать наше приложение
 */
@Configuration//Указывает, что класс объявляет один или несколько методов @Bean и может обрабатываться контейнером Spring для генерации определений компонентов и запросов на обслуживание для этих компонентов во время выполнения
public class AppConfig {
    @Bean
    public CentralRussianBankService cbrService() throws SOAPException {
        //создаем объект класса
        CentralRussianBankService cbrService = new CentralRussianBankService();
        //создаем объект интерфейса маршаллера отвечающего за управление процессом сериализации деревьев содержимого java обратно в XML-данные
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        //оздаем объект класса MessageFactory, отвечающий за формирование строк по шаблону что-то типа дополнения к String.format
        MessageFactory msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
        //создаем объект класса - конструктор принимающий в качестве аргумента MessageFactory
        SaajSoapMessageFactory newSoapMessageFactory = new SaajSoapMessageFactory(msgFactory);
        cbrService.setMessageFactory(newSoapMessageFactory);

        jaxb2Marshaller.setClassesToBeBound(//загружаем классы для привязки объект маршаллера
                GetCursOnDateXML.class,
                GetCursOnDateXmlResponse.class,
                GetCursOnDateXmlResult.class,
                ValuteCursOnDate.class);
        cbrService.setMarshaller(jaxb2Marshaller);//загружаем маршаллер сериализация данных в xml-файл
        cbrService.setUnmarshaller(jaxb2Marshaller);//загружаем маршаллер десериализация из xml-файла
        return cbrService;
    }

    //CharacterEncodingFilter - фильтр кодирования символов
    @Bean
    public CharacterEncodingFilter characterEncodingFilter(){
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding(StandardCharsets.UTF_8.name());
        filter.setForceEncoding(true);
        return filter;
    }
}
