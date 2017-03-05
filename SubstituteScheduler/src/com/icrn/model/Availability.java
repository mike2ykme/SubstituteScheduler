package com.icrn.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Availability {
	//scheduleId is used when updating records in the DB. This way if a current schedule needs to be removed/added/updated it can be
	private final long scheduleId;
	private final long substituteId;
	private final LocalDate startDate;
	private final LocalDate endDate;
	private final Map<DayOfWeek,Map<StartEndEnum,LocalTime>> dailyAvailability;
	
	private Availability(AvailabilityBuilder ab){
		this.startDate = ab.getStartDate();
		this.endDate = ab.getEndDate();
		this.dailyAvailability = ab.getDailyAvailability();
		this.scheduleId = ab.getScheduleId();
		this.substituteId = ab.getSubstituteId();
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public long getScheduleId(){
		return scheduleId;
		
	}
	
	public Map<DayOfWeek, Map<StartEndEnum, LocalTime>> getDailyAvailability() {
		return Collections.unmodifiableMap(dailyAvailability);
	}
	
	public static class AvailabilityBuilder{
		private long scheduleId = 0;
		private long substituteId;
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
//			if(this.dailyAvailability.get(DayOfWeek.MONDAY) == null || this.dailyAvailability.get(DayOfWeek.TUESDAY) == null|| this.dailyAvailability.get(DayOfWeek.WEDNESDAY) == null ||
//					this.dailyAvailability.get(DayOfWeek.THURSDAY) == null || this.dailyAvailability.get(DayOfWeek.FRIDAY) == null ||this.dailyAvailability.get(DayOfWeek.SATURDAY) == null ||
//					this.dailyAvailability.get(DayOfWeek.SUNDAY) == null){
//				throw new IllegalStateException("Daily value has not been set");
//			}
			
			return availability;
		}
		
		public long getSubstituteId() {
			// TODO Auto-generated method stub
			return this.substituteId;
		}
		public AvailabilityBuilder 
		setSubstituteId(long id){
			this.substituteId = id;
			return this;
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

		public long getScheduleId() {
			return scheduleId;
		}

		public void setScheduleId(long scheduleId) {
			this.scheduleId = scheduleId;
		}
	}

}
