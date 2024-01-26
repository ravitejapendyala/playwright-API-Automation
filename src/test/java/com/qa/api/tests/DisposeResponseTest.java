package com.qa.api.tests;

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

public class DisposeResponseTest {
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

    APIResponse apiResponse =  requestContext.get("https://gorest.co.in/public/v2/users",RequestOptions.
            create()
            .setQueryParam("gender","male")
            .setQueryParam("staus","active")
    );
        System.out.println("API response is : "+apiResponse.status());
        System.out.println("API response is : "+apiResponse.statusText());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonReponse =  objectMapper.readTree(apiResponse.body());
        System.out.println("Pretty response is : "+jsonReponse.toPrettyString());

        apiResponse.dispose();
        System.out.println("Pretty response After dispose is : "+apiResponse.text());

    }
    @Test
    public void getUsersWithParameterAPITest() throws IOException {



    }

    @AfterTest
    public void tearDown(){
        playwright.close();
    }
}
