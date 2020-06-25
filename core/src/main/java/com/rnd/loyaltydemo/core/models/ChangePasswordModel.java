package com.rnd.loyaltydemo.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

/**
 * ChangePasswordModel.
 * 
 * @author Prabhat
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ChangePasswordModel {

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The heading. */
	@Inject
	@Optional
	private String heading;

	/** The current password Label. */
	@Inject
	@Optional
	private String currentPasswordLabel;

	/** The new password Label. */
	@Inject
	@Optional
	private String newPasswordLabel;

	/** The password Hint. */
	@Inject
	@Optional
	private String passwordHint;

	/** The confirm password Label. */
	@Inject
	@Optional
	private String confirmPasswordLabel;

	/** The update CTA */
	@ChildResource
	private Resource updateCta;

	/** The cancel CTA */
	@ChildResource
	private Resource cancelCta;

	/** The blank password message. */
	@Inject
	@Optional
	private String blankPassword;

	/** The invalid password message. */
	@Inject
	@Optional
	private String invalidPassword;
	
	/** The password mismatch message. */
	@Inject
	@Optional
	private String passwordMismatch;

	/** The success message. */
	@Inject
	@Optional
	private String successMessage;
	
	/** The generic error message. */
	@Inject
	@Optional
	private String genericErrorMessage;
	
	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getGenericErrorMessage() {
		return genericErrorMessage;
	}

	public void setGenericErrorMessage(String genericErrorMessage) {
		this.genericErrorMessage = genericErrorMessage;
	}

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

	public String getCurrentPasswordLabel() {
		return currentPasswordLabel;
	}

	public void setCurrentPasswordLabel(String currentPasswordLabel) {
		this.currentPasswordLabel = currentPasswordLabel;
	}

	public String getNewPasswordLabel() {
		return newPasswordLabel;
	}

	public void setNewPasswordLabel(String newPasswordLabel) {
		this.newPasswordLabel = newPasswordLabel;
	}

	public String getPasswordHint() {
		return passwordHint;
	}

	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}

	public String getConfirmPasswordLabel() {
		return confirmPasswordLabel;
	}

	public void setConfirmPasswordLabel(String confirmPasswordLabel) {
		this.confirmPasswordLabel = confirmPasswordLabel;
	}

	public Resource getUpdateCta() {
		return updateCta;
	}

	public void setUpdateCta(Resource updateCta) {
		this.updateCta = updateCta;
	}

	public Resource getCancelCta() {
		return cancelCta;
	}

	public void setCancelCta(Resource cancelCta) {
		this.cancelCta = cancelCta;
	}

	public String getInvalidPassword() {
		return invalidPassword;
	}

	public void setInvalidPassword(String invalidPassword) {
		this.invalidPassword = invalidPassword;
	}

	public String getPasswordMismatch() {
		return passwordMismatch;
	}

	public void setPasswordMismatch(String passwordMismatch) {
		this.passwordMismatch = passwordMismatch;
	}

	public String getBlankPassword() {
		return blankPassword;
	}

	public void setBlankPassword(String blankPassword) {
		this.blankPassword = blankPassword;
	}

}
