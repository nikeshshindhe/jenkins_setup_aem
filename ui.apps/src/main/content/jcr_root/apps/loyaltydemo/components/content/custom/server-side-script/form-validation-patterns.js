use(function() {
	var EMAIL_PATTERN = "^([0-9a-zA-Z]+[-._'+&])*[_0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$";
	var PHONE_PATTERN = "^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$";
	
	return{
		email_pattern:EMAIL_PATTERN,
		phone_pattern:PHONE_PATTERN
	};
	
});