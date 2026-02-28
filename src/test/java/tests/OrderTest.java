package tests;

import drivers.DriverFactory;
import pages.MainPage;
import pages.OrderPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;

    @BeforeEach
    public void setUp() {
        DriverFactory.useChrome();
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);
        mainPage.open();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private String getTomorrowDate() {
        return LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @ParameterizedTest
    @CsvSource({
            "Верхняя, Иван, Петров, Москва, Сокольники, +79991112233, black",
            "Нижняя, Мария, Иванова, Москва, Черкизовская, +79991114455, grey"
    })
    public void testOrder(String button, String name, String surname, String address,
                          String metro, String phone, String color) {

        if (button.equals("Верхняя")) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        orderPage.fillFirstForm(name, surname, address, metro, phone);

        String date = getTomorrowDate();

        orderPage.fillSecondForm(date, "сутки", "", "");

        orderPage.confirmOrder();

        boolean isSuccess = orderPage.isOrderSuccess();

        if (!isSuccess) {

            String browser = "chrome";
            if ("chrome".equals(browser)) {
                fail("Баг в Chrome: Окно с номером заказа не появилось после подтверждения");
            } else {
                fail("Заказ не оформился в " + browser);
            }
        } else {
            assertTrue(true);
        }
    }
}