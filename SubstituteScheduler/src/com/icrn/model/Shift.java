package com.icrn.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import com.icrn.enumerations.RequestStatus;
import com.icrn.service.RequestService;
import com.icrn.service.ShiftService;

public class Shift {
	private long shiftId;
	private long requestId;
	private long substituteId;
	private LocalDate date;
	private LocalTime start;
	private LocalTime end;
	private RequestStatus status;
//	private long schoolId;
	
	private ShiftService service;
	
	/*
	 * 
	 * 
	 * Constructor
	 * 
	 * 
	 */
	public Shift(long requestId, long substituteId, LocalDate date, LocalTime start, LocalTime end,RequestStatus status) {
		this(0,requestId,substituteId,date,start,end,status);
	}
	public Shift(long shiftId, long requestId, long substituteId, LocalDate date, LocalTime start, LocalTime end,RequestStatus status) {
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
	 * helper
	 * 
	 * 
	 */
	
//	public boolean isWithinShift(LocalDate day, LocalTime startTime, LocalTime endTime){
//		if(this.date.equals(day) &&	
//				start.plusMinutes(-1).isBefore(startTime) && 
//				end.plusMinutes(1).isAfter(endTime)){
//			return true;
//		}
//		
//		return false;
//	}
	/*
	 * 
	 * 
	 * Persistence
	 * 
	 * 
	 */
	public boolean persist() {
		
		//TODO: Need to ensure data validation is done before sending an object to be persisted
		if(this.shiftId == 0){
			//The DB will provide the keyId for this, so when saved, get the key and update this.
			this.shiftId = this.service.createShift(this);
			return true;
		}
		else{
			return this.service.updateShift(this);	
		}
	}
	
	/*
	 * 
	 * 
	 * Getters and Setters
	 * 
	 * 
	 */
	public long getSubstituteId() {
		return substituteId;
	}
	public void setSubstituteId(long substituteId){
		this.substituteId = substituteId;
	}
	public long getShiftId(){
		return shiftId;
	}
	public long getRequestId() {
		return requestId;
	}
	public void setRequestId(long requestId) {
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

//	public long getSchoolId() {
//		return schoolId;
//	}
//
//	public void setSchoolId(long schoolId) {
//		this.schoolId = schoolId;
//	}


}
