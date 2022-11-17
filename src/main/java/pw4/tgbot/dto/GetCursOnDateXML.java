package pw4.tgbot.dto;
/*
Создадим наш DAO (объекты доступа к данным) для работы с API ЦБ РФ. Для этого заведем отдельный пакет DTO и создадим
классы запросов и ответом
 */
//ЗАПРОС
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlRootElement(name="GetCursOnDateXML", namespace = "http://web.cbr.ru/")
@Data//геттеры и сеттеры
public class GetCursOnDateXML {
    @XmlElement(name = "On_date", required = true, namespace = "http://web.cbr.ru/")//указание на то в каком теге XML должно быть данное поле
    protected XMLGregorianCalendar onDate;
}

