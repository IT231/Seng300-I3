package com.thelocalmarketplace.software;

import com.thelocalmarketplace.software.weight.WeightListener;
import com.thelocalmarketplace.hardware.AttendantStation;

public class callAttendant {
	private AttendantStation Attedant;
	
	public callAttendant(AttendantStation Attendant) {
		this.Attendant = Attendant;
	}
	public AttendantStation getStation() {
		return Attendant;
	}

}
