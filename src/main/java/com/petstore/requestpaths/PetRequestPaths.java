package com.petstore.requestpaths;

public enum PetRequestPaths {   //enum for maintaining request URL Parameters

    ADDPET("/pet"),
    GETPETBYID("/pet"),
    GETPETBYSTATUS("/pet/findByStatus"),
    PLACEORDER("/store/order"),
    GETPURCHASEORDER("/store/order"),
    DELETEPURCHASEORDER("/store/order");

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
