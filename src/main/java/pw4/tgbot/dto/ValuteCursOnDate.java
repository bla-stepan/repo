package pw4.tgbot.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//ОТВЕТ
@XmlAccessorType(XmlAccessType.FIELD)//указывает как получить/указать значение поля
@XmlRootElement(name = "ValueCursOnDate")//корневой элемент
@Data//геттеры и сеттеры
public class ValuteCursOnDate {
    @XmlElement(name = "Vname")//навзвание xml тэга
    private String name;

    @XmlElement(name = "Vnom")
    private int nominal;

    @XmlElement(name = "Vcurs")
    private double course;

    @XmlElement(name = "Vcode")
    private String code;

    @XmlElement(name = "VchCode")
    private String chCode;
}

