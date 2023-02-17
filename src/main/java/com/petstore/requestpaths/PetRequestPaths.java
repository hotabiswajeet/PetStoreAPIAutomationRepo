package com.petstore.requestpaths;

public enum PetRequestPaths {

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
