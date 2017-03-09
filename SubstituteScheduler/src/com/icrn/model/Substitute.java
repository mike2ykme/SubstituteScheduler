package com.icrn.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icrn.enumerations.SchoolStatus;
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
	private Map<Long, SchoolStatus> schoolStatusMap;
	private List<Availability> standardAvailability;
	private List<Availability> tempAvailability;

	private SubstituteService substituteService;
	private SubstituteMessaging substituteMessaging;

	private Substitute(SubstituteBuilder sb) {
		super();
		if(		 
			sb.getFirstName() == null || 
			sb.getLastName() == null ||
			sb.getEmail() == null ||
			sb.getPassword() == null ||
			sb.getPhone() == null ||
			sb.getAddress() == null ||
			sb.getPostalCode() == null ||
			sb.getSubstituteService() == null ||
			sb.getStandardAvailability() == null){
				throw new IllegalArgumentException("Parameter not set");
		}
		
		this.substituteId = sb.getSubstituteId();
		this.active = sb.isActive();
		this.firstName = sb.getFirstName();
		this.lastName = sb.getLastName();
		this.email = sb.getEmail();
		this.password = sb.getPassword();
		this.phone = sb.getPhone();
		this.address = sb.getAddress();
		this.postalCode = sb.getPostalCode();
		this.schoolStatusMap = sb.getSchoolIdList();
		this.substituteService = sb.getSubstituteService();
		this.substituteMessaging = sb.getSubstituteMessaging();
		this.standardAvailability = sb.getStandardAvailability(); 
		this.tempAvailability = sb.getTempAvailability();
	}

	private static Substitute loadSubstituteById(SubstituteService subService,long substituteId){
		return subService.getSubstitute(substituteId);
	}
	
	private static List<Substitute> getSubstitutes(SubstituteService subService){
		return subService.getListSubstitutes();
	}
	private static List<Substitute> getSubstitutesByFirstName(SubstituteService subService,String firstName){
		return subService.getListSubstitutesByFirstName(firstName);
	}
	private static List<Substitute> getSubstitutesByLastName(SubstituteService subService,String lastName){
		return subService.getListSubstitutesByLastName(lastName);
	}

	public boolean isAvailable(LocalDate date, LocalTime start, LocalTime end) {
		Map<LocalDate, Shift> shiftMap = this.substituteService.GetShiftAvailabilityForSubstitute(substituteId,
				LocalDateTime.of(date, start), LocalDateTime.of(date, end));
		Shift shift = shiftMap.get(date);

		// Is this just using the ternary operator because I can?
		return (shift != null) ? shift.isWithinTime(date, start, end) : false;

	}
	//This returns an inclusive date. So 1-3 will return 1,2,3rd dates
	public Map<LocalDate, Shift> getShiftAvailability(LocalDateTime start, LocalDateTime end) {
		return this.substituteService.GetShiftAvailabilityForSubstitute(this.getSubstituteId(), start, end);
	}

	public boolean persist() {
		if(this.substituteId == 0){
			this.substituteId = this.substituteService.createSubstitute(this);
			return true;
		}
		else{
			return this.substituteService.updateSubstitute(this);	
		}
	}

	public List<Availability> getStandardAvailabilityCalendar() {
		return Collections.unmodifiableList(this.standardAvailability);
	}

	public void addStandardAvailabilityCalendar(Availability availability) {
		if (availability == null)
			throw new IllegalArgumentException("Availability cannot be null");
		this.substituteService.addStandardAvailabilityCalendar(availability);
		this.standardAvailability.add(availability);

	}

	public void setStandardAvailabilityCalendar(List<Availability> availability) {
		this.substituteService.setStandardAvailabilityCalendar(availability);
		this.standardAvailability = availability;
	}

	public List<Availability> getTempAvailabilityCalendar() {
		return Collections.unmodifiableList(this.tempAvailability);
	}

	public void addTempAvailabilityCalendar(Availability availability) {
		if (availability == null)
			throw new IllegalArgumentException("Availability null");
		this.substituteService.addTempAvailabilityCalendar(availability);
		this.tempAvailability.add(availability);
	}

	public void setTempAvailabilityCalendar(List<Availability> availability) {
		this.substituteService.setTempAvailabilityCalendar(availability);
		this.tempAvailability = availability;

	}

	public Map<Long, SchoolStatus> getSchoolIdList() {
		return Collections.unmodifiableMap(schoolStatusMap);
	}

	public void updateSchoolStatus(long schoolId, SchoolStatus status) {
		if (status == null)
			throw new IllegalArgumentException("status null");
		schoolStatusMap.put(schoolId, status);
	}

	public void addSchool(long schoolId, SchoolStatus status) {
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

	public void updatePassword(String password) {
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

	public static class SubstituteBuilder {
		private long substituteId;
		private boolean active;
		private String firstName;
		private String lastName;
		private String email;
		private String password;
		private String phone;
		private String address;
		private String postalCode;

		private List<Availability> standardAvailability;
		private List<Availability> tempAvailability;
		private Map<Long, SchoolStatus> schoolIdList;

		private SubstituteService substituteService;
		private SubstituteMessaging substituteMessaging;
		
		public static Substitute loadSubstituteById(SubstituteService subService,long substituteId){
			return Substitute.loadSubstituteById(subService, substituteId);
		}
		public static List<Substitute> getSubstitutes(SubstituteService subService){
			return Substitute.getSubstitutes(subService);
		}
		public static List<Substitute> getSubstitutesByFirstName(SubstituteService subService,String firstName){
			return Substitute.getSubstitutesByFirstName(subService,firstName);
		}
		public static List<Substitute> getSubstitutesByLastName(SubstituteService subService,String lastName){
			return Substitute.getSubstitutesByLastName(subService,lastName);
		}
		public Substitute build() {
			if (this.schoolIdList == null) {
				this.schoolIdList = new HashMap<Long, SchoolStatus>();
			}

			Substitute sub = new Substitute(this);
			return sub;
		}

		public SubstituteBuilder setSubstituteId(long substituteId) {
			this.substituteId = substituteId;
			return this;
		}

		public long getSubstituteId() {
			return substituteId;
		}

		public SubstituteBuilder setActive(boolean active) {
			this.active = active;
			return this;
		}

		public SubstituteBuilder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public SubstituteBuilder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public SubstituteBuilder setEmail(String email) {
			this.email = email;
			return this;
		}

		public SubstituteBuilder setPassword(String password) {
			this.password = password;
			return this;
		}

		public SubstituteBuilder setPhone(String phone) {
			this.phone = phone;
			return this;
		}

		public SubstituteBuilder setAddress(String address) {
			this.address = address;
			return this;
		}

		public SubstituteBuilder setPostalCode(String postalCode) {
			this.postalCode = postalCode;
			return this;
		}

		public SubstituteBuilder setSchoolIdList(Map<Long, SchoolStatus> schoolIdList) {
			this.schoolIdList = schoolIdList;
			return this;
		}

		public SubstituteBuilder setSubstituteService(SubstituteService substituteService) {
			this.substituteService = substituteService;
			return this;
		}

		public SubstituteBuilder setSubstituteMessaging(SubstituteMessaging substituteMessaging) {
			this.substituteMessaging = substituteMessaging;
			return this;
		}

		public boolean isActive() {
			return active;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public String getEmail() {
			return email;
		}

		public String getPassword() {
			return password;
		}

		public String getPhone() {
			return phone;
		}

		public String getAddress() {
			return address;
		}

		public String getPostalCode() {
			return postalCode;
		}

		public List<Availability> getStandardAvailability() {
			return this.standardAvailability;
		}

		public List<Availability> getTempAvailability() {
			return this.tempAvailability;
		}

		public Map<Long, SchoolStatus> getSchoolIdList() {
			return this.schoolIdList;
		}

		public SubstituteService getSubstituteService() {
			return this.substituteService;
		}

		public SubstituteMessaging getSubstituteMessaging() {
			return this.substituteMessaging;
		}

		public SubstituteBuilder setStandardAvailability(List<Availability> standardAvailability) {
			this.standardAvailability = standardAvailability;
			return this;
		}

		public SubstituteBuilder setTempAvailability(List<Availability> tempAvailability) {
			this.tempAvailability = tempAvailability;
			return this;
		}

		public SubstituteBuilder setStandardAvailability(Availability standardAvailability) {
			this.standardAvailability = new ArrayList<>();
			this.standardAvailability.add(standardAvailability);
			return this;
		}

		public SubstituteBuilder setTempAvailability(Availability tempAvailability) {
			this.tempAvailability = new ArrayList<>();
			this.tempAvailability.add(tempAvailability);
			return this;
		}
	}
}
