package com.icrn.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.UnsupportedDataTypeException;

import com.icrn.service.SubstituteMessaging;
import com.icrn.service.SubstituteService;

public class Substitute {

	private long substituteId;
	private boolean active;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phone;
	private String address;
	private String postalCode;
	
	//private List<Map<Long, String>> schoolIdList;
	private Map<Long,String> schoolIdList;
	private SubstituteService substituteService;
	private SubstituteMessaging substituteMessaging;

	public Substitute(SubstituteService substituteService) {
		this.substituteService = substituteService;
	}

	public Substitute(SubstituteService substituteService, long substituteId, String firstName, String lastName,
			boolean active, String password, String phone, String address, String postalCode, String email) {
		super();
		this.substituteId = substituteId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.active = active;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.postalCode = postalCode;
		this.schoolIdList = new HashMap<Long, String>();
		this.substituteService = substituteService;
		this.email = email;
	}
	public Substitute getSubstituteFromId(SubstituteService substituteService, long substituteId){
		return substituteService.getSubstitute(substituteId);
	}
	public Substitute(SubstituteService substituteService, long substituteId) {
		Substitute substitute = substituteService.getSubstitute(substituteId);

		this.substituteService = substituteService;
		this.setSubstituteId(substitute.getSubstituteId());
		this.setFirstName(substitute.getFirstName());
		this.setLastName(substitute.getLastName());
		this.setActive(substitute.isActive());
		this.setPassword(substitute.getPassword());
		this.setPhone(substitute.getPhone());
		this.setAddress(substitute.getAddress());
		this.setPostalCode(substitute.getPostalCode());
		this.setEmail(substitute.getEmail());
		this.setSchoolIdList(substitute.getSchoolIdList());

	}

	public boolean messageSubstitute(String message) {
		return substituteMessaging.messageSubstitute(this.getEmail(), message);
	}

	public long getSubstituteId() {
		return substituteId;
	}

	private void setSubstituteId(long substituteId) {
		this.substituteId = substituteId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	private void setSchoolIdList(Map<Long, String> schoolIdList) {
		this.schoolIdList = schoolIdList;
	}

	public SubstituteService getSubstituteService() {
		return substituteService;
	}

	public void SubstituteService(SubstituteService substituteService) {
		this.substituteService = substituteService;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	//return unmodifiable list of school status
	public Map<Long, String> getSchoolIdList() {
		//return Collections.unmodifiableList(schoolIdList);
		return Collections.unmodifiableMap(schoolIdList);
	}
	public boolean updateSchoolStatus(long schoolId,String status){
		if(status.isEmpty() || status == null) throw new RuntimeException("status was null or empty");
		if(schoolIdList.put(schoolId, status) == null) 
			return false;
		
		return true;
		
	}
}
