package com.rnd.loyaltydemo.core.utils;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Locale.Builder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;

/**
 * Localization utility class
 * 
 */

public class LocalizationUtil {

	private LocalizationUtil() {
		// Restricting Instantiation
	}

	/**
	 * Logger configuration for LocalizationUtil
	 */
	private static final Logger log = LoggerFactory.getLogger(LocalizationUtil.class);

	
	/**
	 * Get locale code from site level configuration
	 * 
	 * @param pageResource
	 * @return locale code
	 */
	private static String getLocaleString(Page page) {
		if (page != null) {
			InheritanceValueMap inheritanceValueMap = new HierarchyNodeInheritanceValueMap(page.getContentResource());
			String locale = inheritanceValueMap.getInherited(JcrConstants.JCR_LANGUAGE, String.class);
			return StringUtils.isNotBlank(locale) ? locale : StringUtils.EMPTY;
		}
		return StringUtils.EMPTY;
	}

    public static Locale getLocale(Page page) {
    	try {
	    	String locale = getLocaleString(page);
	    	String [] localeArr = locale.split("_");
	    	String language = localeArr[0];
	    	String country = (localeArr.length >= 2) ? localeArr[1] : "";
	    	if (StringUtils.isNotBlank(country)  || StringUtils.isNotBlank(language)) {
	    		Builder builder = new Locale.Builder();
	    		if (StringUtils.isNotBlank(language)) {
					builder.setLanguage(language);
				}
	    		if (StringUtils.isNotBlank(country)) {
					builder.setRegion(country.toUpperCase());
				}
				return builder.build();
			}
    	} catch (Exception e) {
			log.error("LocalizationUtil : getLocale : Exception: {}", e);
		}
    	return Locale.ENGLISH;
    }

    public static String numberFormaterByLocale(int number,Page page) {
    	NumberFormat nf = NumberFormat.getInstance(getLocale(page));
        return nf.format(number);
    }
    
    public static String numberFormaterByLocale(Long number,Page page) {
    	NumberFormat nf = NumberFormat.getInstance(getLocale(page));
        return nf.format(number);
    }

    public static String numberFormaterByLocale(Double number,Page page) {
    	NumberFormat nf = NumberFormat.getInstance(getLocale(page));
        return nf.format(number);
    }

    public static String currencyFormaterByLocale(Double ammount,Page page) {
    	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(getLocale(page));
        return currencyFormatter.format(ammount);
    }
}
