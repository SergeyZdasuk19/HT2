package wd;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

public class SampleTest_with_PageObjectSample {
	String base_url = "http://svyatoslav.biz";
	StringBuffer verificationErrors = new StringBuffer();
	WebDriver driver = null;

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


	@Test
	public void sampleTest() {
		// 1-действие: "Открыть http://svyatoslav.biz/testlab/wt/"
		driver.get(base_url + "/testlab/wt/");

		// С этого момента можно использовать PajeObject.
		PageObjectSample page = new PageObjectSample(driver);

		// 1-проверка: "Страница содержит форму с полями «Имя», «Рост», «Вес», радиокнопкой «Пол» и
		// кнопкой отправки данных «Рассчитать». Также на странице есть соответствующие текстовые надписи."
		Assert.assertTrue(page.isFormPresentForReal(), "No suitable forms found!");
        verificationErrors.append(page.getErrorOnTextAbsence("Имя"));
        verificationErrors.append(page.getErrorOnTextAbsence("Рост"));
        verificationErrors.append(page.getErrorOnTextAbsence("Вес"));
        verificationErrors.append(page.getErrorOnTextAbsence("Пол"));
        //verificationErrors.append(page.getErrorOnTextAbsence("Рассчитать"));

        // 2-действие: "В поле «Имя» ввести «username»."
		page.setName("username");

        // 2-проверка: "Значение появляется в поле."
		Assert.assertEquals(page.getName(), "username", "Unable to fill 'Имя' field");


        // 3-действие: "В поле «Рост» ввести «50»."
		page.setHeight("50");

        // 3-проверка: "Значение появляется в поле."
		Assert.assertEquals(page.getHeight(), "50", "Unable to fill 'Рост' field");


        // 4-действие: "В поле «Вес» ввести «3»."
		page.setWeight("3");

        // 4-проверка: "Значение появляется в поле."
		Assert.assertEquals(page.getWeight(), "3", "Unable to fill 'Вес' field");


        // 5-действие: "В радиокнопке «Пол» выбрать пол «М»."
		page.setGender("m");

        // 5-проверка: "Вариант «М» выбран."
		Assert.assertEquals(page.getGender(), "m", "Unable select 'М' gender");


        // 6-действие: "6. Нажать «Рассчитать»."
		page.submitForm();

        // 6-проверка: "6. Форма исчезает, в центральной ячейке таблицы появляется надпись «Слишком малая масса тела»."
		Assert.assertFalse(page.isFormPresentForReal(), "Form is on the page!");
		Assert.assertTrue(page.userMessageEquals("Слишком малая масса тела"), "Message 'Слишком малая масса тела' either is absent or is not in a proper place");

	}
}
