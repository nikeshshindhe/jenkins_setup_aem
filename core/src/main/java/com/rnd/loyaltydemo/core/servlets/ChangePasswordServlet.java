package com.rnd.loyaltydemo.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rnd.loyaltydemo.core.bo.AccessTokenResponse;
import com.rnd.loyaltydemo.core.bo.ChangePasswordRequest;
import com.rnd.loyaltydemo.core.bo.GenericResponse;
import com.rnd.loyaltydemo.core.config.ContextAwareConfig;
import com.rnd.loyaltydemo.core.constants.CookieConstants;
import com.rnd.loyaltydemo.core.constants.LoyaltyDemoConstants;
import com.rnd.loyaltydemo.core.service.LoyaltyService;
import com.rnd.loyaltydemo.core.utils.CommonUtil;
import com.rnd.loyaltydemo.core.utils.ContextAwareConfigUtilService;
import com.rnd.loyaltydemo.core.utils.CookiesUtil;

/**
 * ChangePassword Servlet class
 * 
 */

@ServiceDescription("ChangePassword Servlet")
@Component(immediate = true, service = Servlet.class, property = { "sling.servlet.selectors=changePassword",
		"sling.servlet.extensions=json", "sling.servlet.resourcetypes=cq:Page",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST })
public class ChangePasswordServlet extends SlingAllMethodsServlet {
	private static final long serialVersionUID = 7485497505460995864L;
	private static final Logger log = LoggerFactory.getLogger(ChangePasswordServlet.class);

	@Reference
	private transient ResourceResolverFactory resolverFactory;

	@Reference
	private transient LoyaltyService loyaltyService;
	transient ChangePasswordRequest changePasswordRequest;

	@Reference
	private transient ContextAwareConfigUtilService caConfigUtilService;
	
	private String username = StringUtils.EMPTY;

	/**
	 * doPost method for authenticating user
	 * 
	 * @param SlingHttpServletRequest  - request object
	 * @param SlingHttpServletResponse - response object
	 * 
	 */
	@Override
	protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType(LoyaltyDemoConstants.APPLICATION_JSON);
		response.setCharacterEncoding(LoyaltyDemoConstants.UTF_8);

		ContextAwareConfig contextAwareConfig = null;
		try {

			contextAwareConfig = caConfigUtilService.getContextAwareConfig(request);

			if (contextAwareConfig == null) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			AccessTokenResponse accessTokenResponse = CookiesUtil.getAccessTokenData(request,
					contextAwareConfig.cookiePrefix());
			if (accessTokenResponse == null || StringUtils.isBlank(accessTokenResponse.getAccessToken())
					|| StringUtils.isBlank(accessTokenResponse.getProfileId())) {
				log.error("Profile ID/Access Token is not available in the cookie.");
				CommonUtil.sendGenericResponseError(response, "Session Expired.");
				return;
			}
			changePasswordRequest = new ChangePasswordRequest();

			username = CookiesUtil.getCookie(request, contextAwareConfig.cookiePrefix(), 
					CookieConstants.USER_NAME_COOKIE_NAME);
			if (!isValidRequest(request, response)) {
				log.error("Invalid request data password empty or not matching");
				return;
			}
			changePasswordRequest.setUsername(username);
			final String requestJson = CommonUtil.getJsonFromObject(changePasswordRequest);
			GenericResponse changePasswordResponse = loyaltyService.changePassword(accessTokenResponse.getAccessToken(),
					accessTokenResponse.getProfileId(), accessTokenResponse.getUsername(), requestJson,
					contextAwareConfig);
			if (changePasswordResponse != null && changePasswordResponse.getStatusCode() == HttpServletResponse.SC_OK) {
				changePasswordResponse.setMessage("Password changed successfully");
				log.debug("Password changed successfully");
				response.setStatus(HttpServletResponse.SC_OK);
			}
			response.getWriter().write(CommonUtil.getJsonFromObject(changePasswordResponse));
		} catch (final Exception e) {
			log.error("Exception {}", e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private boolean isValidRequest(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {
		if (StringUtils.isNotBlank(username)) {
			changePasswordRequest.setUsername(username);
		} else {
			CommonUtil.sendGenericResponseError(response, "UserName should not be empty");
			return Boolean.FALSE;
		}

		String currentPwd = request.getParameter("currentPwd");
		if (StringUtils.isNotBlank(currentPwd)) {
			changePasswordRequest.setCurrentPassword(currentPwd);
		} else {
			CommonUtil.sendGenericResponseError(response, "Current Password should not be empty");
			return Boolean.FALSE;
		}
		String newPwd = request.getParameter("newPwd");
		String confirmPwd = request.getParameter("confirmPwd");

		if (StringUtils.isNotBlank(newPwd) && newPwd.equals(confirmPwd)) {
			changePasswordRequest.setNewPassword(newPwd);
		} else {
			CommonUtil.sendGenericResponseError(response, "Password mismatch.");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

}
