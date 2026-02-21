
package tests;

import pages.MainPage;
import pages.OrderPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class OrderTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);
        mainPage.open();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    private String getTomorrowDate() {
        return LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @ParameterizedTest
    @CsvSource({
            "Верхняя, Иван, Петров, Москва, Сокольники, +79991112233, black",
            "Нижняя, Мария, Иванова, Санкт-Петербург, Черкизовская, +79991114455, grey"
    })
    public void testOrder(String button, String name, String surname, String address,
                          String metro, String phone, String color) {

        System.out.println("Тест с метро: " + metro);

        try {
            if (button.equals("Верхняя")) {
                mainPage.clickTopOrderButton();
            } else {
                mainPage.clickBottomOrderButton();
            }


            orderPage.fillFirstForm(name, surname, address, metro, phone);

            String date = getTomorrowDate();
            orderPage.fillSecondForm(date, "сутки", color, "");

            orderPage.confirmOrder();

            boolean isSuccess = orderPage.isOrderSuccess();
            assertTrue(isSuccess, "Заказ не оформился!");

        } catch (Exception e) {
            fail("Тест упал с неожиданной ошибкой: " + e.getMessage());
        }
    }
}
