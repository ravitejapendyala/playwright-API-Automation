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
import java.util.List;
import java.util.Map;

public class APIResponseHeadersTest {
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
    public void getHeadersTest() throws IOException {


        APIResponse apiResponse =  requestContext.get("https://gorest.co.in/public/v2/users");
        System.out.println("Response status code is : "+apiResponse.status());
        System.out.println("Response status code is : "+apiResponse.statusText());
        System.out.println("Response body is : "+apiResponse.body());
        System.out.println("Response with plain text is : "+apiResponse.text());
        Assert.assertEquals(apiResponse.status(),200);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse =  objectMapper.readTree(apiResponse.body());
        //System.out.println("Response body is: ");
        //System.out.println(jsonResponse.toPrettyString());
        System.out.println("API Response url:  "+apiResponse.url());
        Map<String,String> headers = apiResponse.headers();
        headers.forEach((k,v)-> System.out.println("Header Name: "+k+ " With Value: "+v));
        System.out.println("Total response headers : "+headers.size());
//        System.out.println("Headers are : "+ apiResponse.headersArray().toString());
//        System.out.println("Headers are : "+ headers.toString());
        Assert.assertEquals("application/json; charset=utf-8",headers.get("content-type"));
        Assert.assertEquals("cloudflare",headers.get("server"));
        List<HttpHeader> headerslist = apiResponse.headersArray();
        for(HttpHeader h: headerslist){
            System.out.println("Header : "+h.name+" Value: "+h.value);
        }
    }


    @AfterTest
    public void tearDown(){
        playwright.close();
    }
}
