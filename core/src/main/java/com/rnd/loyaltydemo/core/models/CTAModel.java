package com.rnd.loyaltydemo.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.rnd.loyaltydemo.core.utils.LinksUtil;

/**
 * Generic CTAModel Class
 * 
 * @author hgusain
 *
 */
		
@Model(adaptables= Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CTAModel {
	
	@SlingObject
	ResourceResolver resourceResolver;
	
	@Inject
	@Optional
	private String ctaLabel;
	
	@Inject
	@Optional
	private String ctaLink;
	
	@Inject
	@Optional
	private String ctaType;
	
	@Inject
	@Optional
	private String ctaTarget;
	
	@PostConstruct
    protected void init() {
        if (StringUtils.isNotBlank(ctaLink)) {
        	ctaLink = LinksUtil.checkInternalExternalURLByResource(ctaLink, resourceResolver.getResource(ctaLink));
        }
    }

	public String getCtaTarget() {
		return ctaTarget;
	}

	public void setCtaTarget(String ctaTarget) {
		this.ctaTarget = ctaTarget;
	}

	public String getCtaLabel() {
		return ctaLabel;
	}

	public void setCtaLabel(String ctaLabel) {
		this.ctaLabel = ctaLabel;
	}

	public String getCtaLink() {
		return ctaLink;
	}

	public void setCtaLink(String ctaLink) {
		this.ctaLink = ctaLink;
	}

	public String getCtaType() {
		return ctaType;
	}

	public void setCtaType(String ctaType) {
		this.ctaType = ctaType;
	}
	
}
