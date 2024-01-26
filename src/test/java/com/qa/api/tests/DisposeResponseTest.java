package com.qa.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
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
        String status = apiResponse.statusText();
        System.out.println("Status before response dispose  : "+status);

        apiResponse.dispose(); // Will dispose only response body but response text , status ,url will remain intact
        try{
            System.out.println("Pretty response After dispose is : "+apiResponse.text());

        }
        catch (PlaywrightException ex){
            System.out.println("Response is disposed");
        }
        String status_AfterDisponse = apiResponse.statusText();
        System.out.println("Status after response dispose  : "+status_AfterDisponse);
        System.out.println("Response URL after   response dispose  : "+apiResponse.url());
        System.out.println("Response code after   response dispose  : "+apiResponse.status());
        //System.out.println("Pretty response After dispose is : "+apiResponse.text());

    }
    @Test
    public void getUsersWithParameterAPITest() throws IOException {



    }

    @AfterTest
    public void tearDown(){
        playwright.close();
    }
}
