package section4_and_section5_Rest_Assured_Setup_API;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Basics {
	public static String response;
	public static String getPlaceResponse;
	public static String placeID;
	public static JsonPath js;
	public static String newAddress;
	public static String actualAddress;

	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
	response =	given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(payloadAddPlace()).when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)")
		.extract().response().asString();

	    printResponse(); // in case you want to print the response
	    //Now let's say from this response body you want to extract place_id. So we need to parse the output response JSON
	    
		//Add place and then update place with new address - Get place to validate if new address is present in response
	    
	    js = new JsonPath(response); //which takes String as input and converts that to JSON
	    placeID = js.get("place_id");
	
	    printPlaceID(); //in case you want to print the place id
	    
	    //Update place API using PUT http method
	    
	    newAddress = "70 winter walk, USA";
	
	    given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
	    .body("{\r\n"
	    		+ "\"place_id\":\""+placeID+"\",\r\n"
	    		+ "\"address\":\""+newAddress+"\",\r\n"
	    		+ "\"key\":\"qaclick123\"\r\n"
	    		+ "}").when().put("maps/api/place/update/json")
	    .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated")).header("Server", "Apache/2.4.52 (Ubuntu)");
	    
	    //Get Place API using GET http method
	    
 
	    getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
	    .when().get("maps/api/place/get/json")
	    .then().log().all().assertThat().statusCode(200).extract().response().asString();
	    
	    js = new JsonPath(getPlaceResponse);
	    actualAddress = js.get("address");
	    
	    Assert.assertEquals(newAddress, actualAddress);
	}
	
	
	
	
	public static void printPlaceID() {
		System.out.println("****************************************************************************************************");
		System.out.println(placeID);
	}
	
	
	
	
	public static void printResponse() {
		System.out.println("****************************************************************************************************");
		System.out.println(response);
	}
	
	
	
	public static String payloadAddPlace() {
		return "{\r\n"
				+ "  \"location\": {\r\n"
				+ "    \"lat\": -38.383494,\r\n"
				+ "    \"lng\": 33.427362\r\n"
				+ "  },\r\n"
				+ "  \"accuracy\": 50,\r\n"
				+ "  \"name\": \"Frontline house\",\r\n"
				+ "  \"phone_number\": \"(+1) 987-654-3210\",\r\n"
				+ "  \"address\": \"29, side layout, cohen 09\",\r\n"
				+ "  \"types\": [\r\n"
				+ "    \"shoe park\",\r\n"
				+ "    \"shop\"\r\n"
				+ "  ],\r\n"
				+ "  \"website\": \"http://google.com\",\r\n"
				+ "  \"language\": \"French-IN\"\r\n"
				+ "} ";
	}

}
