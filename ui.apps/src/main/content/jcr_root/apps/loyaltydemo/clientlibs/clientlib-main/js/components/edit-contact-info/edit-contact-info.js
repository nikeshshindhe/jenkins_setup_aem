(function() {
    "use strict";
    // edit-contact-info component specific code here…

    $(document).ready(function() {
        var editContactInfo = {

            option: {

                el: $('#editContactInfo')
            },

            init: function() {
                this.populateInputValue();
                this.validation();
                this.showModal();
                this.hideModal();
                this.cancelModal();
            },

            showModal: function() {
                var self = this,
                    profileid = getCookie(PROFILE_ID_CK_NAME);

                 this.option.el.closest('.modal').on('show.bs.modal', function () {
                     if(profileid !== null){
                         self.countryCode();
                         self.populateInputValue();
                     }
                })

            },

            hideModal: function() {
                var self = this;

				this.option.el.closest('.modal').on('hide.bs.modal', function () {
					self.option.el[0].reset();

                     if($('.edit-contact-info').find('.generic__error').hasClass('success--msg')){
                        window.location.reload();
                    }

                })

            },
            cancelModal: function() {
                var self = this,
                    cancelBtn = this.option.el.find('button.cancel');

                $(document).on('click', '.edit-contact-info .cancel', function() {
                    self.option.el.closest('.modal').modal('hide');
                });

            },

            populateInputValue: function() {
                /* eslint-disable */
                var emailId = getCookie(USER_EMAILID_CK_NAME),
                    phoneId = getCookie(USER_PHONEID_CK_NAME),
                    email = getCookie(USER_PRIMARY_EMAIL_CK_NAME),
                    phone = getCookie(USER_PHONE_CK_NAME);
                /* eslint-enable */

                this.option.el.find('input[name="EmailId"]').val(emailId);
                this.option.el.find('input[name="PhoneId"]').val(phoneId);

                this.option.el.find('input[name="email"]').val(email);
                this.option.el.find('input[name="phone"]').val(phone);



            },

            countryCode: function() {
                var self = this;

                var countryCode = {
                    url: $('#resourcePath').val() + ".countries.json",
                    type: 'GET'
                };

                globalUtils.fetchData(countryCode) /* eslint-disable-line */
                    .then(function(data) {
                        var $countryCode = self.option.el.find('select[name="phoneCode"]');

                        if (data !== null && data.statusCode == 200) {
                            var populatedData = self.countryCodeMarkup(data.countries);
                            $countryCode.html(populatedData);
                        }

                    })
                    .catch(function(error) {
                        // console.log(error);
                    });

            },

            countryCodeMarkup: function(data) {

                var dropdown = "",
                	countryCode = getCookie(USER_PHONE_COUNTRYCODE_CK_NAME); /* eslint-disable-line */

                 $.each(data, function(key, val) {

                      if (val.CountryIso3Code == countryCode) {
                        dropdown += '<option selected="selected" value="' + val.CountryIso3Code + '">' + val.CountryIsonCode + '</option>';
                    } else {
                        dropdown += '<option value="' + val.CountryIso3Code + '">' + val.CountryIsonCode + '</option>';
                    }



                 });

                return dropdown;
            },

            validation: function() {
                var self = this;
                // Edit Contact info form validation 
                $('#editContactInfo').validator().on('submit', function(e) {
                    if (e.isDefaultPrevented()) {
                        // handle the invalid form...
                    } else {
                        e.preventDefault();
                        e.stopPropagation();

                        showLoader(); /* eslint-disable-line */
                        self.postFormData();
                    }
                });

            },

            postFormData: function() {

                  var self = this,
                      email = self.option.el.find('input[name="email"]').val(),
                      phone = self.option.el.find('input[name="phone"]').val(),
                      emailId = self.option.el.find('input[name="EmailId"]').val(),
                      phoneId = self.option.el.find('input[name="PhoneId"]').val(),
                      countryCode = self.option.el.find('select[name="phoneCode"]').val();


                 		console.log("tmail", email);

                     var data = {
                         "EmailData": JSON.stringify({
                             "EmailId": emailId,
                             "EmailAddress": email
                         }),
                         "PhoneData":JSON.stringify({
                             "PhoneId": phoneId,
                             "PhoneCountryCode": countryCode,
                             "PhoneNumber": phone
                         })
                     };


                var contactinfo = {
                    url: self.option.el.attr('action'),
                    type: self.option.el.attr('method'),
                    data: data
                };

                var $errorWrapper = $('.edit-contact-info .generic__error'),
                    $errorInner = $('.edit-contact-info .edit-contact-info__inner');

                globalUtils.fetchData(contactinfo) /* eslint-disable-line */
                    .then(function(data) {

                        hideLoader(); /* eslint-disable-line */


                        if (data !== null && data.statusCode == 200) {
                            $errorWrapper.find('span').html("<p>" + $errorInner.data('success-msg') + "</p>");
                            $errorWrapper.addClass('success--msg');
                            $errorWrapper.removeClass('d-none');

                            self.option.el.closest('.modal').scrollTop(0);

                        } else if (data !== null && data.Message !== undefined && data.Message !== "") {
                            $errorWrapper.find('span').html("<p>" + data.Message + "</p>");
                            $errorWrapper.removeClass('d-none');
                            self.option.el.closest('.modal').scrollTop(0);
                        } else {
                            $errorWrapper.find('span').html('<p>' + $errorInner.data('generic-error') + '</p>');
                            $errorWrapper.removeClass('d-none');
                            self.option.el.closest('.modal').scrollTop(0);

                        }
                    })
                    .catch(function(error) {
                        // console.log("error", error);
                        $errorWrapper.find('span').html('<p>' + $errorInner.data('generic-error') + '</p>');
                        $errorWrapper.removeClass('d-none');
                        self.option.el.closest('.modal').scrollTop(0);
                        hideLoader(); /* eslint-disable-line */

                    });
            }

        };


        if ($('#editContactInfo').length > 0) {
            editContactInfo.init();
        }
    });





})();