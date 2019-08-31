-- **************************************************************************************************
-- *
-- * Insert Bale Route Mapping records for the Customer WAL MART PLASTIC 
-- * 6/20/2019
-- *
-- **************************************************************************************************

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Insert BaleRouteCustomerMaterialMapping Record for Customer WAL MART PLASTIC and Material MIXED RECYCLABLE BALE -- 

PRINT ' '
PRINT 'Insert RMT.BaleRouteCustomerMaterialMapping Record'
PRINT ' '

Insert into RMT.BaleRouteCustomerMaterialMapping (BaleRouteCustomerMaterialMappingId, BuyCustomerId, MaterialId, CreateDate, Enabled, UpdatedAt, UpdatedBy)
Select case when (select max(BaleRouteCustomerMaterialMappingId) from RMT.BaleRouteCustomerMaterialMapping) Is Null Then 1 
       else (select max(BaleRouteCustomerMaterialMappingId) from RMT.BaleRouteCustomerMaterialMapping) + 1  end,
       (Select CustomerID from dbo.Customer where CustomerName = 'WAL MART PLASTIC'), 
       (Select MaterialId from dbo.Material where Description = 'MIXED RECYCLABLE BALE'), 
       GetDate(), 1, GetDate(), 'Mapping Maintenance'
where not exists (select 'X' from RMT.BaleRouteCustomerMaterialMapping where BuyCustomerId = (Select CustomerID from dbo.Customer where CustomerName = 'WAL MART PLASTIC') 
                  and MaterialId = (Select MaterialId from dbo.Material where Description = 'MIXED RECYCLABLE BALE'))
and exists (select 'X' from dbo.Customer where CustomerName = 'WAL MART PLASTIC')
and exists (select 'X'from dbo.Material where Description = 'MIXED RECYCLABLE BALE');

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Insert BaleRouteCustomerSupplierMapping Records for Customer WAL MART PLASTIC and all the Suppliers currently in that table --

Insert into RMT.BaleRouteCustomerSupplierMapping (BaleRouteCustomerSupplierMappingId, BuyCustomerId, SupplierId, CreateDate, Enabled, UpdatedAt, UpdatedBy) 
Select (select max(BaleRouteCustomerSupplierMappingId) from RMT.BaleRouteCustomerSupplierMapping) + row_number() over(order by UpdatedBy) BaleRouteCustomerSupplierMappingId, 
       buycustomerid, SupplierId, CreateDate, Enabled, UpdatedAt, UpdatedBy  
from
(
select DISTINCT (Select CustomerID from dbo.Customer where CustomerName = 'WAL MART PLASTIC') buycustomerid,  
       brcsm.SupplierID, GetDate() CreateDate, 1 Enabled, GetDate() UpdatedAt, 'WAL MART PLASTIC MAPPINGS' UpdatedBy
from RMT.BaleRouteCustomerSupplierMapping brcsm
where exists (select 'X' from dbo.Customer where CustomerName = 'WAL MART PLASTIC')
and not exists (select 'X' from RMT.BaleRouteCustomerSupplierMapping brcsmx
                where brcsmx.BuyCustomerId = (Select CustomerID from dbo.Customer where CustomerName = 'WAL MART PLASTIC')
                and brcsmx.SupplierId = brcsm.SupplierID)
) InterimResults;

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Insert BaleRouteCustomerMapping Records for Customer WAL MART PLASTIC and all the Suppliers/Sell Customer relationships currently in that table --

Insert into RMT.BaleRouteCustomerMapping (BaleRouteCustomerMappingId, BuyCustomerId, SupplierId, SellCustomerId, CreateDate, Enabled, UpdatedAt, UpdatedBy) 
Select (select max(BaleRouteCustomerMappingId) from RMT.BaleRouteCustomerMapping) + row_number() over(order by UpdatedBy) BaleRouteCustomerMappingId, 
       buycustomerid, SupplierId, SellCustomerId, CreateDate, Enabled, UpdatedAt, UpdatedBy  
from
(
select DISTINCT (Select CustomerID from dbo.Customer where CustomerName = 'WAL MART PLASTIC') buycustomerid,  
       brcm.SupplierID, brcm.SellCustomerId, GetDate() CreateDate, 1 Enabled, GetDate() UpdatedAt, 'WAL MART PLASTIC MAPPINGS' UpdatedBy
from RMT.BaleRouteCustomerMapping brcm
where exists (select 'X' from dbo.Customer where CustomerName = 'WAL MART PLASTIC')
and not exists (select 'X' from RMT.BaleRouteCustomerMapping brcmx
                where brcmx.BuyCustomerId = (Select CustomerID from dbo.Customer where CustomerName = 'WAL MART PLASTIC')
                and brcmx.SupplierId = brcm.SupplierID
                and brcmx.SellCustomerId = brcm.SellCustomerId) 
) InterimResults;

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------





