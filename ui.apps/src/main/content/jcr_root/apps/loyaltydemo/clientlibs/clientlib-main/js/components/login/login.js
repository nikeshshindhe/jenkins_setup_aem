(function() {
    "use strict";

    //remember me checkbox functionality 
    if($('.login-form').length > 0){
		var getUser = localStorage.getItem('rememberUser');
        if(getUser !== null){
			$('.login-form #userName').val(getUser)
        }

    }


    // login component specific code here…
    var $login = $("#login"),
        $userName = $login.find('input[name="Username"]').data("error"),
        $password = $login.find('input[name="Password"]').data("error");


    // login form validation
    $('form[id="login"]').validate({
        rules: {
            Username: 'required',
            Password: {
                required: true
            }
        },
        messages: {
            Username: $userName,
            Password: $password
        },
        submitHandler: function(form, e) {
            e.preventDefault();
            showLoader();
            $('.login .generic__error').addClass('d-none');

            var action = $(form).attr('action'),
                type = $(form).attr('method'),
                data = {};

            //serialize form data and convert to object
            $(form).serializeArray().map(function(x) {
                if (x.name !== ':cq_csrf_token') {
                    data[x.name] = x.value;
                }

            });

            var option = {
                url: action,
                type: type,
                data: data
            };

            globalUtils.sendData(option)
                .then(function(data) {
                    hideLoader();
                    var $errorWrapper = $('.login .generic__error'),
                        $loginInner = $('.login .registerlogin__inner');

                    if (data !== null && data.statusCode == 200) {
                        var redirectUrl = $('section.login').data('postlogin-link'),
                            storageUrl = getSessionStorageItem('redirectOnSuccessLogin');
                        if (storageUrl !== null) {
                            window.location.href = storageUrl
                        } else {
                            window.location.href = redirectUrl
                        }

                    } else if (data !== null && data.Message !== undefined && data.Message !== "") {
                        $errorWrapper.find('span').html("<p>" + data.Message + "</p>");
                        $errorWrapper.removeClass('d-none');
                        globalUtils.animateScrollToElm($errorWrapper, 1000);
                    } else if (data !== null && (data.Message === undefined || data.Message === "") && data.isPwdExpired !== undefined && data.isPwdExpired == true) {
                        $loginInner.find('.generic__error span').html('<p>' + $loginInner.data('password-error') + '</p>')
                        $errorWrapper.removeClass('d-none');
                        globalUtils.animateScrollToElm($errorWrapper, 1000);
                    } else {
                        $loginInner.find('.generic__error span').html('<p>' + $loginInner.data('generic-error') + '</p>')
                        $errorWrapper.removeClass('d-none');
                        globalUtils.animateScrollToElm($errorWrapper, 1000);

                    }
                })
                .catch(function(error) {
                    console.log(error, "error");
                    hideLoader();
                });
        }
    });


    //remember me checkbox functionality 
    $(document).on('click', '.login-form .checkbox-label', function(){
        var username = $('.login-form #userName').val();
        var status = $(this).find('#rememberMe').is(":checked");
        if(status && username !== ""){
			localStorage.setItem('rememberUser', username);
        }
    });

})();