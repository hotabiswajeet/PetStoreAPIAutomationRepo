package com.petstore.requestpaths;

public enum PetRequestPaths {

    AddPet("/pet"),
    GetPetByID("/pet"),
    GetPetByStatus("/pet/findByStatus"),
    PlaceOrder("/store/order");

    String s;
    PetRequestPaths(String s)
    {
        this.s = s;
    }

    public String getResource()
    {
        return s;
    }

}
