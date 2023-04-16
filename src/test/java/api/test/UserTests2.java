package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {
	
	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setupData() {
		
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority = 1)
	void testPostUser() {
		logger.info(" ************* Creating User *********** ");
		Response response = UserEndPoints2.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println("-------------- FIRST TEST END -------------");
		logger.info(" ************* User is Created *********** ");
	}
	
	@Test(priority = 2)
	void testGetUserByName() {
		logger.info(" ************* Reading User *********** ");
		Response response = UserEndPoints2.readUser(userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println("-------- SECOND TEST END -----------------");
		logger.info(" ************* User Info is displayed *********** ");
	}
	
	@Test(priority = 3)
	void testUpdateUserByName() {
		logger.info(" ************* Updating User *********** ");
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		System.out.println("-------------- CHECKING DATA BEFORE UPDATE -----------");
		
		Response response = UserEndPoints2.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		// checking data after update
		System.out.println("-------------- CHECKING DATA AFTER UPDATE -----------");
		
		Response responseAfterUpdate = UserEndPoints2.readUser(this.userPayload.getUsername());
		responseAfterUpdate.then().log().all();
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		System.out.println("-------- THIRD TEST END -----------------");
		logger.info(" ************* User is Updated *********** ");
	}
	
	
	@Test(priority = 4)
	void testDeleteUserByName() {
		logger.info(" ************* Deleting User *********** ");
		Response response = UserEndPoints2.deleteUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println("-------- FOURTH TEST END -----------------");
		logger.info(" ************* User is deleted *********** ");
	}
}
