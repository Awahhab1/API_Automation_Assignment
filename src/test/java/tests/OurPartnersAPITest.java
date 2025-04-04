package tests;

import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.*;

import java.util.Random;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.RetryAnalyzer;
import utils.TestDataUtil;

import static io.restassured.RestAssured.given;

public class OurPartnersAPITest extends BaseTest {

    private String email;
    private String phoneNumber;
    private String name;
    private String companyName;
    private String strategicGoals;
    private String invalidEmail;
    private String invalidPhone;
    private String default_email_id;
    private String default_special_name;
    private String alphanum_phone;
    private String inconsistent_phone_format;
    private static ExtentReports report;
    //private static ExtentTest test;
   // static ExtentReports extent=new ExtentReports();

    @BeforeClass
    public void setup() {



        name = ConfigReader.getProperty("default.name");
        companyName = ConfigReader.getProperty("default.company.name");
        strategicGoals = ConfigReader.getProperty("default.strategic.goals");
        invalidEmail= ConfigReader.getProperty("invalid.email.format");
        invalidPhone= ConfigReader.getProperty("invalid.phone.format");
        default_email_id= ConfigReader.getProperty("default.email.id");
        default_special_name=ConfigReader.getProperty("default.special.name");
        alphanum_phone=ConfigReader.getProperty("default.alphanum.phone");
        inconsistent_phone_format=ConfigReader.getProperty("default.inconsistent.phone");

    }

    @BeforeMethod
    public void generateEmailPhone() {
        email = TestDataUtil.generateRandomEmail();
        phoneNumber = TestDataUtil.generateRandomPhoneNumber();

    }


