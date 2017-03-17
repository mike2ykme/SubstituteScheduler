package com.icrn.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.icrn.enumerations.RequestStatus;
import com.icrn.enumerations.SchoolStatus;
import com.icrn.service.SubstituteMessaging;
import com.icrn.service.SubstituteService;

public class Substitute {

	private String substituteId;
	private boolean active;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phone;
	private String address;
	private String postalCode;
	private Map<String, SchoolStatus> schoolStatusMap;
	private List<Shift> shiftList;
	private Availability standardAvailability;
	private Availability tempAvailability;

	private SubstituteService substituteService;
//	private SubstituteMessaging substituteMessaging;

	/* 
	 * 
	 * constructors
	 * 
	 */
	private Substitute(){
		
	}
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
			sb.getStandardAvailability() == null ||
			sb.getTempAvailability() == null){
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
		this.schoolStatusMap = sb.getSchoolStatusMap();
		this.substituteService = sb.getSubstituteService();
//		this.substituteMessaging = sb.getSubstituteMessaging();
		this.standardAvailability = sb.getStandardAvailability(); 
		this.tempAvailability = sb.getTempAvailability();
		
	}

	/* 
	 * 
	 * Persistence
	 * 
	 */
	
	public boolean persist() {
		//TODO: Need to ensure data validation is done before sending an object to be persisted
		//TODO: Needs to be implemented
		if(this.substituteId.contains("-")){
			this.substituteId = this.substituteId.replace("-", "");
		}
		return this.substituteService.updateSubstitute(this);
		
	}
		
	/* 
	 * 
	 * Static getter methods for retrieving from DB
	 * 
	 */
	
	public static Substitute loadSubstituteById(SubstituteService subService,String substituteId){
		return subService.getSubstitute(substituteId);
	}
	public static List<Substitute> getSubstitutes(SubstituteService subService){
		return subService.getListSubstitutes();
	}
	public static List<Substitute> getSubstitutesByFirstName(SubstituteService subService,String firstName){
		return subService.getListSubstitutesByFirstName(firstName);
	}
	public static List<Substitute> getSubstitutesByLastName(SubstituteService subService,String lastName){
		return subService.getListSubstitutesByLastName(lastName);
	}

	/* 
	 * 
	 * 
	 * Availability methods
	 * 
	 * 
	 */
	
	public boolean isAvailable(LocalDate day, LocalTime start, LocalTime end) {
		//Using the getter methods since there's no reason to update them.
		
		if(this.getTempAvailabilityCalendar().dayIsBetweenAvailability(day)){
			return this.getTempAvailabilityCalendar().isAvailableDuring(day, start, end);
		}
		else{
			return this.getStandardAvailabilityCalendar().isAvailableDuring(day, start, end);
		}
	}

	public Availability getStandardAvailabilityCalendar() {
		return this.standardAvailability;
	}


	public void setStandardAvailabilityCalendar(Availability availability) {
		this.standardAvailability = availability;
	}

	public Availability getTempAvailabilityCalendar() {
		return this.tempAvailability;
	}



	public void setTempAvailabilityCalendar(Availability availability) {
		this.tempAvailability = availability;

	}
	/*
	 * 
	 * 
	 * Shift methods
	 * 
	 * 
	 */
	public List<Shift> getShiftList() {
		return Collections.unmodifiableList(this.shiftList);
	}
	public void setShiftList(List<Shift> shiftList) {
		this.shiftList = shiftList;
	}
	public List<Shift> getShiftsByStatus(RequestStatus status){
		List<Shift> list = new ArrayList<>();
		this.shiftList.forEach(item ->{
			if(item.getStatus()== status)
				list.add(item);
		});
		return list;
	}
	
	public void addShift(Shift shift){
		this.shiftList.add(shift);
	}
	/* 
	 * 
	 * School Methods
	 * 
	 */
	
	public void addSchool(String schoolId, SchoolStatus status) {
		this.updateSchoolStatus(schoolId, status);
	}
	

	
	public void updateSchoolStatus(String schoolId, SchoolStatus status) {
		if (status == null)
			throw new IllegalArgumentException("status null");
		schoolStatusMap.put(schoolId, status);
	}
	
	public Map<String, SchoolStatus> getSchoolIdList() {
		return Collections.unmodifiableMap(schoolStatusMap);
	}
	/* 
	 * 
	 * Getters and Setters
	 * 
	 */
	
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

	public String getSubstituteId() {
		return substituteId;
	}
	
	/* 
	 * 
	 * 
	 * 
	 * SubstituteBuilider Class
	 * 
	 * 
	 * 
	 */
	public static class SubstituteBuilder {
		private String substituteId;
		private boolean active;
		private String firstName;
		private String lastName;
		private String email;
		private String password;
		private String phone;
		private String address;
		private String postalCode;

		private Availability standardAvailability;
		private Availability tempAvailability;
		private Map<String, SchoolStatus> schoolStatusMap;
		private List<Shift> shiftList;
		private SubstituteService substituteService;
//		private SubstituteMessaging substituteMessaging;
		
		
		/*
		 * 
		 * 
		 * Builder Method
		 * 
		 * 
		 */
		public Substitute build() {
			Substitute sub = null;


			sub = new Substitute(this);
			return sub;
		}
		


		public SubstituteBuilder setSubstituteId(String substituteId) {
			if(substituteId.length() <32) throw new IllegalArgumentException("Invalid Substitute Id");
			this.substituteId = substituteId;
			return this;
		}
		public SubstituteBuilder generateUniqueSubstituteId() {
			this.substituteId = UUID.randomUUID().toString().replace("-", "");
			return this;
		}

		public String getSubstituteId() {
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
		public SubstituteBuilder setSchoolStatusMap(Map<String, SchoolStatus> schoolIdList) {
			this.schoolStatusMap = schoolIdList;
			return this;
		}
		public SubstituteBuilder generateEmptySchoolStatusMap() {
			this.schoolStatusMap = new HashMap<>();
			return this;
		}
		
		public SubstituteBuilder setSubstituteService(SubstituteService substituteService) {
			this.substituteService = substituteService;
			return this;
		}

//		public SubstituteBuilder setSubstituteMessaging(SubstituteMessaging substituteMessaging) {
//			this.substituteMessaging = substituteMessaging;
//			return this;
//		}
		/*
		 * 
		 * 
		 * Getter Methods, separated because this is a builder class
		 * 
		 * 
		 */
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

		public Availability getStandardAvailability() {
			return this.standardAvailability;
		}

		public Availability getTempAvailability() {
			return this.tempAvailability;
		}

		public Map<String, SchoolStatus> getSchoolStatusMap() {
			return this.schoolStatusMap;
		}

		public SubstituteService getSubstituteService() {
			return this.substituteService;
		}

//		public SubstituteMessaging getSubstituteMessaging() {
//			return this.substituteMessaging;
//		}

		public List<Shift> getShiftList() {
			return this.shiftList;
		}

		public SubstituteBuilder setShiftList(List<Shift> shiftList) {
			this.shiftList = shiftList;
			return this;
			
		}
		public SubstituteBuilder generateEmptyShiftList() {
			this.shiftList = new ArrayList<>();
			return this;
			
		}
		/*
		 * 
		 * Availability setter methods
		 * 
		 */


		public SubstituteBuilder setStandardAvailability(Availability standardAvailability) {
			this.standardAvailability = standardAvailability;
			return this;
		}

		public SubstituteBuilder setTempAvailability(Availability tempAvailability) {
			this.tempAvailability = tempAvailability;
			return this;
		}

	}
}
