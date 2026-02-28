package drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    private static boolean isChrome = true; // true = Chrome, false = Firefox

    public static WebDriver getDriver() {
        if (isChrome) {
            System.out.println("Запуск в Chrome");
            return new ChromeDriver();
        } else {
            System.out.println("Запуск в Firefox");
            return new FirefoxDriver();
        }
    }

    public static void useChrome() {
        isChrome = true;
    }

    public static void useFirefox() {
        isChrome = false;
    }
}