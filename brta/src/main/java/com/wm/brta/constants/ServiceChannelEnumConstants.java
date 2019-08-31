package com.wm.brta.constants;

public enum ServiceChannelEnumConstants {
	
	OnSite("On Site"),
    INCOMPLETE("INCOMPLETE"),
    Complete("Complete"),
    DispatchConfirmed("Dispatch Confirmed"),
	PendingConfirmation("Pending Confirmation"),
	Confirmed("Confirmed");
    private String displayName;

    ServiceChannelEnumConstants(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() { return displayName; }

    // Optionally and/or additionally, toString.
    @Override public String toString() { return displayName; }
}

