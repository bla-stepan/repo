package pw4.tgbot.service;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pw4.tgbot.repository.IncomeRepository;
import pw4.tgbot.repository.SpendRepository;

/*
класс-тест для тестирования FinanceService
 */
@SpringBootTest//указывает что класс-тестовый под управлением Spring boot
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//указываем что инстанст теста создается на весь класс (для всех методов класса)
public class FinanceServiceTest {
    /*
    упонимание нашего сервиса с аннотацией, которая имитирует сервис и включает имитацию нужных зависимостей, так как в тестах
    не вся программа запускается, а только ее часть
     */
    @InjectMocks
    private FinanceService financeService;

    //указываем, что нам надо имитировать этот класс
    @Mock
    private SpendRepository spendRepository;

    @Mock
    private IncomeRepository incomeRepository;

    //записываем время начала каждого теста
    @BeforeEach
    public void beforeEach() {
        System.out.println(System.currentTimeMillis());
    }

    //записываем время завершения каждого теста
    @AfterEach
    public void afterEach() {
        System.out.println(System.currentTimeMillis());
    }

    //тестовый метод
    @DisplayName("ADD_INCOME_test")
    @Test
//    public void addFinanceServiceTest() {
    public void addFinanceServiceAddIncomeTest() {
        //указываем произвольное значение переменной, для отправки в метод
        String price = "150.0";
        //обращаемся к методу с произвольными параметрами и сохраняем результат в переменную
        String message = financeService.addFinanceOperation("/addincome", price, 500L);
        //убеждаемся что получим ожидаемый результат
        Assert.assertEquals("Доход в размере " + price + " был успешно добавлен", message);
//        //меняем значение на какоенибидь другое
//        price = "200";
//        //снова обращаемся в метод
//        message = financeService.addFinanceOperation("/addspend", price, 250L);
//        //убеждаемся что получим ожидаемый результат
//        Assert.assertEquals("Расход в размере " + price + " был успешно добавлен", message);
    }

    @DisplayName("ADD_SPEND_test")
    @Test
    public void addFinanceServiceAddSpendTest() {
        //указываем произвольное значение переменной, для отправки в метод
        String price = "200";
        //обращаемся к методу с произвольными параметрами и сохраняем результат в переменную
        String message = financeService.addFinanceOperation("/addspend", price, 250L);
        //убеждаемся что получим ожидаемый результат
        Assert.assertEquals("Расход в размере " + price + " был успешно добавлен", message);
    }
}
