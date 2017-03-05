package com.icrn.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Map;

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
	private Map<Long,SchoolStatus> schoolIdList;

	private SubstituteService substituteService;
	private SubstituteMessaging substituteMessaging;
	

	Substitute(long substituteId, boolean active, String firstName, String lastName, String email, String password,
			String phone, String address, String postalCode, Map<Long, SchoolStatus> schoolIdList,
			com.icrn.service.SubstituteService substituteService, SubstituteMessaging substituteMessaging) {
		super();
		this.substituteId = substituteId;
		this.active = active;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.postalCode = postalCode;
		this.schoolIdList = schoolIdList;
		this.substituteService = substituteService;
		this.substituteMessaging = substituteMessaging;
	}
	
	public boolean isAvailable(LocalDate date, LocalTime start, LocalTime end){
		Map<LocalDate,Shift> shiftMap = this.substituteService.GetAvailabilityForSubstitute(substituteId, LocalDateTime.of(date, start), LocalDateTime.of(date, end));
		Shift shift = shiftMap.get(date); 
		
		//Is this just using the ternary operator because I can?
		return (shift != null) ? shift.isWithinTime(date, start, end) : false;
		
	}
	public Map<LocalDate,Shift> getShiftAvailability(LocalDateTime start, LocalDateTime end){
		return this.substituteService.GetAvailabilityForSubstitute(this.getSubstituteId(),start,end);
	}
	
	public boolean saveSubstitute(){
		return this.substituteService.updateSubstitute(this);
		
	}
	
	public void getAvailabilityCalendar(){
		//TODO
	}
	
	public void setAvailabilityCalendar(){
		//TODO
	}
	
	public void getVacationAvailabilityCalendar(){
		//TODO
	}
	
	public void setVacationAvailabilityCalendar(){
		//TODO
	}

	public Map<Long, SchoolStatus> getSchoolIdList() {
		return Collections.unmodifiableMap(schoolIdList);
	}
	public void updateSchoolStatus(long schoolId,SchoolStatus status){
		if(status == null) throw new IllegalArgumentException("status null");
		schoolIdList.put(schoolId, status);
	}
	
	public void addSchool(long schoolId,SchoolStatus status){
		this.updateSchoolStatus(schoolId, status);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
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



	public long getSubstituteId() {
		return substituteId;
	}
	
}
