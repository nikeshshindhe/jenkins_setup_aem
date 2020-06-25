package com.rnd.loyaltydemo.core.utils;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.acs.commons.util.CookieUtil;
import com.rnd.loyaltydemo.core.bo.AccessTokenResponse;
import com.rnd.loyaltydemo.core.config.ContextAwareConfig;
import com.rnd.loyaltydemo.core.constants.CookieConstants;

/**
 * Cookies utility class
 * 
 */

public class CookiesUtil {

	static final String URL_ENCODED_SPACE = "+";
	static final String ENCODED_SPACE = "%20";

	private CookiesUtil() {
		// Restricting Instantiation
	}

	/**
	 * Logger configuration for CookiesUtil
	 */
	private static final Logger log = LoggerFactory.getLogger(CookiesUtil.class);

	/**
	 * Setting cookie info to AccessTokenResponse POJO
	 * 
	 * @param request
	 * @param cookiePrefix
	 * @return AccessTokenResponse
	 */
	public static AccessTokenResponse getAccessTokenData(SlingHttpServletRequest request, String cookiePrefix) {
		AccessTokenResponse accessTokenResponse = null;
		try {
			final String loyaltyAccessToken = getCookie(request,
					cookiePrefix,CookieConstants.ACCESS_TOKEN_COOKIE_NAME);
			final String loyaltyRefreshToken = getCookie(request,
					cookiePrefix , CookieConstants.REFRESH_TOKEN_COOKIE_NAME);
			final String profileId = getCookie(request,
					cookiePrefix , CookieConstants.PROFILE_ID_COOKIE_NAME);
			final String loyaltyUserName = getCookie(request,
					cookiePrefix , CookieConstants.USER_NAME_COOKIE_NAME);
			if (StringUtils.isNotBlank(loyaltyAccessToken) && StringUtils.isNotBlank(profileId)) {
				accessTokenResponse = new AccessTokenResponse();
				accessTokenResponse.setAccessToken(loyaltyAccessToken);
				accessTokenResponse.setProfileId(profileId);
				if (StringUtils.isNotBlank(loyaltyRefreshToken)) {
					accessTokenResponse.setRefreshToken(loyaltyRefreshToken);
				}
				if (StringUtils.isNotBlank(loyaltyUserName)) {
					accessTokenResponse.setUsername(loyaltyUserName);
				}
			}
		}catch (Exception e) {
			log.error("CookiesUtil : getAccessTokenData : Exception: {}", e);
		}
		log.debug("accessTokenResponse {}",accessTokenResponse);
		return accessTokenResponse;
	}

	/**
	 * Creating cookie while logging in
	 * 
	 * @param response
	 * @param prefix
	 * @param cookieName
	 * @param value
	 * @param expiry
	 * @param cookiePath
	 * @param secure
	 * @param httpSecure
	 * @return Cookie
	 */
	public static Cookie createCookie(SlingHttpServletResponse response, String prefix, String cookieName, String value,
			int expiry, String cookiePath, boolean secure, boolean httpSecure) {
		try {
			log.debug("create ck prefix --> {} name --> {} val --> {}",prefix,cookieName,value);
			if (StringUtils.isNotBlank(value)) {
				value = URLEncoder.encode(value,StandardCharsets.UTF_8.toString());
				value = value.replace(URL_ENCODED_SPACE, ENCODED_SPACE);
				final Cookie cookie = new Cookie(prefix + cookieName, value);
				cookie.setMaxAge(expiry);
				cookie.setPath(cookiePath);
				cookie.setSecure(secure);
				cookie.setHttpOnly(httpSecure);
				CookieUtil.addCookie(cookie, response);
				return cookie;
			}
		} catch (Exception e) {
			log.error("CookiesUtil : createCookie : Exception: {}", e);
		}
		return null;
	}

	/**
	 * Delete cookie from request.
	 * 
	 * @param request
	 * @param cookiePrefix
	 * @param cookieName
	 */
	public static void deleteCookie(SlingHttpServletRequest request, SlingHttpServletResponse response, String cookiePrefix, String cookieName,
			 String cookiePath, boolean secure, boolean httpSecure) {
		try {
			log.debug("delete ck prefix {} name {}",cookiePrefix , cookieName);
			final Cookie cookie = CookieUtil.getCookie(request,
					cookiePrefix + cookieName);
			if (cookie != null) {
				cookie.setPath(cookiePath);
				cookie.setMaxAge(0);
				cookie.setHttpOnly(httpSecure);
				cookie.setSecure(secure);
		        response.addCookie(cookie);
			}
		} catch (Exception e) {
			log.error("CookiesUtil : deleteCookie : Exception: {}", e);
		}
	}

	/**
	 * Delete cookie from request.
	 * 
	 * @param request
	 * @param cookiePrefix
	 * @param cookieName
	 */
	public static String getCookie(SlingHttpServletRequest request, String cookiePrefix, String cookieName) {
		log.debug("Get ck prefix {} name {}",cookiePrefix , cookieName);
		String value =  StringUtils.EMPTY;
		try {
			final Cookie cookie = CookieUtil.getCookie(request,
					cookiePrefix + cookieName);
			if (cookie != null && StringUtils.isNotBlank(cookie.getValue())) {
				value =  cookie.getValue();
				if (StringUtils.isNotBlank(value)) {
					value = value.replace(ENCODED_SPACE, URL_ENCODED_SPACE);
					value =  URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
				}
			}
		}catch (Exception e) {
			log.error("CookiesUtil : getCookie : Exception: {}", e);
		}
		return value;
	}

	/**
	 * Setting cookies in SlingHttpServletResponse object
	 * 
	 * @param response
	 * @param accessTokenResponse
	 * @param contextAwareConfig
	 * @throws IOException
	 */
	public static void setLoginCookies(final SlingHttpServletResponse response, AccessTokenResponse accessTokenResponse,
			ContextAwareConfig contextAwareConfig){
		String cookiePath = CookieConstants.DEFAULT_COOKIE_PATH;
		String cookiePrefix = contextAwareConfig.cookiePrefix();
		createCookie(response, cookiePrefix, CookieConstants.ACCESS_TOKEN_COOKIE_NAME,
				accessTokenResponse.getAccessToken(), contextAwareConfig.cookieExpiry(), cookiePath,
				Boolean.FALSE, Boolean.TRUE);
		createCookie(response, cookiePrefix, CookieConstants.PROFILE_ID_COOKIE_NAME,
				accessTokenResponse.getProfileId(), contextAwareConfig.cookieExpiry(), cookiePath,
				Boolean.FALSE, Boolean.FALSE);
		createCookie(response, cookiePrefix, CookieConstants.REFRESH_TOKEN_COOKIE_NAME,
				accessTokenResponse.getRefreshToken(), contextAwareConfig.refreshTokenExpiry(), cookiePath,
				Boolean.FALSE, Boolean.TRUE);
	}

	
	
}
