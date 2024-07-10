package com.qa.api.tests.Post;

import com.fasterxml.jackson.databind.JsonNode;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CreateUserPostCallWithJsonFile {
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
    public void CreateUserWithStringBody() throws IOException {

        Faker faker = new Faker();
     /*   Map<String,Object> inputData = new HashMap<String, Object>();
        inputData.put("name","Ravi Teja Pendyala");
        inputData.put("gender","male");
        inputData.put("email",faker.internet().emailAddress());
        inputData.put("status","active");*/
        String email = faker.internet().emailAddress();

        // Get the Json file
        
        File file = new File("./src/test/data/create_user.json");
        byte[] fileBytes =  Files.readAllBytes(file.toPath());

        APIResponse apiPostResponse =  requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                .setHeader("Content-Type","application/json")
                .setHeader("Authorization","Bearer 6979bcde6ca87ebff51952ec22dd7fd0a0af9f4156c69cb2f509268d040c410e")
                .setData(fileBytes));
        System.out.println("Response status code is : "+apiPostResponse.status());
        Assert.assertEquals(apiPostResponse.status(),201);
        Assert.assertEquals(apiPostResponse.statusText(),"Created");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode postJsonResponse =  objectMapper.readTree(apiPostResponse.body());
        System.out.println("Post output is : "+postJsonResponse.toPrettyString());

        // capture Id from postjson response

        String id =  postJsonResponse.get("id").asText();

        // Get call : Fetch same user by Id

        APIResponse getResponse =  requestContext.get("https://gorest.co.in/public/v2/users/"+id+"",
                RequestOptions.create()
                        .setHeader("Authorization","Bearer 6979bcde6ca87ebff51952ec22dd7fd0a0af9f4156c69cb2f509268d040c410e")
        );
        System.out.println("Get response is : "+getResponse.status());

        JsonNode getResponseJson = objectMapper.readTree(getResponse.body());
        System.out.println("Get response is : "+getResponseJson.toPrettyString());
        Assert.assertEquals(getResponse.status(),200);
        System.out.println("Get response is : "+getResponse.statusText());
        Assert.assertTrue(getResponse.text().contains(id));
        //Assert.assertTrue(getResponse.text().contains("Pendyala"));
        //Assert.assertTrue(getResponse.text().contains(email));

    }


    @AfterTest
    public void tearDown(){
        playwright.close();
    }
}
