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

    public static RequestSpecification requestSpecification()  {  //Base Request Specification and Request Logging method

        try {
            if (baseRequestSpecification == null) {
                PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
                baseRequestSpecification = new RequestSpecBuilder().setBaseUri(loadPropertiesFile().getProperty("baseURL"))
                        .setContentType(ContentType.JSON)
                        .addFilter(RequestLoggingFilter.logRequestTo(log))
                        .addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
                return baseRequestSpecification;
            }
        }catch (FileNotFoundException e )
        {
            e.printStackTrace();
        }

        return baseRequestSpecification;

    }

    public static Properties loadPropertiesFile()
    {
        try {
            prop = new Properties();
            FileInputStream fis = new FileInputStream("src/main/java/com/petstore/specbuilders/config.properties");
            prop.load(fis);
            return prop;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return prop;
    }
}
