package com.icrn.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.icrn.model.Availability;
import com.icrn.model.Shift;
import com.icrn.model.Substitute;

public interface SubstituteService {

	Substitute getSubstitute(long substituteId);

	boolean updateSubstitute(long substituteId, Substitute substitute);

	Map<LocalDate, Shift> GetAvailabilityForSubstitute(long substituteId, LocalDateTime start, LocalDateTime end);

	boolean updateSubstitute(Substitute substitute);

	void updateStandardAvailabilityCalendar(Availability availability);
	
	void updateTempAvailabilityCalendar(Availability availability);

	void setTempAvailabilityCalendar(List<Availability> availability);

	void setStandardAvailabilityCalendar(List<Availability> availability);

	void addTempAvailabilityCalendar(Availability availability);

	void addStandardAvailabilityCalendar(Availability availability);
}
