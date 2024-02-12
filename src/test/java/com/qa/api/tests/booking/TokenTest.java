package com.qa.api.tests.booking;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class TokenTest {

    Playwright playwright;
    APIRequest request;
    APIRequestContext context;
    String tokenValue ="";
    @BeforeTest
    public void BeforeTest() throws IOException {

        playwright = Playwright.create();
        request = playwright.request();
        context = request.newContext();
        String tokenRequestBody = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";

        APIResponse tokenRespponse = context.post("https://restful-booker.herokuapp.com/auth", RequestOptions.create()
                                            .setHeader("Content-Type","application/json")
                                            .setData(tokenRequestBody));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode tokenJson = objectMapper.readTree(tokenRespponse.body());
        tokenValue = tokenJson.get("token").toString();
        System.out.println("Token value is : "+tokenValue);


    }

    @Test
    public void UpdateBookingUsingTokenTest() throws IOException {

        Random random = new Random();



// Create Order
        String bookingCreateRequestBody = "{\n" +
                "    \"firstname\" : \"Jim\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";
        APIResponse POSTResponse = context.post("https://restful-booker.herokuapp.com/booking", RequestOptions.create()
                .setHeader("Content-Type","application/json")
                .setHeader("cookie","token="+tokenValue+"")
                .setData(bookingCreateRequestBody));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode PostJson = objectMapper.readTree(POSTResponse.body());
        System.out.println("Post response status code is: "+POSTResponse.status());
        System.out.println("Post response status text is: "+POSTResponse.statusText());
        System.out.println("Post response body is : "+PostJson.toPrettyString());
        System.out.println("Booking Id is : "+PostJson.get("bookingid"));




        // Update Order

        String bookingUpdateRequestBody = "{\n" +
                "    \"firstname\" : \"James\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : "+random.nextInt(1000)+",\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        APIResponse PutRespponse = context.put("https://restful-booker.herokuapp.com/booking/"+PostJson.get("bookingid")+"", RequestOptions.create()
                .setHeader("Content-Type","application/json")
                .setHeader("cookie","token="+tokenValue+"")
                .setData(bookingUpdateRequestBody));
         objectMapper = new ObjectMapper();
        JsonNode putJson = objectMapper.readTree(PutRespponse.body());
        System.out.println("Put response status code is: "+PutRespponse.status());
        System.out.println("Put response status text is: "+PutRespponse.statusText());
        System.out.println("PUT response body is : "+putJson.toPrettyString());


    }

    @Test
    public void DeleteBookingUsingTokenTest() throws IOException {

        Random random = new Random();



// Create Order
        String bookingCreateRequestBody = "{\n" +
                "    \"firstname\" : \"Jim\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";
        APIResponse POSTResponse = context.post("https://restful-booker.herokuapp.com/booking", RequestOptions.create()
                .setHeader("Content-Type","application/json")
                .setHeader("cookie","token="+tokenValue+"")
                .setData(bookingCreateRequestBody));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode PostJson = objectMapper.readTree(POSTResponse.body());
        System.out.println("Post response status code is: "+POSTResponse.status());
        System.out.println("Post response status text is: "+POSTResponse.statusText());
        System.out.println("Post response body is : "+PostJson.toPrettyString());
        System.out.println("Booking Id is : "+PostJson.get("bookingid"));




        // Update Order

        String bookingUpdateRequestBody = "{\n" +
                "    \"firstname\" : \"James\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : "+random.nextInt(1000)+",\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        APIResponse DeleteRespponse = context.delete("https://restful-booker.herokuapp.com/booking/"+PostJson.get("bookingid")+"", RequestOptions.create()
                .setHeader("cookie","token="+tokenValue+"")
                );
         objectMapper = new ObjectMapper();

        System.out.println("Delete response status code is: "+DeleteRespponse.status());
        System.out.println("Delete response status text is: "+DeleteRespponse.statusText());



    }


    @AfterTest
    public  void AfterTest(){
        playwright.close();
    }
}
