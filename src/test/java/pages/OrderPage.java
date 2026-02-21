package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;

    // Поле ввода имени
    private final By nameField = By.xpath(".//input[@placeholder='* Имя']");

    // Поле ввода фамилии
    private final By surnameField = By.xpath(".//input[@placeholder='* Фамилия']");

    // Поле ввода адреса доставки
    private final By addressField = By.xpath(".//input[contains(@placeholder, 'Адрес')]");

    // Поле выбора станции метро
    private final By metroField = By.xpath(".//input[@placeholder='* Станция метро']");

    // Поле ввода телефона
    private final By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка перехода ко второй форме
    private final By nextButton = By.xpath(".//button[text()='Далее']");

    // Поле выбора даты доставки
    private final By dateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");

    // Поле выбора срока аренды (выпадающий список)
    private final By rentalField = By.className("Dropdown-placeholder");

    // Чекбокс выбора цвета "чёрный жемчуг"
    private final By blackCheckbox = By.id("black");

    // Чекбокс выбора цвета "серая безысходность"
    private final By greyCheckbox = By.id("grey");

    // Поле для комментария курьеру
    private final By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");

    // Кнопка «Заказать» во второй форме
    private final By orderButton = By.xpath(".//div[contains(@class, 'Order_Buttons')]//button[text()='Заказать']");

    // Заголовок окна подтверждения заказа
    private final By confirmHeader = By.xpath(".//div[contains(text(), 'Хотите оформить заказ?')]");

    // Кнопка «Да» в окне подтверждения
    private final By confirmButton = By.xpath(".//button[text()='Да']");

    // Заголовок окна с номером заказа
    private final By orderSuccessHeader = By.xpath(".//div[contains(text(), 'Заказ оформлен')]");

    // Строка с номером заказа
    private final By orderNumber = By.xpath(".//div[contains(text(), 'Номер заказа:')]");

    // Кнопка «Посмотреть статус» в окне с номером заказа
    private final By trackButton = By.xpath(".//div[contains(@class, 'Order_NextButton')]//button[text()='Посмотреть статус']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillFirstForm(String name, String surname, String address, String metro, String phone) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);

        driver.findElement(metroField).click();
        driver.findElement(metroField).sendKeys(metro);

        By stationOption = By.xpath(".//div[text()='" + metro + "']");
        wait.until(ExpectedConditions.elementToBeClickable(stationOption)).click();

        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    public void fillSecondForm(String date, String rentalDays, String color, String comment) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.visibilityOfElementLocated(dateField)).sendKeys(date);
        driver.findElement(dateField).sendKeys(Keys.ENTER);

        driver.findElement(rentalField).click();

        By rentalOption = By.xpath(".//div[@class='Dropdown-menu']/div[text()='" + rentalDays + "']");
        wait.until(ExpectedConditions.elementToBeClickable(rentalOption)).click();

        if (color.equals("black")) {
            driver.findElement(blackCheckbox).click();
        } else {
            driver.findElement(greyCheckbox).click();
        }

        if (!comment.isEmpty()) {
            driver.findElement(commentField).sendKeys(comment);
        }

        wait.until(ExpectedConditions.elementToBeClickable(orderButton)).click();
    }

    public void confirmOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmHeader));
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
    }

    public boolean isOrderSuccess() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessHeader));
            wait.until(ExpectedConditions.visibilityOfElementLocated(orderNumber));
            WebElement trackBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(trackButton));
            return trackBtn.isDisplayed();
        } catch (Exception e) {
            throw new AssertionError("Баг в Chrome: Окно с номером заказа не появилось после подтверждения");
        }
    }
}