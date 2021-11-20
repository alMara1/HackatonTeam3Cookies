package cz.czechitas.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HackatonTesty {

    public static final String URL_EN = "http://czechitas-datestovani-hackathon.cz/en/";
    public static final String URL_CS = "http://czechitas-datestovani-hackathon.cz/cs/";
    WebDriver prohlizec;
    WebDriverWait cekaniNaPrvek;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.home") + "/Java-Training/Selenium/geckodriver");
        //System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        prohlizec = new FirefoxDriver();
        prohlizec.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        cekaniNaPrvek = new WebDriverWait(prohlizec,3);
    }

    @Disabled
    @Test
    public void zakladniSmokeTestOvereniPrvku() {
        prohlizec.navigate().to(URL_CS);
        kontrolaHomePage();
        prohlizec.navigate().to(URL_EN);
        kontrolaHomePage();
    }

    @Disabled
    @Test
    public void kdyzUzivatelZadaJmenoAHesloPrihlasiSeDoSystemu() {
        // login:liskapodsitaczechitas@seznam.cz heslo:liskapodsita
        prihlaseniUzivatele("liskapodsitaczechitas@seznam.c","liskapodsita");
        prohlizec.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        Assertions.assertNotNull(prohlizec.findElement(By.xpath("//button[@id='user_info_acc']")),"NEPODARILO se prihlasit");

    }

    @Test
    public void kontrolaPritomnostiPrvku (){
        prohlizec.navigate().to(URL_CS);
        kontrolaPritomnostiPrvkuPodleXpath("//p[text() = 'Interior']");
    }

    public void kontrolaPritomnostiPrvkuPodleXpath(String xPath) {
        try {
            prohlizec.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            prohlizec.findElement(By.xpath(xPath));
        } catch (NoSuchElementException elementException) {
            System.out.println("Nepodarilo se najit xPath, vyhazujehlasku: " + elementException);
            System.exit(1);
        }

    }

    public void prihlaseniUzivatele(String username, String password) {
        prohlizec.navigate().to(URL_CS);
        cekaniNaPrvek.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class, 'user_login')]"))).click();
        cekaniNaPrvek.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys(username);
        prohlizec.findElement(By.id("passwd")).sendKeys(password);
        prohlizec.findElement(By.id("SubmitLogin")).click();
        prohlizec.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }


    public void kontrolaHomePage() {
        cekaniNaPrvek.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='nav_toggle']"))).click();
        Assertions.assertNotNull(prohlizec.findElement(By.xpath("//p[text() = 'Interior']")));
        Assertions.assertNotNull(prohlizec.findElement(By.xpath("//p[text() = 'Amenities']")));
        Assertions.assertNotNull(prohlizec.findElement(By.xpath("//p[text() = 'Our Rooms']")));
        Assertions.assertNotNull(prohlizec.findElement(By.xpath("//p[text() = 'What our Guest say?']")));
        Assertions.assertNotNull(prohlizec.findElement(By.xpath("//a[@class='user_login navigation-link']")));
    }

    @AfterEach
    public void tearDown () {
        prohlizec.quit();
    }
}
