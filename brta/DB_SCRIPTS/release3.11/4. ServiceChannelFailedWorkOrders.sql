
/****** Object:  Table [dbo].[ServiceChannelFailedWorkOrders]    Script Date: 4/29/2019 2:38:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ServiceChannelFailedWorkOrders](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Status_Primary] [nvarchar](255) NULL,
	[Status_Extended] [nvarchar](255) NULL,
	[Comments] [nvarchar](255) NULL,
	[Check_In_Date_Time] [nvarchar](255) NULL,
	[Check_Out_Date_Time] [nvarchar](255) NULL,
	[WorkOrderNumber] [int] NULL,
	[Latitude] [nvarchar](255) NULL,
	[Longitude] [nvarchar](255) NULL
) ON [PRIMARY]

GO
