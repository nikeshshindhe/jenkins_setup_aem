(function() {
    "use strict";
    // change-password component specific code here…
    var $changePassword = $("#changePassword"),
        $errorWrapper = $('.change-password .generic__error'),
        $errorInner = $('.change-password .change-password__inner');

    $(document).on('click', '.change-password-form .cancel', function () {
        resetForm();
    });

    $(document).on('click', '.close', function () {
        resetForm();
    });

    function resetForm() {
        $changePassword
        .find('.form-group').removeClass('has-error has-danger')
        .find('input').val('')
        .siblings('.with-errors').html('')
        .closest('.modal').modal('hide');
        $errorWrapper.addClass('d-none');
    }

    // change password form validation 
    $('#changePassword').validator().on('submit', function(e) {
        var $form = $('#changePassword');
        if (e.isDefaultPrevented()) {
            // handle the invalid form...
                
        } else {
            e.preventDefault();
            e.stopPropagation();
            
            showLoader();
            var action = $(this).attr('action'),
                type = $(this).attr('method'),
                $username = getCookie(USERNAME_CK_NAME),
                $currentPassword = $changePassword.find('input[name="currentPassword"]').val() || '',
                $newPassword = $changePassword.find('input[name="newPassword"]').val() || '',
                $confirmPassword = $changePassword.find('input[name="confirmPassword"]').val() || '';

            var option = {
                url: action,
                type: type,
                data: {"username": $username,"currentPwd": $currentPassword,"newPwd": $newPassword, "confirmPwd": $confirmPassword}
            };

            globalUtils.sendData(option)
            .then(function(data) {
                hideLoader();

                if (data !== null && data.statusCode == 200) {
                    $errorWrapper.find('span').html("<p>" + $errorInner.data('success-msg') + "</p>");
                    $errorWrapper.addClass('success--msg');
                    $errorWrapper.removeClass('d-none');

                } else if (data !== null && data.Message !== undefined && data.Message !== "") {
                    $errorWrapper.find('span').html("<p>" + data.Message + "</p>");
                    $errorWrapper.removeClass('success--msg');
                    $errorWrapper.removeClass('d-none');
                    $form.closest('.modal').scrollTop(0);
                } else {
                    $errorInner.find('.generic__error span').html('<p>' + $errorInner.data('generic-error') + '</p>');
                    $errorWrapper.removeClass('success--msg');
                    $errorWrapper.removeClass('d-none');                    
                    $form.closest('.modal').scrollTop(0);

                }
            })
            .catch(function(error) {
                // console.log(error);
                hideLoader(); /* eslint-disable-line */
            });
        }
    });

})();