package com.wm.brta.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wm.brta.constants.ApplicationCommonConstants;
import com.wm.brta.dao.BalePickUpAssignmentDAO;
import com.wm.brta.dao.ConfigurationDao;
import com.wm.brta.dao.CustomerSiteDAO;
import com.wm.brta.dao.MasterDataDao;
import com.wm.brta.domain.BalePickupCustomerSiteConfiguration;
import com.wm.brta.domain.BalePickupMaterialConfiguration;
import com.wm.brta.domain.BalePickupSupplierConfiguration;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.dto.ConfigurationDataDTO;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.ConfigurationService;

@Component("configurationService")
@Transactional
public class BalePickupConfigurationServiceImpl implements ConfigurationService {

	@Autowired
	ConfigurationDao configDao;

	@Autowired
	CustomerSiteDAO customerSiteDAO;

	@Autowired
	MasterDataDao masterDataDao;

	@Autowired
	BalePickUpAssignmentDAO balePickupassignmentDAO;

	// @Autowired BalePickupServiceImpl balePickupServiceImpl;

	@Override
	public String addSupplierMaterialConfigData(
			ConfigurationDataDTO configurationDataDTO, UserDTO user) {

		boolean errorFlag = false;
		List<BalePickupSupplierConfiguration> supplierConfigList = configurationDataDTO
				.getSupplierConfig();
		List<BalePickupMaterialConfiguration> materialConfigList = configurationDataDTO
				.getMaterialConfig();
		List<BalePickupCustomerSiteConfiguration> balePickupCustomerSiteConfigurations = configurationDataDTO
				.getCustomerSiteConfig();

		List<Integer> customerSiteNameList = configurationDataDTO
				.getCustomerSiteIdList();
		
		Date balePickupStartDate = configurationDataDTO
				.getBalePickupStartDate();
		Date balePickupEndDate = configurationDataDTO.getBalePickupEndDate();


		for (Integer customerSiteId : customerSiteNameList) {
			
			
			CustomerSite customerSite = customerSiteDAO
					.getCustomerSiteFromId(customerSiteId);
			customerSite.setBalePickupStartDate(balePickupStartDate);
			customerSite.setBalePickupEndDate(balePickupEndDate);
			customerSite.setFrequency(configurationDataDTO.getFrequency()
					.getFrequencyId());
			customerSite
					.setFrequencyDay(configurationDataDTO.getFrequencyDay());

			masterDataDao.mergeCustomerSite(customerSite);

			configDao
					.deleteCustomerSiteConfigurationRecordsForCustomerSite(customerSite);
			configDao
					.deleteSupplierConfigurationRecordsForCustomerSite(customerSite);
			configDao
					.deleteMaterialConfigurationRecordsForCustomerSite(customerSite);

			for (BalePickupMaterialConfiguration materialConfig : materialConfigList) {
				materialConfig.setUpdatedBy(user.getFirstName());
				materialConfig.setUpdatedAt(new Date());
				materialConfig.setCreateDate(new Date());
				materialConfig.setEnabled(true);
				materialConfig.setCustomerSite(customerSite);

				BalePickupMaterialConfiguration balePickupMaterialConfiguration = new BalePickupMaterialConfiguration(
						materialConfig.getCustomerSite(),
						materialConfig.getMaterial(),
						materialConfig.getCreateDate(),
						materialConfig.getUpdatedAt(),
						materialConfig.isEnabled(),
						materialConfig.getUpdatedBy(),
						materialConfig.getAvgBaleWeight());

				int materialConfigId = configDao
						.addMaterialConfigData(balePickupMaterialConfiguration);
				if (materialConfigId == 0) {
					errorFlag = true;
				}
			}

			
			if (!errorFlag) {
				
				for (BalePickupCustomerSiteConfiguration balePickupCustomerSiteConfig : balePickupCustomerSiteConfigurations) {

					balePickupCustomerSiteConfig.setUpdatedBy(user
							.getFirstName());
					balePickupCustomerSiteConfig.setUpdatedAt(new Date());
					balePickupCustomerSiteConfig.setCreatedDate(new Date());
					balePickupCustomerSiteConfig
							.setBuyCustomerSite(customerSite);
					balePickupCustomerSiteConfig.setEnabled(true);

					BalePickupCustomerSiteConfiguration balePickupCustomerSiteConfiguration = new BalePickupCustomerSiteConfiguration(
							balePickupCustomerSiteConfig.getBuyCustomerSite(),
							balePickupCustomerSiteConfig.getSellCustomerSite(),
							balePickupCustomerSiteConfig.getCreatedDate(),
							balePickupCustomerSiteConfig.getUpdatedAt(),
							balePickupCustomerSiteConfig.getUpdatedBy(),
							balePickupCustomerSiteConfig.isEnabled());
					
					int balePickupCustomerSiteConfigId = configDao
							.addCustomerSiteConfig(balePickupCustomerSiteConfiguration);

					if (balePickupCustomerSiteConfigId == 0) {
						errorFlag = true;
					}
				}
				if (!errorFlag) {
				
					for (BalePickupSupplierConfiguration supplierConfig : supplierConfigList) {

						supplierConfig.setUpdatedBy(user.getFirstName());
						supplierConfig.setCreateDate(new Date());
						supplierConfig.setUpdatedAt(new Date());
						supplierConfig.setEnabled(true);
						supplierConfig.setCustomerSite(customerSite);

						BalePickupSupplierConfiguration balePickupSupplierConfiguration = new BalePickupSupplierConfiguration(
								supplierConfig.getCustomerSite(),
								supplierConfig.getSupplier(),
								supplierConfig.getCreateDate(),
								supplierConfig.getUpdatedAt(),
								supplierConfig.isEnabled(),
								supplierConfig.getUpdatedBy());

						int supplierConfigId = configDao
								.addSupplierConfigData(balePickupSupplierConfiguration);
						if (supplierConfigId == 0) {
							errorFlag = true;
						}
					}
				}
			}
		}
		
		if (!errorFlag) {
			return ApplicationCommonConstants.INSERTION_SUCCESSFULL_MESSAGE;

		} else {
			return ApplicationCommonConstants.INSERTION_FAILURE_MESSAGE;
		}

	}
}
