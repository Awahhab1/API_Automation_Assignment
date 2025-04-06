# API Automation Framework – Rest Assured + TestNG + Extend Report

This is a modular and scalable API automation framework built using **Rest Assured**, **TestNG**, **Extent Reports**, and reusable utility components. It is designed to automate API testing for RESTful services, support dynamic data, and provide structured test execution reports.



## 🚀 Technologies Used

### ✅ TestNG
- **Purpose**: Acts as the test runner and framework for organizing and executing test cases.
- **Benefits**:
    - Supports annotations like `@BeforeSuite`,`@BeforeClass`, `@Test`, etc.
    - XML-based suite execution.
    - Parallel and grouped test execution support.

### ✅ Rest Assured
- **Purpose**: Simplifies sending HTTP requests and validating REST API responses.
- **Benefits**:
    - Fluent and readable syntax for requests and assertions.
    - Easy integration with TestNG for end-to-end testing.

### ✅ Extent Reports
- **Purpose**: Generates detailed and visually rich test execution reports.
- **Benefits**:
    - Real-time logging and status updates.
    - HTML reports with test steps, status (pass/fail/skip), and timestamps.
    - Easy to customize and share with stakeholders.

### ✅ Utility Classes
- **Purpose**: Handle reusable functions like data generation, payload building, or config management.
- **Benefits**:
    - Avoid code duplication.
    - Centralize logic for cleaner test scripts.
    - Easier maintenance and updates.

### ✅ Properties File
- **Purpose**: Stores configurable values like base URI, environment-specific endpoints, and credentials.
- **Benefits**:
    - Easily switch environments.
    - Centralized configuration management.
    - Avoids hardcoding sensitive or variable data.

---

## 🗂 Project Structure


# API Automation Assignment Project Structure

```
**API_Automation_Assignment/
- .idea/                          # IDE configuration files (IntelliJ)
- Extend_Report/
  - ExtentReport.html             # Test execution report
- src/
  - main/
    - java/                       # Main Java source code
  - test/
    - java/
      - base/
        - BaseTest.java           # Base test class
      - tests/
        - OurPartnersAPiTest.java # API test cases
      - utils/
        - ConfigReader.java       # Configuration utility
        - RetryAnalyzer.java       # Test retry logic
        - TestDataUtil.java        # Test data generator
  - resources/
    - config.properties           # Configuration settings
    - testing.xml                 # Test configuration
- target/                        # Compiled classes and build output
- .gitignore                     # Specifies untracked files to ignore
- pom.xml                        # Maven project configuration
- README.md                      # Project documentation**
```

### Key Components
- **`src/test/java/base/`**: Contains the base test class for common setup/teardown.
- **`src/test/java/tests/`**: Holds API test implementations (e.g., `OurPartnersAPiTest`).
- **`src/test/java/utils/`**: Utilities for configuration, retry logic, and test data.
- **`Extend_Report/`**: Generated HTML report for test results.

### Setup Instructions
1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   ```
2. **Build the project** (Maven):
   ```bash
   mvn clean install
   ```
3. **Run tests**:
   ```bash
   mvn test
   ```

## 🛠️ Setup Instructions

### Prerequisites

- Java 11+
- Maven 3.6+
- IntelliJ IDEA
- Internet access to download dependencies

### Steps

1. **Clone the Repository**
   
   git clone https://your-repo-url.git
   cd your-project

2. **Import in IntelliJ**

* Open IntelliJ.

* Select "Open" and choose the root project folder.

* IntelliJ will auto-detect the Maven project and import dependencies.

3. **Update Properties**

Open config/config.properties and set:

ini
Copy
Edit
base.uri=https://ffw.futurework.com.sa/premium
report.path=./reports
4. **Run Tests**

Right-click testng.xml → Run

OR use terminal:


mvn clean test

 

## Execution Flow 
1.TestNG kicks off test execution.

2.Tests use Rest Assured to send HTTP requests.

3.Utility classes generate random emails/phones for unique test data.

4.Responses are validated with assertions.

5.Extent Reports logs each test and generates an HTML report.

## ♻️ Extensibility
Add New API Endpoint Test:
Create a new class in tests/.

Write your test using:
given().body(payload).post("/endpoint").then().statusCode(200);

Add a New Test Case:
Use @Test methods inside any test class.
Use Test NG Assert to validate the API

Add the class in the testng.xml or some specific function that need to be execute
in the test suite



