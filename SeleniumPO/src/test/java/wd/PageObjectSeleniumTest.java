package wd;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.testng.Assert.*;

public class PageObjectSeleniumTest {
    String base_url = "http://localhost:8080/login?from=%2F";
    String first_page = "http://localhost:8080";
    String create_page = "http://localhost:8080/securityRealm/addUser";
    String manageUsers = "http://localhost:8080/securityRealm/";
    StringBuffer verificationErrors = new StringBuffer();
    WebDriver driver = null;
    PageObjectSelenium page;

    @BeforeClass
    public void beforeClass() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:\\Курсы\\chromedriver.exe");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("chrome.switches", Arrays.asList("--homepage=about:blank"));
        driver = new ChromeDriver(capabilities);
    }


    @AfterClass
    public void afterClass() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            Assert.fail(verificationErrorString);
        }
    }

    @Test(priority = 1)
    public void sampleTestForSing() {
        driver.get(base_url);
        page = new PageObjectSelenium(driver);
        page.setLogin("sergey");
        page.setPassword("05apr1999");
        Assert.assertEquals(page.getUserLogin(), "sergey", "Unable to fill 'Имя' field");
        Assert.assertEquals(page.getUserPassword(), "05apr1999", "Unable to fill 'Имя' field");
        page.submitForm();
        System.out.println("Авторизация");
    }

    @Test(priority = 2)
    public void testNumberOneAndTwo() {
        driver.get(first_page);
        page = new PageObjectSelenium(driver);
        page.submitManageJenkins();
        page.submitManageUsers();
        page.submitCreateUser();
        System.out.println("Manage Jenkins -> Manage Users -> Create user");
    }

    @Test(priority = 3)
    public void testNumberThree() {
        driver.get(create_page);
        page = new PageObjectSelenium(driver);
        Assert.assertEquals(page.getUserName(), "", "User name filled");
        Assert.assertEquals(page.getPassword(), "", "User password filled");
        Assert.assertEquals(page.getConfirmPassword(), "", "Confirm password filled");
        Assert.assertEquals(page.getFullName(), "", "Full name filled");
        Assert.assertEquals(page.getEmail(), "", "User email filled");
        System.out.println("Check empty fields");
    }

    @Test(priority = 4)
    public void testNumberFour() {
        driver.get(create_page);
        page = new PageObjectSelenium(driver);
        page.setUserNameInCreateUser("someuser");
        page.setUserPasswordInCreateUser("somepassword");
        page.setConfirmPasswordInCreateUser("somepassword");
        page.setUserFullNameInCreateUser("Some Full Name");
        page.setUserEmailInCreateUser("some@addr.dom");
        page.submitButtonCreateUser();
        Assert.assertEquals(page.getNewUser(), "someuser", "Don't match");
        Assert.assertEquals(page.getNewUser(), "someuser");

    }

    @Test(priority = 5,expectedExceptions = org.openqa.selenium.NoSuchElementException.class)
    public void testNumberFiveAndSix() {
        driver.get(manageUsers);
        page = new PageObjectSelenium(driver);
        page.submitButtonDeleteUser();
        page.submitButtonConfirmToDelteUser();
        Assert.assertEquals(page.getNewUserDelete(), manageUsers + "user/someuser/delete");
        System.out.println("Успешность теста в проверки на не существование удаленной записи");
    }
}