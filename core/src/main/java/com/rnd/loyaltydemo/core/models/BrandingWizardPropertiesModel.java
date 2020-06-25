/**
 * 
 */
package com.rnd.loyaltydemo.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;

/**
 * @author ppriya
 *
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BrandingWizardPropertiesModel {

	private static final Logger log = LoggerFactory.getLogger(BrandingWizardPropertiesModel.class);

	@Inject
	private Resource resource;

	/** Footer colors **/
	@ValueMapValue
	private String footerBgColor;
	@ValueMapValue
	private String footerLinkColor;
	@ValueMapValue
	private String footerLinkHoverColor;
	@ValueMapValue
	private String footerTextColor;

	/** Headers Colors and size . **/
	@ValueMapValue
	private String headerColor;
	@ValueMapValue
	private String h1size;
	@ValueMapValue
	private String h2size;
	@ValueMapValue
	private String h3size;
	@ValueMapValue
	private String h4size;
	@ValueMapValue
	private String h5size;
	@ValueMapValue
	private String h6size;

	/** Primary Button Colors **/
	@ValueMapValue
	private String primaryButtonBgColor;
	@ValueMapValue
	private String primaryButtonColor;
	@ValueMapValue
	private String primaryButtonBorderColor;
	@ValueMapValue
	private String primaryButtonHoverBGColor;
	@ValueMapValue
	private String primaryButtonHoverColor;
	@ValueMapValue
	private String primaryButtonHoverBorderColor;

	/** Secondary Button Colors **/
	@ValueMapValue
	private String secondaryButtonBgColor;
	@ValueMapValue
	private String secondaryButtonColor;
	@ValueMapValue
	private String secondaryButtonBorderColor;
	@ValueMapValue
	private String secondaryButtonHoverBGColor;
	@ValueMapValue
	private String secondaryButtonHoverColor;
	@ValueMapValue
	private String secondaryButtonHoverBorderColor;

	/** Outline CTA Colors **/
	@ValueMapValue
	private String outlineButtonBgColor;
	@ValueMapValue
	private String outlineButtonColor;
	@ValueMapValue
	private String outlineButtonBorderColor;
	@ValueMapValue
	private String outlineButtonHoverBGColor;
	@ValueMapValue
	private String outlineButtonHoverColor;
	@ValueMapValue
	private String outlineButtonHoverBorderColor;

	/** Outline CTA2 Colors **/
	@ValueMapValue
	private String outline2ButtonBgColor;
	@ValueMapValue
	private String outline2ButtonColor;
	@ValueMapValue
	private String outline2ButtonBorderColor;
	@ValueMapValue
	private String outline2ButtonHoverBGColor;
	@ValueMapValue
	private String outline2ButtonHoverColor;
	@ValueMapValue
	private String outline2ButtonHoverBorderColor;

	/** Link CTA Colors **/
	@ValueMapValue
	private String bodyBgColor;
	@ValueMapValue
	private String bodyTextColor;
	@ValueMapValue
	private String fontSizeBase;

	/** Body Colors **/
	@ValueMapValue
	private String globalTextLinkColor;
	@ValueMapValue
	private String globalTextLinkHoverColor;

	/** Success/Error Message Colors **/
	@ValueMapValue
	private String errorWrapperBgColor;
	@ValueMapValue
	private String errorTextColor;
	@ValueMapValue
	private String errorBorderColor;
	@ValueMapValue
	private String successWrapperBgColor;
	@ValueMapValue
	private String successTextColor;
	@ValueMapValue
	private String successBorderColor;

	/** Form Colors **/
	@ValueMapValue
	private String formLabelColor;
	@ValueMapValue
	private String textinputColorBorder;
	@ValueMapValue
	private String checkBoxBorderColor;
	@ValueMapValue
	private String checkboxCheckedColor;
	@ValueMapValue
	private String radioBorderColor;
	@ValueMapValue
	private String radioCheckedColor;
	@ValueMapValue
	private String calendarIconColor;
	@ValueMapValue
	private String calendarTodayBgColor;
	@ValueMapValue
	private String calendarDateHoverBgColor;
	@ValueMapValue
	private String calendarDateHoverTextColor;

	/** Header Colors **/
	@ValueMapValue
	private String mainNavBgColor;
	@ValueMapValue
	private String mainNavTextColor;
	@ValueMapValue
	private String userNavBgColor;
	@ValueMapValue
	private String userNavTextColor;
	@ValueMapValue
	private String hoverActiveBGColor;
	@ValueMapValue
	private String hoverActiveTextColor;
	@ValueMapValue
	private String dropdownBGColor;
	@ValueMapValue
	private String dropdownTextColor;

	@PostConstruct
	protected void init() {

		log.info("resource {}", resource);
		HierarchyNodeInheritanceValueMap iProperties = new HierarchyNodeInheritanceValueMap(resource);

		/** Headers Colors and size . **/
		headerColor = iProperties.getInherited("headerColor", String.class);
		h1size = iProperties.getInherited("h1size", String.class);
		h2size = iProperties.getInherited("h2size", String.class);
		h3size = iProperties.getInherited("h3size", String.class);
		h4size = iProperties.getInherited("h4size", String.class);
		h5size = iProperties.getInherited("h5size", String.class);
		h6size = iProperties.getInherited("h6size", String.class);

		/** Footer colors **/
		footerBgColor = iProperties.getInherited("footerBgColor", String.class);
		footerLinkColor = iProperties.getInherited("footerLinkColor", String.class);
		footerLinkHoverColor = iProperties.getInherited("footerLinkHoverColor", String.class);
		footerTextColor = iProperties.getInherited("footerTextColor", String.class);

		/** Primary Button Colors **/
		primaryButtonBgColor = iProperties.getInherited("primaryButtonBgColor", String.class);
		primaryButtonColor = iProperties.getInherited("primaryButtonColor", String.class);
		primaryButtonBorderColor = iProperties.getInherited("primaryButtonBorderColor", String.class);
		primaryButtonHoverBGColor = iProperties.getInherited("primaryButtonHoverBGColor", String.class);
		primaryButtonHoverColor = iProperties.getInherited("primaryButtonHoverColor", String.class);
		primaryButtonHoverBorderColor = iProperties.getInherited("primaryButtonHoverBorderColor", String.class);

		/** Secondary Button Colors **/
		secondaryButtonBgColor = iProperties.getInherited("secondaryButtonBgColor", String.class);
		secondaryButtonColor = iProperties.getInherited("secondaryButtonColor", String.class);
		secondaryButtonBorderColor = iProperties.getInherited("secondaryButtonBorderColor", String.class);
		secondaryButtonHoverBGColor = iProperties.getInherited("secondaryButtonHoverBGColor", String.class);
		secondaryButtonHoverColor = iProperties.getInherited("secondaryButtonHoverColor", String.class);
		secondaryButtonHoverBorderColor = iProperties.getInherited("secondaryButtonHoverBorderColor", String.class);

		/** outline CTA Button Colors **/
		outlineButtonBgColor = iProperties.getInherited("outlineButtonBgColor", String.class);
		outlineButtonColor = iProperties.getInherited("outlineButtonColor", String.class);
		outlineButtonBorderColor = iProperties.getInherited("outlineButtonBorderColor", String.class);
		outlineButtonHoverBGColor = iProperties.getInherited("outlineButtonHoverBGColor", String.class);
		outlineButtonHoverColor = iProperties.getInherited("outlineButtonHoverColor", String.class);
		outlineButtonHoverBorderColor = iProperties.getInherited("outlineButtonHoverBorderColor", String.class);

		/** outline CTA 2 Button Colors **/
		outline2ButtonBgColor = iProperties.getInherited("outline2ButtonBgColor", String.class);
		outline2ButtonColor = iProperties.getInherited("outline2ButtonColor", String.class);
		outline2ButtonBorderColor = iProperties.getInherited("outline2ButtonBorderColor", String.class);
		outline2ButtonHoverBGColor = iProperties.getInherited("outline2ButtonHoverBGColor", String.class);
		outline2ButtonHoverColor = iProperties.getInherited("outline2ButtonHoverColor", String.class);
		outline2ButtonHoverBorderColor = iProperties.getInherited("outline2ButtonHoverBorderColor", String.class);

		/** Body Colors. **/
		bodyBgColor = iProperties.getInherited("bodyBgColor", String.class);
		bodyTextColor = iProperties.getInherited("bodyTextColor", String.class);
		fontSizeBase = iProperties.getInherited("fontSizeBase", String.class);

		/** Global Link CTA Colors **/
		globalTextLinkColor = iProperties.getInherited("globalTextLinkColor", String.class);
		globalTextLinkHoverColor = iProperties.getInherited("globalTextLinkHoverColor", String.class);

		/** Success/Error message colors **/
		errorWrapperBgColor = iProperties.getInherited("errorWrapperBgColor", String.class);
		errorTextColor = iProperties.getInherited("errorTextColor", String.class);
		errorBorderColor = iProperties.getInherited("errorBorderColor", String.class);
		successWrapperBgColor = iProperties.getInherited("successWrapperBgColor", String.class);
		successTextColor = iProperties.getInherited("successTextColor", String.class);
		successBorderColor = iProperties.getInherited("successBorderColor", String.class);

		/** Form Colors. **/
		formLabelColor = iProperties.getInherited("formLabelColor", String.class);
		textinputColorBorder = iProperties.getInherited("textinputColorBorder", String.class);
		checkBoxBorderColor = iProperties.getInherited("checkBoxBorderColor", String.class);
		checkboxCheckedColor = iProperties.getInherited("checkboxCheckedColor", String.class);
		radioBorderColor = iProperties.getInherited("radioBorderColor", String.class);
		radioCheckedColor = iProperties.getInherited("radioCheckedColor", String.class);
		calendarIconColor = iProperties.getInherited("calendarIconColor", String.class);
		calendarTodayBgColor = iProperties.getInherited("calendarTodayBgColor", String.class);
		calendarDateHoverBgColor = iProperties.getInherited("calendarDateHoverBgColor", String.class);
		calendarDateHoverTextColor = iProperties.getInherited("calendarDateHoverTextColor", String.class);

		/** Header Colors **/
		mainNavBgColor = iProperties.getInherited("headerBgColor", String.class);
		mainNavTextColor = iProperties.getInherited("headerTextColor", String.class);
		userNavBgColor = iProperties.getInherited("headerLinkColor", String.class);
		userNavTextColor = iProperties.getInherited("headerLinkHoverColor", String.class);
		hoverActiveBGColor = iProperties.getInherited("navBarBgColor", String.class);
		hoverActiveTextColor = iProperties.getInherited("navBarToggleIconColor", String.class);
		dropdownBGColor = iProperties.getInherited("navBarToggleIconColorMobile", String.class);
		dropdownTextColor = iProperties.getInherited("navLinkColor", String.class);

	}

	/**
	 * @return the footerBgColor
	 */
	public String getFooterBgColor() {
		return footerBgColor;
	}

	/**
	 * @return the footerLinkColor
	 */
	public String getFooterLinkColor() {
		return footerLinkColor;
	}

	/**
	 * @return the footerLinkHoverColor
	 */
	public String getFooterLinkHoverColor() {
		return footerLinkHoverColor;
	}

	/**
	 * @return the footerTextColor
	 */
	public String getFooterTextColor() {
		return footerTextColor;
	}

	/**
	 * @return the headerColor
	 */
	public String getHeaderColor() {
		return headerColor;
	}

	/**
	 * @return the h1size
	 */
	public String getH1size() {
		return h1size;
	}

	/**
	 * @return the h2size
	 */
	public String getH2size() {
		return h2size;
	}

	/**
	 * @return the h3size
	 */
	public String getH3size() {
		return h3size;
	}

	/**
	 * @return the h4size
	 */
	public String getH4size() {
		return h4size;
	}

	/**
	 * @return the h5size
	 */
	public String getH5size() {
		return h5size;
	}

	/**
	 * @return the h6size
	 */
	public String getH6size() {
		return h6size;
	}

	/**
	 * @return the primaryButtonBgColor
	 */
	public String getPrimaryButtonBgColor() {
		return primaryButtonBgColor;
	}

	/**
	 * @return the primaryButtonColor
	 */
	public String getPrimaryButtonColor() {
		return primaryButtonColor;
	}

	/**
	 * @return the primaryButtonBorderColor
	 */
	public String getPrimaryButtonBorderColor() {
		return primaryButtonBorderColor;
	}

	/**
	 * @return the primaryButtonHoverBGColor
	 */
	public String getPrimaryButtonHoverBGColor() {
		return primaryButtonHoverBGColor;
	}

	/**
	 * @return the primaryButtonHoverColor
	 */
	public String getPrimaryButtonHoverColor() {
		return primaryButtonHoverColor;
	}

	/**
	 * @return the primaryButtonHoverBorderColor
	 */
	public String getPrimaryButtonHoverBorderColor() {
		return primaryButtonHoverBorderColor;
	}

	/**
	 * @return the secondaryButtonBgColor
	 */
	public String getSecondaryButtonBgColor() {
		return secondaryButtonBgColor;
	}

	/**
	 * @return the secondaryButtonColor
	 */
	public String getSecondaryButtonColor() {
		return secondaryButtonColor;
	}

	/**
	 * @return the secondaryButtonBorderColor
	 */
	public String getSecondaryButtonBorderColor() {
		return secondaryButtonBorderColor;
	}

	/**
	 * @return the secondaryButtonHoverBGColor
	 */
	public String getSecondaryButtonHoverBGColor() {
		return secondaryButtonHoverBGColor;
	}

	/**
	 * @return the secondaryButtonHoverColor
	 */
	public String getSecondaryButtonHoverColor() {
		return secondaryButtonHoverColor;
	}

	/**
	 * @return the secondaryButtonHoverBorderColor
	 */
	public String getSecondaryButtonHoverBorderColor() {
		return secondaryButtonHoverBorderColor;
	}

	/**
	 * @return the outlineButtonBgColor
	 */
	public String getOutlineButtonBgColor() {
		return outlineButtonBgColor;
	}

	/**
	 * @return the outlineButtonColor
	 */
	public String getOutlineButtonColor() {
		return outlineButtonColor;
	}

	/**
	 * @return the outlineButtonBorderColor
	 */
	public String getOutlineButtonBorderColor() {
		return outlineButtonBorderColor;
	}

	/**
	 * @return the outlineButtonHoverBGColor
	 */
	public String getOutlineButtonHoverBGColor() {
		return outlineButtonHoverBGColor;
	}

	/**
	 * @return the outlineButtonHoverColor
	 */
	public String getOutlineButtonHoverColor() {
		return outlineButtonHoverColor;
	}

	/**
	 * @return the outlineButtonHoverBorderColor
	 */
	public String getOutlineButtonHoverBorderColor() {
		return outlineButtonHoverBorderColor;
	}

	/**
	 * @return the outline2ButtonBgColor
	 */
	public String getOutline2ButtonBgColor() {
		return outline2ButtonBgColor;
	}

	/**
	 * @return the outline2ButtonColor
	 */
	public String getOutline2ButtonColor() {
		return outline2ButtonColor;
	}

	/**
	 * @return the outline2ButtonBorderColor
	 */
	public String getOutline2ButtonBorderColor() {
		return outline2ButtonBorderColor;
	}

	/**
	 * @return the outline2ButtonHoverBGColor
	 */
	public String getOutline2ButtonHoverBGColor() {
		return outline2ButtonHoverBGColor;
	}

	/**
	 * @return the outline2ButtonHoverColor
	 */
	public String getOutline2ButtonHoverColor() {
		return outline2ButtonHoverColor;
	}

	/**
	 * @return the outline2ButtonHoverBorderColor
	 */
	public String getOutline2ButtonHoverBorderColor() {
		return outline2ButtonHoverBorderColor;
	}

	/**
	 * @return the bodyBgColor
	 */
	public String getBodyBgColor() {
		return bodyBgColor;
	}

	/**
	 * @return the bodyTextColor
	 */
	public String getBodyTextColor() {
		return bodyTextColor;
	}

	/**
	 * @return the fontSizeBase
	 */
	public String getFontSizeBase() {
		return fontSizeBase;
	}

	/**
	 * @return the globalTextLinkColor
	 */
	public String getGlobalTextLinkColor() {
		return globalTextLinkColor;
	}

	/**
	 * @return the globalTextLinkHoverColor
	 */
	public String getGlobalTextLinkHoverColor() {
		return globalTextLinkHoverColor;
	}

	/**
	 * @return the errorWrapperBgColor
	 */
	public String getErrorWrapperBgColor() {
		return errorWrapperBgColor;
	}

	/**
	 * @return the errorTextColor
	 */
	public String getErrorTextColor() {
		return errorTextColor;
	}

	/**
	 * @return the errorBorderColor
	 */
	public String getErrorBorderColor() {
		return errorBorderColor;
	}

	/**
	 * @return the successWrapperBgColor
	 */
	public String getSuccessWrapperBgColor() {
		return successWrapperBgColor;
	}

	/**
	 * @return the successTextColor
	 */
	public String getSuccessTextColor() {
		return successTextColor;
	}

	/**
	 * @return the successBorderColor
	 */
	public String getSuccessBorderColor() {
		return successBorderColor;
	}

	/**
	 * @return the formLabelColor
	 */
	public String getFormLabelColor() {
		return formLabelColor;
	}

	/**
	 * @return the textinputColorBorder
	 */
	public String getTextinputColorBorder() {
		return textinputColorBorder;
	}

	/**
	 * @return the checkBoxBorderColor
	 */
	public String getCheckBoxBorderColor() {
		return checkBoxBorderColor;
	}


	/**
	 * @return the checkboxCheckedColor
	 */
	public String getCheckboxCheckedColor() {
		return checkboxCheckedColor;
	}

	/**
	 * @return the radioBorderColor
	 */
	public String getRadioBorderColor() {
		return radioBorderColor;
	}
	/**
	 * @return the radioCheckedColor
	 */
	public String getRadioCheckedColor() {
		return radioCheckedColor;
	}

	/**
	 * @return the calendarIconColor
	 */
	public String getCalendarIconColor() {
		return calendarIconColor;
	}

	/**
	 * @return the calendarTodayBgColor
	 */
	public String getCalendarTodayBgColor() {
		return calendarTodayBgColor;
	}

	/**
	 * @return the calendarDateHoverBgColor
	 */
	public String getCalendarDateHoverBgColor() {
		return calendarDateHoverBgColor;
	}

	/**
	 * @return the calendarDateHoverTextColor
	 */
	public String getCalendarDateHoverTextColor() {
		return calendarDateHoverTextColor;
	}

	/**
	 * @return the mainNavBgColor
	 */
	public String getMainNavBgColor() {
		return mainNavBgColor;
	}

	/**
	 * @return the mainNavTextColor
	 */
	public String getMainNavTextColor() {
		return mainNavTextColor;
	}

	/**
	 * @return the userNavBgColor
	 */
	public String getUserNavBgColor() {
		return userNavBgColor;
	}

	/**
	 * @return the userNavTextColor
	 */
	public String getUserNavTextColor() {
		return userNavTextColor;
	}

	/**
	 * @return the hoverActiveBGColor
	 */
	public String getHoverActiveBGColor() {
		return hoverActiveBGColor;
	}

	/**
	 * @return the hoverActiveTextColor
	 */
	public String getHoverActiveTextColor() {
		return hoverActiveTextColor;
	}

	/**
	 * @return the dropdownBGColor
	 */
	public String getDropdownBGColor() {
		return dropdownBGColor;
	}

	/**
	 * @return the dropdownTextColor
	 */
	public String getDropdownTextColor() {
		return dropdownTextColor;
	}

}
