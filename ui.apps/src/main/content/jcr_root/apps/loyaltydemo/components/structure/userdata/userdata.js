use(function() {
    var prefix = request.getAttribute("cookiePrefix") ? request.getAttribute("cookiePrefix") : "";
    var profileIdCookie = request.getCookie(prefix + "loyaltyProfileIDCookie");
    var userName = request.getAttribute("userName");
    var userFirstName = request.getAttribute("userFirstName");
    var userLastName = request.getAttribute("userLastName");
    var userPrimaryEmailId = request.getAttribute("userPrimaryEmailId");
    var userPrimaryEmail = request.getAttribute("userPrimaryEmail");
    var userPrimaryPhoneId = request.getAttribute("userPrimaryPhoneId");
    var userPhoneCountryCode = request.getAttribute("userPhoneCountryCode");
    var userPrimaryPhone = request.getAttribute("userPrimaryPhone");
    var userTier = request.getAttribute("userTier");
    var userPoints = request.getAttribute("userPoints");
    var userPointsTypeDesc = request.getAttribute("userPointsTypeDesc");
    var userNextTier = request.getAttribute("userNextTier");
    var userPointsToNextTier = request.getAttribute("userPointsToNextTier");
    var cardNum = request.getAttribute("cardNumber");
    var inLinkedPool = request.getAttribute("inLinkedPool");
    var userGender = request.getAttribute("userGender");
    var userDateOfBirth = request.getAttribute("userDateOfBirth");
    var userJoinDate = request.getAttribute("userJoinDate");
    var userGamerAlias = request.getAttribute("userGamerAlias");
    //Address cookie constants
    var userAddressId = request.getAttribute("userAddressId");
    var userAddressLine1 = request.getAttribute("userAddressLine1");
    var userAddressLine2 = request.getAttribute("userAddressLine2");
    var userCity = request.getAttribute("userCity");
    var userStateCode = request.getAttribute("userStateCode");
    var userPostalCode = request.getAttribute("userPostalCode");
    var userCountryCode = request.getAttribute("userCountryCode");
    var cookieFound = false;
    if (profileIdCookie != null) {
        cookieFound = true;
    }
    return {
    	userName : userName != null ? userName : '',
		userFirstName : userFirstName != null ? userFirstName : '',
		userLastName : userLastName != null ? userLastName : '',
		userPrimaryEmailId : userPrimaryEmailId != null ? userPrimaryEmailId : '',
		userPrimaryEmail : userPrimaryEmail != null ? userPrimaryEmail : '',
		userPrimaryPhoneId : userPrimaryPhoneId != null ? userPrimaryPhoneId : '',
		userPhoneCountryCode : userPhoneCountryCode != null ? userPhoneCountryCode : '',
		userPrimaryPhone : userPrimaryPhone != null ? userPrimaryPhone : '',
		userTier : userTier != null ? userTier : '',
		userPoints : userPoints != null ? userPoints : '',
		userPointsTypeDesc : userPointsTypeDesc != null ? userPointsTypeDesc : '',
		userNextTier : userNextTier != null ? userNextTier : '',
		userPointsToNextTier : userPointsToNextTier != null ? userPointsToNextTier: '',
		cardNum : cardNum != null ? cardNum : '',
		inLinkedPool : inLinkedPool != null ? inLinkedPool : '',
		userGender : userGender != null ? userGender : '',
		userDateOfBirth : userDateOfBirth != null ? userDateOfBirth : '',
		userJoinDate : userJoinDate != null ? userJoinDate : '',
		userGamerAlias : userGamerAlias != null ? userGamerAlias : '',
		userAddressId : userAddressId != null ? userAddressId : '',
		userAddressLine1 : userAddressLine1 != null ? userAddressLine1 : '',
		userAddressLine2 : userAddressLine2 != null ? userAddressLine2 : '',
		userCity : userCity != null ? userCity : '',
		userPostalCode : userPostalCode != null ? userPostalCode : '',
		userStateCode : userStateCode != null ? userStateCode : '',
		userCountryCode : userCountryCode != null ? userCountryCode : '',
		cookieFound : cookieFound
    };

});