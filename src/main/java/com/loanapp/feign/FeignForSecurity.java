package com.loanapp.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.loanapp.model.AuthResponse;

@FeignClient(name = "Loan-Security", url = "${auth.valid}")
public interface FeignForSecurity {

	@GetMapping("/validate")
	public ResponseEntity<AuthResponse> validate(@RequestHeader(name = "Authorization") String tokenObtained);

}
