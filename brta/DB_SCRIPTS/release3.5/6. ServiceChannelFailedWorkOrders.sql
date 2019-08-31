
GO

/****** Object:  Table [dbo].[ServiceChannelFailedWorkOrders]    Script Date: 1/8/2019 3:37:59 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ServiceChannelFailedWorkOrders](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[suscriberId] [nvarchar] (20) NOT NULL,
	[requestTrackingId] [nvarchar] (20) NOT NULL,
	[limit] [int]  NULL,
	[offset] [int]  NULL,
	[requestStatus] [nvarchar] (30)  NULL,
	[errorMessage] [nvarchar](100)  NULL, 
	[createdDate] [smalldatetime]  NULL,
	[updatedDate] [smalldatetime]  NULL,
	[updatedBy] [nvarchar] (30)  NULL,
	[isResolved] [bit]  NULL,
	 CONSTRAINT [PK_ServiceChannelFailedWorkOrders] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]