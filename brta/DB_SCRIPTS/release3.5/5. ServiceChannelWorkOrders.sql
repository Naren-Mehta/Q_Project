

GO

/****** Object:  Table [dbo].[ServiceChannelWorkOrders]    Script Date: 1/8/2019 3:37:59 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ServiceChannelWorkOrders](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[workOrderNumber] [int] NOT NULL,
	[callDate] [smalldatetime] NOT NULL,
	[scheduledDate] [smalldatetime] NOT NULL,
	[storeId] [int] NOT NULL, 
	[requesttrackingid] [nvarchar] (20) NULL,
	[createdDate] [smalldatetime]  NULL,
	[updatedDate] [smalldatetime]  NULL,
	[updatedBy] [nvarchar] (30)  NULL,
	 CONSTRAINT [PK_ServiceChannelWorkOrders] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
