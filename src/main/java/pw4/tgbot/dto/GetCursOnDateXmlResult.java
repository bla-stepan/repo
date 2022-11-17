package pw4.tgbot.dto;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

//ОТВЕТ
@XmlAccessorType(XmlAccessType.FIELD)//указывает как получить/указать значение поля
@XmlRootElement(name = "GetCursOnDateXmlResult")//корневой элемент
@Data//геттеры и сеттеры
public class GetCursOnDateXmlResult {

    @XmlElementWrapper(name = "ValuteDate", namespace = "")
    @XmlElement(name = "ValuteCursOnDate", namespace = "")
    private List<ValuteCursOnDate> valuteDate = new ArrayList<>();
}

