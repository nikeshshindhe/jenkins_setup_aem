package com.rnd.loyaltydemo.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author spathak2
 *
 */

import com.rnd.loyaltydemo.core.bo.GenderResponse;
import com.rnd.loyaltydemo.core.config.ContextAwareConfig;
import com.rnd.loyaltydemo.core.constants.LoyaltyDemoConstants;
import com.rnd.loyaltydemo.core.service.LoyaltyService;
import com.rnd.loyaltydemo.core.utils.CommonUtil;
import com.rnd.loyaltydemo.core.utils.ContextAwareConfigUtilService;
/**
 * 
 * @author spathak2
 *
 */
@ServiceDescription("Get Gender List Servlet")
@Component(immediate = true, service = Servlet.class, property = { "sling.servlet.selectors=genders",
		"sling.servlet.extensions=json", "sling.servlet.resourcetypes=cq:Page",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET })
public class GetGenderListServlet extends SlingAllMethodsServlet{

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(GetGenderListServlet.class);
	private static final String STATUS = "A";
	
	@Reference
	private transient ContextAwareConfigUtilService caConfigUtilService;
	
	@Reference
	private transient LoyaltyService loyaltyService;
	
	/**
	 * doGet method for GetGenderListServlet
	 * 
	 * @param SlingHttpServletRequest  - request object
	 * @param SlingHttpServletResponse - response object
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(LoyaltyDemoConstants.APPLICATION_JSON);
		response.setCharacterEncoding(LoyaltyDemoConstants.UTF_8);
		try {
				ContextAwareConfig contextAwareConfig = caConfigUtilService.getContextAwareConfig(request);
				if (contextAwareConfig == null) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return;
				}
				GenderResponse genderResponse =  loyaltyService.getGenderList(STATUS,contextAwareConfig);
				response.getWriter().write(CommonUtil.getJsonFromObject(genderResponse));
			}catch(Exception e) {
				log.error("GetGenderListServlet :error occured::{}", e.getMessage());
			}
	}
}
