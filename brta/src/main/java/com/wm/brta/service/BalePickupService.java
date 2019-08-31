package com.wm.brta.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wm.brta.dao.AssignDriverDTO;
import com.wm.brta.dao.GenerateAssignmentDriverPopup;
/*import com.wm.brta.data.domain.Pickupsview;*/
/*import com.wm.brta.data.dto.BalePickupFilterDTO;
import com.wm.brta.data.dto.BalePickupUpdateDTO;*/
import com.wm.brta.domain.BalePickupAssignment;
import com.wm.brta.domain.BalePickupSummaryView;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.DriverSupplierPickupsView;
import com.wm.brta.domain.EditPendingReportDetails;
import com.wm.brta.domain.Frequency;
import com.wm.brta.domain.PendingReport;
import com.wm.brta.domain.StoreSupplierListView;
import com.wm.brta.dto.ActivityListInputPayload;
import com.wm.brta.dto.AssignmentFilterDTO;
import com.wm.brta.dto.BalePickupAssignmentDTO;
import com.wm.brta.dto.BalePickupFilterDTO;
import com.wm.brta.dto.BalePickupMobileDetailsForSC;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.EditPendingReportDTO;
import com.wm.brta.dto.OnCallInputPayload;
import com.wm.brta.dto.PendingReportDTO;
import com.wm.brta.dto.PendingStoreReportDTO;
import com.wm.brta.dto.PickupDetails;
import com.wm.brta.dto.PickupDetailsFromExcel;
import com.wm.brta.dto.PickupDetailsWithMasterSets;
import com.wm.brta.dto.SaveBalePickupFromPendingReport;
import com.wm.brta.dto.SupplierPickupDetailsMobileDTO;
import com.wm.brta.dto.SupplierPickupsMobileDTO;
import com.wm.brta.dto.UserDTO;

public interface BalePickupService {
	
	public HashSet<Destination>getAllDestinations(ActivityListInputPayload activityListPayload);
	public List<PickupDetails> getAllDestinationsNew(ActivityListInputPayload activityListPayload);
	public List<PickupDetails> getAllPickUpsFromSupplier(SupplierPickupsMobileDTO supplierPickupsMobileDTO);
	
	public List<PickupDetails> getAllSavedPickUps(SupplierPickupsMobileDTO supplierPickupsMobileDTO);
	public List<Frequency> getAllFrequency();
	
	public PickupDetailsWithMasterSets getPickupDetailsForSupplier(SupplierPickupDetailsMobileDTO supplierPickupDetailsMobileDTO);

	public Integer deleteSCTripRecord(BalePickupMobileDetailsForSC sc);
	public PickupDetailsWithMasterSets getPickupDetails(PickupDetails pickup);
	public void addBalePickupAssignments(BalePickupAssignmentDTO assignmentDTO, UserDTO user);
	public Set<BalePickupAssignment> getPickupAssignments(AssignmentFilterDTO assignmentFilter);
	//for stopre supplier list
	public List<StoreSupplierListView> getSupplierStoreList(AssignDriverDTO assignDriverDTO);
	//modifed by deepak
	public List<BalePickupSummaryView> getPickUps(BalePickupFilterDTO balePickupFilter);
	
	//created ny Naren for DA Report
	public List<BalePickupSummaryView> getPickUpsForDAR(BalePickupFilterDTO balePickupFilter);

	public Set<PendingReportDTO> getAllPendingPickups(PendingStoreReportDTO pendingStoreReportDTO);
	
	public List<EditPendingReportDetails> getAllBalePickupDates(EditPendingReportDTO editPendingReportDTO);
	public Map savePickupFromPendingStore(SaveBalePickupFromPendingReport saveBalePickupFromPendingReport);

	public List<String> getBalePickupImages(Integer balePickupId);
	
	//by deepak Driver Dashboard
	 public List<DriverSupplierPickupsView> showDriverDashboard(AssignDriverDTO assignDriverDTO);

	public void updateBalePickupAssignmentstoAssignActivity(List<BalePickupAssignment> assignments, UserDTO user);
	//by deepak for driverpopup == modified by naren void to boolean
	public Boolean  updateStoreSupplierDriver(GenerateAssignmentDriverPopup generateAssignmentDriverPopup);
	public void  updateAssignmentDates(GenerateAssignmentDriverPopup generateAssignmentDriverPopup);

	public HashSet<Destination> getAllPickupsForOnCall(OnCallInputPayload inputPayLoad);
	
	public List<PickupDetails> getAllPickupsForOnCallNew(OnCallInputPayload inputPayLoad);

	
	public Set<BalePickupAssignment> getPickupAssignmentsForCustomerSite(
			CustomerSite customerSite);
	public String assignNewDriver(BalePickupAssignment assignment) throws Exception;
	
	
	public Map<Integer,String> createPickupDetailsObject(PickupDetailsFromExcel pickupDetailsFromExcel);
	public String removeNewDriver(BalePickupAssignment assignment);

	public String getBalePickupImagesFromName(String imageName);

}
