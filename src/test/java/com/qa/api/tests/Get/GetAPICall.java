package com.qa.api.tests.Get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class GetAPICall {
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
    public void getUsersAPITest() throws IOException {


        APIResponse apiResponse =  requestContext.get("https://gorest.co.in/public/v2/users");
        System.out.println("Response status code is : "+apiResponse.status());
        System.out.println("Response status code is : "+apiResponse.statusText());
        System.out.println("Response body is : "+apiResponse.body());
        System.out.println("Response with plain text is : "+apiResponse.text());
        Assert.assertEquals(apiResponse.status(),200);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse =  objectMapper.readTree(apiResponse.body());
        System.out.println("Response body is: ");
        System.out.println(jsonResponse.toPrettyString());
        System.out.println("API Response url:  "+apiResponse.url());
        Map<String,String> headers = apiResponse.headers();
        System.out.println("Headers are : "+ apiResponse.headersArray().toString());
        System.out.println("Headers are : "+ headers.toString());
        Assert.assertEquals("application/json;",headers.get("content-type"));
    }
    @Test
    public void getUsersWithParameterAPITest() throws IOException {


        APIResponse apiResponse =  requestContext.get("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setQueryParam("gender","male")
                        .setQueryParam("status","inactive"));
        System.out.println("Response status code is : "+apiResponse.status());
        System.out.println("Response status code is : "+apiResponse.statusText());
        System.out.println("Response body is : "+apiResponse.body());
        System.out.println("Response with plain text is : "+apiResponse.text());
        Assert.assertEquals(apiResponse.status(),200);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse =  objectMapper.readTree(apiResponse.body());
        System.out.println("Response body is: ");
        System.out.println(jsonResponse.toPrettyString());
        System.out.println("API Response url:  "+apiResponse.url());
        Map<String,String> headers = apiResponse.headers();
        System.out.println("Headers are : "+ apiResponse.headersArray().toString());
        System.out.println("Headers are : "+ headers.toString());

    }

    @AfterTest
    public void tearDown(){
        playwright.close();
    }
}
