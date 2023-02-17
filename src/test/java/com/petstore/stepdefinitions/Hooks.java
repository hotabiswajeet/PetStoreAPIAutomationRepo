package com.petstore.stepdefinitions;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.petstore.stepdefinitions.StepDefinitions.*;

public class Hooks {

@Before("@GetPetByID or @GetPetByStatus or @PlaceOrderPet")
public void addPetAsPrerequisite() throws IOException {

    if(id==null) {
        StepDefinitions sd = new StepDefinitions();
        sd.user_adds_a_pet_with_and(prop.getProperty("ID"), prop.getProperty("Name"));
        sd.user_provides_and(prop.getProperty("CategoryID"), prop.getProperty("CategoryName"));
        sd.user_calls_api_with_request("AddPet","Post");
    }

}

@Before("@FindPurchaseOrder or @DeletePurchaseOrder")
    public void placeOrderAsPrerequisite() throws IOException {
    if (id == null) {
        StepDefinitions sd = new StepDefinitions();
        sd.user_adds_a_pet_with_and(prop.getProperty("ID"), prop.getProperty("Name"));
        sd.user_provides_and(prop.getProperty("CategoryID"), prop.getProperty("CategoryName"));
        sd.user_calls_api_with_request("AddPet", "Post");
        sd.user_calls_api_with_request("PlaceOrder", "Post");
    }
}

}
