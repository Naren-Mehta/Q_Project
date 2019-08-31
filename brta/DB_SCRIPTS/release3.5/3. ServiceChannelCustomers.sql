 CREATE TABLE [dbo].[ServiceChannelCustomers](
	[Id] [int]  NOT NULL,
	[SuscriberId] [int]  NOT NULL,
	[CustomerId] [int] NOT NULL,
	[Enabled] [bit] NOT NULL,
	[CreatedDate] [smalldatetime]  NULL,
	[UpdatedDate] [smalldatetime]  NULL,
	[UpdatedBy] [nvarchar] (30)  NULL,
	)