package com.petstore.stepdefinitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.petstore.pojo.addpet.AddPetPojo;
import com.petstore.pojo.deleteorder.DeleteOrderPojo;
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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class StepDefinitions extends RequestResponseSpec {

    static String id ;
    static String name ;
    static String categoryId;
    String categoryName;

    RequestSpecification request;

    ResponseSpecification baseResponseSpecification;

    AddPetPojo pojoObject;

    Response res;

    int statusCode;

    static PlaceOrderPojo placeOrderPojo;

    PlaceOrderPojo findPurchaseOrderResponse;

    DeleteOrderPojo deleteOrder;

    String orderDate;




    public StepDefinitions() {
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

        PetRequestPaths p =PetRequestPaths.valueOf(requestName.toUpperCase());
        baseResponseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();

        if(requestName.equalsIgnoreCase("AddPet")&&requestMethod.equals("Post"))
        {
        Utility utility = new Utility();
        AddPetPojo pj = utility.pojoObject(id,name,categoryId,categoryName,prop.getProperty("Status"));
        request = given().spec(requestSpecification()).body(pj);
        res = request.when().post(p.getResource()).then().spec(baseResponseSpecification).extract().response();
        statusCode = res.getStatusCode();
        pojoObject = res.as(AddPetPojo.class);

        }

        if(requestName.equalsIgnoreCase("GetPetByID")&&requestMethod.equals("Get"))
        {
            request = given().spec(requestSpecification()).pathParam("key",id);
            res = request.when().get(p.getResource()+"/{key}").then().spec(baseResponseSpecification).extract().response();
            statusCode = res.getStatusCode();
            pojoObject = res.as(AddPetPojo.class);

        }

        if(requestName.equalsIgnoreCase("GetPetByStatus")&&requestMethod.equals("Get"))
        {
            request = given().spec(requestSpecification()).queryParam("status",prop.getProperty("Status"));
            res = request.when().get(p.getResource()).then().spec(baseResponseSpecification).extract().response();
            statusCode = res.getStatusCode();


        }

        if(requestName.equalsIgnoreCase("PlaceOrder")&&requestMethod.equals("Post"))
        {
            Utility utility = new Utility();
            PlaceOrderPojo orderObject = utility.orderPojoObject(id,categoryId,prop.getProperty("Quantity"),prop.getProperty("OrderStatus"), prop.getProperty("OrderComplete"));
            orderDate = orderObject.getShipDate();
            request = given().spec(requestSpecification()).body(orderObject);
            res = request.when().post(p.getResource()).then().spec(baseResponseSpecification).extract().response();
            statusCode = res.getStatusCode();
            placeOrderPojo = res.as(PlaceOrderPojo.class);

        }

        if(requestName.equalsIgnoreCase("GetPurchaseOrder")&&requestMethod.equals("Get"))
        {
            request = given().spec(requestSpecification()).pathParam("key",id);
            res = request.when().get(p.getResource()+"/{key}").then().spec(baseResponseSpecification).extract().response();
            statusCode = res.getStatusCode();
            if(String.valueOf(statusCode).equals("200")) {
                findPurchaseOrderResponse = res.as(PlaceOrderPojo.class);
            }
            if(String.valueOf(statusCode).equals("404"))
            {
                deleteOrder = res.as(DeleteOrderPojo.class);
            }
        }

        if(requestName.equalsIgnoreCase("DeletePurchaseOrder")&&requestMethod.equals("Delete"))
        {

            request = given().spec(requestSpecification()).pathParam("key",id);
            res = request.when().delete(p.getResource()+"/{key}").then().spec(baseResponseSpecification).extract().response();
            statusCode = res.getStatusCode();
            deleteOrder = res.as(DeleteOrderPojo.class);

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
    public void verify_the_response_parameters_are_the_same_as_passed_in_body() throws ParseException {

        Utility utility = new Utility();
        String expectedOrderDate = utility.offsetTimeStampConversion(orderDate);

        String responseID = placeOrderPojo.getId();
        String responsePetID = placeOrderPojo.getPetId();
        String responseQuantity = placeOrderPojo.getQuantity();
        String responseStatus = placeOrderPojo.getStatus();
        String responseComplete = placeOrderPojo.getComplete();
        String responseDate = placeOrderPojo.getShipDate();

        assertEquals(id,responseID);
        assertEquals(categoryId,responsePetID);
        assertEquals(prop.getProperty("Quantity"),responseQuantity);
        assertEquals(prop.getProperty("OrderStatus"),responseStatus);
        assertEquals(prop.getProperty("OrderComplete"),responseComplete);
        assertEquals(expectedOrderDate,responseDate);



    }

    @Then("response parameters are same as for Place Order Request")
    public void response_parameters_are_same_as_for_place_order_request() {

        assertEquals(placeOrderPojo.getShipDate(),findPurchaseOrderResponse.getShipDate());
        assertEquals(placeOrderPojo.getId(),findPurchaseOrderResponse.getId());
        assertEquals(placeOrderPojo.getPetId(),findPurchaseOrderResponse.getPetId());
        assertEquals(placeOrderPojo.getQuantity(),findPurchaseOrderResponse.getQuantity());
        assertEquals(placeOrderPojo.getStatus(),findPurchaseOrderResponse.getStatus());
        assertEquals(placeOrderPojo.getComplete(),findPurchaseOrderResponse.getComplete());



    }

    @Then("In case of successful {string} request {string} in response is equal to {string}")
    public void in_case_of_successful_request_in_response_is_equal_to(String requestName,String verificationParameter, String value) {

        if(requestName.equals("DeletePurchaseOrder")) {
            switch (verificationParameter) {
                case "message":
                    value = id;
                    assertEquals(value, deleteOrder.getMessage());
                    break;
                case "type":
                    assertEquals(value, deleteOrder.getType());
                    break;
                default:
                    fail("Conditions not matched");

            }
        }

        if(requestName.equals("GetPurchaseOrder")) {
            switch (verificationParameter) {
                case "message":
                    assertEquals(value, deleteOrder.getMessage());
                    break;
                case "type":
                    assertEquals(value, deleteOrder.getType());
                    break;
                default:
                    fail("Conditions not matched");

            }
        }



    }










}
