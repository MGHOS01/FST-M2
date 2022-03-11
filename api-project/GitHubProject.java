package Github_RestAssured_project;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

public class GitHubProject {
	
	RequestSpecification requestSpec;
	ResponseSpecification responseSpec;
	
	String sshKey;
	int sshkeyID;
	
	@BeforeClass
	public void Setup()
	{
		requestSpec = new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.addHeader("Authorization","token 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")
				.setBaseUri("https://api.github.com")
				.build();
		
		sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC//nGNLrj7ny/1kHEKVTT3Kr1zBbL5Xn/JmjA9FZwJfrbkG/Fy9DCI0diSNCHcy8QT9awsxqLB7w2yJ3Z3E4YjGAHVWDGEbqS60DmcRkSUO8XtE70BAV+RMTPPD2As9HPNUkApIrsTejni3RkHQdM/gH4FxAF6BWa+u/Gf9Y2TOf+oKUT3QKsFbL4P86djaBArydjpchv4FCBEsL2A2N/bks8vNFCf5LkQwEIPOtp2FNXo89WAoPsjivpPbx3eOwfH2m7WlUYbLjbEbG/7nfSesiLDn5ThW7y3juqpgWaHWEDVAW0aU7nzE9OWADG2UogSNsmeQ3ZwkC4lNcy9LOhx";
	}
	
	@Test(priority =1)
	public void addKey()
	{
		
		String reqBody = "{\"title\": \"TestKey\", \"key\": \"" +sshKey+ "\"}";
		Response response = given().spec(requestSpec)
				.body(reqBody)
				.when().post("/user/keys");
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		sshkeyID = response.then().extract().path("id");
		
	response.then().statusCode(201);
		
	}
	
	@Test(priority= 2)
	public void getKeys()
	{
		Response response = given().spec(requestSpec)
				.when().get("/user/keys");
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		
		response.then().statusCode(200);
	}
	
	@Test(priority= 3)
	public void deleteKeys()
	{
		Response response = given().spec(requestSpec)
				.pathParam("keyID",sshkeyID).when().delete("/user/keys/{keyID}");
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		
		response.then().statusCode(204);
	}
	
	
}
