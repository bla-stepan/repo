package pw4.tgbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.WebServiceTemplate;
import pw4.tgbot.dto.GetCursOnDateXML;
import pw4.tgbot.dto.GetCursOnDateXmlResponse;
import pw4.tgbot.dto.ValuteCursOnDate;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
пункт 11 - создадим реализацию интерфейса, который будет отправлять запросы в сторону API Центрального банка России:
 */
//данный класс наследуется от WebServiceTemplate, который предоставляет удобный способ взаимодействия с SOAP веб сервисами
public class CentralRussianBankService extends WebServiceTemplate {
    //тут случается некоторая магия Spring и в момент запуска нашего приложения,сюда поставляются значение из application.properties
    //или application.yml
    @Value("${cbr.api.url}")//@Value("${cbr.api.url}")
    private String cbrApiUrl;//="http://www.cbr.ru/dailyinfowebserv/dailyinfo.asmx?wsdl";

    //создаем метод получения данных
    public List<ValuteCursOnDate> getCurrenciesFromCbr() throws DatatypeConfigurationException {
        final GetCursOnDateXML getCursOnDateXML = new GetCursOnDateXML();//создаем объект соотвествующего класса (запроса)
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());

        XMLGregorianCalendar xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        getCursOnDateXML.setOnDate(xmlGregCal);

        GetCursOnDateXmlResponse response = (GetCursOnDateXmlResponse) marshalSendAndReceive(cbrApiUrl, getCursOnDateXML);

        if (response == null){
            throw new IllegalStateException("Не удалось получить ответ от службы ЦБ РФ");
        }
        final List<ValuteCursOnDate> courses = response.getGetCursOnDateXmlResult().getValuteDate();
        courses.forEach(course -> course.setName(course.getName().trim()));
        return courses;
    }
}
