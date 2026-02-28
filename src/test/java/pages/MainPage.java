package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private final WebDriver driver;

    // Верхняя кнопка «Заказать»
    private final By topOrderButton = By.xpath(".//button[text()='Заказать']");

    // Нижняя кнопка «Заказать»
    private final By bottomOrderButton = By.xpath(".//button[text()='Заказать']");

    // Заголовок раздела «Вопросы о важном»
    private final By faqHeader = By.xpath(".//div[text()='Вопросы о важном']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(faqHeader));
    }

    public void clickTopOrderButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(topOrderButton)).click();
    }

    public void clickBottomOrderButton() {
        WebElement button = driver.findElement(bottomOrderButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(button)).click();
    }

    public String getAnswer(int questionNumber) {
        // Вопросы в разделе "Вопросы о важном"
        By question = By.id("accordion__heading-" + questionNumber);
        // Ответ на соответствующий вопрос
        By answer = By.xpath(".//div[@id='accordion__panel-" + questionNumber + "']/p");

        WebElement questionElement = driver.findElement(question);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", questionElement);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", questionElement);

        return new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(answer)).getText();
    }
}