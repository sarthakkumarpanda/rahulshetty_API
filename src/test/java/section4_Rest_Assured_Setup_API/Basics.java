package section4_Rest_Assured_Setup_API;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payloads.Payload;

public class Basics {
	// given [all input details]

	// when [submit the API] - resource, http methods

	// then [validate the response]

	public static void main(String[] args) {
		// Validate if AddPlaceAPI is working as expected

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String allPlaceResponse = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(Payload.addPlace())
				.when().post("maps/api/place/add/json")
				.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)")
				.extract().response().asString();
		
		System.out.println(allPlaceResponse);
		
		JsonPath js = new JsonPath(allPlaceResponse); //for parsing json
		String placeID = js.getString("place_id");
		
		System.out.println(placeID);
		
		String newAddress = "Winter Walk, Virginia";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body("{\r\n"
				+ "\"place_id\":\""+placeID+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("/maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get Place
		
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1 = new JsonPath(getPlaceResponse);
		String actualAddress = js1.getString("address");
		
		System.out.println(actualAddress);
		Assert.assertEquals(newAddress, actualAddress);

	}

}
