var PROFILE_ID_CK_NAME = 'loyaltyProfileIDCookie';
var USERNAME_CK_NAME = 'userName';
var USER_FIRST_NAME_CK_NAME = 'userFirstName';
var USER_LAST_NAME_CK_NAME = 'userLastName';
var USER_EMAILID_CK_NAME = "userPrimaryEmailId";
var USER_PRIMARY_EMAIL_CK_NAME = 'userPrimaryEmail';
var USER_PHONEID_CK_NAME = "userPrimaryPhoneId";
var USER_PHONE_COUNTRYCODE_CK_NAME = "userPhoneCountryCode";
var USER_PHONE_CK_NAME = "userPrimaryPhone";
var TIER_CK_NAME = 'userTier';
var POINTS_CK_NAME = 'userPoints';
var POINTS_TYPE_DESC_CK_NAME = 'userPointsTypeDesc';
var USER_NEXT_TIER_CK_NAME = 'userNextTier';
var USER_POINTS_NEXT_TIER_CK_NAME = 'userPointsToNextTier';
var CARDNUM_CK_NAME = 'cardNumber';
var LINKEDPOOL_CK_NAME = 'inLinkedPool';
var GENDER_CK_NAME = "userGender";
var DOB_CK_NAME = "userDateOfBirth";
var JOIN_DATE_CK_NAME = "userJoinDate";
var GAMER_ALIASL_CK_NAME = "userGamerAlias";
//Address cookie constants
var USER_ADDRESSID_CK_NAME = "userAddressId";
var USER_ADDRESSLINE1_CK_NAME = "userAddressLine1";
var USER_ADDRESSLINE2_CK_NAME = "userAddressLine2";
var USER_CITY_CK_NAME = "userCity";
var USER_STATECODE_CK_NAME = "userStateCode";
var USER_POSTALCODE_CK_NAME = "userPostalCode";
var USER_COUNTRYCODE_CK_NAME = "userCountryCode";
var EMPTY_VALUE = "";
var LOGIN_REDIRECT_KEY = "redirectOnSuccessLogin"
var localeCode = "en";


function isObject(obj) {
   return Object.prototype.toString.call(obj) === '[object Object]';
}
var ckprefix;
var queryParams = [];
function getQueryParameterValue(param){
	if (queryParams.length <= 0) {
		var hash;
	    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	    for(var i = 0; i < hashes.length; i++)
	    {
	        hash = hashes[i].split('=');
	        queryParams.push(hash[0]);
	        queryParams[hash[0]] = hash[1];
	    }
	}
    return ($.inArray(param,queryParams) != -1) ? queryParams[param] : "";
}

function setCookie(name,value,days) {
    var expires = "";
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days*24*60*60*1000));
        expires = "; expires=" + date.toUTCString();
    }
    name = ckprefix + name;
    document.cookie = name + "=" + (value || "")  + expires + "; path=/";
}
function getCookie(name) {
	name = ckprefix + name;
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return decodeURIComponent(c.substring(nameEQ.length,c.length));
    }
    return null;
}

