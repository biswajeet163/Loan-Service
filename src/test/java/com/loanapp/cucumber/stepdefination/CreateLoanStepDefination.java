package com.loanapp.cucumber.stepdefination;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CreateLoanStepDefination extends AbstractSpringTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateLoanStepDefination.class);
 
	private String memberName = null;
	private String loanNumber=null;// loanType=null, loanTerm=null, amount=0, originDate=null, endDate=null}
	private ResponseEntity<String> response = null;

	@Given("^the Loan with LoanNumber \"([^\"]*)\" and  MemberName  \"([^\"]*)\" and other details as well of Loan Item$")
	public void the_Loan_with_LoanNumber_and_MemberName_and_other_details_as_well_of_Loan_Item(String loanNumber,
			String memberName) throws Throwable {
		System.out.println("\n\nPost Loan Item========================================================================");
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Loan to be saved with LoanNumber {} and Loan MemberName {}", loanNumber, memberName);
		}
		this.loanNumber = loanNumber; 
		this.memberName = memberName;
	} 

	@When("^the client calls \"([^\"]*)\" with the given details$")
	public void the_client_calls_with_the_given_details(String path) throws Throwable {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("path {}", path);
		}
		String url = buildUrl(HOST, PORT, path);
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("memberName", this.memberName);
		requestMap.put("loanNumber", this.loanNumber);
		HttpEntity<?> requestEntity = new HttpEntity<>(requestMap, getDefaultHttpHeaders());
		System.out.println("**************** Sending ********************");
		System.out.println(requestEntity );
		System.out.println("**********************************************");
		response = invokeRESTCall(url, HttpMethod.POST, requestEntity);
	}
	
	@Then("^the client receives status code of (\\d+)$")
	public void the_client_receives_status_code_of(int statusCode) throws Throwable {
		if (response != null && response.getStatusCode().is2xxSuccessful()) {
			assertEquals(statusCode, response.getStatusCode().value());
		}
	}

	@Then("^the response contains MemberName \"([^\"]*)\"$")
	public void the_response_contains_MemberName(String memberName) throws Throwable {
		if (response != null && response.getStatusCode().is2xxSuccessful()) {
			String responseBody = response.getBody();
			com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
			Map<String, String> responseMap = mapper.readValue(responseBody, Map.class);
			System.out.println("****************** Recieved ******************");
			System.out.println(responseMap );
			System.out.println("**********************************************");
			assertEquals(memberName, responseMap.get("memberName"));
		}
	}
	
	
	 
	
	  



}
