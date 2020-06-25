package com.rnd.loyaltydemo.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.rnd.loyaltydemo.core.bo.AccessTokenResponse;
import com.rnd.loyaltydemo.core.bo.Address;
import com.rnd.loyaltydemo.core.bo.Email;
import com.rnd.loyaltydemo.core.bo.LoyaltyProfile;
import com.rnd.loyaltydemo.core.bo.Phone;
import com.rnd.loyaltydemo.core.config.ContextAwareConfig;
import com.rnd.loyaltydemo.core.constants.LoyaltyDemoConstants;
import com.rnd.loyaltydemo.core.service.LoyaltyService;
import com.rnd.loyaltydemo.core.utils.CommonUtil;
import com.rnd.loyaltydemo.core.utils.ContextAwareConfigUtilService;
import com.rnd.loyaltydemo.core.utils.CookiesUtil;
/**
 * 
 * @author spathak2
 *
 */
@ServiceDescription("Edit Profile Servlet")
@Component(immediate = true, service = Servlet.class, property = { "sling.servlet.selectors=editProfile",
		"sling.servlet.extensions=json", "sling.servlet.resourcetypes=cq:Page",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST })
public class EditProfileServlet extends SlingAllMethodsServlet{

	private static final long serialVersionUID = 7485497505460995864L;
	private static final Logger log = LoggerFactory.getLogger(EditProfileServlet.class);

	private static final String STATUS = "A";
	@Reference
	private transient ContextAwareConfigUtilService caConfigUtilService;

	@Reference
	private transient LoyaltyService loyaltyService;
       
	/**
	 * doPost method for EditProfileServlet
	 * 
	 * @param SlingHttpServletRequest  - request object
	 * @param SlingHttpServletResponse - response object
	 */
	    @Override
		protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
				throws ServletException, IOException {
		
		response.setContentType(LoyaltyDemoConstants.APPLICATION_JSON);
		response.setCharacterEncoding(LoyaltyDemoConstants.UTF_8);
		
		LoyaltyProfile loyaltyProfile =null;
		
		try {
		
		ContextAwareConfig contextAwareConfig = caConfigUtilService.getContextAwareConfig(request);
		if (contextAwareConfig == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		AccessTokenResponse accessTokenResponse = CookiesUtil.getAccessTokenData(request,
				contextAwareConfig.cookiePrefix());
		if (accessTokenResponse != null && StringUtils.isNotBlank(accessTokenResponse.getAccessToken())
				&& StringUtils.isNotBlank(accessTokenResponse.getProfileId())) {

			 String profileData= request.getParameter("ProfileData");
			 String addressData= request.getParameter("AddressData");
			 String emailsData= request.getParameter("EmailData");
			 String phoneData= request.getParameter("PhoneData");
		 Gson gson = new Gson();
		 loyaltyProfile =  gson.fromJson(profileData, LoyaltyProfile.class);
		 Address address = gson.fromJson(addressData, Address.class);
		 Email emails = gson.fromJson(emailsData, Email.class);
		 Phone phones = gson.fromJson(phoneData, Phone.class);
		 if(null == loyaltyProfile && (address != null || emails != null || phones != null)) {
			 loyaltyProfile =	 loyaltyService.getProfile(accessTokenResponse.getAccessToken(), accessTokenResponse.getProfileId(),
					 contextAwareConfig);
		 }
		 if(null == loyaltyProfile ||StringUtils.isNotBlank(loyaltyProfile.getErrorCode()) )
		 {
			 response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			 return;
		 }
		 loyaltyProfile.setStatus(STATUS);
		 if(null != address) {
		 address.setStatus(STATUS);
		 address.setIsPreferred(true);
		 List<Address> addressList = new ArrayList<>();
		 addressList.add(address);
		 loyaltyProfile.setAddresses(addressList);
		 }		 
		 if(null != emails) {
			 emails.setStatus(STATUS);
			 emails.setIsPreferred(true);
			 List<Email> emailsList = new ArrayList<>();
			 emailsList.add(emails);
			 loyaltyProfile.setEmails(emailsList);
		 }
		 if(null != phones) {
			 phones.setStatus(STATUS);
			 phones.setIsPreferred(true);
			 List<Phone> phonesList = new ArrayList<>();
			 phonesList.add(phones);
			 loyaltyProfile.setPhones(phonesList);
		 }
		 String profileDataRequestJson = gson.toJson(loyaltyProfile);   
		 loyaltyProfile	= loyaltyService.updateProfile(accessTokenResponse.getProfileId(), accessTokenResponse.getAccessToken(),profileDataRequestJson,
						contextAwareConfig);
		response.getWriter().write(CommonUtil.getJsonFromObject(loyaltyProfile));
			}
		}catch(Exception e) {
			log.error("EditProfileServlet :error occured::{}", e);
		}
}
}