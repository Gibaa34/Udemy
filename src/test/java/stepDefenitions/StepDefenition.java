package stepDefenitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StepDefenition extends Utils {

    ResponseSpecification responseSpec;
    RequestSpecification res;
    Response response;
    TestDataBuild data = new TestDataBuild();
    static String place_Id;

    @Given("Add Place Payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String language, String address) throws IOException {

        responseSpec = new ResponseSpecBuilder().expectStatusCode(200)
                .expectContentType("application/json;charset=UTF-8").build();

         res = given().spec(requestSpecification()).body(data.addPlacePayload(name,language,address));

    }


    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String method) {

        APIResources resourceAPI = APIResources.valueOf(resource);
        resourceAPI.getResource();

        if(method.equalsIgnoreCase("POST")) {
            response = res.when().post(resourceAPI.getResource());
        } else if (method.equalsIgnoreCase("GET")) {
            response = res.when().get(resourceAPI.getResource());
        }

    }


    @Then("the API call is successful with status code {int}")
    public void the_api_call_is_successful_with_status_code(int value) {

        assertEquals(value,response.getStatusCode());

    }


    @Then("{string} in response body {string}")
    public void in_response_body(String key, String expectedValue) {

        assertEquals(expectedValue, getJsonPath(response, key));

    }


    @Then("Verify place_Id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {

        place_Id = getJsonPath(response, "place_id");
        res = given().spec(requestSpecification()).queryParam("place_id", place_Id);
        user_calls_with_http_request(resource, "GET");
        String actualName = getJsonPath(response, "name");
        assertEquals(expectedName, actualName);

    }

    @Given("DeletePlace Payload")
    public void delete_place_payload() throws IOException {

            res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_Id));

    }

}
