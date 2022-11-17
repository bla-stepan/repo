package pw4.tgbot.dto;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//получить курс на дату ответа
//ОТВЕТ
@XmlRootElement(name = "GetCursOnDateXmlResponse", namespace = "http://web.cbr.ru/")//указывает XMLтэг и пространство имен
@XmlAccessorType(XmlAccessType.FIELD)//указывает как получить/указать значение поля данного объекта
@Data//геттеры и сеттеры
public class GetCursOnDateXmlResponse {

    @XmlElement(name = "GetCursOnDateXMLResult", namespace = "http://web.cbr.ru/")
    private GetCursOnDateXmlResult getCursOnDateXmlResult;
}
