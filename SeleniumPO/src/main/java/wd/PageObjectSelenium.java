package wd;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObjectSelenium {
    private WebDriverWait wait;
    private final WebDriver driver;
    By username_locator = By.name("j_username");
    By userpassword_locator = By.name("j_password");
    By submit_button_locator = By.name("Submit");
    By manage_jenkins = By.xpath("//div[@id='tasks']/div[4]/a[@class='task-link']");
    By manage_users = By.xpath("//div[@id='main-panel']/div[16]/a[@title ='Manage Users']");
    By create_user = By.xpath("//div[@id='page-body']/div[@id='side-panel']/div[@id='tasks']/div[3]/a[@class='task-link']");
    By userName_in_createUser = By.xpath("//input[@name='username']");
    By userPassword_in_createUser = By.xpath("//input[@name='password1']");
    By userConfirmPassword_in_createUser = By.xpath("//input[@name='password2']");
    By userFullName_in_createUser = By.xpath("//input[@name='fullname']");
    By userEmail_in_createUser = By.xpath("//input[@name='email']");
    By submit_button_create = By.xpath("//span[@class='first-child']");
    By newUser = By.xpath("//tbody/tr[3]/td[2]/a");
    By deleteUser = By.xpath("//tbody/tr[3]/td[4]/a[2]");
    By confirmDeleteUser = By.xpath("//span[@class='first-child']");
    By findByHref = By.xpath("//a[@href='user/someuser/delete']");

    public PageObjectSelenium(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);
    }

    // Заполнение имени.
    public PageObjectSelenium setLogin(String name) {
        driver.findElement(username_locator).clear();
        driver.findElement(username_locator).sendKeys(name);
        return this;
    }

    // Заполнение пароля.
    public PageObjectSelenium setPassword(String password) {
        driver.findElement(userpassword_locator).clear();
        driver.findElement(userpassword_locator).sendKeys(password);
        return this;
    }

    // Заполнение Username.
    public PageObjectSelenium setUserNameInCreateUser(String name) {
        driver.findElement(userName_in_createUser).clear();
        driver.findElement(userName_in_createUser).sendKeys(name);
        return this;
    }

    // Заполнение User password.
    public PageObjectSelenium setUserPasswordInCreateUser(String name) {
        driver.findElement(userPassword_in_createUser).clear();
        driver.findElement(userPassword_in_createUser).sendKeys(name);
        return this;
    }

    // Заполнение User confirm password.
    public PageObjectSelenium setConfirmPasswordInCreateUser(String name) {
        driver.findElement(userConfirmPassword_in_createUser).clear();
        driver.findElement(userConfirmPassword_in_createUser).sendKeys(name);
        return this;
    }

    // Заполнение User full name.
    public PageObjectSelenium setUserFullNameInCreateUser(String name) {
        driver.findElement(userFullName_in_createUser).clear();
        driver.findElement(userFullName_in_createUser).sendKeys(name);
        return this;
    }

    // Заполнение User Email.
    public PageObjectSelenium setUserEmailInCreateUser(String name) {
        driver.findElement(userEmail_in_createUser).clear();
        driver.findElement(userEmail_in_createUser).sendKeys(name);
        return this;
    }

    // Получение значения имени.
    public String getUserLogin() {
        return driver.findElement(username_locator).getAttribute("value");
    }

    // Получение значения имени.
    public String getUserPassword() {
        return driver.findElement(userpassword_locator).getAttribute("value");
    }

    //Получение значения UserName
    public String getUserName() {
        return driver.findElement(userName_in_createUser).getAttribute("value");
    }

    //Получение значения Password
    public String getPassword() {
        return driver.findElement(userPassword_in_createUser).getAttribute("value");
    }

    //Получение значения ConfirmPassword
    public String getConfirmPassword() {
        return driver.findElement(userConfirmPassword_in_createUser).getAttribute("value");
    }

    //Получение значения FullName
    public String getFullName() {
        return driver.findElement(userFullName_in_createUser).getAttribute("value");
    }

    //Получение значения Email
    public String getEmail() {
        return driver.findElement(userEmail_in_createUser).getAttribute("value");
    }

    //Проверка таблицы на добавление нового пользователя
    public String getNewUser() {
        return driver.findElement(newUser).getAttribute("innerHTML");
    }

    //Проверка таблицы на ссылку удаления
    public String getNewUserDelete() {
        return driver.findElement(findByHref).getAttribute("href");
    }

    // Отправка данных из формы.
    public PageObjectSelenium submitForm() {
        driver.findElement(submit_button_locator).click();
        return this;
    }

    //Клик по ссылке Manage Jenkins
    public PageObjectSelenium submitManageJenkins() {
        driver.findElement(manage_jenkins).click();
        return this;
    }

    //Клик по ссылке Manage Users
    public PageObjectSelenium submitManageUsers() {
        driver.findElement(manage_users).click();
        return this;
    }

    //Клик по ссылке Create user
    public PageObjectSelenium submitCreateUser() {
        driver.findElement(create_user).click();
        return this;
    }

    //Клик по кнопке Create user
    public PageObjectSelenium submitButtonCreateUser() {
        driver.findElement(submit_button_create).click();
        return this;
    }

    //Клик по ссылке delete
    public PageObjectSelenium submitButtonDeleteUser() {
        driver.findElement(deleteUser).click();
        return this;
    }

    //Клик по ссылке Confirm to delete
    public PageObjectSelenium submitButtonConfirmToDelteUser() {
        driver.findElement(confirmDeleteUser).click();
        return this;
    }
}
