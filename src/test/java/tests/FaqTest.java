package tests;

import pages.MainPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FaqTest {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testQuestion0() {
        String actual = mainPage.getAnswer(0);
        assertEquals("Сутки — 400 рублей. Оплата курьеру — наличными или картой.", actual);
    }

    @Test
    public void testQuestion1() {
        String actual = mainPage.getAnswer(1);
        assertEquals("Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.", actual);
    }

    @Test
    public void testQuestion2() {
        String actual = mainPage.getAnswer(2);
        assertEquals("Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.", actual);
    }

    @Test
    public void testQuestion3() {
        String actual = mainPage.getAnswer(3);
        assertEquals("Только начиная с завтрашнего дня. Но скоро станем расторопнее.", actual);
    }

    @Test
    public void testQuestion4() {
        String actual = mainPage.getAnswer(4);
        assertEquals("Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.", actual);
    }

    @Test
    public void testQuestion5() {
        String actual = mainPage.getAnswer(5);
        assertEquals("Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.", actual);
    }

    @Test
    public void testQuestion6() {
        String actual = mainPage.getAnswer(6);
        assertEquals("Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.", actual);
    }

    @Test
    public void testQuestion7() {
        String actual = mainPage.getAnswer(7);
        assertEquals("Да, обязательно. Всем самокатов! И Москве, и Московской области.", actual);
    }
}