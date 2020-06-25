package com.rnd.loyaltydemo.core.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.rnd.loyaltydemo.core.bo.AccessTokenResponse;
import com.rnd.loyaltydemo.core.bo.Aggregate;
import com.rnd.loyaltydemo.core.bo.Chain;
import com.rnd.loyaltydemo.core.bo.LoyaltyValidationResponse;
import com.rnd.loyaltydemo.core.bo.ProfileAggregate;
import com.rnd.loyaltydemo.core.bo.ProgramProfileTier;
import com.rnd.loyaltydemo.core.bo.ProgramProfileTiers;
import com.rnd.loyaltydemo.core.models.CTAModel;
import com.rnd.loyaltydemo.core.models.IconModel;

/**
 * Common utility class
 * 
 */

public class CommonUtil {
	
	private CommonUtil() {
		// Restricting Instantiation
	}

	/**
	 * Logger configuration for CommonUtil
	 */
	static final Logger log = LoggerFactory.getLogger(CommonUtil.class);

	public static final String GENERIC_ERRORCODE = "403";

	/**
	 * Converting JSON object to Generic object
	 * 
	 * @param jsonString
	 * @param obj
	 * @return Object
	 */
	public static Object getObjectFromJson(final String jsonString, final Object obj) {
		final Gson gson = new Gson();
		Object returnValue = null;
		try {
			returnValue = gson.fromJson(jsonString, obj.getClass());
		} catch (final Exception e) {
			log.error("Exception occured in CommonUtil :: getObjectFromJson {}", e);
		}
		return returnValue;
	}

	/**
	 * Converting Generic object to JSON string
	 * 
	 * @param obj
	 * @return String
	 */
	public static String getJsonFromObject(final Object obj) {
		final Gson gson = new Gson();
		String json = null;
		try {
			json = gson.toJson(obj);
		} catch (final Exception e) {
			log.error("Exception occured in CommonUtil :: getJsonFromObject {}", e);
		}
		return json;
	}

	/**
	 * Generic Response Error message
	 * 
	 * @param SlingHttpServletResponse, String
	 */
	public static void sendGenericResponseError(final SlingHttpServletResponse response, final String message)
			throws IOException {
		AccessTokenResponse res = new AccessTokenResponse();
		res.setErrorCode(GENERIC_ERRORCODE);
		res.setMessage(message);
		response.getWriter().write(CommonUtil.getJsonFromObject(res));
	}

	/**
	 * Generic Response Success message
	 * 
	 * @param SlingHttpServletResponse
	 */
	public static void sendGenericResponseSuccess(final SlingHttpServletResponse response) throws IOException {
		AccessTokenResponse res = new AccessTokenResponse();
		res.setStatusCode(HttpServletResponse.SC_OK);
		response.getWriter().write(CommonUtil.getJsonFromObject(res));
	}

	/**
	 * Generic Response PAssword Expiry message
	 * 
	 * @param SlingHttpServletResponse
	 */
	public static void sendGenericResponsePasswordExpiry(final SlingHttpServletResponse response) throws IOException {
		LoyaltyValidationResponse res = new LoyaltyValidationResponse();
		res.setPwdExpired(Boolean.TRUE);
		response.getWriter().write(CommonUtil.getJsonFromObject(res));
	}
	
	/**
	 * Recursive method - to check whether the given resource and its children is
	 * empty with no component specific properties in it. Method is considering that
	 * all the OOB AEM generated properties will have a colon in it. Method will
	 * iterate through all the properties of the give resource and check whether any
	 * custom properties are present - in the node by doing a contains check on
	 * colon(:).
	 * 
	 * @param resource
	 * @return
	 */
	public static boolean isEmptyNode(final Resource resource) {
		boolean isEmpty = true;

		if (resource == null) {
			return isEmpty;
		} else {
			for (final Entry<String, Object> entry : resource.getValueMap().entrySet()) {
				if (!entry.getKey().contains(":")) {
					return false;
				}
			}
			if (resource.hasChildren()) {
				final Iterator<Resource> childResource = resource.listChildren();
				while (isEmpty && childResource.hasNext()) {
					isEmpty = isEmptyNode(childResource.next());
				}
			}
		}
		return isEmpty;
	}
	
	/**
	 * For fetching CTA fields
	 * @param res
	 * @return CTAModel object
	 */
	public static CTAModel getCTAModel(Resource res) {
		if (isEmptyNode(res))
			return null;
		return res.adaptTo(CTAModel.class);
	}
	