function eraseCookie(name) {   
	name = ckprefix + name;
	document.cookie = name + '=; Path=/;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

function setSessionStorageItem(name,value) {
	name = ckprefix + name;
    sessionStorage.setItem(name,value);
}

function getSessionStorageItem(name) {
	name = ckprefix + name;
	return sessionStorage.getItem(name)
}

function removeSessionStorageItem(name) {   
	name = ckprefix + name;
	sessionStorage.removeItem(name);
}

function setLocalStorageItem(name,value) {
	name = ckprefix + name;
	localStorage.setItem(name,value);
}

function getLocalStorageItem(name) {
	name = ckprefix + name;
	return localStorage.getItem(name)
}

function removeLocalStorageItem(name) {   
	name = ckprefix + name;
	localStorage.removeItem(name);
}

function clearUserSessionData() {
	clearLoyaltyCookies();
	//clearLoyaltySessionStorage();
}

function clearLoyaltyCookies(){
	var cookieNames = document.cookie.split(/=[^;]*(?:;\s*|$)/);
	for (var i = 0; i < cookieNames.length; i++) {
		var name = cookieNames[i];
	    if (name.startsWith(ckprefix)) {
	    	document.cookie = name + '=; Path=/;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
	    }
	}
}

function localeBasedNumberFormat(number) {
	if (number && number != "") {
		number = Number(number).toLocaleString(localeCode);
	}
	return number;
}

function localeBasedCurrencyFormat(ammount,currencyCode) {
	if (ammount && ammount != "") {
		ammount = Number(ammount).toLocaleString(localeCode, {style:'currency', currency:currencyCode});
	}
	return ammount;
}

function localeBasedDateTimeFormat(dateStr) {
	if (dateStr && dateStr != "") {
		var date = new Date(dateStr);
		dateStr = date.toLocaleString(localeCode);
	}
	return dateStr;
}

function localeBasedDateFormat(dateStr) {
	if (dateStr && dateStr != "") {
		var date = new Date(dateStr);
		dateStr = date.toLocaleDateString(localeCode);
	}
	return dateStr;
}

function localeBasedTimeFormat(dateStr) {
	if (dateStr && dateStr != "") {
		var date = new Date(dateStr);
		dateStr = date.toLocaleTimeString(localeCode);
	}
	return dateStr;
}

function clearLoyaltySessionStorage(){
	for (var i = 0; i < sessionStorage.length; i++){
		var name = sessionStorage.key(i);
	    if (name.startsWith(ckprefix)) {
	    	sessionStorage.removeItem(name);
	    }
	}
}

function updateUserDetails(){
	var profileId = getCookie(PROFILE_ID_CK_NAME);
	if (profileId != null && profileId != "") {
		//Username update in DOM
		if ($(".loyaltyUsername").length > 0) {
			var username = getCookie(USERNAME_CK_NAME);
			username = username != null ? username : EMPTY_VALUE;
			$(".loyaltyUsername").text(username);
		}
		
		//First Name update in DOM
		if ($(".userFirstName").length > 0) {
			var firstNname = getCookie(USER_FIRST_NAME_CK_NAME);
			firstNname = firstNname != null ? firstNname : EMPTY_VALUE;
			$(".userFirstName").text(firstNname);
		}
		
		//Last Name update in DOM
		if ($(".userLastName").length > 0) {
			var lastNname = getCookie(USER_LAST_NAME_CK_NAME);
			lastNname = lastNname != null ? lastNname : EMPTY_VALUE;
			$(".userLastName").text(lastNname);
		}
		
		//Email update in DOM
		if ($(".userPrimaryEmail").length > 0) {
			var email = getCookie(USER_PRIMARY_EMAIL_CK_NAME);
			email = email != null ? email : EMPTY_VALUE;
			$(".userPrimaryEmail").text(email);
		}
		
		//Phone update in DOM
		if ($(".userPrimaryPhone").length > 0) {
			var phone = getCookie(USER_PHONE_CK_NAME);
			phone = phone != null ? phone : EMPTY_VALUE;
			$(".userPrimaryPhone").text(phone);
		}
		
		//Tier update in DOM
		if ($(".userTier").length > 0) {
			var tier = getCookie(TIER_CK_NAME);
			tier = tier != null ? tier : EMPTY_VALUE;
			$(".userTier").text(tier);
		}
		
		//Points update in DOM
		if ($(".userPoints").length > 0) {
			var availblePoints = getCookie(POINTS_CK_NAME);
			availblePoints = (availblePoints != null && availblePoints != "") ?
					localeBasedNumberFormat(availblePoints) : 0;
			$(".userPoints").text(availblePoints);
		}
		
		//Next tier update in DOM
		if ($(".userNextTier").length > 0) {
			var nextTier = getCookie(USER_NEXT_TIER_CK_NAME);
			nextTier = nextTier != null ? nextTier : EMPTY_VALUE;
			$(".userNextTier").text(nextTier);
		}
		//Points to next tier update in DOM
		if ($(".userPointsToNextTier").length > 0) {
			var nextTierPoints = getCookie(USER_POINTS_NEXT_TIER_CK_NAME);
			nextTierPoints = (nextTierPoints != null && nextTierPoints != "" && nextTierPoints != "N/A") ?
					localeBasedNumberFormat(nextTierPoints) : EMPTY_VALUE;
			$(".userPointsToNextTier").text(nextTierPoints);			
		}
		
		//Loyalty Card Number update in DOM
		if ($(".userCardNumber").length > 0) {
			var userCardNum = getCookie(CARDNUM_CK_NAME);
			userCardNum = userCardNum != null ? userCardNum : EMPTY_VALUE;
			$(".userCardNumber").text(userCardNum);
		}
		
		//DOB update in DOM
		if ($(".userDateOfBirth").length > 0) {
			var birthDate = getCookie(DOB_CK_NAME);
			birthDate = (birthDate != null && birthDate != "") ?
					localeBasedDateFormat(birthDate) : EMPTY_VALUE;
			$(".userDateOfBirth").text(birthDate);
		}
		
		//Join Date update in DOM
		if ($(".userJoinDate").length > 0) {
			var joinDate = getCookie(JOIN_DATE_CK_NAME);
			joinDate = (joinDate != null && joinDate != "") ?
					localeBasedDateFormat(joinDate) : EMPTY_VALUE;
			$(".userJoinDate").text(joinDate);
		}
		
		//Gamer update in DOM
        if ($(".loyaltyGamerAlias").length > 0) {
			var gamerAlias = getCookie(GAMER_ALIASL_CK_NAME);
			gamerAlias = gamerAlias != null ? gamerAlias : EMPTY_VALUE;
        	$(".loyaltyGamerAlias").text(gamerAlias);
        }
        
		//Points label update in DOM
        if ($(".userPointsTypeDesc").length > 0) {
        	var pointsLabel = getCookie(POINTS_TYPE_DESC_CK_NAME);
        	pointsLabel = pointsLabel != null ? pointsLabel : EMPTY_VALUE;
        	$(".userPointsTypeDesc").text(pointsLabel);
        }
        
		//Pooled points label update in DOM
        if ($(".userPooledPointsIndicator").length > 0 && getCookie(LINKEDPOOL_CK_NAME) != 'true') {
        	$(".userPooledPointsIndicator").text(EMPTY_VALUE);
        }
	} else if (getCookie(USERNAME_CK_NAME) != null) {
		clearUserSessionData();	
	}
}

function componentDisplayBasedonUserSession(){
	var profileId = getCookie(PROFILE_ID_CK_NAME);
	if (profileId != null && profileId != "") {
		$(".unauth-only").addClass("d-none");
		$(".auth-only").removeClass("d-none");
	} else {
		$(".auth-only").addClass("d-none");
		$(".unauth-only").removeClass("d-none");
	}
}

$('button.linkBtn').click(function() {
	var url = $(this).data("url");
	var target = $(this).data("target") ? $(this).data("target") : "_self";
	window.open(url, target);
});

$(document).ready(function() {
	ckprefix = $("#cookiePrefix").val() ?  $("#cookiePrefix").val() : "";
	localeCode = $("#localeCode").val() ?  $("#localeCode").val() : "en";
	componentDisplayBasedonUserSession();
	updateUserDetails();
});


function showLoader(){
	$('.loader').removeClass('d-none');
}

function hideLoader(){
	$('.loader').addClass('d-none');
}