package com.sdet.automationFramework.services.SampleService.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sdet.automationFramework.services.SampleService.model.request.Request;
import com.sdet.automationFramework.services.SampleService.model.response.Response;

import static com.jayway.restassured.RestAssured.given;

public class RestApi {

    private String endPoint;

    public RestApi(String endPoint) {
        this.endPoint = endPoint;
    }

    public RestApi() {
    }

    /**
     * Function to post the API Request
     * @param mabRequest
     * @return
     */
    public Response getMABResponse(Request mabRequest)
            throws JsonProcessingException {

        Response response =
                given().
                        log().all()
                        .accept("application/json")
                        .contentType("application/json")
                        .body(mabRequest)
                .when()
                        .post(endPoint)
                .then()
                        .statusCode(200)
                        .log().all()
                        .extract()
                        .as(Response.class);
        return response;
    }


}
