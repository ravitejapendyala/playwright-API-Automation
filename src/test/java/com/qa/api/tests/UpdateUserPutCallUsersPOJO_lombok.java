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

public class UpdateUserPutCallUsersPOJO_lombok {
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
    public void UpdateUserWithPojoLombok() throws IOException {

        Faker faker = new Faker();

        String email = faker.internet().emailAddress();

        User_lombok user = User_lombok.builder()
                    .name("Ravi Teja")
                    .email(email)
                    .gender("male")
                    .status("active")
                    .build();

        APIResponse postResponse =  requestContext.post("https://gorest.co.in/public/v2/users",RequestOptions.create()
                .setHeader("authorization","Bearer 6979bcde6ca87ebff51952ec22dd7fd0a0af9f4156c69cb2f509268d040c410e")
                .setHeader("Content-Type","application/json")
                .setData(user));

        System.out.println("API Post response text is: "+postResponse.statusText());
        System.out.println("API Post response code is: "+postResponse.status());
        ObjectMapper objectMapper = new ObjectMapper();
        User_lombok userResponse = objectMapper.readValue(postResponse.body(),User_lombok.class);
        String id =  userResponse.getId();


        user.setStatus("inActive");
        APIResponse putResponse =  requestContext.put("https://gorest.co.in/public/v2/users/"+id+"",RequestOptions.create()
                .setHeader("authorization","Bearer 6979bcde6ca87ebff51952ec22dd7fd0a0af9f4156c69cb2f509268d040c410e")
                .setHeader("Content-Type","application/json")
                .setData(user));
        System.out.println("API Put response text is: "+putResponse.statusText());
        System.out.println("API Put response code is: "+putResponse.status());

        APIResponse getResponse =  requestContext.get("https://gorest.co.in/public/v2/users/"+id+"",RequestOptions.create()
                .setHeader("authorization","Bearer 6979bcde6ca87ebff51952ec22dd7fd0a0af9f4156c69cb2f509268d040c410e")
                    );
        User_lombok getResponse_AfterUpdate = objectMapper.readValue(getResponse.body(),User_lombok.class);

        System.out.println("API Get response text is: "+getResponse.statusText());
        System.out.println("API Get response code is: "+getResponse.status());
        System.out.println("API Get response body after update is: "+getResponse_AfterUpdate);


    }


    @AfterTest
    public void tearDown(){
        playwright.close();
    }
}
