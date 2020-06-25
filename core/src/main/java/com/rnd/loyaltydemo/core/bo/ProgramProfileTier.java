
package com.rnd.loyaltydemo.core.bo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rnd.loyaltydemo.core.constants.LoyaltyDemoConstants;

public class ProgramProfileTier extends GenericResponse{

    @SerializedName("ProfileId")
    @Expose
    private String profileId;
    @SerializedName("TierProgressionDate")
    @Expose
    private String tierProgressionDate;
    @SerializedName("TierProgression")
    @Expose
    private TierProgression tierProgression;
    @SerializedName("ProgramProfileTiers")
    @Expose
    private List<ProgramProfileTiers> programProfileTiers = null;
    @SerializedName("ProfileAggregates")
    @Expose
    private List<ProfileAggregate> profileAggregates = null;
    @SerializedName("IsMaxTier")
    @Expose
    private Boolean isMaxTier;
    @Expose
    private String nextTierName = LoyaltyDemoConstants.NA;
    @Expose
    private String pointsToNextTier = LoyaltyDemoConstants.NA;
    @Expose
    private String currentTierName;
    @Expose
    private String currentTierCode;
    @Expose
    private String nextTierCode;
    
    
    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getTierProgressionDate() {
        return tierProgressionDate;
    }

    public void setTierProgressionDate(String tierProgressionDate) {
        this.tierProgressionDate = tierProgressionDate;
    }

    public TierProgression getTierProgression() {
        return tierProgression;
    }

    public void setTierProgression(TierProgression tierProgression) {
        this.tierProgression = tierProgression;
    }

    public List<ProgramProfileTiers> getProgramProfileTiers() {
        return programProfileTiers;
    }

    public void setProgramProfileTiers(List<ProgramProfileTiers> programProfileTiers) {
        this.programProfileTiers = programProfileTiers;
    }

    public List<ProfileAggregate> getProfileAggregates() {
        return profileAggregates;
    }

    public void setProfileAggregates(List<ProfileAggregate> profileAggregates) {
        this.profileAggregates = profileAggregates;
    }

    public Boolean getIsMaxTier() {
        return isMaxTier;
    }

    public void setIsMaxTier(Boolean isMaxTier) {
        this.isMaxTier = isMaxTier;
    }

	public String getNextTierName() {
		return nextTierName;
	}

	public void setNextTierName(String nextTierName) {
		this.nextTierName = nextTierName;
	}

	public String getPointsToNextTier() {
		return pointsToNextTier;
	}

	public void setPointsToNextTier(String pointsToNextTier) {
		this.pointsToNextTier = pointsToNextTier;
	}

	public String getCurrentTierName() {
		return currentTierName;
	}

	public void setCurrentTierName(String currentTierName) {
		this.currentTierName = currentTierName;
	}

	public String getCurrentTierCode() {
		return currentTierCode;
	}

	public void setCurrentTierCode(String currentTierCode) {
		this.currentTierCode = currentTierCode;
	}

	public String getNextTierCode() {
		return nextTierCode;
	}

	public void setNextTierCode(String nextTierCode) {
		this.nextTierCode = nextTierCode;
	}
    
}
