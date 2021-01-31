package com.loanapp.cucumber.stepdefination;

import static org.junit.Assert.assertEquals; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DeleteLoanStepDefination extends AbstractSpringTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateLoanStepDefination.class);

	private String loanNumber = null;
	private ResponseEntity<String> response = null; 

	@Given("^the Loan to Be Deleted with LoanNumber \"([^\"]*)\"$")
	public void the_Loan_to_Be_Deleted_with_LoanNumber(String loanNumber) throws Throwable {
		System.out.println("\n\nDelete Item=========================================================================");
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Loan to be deleted with LoanNumber  {} ", loanNumber);
		}
		this.loanNumber = loanNumber; 
	}

	@When("^the client calls \"([^\"]*)\" with the LoanNumber in query Param$")
	public void the_client_calls_with_the_LoanNumber_in_query_Param(String path) throws Throwable {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("path {}", path); 
		}

		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("id", this.loanNumber); // id or loanId
		String url = buildUrl(HOST, PORT, path, null, queryParams);
		LOGGER.info("url {}", url);

		HttpEntity<?> requestEntity = new HttpEntity<>(queryParams, getDefaultHttpHeaders());
		System.out.println("**************** Sending ********************");
		System.out.println(requestEntity );
		System.out.println("**********************************************");
		Thread.sleep(3000);
		response = invokeRESTCall(url, HttpMethod.DELETE, requestEntity);
	}

	@Then("^On successful delete the client receive status code of (\\d+)$")
	public void on_successful_delete_the_client_receive_status_code_of(int statusCode) throws Throwable {
		if (response != null && response.getStatusCode().is2xxSuccessful()) {
			assertEquals(statusCode, response.getStatusCode().value());
		}
		
	}


}
