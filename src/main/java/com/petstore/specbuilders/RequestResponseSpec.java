package com.petstore.specbuilders;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class RequestResponseSpec {

    public static RequestSpecification baseRequestSpecification;

    public static Properties prop;

    public RequestResponseSpec() throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream("src/main/java/com/petstore/specbuilders/config.properties");
        prop.load(fis);
    }


    public static RequestSpecification requestSpecification() throws FileNotFoundException {  //Base Request Specification and Request Logging method

        if(baseRequestSpecification ==null){
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
            baseRequestSpecification = new RequestSpecBuilder().setBaseUri(prop.getProperty("baseURL"))
                    .setContentType(ContentType.JSON)
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
            return baseRequestSpecification;}

        return baseRequestSpecification;

    }
}
