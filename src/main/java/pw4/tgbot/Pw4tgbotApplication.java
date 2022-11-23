package pw4.tgbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling//добавляем аннотацию для дальнейшего уведомления о превышении курсов валют
@SpringBootApplication
public class Pw4tgbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(Pw4tgbotApplication.class, args);
    }

}
