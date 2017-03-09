package com.icrn.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.icrn.enumerations.RequestStatus;
import com.icrn.service.RequestService;

public class Request {
	private long requestId;
	private long substituteId;
	private LocalDate day;
	private LocalTime startTime;
	private LocalTime endTime;
	private RequestStatus status;
	
	private RequestService service;
	
	//Used when pulling out of DB b/c ID will be created at that time. Otherwise just leave to 0
	public Request(long requestId,long substituteId, LocalDate day, LocalTime startTime, LocalTime endTime,RequestStatus status) {
		super();
		this.requestId = requestId;
		this.setSubstituteId(substituteId);
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
	}
	public Request(long substituteId, LocalDate day, LocalTime startTime, LocalTime endTime,RequestStatus status) {
		super();
		this.setSubstituteId(substituteId);
		this.requestId = 0;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
	}

	public long getRequestId() {
		return requestId;
	}

	public LocalDate getDay() {
		return day;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}
	public boolean isDuring(LocalDate startDate, LocalTime startTime, LocalTime endTime) {
		if(this.startTime.isBefore(startTime.plusMinutes(1)) && this.endTime.isAfter(endTime.minusMinutes(1)) && this.day.equals(startDate)){
			return true;
		}
		return false;
	}
	public RequestStatus getStatus() {
		return status;
	}
	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	public long getSubstituteId() {
		return substituteId;
	}
	public void setSubstituteId(long substituteId) {
		this.substituteId = substituteId;
	}
	public RequestService getService() {
		return service;
	}
	public void setService(RequestService service) {
		this.service = service;
	}
	public void setRequestId(long requestId) {
		this.requestId = requestId;
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
}
