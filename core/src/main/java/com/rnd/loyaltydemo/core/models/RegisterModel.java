package com.rnd.loyaltydemo.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.adobe.acs.commons.models.injectors.annotation.HierarchicalPageProperty;
import com.rnd.loyaltydemo.core.utils.CommonUtil;
import com.rnd.loyaltydemo.core.utils.LinksUtil;
/**
 * 
 * @author spathak2
 * Registration Model Class
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RegisterModel {
    
	@SlingObject
	private ResourceResolver resourceResolver;
	
	@Inject
	private String heading;
	
	@Inject
	private String showAccountNumber;
	
	@Inject
	@Default(values="Account Number")
	private String accountNumberLabel;
	
	@Inject
	@Default(values="First Name")
	private String firstNameLabel;
	
	@Inject
	@Default(values="Last Name")
	private String lastNameLabel;
	
	@Inject
	@Default(values="Email Address")
	private String emailLabel;
	
	@Inject
	@Default(values="Username")
	private String usernameLabel;
	
	@Inject
	@Default(values="Password")
	private String passwordLabel;
	
	@Inject
	private String passwordHint;
	
	@Inject
	@Default(values="Confirm Password")
	private String confirmPwdLabel;
	
	@Inject
	@Default(values="Already have an Account?")
	private String loginText;
	
	@Inject
	@Default(values="SIGN IN")
	private String loginLabel;
	
	@Inject
	private String completionHeading;
	
	@Inject
	@Default(values="Registration failed")
	private String genericErrorMsg;
	
	@Inject
	@Default(values="Please enter account number")
	private String accNumReqErrorMsg;
	
	@Inject
	@Default(values="Please enter first name")
	private String fnameReqErrorMsg;
	
	@Inject
	@Default(values="Please enter last name")
	private String lnameReqErrorMsg;
	
	@Inject
	@Default(values="Please enter email address")
	private String emailReqErrorMsg;
	
	@Inject
	@Default(values="Invalid email address")
	private String emailPatternErrorMsg;
	
	@Inject
	@Default(values="Please enter username")
	private String usernameReqErrorMsg;
	
	@Inject
	@Default(values="Please enter password")
	private String pwdReqErrorMsg;
	@Inject
	@Default(values="Password is not strong")
	private String pwdPatternErrorMsg;
	@Inject
	@Default(values="Please enter confirm password")
	private String confirmPwdReqErrorMsg;
	@Inject
	@Default(values="Confirm password should match password")
	private String confirmPwdMismatchErrorMsg;
	
	@ChildResource
	private Resource registerCTA;
	
	@ChildResource
	private Resource switchToLoginCTA;
	
	@ChildResource
	private Resource completionCTA;
	
	@HierarchicalPageProperty
    private String loginPagePath;
	
	@HierarchicalPageProperty
	private String postLoginPath;
	
	private CTAModel registerModelCTA;
	private CTAModel switchToLoginModelCTA;
	private CTAModel completionModelCTA;
	
	@PostConstruct
	private void init() {
		registerModelCTA = CommonUtil.getCTAModel(registerCTA);
		switchToLoginModelCTA = CommonUtil.getCTAModel(switchToLoginCTA);
		completionModelCTA = CommonUtil.getCTAModel(completionCTA);
	}
	
	public String getHeading() {
		return heading;
	}
	public String getShowAccountNumber() {
		return showAccountNumber;
	}
	public String getAccountNumberLabel() {
		return accountNumberLabel;
	}
	public String getFirstNameLabel() {
		return firstNameLabel;
	}
	public String getLastNameLabel() {
		return lastNameLabel;
	}
	public String getEmailLabel() {
		return emailLabel;
	}
	public String getUsernameLabel() {
		return usernameLabel;
	}
	public String getPasswordLabel() {
		return passwordLabel;
	}
	public String getPasswordHint() {
		return passwordHint;
	}
	public String getConfirmPwdLabel() {
		return confirmPwdLabel;
	}
	public String getLoginText() {
		return loginText;
	}
	public String getLoginLabel() {
		return loginLabel;
	}
	public String getCompletionHeading() {
		return completionHeading;
	}
	public String getGenericErrorMsg() {
		return genericErrorMsg;
	}
	public String getAccNumReqErrorMsg() {
		return accNumReqErrorMsg;
	}
	public String getFnameReqErrorMsg() {
		return fnameReqErrorMsg;
	}
	public String getLnameReqErrorMsg() {
		return lnameReqErrorMsg;
	}
	public String getEmailReqErrorMsg() {
		return emailReqErrorMsg;
	}
	public String getEmailPatternErrorMsg() {
		return emailPatternErrorMsg;
	}
	public String getUsernameReqErrorMsg() {
		return usernameReqErrorMsg;
	}
	public String getPwdReqErrorMsg() {
		return pwdReqErrorMsg;
	}
	public String getPwdPatternErrorMsg() {
		return pwdPatternErrorMsg;
	}
	public String getConfirmPwdReqErrorMsg() {
		return confirmPwdReqErrorMsg;
	}
	public String getConfirmPwdMismatchErrorMsg() {
		return confirmPwdMismatchErrorMsg;
	}
	public CTAModel getRegisterModelCTA() {
		return registerModelCTA;
	}

	public CTAModel getSwitchToLoginModelCTA() {
		return switchToLoginModelCTA;
	}

	public CTAModel getCompletionModelCTA() {
		return completionModelCTA;
	}

	public String getLoginPagePath() {
		return LinksUtil.checkInternalURLByPath(loginPagePath, resourceResolver);
	}

	public String getPostLoginPath() {
		return LinksUtil.checkInternalURLByPath(postLoginPath, resourceResolver);
	}

	
	
	
	
}
