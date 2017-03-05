package com.icrn.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Shift {
	private long substituteId;
	private LocalDate date;
	private LocalTime start;
	private LocalTime end;
	
	Shift(long substituteId, LocalDate date, LocalTime start, LocalTime end) {
		super();
		this.substituteId = substituteId;
		this.date = date;
		this.start = start;
		this.end = end;
	}
//	Shift(LocalDate date, LocalTime start, LocalTime end) {
//		super();
//		this.substituteId = substituteId;
//		this.date = date;
//		this.start = start;
//		this.end = end;
//	}
	
	
	public boolean isWithinTime(LocalDate date, LocalTime startTime, LocalTime endTime){
		if(date.equals(date))
			if(start.plusMinutes(-1).isBefore(startTime) && end.plusMinutes(1).isAfter(endTime))
				return true;
				
		return false;
	}
	
	public LocalDateTime getLocalDateTimeStart(){
		return LocalDateTime.of(date, start);
	}
	
	public LocalDateTime getLocalDateTimeEnd(){
		return LocalDateTime.of(date, end);
	}
	
	public long getSubstituteId() {
		return substituteId;
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
}
