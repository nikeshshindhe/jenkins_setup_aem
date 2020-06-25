(function() {
    "use strict";


    // custom email validation 
    $.validator.addMethod(
        "emailValidation",
        function(value, element) {
            if (!/^([0-9a-zA-Z]+[-._'+&])*[_0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$/.test(value)) {
                return false; // FAIL validation when REGEX matches
            } else {
                return true; // PASS validation otherwise
            }
        },
        ""
    );

    $.validator.addMethod(
        "passwordStrength",
        function(value, element) {
            if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&\.])[A-Za-z\d@$!%*?&\.]{8,}$/.test(value)) {
                return false; // FAIL validation when REGEX matches
            } else {
                return true; // PASS validation otherwise
            }
        },
        ""
    );

    $.validator.addMethod(
        "nospace",
        function(value, element) {
            if (!/^\S*$/.test(value)) {
                return false; // FAIL validation when REGEX matches
            } else {
                return true; // PASS validation otherwise
            }
        },
        ""
    );

    var creditCardRequired = false;

    if($("#register").find('input[name="CreditCardNumber"]').length > 0){

		creditCardRequired = true;
    }



    // register form validation 
    var $register = $("#register"),
        $accountNo = $register.find('input[name="CreditCardNumber"]').data("error"),
        $fname = $register.find('input[name="FirstName"]').data("error"),
        $lname = $register.find('input[name="LastName"]').data("error"),
        $email = $register.find('input[name="EmailAddress"]').data("error"),
        $invalidEmail = $register.find('input[name="EmailAddress"]').data("pattern"),
        $userName = $register.find('input[name="Username"]').data("error"),
        $userNamePattern = $register.find('input[name="Username"]').data("pattern"),
        $password = $register.find('input[name="Password"]').data("error"),
        $passwordStrength = $register.find('input[name="Password"]').data("pattern"),
        $confirmPassword = $register.find('input[name="confirmPassword"]').data("error"),
        $invalidConfirmPassword = $register.find('input[name="confirmPassword"]').data("pattern");

    $('form[id="register"]').validate({
        onkeyup: false,
        rules: {
            CreditCardNumber: {
                required : creditCardRequired
            },
            FirstName: 'required',
            LastName: 'required',
            EmailAddress: {
                required: true,
                email: true,
                emailValidation: true
            },
            Username:{
                required : true,
                nospace : true,
                remote: {
                    type: 'POST',
                    url: $register.find('input[name="Username"]').data('servlet-path'),
                    data: { "Username": $(this).val() },
                    dataType: 'json',
                    dataFilter: function( data ) {

                        var json = JSON.parse(data);
                        if (json.statusCode !== 200) {
                            return JSON.stringify(json.Message);
                        } else if (json.statusCode == 200 && json.usernameValid == true) {
                            $('#userName-error').remove();
                            $('#userName').removeClass('error').removeClass('pending');
                            $('#userName').attr('aria-invalid', false);
                            return true;
                        }
                    }
                 }

            }, 
            Password: {
                required: true,
                passwordStrength : true
            },
            confirmPassword: {
                required: true,
                equalTo: "#password"
            }
        },
        messages: {
            CreditCardNumber: $accountNo,
            FirstName: $fname,
            LastName: $lname,
            EmailAddress: {
                required: $email,
                email: $invalidEmail,
                emailValidation: $invalidEmail
            },
            Username: {
                required: $userName,
                nospace: $userNamePattern
            },
            Password: {
                required : $password,
                passwordStrength : $passwordStrength
            },
            confirmPassword: {
                required: $confirmPassword,
                equalTo: $invalidConfirmPassword
            }
        },
        submitHandler: function(form, e) {
            e.preventDefault();
            showLoader(); /* eslint-disable-line */
            var action = $(form).attr('action'),
                type = $(form).attr('method'),
                data = {};

            //serialize form data and convert to object
            $(form).serializeArray().map(function(x) {
                if (x.name !== 'confirmPassword' && x.name !== ':cq_csrf_token') {
                    data[x.name] = x.value;
                }

            });

            var option = {
                url: action,
                type: type,
                data: data
            };

            globalUtils.sendData(option) /* eslint-disable-line */
                .then(function(data) {
                    hideLoader(); /* eslint-disable-line */
                    var $errorWrapper = $('.register .generic__error'),
                        $registrationInner = $('.register .registerlogin__inner');

                    if (data !== null && data.statusCode == 200) {
                        $registrationInner.addClass('d-none');
                        $('.register__success').removeClass('d-none');

                    } else if (data !== null && data.Message !== undefined && data.Message !== "") {
                        $errorWrapper.find('span').html("<p>" + data.Message + "</p>");
                        $errorWrapper.removeClass('d-none');
                        globalUtils.animateScrollToElm($errorWrapper, 1000); /* eslint-disable-line */
                    } else {
                        $registrationInner.find('.generic__error span').html('<p>' + $registrationInner.data('generic-error') + '</p>');
                        $errorWrapper.removeClass('d-none');
                        globalUtils.animateScrollToElm($errorWrapper, 1000); /* eslint-disable-line */

                    }
                })
                .catch(function(error) {
                    // console.log(error);
                    hideLoader(); /* eslint-disable-line */
                });


        }
    });


  $(document).on('click', '.regiser-redirect', function(e) {
        e.preventDefault();

        /* eslint-disable-next-line */
        var successLoginCookie = getCookie('loyaltyProfileIDCookie'),

            successLogin = $(this).attr('href'),
            failedLogin = $(this).data('cookie-failure');

        if (successLoginCookie !== undefined && successLoginCookie !== null) {
            window.location.href = successLogin;
        } else {
            window.location.href = failedLogin;
        }


    });

})();