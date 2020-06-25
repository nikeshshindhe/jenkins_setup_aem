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

import com.rnd.loyaltydemo.core.bo.LoyaltyValidationResponse;
import com.rnd.loyaltydemo.core.config.ContextAwareConfig;
import com.rnd.loyaltydemo.core.constants.LoyaltyDemoConstants;
import com.rnd.loyaltydemo.core.service.LoyaltyService;
import com.rnd.loyaltydemo.core.utils.CommonUtil;
/**
 * 
 * @author spathak2
 * Verify User Name Servlet
 */
import com.rnd.loyaltydemo.core.utils.ContextAwareConfigUtilService;

@ServiceDescription("Verify UserName Servlet")
@Component(immediate = true, service = Servlet.class, property = { "sling.servlet.selectors=verifyUsername",
		"sling.servlet.extensions=json", "sling.servlet.resourcetypes=cq:Page",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST })
public class VerifyUsernameServlet extends SlingAllMethodsServlet{
    
	private static final long serialVersionUID = 7485497505460995864L;	
	private static final String USERNAME = "Username";
	
	@Reference
	private transient ContextAwareConfigUtilService caConfigUtilService;
	
	@Reference
	private transient LoyaltyService loyaltyService;    
	
	@Override
	protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(LoyaltyDemoConstants.APPLICATION_JSON);
		response.setCharacterEncoding(LoyaltyDemoConstants.UTF_8);
				
		final String userName = request.getParameter(USERNAME);
		
		try {
			ContextAwareConfig contextAwareConfig = caConfigUtilService.getContextAwareConfig(request);
			if (contextAwareConfig == null || StringUtils.isBlank(userName)) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			LoyaltyValidationResponse genericResponse = loyaltyService.verifyUsername(userName, contextAwareConfig);
			response.getWriter().write(CommonUtil.getJsonFromObject(genericResponse));
		}catch (Exception e) {
			CommonUtil.sendGenericResponseError(response, StringUtils.EMPTY);
			
		}
	}
}
