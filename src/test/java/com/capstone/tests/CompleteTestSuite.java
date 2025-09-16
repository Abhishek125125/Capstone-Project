package com.capstone.tests;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.Status;

public class CompleteTestSuite {

    private ExtentReports extent;
    private ExtentTest test;

    private String baseUrl = "https://thinking-tester-contact-list.herokuapp.com";
    private String token;
    private String contactId;
    private String uniqueEmail;
    private String updatedEmail;
    private String updatedPassword;


    @BeforeSuite
    public void setupSuite() {
        extent = ExtentManager.getInstance();
    }

    @AfterSuite
    public void tearDownSuite() {
        extent.flush();
        System.out.println("Extent report generated at target/ExtentReport.html");
    }

    @Test(priority = 1)
    public void addUser() {
        test = extent.createTest("Step 1: Add New User");
        uniqueEmail = "user" + System.currentTimeMillis() + "@fake.com";
        String requestBody = "{\"firstName\":\"Test\",\"lastName\":\"User\",\"email\":\"" + uniqueEmail + "\",\"password\":\"myPassword\"}";

        Response res = given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract().response();

        token = res.jsonPath().getString("token");
       
        System.out.println("Step 1: User added.");
        System.out.println("Request: " + requestBody);
        System.out.println("Response: " + res.asString());
        System.out.println("Status code: " + res.getStatusCode());

        test.log(Status.INFO, "Request: " + requestBody);
        test.log(Status.INFO, "Response: " + res.asString());
        test.log(Status.INFO, "Status code: " + res.getStatusCode());
    }

    @Test(priority = 2)
    public void getUserProfile() {
        test = extent.createTest("Step 2: Get User Profile");

        Response res = given()
                .baseUri(baseUrl)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/users/me")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Step 2: User profile fetched.");
        System.out.println("Response: " + res.asString());

        test.log(Status.INFO, "Response: " + res.asString());
        test.log(Status.INFO, "Status code: " + res.getStatusCode());
    }

    @Test(priority = 3)
    public void updateUser() {
        test = extent.createTest("Step 3: Update User");

        updatedEmail = "updated" + System.currentTimeMillis() + "@fake.com";
        updatedPassword = "myNewPassword";

        String requestBody = "{\"firstName\":\"Updated\",\"lastName\":\"Username\",\"email\":\"" + updatedEmail + "\",\"password\":\"" + updatedPassword + "\"}";

        Response res = given()
                .baseUri(baseUrl)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .patch("/users/me")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Step 3: User updated.");
        System.out.println("Request: " + requestBody);
        System.out.println("Response: " + res.asString());

        test.log(Status.INFO, "Request: " + requestBody);
        test.log(Status.INFO, "Response: " + res.asString());
        test.log(Status.INFO, "Status code: " + res.getStatusCode());
    }

    @Test(priority = 4)
    public void loginUser() {
        test = extent.createTest("Step 4: Login User");

        String requestBody = "{\"email\":\"" + updatedEmail + "\",\"password\":\"" + updatedPassword + "\"}";

        Response res = given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users/login")
                .then()
                .statusCode(200)
                .extract().response();

        token = res.jsonPath().getString("token");

        System.out.println("Step 4: User logged in.");
        System.out.println("Request: " + requestBody);
        System.out.println("Response: " + res.asString());

        test.log(Status.INFO, "Request: " + requestBody);
        test.log(Status.INFO, "Response: " + res.asString());
        test.log(Status.INFO, "Status code: " + res.getStatusCode());
    }

    @Test(priority = 5)
    public void addContact() {
        test = extent.createTest("Step 5: Add Contact");

        String requestBody = "{\n" +
                "\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthdate\":\"1970-01-01\"," +
                "\"email\":\"jdoe@fake.com\",\"phone\":\"8005555555\",\"street1\":\"1 Main St.\"," +
                "\"street2\":\"Apartment A\",\"city\":\"Anytown\",\"stateProvince\":\"KS\"," +
                "\"postalCode\":\"12345\",\"country\":\"USA\"}";

        Response res = given()
                .baseUri(baseUrl)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/contacts")
                .then()
                .statusCode(201)
                .extract().response();

        contactId = res.jsonPath().getString("_id");

        System.out.println("Step 5: Contact added.");
        System.out.println("Request: " + requestBody);
        System.out.println("Response: " + res.asString());

        test.log(Status.INFO, "Request: " + requestBody);
        test.log(Status.INFO, "Response: " + res.asString());
        test.log(Status.INFO, "Status code: " + res.getStatusCode());
    }

    @Test(priority = 6)
    public void getContactList() {
        test = extent.createTest("Step 6: Get Contact List");

        Response res = given()
                .baseUri(baseUrl)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/contacts")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Step 6: Contact list fetched.");
        System.out.println("Response: " + res.asString());

        test.log(Status.INFO, "Response: " + res.asString());
    }

    @Test(priority = 7)
    public void getSingleContact() {
        test = extent.createTest("Step 7: Get Single Contact");

        Response res = given()
                .baseUri(baseUrl)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/contacts/" + contactId)
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Step 7: Single contact fetched.");
        System.out.println("Response: " + res.asString());

        test.log(Status.INFO, "Response: " + res.asString());
    }

    @Test(priority = 8)
    public void updateContactPUT() {
        test = extent.createTest("Step 8: Contact updated (PUT)");

        String requestBody = "{\n" +
                "\"firstName\":\"Amy\",\"lastName\":\"Miller\",\"birthdate\":\"1992-02-02\"," +
                "\"email\":\"amiller@fake.com\",\"phone\":\"8005554242\",\"street1\":\"13 School St.\"," +
                "\"street2\":\"Apt. 5\",\"city\":\"Washington\",\"stateProvince\":\"QC\"," +
                "\"postalCode\":\"A1A1A1\",\"country\":\"Canada\"}";

        Response res = given()
                .baseUri(baseUrl)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put("/contacts/" + contactId)
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Step 8: Contact updated (PUT).");
        System.out.println("Request: " + requestBody);
        System.out.println("Response: " + res.asString());

        test.log(Status.INFO, "Request: " + requestBody);
        test.log(Status.INFO, "Response: " + res.asString());
    }

    @Test(priority = 9)
    public void updateContactPATCH() {
        test = extent.createTest("Step 9: Contact partially updated (PATCH)");

        String requestBody = "{\"firstName\":\"Anna\"}";

        Response res = given()
                .baseUri(baseUrl)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .patch("/contacts/" + contactId)
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Step 9: Contact partially updated (PATCH).");
        System.out.println("Request: " + requestBody);
        System.out.println("Response: " + res.asString());

        test.log(Status.INFO, "Request: " + requestBody);
        test.log(Status.INFO, "Response: " + res.asString());
    }

    @Test(priority = 10)
    public void logoutUser() {
        test = extent.createTest("Step 10: Logout User");

        Response res = given()
                .baseUri(baseUrl)
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/users/logout")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Step 10: User logged out.");
        System.out.println("Response: " + res.asString());

        test.log(Status.INFO, "Response: " + res.asString());
    }
}
