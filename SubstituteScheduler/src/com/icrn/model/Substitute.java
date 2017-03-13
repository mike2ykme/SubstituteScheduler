package com.icrn.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icrn.enumerations.RequestStatus;
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
	private List<Shift> shiftList;
	private Availability standardAvailability;
	private Availability tempAvailability;

	private SubstituteService substituteService;
	private SubstituteMessaging substituteMessaging;

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
		this.substituteMessaging = sb.getSubstituteMessaging();
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
		if(this.substituteId == 0){
			//The DB will provide the keyId for this, so when saved, get the key and update this.
			this.substituteId = this.substituteService.createSubstitute(this);
			
			this.shiftList.forEach(shift ->{
				if(shift.getSubstituteId() != this.substituteId)
					shift.setSubstituteId(this.substituteId);
			});
			
			return true;
		}
		else{
			return this.substituteService.updateSubstitute(this);	
		}
	}
		
	/* 
	 * 
	 * Static getter methods for retrieving from DB
	 * 
	 */
	
	public static Substitute loadSubstituteById(SubstituteService subService,long substituteId){
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
	
	//This returns an inclusive date. So 1-3 will return 1,2,3rd dates
	public Map<LocalDate, Shift> getShiftAvailability(LocalDateTime start, LocalDateTime end) {
		return this.substituteService.GetShiftAvailabilityForSubstitute(this.getSubstituteId(), start, end);
	}

	public Availability getStandardAvailabilityCalendar() {
		return this.standardAvailability;
	}


	public void setStandardAvailabilityCalendar(Availability availability) {
		this.substituteService.setStandardAvailabilityCalendar(availability);
		this.standardAvailability = availability;
	}

	public Availability getTempAvailabilityCalendar() {
		return this.tempAvailability;
	}



	public void setTempAvailabilityCalendar(Availability availability) {
		this.substituteService.setTempAvailabilityCalendar(availability);
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
	
	/* 
	 * 
	 * School Methods
	 * 
	 */
	
	public void addSchool(long schoolId, SchoolStatus status) {
		this.updateSchoolStatus(schoolId, status);
	}
	
	public void addShift(Shift shift){
		this.shiftList.add(shift);
	}
	
	public void updateSchoolStatus(long schoolId, SchoolStatus status) {
		if (status == null)
			throw new IllegalArgumentException("status null");
		schoolStatusMap.put(schoolId, status);
	}
	
	public Map<Long, SchoolStatus> getSchoolIdList() {
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

	public long getSubstituteId() {
		return substituteId;
	}
	
	/* 
	 * 
	 * 
	 * 
	 * SubstituteBuilider
	 * 
	 * 
	 * 
	 */
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

		private Availability standardAvailability;
		private Availability tempAvailability;
		private Map<Long, SchoolStatus> schoolStatusMap;
		private List<Shift> shiftList;
		private SubstituteService substituteService;
		private SubstituteMessaging substituteMessaging;
		
		
		/*
		 * 
		 * 
		 * Builder Method
		 * 
		 * 
		 */
		public Substitute build() {
			Substitute sub = null;
			if (this.schoolStatusMap == null) {
				this.schoolStatusMap = new HashMap<Long, SchoolStatus>();
			}
			if (this.shiftList == null) {
				this.shiftList= new ArrayList<>();
			}

			sub = new Substitute(this);
			return sub;
		}
		
		/*
		 * 
		 * 
		 * 
		 
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
		*/

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
		//If not used then it will create an empty HashMap
		public SubstituteBuilder setSchoolStatusMap(Map<Long, SchoolStatus> schoolIdList) {
			this.schoolStatusMap = schoolIdList;
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

		public Map<Long, SchoolStatus> getSchoolStatusMap() {
			return this.schoolStatusMap;
		}

		public SubstituteService getSubstituteService() {
			return this.substituteService;
		}

		public SubstituteMessaging getSubstituteMessaging() {
			return this.substituteMessaging;
		}

		public List<Shift> getShiftList() {
			return this.shiftList;
		}

		public SubstituteBuilder setShiftList(List<Shift> shiftList) {
			this.shiftList = shiftList;
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
