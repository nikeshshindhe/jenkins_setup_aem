package com.rnd.loyaltydemo.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

/**
 * Generic IconModel Class
 * 
 * @author iguddu
 *
 */
		
@Model(adaptables= Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class IconModel extends CTAModel {
	
	@Inject
	@Optional
	private String ctaNavIcon;
	
	@Inject
	@Optional
	private boolean ctaCheckBox;
	
	private boolean active;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCtaNavIcon() {
		return ctaNavIcon;
	}

	public void setCtaNavIcon(String ctaNavIcon) {
		this.ctaNavIcon = ctaNavIcon;
	}

	public boolean isCtaCheckBox() {
		return ctaCheckBox;
	}

	public void setCtaCheckBox(boolean ctaCheckBox) {
		this.ctaCheckBox = ctaCheckBox;
	}	
}
