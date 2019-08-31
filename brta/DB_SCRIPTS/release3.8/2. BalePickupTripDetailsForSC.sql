
/****** Object:  Table [dbo].[BalePickupTripDetailsForSC]    Script Date: 4/17/2019 5:22:52 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BalePickupTripDetailsForSC](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Customersite] [int] NOT NULL,
	[Material] [int] NOT NULL,
	[Bol] [int] NULL,
	[StoreName] [nvarchar](255) NOT NULL,
	[CheckinDateTime] [smalldatetime] NOT NULL,
	[CheckoutDateTime] [smalldatetime] NULL,
	[UTCCheckinDateTime] [nvarchar](255) NOT NULL,
	[UTCCheckoutDateTime] [nvarchar](255) NULL,
	[Status] [nvarchar](255) NULL,
	[Driver] [int] NULL,
	[SupplierId] [int] NOT NULL,
	[BalesPicked] [smallint] NOT NULL,
	[BalesRemaining] [smallint] NOT NULL,
	[TruckId] [nvarchar](30) NULL,
	[Latitude] [nvarchar](30) NULL,
	[Longitude] [nvarchar](30) NULL,
	[Resolution] [nvarchar](255) NULL,
	[UpdatedDevice] [nvarchar](255) NULL,
	[TimeZone] [nvarchar](255) NULL,
 CONSTRAINT [PK_BalePickupTripDetailsForSC] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
ALTER TABLE [dbo].[BalePickupTripDetailsForSC]  WITH CHECK ADD  CONSTRAINT [FK_BalePickupTripDetailsForSC_CustomerSite] FOREIGN KEY([Customersite])
REFERENCES [dbo].[CustomerSite] ([CustomerSiteId])
GO
ALTER TABLE [dbo].[BalePickupTripDetailsForSC] CHECK CONSTRAINT [FK_BalePickupTripDetailsForSC_CustomerSite]
GO
ALTER TABLE [dbo].[BalePickupTripDetailsForSC]  WITH CHECK ADD  CONSTRAINT [FK_BalePickupTripDetailsForSC_Material] FOREIGN KEY([Material])
REFERENCES [dbo].[Material] ([MaterialId])
GO
ALTER TABLE [dbo].[BalePickupTripDetailsForSC] CHECK CONSTRAINT [FK_BalePickupTripDetailsForSC_Material]
GO
ALTER TABLE [dbo].[BalePickupTripDetailsForSC]  WITH CHECK ADD  CONSTRAINT [FK_BalePickupTripDetailsForSC_Supplier] FOREIGN KEY([SupplierId])
REFERENCES [dbo].[Supplier] ([SupplierId])
GO
ALTER TABLE [dbo].[BalePickupTripDetailsForSC] CHECK CONSTRAINT [FK_BalePickupTripDetailsForSC_Supplier]
GO
ALTER TABLE [dbo].[BalePickupTripDetailsForSC]  WITH CHECK ADD  CONSTRAINT [FK_BalePickupTripDetailsForSC_User] FOREIGN KEY([Driver])
REFERENCES [dbo].[User] ([UserId])
GO
ALTER TABLE [dbo].[BalePickupTripDetailsForSC] CHECK CONSTRAINT [FK_BalePickupTripDetailsForSC_User]
GO
