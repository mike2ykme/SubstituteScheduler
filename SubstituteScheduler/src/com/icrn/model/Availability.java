package com.icrn.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.icrn.enumerations.StartEndEnum;

public class Availability {
	private final LocalDate startDate;
	private final LocalDate endDate;
	private final Map<DayOfWeek,Map<StartEndEnum,LocalTime>> dailyAvailability;
	
	private Availability(AvailabilityBuilder ab){
		this.startDate = ab.getStartDate();
		this.endDate = ab.getEndDate();
		this.dailyAvailability = ab.getDailyAvailability();
	}
	/*
	 * 
	 * Getters no setters, if a substitute needs to have it's availability changed, then it should be given a new availability and that should be persisted in the DB
	 * 
	 */

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}
	
	public Map<DayOfWeek, Map<StartEndEnum, LocalTime>> getDailyAvailability() {
		return Collections.unmodifiableMap(dailyAvailability);
	}
	

	/*
	 * 
	 * Validation Methods
	 * 
	 */
	
	public boolean isAvailableDuring(LocalDate day, LocalTime startTime, LocalTime endTime){
		
		if(this.dayIsBetweenAvailability(day)){
			DayOfWeek weekDay = day.getDayOfWeek();
			LocalTime start = this.getDailyAvailability().get(weekDay).get(StartEndEnum.START);
			LocalTime end = this.getDailyAvailability().get(weekDay).get(StartEndEnum.END);
			
			if(start.plusMinutes(-1).isBefore(startTime) && end.plusMinutes(1).isAfter(endTime))
				return true;
		}
		
				
		return false;
	}
	public boolean dayIsBetweenAvailability(LocalDate day){
		if(	(this.startDate.equals(day) || this.startDate.isBefore(day)) &&
			(this.endDate.equals(day) || this.endDate.isAfter(day))){ 
			return true;
		}
		return false;
	}
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * AvailabilityBuilder
	 * 
	 * 
	 * 
	 * 
	 */
	
	public static class AvailabilityBuilder{
		private LocalDate startDate;
		private LocalDate endDate;
		private Map<DayOfWeek,Map<StartEndEnum,LocalTime>> dailyAvailability;
		
		public Availability build(){
			Availability availability = new Availability(this);
			if(availability.getStartDate() == null || 
					availability.getEndDate() == null  ||
					availability.getDailyAvailability() == null ){
				throw new IllegalStateException("Value has not been set");
			}
			
			return availability;
		}
		public static Availability getEmptyAvailability(){
			AvailabilityBuilder ab = new AvailabilityBuilder();
			ab.setEndDateAvailability(LocalDate.ofEpochDay(0));
			ab.setStartDateAvailability(LocalDate.ofEpochDay(0));
			return new Availability(ab);
		}
		

		public AvailabilityBuilder() {
			this.dailyAvailability = new HashMap<>();
		}
		
		public Map<DayOfWeek, Map<StartEndEnum, LocalTime>> getDailyAvailability() {
			return this.dailyAvailability;
		}
		public LocalDate getStartDate() {
			return this.startDate;
		}
		public LocalDate getEndDate() {
			return this.endDate;
		}
		public AvailabilityBuilder setStartDateAvailability(LocalDate startDate){
			this.startDate = startDate;
			return this;
		}
		
		public AvailabilityBuilder setEndDateAvailability(LocalDate endDate){
			this.endDate = endDate;
			return this;
		}
		
		
		/*
		 *
		 * 
		 * 
		 * Daily methods
		 * 
		 * 
		 * 
		 */
		
		public AvailabilityBuilder setMondayAvailability(LocalTime start, LocalTime end){
			if(start.isAfter(end))
				throw new IllegalStateException("Start time is scheduled after end time");
			
			this.dailyAvailability.put(DayOfWeek.MONDAY, new HashMap<StartEndEnum,LocalTime>());
			this.dailyAvailability.get(DayOfWeek.MONDAY).put(StartEndEnum.START, start);
			this.dailyAvailability.get(DayOfWeek.MONDAY).put(StartEndEnum.END, end);
			return this;
		}
		
		public AvailabilityBuilder setTuesdayAvailability(LocalTime start, LocalTime end){
			if(start.isAfter(end))
				throw new IllegalStateException("Start time is scheduled after end time");
			
			this.dailyAvailability.put(DayOfWeek.TUESDAY, new HashMap<StartEndEnum,LocalTime>());
			this.dailyAvailability.get(DayOfWeek.TUESDAY).put(StartEndEnum.START, start);
			this.dailyAvailability.get(DayOfWeek.TUESDAY).put(StartEndEnum.END, end);
			return this;
		}
		
		public AvailabilityBuilder setWednesdayAvailability(LocalTime start, LocalTime end){
			if(start.isAfter(end))
				throw new IllegalStateException("Start time is scheduled after end time");
			
			this.dailyAvailability.put(DayOfWeek.WEDNESDAY, new HashMap<StartEndEnum,LocalTime>());
			this.dailyAvailability.get(DayOfWeek.WEDNESDAY).put(StartEndEnum.START, start);
			this.dailyAvailability.get(DayOfWeek.WEDNESDAY).put(StartEndEnum.END, end);
			return this;
		}
		
		public AvailabilityBuilder setThursdayAvailability(LocalTime start, LocalTime end){
			if(start.isAfter(end))
				throw new IllegalStateException("Start time is scheduled after end time");
			
			this.dailyAvailability.put(DayOfWeek.THURSDAY, new HashMap<StartEndEnum,LocalTime>());
			this.dailyAvailability.get(DayOfWeek.THURSDAY).put(StartEndEnum.START, start);
			this.dailyAvailability.get(DayOfWeek.THURSDAY).put(StartEndEnum.END, end);
			return this;
		}
		
		public AvailabilityBuilder setFridayAvailability(LocalTime start, LocalTime end){
			if(start.isAfter(end))
				throw new IllegalStateException("Start time is scheduled after end time");
			
			this.dailyAvailability.put(DayOfWeek.FRIDAY, new HashMap<StartEndEnum,LocalTime>());
			this.dailyAvailability.get(DayOfWeek.FRIDAY).put(StartEndEnum.START, start);
			this.dailyAvailability.get(DayOfWeek.FRIDAY).put(StartEndEnum.END, end);
			return this;
		}
		
		public AvailabilityBuilder setSaturdayAvailability(LocalTime start, LocalTime end){
			if(start.isAfter(end))
				throw new IllegalStateException("Start time is scheduled after end time");
			
			this.dailyAvailability.put(DayOfWeek.SATURDAY, new HashMap<StartEndEnum,LocalTime>());
			this.dailyAvailability.get(DayOfWeek.SATURDAY).put(StartEndEnum.START, start);
			this.dailyAvailability.get(DayOfWeek.SATURDAY).put(StartEndEnum.END, end);
			return this;
		}
		
		public AvailabilityBuilder setSundayAvailability(LocalTime start, LocalTime end){
			if(start.isAfter(end))
				throw new IllegalStateException("Start time is scheduled after end time");
			
			this.dailyAvailability.put(DayOfWeek.SUNDAY, new HashMap<StartEndEnum,LocalTime>());
			this.dailyAvailability.get(DayOfWeek.SUNDAY).put(StartEndEnum.START, start);
			this.dailyAvailability.get(DayOfWeek.SUNDAY).put(StartEndEnum.END, end);
			return this;
		}
	}

}
