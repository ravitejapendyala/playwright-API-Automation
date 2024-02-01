package com.qa.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.HttpHeader;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

public class PostRequestTest {
    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;
    @BeforeTest
    public  void beforeTest(){
         playwright =  Playwright.create();
         request =  playwright.request();
            requestContext =  request.newContext();
    }

    @Test
    public void PostRequestTest() throws IOException {

        Faker faker = new Faker();
        Map<String,Object> inputData = new HashMap<String, Object>();
        inputData.put("name","Ravi Teja Pendyala");
        inputData.put("gender","male");
        inputData.put("email",faker.internet().emailAddress());
        inputData.put("status","active");
        APIResponse apiPostResponse =  requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                .setHeader("Content-Type","application/json")
                .setHeader("Authorization","Bearer 6979bcde6ca87ebff51952ec22dd7fd0a0af9f4156c69cb2f509268d040c410e")
                .setData(inputData));
        System.out.println("Response status code is : "+apiPostResponse.status());
        Assert.assertEquals(apiPostResponse.status(),201);
        Assert.assertEquals(apiPostResponse.statusText(),"Created");


    }


    @AfterTest
    public void tearDown(){
        playwright.close();
    }
}
