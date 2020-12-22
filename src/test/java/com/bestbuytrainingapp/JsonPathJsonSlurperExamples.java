package com.bestbuytrainingapp;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class JsonPathJsonSlurperExamples {

    static ValidatableResponse validatableResponse;

    private static void print(String val) {
        System.out.println ("----------------------------------------------------------");
        System.out.println (val);
        System.out.println ("----------------------------------------------------------");
    }

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3030;

        validatableResponse = given ().when ().get ("/stores").then ();
    }

    @BeforeMethod
    void startTestMethod() {
        System.out.println ("-----------------------Starting the test method-------------");
        System.out.println ("     ");
    }

    @AfterMethod
    void EndTestMethod() {
        System.out.println ("-----------------------Ending the test method-------------");
        System.out.println ("     ");
    }

    @Test
    public void getTotal(){
        int total = validatableResponse.extract ().path ("total");
        print (String.valueOf (total));
    }

    @Test
    public void getFirstStoreName() {
        String storeName = validatableResponse.extract ().path ("data[0].name");
        print (storeName);
    }

    @Test
    public void getFirstServiceNameFromFirstDataElement() {
        String serviceName = validatableResponse.extract ().path ("data[0].services[0].name");
        print (serviceName);
    }

    @Test
    public void getStoreDetailsWithZip(){
        HashMap<String, ?> storeDetails = validatableResponse.extract ().path ("data.find{it.zip=='55901'}");
        print (storeDetails.toString ());
    }

    @Test
    public void getStoreAddressWithZip(){
        String storeAddress = validatableResponse.extract ().path ("data.find{it.zip=='55901'}.address");
        print (storeAddress);
    }

    @Test
    public void getInfoOfStoreWithMaxId(){
        HashMap<String, ?> maxId = validatableResponse.extract ().path ("data.max{it.id}");
        print (maxId.toString ());

        HashMap<String, ?> minId = validatableResponse.extract ().path ("data.min{it.id}");
        print (minId.toString ());
    }

    @Test
    public void getAllStoresWithIdsLessThan10(){
        List<String> storeDetails = validatableResponse.extract ().path ("data.findAll{it.id<10}");
        print (storeDetails.toString ());
    }

    @Test
    public void getAllServiceNames(){
        List<List<String>> storeNames = validatableResponse.extract ().path ("data.services.findAll{it.name}.name");
        print (storeNames.toString ());
    }

}
