package com.wm.brta.service;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { Application.class })
//@TestPropertySource(locations = "classpath:application-test.properties")
public class BalePickupServiceTest {

//	private static final Logger LOGGER = Logger
//			.getLogger("BalePickupServiceTest");
//
//	@Autowired
//	BalePickupService balePickupService;
//
//	@Autowired
//	BalePickUpAssignmentDAO balePickUpAssignmentDAO;
//
//	private SessionFactory mockedSessionFactory;
//	private Session mockedSession;
//	private Transaction mockedTransaction;

/*	public void setUpMokito() {
		mockedSessionFactory = Mockito.mock(SessionFactory.class);
		mockedSession = Mockito.mock(Session.class);
		mockedTransaction = Mockito.mock(Transaction.class);
	}*/
/*
	@Test
	public void testGetAllDestinations() {

		ActivityListInputPayload activityListInputPayload = new ActivityListInputPayload();
		activityListInputPayload.setUserId(1305);
		List<String> dates = new ArrayList<String>();
		dates.add("04/10/2017");
		activityListInputPayload.setDates(dates);

		HashSet<Destination> destinationList = new HashSet<Destination>();
		HashSet<PickupDetails> picHashSet = new HashSet<PickupDetails>();
		Destination destination = new Destination();
		destination.setDestinationId(1234);
		destination.setName("yo");

		PickupDetails pickupDetails = new PickupDetails();
		picHashSet.add(pickupDetails);
//		destination.setPickupDetails(picHashSet);
		destinationList.add(destination);

		BaleUtils.setUpMokito();
		balePickupService = Mockito.mock(BalePickupService.class);

//		mockedSessionFactory = Mockito.mock(SessionFactory.class);
//		mockedSession = Mockito.mock(Session.class);
//		mockedTransaction = Mockito.mock(Transaction.class);
//		Mockito.when(
//				balePickupService.getAllDestinations(activityListInputPayload))
//				.thenReturn(destinationList);

		 HashSet<Destination> destinations = balePickupService
		 .getAllDestinations(activityListInputPayload);
		
		// System.out.println("----destinations-11-" + destinations);

		 Assert.assertNotNull(destinations);

	}
	
	@Test
	public void testGetBalePickupImages() {
		
		BaleUtils.setUpMokito();
		balePickupService=Mockito.mock(BalePickupService.class);
		
		
		List<String>  images=balePickupService.getBalePickupImages(20622);
		//System.out.println("---images site--"+images);		
	}

	@Test
	public void massageTest() {
		Assert.assertEquals(null,"abc", "abc");
	}
	
	@Test
	public void testGetBalePickupImagesFromTripAndName() {
		
		BaleUtils.setUpMokito();
		balePickupService=Mockito.mock(BalePickupService.class);
		
		
		String  data=balePickupService.getBalePickupImagesFromName("INCIDENT_IMAGE_20664_2_4_8_2");
	//	System.out.println("---images site--"+data);		
	}
*/}
