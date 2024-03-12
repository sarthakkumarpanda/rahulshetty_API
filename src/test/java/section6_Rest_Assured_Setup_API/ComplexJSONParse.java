package section6_Rest_Assured_Setup_API;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class ComplexJSONParse {

	public static void main(String[] args) {
		JsonPath js = new JsonPath(complexJsonBody());
		
		//Print number of courses returned by API
		int totalCourses = js.getInt("courses.size()");
		System.out.println("Total number of courses are : " + totalCourses);
		
		//Print purchase amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Total purchase amount is : " + purchaseAmount);
		
		//Print title of first course
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println("The title of the first course is : " + titleFirstCourse);
		
		//Print all course titles and their respective prices
		
		for(int i=0 ; i<totalCourses ; i++) {
			String courseTitles = js.get("courses["+i+"].title");
			 String coursePrices = js.get("courses["+i+"].price").toString();
			System.out.println(courseTitles + "------------>" + coursePrices);
		}
		
		//Print number of compies sold by RPA course in a normal way
		int rpaCopies = js.getInt("courses[2].copies");
		System.out.println("Total number of copies sold by RPA : " + rpaCopies);
		
		//Print number of compies sold by RPA course in a dynamic way
		
		for(int i=0 ; i<totalCourses ; i++) {
			String courseTitles = js.get("courses["+i+"].title");
			if(courseTitles.equalsIgnoreCase("RPA")) {
				int copies = js.getInt("courses["+i+"].copies");
				System.out.println("Total number of copies sold by RPA dynamically :" + copies);
				break;
			}	
		}
		
		//Verify if sum of all course price matches with purchase amount
		
		int sum = 0;
		for(int i=0 ; i<totalCourses ; i++) {
			int coursePrices = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			int amount = coursePrices*copies;
			System.out.println(amount);
			sum = sum+amount;
		}
		System.out.println(sum);
		
		Assert.assertEquals(sum, purchaseAmount);
	}
	
	
	
	
	
	
	
	
	public static String complexJsonBody() {
		return "{\r\n"
				+ "\"dashboard\": {\r\n"
				+ "\"purchaseAmount\": \"910\",\r\n"
				+ "\"website\": \"rahulshettyacademy.com\"\r\n"
				+ "},\r\n"
				+ "\"courses\" : [\r\n"
				+ "{\r\n"
				+ "\"title\": \"Selenium Python\",\r\n"
				+ "\"price\": \"50\",\r\n"
				+ "\"copies\": \"6\"\r\n"
				+ "},\r\n"
				+ "{\r\n"
				+ "\"title\": \"Cypress\",\r\n"
				+ "\"price\": \"40\",\r\n"
				+ "\"copies\": \"4\"\r\n"
				+ "},\r\n"
				+ " {\r\n"
				+ "\"title\": \"RPA\",\r\n"
				+ "\"price\": \"45\",\r\n"
				+ "\"copies\": \"10\"\r\n"
				+ "} \r\n"
				+ "]\r\n"
				+ "}";
	}

}
