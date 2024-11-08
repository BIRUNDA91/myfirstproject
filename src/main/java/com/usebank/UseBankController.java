package com.usebank;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/Usebankreceiver")
public class UseBankController {
	@Autowired
	RestTemplate rest;

	@GetMapping(value = "/getbranchbyifsc")
	public List<Customer> getbranchbyifsc() {

		String url1="http://localhost:8091/sender1/getAll";
		String url2 = "http://localhost:8090/sender/getbranch/";

		ResponseEntity<List<Customer>> customdata = rest.exchange(url1, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Customer>>() {});
		List<Customer> data = customdata.getBody();
		data.forEach(x ->	 {
			String ifsc = x.getIfsccode();
			ResponseEntity<String> branchdata = rest.exchange(url2 + ifsc, HttpMethod.GET, null, String.class);
			String branchname=branchdata.getBody();
			if(x.getIfsccode()!=null)
			{
			 x.setBranch(branchname);
			}
		});
		//List<String> data1=data.stream().map(x->x.getBranch()).collect(Collectors.toList());
		System.out.println(data);
		return data;
	}
}


