<%@page import="com.wm.brta.util.XSSUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@page import="com.wm.brta.dto.UserDTO"%>
<html lang="en" ng-app="brtaApp">

<head>
    <!-- meta tag here -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="author" content="Site author">
    <meta name="keywords" content="Site keywords">
    <meta name="description" content="Site description">
    <title>WM-Bale Route Tracking </title>
    
    <% Double versionNo =(Double)session.getAttribute("versionNo"); %>
    <% String supplierDesc =XSSUtils.stripXSS((String)session.getAttribute("description")); %>
    <% Integer supplierId =(Integer)session.getAttribute("supplierId"); %>
    
    
    <!-- favorite icon -->
    <link type="image/icon" rel="shortcut icon" href="/brta/app/ui/resources/images/favicon.png">
	 <link rel="shortcut icon" href="/brta/app/ui/resources/images/favicon.png">

	 <link href="/brta/app/ui/resources/css/bootstrap.min.css?v=${versionNo}" rel="stylesheet" type="text/css" />
     <link href="/brta/app/ui/resources/css/fonts/fonts.css?v=${versionNo}" rel="stylesheet" type="text/css" />
     <link href="/brta/app/ui/resources/css/fonts/font-awesome.min.css?v=${versionNo}" rel="stylesheet" type="text/css" />   
	 <link href="/brta/app/ui/resources/css/style.css?v=${versionNo}" rel="stylesheet" type="text/css" />
	 <link href="/brta/app/ui/resources/css/angular-bootstrap-lightbox.css?v=${versionNo}" rel="stylesheet" type="text/css" />
	 
	<link rel="stylesheet" href="/brta/app/ui/resources/css/datatablecss/jquery.dataTables.min.css?v=${versionNo}" type="text/css">
	<link rel="stylesheet" href="/brta/app/ui/resources/css/datatablecss/angular-datatables.css?v=${versionNo}" type="text/css">
	<link rel="stylesheet" href="/brta/app/ui/resources/css/datatablecss/responsive.dataTables.min.css?v=${versionNo}" type="text/css">
	<link rel="stylesheet" href="/brta/app/ui/resources/css/datatablecss/ng-table.min.css?v=${versionNo}" type="text/css">
	<link rel="stylesheet" href="/brta/app/ui/resources/css/datatablecss/responsive.css?v=${versionNo}" type="text/css">
	<link rel="stylesheet" href="/brta/app/ui/resources/css/datatablecss/dataTables.fixedColumns.css?v=${versionNo}"> 
	<link href="/brta/app/ui/resources/css/select.css?v=${versionNo}" rel="stylesheet" type="text/css" />
	<link href="/brta/app/ui/resources/css/select2.css?v=${versionNo}" rel="stylesheet" type="text/css" />
    <link href="/brta/app/ui/resources/css/selectize.default.css?v=${versionNo}" rel="stylesheet" type="text/css" />
     
	 <script src="/brta/app/ui/resources/js/angular/lib/jquery.min.js?v=${versionNo}" type="text/javascript"></script>
  	<script src="/brta/app/ui/resources/js/angular/lib/bootstrap.min.js?v=${versionNo}" type="text/javascript"></script>
  	<script src="/brta/app/ui/resources/js/angular/lib/angular.min.js?v=${versionNo}" type="text/javascript"></script>
  	
  	
	    <script src="/brta/app/ui/resources/js/angular/lib/angular-route.min.js?v=${versionNo}" type="text/javascript"></script>
	    <script src="/brta/app/ui/resources/js/angular/lib/xeditable.min.js?v=${versionNo}" type="text/javascript"></script>

	    <!--  <script src="/brta/app/ui/resources/js/angular/lib/xlsx.full.min.js?v=${versionNo}" type="text/javascript"></script>-->
		<script src="/brta/app/ui/resources/js/angular/lib/angular-js-xlsx.js?v=${versionNo}" type="text/javascript"></script>
		
		
		<script src="/brta/app/ui/resources/js/angular/lib/core.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/lib/scalyr.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/lib/slyEvaluate.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/lib/slyRepeat.js?v=${versionNo}" type="text/javascript"></script>
		
		
		<!-- <script src="https://rawgithub.com/eligrey/FileSaver.js/master/FileSaver.js" type="text/javascript"></script> -->
		<script src="/brta/app/ui/resources/js/angular/lib/FileSaver.min.js?v=${versionNo}" type="text/javascript"></script>
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/alasql/0.4.5/alasql.min.js" type="text/javascript"></script>  -->

 		<script src="/brta/app/ui/resources/js/angular/lib/xlsx.core.min.js?v=${versionNo}"></script>
 		<script src="/brta/app/ui/resources/js/angular/lib/jspdf.min.js?v=${versionNo}" type="text/javascript"></script>		
		<script src="/brta/app/ui/resources/js/angular/lib/jspdf.debug.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/lib/jspdf.plugin.autotable.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/lib/angular-bootstrap-lightbox.min.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/lib/angular-base64-upload.min.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/lib/angular-base64.min.js?v=${versionNo}"></script>
		<script src="/brta/app/ui/resources/js/angular/lib/ng-pattern-restrict.min.js?v=${versionNo}"type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/lib/ng-csv.min.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/lib/angular-sanitize.min.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/lib/angular-idle.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/ui-bootstrap-tpls-2.4.0.min.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/lib/dirPagination.js?v=${versionNo}" type="text/javascript"></script>
	   <script src="/brta/app/ui/resources/js/angular/datatables/jquery.scrollbar.js?v=${versionNo}" type="text/javascript"></script> 
	    <script src="/brta/app/ui/resources/js/angular/datatables/jquery.dataTables.min.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/datatables/lodash.min.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/datatables/angular-datatables.min.js?v=${versionNo}" type="text/javascript"></script>	
		<script src="/brta/app/ui/resources/js/angular/datatables/dataTables.responsive.min.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/datatables/dataTables.columnFilter.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/datatables/angular-datatables.columnfilter.min.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/datatables/dataTables.fixedColumns.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/datatables/angular-datatables.fixedcolumns.min.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/datatables/ng-table.min.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/lib/select.js?v=${versionNo}" type="text/javascript"></script>
		
		<!--<script src="/brta/app/ui/resources/js/angular/lib/angular-sanitize.js?v=${versionNo}" type="text/javascript"></script> -->
		<script src="/brta/app/ui/resources/js/angular/lib/angular-messages.min.js?v=${versionNo}" type="text/javascript"></script>
	     <script src="/brta/app/ui/resources/js/angular/main.js?v=${versionNo}" type="text/javascript"></script>
	   
		<script src="/brta/app/ui/resources/js/angular/service/masterservice.js?v=${versionNo} " type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/service/incidenttypeservice.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/service/storeservice.js?v=${versionNo}" type="text/javascript"></script>
   		<script src="/brta/app/ui/resources/js/angular/service/balepickupservice.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/service/reportservice.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/service/userservice.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/service/supplierservice.js?v=${versionNo}" type="text/javascript"></script>
		 <script src="/brta/app/ui/resources/js/angular/service/frequencyService.js?v=${versionNo}" type="text/javascript"></script>
		 <script src="/brta/app/ui/resources/js/angular/service/servicechannelservice.js?v=${versionNo}" type="text/javascript"></script>
		      
		      
		 <script src="/brta/app/ui/resources/js/common.js?v=${versionNo}" type="text/javascript"></script>
	     <script src="/brta/app/ui/resources/js/angular/app/landing.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/app/supplierlogin.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/app/usermanagement.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/app/incidenttypemanagement.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/app/storemanagement.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/app/balepickup.js?v=${versionNo}" type="text/javascript"></script>
	    <script src="/brta/app/ui/resources/js/angular/app/reportmanagement.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/app/needHelp.js?v=${versionNo}" type="text/javascript"></script>	
		<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.16/datatables.min.js"></script>
		
		<!-- Excel upload dependencies -->
		<!-- <script src="/brta/app/ui/resources/js/angular/ExcelUpload/filesaver.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/ExcelUpload/blob.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/ExcelUpload/infragistics.core.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/ExcelUpload/infragistics.ext_core.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/ExcelUpload/infragistics.ext_collections.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/ExcelUpload/infragistics.ext_text.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/ExcelUpload/infragistics.ext_io.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/ExcelUpload/infragistics.ext_ui.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/ExcelUpload/infragistics.documents.core_core.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/ExcelUpload/infragistics.ext_collectionsextended.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/ExcelUpload/infragistics.excel_core.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/ExcelUpload/infragistics.ext_threading.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/ExcelUpload/infragistics.ext_web.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/ExcelUpload/infragistics.xml.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/ExcelUpload/infragistics.documents.core_openxml.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/ui/resources/js/angular/ExcelUpload/infragistics.excel_serialization_openxml.js?v=${versionNo}" type="text/javascript"></script>
		 -->
		<!-- End Excel upload dependencies -->
	<!--  
		<script src="https://cdn.datatables.net/1.10.10/js/jquery.dataTables.min.js"></script>

		<script src="https://cdn.datatables.net/buttons/1.2.2/js/dataTables.buttons.min.js"></script>
		<script src="https://rawgithub.com/l-lin/angular-datatables/angular1/dist/angular-datatables.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.1.0/js/buttons.flash.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
		<script src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js"></script>
		<script src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.1.0/js/buttons.html5.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.1.0/js/buttons.print.min.js"></script>
		<script src="http://l-lin.github.io/angular-datatables/archives/dist/plugins/buttons/angular-datatables.buttons.min.js"></script>

		
	-->	
	
    	<link href="/brta/app/ui/resources/css/selectize.default.css" rel="stylesheet" type="text/css" />
    
		
	
