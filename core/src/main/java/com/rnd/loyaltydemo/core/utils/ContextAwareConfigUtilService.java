package com.rnd.loyaltydemo.core.utils;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.apache.sling.caconfig.resource.ConfigurationResourceResolver;
import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.rnd.loyaltydemo.core.config.ContextAwareConfig;
import com.rnd.loyaltydemo.core.config.LoyaltyDemoOSGIConfig;
import com.rnd.loyaltydemo.core.constants.GlobalConstants;

/**
 * Context Aware Configuration Utility service class to identify configuration based on
 * the resource
 * 
 */
@Component(service = ContextAwareConfigUtilService.class, immediate = true, property = { "process.label=ContextAware Config Util Service",
		Constants.SERVICE_DESCRIPTION + "=ContextAware Config Util Service", Constants.SERVICE_VENDOR + "=Loyalty Demo"})

public class ContextAwareConfigUtilService {

	private static final String SLING_CONFIG_REF = "sling:configRef";

	/**
	 * Logger configuration for
	 */
	private static final Logger log = LoggerFactory.getLogger(ContextAwareConfigUtilService.class);
	
	@Reference
	private ConfigurationResourceResolver configurationResourceResolver;
	
    @Reference
    private LoyaltyDemoOSGIConfig loyaltyDemoOSGIConfig;
	

	/**
	 * ContextAwareConfig with resource path
	 * 
	 * @param currentPagePath
	 * @param resourceResolver
	 * @return
	 */
	public ContextAwareConfig getContextAwareConfig(final String currentPagePath) {
		if (StringUtils.isNotBlank(currentPagePath)) {
			try {
				return getConfig(currentPagePath);
			} catch (final Exception e) {
				log.error("ContextAwareConfigUtilService::getContextAwareConfig::Exception {}", e.getMessage());
			}
		}
		return null;
	}

	/**
	 * ContextAwareConfig with request object
	 * 
	 * @param request
	 * @return ContextAwareConfig	
	 */
	public ContextAwareConfig getContextAwareConfig(final SlingHttpServletRequest request) {
		try {
			return getConfig(request.getResource().getPath());
		} catch (final Exception e) {
			log.error("ContextAwareConfigUtilService::getContextAwareConfig::Exception {}", e.getMessage());
		}
		return null;
	}
	
	private ContextAwareConfig getConfig(String path) {
		try {
			ResourceResolver resolver = JcrUtilService.getResourceResolver();
			Resource contentResource = resolver.getResource(path);
			log.debug("contentResource {}",contentResource);
			String contextPath = configurationResourceResolver.getContextPath(contentResource);
			if (StringUtils.isBlank(contextPath) || resolver.getResource(contextPath) == null) {
				return null;
			}
			if (contextPath.contains(JcrConstants.JCR_CONTENT)) {
				contextPath = contextPath.replace(GlobalConstants.FORWARD_SLASH + JcrConstants.JCR_CONTENT, StringUtils.EMPTY);
			}
			log.debug("contextPath {}",contextPath);
			ValueMap vm = resolver.resolve(contextPath).adaptTo(Page.class).getProperties();
			if (!vm.containsKey(SLING_CONFIG_REF)) {
				return null;
			}
			String configPath = vm.get(SLING_CONFIG_REF) + GlobalConstants.FORWARD_SLASH + 
					loyaltyDemoOSGIConfig.getLoyaltyApiConfilgBucketName() + GlobalConstants.FORWARD_SLASH + 
					loyaltyDemoOSGIConfig.getLoyaltyApiConfilgPath();
			log.debug("configPath {}",configPath);
			if (resolver.getResource(configPath) == null) {
				return null;
			}
			Resource configResource = resolver.getResource(configPath);
			log.debug("configResource {}",configResource);
			final ConfigurationBuilder configurationBuilder = configResource.adaptTo(ConfigurationBuilder.class);
			if (configurationBuilder != null) {
				return configurationBuilder.as(ContextAwareConfig.class);
			}
		} catch (Exception e) {
			log.error("ContextAwareConfigUtilService::getConfigResource::Exception {}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * Activate.
	 *
	 * @param componentContext the component context
	 * @throws RepositoryException the repository exception
	 * @throws LoginException      the login exception
	 */
	@Activate
	protected void activate(final ComponentContext componentContext) throws RepositoryException, LoginException {
		log.info("ContextAware Config Util :: Activate Method");
	}
}
