package StepDefinition;

//import java.awt.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class TheSouledStore {

	static ReportGenerator reportGenerator = new ReportGenerator();
	public WebDriver driver;
	ExtentTest test;

	@Given("^Driver Initialisation")
	public void openbrowser() {

		System.setProperty("webdriver.chrome.driver",
				"C://Users//IBM_ADMIN//Downloads//Selenium//chromedriver.exe");

		driver = new ChromeDriver();

	}

	@Given("I navigate to the Application URL")
	public void openURL() throws Exception {
		try {
			driver.get("https://www.thesouledstore.com/");
			driver.manage().window().maximize();
			String currentURL = driver.getCurrentUrl();

			SoftAssertions softAssertions = new SoftAssertions();
			Assert.assertEquals(currentURL, "https://www.thesouledstore.com/");
			softAssertions.assertAll();

			reportGenerator.createReport();

			reportGenerator.reportStatus(Status.PASS,
					"I navigate to the Application URL", capture(driver));

		} catch (Exception e) {

			reportGenerator.reportStatus(Status.FAIL,
					"I navigate to the Application URL", capture(driver));

			e.printStackTrace();
			throw e;

		}
		driver.close();

	}

	@Then("Finding the mobile CoverURLs")
	public void fillIndetails() throws InterruptedException, IOException {
		try {

			driver.findElement(
					By.xpath("/html/body/div[1]/div[1]/nav/div[2]/ul/li[1]/a"))
					.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(
					By.xpath("/html/body/div[1]/div[1]/nav/div[2]/ul/li[1]/div/div/div/div/div[1]/div/div[1]/ul/li[3]/a"))
					.click();

			Thread.sleep(8000);
			driver.findElement(By.xpath("//a[contains(text(),'OnePlus 6T')]"))
					.click();

			Thread.sleep(7000);
			// ---Finding number of pages
			countNoOfpages();

			List<String> urlList = new ArrayList<String>();

			List<WebElement> urlGOTList = driver.findElements(By.tagName("a"));

			for (int i = 0; i < urlGOTList.size(); i++) {

				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				String url = urlGOTList.get(i).getAttribute("href");
				if (url.contains("got")) {

					urlList.add(url);
				}

			}
			// System.out.println(urlList);
			excelwriter(urlList);
			/*
			 * reportGenerator.reportStatus(Status.PASS,
			 * "Finding the mobile CoverURLs");
			 */
		} catch (Exception e) {
			/*
			 * reportGenerator.reportStatus(Status.FAIL,
			 * "Finding the mobile CoverURLs");
			 */
			e.printStackTrace();
			throw e;

			// TODO: handle exception
		}
	}

	// Writing the element to excel

	public static void excelwriter(List<String> urlList) throws IOException {

		System.out.println(urlList);
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet("GOTMobCover Link");

		XSSFRow rowheader = spreadsheet.createRow(0);
		Cell cell1 = rowheader.createCell(0);
		Cell cell2 = rowheader.createCell(1);
		cell1.setCellValue("Serial no");
		cell2.setCellValue("Urls for GOT");

		XSSFRow row;
		for (int j = 1; j < (urlList.size() + 1); j++) {
			row = spreadsheet.createRow(j);

			Cell cellserialno = row.createCell(0);
			cellserialno.setCellValue(((j)));

			Cell cell = row.createCell(1);
			cell.setCellValue((urlList.get((j - 1))));
		} // Write the workbook in file system
		FileOutputStream out = new FileOutputStream(new File(
				"C:/SeleniumTesting/URLsExcel/GOtUrls.xlsx"));
		workbook.write(out);
		out.close();

	}

	public int countNoOfpages() {
		int noOfPages = 0;
		try {

			for (int i = 2; i < 10; i++) {// given i=2 because the page buton
											// Xpath starts with 2
				WebElement elem = driver
						.findElement(By
								.xpath("//*[@id='app']/div[2]/div/div/div[1]/div/div/div/div/div[2]/div/div/div/div/ul/li["
										+ i + "]/span"));

				noOfPages++;

			}
		} catch (Exception e) {

		}
		System.out.println(noOfPages);
		return noOfPages++;
	}

	@After
	public static void after() {
		reportGenerator.endtest();
		// System.out.println("test flushed");

	}

	// test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+
	// "Test Failed");
	public String capture(WebDriver driver) throws IOException {
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		File Dest = new File("/screenshot/snap" + ".png");
		String errflpath = Dest.getAbsolutePath();
		FileUtils.copyFile(scrFile, Dest);
		return errflpath;
	}

}

// /create report,screenshot for all the mobile covers,Url should be beside the

