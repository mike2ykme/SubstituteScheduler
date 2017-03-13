package com.icrn.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.icrn.enumerations.RequestStatus;
import com.icrn.service.RequestService;

public class Request {
	private long requestId;
	private Map<Long,RequestStatus> substituteIdStatusMap; //This will store the SubId-> how they responded
	private LocalDate day;
	private LocalTime startTime;
	private LocalTime endTime;
	private RequestStatus status;
	private String notes;
	
	private RequestService service;
	
	/* 
	 * 
	 * 
	 * constructors
	 * 
	 * 
	 */
	
	//Used when pulling out of DB b/c ID will be created at that time. Otherwise just leave to 0
	public Request(long requestId,long substituteId,RequestStatus subStatus, LocalDate day, LocalTime startTime, LocalTime endTime,RequestStatus status, String notes) {
		this();
		this.requestId = requestId;
		this.substituteIdStatusMap.put(substituteId, subStatus);
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.notes = notes;
	}
	
	public Request(long substituteId,RequestStatus subStatus, LocalDate day, LocalTime startTime, LocalTime endTime,RequestStatus status, String notes) {
		this();
		this.substituteIdStatusMap.put(substituteId, subStatus);
		this.requestId = 0;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.notes = notes;
	}
	
	public Request(Map<Long,RequestStatus> substituteIdStatusMap, LocalDate day, LocalTime startTime, LocalTime endTime,RequestStatus status, String notes) {
		this.substituteIdStatusMap = new HashMap<>(substituteIdStatusMap);
		this.requestId = 0;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.notes = notes;
	}
	
		//Used to initialize the HashMap for the substituteIds
	public Request(){
		this.substituteIdStatusMap = new HashMap<>();
	}
	/*
	 * 
	 * 
	 * Persistence 
	 * 
	 * 
	 */
	
	public void persist(){
		if(this.requestId ==0){
			this.requestId = this.service.createRequest(this);
		}
		else
			this.service.updateRequest(this);
	}
	
	/*
	 * 
	 * For the substituteIds Map
	 * 
	 */
	
	public Map<Long,RequestStatus> getSubstituteIdStatusMap(){
		return Collections.unmodifiableMap(this.substituteIdStatusMap);
	}
	
	public void setSubstituteIdStatusMap(Map<Long,RequestStatus> substituteIdStatusMap){
		if(substituteIdStatusMap == null){
			throw new IllegalArgumentException("substituteIdStatusMap cannot be null");
		}
		this.substituteIdStatusMap = new HashMap<>(substituteIdStatusMap);
		
		
	}
	//This returns like map does, previous value associated with key or null if nothing was there.
	public RequestStatus addSubstituteId(long substituteId, RequestStatus status){
		if(status ==null || substituteId == 0) 
			throw new IllegalArgumentException("status cannot be null and substituteId cannot be 0");
		else
			return this.substituteIdStatusMap.put(substituteId, status);
	}
	
	/*
	 * 
	 * 
	 * Request variable getter and setters
	 * 
	 * 
	 */
	
	public long getRequestId() {
		return requestId;
	}
	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public RequestStatus getStatus() {
		return this.status;
	}
	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public RequestService getService() {
		return this.service;
	}
	public void setService(RequestService service) {
		this.service = service;
	}
	
	/*
	 * Date and Time data
	 * 
	 */
	public LocalDate getDay() {
		return this.day;
	}
	public LocalTime getStartTime() {
		return this.startTime;
	}
	public LocalTime getEndTime() {
		return this.endTime;
	}
	public void setDay(LocalDate day) {
		this.day = day;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/*
	 * 
	 * Validation methods
	 * 
	 */
	
	public boolean isDuring(LocalDate startDate, LocalTime startTime, LocalTime endTime) {
		if(this.day.equals(startDate) &&
				this.startTime.isBefore(startTime.plusMinutes(1)) && 
				this.endTime.isAfter(endTime.minusMinutes(1))){
			return true;
		}
		else
			return false;
	}

}
