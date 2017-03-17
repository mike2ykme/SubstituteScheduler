package com.icrn.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import com.icrn.enumerations.RequestStatus;
import com.icrn.service.RequestService;
import com.icrn.service.ShiftService;

public class Shift {
	private String shiftId;
	private String requestId;
	private String substituteId;
	private LocalDate date;
	private LocalTime start;
	private LocalTime end;
	private RequestStatus status;
//	private String schoolId;
	
	private ShiftService service;
	
	/*
	 * 
	 * 
	 * Constructor
	 * 
	 * 
	 */
	public Shift(String requestId, String substituteId, LocalDate date, LocalTime start, LocalTime end,RequestStatus status) {
		this(UUID.randomUUID().toString().replace("-",""),
				requestId,
				substituteId,
				date,
				start,
				end,
				status
			);
	}
	public Shift(String shiftId, String requestId, String substituteId, LocalDate date, LocalTime start, LocalTime end,RequestStatus status) {
		super();
		this.shiftId = shiftId;
		this.requestId = requestId;
		this.substituteId =substituteId;
		
		this.date = date;
		this.start = start;
		this.end = end;
		this.status = status;

	}
	
	/*
	 * 
	 * 
	 * Persistence
	 * 
	 * 
	 */
	public boolean persist() {
		
		//TODO: Need to ensure data validation is done before sending an object to be persisted
		if(this.shiftId.contains("-")){
			this.shiftId = this.shiftId.replace("-","");
		}
			return this.service.updateShift(this);	
		
	}
	
	/*
	 * 
	 * 
	 * Getters and Setters
	 * 
	 * 
	 */
	public String getSubstituteId() {
		return substituteId;
	}
	public void setSubstituteId(String substituteId){
		this.substituteId = substituteId;
	}
	public String getShiftId(){
		return shiftId;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	public LocalDateTime getLocalDateTimeStart(){
		return LocalDateTime.of(date, start);
	}
	
	public LocalDateTime getLocalDateTimeEnd(){
		return LocalDateTime.of(date, end);
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public LocalTime getStartTime() {
		return start;
	}

	public LocalTime getEndTime() {
		return end;
	}
	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

//	public String getSchoolId() {
//		return schoolId;
//	}
//
//	public void setSchoolId(String schoolId) {
//		this.schoolId = schoolId;
//	}


}
