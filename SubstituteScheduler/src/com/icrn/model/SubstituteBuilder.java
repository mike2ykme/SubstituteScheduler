package com.icrn.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.icrn.service.SubstituteMessaging;
import com.icrn.service.SubstituteService;

public class SubstituteBuilder {
	
	private long substituteId;
	private boolean active;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phone;
	private String address;
	private String postalCode;
	private Map<Long,SchoolStatus> schoolIdList;
	
	@Autowired private SubstituteService substituteService;
	@Autowired private SubstituteMessaging substituteMessaging;
	
	
	public Substitute buildBySubstituteId(long substituteId){
		return substituteService.getSubstitute(substituteId);

	}
	public Substitute build(){
		if(this.schoolIdList == null){
			this.schoolIdList = new HashMap<Long,SchoolStatus>();
		}
		if(substituteId == 0 ||firstName == null ||lastName == null ||email == null ||password == null ||
				phone == null ||address == null ||postalCode == null ||substituteService == null ||substituteMessaging == null){
			throw new IllegalArgumentException("Parameter not set or substituteId equals 0");
		}
			
			
		
		return new Substitute(substituteId, active, firstName, lastName, email, password, phone, address, postalCode, schoolIdList, substituteService, substituteMessaging);
	}
	public SubstituteBuilder setSubstituteId(long substituteId) {
		this.substituteId = substituteId;
		return this;
	}

	public SubstituteBuilder setActive(boolean active) {
		this.active = active;
		return this;
	}

	public SubstituteBuilder setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public SubstituteBuilder setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public SubstituteBuilder setEmail(String email) {
		this.email = email;
		return this;
	}

	public SubstituteBuilder setPassword(String password) {
		this.password = password;
		return this;
	}

	public SubstituteBuilder setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public SubstituteBuilder setAddress(String address) {
		this.address = address;
		return this;
	}

	public SubstituteBuilder setPostalCode(String postalCode) {
		this.postalCode = postalCode;
		return this;
	}

	public SubstituteBuilder setSchoolIdList(Map<Long, SchoolStatus> schoolIdList) {
		this.schoolIdList = schoolIdList;
		return this;
	}

	public SubstituteBuilder setSubstituteService(SubstituteService substituteService) {
		this.substituteService = substituteService;
		return this;
	}

	public SubstituteBuilder setSubstituteMessaging(SubstituteMessaging substituteMessaging) {
		this.substituteMessaging = substituteMessaging;
		return this;
	}
	
	
}
