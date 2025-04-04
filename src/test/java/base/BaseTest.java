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
        //System.out.println("✅ [BeforeSuite] RestAssured.baseURI = " + RestAssured.baseURI);
        // Load base URI from properties
        RestAssured.baseURI = ConfigReader.getProperty("base.url");

        // Configure global timeout
        RestAssured.config = RestAssuredConfig.config().httpClient(
                HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 5000)
                        .setParam("http.socket.timeout", 5000)
                        .setParam("http.connection-manager.timeout", 5000)
        );

        System.out.println("✅ Framework Initialized with Base URI: " + RestAssured.baseURI);

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/ExtentReport.html");
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Framework", "RestAssured with TestNG");

        System.out.println("✅ Extent Report Initialized");
    }

    @AfterSuite
    public void tearDownSuite() {
        if (extent != null) {
            extent.flush();
            System.out.println("✅ Extent Report Flushed");
        }
    }
}
