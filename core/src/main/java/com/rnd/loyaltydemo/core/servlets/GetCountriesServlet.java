package com.rnd.loyaltydemo.core.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

import com.rnd.loyaltydemo.core.bo.CountriesResponse;
import com.rnd.loyaltydemo.core.bo.Country;
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

@ServiceDescription("Get Country Servlet")
@Component(immediate = true, service = Servlet.class, property = { "sling.servlet.selectors=countries",
		"sling.servlet.extensions=json", "sling.servlet.resourcetypes=cq:Page",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET })
public class GetCountriesServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(GetCountriesServlet.class);
	private static final String STATUS = "A";
	
	@Reference
	private transient ContextAwareConfigUtilService caConfigUtilService;

	
	@Reference
	private transient LoyaltyService loyaltyService;
	
	/**
	 * doGet method for GetCountriesServlet
	 * 
	 * @param SlingHttpServletRequest  - request object
	 * @param SlingHttpServletResponse - response object
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(LoyaltyDemoConstants.APPLICATION_JSON);
		response.setCharacterEncoding(LoyaltyDemoConstants.UTF_8);
		CountriesResponse countriesResponse = null;
		try {
			ContextAwareConfig contextAwareConfig = caConfigUtilService.getContextAwareConfig(request);
			if (contextAwareConfig == null) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			countriesResponse = loyaltyService.getCountries(STATUS,contextAwareConfig);
			if (countriesResponse != null && countriesResponse.getCountries() != null && !countriesResponse.getCountries().isEmpty()) {
				List<Country> countries =countriesResponse
						.getCountries().stream().sorted(
								(country1,country2) -> country1.getCountryName().compareTo(country2.getCountryName()))
						.collect(Collectors.toList());
				countriesResponse.setCountries(countries);
			}
			response.getWriter().write(CommonUtil.getJsonFromObject(countriesResponse));
			    
		}catch(Exception e) {
			log.error("GetCountriesServlet :error occured::{}", e.getMessage());
		}
		
	}
}
