package io.utils;

import io.automation.pojo.Authorization;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RESTUtils {
	public Response get(String url, String bearertoken) {
		return RestAssured.given().header("Accept", "application/json, text/plain, */*")
				.header("Accept-Encoding", "gzip, deflate, br").header("Accept-Language", "en-US,en;q=0.9")
				.header("Authorization", bearertoken).header("Connection", "keep-alive").header("Content-Length", "419")
				.header("Content-Type", "application/json").log().all().get(url);
	}

	public Response getAll(String url, String bearertoken) {
		return RestAssured.given().header("Authority", "qa-accounting.api.mousiki.io").header("project","Mousiki")
				.header("Accept", "application/json, text/plain, */*").header("Accept-Encoding", "gzip, deflate, br")
				.header("Accept-Language", "en-US,en;q=0.9").header("Authorization", bearertoken).log().all().get(url);
	}

	public Response getAuthToken(String url, String body) {
		return RestAssured.given()
//		.header("Connection", "keep-alive")
//		.header("Content-Length", "99")
//		.header("Content-Type", "text/plain")
//		.header("Host", "www.googleapis.com")
//		.header("User-Agent", "Apache-HttpClient/4.5.13 (Java/11.0.15)")
				.log().all().body(body).post(url);
	}

	public Response post(String url, Object body, String bearertoken) {
		return RestAssured.given().contentType("application/json").accept("application/json, text/plain, */*")
//		.header("Accept", "application/json")
//		.header("Accept-Encoding", "gzip, deflate, br")
//		.header("Accept-Language", "en-US,en;q=0.9")
				.header("Authorization", bearertoken).header("project","Mousiki")
//		.header("Connection", "keep-alive")
//		.header("Content-Length", "419")
//		.header("Content-Type", "application/json")
				.redirects().follow(false).log().all().body(body).post(url);
	}

	public Response put(String url, Object body, String bearertoken) {
		return RestAssured.given().contentType("application/json").accept("application/json, text/plain, */*")
//		.header("Accept", "application/json")
//		.header("Accept-Encoding", "gzip, deflate, br")
//		.header("Accept-Language", "en-US,en;q=0.9")
				.header("Authorization", bearertoken)
//		.header("Connection", "keep-alive")
//		.header("Content-Length", "419")
//		.header("Content-Type", "application/json")
				.redirects().follow(false).log().all().body(body).put(url);
	}

	public Response patch(String url, String body, String bearertoken) {
		return RestAssured.given().contentType("application/json").accept("application/json, text/plain, */*")
//		.header("Accept", "application/json")
//		.header("Accept-Encoding", "gzip, deflate, br")
//		.header("Accept-Language", "en-US,en;q=0.9")
				.header("Authorization", bearertoken)
//		.header("Connection", "keep-alive")
//		.header("Content-Length", "419")
//		.header("Content-Type", "application/json")
				.redirects().follow(false).log().all().body(body).patch(url);
	}

	public Response delete(String url, String bearertoken) {
		return RestAssured.given().contentType("application/json").accept("application/json, text/plain, */*")
//		.header("Accept", "application/json")
//		.header("Accept-Encoding", "gzip, deflate, br")
//		.header("Accept-Language", "en-US,en;q=0.9")
				.header("Authorization", bearertoken)
//		.header("Connection", "keep-alive")
//		.header("Content-Length", "419")
//		.header("Content-Type", "application/json")
				.header("Authority","localhost:7197")
				.redirects().follow(false).log().all().delete(url);
	}

	public String getAuthTokenByUserIdAndPassword(String userId, String password) {
		Authorization auth = new Authorization();
		RESTUtils restutils = new RESTUtils();
		String authpayload = auth.getauthPayLoad(userId, password);
		
		String token = "";
		Response resp1 = restutils.post(
				"https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword?key=AIzaSyD7oMsXvjTMB0S3Xwbm0wV0ycQvE8AZiig",
				authpayload, token);
		try {
			token = resp1.jsonPath().get("idToken");
			System.out.println("token:" + token);
			
//			reportlog("Authorization Token extracted:" + token, "PASS");
		} catch (Exception e) {
//			reportlog("Error in Authorization Token extracted. Response:" + resp.asPrettyString(), "FAIL");
		}
		return token;
	}
//	
//	String authpayload = auth.getauthPayLoad(userid, password);
//	Response resp = restutils.post(
//			"https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword?key=AIzaSyD7oMsXvjTMB0S3Xwbm0wV0ycQvE8AZiig",
//			authpayload, token);
//	System.out.println("resp " + resp.getBody().asPrettyString());
}
