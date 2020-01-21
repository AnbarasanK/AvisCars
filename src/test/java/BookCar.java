import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class BookCar {

    WebDriver driver;

    @Test
    void carBooking() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);

        driver.get("https://www.avis.co.in/landingpage-avis-flat25-1.aspx?utm_source=Google&utm_medium=CPC&utm_campaign=Search_SDOffer&gclid=EAIaIQobChMIxOHSsen95gIVi2kqCh26Fw-UEAAYASAAEgLfuPD_BwE");
        waitforPageLoad();

        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement address = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@name='ctl00$SearchControl_desktop$txtPickUp']")));

        JavascriptExecutor jse2 = (JavascriptExecutor) driver;
        jse2.executeScript("arguments[0].scrollIntoView()", driver.findElement(By.xpath("//*[@name='ctl00$SearchControl_desktop$txtPickUp']")));

        driver.findElement(By.xpath("//*[@name='ctl00$SearchControl_desktop$txtPickUp']")).click();
        address.sendKeys("Rajakilpakkam");

        WebElement city = wait.until(ExpectedConditions.elementToBeClickable(By.id("DrpCity")));

        Select selectCity = new Select(city);
        selectCity.selectByVisibleText("Chennai");

        WebElement datetimepicker = wait.until(ExpectedConditions.elementToBeClickable(By.id("datetimepicker")));

        jse2.executeScript("arguments[0].scrollIntoView()", driver.findElement(By.id("datetimepicker")));

        GregorianCalendar calendar = new GregorianCalendar();
        DateFormat dateFormat = new SimpleDateFormat("dd MMM YYYY HH:mm");
        calendar.add(calendar.DAY_OF_MONTH, 1);
        Date tomorrow = calendar.getTime();
        String startDate = dateFormat.format(tomorrow);

        calendar.add(calendar.DAY_OF_MONTH, 5);
        Date fivedaysFromToday = calendar.getTime();
        String endDate = dateFormat.format(fivedaysFromToday);

        jse2.executeScript("$('#datetimepicker').datetimepicker({\n" +
                " value: '" + startDate + "'\n" +
                " });");

        jse2.executeScript("$('#datetimepicker1').datetimepicker({\n" +
                " value: '" + endDate + "'\n" +
                " });");

        WebElement car = driver.findElement(By.id("ctl00_SearchControl_desktop_drpCars"));
        car.click();
        Select selectCar = new Select(car);
        selectCar.selectByIndex(1);

        driver.findElement(By.id("ctl00_SearchControl_desktop_LnkSubmit")).click();
        waitforPageLoad();
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_RptCar_ctl00_LnkSelect")).click();
    }


    public void waitforPageLoad() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 40);
            wait.until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver driver) {
                    try {
                        return ((((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete")) && (((JavascriptExecutor) driver).executeScript("return JQuery.active").toString().equals("0")));
                    } catch (Exception e) {
                        return true;
                    }
                }
            });
        } catch (Exception e) {
        }
    }
}
