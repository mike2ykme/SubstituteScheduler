package com.icrn.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.icrn.enumerations.SchoolStatus;
import com.icrn.model.Availability;
import com.icrn.model.Shift;
import com.icrn.model.Substitute;

public interface SubstituteService {

	Substitute getSubstitute(long substituteId);

	boolean updateSubstitute(long substituteId, Substitute substitute);

	Map<LocalDate, Shift> GetShiftAvailabilityForSubstitute(long substituteId, LocalDateTime start, LocalDateTime end);

	boolean updateSubstitute(Substitute substitute);

	void updateStandardAvailabilityCalendar(Availability availability);
	
	void updateTempAvailabilityCalendar(Availability availability);

	void setTempAvailabilityCalendar(Availability availability);

	void setStandardAvailabilityCalendar(Availability availability);

	void addTempAvailabilityCalendar(Availability availability);

	void addStandardAvailabilityCalendar(Availability availability);

	List<Availability> getStandardAvailability(long substituteId);

	List<Availability> getTempAvailability(long substituteId);

	Map<Long, SchoolStatus> getSchoolAvailability(long substituteId);

	List<Substitute> getListSubstitutes();

	List<Substitute> getListSubstitutesByLastName(String lastName);

	List<Substitute> getListSubstitutesByFirstName(String firstName);

	long createSubstitute(Substitute substitute);
}
