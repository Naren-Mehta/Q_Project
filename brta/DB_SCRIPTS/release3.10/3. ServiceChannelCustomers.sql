
/****** Object:  Table [dbo].[ServiceChannelCustomers]    Script Date: 4/29/2019 2:35:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ServiceChannelCustomers](
	[Id] [int] NOT NULL,
	[SuscriberId] [int] NOT NULL,
	[CustomerId] [int] NOT NULL,
	[Enabled] [bit] NOT NULL,
	[CreatedDate] [smalldatetime] NULL,
	[UpdatedDate] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](30) NULL
) ON [PRIMARY]

GO
INSERT [dbo].[ServiceChannelCustomers] ([Id], [SuscriberId], [CustomerId], [Enabled], [CreatedDate], [UpdatedDate], [UpdatedBy]) VALUES (1, 2000051259, 532, 1, CAST(0xAA260076 AS SmallDateTime), CAST(0xAA260076 AS SmallDateTime), N'Manual By Deepak')
INSERT [dbo].[ServiceChannelCustomers] ([Id], [SuscriberId], [CustomerId], [Enabled], [CreatedDate], [UpdatedDate], [UpdatedBy]) VALUES (2, 2014917515, 533, 1, CAST(0xAA260076 AS SmallDateTime), CAST(0xAA260076 AS SmallDateTime), N'Manual By Deepak')
ALTER TABLE [dbo].[ServiceChannelCustomers] ADD  DEFAULT (getdate()) FOR [CreatedDate]
GO
ALTER TABLE [dbo].[ServiceChannelCustomers] ADD  DEFAULT (getdate()) FOR [UpdatedDate]
GO
