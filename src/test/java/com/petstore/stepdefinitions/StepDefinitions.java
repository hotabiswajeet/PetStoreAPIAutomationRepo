package com.petstore.stepdefinitions;

import com.petstore.pojo.addpet.AddPetPojo;
import com.petstore.pojo.placeorder.PlaceOrderPojo;
import com.petstore.requestpaths.PetRequestPaths;
import com.petstore.specbuilders.RequestResponseSpec;
import com.petstore.utilities.Utility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StepDefinitions extends RequestResponseSpec {

    static String id ;
    static String name ;
    static String categoryId;
    String categoryName;

    RequestSpecification request;

    ResponseSpecification resSpec;

    AddPetPojo pojoObject;

    Response res;

    int statusCode;

    PlaceOrderPojo placeOrderPojo;



    public StepDefinitions() throws IOException {
        super();
    }


    @Given("User adds a pet with {string} and {string}")
    public void user_adds_a_pet_with_and(String petID, String petName){

        id = petID;
        name = petName;

    }

    @Given("User provides {string} and {string}")
    public void user_provides_and(String petCategoryID, String petCategoryName) {

      categoryId = petCategoryID;
      categoryName = petCategoryName;

    }
    @When("User calls {string} API with {string} Request")
    public void user_calls_api_with_request(String requestName, String requestMethod) throws FileNotFoundException {

        PetRequestPaths p =PetRequestPaths.valueOf(requestName);
        resSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(200).build();

        if(requestName.equals("AddPet")&&requestMethod.equals("Post"))
        {
        Utility utility = new Utility();
        AddPetPojo pj = utility.pojoObject(id,name,categoryId,categoryName,prop.getProperty("Status"));
        request = given().spec(requestSpecification()).body(pj);
        res = request.when().post(p.getResource()).then().spec(resSpec).extract().response();
        statusCode = res.getStatusCode();
        pojoObject = res.as(AddPetPojo.class);
        }

        if(requestName.equals("GetPetByID")&&requestMethod.equals("Get"))
        {
            request = given().spec(requestSpecification()).pathParam("key",id);
            res = request.when().get(p.getResource()+"/{key}").then().spec(resSpec).extract().response();
            statusCode = res.getStatusCode();
            pojoObject = res.as(AddPetPojo.class);
        }

        if(requestName.equals("GetPetByStatus")&&requestMethod.equals("Get"))
        {
            request = given().spec(requestSpecification()).queryParam("status",prop.getProperty("Status"));
            res = request.when().get(p.getResource()).then().spec(resSpec).extract().response();
            statusCode = res.getStatusCode();

        }

        if(requestName.equals("PlaceOrder")&&requestMethod.equals("Post"))
        {
            Utility utility = new Utility();
            PlaceOrderPojo orderObject = utility.orderPojoObject(id,categoryId,prop.getProperty("Quantity"),prop.getProperty("OrderStatus"), prop.getProperty("OrderComplete"));
            request = given().spec(requestSpecification()).body(orderObject);
            res = request.when().post(p.getResource()).then().spec(resSpec).extract().response();
            statusCode = res.getStatusCode();
            placeOrderPojo = res.as(PlaceOrderPojo.class);

        }



    }
    @Then("API call is success with statusCode as {string}")
    public void api_call_is_success_with_status_code_as(String expectedStatusCode) {

        String actualStatusCode = String.valueOf(statusCode);
        assertEquals(expectedStatusCode,actualStatusCode);


    }
    @Then("ID in response is same as ID provided")
    public void id_in_response_is_same_as_id_provided() {


        String idInResponse = pojoObject.getId();
        assertEquals(id,idInResponse);

    }

    @Then("Name in response is same as Name provided")
    public void name_in_response_is_same_as_name_provided() {
       String nameInResponse = pojoObject.getName();
       assertEquals(name,nameInResponse);
    }

    @Then("response contains pet with provided ID and Name")
    public void response_contains_pet_with_provided_id_and_name() {
        JsonPath js = new JsonPath(res.asString());
        ArrayList l = js.getJsonObject("");
        boolean flag = false;
        for(int i =0;i<l.size();i++)
        {
            if(js.getString("["+i+"].id").equals(id) && js.getString("["+i+"].name").equals(name));
            {
                flag = true;
            }
        }

        assertTrue(flag);

    }

    @Then("verify the response parameters are the same as passed in Body")
    public void verify_the_response_parameters_are_the_same_as_passed_in_body() {

        String responseID = placeOrderPojo.getId();
        String responsePetID = placeOrderPojo.getPetId();
        String responseQuantity = placeOrderPojo.getQuantity();
        String responseStatus = placeOrderPojo.getStatus();
        String responseComplete = placeOrderPojo.getComplete();

        assertEquals(id,responseID);
        assertEquals(categoryId,responsePetID);
        assertEquals(prop.getProperty("Quantity"),responseQuantity);
        assertEquals(prop.getProperty("OrderStatus"),responseStatus);
        assertEquals(prop.getProperty("OrderComplete"),responseComplete);


    }





}
