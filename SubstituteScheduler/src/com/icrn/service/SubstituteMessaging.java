package com.icrn.service;

import com.icrn.model.Substitute;

public interface SubstituteMessaging {

	boolean messageSubstitute(String email,String message);

	boolean messageSubstitute(Substitute substitute, String message);

}
