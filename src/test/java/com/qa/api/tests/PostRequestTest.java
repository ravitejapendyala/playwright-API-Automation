package com.qa.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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


        Map<String,Object> inputData = new HashMap<String, Object>();
        inputData.put("name","Ravi Teja Pendyala");
        inputData.put("gender","male");
        inputData.put("email","tenali.ramakrishna3@17ce.com");
        inputData.put("status","active");
        APIResponse apiPostResponse =  requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                .setHeader("Content-Type","application/json")
                .setHeader("Authorization","Bearer 7bb2b7149e52ec7745208dd33822ad09a8117980860506d564ed566d5b12c4aa")
                .setData(inputData));
        System.out.println("Response status code is : "+apiPostResponse.status());
        Assert.assertEquals(apiPostResponse.status(),201);

        List<HttpHeader> headerslist = apiPostResponse.headersArray();
        for(HttpHeader h: headerslist){
            System.out.println("Header -> "+h.name+" Value-> "+h.value);
        }
    }


    @AfterTest
    public void tearDown(){
        playwright.close();
    }
}
