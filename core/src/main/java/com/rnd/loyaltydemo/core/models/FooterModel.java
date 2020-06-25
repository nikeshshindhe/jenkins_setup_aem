package com.rnd.loyaltydemo.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.rnd.loyaltydemo.core.utils.LinksUtil;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterModel {
	@SlingObject
	private ResourceResolver resourceResolver;
	@Inject
	@Named("logoRef")
	private String logoUrl;
	@Inject
	private String logoLink;
	@Inject
	private String altText;
	@Inject
	private String copyright;
	@Inject
	private String optionalText;
	@Inject
	@Named("list")

	private List<LabelLink> labellinks;

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getLogoLink() {
		return LinksUtil.checkInternalURLByPath(logoLink, resourceResolver);
	}

	public void setLogoLink(String logoLink) {
		this.logoLink = logoLink;
	}

	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getOptionalText() {
		return optionalText;
	}

	public void setOptionalText(String optionalText) {
		this.optionalText = optionalText;
	}

	public List<LabelLink> getLabellinks() {
		return labellinks;
	}

	public void setLabellinks(List<LabelLink> labellinks) {
		this.labellinks = labellinks;
	}
}
