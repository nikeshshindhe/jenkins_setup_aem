package com.rnd.loyaltydemo.core.models;

import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import com.adobe.acs.commons.models.injectors.annotation.HierarchicalPageProperty;
import com.rnd.loyaltydemo.core.utils.LinksUtil;

/**
 * ForgotUsernameModel.
 * 
 * @author Prabhat
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ForgotUsernameModel {

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The heading. */
	@Inject
	@Optional
	private String heading;

	/** The email address Label. */
	@Inject
	@Optional
	private String emailAddressLabel;

	/** The missing email message. */
	@Inject
	@Optional
	private String emailMissing;

	/** The email format message. */
	@Inject
	@Optional
	private String emailFormat;

	/** The Forgot UserName CTA */
	@ChildResource
	private Resource forgotUsernameCta;

	/** The completion heading. */
	@Inject
	@Optional
	private String completionHeading;

	@HierarchicalPageProperty
	private String loginPagePath;

	public String getLoginPagePath() {
		return LinksUtil.checkInternalURLByPath(loginPagePath, resourceResolver);
	}

	/** The Completion CTA */
	@ChildResource
	private Resource completionCta;

	public ResourceResolver getResourceResolver() {
		return resourceResolver;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getEmailMissing() {
		return emailMissing;
	}

	public void setEmailMissing(String emailMissing) {
		this.emailMissing = emailMissing;
	}

	public String getEmailFormat() {
		return emailFormat;
	}

	public void setEmailFormat(String emailFormat) {
		this.emailFormat = emailFormat;
	}

	public String getEmailAddressLabel() {
		return emailAddressLabel;
	}

	public void setEmailAddressLabel(String emailAddressLabel) {
		this.emailAddressLabel = emailAddressLabel;
	}

	public Resource getForgotUsernameCta() {
		return forgotUsernameCta;
	}

	public void setForgotUsernameCta(Resource forgotUsernameCta) {
		this.forgotUsernameCta = forgotUsernameCta;
	}

	public String getCompletionHeading() {
		return completionHeading;
	}

	public void setCompletionHeading(String completionHeading) {
		this.completionHeading = completionHeading;
	}

	public Resource getCompletionCta() {
		return completionCta;
	}

	public void setCompletionCta(Resource completionCta) {
		this.completionCta = completionCta;
	}

}