    @Test
    public void testValidRequest() {

        test = extent.createTest("Request with Valid credentials ","OurPartners API Test with Valid Request ");
        test.info("Sending valid request to create a new partner");
        // Define the payload with the unique email and phone number
        String payload = "{\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"phoneNumber\": \"" + phoneNumber + "\",\n" +
                "\"companyName\": \"" + companyName + "\",\n" +
                "\"strategicGoals\": \"" + strategicGoals + "\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .log().all() // Log the request
                .when()
                .post("/our-partners")
                .then()
                .log().all()  // Log the response
                .statusCode(200);  // Validate status code for successful creation

        test.pass("Valid request sent successfully and response is as expected.");

    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testMissingPhoneField() {
        test = extent.createTest("Missing phone Field", "Missing phone in payload");


        String payload = "{\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"email\": \"" + email + "\",\n" +

                "\"companyName\": \"" + companyName + "\",\n" +
                "\"strategicGoals\": \"" + strategicGoals + "\"\n" +
                "}";

        // Send the POST request and capture the response
        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .log().all()
                .when()
                .post("/our-partners");

        // Log the response
        response.then().log().all();

        // Validate the response status code and message using TestNG Assert
        Assert.assertEquals(response.getStatusCode(), 400, "Status code mismatch, expected 400.");
        Assert.assertEquals(response.jsonPath().getString("message"), "phone number can not be blank", "Error message doesn't match expected value.");
        test.pass("Error correctly returned for missing phone");

    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testMissingEmailField() {

        test = extent.createTest("Missing Email Field", "Missing email in payload");


        String payload = "{\n" +
                "\"name\": \"" + name + "\",\n" +

                "\"phoneNumber\": \"" + phoneNumber + "\",\n" +
                "\"companyName\": \"" + companyName + "\",\n" +
                "\"strategicGoals\": \"" + strategicGoals + "\"\n" +
                "}";

        // Send the POST request and capture the response
        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .log().all()
                .when()
                .post("/our-partners");

        // Log the response
        response.then().log().all();

        // Validate the response status code and message using TestNG Assert
        Assert.assertEquals(response.getStatusCode(), 400, "Status code mismatch, expected 400.");
        Assert.assertEquals(response.jsonPath().getString("message"), "email can not be blank", "Error message doesn't match expected value.");
        test.pass("Error correctly returned for missing email");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testInvalidEmailFormat() {
        test = extent.createTest("Invalid Email Format", "Using badly formatted email");
        String payload = "{\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"email\": \"" + invalidEmail + "\",\n" +
                "\"phoneNumber\": \"" + phoneNumber + "\",\n" +
                "\"companyName\": \"" + companyName + "\",\n" +
                "\"strategicGoals\": \"" + strategicGoals + "\"\n" +
                "}";

        // Send the POST request and capture the response
        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .log().all()
                .when()
                .post("/our-partners");

        // Log the response
        response.then().log().all();

        // Validate the response status code and message using TestNG Assert
        Assert.assertEquals(response.getStatusCode(), 400, "Status code mismatch, expected 400.");
        Assert.assertEquals(response.jsonPath().getString("message"), "email format is not correct", "Error message doesn't match expected value.");
        test.pass("Correct error shown for invalid email format");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testInvalidPhoneFormat() {
        test = extent.createTest("Invalid Phone Format", "Using invalid phone number");
        String payload = "{\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"phoneNumber\": \"" + invalidPhone + "\",\n" +
                "\"companyName\": \"" + companyName + "\",\n" +
                "\"strategicGoals\": \"" + strategicGoals + "\"\n" +
                "}";

        // Send the POST request and capture the response
        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .log().all()
                .when()
                .post("/our-partners");

        // Log the response
        response.then().log().all();

        // Validate the response status code and message using TestNG Assert
        Assert.assertEquals(response.getStatusCode(), 400, "Status code mismatch, expected 400.");
       // Assert.assertEquals(response.jsonPath().getString("message"), "phone number is not correct", "Error message doesn't match expected value.");
        test.pass("Correct error returned for invalid phone number");
    }

    @Test
    public void testDuplicateEmail() {
        test = extent.createTest("Duplicate Email", "Email already exists scenario");
        String payload = "{\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"email\": \"" + default_email_id + "\",\n" +
                "\"phoneNumber\": \"" + phoneNumber + "\",\n" +
                "\"companyName\": \"" + companyName + "\",\n" +
                "\"strategicGoals\": \"" + strategicGoals + "\"\n" +
                "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .log().all()
                .when()
                .post("/our-partners");

        // Log the response
        response.then().log().all();

        // Validate the response status code and message using TestNG Assert
        Assert.assertEquals(response.jsonPath().getString("message"), "email_already_exists", "Error message doesn't match expected value.");
        test.pass("Proper message returned for duplicate email");
    }

    @Test
    public void testResponseTime() {
        test = extent.createTest("Response Time Test", "Verifying response time under 5 seconds");
        String payload = "{\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"phoneNumber\": \"" + phoneNumber + "\",\n" +
                "\"companyName\": \"" + companyName + "\",\n" +
                "\"strategicGoals\": \"" + strategicGoals + "\"\n" +
                "}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .log().all()
                .when()
                .post("/our-partners")
                .then()
                .log().all()
                .time(lessThan(5000L));  // Ensure response time is under 5000 ms
        test.pass("Response time is within 5 seconds");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testInvalidEndpoint() {
        test = extent.createTest("Invalid Endpoint", "Hitting a non-existing endpoint");
        String payload = "{\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"phoneNumber\": \"" + phoneNumber + "\",\n" +
                "\"companyName\": \"" + companyName + "\",\n" +
                "\"strategicGoals\": \"" + strategicGoals + "\"\n" +
                "}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .log().all()
                .when()
                .post("/invalid-endpoint")  // Incorrect endpoint
                .then()
                .log().all()
                .statusCode(403);
        test.pass("Correctly handled invalid endpoint");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testSpecialCharactersInFields() {

        test = extent.createTest("Special Characters in Name", "Handling special characters in name field");
        String payload = "{\n" +

                "\"name\": \"" + default_special_name + "\",\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"phoneNumber\": \"" + phoneNumber + "\",\n" +
                "\"companyName\": \"" + companyName + "\",\n" +
                "\"strategicGoals\": \"" + strategicGoals + "\"\n" +
                "}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .log().all()
                .when()
                .post("/our-partners")
                .then()
                .log().all()
                .statusCode(200); // Should succeed as API can handle special characters
        test.pass("Special characters handled successfully");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testPhoneNumberWithNonNumericCharacters() {
        test = extent.createTest("Alphanumeric Phone", "Testing phone number with letters");
        String payload = "{\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"phoneNumber\": \"" + alphanum_phone + "\",\n" +
                "\"companyName\": \"" + companyName + "\",\n" +
                "\"strategicGoals\": \"" + strategicGoals + "\"\n" +
                "}";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .log().all()
                .when()
                .post("/our-partners");

        response.then().log().all(); // Log full response

        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);
        Assert.assertEquals(response.getStatusCode(), 400, "Status code mismatch, expected 400.");
        test.pass("Error correctly returned for alphanumeric phone");

    }
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testPhoneNumberFormatInconsistency() {
        test = extent.createTest("Inconsistent Phone Format", "Phone number with incorrect format");
        String payload = "{\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"phoneNumber\": \"" +inconsistent_phone_format+ "\",\n" +
                "\"companyName\": \"" + companyName + "\",\n" +
                "\"strategicGoals\": \"" + strategicGoals + "\"\n" +
                "}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .log().all()
                .when()
                .post("/our-partners")
                .then()
                .log().all()
                .statusCode(400)  // Expecting Bad Request due to phone number format
                .body("message", equalTo("phone number is not correct"));
        test.pass("Phone format inconsistency handled correctly");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testQueryParametersWithUnnecessaryData() {
        test = extent.createTest("Unnecessary Query Param", "Testing irrelevant query params");

        String payload = "{\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"phoneNumber\": \"" + phoneNumber + "\",\n" +
                "\"companyName\": \"" + companyName + "\",\n" +
                "\"strategicGoals\": \"" + strategicGoals + "\"\n" +
                "}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .log().all()
                .queryParam("extra", "irrelevant")  // Unnecessary query parameter
                .when()
                .post("/our-partners")
                .then()
                .log().all()
                .statusCode(200);  // Should be successful; API should ignore irrelevant query parameters
        test.pass("Handled unnecessary query params without issues");
    }











}
