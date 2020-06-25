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
import com.rnd.loyaltydemo.core.utils.CommonUtil;
import com.rnd.loyaltydemo.core.utils.LinksUtil;

/**
 * LoginModel.
 * 
 * @author hgusain
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LoginModel {

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The heading. */
	@Inject
	@Optional
	private String heading;

	/** The user name Label. */
	@Inject
	@Optional
	private String usernameLabel;

	/** The user name empty message. */
	@Inject
	@Optional
	private String usernameEmptyMsg;

	/** The forgot user name label. */
	@Inject
	@Optional
	private String forgotUsernameLabel;

	/** The forgot user name link. */
	@Inject
	@Optional
	private String forgotUsernameLink;

	/** The password Label. */
	@Inject
	@Optional
	private String passwordLabel;

	/** The password empty message. */
	@Inject
	@Optional
	private String passwordEmptyMsg;

	/** The forgot password label. */
	@Inject
	@Optional
	private String forgotPasswordLabel;

	/** The forgot password link. */
	@Inject
	@Optional
	private String forgotPasswordLink;

	/** The remember me label. */
	@Inject
	@Optional
	private String rememberMeLabel;

	/** The Login CTA */
	@ChildResource
	private Resource logincta;

	/** The Register CTA */
	@ChildResource
	private Resource registercta;
	
	@Inject
	@Optional
	private String registerText;

	/** The Generic Error. */
	@Inject
	@Optional
	private String genericErrorMsg;

	/** The Password Expire Error. */
	@Inject
	@Optional
	private String pwdExpiredErrorMsg;
	
	@HierarchicalPageProperty
    private String registerPath;
	
	@HierarchicalPageProperty
    private String postLoginPath;
	
	public String getRegisterPath() {
		return LinksUtil.checkInternalURLByPath(registerPath, resourceResolver); 
	}
	
	public String getPostLoginPath() {
		return LinksUtil.checkInternalURLByPath(postLoginPath, resourceResolver); 
	}
	
	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getUsernameLabel() {
		return usernameLabel;
	}

	public void setUsernameLabel(String usernameLabel) {
		this.usernameLabel = usernameLabel;
	}

	public String getUsernameEmptyMsg() {
		return usernameEmptyMsg;
	}

	public void setUsernameEmptyMsg(String usernameEmptyMsg) {
		this.usernameEmptyMsg = usernameEmptyMsg;
	}

	public String getForgotUsernameLabel() {
		return forgotUsernameLabel;
	}

	public void setForgotUsernameLabel(String forgotUsernameLabel) {
		this.forgotUsernameLabel = forgotUsernameLabel;
	}

	public String getForgotUsernameLink() {
		return LinksUtil.checkInternalURLByPath(forgotUsernameLink, resourceResolver);
	}

	public void setForgotUsernameLink(String forgotUsernameLink) {
		this.forgotUsernameLink = forgotUsernameLink;
	}

	public String getPasswordLabel() {
		return passwordLabel;
	}

	public void setPasswordLabel(String passwordLabel) {
		this.passwordLabel = passwordLabel;
	}

	public String getPasswordEmptyMsg() {
		return passwordEmptyMsg;
	}

	public void setPasswordEmptyMsg(String passwordEmptyMsg) {
		this.passwordEmptyMsg = passwordEmptyMsg;
	}

	public String getForgotPasswordLabel() {
		return forgotPasswordLabel;
	}

	public void setForgotPasswordLabel(String forgotPasswordLabel) {
		this.forgotPasswordLabel = forgotPasswordLabel;
	}

	public String getForgotPasswordLink() {
		return LinksUtil.checkInternalURLByPath(forgotPasswordLink, resourceResolver);
	}

	public void setForgotPasswordLink(String forgotPasswordLink) {
		this.forgotPasswordLink = forgotPasswordLink;
	}

	public String getRememberMeLabel() {
		return rememberMeLabel;
	}

	public void setRememberMeLabel(String rememberMeLabel) {
		this.rememberMeLabel = rememberMeLabel;
	}

	public CTAModel getLogincta() {
		return CommonUtil.getCTAModel(logincta);
	}

	public void setLogincta(Resource logincta) {
		this.logincta = logincta;
	}
	
	public CTAModel getRegistercta() {
		return CommonUtil.getCTAModel(registercta);
	}

	public void setRegistercta(Resource registercta) {
		this.registercta = registercta;
	}
	
	public String getRegisterText() {
		return registerText;
	}

	public void setRegisterText(String registerText) {
		this.registerText = registerText;
	}

	public String getGenericErrorMsg() {
		return genericErrorMsg;
	}

	public void setGenericErrorMsg(String genericErrorMsg) {
		this.genericErrorMsg = genericErrorMsg;
	}

	public String getPwdExpiredErrorMsg() {
		return pwdExpiredErrorMsg;
	}

	public void setPwdExpiredErrorMsg(String pwdExpiredErrorMsg) {
		this.pwdExpiredErrorMsg = pwdExpiredErrorMsg;
	}

}
