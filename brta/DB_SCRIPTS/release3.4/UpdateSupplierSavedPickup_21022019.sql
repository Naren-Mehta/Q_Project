
/****** Object:  StoredProcedure [dbo].[GetSupplierSavedPickups]    Script Date: 21-Feb-19 4:10:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[GetSupplierSavedPickups]
	-- Add the parameters for the stored procedure here
	@SupplierId int, 
	@DriverId int,
	@VehiclePlateNo nvarchar(6)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	select ROW_NUMBER() OVER (ORDER BY newid()) AS SequenceNo,a.* from(SELECT     DISTINCT 
					  dbo.DriverSupplierPickupsView.CustomerSiteId, dbo.DriverSupplierPickupsView.CustomerId, dbo.DriverSupplierPickupsView.SiteName, 
                      dbo.DriverSupplierPickupsView.HouseNumber, dbo.DriverSupplierPickupsView.Address1, dbo.DriverSupplierPickupsView.Address2, 
                      dbo.DriverSupplierPickupsView.Address3, dbo.DriverSupplierPickupsView.Address4, dbo.DriverSupplierPickupsView.Address5, 
                      dbo.DriverSupplierPickupsView.Postcode, dbo.DriverSupplierPickupsView.TelNo, dbo.DriverSupplierPickupsView.CustomerName, 
                      dbo.DriverSupplierPickupsView.SupplierId, dbo.DriverSupplierPickupsView.Driver1Id, dbo.DriverSupplierPickupsView.Driver2Id, 
                      dbo.DriverSupplierPickupsView.Driver1_FirstName, dbo.DriverSupplierPickupsView.Driver1_MiddleName, dbo.DriverSupplierPickupsView.Driver_1_LastName, 
                      dbo.DriverSupplierPickupsView.Driver1_Email, dbo.DriverSupplierPickupsView.Driver2_MobilePhone, dbo.DriverSupplierPickupsView.Driver2_Email, 
                      dbo.DriverSupplierPickupsView.Driver2_LastName, dbo.DriverSupplierPickupsView.Driver2_MiddleName, dbo.DriverSupplierPickupsView.Driver2_FirstName, 
                      dbo.DriverSupplierPickupsView.Driver1_MobilePhone, dbo.BalePickupTrip.PickupDate,dbo.BalePickupTrip.VehiclePlateNo
FROM         dbo.DriverSupplierPickupsView LEFT OUTER JOIN
                      dbo.BalePickupTrip ON dbo.BalePickupTrip.BuyCustomerSiteId = dbo.DriverSupplierPickupsView.CustomerSiteId AND (dbo.BalePickupTrip.Enabled IS NULL OR
                      dbo.BalePickupTrip.Enabled = 1) 
					  --AND (dbo.BalePickupTrip.PickupDate IS NULL OR DATEDIFF(DD, dbo.BalePickupTrip.PickupDate, GETDATE()) = 0)
WHERE     (dbo.DriverSupplierPickupsView.SupplierId = @SupplierId)  AND (dbo.BalePickupTrip.TripId IS NOT NULL) AND (dbo.BalePickupTrip.ReleaseNo IS NULL) AND (dbo.BalePickupTrip.GrossWeight IS NULL) AND 
                      (dbo.BalePickupTrip.TareWeight IS NULL) AND (dbo.BalePickupTrip.UserId = @DriverId)
					  AND 
					  (((@VehiclePlateNo is null) AND  dbo.BalePickupTrip.VehiclePlateNo IS NULL)
					  OR (@VehiclePlateNo IS NOT NULL AND dbo.BalePickupTrip.VehiclePlateNo = @VehiclePlateNo)) ) a
					  order by CustomerSiteId;
					  END