<script type="text/javascript">
		var role = "${role}";
		var user='${user}'
	    var supplierId= "${supplierId}";
		

	
		$('.dropdownFirst, .dropdownSec').hide();
		$('.custom-select').click(function(){
		$('.dropdownSec').hide();
		$('.dropdownFirst').toggle();
		});
		$('.custom-select > .dropdownFirst >ul > li' ).click(function(event){
			event.stopPropagation();
			
			$('.dropdownSec').hide();
			$('.custom-select > .dropdownFirst >ul > li').removeClass('active');
			$(this).addClass('active');
			$(this).children('.dropdownSec').toggle();
		});
		
		$('.scrollpagehide').on('click', function() {
 $('body').toggleClass('body-popup-opened');
});
	</script>

	</head>
	<body ng-class="{'leftmenuinactive': !backClick, '':backClick}" ng-controller="sideMenuController" >
	<%
	UserDTO userDto= (UserDTO)session.getAttribute("user");
	String userRole= XSSUtils.stripXSS(userDto.getRole());
	String nameValue=XSSUtils.stripXSS((userDto.getFirstName() != null) ? (userDto.getFirstName().substring(0, 1).toUpperCase() + userDto.getFirstName().substring(1)) :"username");
	%>
<!--Header Content-->
		
			
		
 <c:set var="name" value="<%=userRole %>"/>
 

 <c:choose>
      <c:when test="${name=='Supplier'}">

 
       <div class="dropdown a1 ">
					<button class="btn btn-success  btn-lg dropdown-toggle a2" type="button" data-toggle="dropdown" tabindex="-1">
						<span class="glyphicon  a3"> <img src="/brta/app/ui/resources/images/menu.png" style=" width: 100%;"></span>
						</button>
			<ul class="dropdown-menu a4 sidebar-menu">
					 
					  
			<li ng-show="storeAssignment" class="store_menu"  ><a href="#storesupplierForSupplier" ng-click ="showstoreManagement=true;showuserManagement=false;showbalePickup=false;showreports=false;showsupplierManagement=false;showincidentManagement=false;showneedHelp=false;" ng-class="{active:showstoreManagement}"><span class="menu-icon"><p class="storageManagementHover">Store Management</p></span></a></li>
			<li ng-show="userManagement" class="user_menu" ><a href="#usersForSupplier" ng-click ="showuserManagement=true;showstoreManagement=false;showbalePickup=false;showreports=false;showsupplierManagement=false;showincidentManagement=false;showneedHelp=false;" ng-class="{active:showuserManagement}"><span class="menu-icon"><p class= "userManagementHover">User Management</p></span></a></li>
			<li ng-show="incidentManagement" class="incident_menu"><a href="#incidentmanagementForSupplier" ng-click ="showincidentManagement=true;showreports=false;showbalePickup=false;showuserManagement=false;showstoreManagement=false;showsupplierManagement=false;showneedHelp=false;"ng-class="{active:showincidentManagement}" ><span class="menu-icon"><p class="incidentManagementHover">Incident Management</p></span></a></li>
			<li ng-show="balePickup" class="pickup_menu"  ><a href="#balepickupForSupplier" ng-click ="showbalePickup=true;showuserManagement=false;showstoreManagement=false;showreports=false;showsupplierManagement=false;showincidentManagement=false;showneedHelp=false;" ng-class="{active:showbalePickup}"><span class="menu-icon"><p class="balePickupHover">Bale Pickup</p></span></a></li>
			<li ng-show="reports" class="report_menu" ><a href="#reportsForSupplier" ng-click ="showreports=true;showincidentManagement=false;showstoreManagement=false;showuserManagement=false;showbalePickup=false;showsupplierManagement=false;showneedHelp=false;" ng-class="{active:showreports}" ><span class="menu-icon"><p class="reportsHover">Reports</p></span></a></li>
			<li ng-show="needHelp" class="needHelp_menu" ><a href="#needHelpForSupplier" ng-click ="showneedHelp=true;showreports=false;showincidentManagement=false;showstoreManagement=false;showuserManagement=false;showbalePickup=false;showsupplierManagement=false;" ng-class="{active:showneedHelp}" ><span class="menu-icon"><p class="needHelpHover">Help</p></span></a></li>


			<li class="logout_menu" ><a  ng-click="logOutUser($event)" href="logoutSupplier"><span class="menu-icon"><p class="reportsHover">
			Logout ( <%= nameValue %> )</p></span></a></li>


					</ul>
				  </div>
   
      </c:when>
     
      <c:otherwise> 
  
      <div class="dropdown a1 ">
					<button class="btn btn-success  btn-lg dropdown-toggle a2" type="button" data-toggle="dropdown" tabindex="1">
						<span class="glyphicon  a3"> <img src="/brta/app/ui/resources/images/menu.png" style=" width: 100%;"></span>
						</button>
					<ul class="dropdown-menu a4 sidebar-menu">
					 
					  
			<li ng-show="storeAssignment" class="store_menu"  ><a href="#storemanagement" ng-click ="showstoreManagement=true;showuserManagement=false;showbalePickup=false;showreports=false;showsupplierManagement=false;showincidentManagement=false;showneedHelp=false;" ng-class="{active:showstoreManagement}"><span class="menu-icon"><p class="storageManagementHover">Store Management</p></span></a></li>
			<li ng-show="userManagement" class="user_menu" ><a href="#users" ng-click ="showuserManagement=true;showstoreManagement=false;showbalePickup=false;showreports=false;showsupplierManagement=false;showincidentManagement=false;showneedHelp=false;" ng-class="{active:showuserManagement}"><span class="menu-icon"><p class= "userManagementHover">User Management</p></span></a></li>
			<li ng-show="incidentManagement" class="incident_menu"><a ng-click ="showincidentManagement=true;showreports=false;showbalePickup=false;showuserManagement=false;showstoreManagement=false;showsupplierManagement=false;showneedHelp=false;" ng-class="{active:showincidentManagement}" href="#incidentmanagement"><span class="menu-icon"><p class="incidentManagementHover">Incident Management</p></span></a></li>
			<li ng-show="balePickup" class="pickup_menu"  ><a href="#balepickup" ng-click ="showbalePickup=true;showuserManagement=false;showstoreManagement=false;showreports=false;showsupplierManagement=false;showincidentManagement=false;showneedHelp=false;" ng-class="{active:showbalePickup}"><span class="menu-icon"><p class="balePickupHover">Bale Pickup</p></span></a></li>
			<li ng-show="supplierManagement" class="supplier_menu" ><a  ng-click ="showsupplierManagement=true;showstoreManagement=false;showbalePickup=false;showreports=false;showuserManagement=false;showincidentManagement=false;showneedHelp=false;" ng-class="{active:showsupplierManagement}"href="#suppliers"><span class="menu-icon"><p class="supplierManagementHover">Supplier Management</p></span></a></li>
			<li ng-show="reports" class="report_menu" ><a ng-click ="showreports=true;showincidentManagement=false;showstoreManagement=false;showuserManagement=false;showbalePickup=false;showsupplierManagement=false;showneedHelp=false;" ng-class="{active:showreports}" href="#reports"><span class="menu-icon"><p class="reportsHover">Reports</p></span></a></li>
			<li ng-show="needHelp" class="needHelp_menu" ><a ng-click ="showneedHelp=true;showreports=false;showincidentManagement=false;showstoreManagement=false;showuserManagement=false;showbalePickup=false;showsupplierManagement=false;" ng-class="{active:showneedHelp}" href="#needHelp"><span class="menu-icon"><p class="needHelpHover">Help</p></span></a></li>

			<li class="logout_menu" ><a  ng-click="logOutUser($event)" href="logout"><span class="menu-icon"><p class="reportsHover">
			Logout ( <%= nameValue %> )</p></span></a></li>

					</ul>
				  </div>
    
      </c:otherwise>
    </c:choose>
    <div class="headerContainer" style="position: fixed;z-index: 1;top:0">
			<div class="HeaderTopLayer">
				
			<div class="logo"><img src="/brta/app/ui/resources/images/Waste_Management_Logo.png" style="pointer-events: none"></div>
			<div class="page-title">Bale Route Tracking <small style="font-size:50%">v=${versionNo}</small> </div>
		
	</div>
	 
</div>
<!-- <br> //commented by prema-->
<div class="ContentFullContainer">

<!---loader-->
<div id="loader" ng-if="loaderActive=false">
	<div class="spinner"> 
	<i class="fa fa-spin fa-circle-o-notch"></i> Loading...</div>
</div>
<!--loader end-->





<div ng-view></div>

</div>

	</body>
	
		
		
</html>