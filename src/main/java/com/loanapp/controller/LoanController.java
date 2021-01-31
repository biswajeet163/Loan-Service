package com.loanapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loanapp.exception.InvalidExpiredTokenException;
import com.loanapp.exception.LoanDoesNotExistException;
import com.loanapp.feign.FeignForSecurity;
import com.loanapp.model.Loan;
import com.loanapp.service.LoanService;

@RestController
@CrossOrigin
public class LoanController {
	@Autowired
	private LoanService loanService;

	@Autowired 
	private FeignForSecurity feignForSecurity;

	@GetMapping(value = "/getloan") // ****************************** edit
	public ResponseEntity<List<Loan>> getLoan(@RequestHeader(name = "Authorization") String token,
			@RequestParam("clientType") String clientType) throws InvalidExpiredTokenException {

		try {// Validating
			feignForSecurity.validate(token).getBody();
		} catch (Exception e) {  
			throw new InvalidExpiredTokenException("");
		}

		// Returning Result according to UserType Admin/User
		String[] clientSplit = clientType.split("_");
		List<Loan> allLoanList = new ArrayList<>();

		if (clientSplit[0].equalsIgnoreCase("admin")) { // It is admin
			allLoanList = loanService.getAllLoanListAdmin();

		} else {// It is User
			allLoanList = loanService.getAllLoanListUser(clientSplit[1]);
		}
		return new ResponseEntity<>(allLoanList, HttpStatus.OK);
	}
	
	
	 
	
	

	@GetMapping(value = "/getloan/loannumber") // ***************** edit
	public ResponseEntity<List<Loan>> getLoanByLoanNumber(@RequestHeader(name = "Authorization") String token,
			@RequestParam("clientType") String clientType, @RequestParam("loannumber") String loannumber)
			throws InvalidExpiredTokenException {

		try {// Validating
			feignForSecurity.validate(token).getBody();
		} catch (Exception e) {
			throw new InvalidExpiredTokenException("");
		}

		String[] clientSplit = clientType.split("_");
		List<Loan> allLoanListByLoanNumber = new ArrayList<>();

		if (clientSplit[0].equalsIgnoreCase("admin")) { // It is admin
			allLoanListByLoanNumber = loanService.getLoanByLoanNumberAdmin(loannumber);
		} else {// It is User
			allLoanListByLoanNumber = loanService.getLoanByLoanNumberUser(clientSplit[1], loannumber);
		}
		return new ResponseEntity<>(allLoanListByLoanNumber, HttpStatus.OK);
	}

	 
	
	
	@GetMapping(value = "/getloan/firstname")
	public ResponseEntity<List<Loan>> getLoanByFirstName(@RequestHeader(name = "Authorization") String token,
			@RequestParam("firstname") String firstname) throws InvalidExpiredTokenException {

		try {// Validating
			feignForSecurity.validate(token).getBody();
		} catch (Exception e) {
			throw new InvalidExpiredTokenException("");
		}

		 ///loanListByFirstName = new ArrayList<>();
		 List<Loan> loanListByFirstName = loanService.getLoanByFirstName(firstname);
		return new ResponseEntity<>(loanListByFirstName, HttpStatus.OK);
	}
	
	
	
	

	@GetMapping(value = "/getloan/lastname")
	public ResponseEntity<List<Loan>> getLoanByLastName(@RequestHeader(name = "Authorization") String token,
			@RequestParam("lastname") String lastname) throws InvalidExpiredTokenException {

		try {// Validating
			feignForSecurity.validate(token).getBody();
		} catch (Exception e) {
			throw new InvalidExpiredTokenException("");
		}

		//List<Loan> loanListByLastName = new ArrayList<>();
		List<Loan> loanListByLastName = loanService.getLoanByLastName(lastname);
		return new ResponseEntity<>(loanListByLastName, HttpStatus.OK);
	}
	
	
	

	////////////////////////////////////////////////////////////////////////////////////////////////////////

	@PostMapping(value = "/addnewloan")
	public ResponseEntity<Loan> originateNewLoan(@RequestHeader(name = "Authorization") String token,
			@RequestBody Loan loan) throws InvalidExpiredTokenException {

		try {// Validating
			//feignForSecurity.validate(token).getBody();
		} catch (Exception e) {
			throw new InvalidExpiredTokenException("");
		}

		Loan newLoan = loanService.addNewLoanItem(loan);
		return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
	}
	
	
	
	
	

	@DeleteMapping(value = "/delete")
	public ResponseEntity<String> deleteLoan(@RequestHeader(name = "Authorization") String token,
			@RequestParam(name = "id") String loanId) throws LoanDoesNotExistException, InvalidExpiredTokenException {

		try {// Validating
			feignForSecurity.validate(token).getBody();
		} catch (Exception e) {

			throw new InvalidExpiredTokenException("");
		}

		loanService.deleteLoanItemById(loanId);
		return new ResponseEntity<>(HttpStatus.OK);
	}  
	
	
	
	

	@PutMapping(value = "/update")
	public ResponseEntity<String> updateLoan(@RequestHeader(name = "Authorization") String token,
			@RequestBody Loan loan) throws LoanDoesNotExistException, InvalidExpiredTokenException {

		try {// Validating
			feignForSecurity.validate(token).getBody();
		} catch (Exception e) {

			throw new InvalidExpiredTokenException("");
		}

		loanService.updateLoanItem(loan);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	 
	
	

	// Just For Testing Purpose..............................
	@GetMapping("/hi")
	public ResponseEntity<List<Loan>> getHello(@RequestHeader(name = "Authorization") String token)
			throws InvalidExpiredTokenException {

		try {// Validating
			feignForSecurity.validate(token).getBody();
		} catch (Exception e) {

			throw new InvalidExpiredTokenException("");
		}

		List<Loan> test = loanService.test();
		return new ResponseEntity<>(test, HttpStatus.OK);

	}

}
