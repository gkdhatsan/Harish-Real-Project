package io.testscripts;

import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.automation.pojo.Authorization;
import io.automation.pojo.CompanyAPI;
import io.dataprovider.TestCaseData;
import io.restassured.response.Response;
import io.testbase.TestBase;
import io.utils.RESTUtils;

public class Companies extends TestBase {

	@Test(dataProviderClass = TestCaseData.class, dataProvider = "mytestdata", alwaysRun = true, priority = -1)
	public void authenticateUser(Map<String, String> data, ITestContext context) throws Throwable {
		RESTUtils restutils = new RESTUtils();
		Authorization auth = new Authorization();
		String userid = data.get("Email");
		String password = data.get("Password");
		String url = data.get("Url");
		extenttestinitialize("authenticateUser", "authenticateUser for accounting");

		// get authorization token
		String token = restutils.getAuthTokenByUserIdAndPassword(userid, password);
		String authpayload = auth.getauthPayLoad(userid, password);
		Response resp = restutils.post(url, authpayload, token);
		System.out.println("resp " + resp.getBody().asPrettyString());
		String responsecode = resp.getStatusCode() + "";
		if (responsecode.contains("200")) {
			context.setAttribute(userid, token);
			reportlog("Authenticated Successful Response:" + responsecode, "PASS");
		} else {
			reportlog("Authenticated Failed Response Code: ", "FAIL", String.valueOf(resp.getStatusCode()),resp.asPrettyString());
		}
	}

	@Test(dataProviderClass = TestCaseData.class, dataProvider = "mytestdata", alwaysRun = true)
	public void getCompanies(Map<String, String> data, ITestContext context) throws Throwable {
		RESTUtils restutils = new RESTUtils();
		String userid = data.get("Email");
		String password = data.get("Password");
		String url = data.get("Url");
		extenttestinitialize("getCompanies", "getCompanies for accounting user");

		// get authorization token
		String token = "";
		if(context.getAttributeNames().contains(userid)) {
			token = (String) context.getAttribute(userid);
		}else {
			token = restutils.getAuthTokenByUserIdAndPassword(userid, password);
		}
		String bearerToken = "Bearer " + token;

		// calling getAll method
		Response response = restutils.getAll(url, bearerToken);
		System.out.println("response.getStatusCode() " + response.getStatusCode());
		System.out.println("response " + response.getBody().asPrettyString());
		if (response.getStatusCode() == 200) {
			reportlog("getCompanies Successful Response:", "PASS", response);
		} else {
			reportlog("getCompanies Failed Response Code: ", "FAIL",String.valueOf(response.getStatusCode()),response.asPrettyString());
		}
	}
	
	@Test(dataProviderClass = TestCaseData.class, dataProvider = "mytestdata", alwaysRun = true)
	public void addCompany(Map<String, Object> data, ITestContext context) throws Throwable {
		RESTUtils restutils = new RESTUtils();
		CompanyAPI company = new CompanyAPI();
		String userid = data.get("Email").toString();
		String password = data.get("Password").toString();
		String url = data.get("Url").toString();

		extenttestinitialize("addCompany", "add Company for accounting user");

		// get authorization token
		String token = "";
//		if (context.getAttributeNames().contains(userid)) {
//			token = (String) context.getAttribute(userid);
//		} else {
			token = restutils.getAuthTokenByUserIdAndPassword(userid, password);
//		}
		String bearerToken = "Bearer " + token;

		// getting companypayload (request body)
		String body = company.generateCompanyApi(data);

		// calling post method
		Response response = restutils.post(url, body, bearerToken);
		System.out.println("response.getStatusCode() " + response.getStatusCode());
		System.out.println("response " + response.getBody().asPrettyString());
		if (response.getStatusCode() == 201) {
			reportlog("addCompany Successful Response: ", "PASS", response.getBody());
		} else {
			reportlog("addCompany Failed Response Code: ", "FAIL", String.valueOf(response.getStatusCode()),response.asPrettyString());
		}
	}

	@Test(dataProviderClass = TestCaseData.class, dataProvider = "mytestdata", alwaysRun = true)
	public void addConflictError(Map<String, Object> data, ITestContext context) throws Throwable {
		RESTUtils restutils = new RESTUtils();
		CompanyAPI company = new CompanyAPI();
		String userid = data.get("Email").toString();
		String password = data.get("Password").toString();
		String url = data.get("Url").toString();

		extenttestinitialize("addConflictError", "add Same Company for accounting user");

		// get authorization token
		String token = "";
		if (context.getAttributeNames().contains(userid)) {
			token = (String) context.getAttribute(userid);
		} else {
			token = restutils.getAuthTokenByUserIdAndPassword(userid, password);
		}
		String bearerToken = "Bearer " + token;

		// getting company payload (request body)
		String body = company.generateCompanyApi(data);

		// calling post method
		Response response = restutils.post(url, body, bearerToken);
		if (response.getStatusCode() == 409) {
			reportlog("addConflictError Successful Response: ", "PASS", response);
		} else {
			reportlog("addConflictError Failed Response Code: ", "FAIL", String.valueOf(response.getStatusCode()),response.asPrettyString());
		}
	}
	

}
