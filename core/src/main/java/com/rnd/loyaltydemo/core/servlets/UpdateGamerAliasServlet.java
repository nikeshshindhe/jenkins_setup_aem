package com.rnd.loyaltydemo.core.servlets;

import java.io.IOException;

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

import com.rnd.loyaltydemo.core.bo.AccessTokenResponse;
import com.rnd.loyaltydemo.core.bo.GenericResponse;
import com.rnd.loyaltydemo.core.bo.LoyaltyProfile;
import com.rnd.loyaltydemo.core.config.ContextAwareConfig;
import com.rnd.loyaltydemo.core.service.LoyaltyService;
import com.rnd.loyaltydemo.core.utils.CommonUtil;
import com.rnd.loyaltydemo.core.utils.ContextAwareConfigUtilService;
import com.rnd.loyaltydemo.core.utils.CookiesUtil;

/**
 * Update Gamer Alias servlet to update gamer profile image and name
 * 
 * @author hgusain
 *
 */
@ServiceDescription("Update Gamer Alias Servlet")
@Component(immediate = true, service = Servlet.class, property = { "sling.servlet.selectors=updateGamerAlias",
		"sling.servlet.extensions=json", "sling.servlet.resourcetypes=cq:Page",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST })
public class UpdateGamerAliasServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 8690269450987124523L;
	private static final Logger log = LoggerFactory.getLogger(UpdateGamerAliasServlet.class);

	private static final String GAMERALIAS = "gamerAlias";

	@Reference
	private transient LoyaltyService loyaltyService;

	@Reference
	private transient ContextAwareConfigUtilService caConfigUtilService;

	/**
	 * doPost method for updating gamer alias details
	 * 
	 * @param SlingHttpServletRequest  - request object
	 * @param SlingHttpServletResponse - response object
	 */
	@Override
	protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {

		log.info("UpdateGamerAliasServlet Post method [Start]");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String gamerAlias = request.getParameter(GAMERALIAS);

		if (StringUtils.isBlank(gamerAlias)) {
			CommonUtil.sendGenericResponseError(response, "Gamer Alias is Empty.");
			return;
		}

		try {
			ContextAwareConfig contextAwareConfig = caConfigUtilService.getContextAwareConfig(request);
			if (contextAwareConfig == null) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			LoyaltyProfile loyaltyProfile = new LoyaltyProfile();
			loyaltyProfile.setGamerAlias(gamerAlias);
			String requestBody = CommonUtil.getJsonFromObject(loyaltyProfile);
			AccessTokenResponse accessTokenResponse = CookiesUtil.getAccessTokenData(request,
					contextAwareConfig.cookiePrefix());
			if (accessTokenResponse == null || StringUtils.isBlank(accessTokenResponse.getAccessToken())
					|| StringUtils.isBlank(accessTokenResponse.getProfileId())) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			GenericResponse genericResponse = loyaltyService.updateGamerAlias(accessTokenResponse.getProfileId(),
					accessTokenResponse.getAccessToken(), requestBody, contextAwareConfig);
			String finalJson = CommonUtil.getJsonFromObject(genericResponse);
			log.info("UpdateGamerAliasServlet Post method [End]");
			response.getWriter().write(finalJson);
		} catch (Exception e) {
			log.error("UpdateGamerAliasServlet :Error Occured::{}", e.getMessage());
			CommonUtil.sendGenericResponseError(response, StringUtils.EMPTY);
		}

	}

}
