package base;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.testng.annotations.*;
import utils.ConfigReader;

public class BaseTest {

    public static ExtentTest test;
    public static ExtentReports extent=new ExtentReports();
    @BeforeSuite
    public void setUpSuite() {

        // Create a test entry for the setup process
        test = extent.createTest("Framework Initialization");


        RestAssured.baseURI = ConfigReader.getProperty("base.url");
        test.info("Base URI set to: " + RestAssured.baseURI);

        // Configure global timeout
        RestAssured.config = RestAssuredConfig.config().httpClient(
                HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 5000)
                        .setParam("http.socket.timeout", 5000)
                        .setParam("http.connection-manager.timeout", 5000)
        );
        test.info("Timeouts configured: connection, socket, and connection-manager set to 5000ms");

       // System.out.println(" Framework Initialized with Base URI: " + RestAssured.baseURI);

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("Extend_Report/ExtentReport.html");
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Framework", "RestAssured with TestNG");

        test.pass("✅ Framework Initialized Successfully");
    }

    @AfterSuite
    public void tearDownSuite() {
        if (extent != null) {
            extent.flush();
            test.info("Flushing Extent Report...");
        }
    }
}
