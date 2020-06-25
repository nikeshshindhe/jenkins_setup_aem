package com.rnd.loyaltydemo.core.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

import com.rnd.loyaltydemo.core.bo.State;
import com.rnd.loyaltydemo.core.bo.StatesResponse;
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
@ServiceDescription("Get State Servlet")
@Component(immediate = true, service = Servlet.class, property = { "sling.servlet.selectors=states",
		"sling.servlet.extensions=json", "sling.servlet.resourcetypes=cq:Page",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET })
public class GetStatesServlet extends SlingAllMethodsServlet{

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(GetStatesServlet.class);
	private static final String STATUS = "A";
	private static final String COUNTRY = "countryCode";
	
	@Reference
	private transient ContextAwareConfigUtilService caConfigUtilService;

	
	@Reference
	private transient LoyaltyService loyaltyService;
	
	/**
	 * doGet method for GetStatesServlet
	 * 
	 * @param SlingHttpServletRequest  - request object
	 * @param SlingHttpServletResponse - response object
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(LoyaltyDemoConstants.APPLICATION_JSON);
		response.setCharacterEncoding(LoyaltyDemoConstants.UTF_8);
		String country = request.getParameter(COUNTRY);
		
		try {
			ContextAwareConfig contextAwareConfig = caConfigUtilService.getContextAwareConfig(request);
			if (contextAwareConfig == null || StringUtils.isBlank(country)) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			StatesResponse statesResponse = loyaltyService.getStates(STATUS,country,contextAwareConfig);
			if (statesResponse != null && statesResponse.getStates() != null && !statesResponse.getStates().isEmpty()) {
				List<State> states =statesResponse
						.getStates().stream().sorted(
								(country1,country2) -> country1.getStateName().compareTo(country2.getStateName()))
						.collect(Collectors.toList());
				statesResponse.setStates(states);
			}
			response.getWriter().write(CommonUtil.getJsonFromObject(statesResponse));
			
		}catch(Exception e) {
			log.error("GetStatesServlet :error occured::{}", e.getMessage());
		}
	}
}
