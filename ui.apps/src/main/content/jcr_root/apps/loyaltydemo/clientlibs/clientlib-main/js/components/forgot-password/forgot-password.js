(function() {
	"use strict";
	// forgot-password component specific code here…

	// custom email validation
	$.validator
			.addMethod(
					"emailValidation",
					function(value, element) {
						if (!/^([0-9a-zA-Z]+[-._'+&])*[_0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$/
								.test(value)) {
							return false; // FAIL validation when REGEX
											// matches
						} else {
							return true; // PASS validation otherwise
						}
					}, "");

	var $forgotPassword = $("#forgotPassword"), $email = $forgotPassword.find(
			'input[name="email"]').data("error"), $invalidEmail = $forgotPassword
			.find('input[name="email"]').data("pattern");

	// forgot form validation
	$('form[id="forgotPassword"]').validate({
		rules : {
			email : {
				required : true,
				email : true,
				emailValidation : true
			}
		},
		messages : {
			email : {
				required : $email,
				email : $invalidEmail,
				emailValidation : $invalidEmail
			}
		},
		submitHandler : function(form) {
			$(".forgot-password__inner").addClass("d-none");
			$(".register__success").removeClass("d-none");
			// form.submit();
		}
	});
})();