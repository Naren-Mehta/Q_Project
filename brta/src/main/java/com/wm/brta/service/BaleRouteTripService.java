package com.wm.brta.service;

import java.util.HashSet;
import java.util.List;

import com.wm.brta.dto.ActivityListInputPayload;
import com.wm.brta.dto.BalePickupMobileDetailsForSC;
import com.wm.brta.dto.Commodity;
import com.wm.brta.dto.CompleteTripInputPayLoad;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.PickupDetails;

public interface BaleRouteTripService {
	

	public Integer addTripDetailsNew(PickupDetails pickupDetails);
	public List<Destination> getAllDestinations(List<Integer> storeIdList);
	
	public void completeTrip(CompleteTripInputPayLoad completeTripPayLoad);

	public HashSet<Destination> getSavedOnlyTrips(
			ActivityListInputPayload inputPayload); 
	
	public List<PickupDetails> getSavedOnlyTripsNew(
			ActivityListInputPayload inputPayload);
	public Integer saveCheckInCheckOut(BalePickupMobileDetailsForSC balePickupMobileDetailsForSC);
	

	public HashSet<Commodity> retriveBalesCountForSC(BalePickupMobileDetailsForSC balePickupMobileDetailsForSC);

}
