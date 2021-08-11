package com.sdet.automationFramework.stepDef.RestServiceTesting;

import com.jcraft.jsch.JSchException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestServiceStepDef {

    Response response = null;
    String accessToken;

    public RestServiceStepDef() throws JSchException {

    }

    @Given("Call get access token api endpoint with status code {string}")
    public void call_get_access_token_api_endpoint(String statusCode) {
        //RestAssured.baseURI = "http://rest-api.upskills.in/api/rest_admin/oauth2/token/client_credentials";
        System.out.println("Started Token service");
        response = given()
                .header("content-type", "application/json")
                .header("Authorization",
                        "Basic dXBza2lsbHNfcmVzdF9hZG1pbl9vYXV0aF9jbGllbnQ6dXBza2lsbHNfcmVzdF9hZG1pbl9vYXV0aF9zZWNyZXQ=")
                .header("grant_type", "client_credentials")
                .when()
                .post("http://rest-api.upskills.in/api/rest_admin/oauth2/token/client_credentials")
                .then()
                .assertThat().statusCode(Integer.parseInt(statusCode)).and()
                .contentType(ContentType.JSON)
                .extract().response();
        System.out.println("Token generated successfully");

    }

    @Then("Get the access token")
    public void getAccessToken() {
        String jsonResponse = response.toString();
        JsonPath responseBody = new JsonPath(jsonResponse);
        accessToken = responseBody.get("access_token");
        System.out.println("Access Token" + accessToken);
    }

    @And("Call login admin user api endpoint provided with valid admin user request body with status code {string}")
    public void call_login_admin_user_api_endpoint(String statusCode) {
        System.out.println("Started Login service");
        response = given()
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .body("{\n" +
                        "  \"username\": \"upskills_admin\",\n" +
                        "  \"password\": \"Talent4$$\"\n" +
                        "}")
                .when()
                .post("http://rest-api.upskills.in/api/rest_admin/login")
                .then()
                .assertThat().statusCode(Integer.parseInt(statusCode)).and()
                .contentType(ContentType.JSON)
                .extract().response();
        System.out.println("Logged in with admin credentials");
    }

    @And("Call get admin user account details api endpoint with status code {string}")
    public void call_get_admin_user_account_details_api_endpoint(String statusCode) {
        System.out.println("Started User service");
        response = given()
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("http://rest-api.upskills.in/api/rest_admin/user")
                .then()
                .assertThat().statusCode(Integer.parseInt(statusCode)).and()
                .contentType(ContentType.JSON)
                .extract().response();
        System.out.println("Got the user account details");
    }

    @And("Call logout admin user api endpoint with status code {string}")
    public void call_logout_admin_user_api_endpoint(String statusCode) {
        System.out.println("Started Logout service");
        response = given()
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("http://rest-api.upskills.in/api/rest_admin/logout")
                .then()
                .assertThat().statusCode(Integer.parseInt(statusCode)).and()
                .contentType(ContentType.JSON)
                .extract().response();
        System.out.println("Logged out using service");
        System.out.println("Got token, logged in, read users and logged out using REST ASSURED services");
    }

}

