package StepDefinition;

import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ReportGenerator {
	ExtentHtmlReporter htmlReporter;
	ExtentReports report;
	ExtentTest test;

	public void createReport() throws IOException {

		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")
				+ "/test-output/testReport.html");

		// to change look & feel of the report
		htmlReporter.config().setDocumentTitle("Extent Report Demo");
		htmlReporter.config().setReportName("Test Report");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setTimeStampFormat(
				"EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		// htmlReporter.config().setAutoCreateRelativePathMedia(true);

		report = new ExtentReports();
		report.attachReporter(htmlReporter);
		test = report.createTest("Prathiba");

	}

	public void endtest() {
		try {
			report.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void reportStatus(Status status, String ScenarioStep,
			String errflpath) throws IOException {
		/*
		 * String imageString = "<img  src= " + errflpath + ">"; // String
		 * im="img"
		 */
		test.log(status, ScenarioStep, MediaEntityBuilder
				.createScreenCaptureFromPath(errflpath).build());

		// Gives bold label in the report

		/*
		 * String testCaseName = "TestCaseName"; Markup m =
		 * MarkupHelper.createLabel(testCaseName, ExtentColor.BLUE);
		 * test.pass(m);
		 */

		String url = "https://www.thesouledstore.com/";

		String[][] data = { { "TestCaseName", "Status", "URL", },
				{ ScenarioStep, "PASSED", url, } };

		Markup n = MarkupHelper.createTable(data);

		test.pass(n);

	}
}
