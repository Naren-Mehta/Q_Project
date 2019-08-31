package com.wm.brta.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wm.brta.constants.ServiceChannelEnumConstants;
import com.wm.brta.dto.SavedWorkOrdersToServiceChannel;
import com.wm.brta.util.BaleUtils;

public class SendResponseBackTOSC {

	public static void main(String[] args) {
		
		String url = "http://esbqat.wm.com:8888/api/v1/workorders/113341818/status";
		String requestTrackingId = "20190405040500";
				
		
				
				 SavedWorkOrdersToServiceChannel  dtoObj= new SavedWorkOrdersToServiceChannel();
				 dtoObj.setCheck_in_date_time("2019-04-10T22:19:00Z");
				 dtoObj.setStatus_extended(ServiceChannelEnumConstants.OnSite);
				 dtoObj.setStatus_primary("IN PROGRESS");
				 
				
					
					
					try{
						String plainCreds = "baleuser:baleuser";
						byte[] plainCredsBytes = plainCreds.getBytes();
						byte[] base64CredsBytes = Base64Utils.encode(plainCredsBytes);
						String base64Creds = new String(base64CredsBytes);

						HttpHeaders headers = new HttpHeaders();
						headers.add("Authorization", "Basic " + base64Creds);
						//headers.set("ContentType", "application/json");
						headers.set("Request-Tracking-Id",requestTrackingId);
						headers.set("ProfileId", BaleUtils.getProileIdtoUpdateWorkOrders());
						List <MediaType> mediaTypeList = new ArrayList<MediaType>();
						 mediaTypeList.add(MediaType.APPLICATION_JSON);
						 headers.setAccept(mediaTypeList);
						 headers.setContentType(MediaType.APPLICATION_JSON);
						 HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);
						 

						//HttpEntity<String> entity = new HttpEntity<String>(headers);
						HttpEntity<SavedWorkOrdersToServiceChannel> entity = new HttpEntity<SavedWorkOrdersToServiceChannel>(dtoObj, headers);
						
						RestTemplate restTemplate = new RestTemplate();
						
							
						ObjectMapper mapper = new ObjectMapper();
						String jsonInString = mapper.writeValueAsString(dtoObj);
						
					
						
						
						if (dtoObj instanceof SavedWorkOrdersToServiceChannel) {
							
						    HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

						    String status = ((ResponseEntity<String>) response).getStatusCode().toString();
						    	
						    	
						    	
							// restTemplate.exchange(url,HttpMethod.PUT, entity, SavedWorkOrdersToServiceChannel.class,dtoObj);
						}

					}catch(Exception e){
						
						
					}
	}	
	}