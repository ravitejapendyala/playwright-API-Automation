package com.qa.api.tests;

import com.api.models.User_lombok;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteAPICall {

    Playwright playwright;
    APIRequest request;
    APIRequestContext context;
    @BeforeTest
    public void beforeTest(){
         playwright =  Playwright.create();
         request = playwright.request();
         context = request.newContext();
    }

    @Test
    public  void DeleteAPI() throws IOException {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String name = faker.name().fullName();
        name="Ravi O' 12Heis@n";
        name=name.replaceAll("[^a-zA-Z0-9]+","");

        User_lombok user_lombok = User_lombok.builder()
                .name("RaviTeja")
                .email(email)
                .gender("male")
                .status("active")
                .build();
        APIResponse postResponse = context.post("https://gorest.co.in/public/v2/users", RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization","Bearer 6979bcde6ca87ebff51952ec22dd7fd0a0af9f4156c69cb2f509268d040c410e")
                        .setData(user_lombok));
        ObjectMapper objectMapper = new ObjectMapper();
        User_lombok user_PostResponse =  objectMapper.readValue(postResponse.body(),User_lombok.class);
        System.out.println("Post user response is: "+user_PostResponse);
        System.out.println("Post user status is: "+postResponse.status());
        System.out.println("Post user status text is: "+postResponse.statusText());

        // Get API

        APIResponse GetResponse = context.get("https://gorest.co.in/public/v2/users/"+user_PostResponse.getId()+"", RequestOptions.create()
                .setHeader("Authorization","Bearer 6979bcde6ca87ebff51952ec22dd7fd0a0af9f4156c69cb2f509268d040c410e"));
        System.out.println("Get user status is: "+GetResponse.status());
        System.out.println("Get user status text is: "+GetResponse.statusText());

        // Delete API

        APIResponse deleteResponse = context.delete("https://gorest.co.in/public/v2/users/"+user_PostResponse.getId()+"", RequestOptions.create()
                .setHeader("Authorization","Bearer 6979bcde6ca87ebff51952ec22dd7fd0a0af9f4156c69cb2f509268d040c410e"));
        System.out.println("Delete user status is: "+deleteResponse.status());
        System.out.println("Delete user status text is: "+deleteResponse.statusText());



    }
}
