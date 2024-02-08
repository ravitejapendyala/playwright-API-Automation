package com.qa.api.tests;

import com.api.models.User_lombok;
import com.api.models.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
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

public class CreateUserPostCallUsersPOJO_lombok {
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
    public void CreateUserWithPojoLombok() throws IOException {

        Faker faker = new Faker();

        String email = faker.internet().emailAddress();

//        Users user = new Users("Ravi Teja",email,"male","active");

        // creating user_lombok object using builder patter
        User_lombok user_lombok =  User_lombok.builder()
                .name("Ravi Teja Pendyala")
                .email(email)
                .status("active")
                .gender("Alpha male")
                .build();
        //User_lombok user_lombok = new User_lombok("Ravi Teja",email,"male","active");

        APIResponse apiPostResponse =  requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                .setHeader("Content-Type","application/json")
                .setHeader("Authorization","Bearer 6979bcde6ca87ebff51952ec22dd7fd0a0af9f4156c69cb2f509268d040c410e")
                .setData(user_lombok));
        System.out.println("Response status code is : "+apiPostResponse.status());
        Assert.assertEquals(apiPostResponse.status(),201);
        Assert.assertEquals(apiPostResponse.statusText(),"Created");

       System.out.println("Response is : "+apiPostResponse.text());

       // convert the response text/json to POJO - deserialization

        ObjectMapper objectMapper = new ObjectMapper();
        Users responseUser =  objectMapper.readValue(apiPostResponse.text(),Users.class);
        System.out.println("Email from user Response is: "+responseUser.getEmail());
        System.out.println("Name from user Response is: "+responseUser.getName());
        System.out.println("Status from user Response is: "+responseUser.getStatus());
        System.out.println("User Response from toString is: "+responseUser);
        Assert.assertEquals(responseUser.getEmail(),user_lombok.getEmail());

    }


    @AfterTest
    public void tearDown(){
        playwright.close();
    }
}
