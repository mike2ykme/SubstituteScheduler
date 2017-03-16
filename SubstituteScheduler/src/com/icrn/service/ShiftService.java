package com.icrn.service;

import com.icrn.model.Shift;

public interface ShiftService {

	public long createShift(Shift shift);

	public boolean updateShift(Shift shift);
}
