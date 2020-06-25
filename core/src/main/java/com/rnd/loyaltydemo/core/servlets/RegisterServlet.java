package com.rnd.loyaltydemo.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import com.rnd.loyaltydemo.core.bo.Email;
import com.rnd.loyaltydemo.core.bo.LoyaltyProfile;
import com.rnd.loyaltydemo.core.bo.UpdateProfileRequest;
import com.rnd.loyaltydemo.core.config.ContextAwareConfig;
import com.rnd.loyaltydemo.core.constants.LoyaltyDemoConstants;
import com.rnd.loyaltydemo.core.service.LoyaltyService;
import com.rnd.loyaltydemo.core.utils.CommonUtil;
import com.rnd.loyaltydemo.core.utils.ContextAwareConfigUtilService;
import com.rnd.loyaltydemo.core.utils.CookiesUtil;

/**
 * 
 * @author spathak2 Registraion Servlet
 */

@ServiceDescription("Registration Servlet")
@Component(immediate = true, service = Servlet.class, property = { "sling.servlet.selectors=register",
		"sling.servlet.extensions=json", "sling.servlet.resourcetypes=cq:Page",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST })
public class RegisterServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 7485497505460995864L;
	private static final Logger log = LoggerFactory.getLogger(RegisterServlet.class);

	private static final String FNAME = "FirstName";
	private static final String LNAME = "LastName";
	private static final String EMAIL = "EmailAddress";
	private static final String USERNAME = "Username";
	private static final String PASSWORD = "Password";
	private static final boolean TERMS_CONDITIONS_ACCEPTED = true;
	private static final String CHANNEL_CODE = "EM";
	private static final boolean ISPREFERRED = true;

	@Reference
	private transient ContextAwareConfigUtilService caConfigUtilService;

	@Reference
	private transient LoyaltyService loyaltyService;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(LoyaltyDemoConstants.APPLICATION_JSON);
		response.setCharacterEncoding(LoyaltyDemoConstants.UTF_8);

		final String fname = request.getParameter(FNAME);
		final String lname = request.getParameter(LNAME);
		final String email = request.getParameter(EMAIL);
		final String userName = request.getParameter(USERNAME);
		final String password = request.getParameter(PASSWORD);

		try {
			ContextAwareConfig contextAwareConfig = caConfigUtilService.getContextAwareConfig(request);
			if (contextAwareConfig == null) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
			updateProfileRequest
					.setCardNumber(contextAwareConfig.loyaltyCardnumberPrefix() + generateRandomCardNumber());
			updateProfileRequest.setFirstName(fname);
			updateProfileRequest.setLastName(lname);
			updateProfileRequest.setUsername(userName);
			updateProfileRequest.setPassword(password);
			updateProfileRequest.setTermsConditionsAcceptedInd(TERMS_CONDITIONS_ACCEPTED);
			updateProfileRequest.setSourceCode(contextAwareConfig.loyaltySourceCode());
			updateProfileRequest.setEnrollChannelCode(contextAwareConfig.loyaltyEnrollChannelCode());

			final List<Email> emails = new ArrayList<>();
			final Email emailObj = new Email();
			emailObj.setEmailAddress(email);
			emailObj.setChannelCode(CHANNEL_CODE);
			emailObj.setIsPreferred(ISPREFERRED);

			emails.add(emailObj);
			updateProfileRequest.setEmails(emails);

			final String updateProfileRequestJson = new Gson().toJson(updateProfileRequest);
			final LoyaltyProfile loyaltyProfile = loyaltyService.addProfile(updateProfileRequestJson,
					contextAwareConfig);

			if (loyaltyProfile != null && loyaltyProfile.getStatusCode() == HttpServletResponse.SC_OK
					&& StringUtils.isNotBlank(loyaltyProfile.getProfileId())) {
				setUserLoginTokenDetails(response, contextAwareConfig, updateProfileRequest.getUsername(),
						updateProfileRequest.getPassword());
			}
			response.getWriter().write(CommonUtil.getJsonFromObject(loyaltyProfile));
		} catch (Exception e) {
			CommonUtil.sendGenericResponseError(response, StringUtils.EMPTY);

		}

	}

	private void setUserLoginTokenDetails(SlingHttpServletResponse response, ContextAwareConfig contextAwareConfig,
			String username, String password) {
		try {
			AccessTokenResponse accessTokenResponse = loyaltyService.login(username, password, contextAwareConfig);
			if (accessTokenResponse != null && accessTokenResponse.getSuccess() != null
					&& Boolean.TRUE.equals(accessTokenResponse.getSuccess())
					&& StringUtils.isNotBlank(accessTokenResponse.getAccessToken())
					&& Boolean.FALSE.equals(accessTokenResponse.getIsPasswordExpired())) {
				CookiesUtil.setLoginCookies(response, accessTokenResponse, contextAwareConfig);
			}
		} catch (final Exception e) {
			log.error("RegisterServlet : doPost : Could not login token API error {}", e.getMessage());
		}
	}

	private String generateRandomCardNumber() {
		int length = 21;
		Random random = new Random();
		char[] digits = new char[length];
		digits[0] = (char) (random.nextInt(9) + '1');
		for (int i = 1; i < length; i++) {
			digits[i] = (char) (random.nextInt(10) + '0');
		}
		return new String(digits);

	}
}