	/**
	 * For fetching CTA and icon fields
	 * @param res
	 * @return IconModel type list
	 */
	public static List<IconModel> getCTAandIconItems(Resource res) {
		List<IconModel> itemList = new ArrayList<>();
		if (CommonUtil.isEmptyNode(res))
			return itemList;
		Iterator<Resource> configuredCTA = res.listChildren();
		while (configuredCTA.hasNext()) {
			itemList.add(configuredCTA.next().adaptTo(IconModel.class));
		}
		return itemList;
	}
	
	/**
	 * getProfileTierDetails method is to get "nextTierName" and "points to next tier"
	 * 
	 * @param loyaltyProfile
	 * @param contextAwareConfig
	 */
	public static void getProfileTierDetails(ProgramProfileTier programProfileTier,String aggregateTypeCode) {
		try {
			if (programProfileTier == null || programProfileTier.getStatusCode() != HttpServletResponse.SC_OK ||  
					programProfileTier.getProgramProfileTiers() == null || programProfileTier.getProgramProfileTiers().isEmpty()) {
				return;
			}
			processProfileTierList(programProfileTier);
			if (programProfileTier.getProfileAggregates() == null || programProfileTier.getProfileAggregates().isEmpty()) {
				return;
			}			
			if (StringUtils.isBlank(programProfileTier.getNextTierCode())) {
				return;
			}
			List<Chain> chainObject = programProfileTier.getTierProgression().getChains().stream()
					.filter(chainObj -> StringUtils.equals(chainObj.getTierCode(), programProfileTier.getNextTierCode()))
					.collect(Collectors.toList());
			if (chainObject == null || chainObject.isEmpty()) {
				return;
			}
			if (StringUtils.isNotBlank(aggregateTypeCode)) {
				int profileAggregateTotal = 0;
				List<ProfileAggregate> profileAggregateList = programProfileTier.getProfileAggregates().stream()
						.filter(profileAggregate -> StringUtils
								.equals(profileAggregate.getAggregateEntityCode(),aggregateTypeCode))
						.collect(Collectors.toList());
				if (profileAggregateList != null && !profileAggregateList.isEmpty()) {
					profileAggregateTotal = profileAggregateList.get(0).getAggregateTotal();
				}
				int annualThreshold = getAnnualThreshold( aggregateTypeCode, chainObject, profileAggregateTotal);
				programProfileTier.setPointsToNextTier(String.valueOf(annualThreshold- profileAggregateTotal));
			} else {
				for (ProfileAggregate profileAggregate : programProfileTier.getProfileAggregates()) {
					int profileAggregateTotal = profileAggregate.getAggregateTotal();
					int annualThreshold = getAnnualThreshold(profileAggregate.getAggregateEntityCode(), chainObject, profileAggregate.getAggregateTotal());
					programProfileTier.setPointsToNextTier(String.valueOf(annualThreshold- profileAggregateTotal));
				}
			}
		} catch (final Exception e) {
			log.error("CommonUtil : getProfileTierDetails : error whie getting profile tier detials: {}", e);
		}
	}

	private static int getAnnualThreshold(String aggregateTypeCode,
			List<Chain> chainObject, int profileAggregateTotal) {
		int points = 0;
		List<Aggregate> aggregateObj = chainObject
				.get(0).getAggregates().stream().filter(aggregate -> StringUtils
						.equals(aggregate.getAggregateCode(), aggregateTypeCode))
				.collect(Collectors.toList());
		if (aggregateObj != null && !aggregateObj.isEmpty()) {
			// Setting points to next tier in ProgramProfileTier Class
			points = (aggregateObj.get(0).getAnnualThreshold() - profileAggregateTotal);
		}
		return points;
	}

	private static void processProfileTierList(ProgramProfileTier programProfileTier) {
		int tiersCount = 0;
		List<ProgramProfileTiers> programProfileTiersList = programProfileTier
				.getProgramProfileTiers().stream().sorted(
						(firstProfileTier,secondProfileTier) -> firstProfileTier.getRank().compareTo(secondProfileTier.getRank()))
				.collect(Collectors.toList());
		
		if(programProfileTiersList != null && !programProfileTiersList.isEmpty()) {
			for (ProgramProfileTiers tier : programProfileTiersList) {
				tiersCount++;
				if (tier.getIsCurrentTier() != null && tier.getIsCurrentTier().equals(Boolean.TRUE)) {
					programProfileTier.setCurrentTierName(tier.getTierName());
					programProfileTier.setCurrentTierCode(tier.getTierCode());
					break;
				}
			}
			// Setting next tier name in ProgramProfileTier Class
			String nextTierName = programProfileTiersList.get(tiersCount).getTierName();
			programProfileTier.setNextTierName(nextTierName);
			programProfileTier.setNextTierCode(programProfileTiersList.get(tiersCount).getTierCode());
			log.debug("Next Tier {}",programProfileTier.getNextTierName());
		}
	}
}
