package wd;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collection;
import java.util.Iterator;

public class PageObjectSample {
    private WebDriverWait wait;
    private final WebDriver driver;
    // Подготовка элементов страницы.
    By body_locator = By.xpath("//body");
    By form_locator = By.xpath("//form[@action='/testlab/wt/index.php']");
    By username_locator = By.name("name");
    By weight_locator = By.name("weight");
    By height_locator = By.name("height");
    By gender_m_locator = By.xpath("//input[@name='gender'][@value='m']");
    By gender_f_locator = By.xpath("//input[@name='gender'][@value='f']");
    By submit_button_locator = By.xpath("//input[@type='submit']");
    By user_message_locator = By.xpath("//table/tbody/tr[2]/td[2]");
    By error_message_locator = By.xpath("//form/table/tbody/tr/td");


    public PageObjectSample(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Расчёт веса")) ||
                (!driver.getCurrentUrl().equals("http://svyatoslav.biz/testlab/wt/"))) {
            throw new IllegalStateException("Wrong site page!");
        }
    }

    // Заполнение имени.
    public PageObjectSample setName(String name) {
        driver.findElement(username_locator).clear();
        driver.findElement(username_locator).sendKeys(name);
        return this;
    }

    // Заполнение веса.
    public PageObjectSample setWeight(String weight) {
        driver.findElement(weight_locator).clear();
        driver.findElement(weight_locator).sendKeys(weight);
        return this;
    }

    // Заполнение роста.
    public PageObjectSample setHeight(String height) {
        driver.findElement(height_locator).clear();
        driver.findElement(height_locator).sendKeys(height);
        return this;
    }

    // Указание пола.
    public PageObjectSample setGender(String gender) {
        if (gender.equals("m")) {
            driver.findElement(gender_m_locator).click();
        } else {
            driver.findElement(gender_f_locator).click();
        }
        return this;
    }

    // Заполнение всех полей формы.
    public PageObjectSample setFields(String name, String weight, String height, String gender) {
        setName(name);
        setWeight(weight);
        setHeight(height);
        setGender(gender);
        return this;
    }


    // Отправка данных из формы.
    public PageObjectSample submitForm() {
        driver.findElement(submit_button_locator).click();
        return this;
    }

    // Обёртка для упрощения отправки данных.
    public PageObjectSample submitFilledForm(String name, String weight, String height, String gender) {
        setFields(name, weight, height, gender);
        return submitForm();
    }

    // Упрощённый поиск формы.
    public boolean isFormPresent() {
        if (driver.findElement(form_locator) != null) {
            return true;
        } else {
            return false;
        }
    }

    // Надёжный поиск формы.
    public boolean isFormPresentForReal() {
        // Первое (самое правильное) решение (работает примерно в 30-50% случаев)
        // wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//html/body"), 1));

        // Второе (самое интересное) решение (работает примерно в 20-30% случаев; не работает в 3.3.1)
        // waitForLoad(driver);

        // Третье (самое убогое, почти за гранью запрещённого) решение -- работает в 100% случаев

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Collection<WebElement> forms = driver.findElements(By.tagName("form"));
        if (forms.isEmpty()) {
            return false;
        }

        Iterator<WebElement> i = forms.iterator();
        boolean form_found = false;
        WebElement form = null;

        while (i.hasNext()) {
            form = i.next();
            if ((form.findElement(By.name("name")).getAttribute("type").equalsIgnoreCase("text")) &&
                    (form.findElement(By.name("height")).getAttribute("type").equalsIgnoreCase("text")) &&
                    (form.findElement(By.name("weight")).getAttribute("type").equalsIgnoreCase("text")) &&
                    (form.findElement(By.xpath("//input[@type=\"submit\"]")).getAttribute("value").equalsIgnoreCase("Рассчитать")) &&
                    (form.findElements(By.name("gender")).size() == 2) &&
                    (form.findElements(By.xpath("//input")).size() == 6)) {
                form_found = true;
                break;
            }
        }

        return form_found;
    }

    // Проверка вхождения подстроки в текст страницы.
    public boolean pageTextContains(String search_string) {
        return driver.findElement(body_locator).getText().contains(search_string);
    }

    // Проверка вхождения подстроки в пользовательское сообщение.
    public boolean userMessageContains(String search_string) {
        return driver.findElement(user_message_locator).getText().contains(search_string);
    }

    // Проверка равенства пользовательского сообщения строке.
    public boolean userMessageEquals(String search_string) {
        return driver.findElement(user_message_locator).getText().equals(search_string);
    }

    // Проверка вхождения подстроки в сообщение об ошибке.
    public boolean errorMessageContains(String search_string) {
        return driver.findElement(error_message_locator).getText().contains(search_string);
    }

    // Проверка равенства сообщения об ошибке строке.
    public boolean errorMessageEquals(String search_string) {
        return driver.findElement(error_message_locator).getText().equals(search_string);
    }

    // Получение значения имени.
    public String getName() {
        return driver.findElement(username_locator).getAttribute("value");
    }

    // Получение значения веса.
    public String getWeight() {
        return driver.findElement(weight_locator).getAttribute("value");
    }

    // Получение значения роста.
    public String getHeight() {
        return driver.findElement(height_locator).getAttribute("value");
    }

    // Получение значения пола.
    public String getGender() {
        if (driver.findElement(gender_m_locator).isSelected()) {
            return "m";
        } else if (driver.findElement(gender_f_locator).isSelected()) {
            return "f";
        } else {
            return "";
        }
    }

    public String getErrorOnTextAbsence(String search_string) {
        if (!pageTextContains(search_string)) {
            return "No '" + search_string + "' is found inside page text!\n";
        } else {
            return "";
        }
    }

    /* Не работает в 3.3.1!*/
    /*
    void waitForLoad(WebDriver driver) {

    	Predicate<WebDriver> pageLoaded = new Predicate<WebDriver>() {

    		@Override
    		public boolean apply(WebDriver input) {
    			return ((JavascriptExecutor) input).executeScript("return document.readyState").equals("complete");
    		}

    	};
    	new FluentWait<WebDriver>(driver).until(pageLoaded);
   	}
   	*/

}




