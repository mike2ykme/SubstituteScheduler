package com.icrn.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.icrn.enumerations.RequestStatus;
import com.icrn.enumerations.SchoolStatus;
import com.icrn.service.SchoolService;

public class School {
	
	private long schoolId;
	private String schoolName;
	private String address;
	private String zipCode;
	
	private List<Request> requests;
	private Map<Long,SchoolStatus> substituteStatusMap;
	private SchoolService service;

	
	public School(long schoolId, String name, String address, String zipCode,SchoolService service,List<Request> requests){
		this.schoolName = name;
		this.address = address;
		this.zipCode = zipCode;
		this.service = service;
		this.schoolId = schoolId;
		this.requests = requests;
	}
	public School(long schoolId, String name, String address, String zipCode,SchoolService service,Request requests){
		this(schoolId,name,address,zipCode,service,new ArrayList<>());
		this.requests.add(requests);

		
	}
	public School(String name, String address, String zipCode,SchoolService service,List<Request> requests){
		this(0,name,address,zipCode,service,new ArrayList<>());

	}
	
	public static List<School> getListSchools(SchoolService service){
		return service.getListSchools();
	}
	public static List<School> getListSchoolsByName(SchoolService service,String name){
		return service.getListSchoolsbyName(name);
	}
	public static List<School> getListSchools(SchoolService service,String zipCode){
		return service.getListSchoolsByZipCode(zipCode);
	}
	
	public void registerSubstitute(Substitute substitute,SchoolStatus status){
		this.substituteStatusMap.put(substitute.getSubstituteId(), status);
	}
	public void registerSubstitute(long substituteId, SchoolStatus status) {
		this.substituteStatusMap.put(substituteId, status);
	}
	
//	substituteStatusMap
	public void persist(){
		if(this.schoolId==0){
			this.schoolId = this.service.createSchool(this);
		}
		else{
			this.service.updateSchool(this);	
		}
		
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public SchoolService getService() {
		return service;
	}
	public void setService(SchoolService service) {
		this.service = service;
	}
	public long getSchoolId() {
		return schoolId;
	}
	public List<Request> getRequests() {
		return requests;
	}
	
	public List<Request> getRequests(LocalDate startDate, LocalTime startTime, LocalTime endTime){
		List<Request> requestList = new ArrayList<>();
		this.requests.forEach(request->{
			if(request.isDuring(startDate, startTime, endTime)){
				requestList.add(request);
			}});
		return Collections.unmodifiableList(requestList);
	}
	public List<Request> getRequests(LocalDate day){
		List<Request> requestList = new ArrayList<>();
		this.requests.forEach(request->{
			if(request.getDay().equals(day))
				requestList.add(request);
			});
		return Collections.unmodifiableList(requestList);
	}
	public List<Request> getRequests(RequestStatus status){
		List<Request> requestList = new ArrayList<>();
		this.requests.forEach(request->{
			if(request.getStatus()== status)
				requestList.add(request);
			});
		return Collections.unmodifiableList(requestList);
	}	
	
	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}
	

	
	
}
