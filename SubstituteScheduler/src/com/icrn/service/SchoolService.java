package com.icrn.service;

import java.time.LocalDate;
import java.util.List;

import com.icrn.enumerations.RequestStatus;
import com.icrn.model.Request;
import com.icrn.model.School;
import com.icrn.model.Substitute;

public interface SchoolService {

	void updateSchool(School school);

	List<School> getListSchools();

	List<School> getListSchoolsbyName(String name);

	List<School> getListSchoolsByZipCode(String zipCode);

	void registerSubstituteToSchool(Substitute sub, School school);

	List<Request> getRequestsByDate(LocalDate start, LocalDate end);

	List<Request> getRequestsByStatus(RequestStatus status);

	long createSchool(School school);

}
