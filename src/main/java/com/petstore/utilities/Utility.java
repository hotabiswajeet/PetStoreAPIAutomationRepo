package com.petstore.utilities;

import com.petstore.pojo.addpet.AddPetPojo;
import com.petstore.pojo.addpet.Category;
import com.petstore.pojo.addpet.Tags;
import com.petstore.pojo.placeorder.PlaceOrderPojo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utility { //Utility class for serialization purpose

    public AddPetPojo pojoObject(String id, String name, String categoryId, String categoryName,String status)
    {

        AddPetPojo addPet = new AddPetPojo();
        Category cj = new Category();
        Tags tag = new Tags();
        tag.setId(id);
        tag.setName(name);
        ArrayList<Tags> tagList= new ArrayList<>();
        tagList.add(tag);
        cj.setId(categoryId);
        cj.setName(categoryName);
        addPet.setId(id);
        addPet.setName(name);
        addPet.setCategory(cj);
        addPet.setTags(tagList);
        addPet.setStatus(status);

        return addPet;

    }

    public PlaceOrderPojo orderPojoObject(String id,String petID,String quantity,String status,String complete)
    {
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timeStamp = format.format(new java.util.Date());

        PlaceOrderPojo placeOrder = new PlaceOrderPojo();
        placeOrder.setId(id);
        placeOrder.setPetId(petID);
        placeOrder.setQuantity(quantity);
        placeOrder.setStatus(status);
        placeOrder.setComplete(complete);
        placeOrder.setShipDate(timeStamp);

        return placeOrder;
    }

    public String offsetTimeStampConversion(String orderDate) throws ParseException {

        DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = utcFormat.parse(orderDate);
        DateFormat offsetFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+0000'");
        offsetFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        orderDate = offsetFormat.format(date); //UTC to UTC Offset conversion
        return orderDate;

    }

}
