(function() {
    "use strict";
    // profile-information component specific code here…

    $(document).ready(function() {

        //edit perl info
        var editPerlInfo = {

            option: {

                el: $('#profileInfo')
            },

            init: function() {
			          var profileid = getCookie(PROFILE_ID_CK_NAME);

                this.datePicker();

                if(profileid !== null){
                    this.fetchCountry();
                    this.fetchState();
                    this.fetchGender();
                }


                this.validateForm();
                this.countryChange();
                this.showModal();
                this.hideModal();
                this.cancelModal();
            },

            datePicker: function() {

                // profile form date picker
                if ($('.date-picker').length > 0) {
                    $('.date-picker input').datetimepicker({
                        format: "MM-DD-YYYY",
                        maxDate: moment()
                    });
                }
            },

            showModal: function() {
                var self = this;

                this.option.el.closest('.modal').on('show.bs.modal', function() {
                    self.populateFieldValue();
                });

            },

            hideModal: function() {
                var self = this;

                this.option.el.closest('.modal').on('hide.bs.modal', function() {
                    self.option.el[0].reset();
                    if ($('.profile-information').find('.generic__error').hasClass('success--msg')) {
                        window.location.reload();
                    }
                });

            },
            cancelModal: function() {
                var self = this,
                    cancelBtn = this.option.el.find('button.cancel');

                $(document).on('click', '.profile-information .cancel', function() {
                    self.option.el.closest('.modal').modal('hide');
                });

            },

            fetchCountry: function() {
                //populate country
                var self = this;
                var country = {
                    url: self.option.el.find('select[name="CountryCode"]').data('servlet-path'),
                    type: 'GET'
                };

                globalUtils.fetchData(country) /* eslint-disable-line */
                    .then(function(data) {

                        var $country = self.option.el.find('select[name="CountryCode"]');

                        if (data !== null && data.statusCode == 200) {
                            var populatedData = self.countryMarkup(data.countries);
                            $country.html(populatedData);
                            $country.selectric();
                        }
                    })
                    .catch(function(error) {
                        // console.log(error);

                    });

            },
            countryMarkup: function(data) {
                //markup for country options
                var dropdown = "",
                    country = getCookie(USER_COUNTRYCODE_CK_NAME); /* eslint-disable-line */

                $.each(data, function(key, val) {

                    if (val.CountryCode == country) {
                        dropdown += '<option selected="selected" value="' + val.CountryCode + '">' + val.CountryName + '</option>';
                    } else {
                        dropdown += '<option value="' + val.CountryCode + '">' + val.CountryName + '</option>';
                    }

                });

                return dropdown;
            },

            countryChange: function() {

                var self = this;
                $(document).on('change', '#profileInfo #country', function() {
                    var countrySelected = $(this).find(":selected").val();
                    self.fetchState(countrySelected);
                });

            },

            fetchState: function(countrycode) {
                //populate country
                var self = this,
                    countryCode = (countrycode) ? countrycode : getCookie(USER_COUNTRYCODE_CK_NAME); /* eslint-disable-line */

                var country = {
                    url: self.option.el.find('select[name="StateCode"]').data('servlet-path'),
                    type: 'GET',
                    data: { "countryCode": countryCode }
                };

                if (countryCode !== null && (countryCode == 'USA' || countryCode == 'CAN')) {
                    self.option.el.find('select[name="StateCode"]').removeAttr('disabled');
                    self.option.el.find('select[name="StateCode"]').prop('required', true);

                    globalUtils.fetchData(country) /* eslint-disable-line */
                        .then(function(data) {
                            var $state = self.option.el.find('select[name="StateCode"]');

                            if (data !== null && data.statusCode == 200) {
                                var populatedData = self.stateMarkup(data.states);
                                $state.html(populatedData);
                                $state.selectric('refresh');
                            }
                        })
                        .catch(function(error) {
                            // console.log("error", error);

                        });
                } else {
                    self.option.el.find('select[name="StateCode"]').removeAttr('required');
                    self.option.el.find('select[name="StateCode"]').attr('disabled', 'disabled');
                    self.option.el.find('select[name="StateCode"]').html("");
                    //self.option.el.find('select[name="StateCode"]').selectric('refresh');
                   // self.option.el.find('select[name="StateCode"]').selectric('destroy');
                    self.option.el.find('select[name="StateCode"]').selectric('init');
                }

            },
            stateMarkup: function(data) {
                //markup for country options
                var dropdown = "",
                    state = getCookie(USER_STATECODE_CK_NAME); /* eslint-disable-line */

                $.each(data, function(key, val) {

                    if (val.StateCode == state) {
                        dropdown += '<option selected="selected" value="' + val.StateCode + '">' + val.StateName + '</option>';
                    } else {
                        dropdown += '<option value="' + val.StateCode + '">' + val.StateName + '</option>';
                    }

                });

                return dropdown;
            },

            fetchGender: function() {
                //populate country
                var self = this;
                var country = {
                    url: self.option.el.find('select[name="Gender"]').data('servlet-path'),
                    type: 'GET'
                };

                globalUtils.fetchData(country) /* eslint-disable-line */
                    .then(function(data) {

                        var $gender = self.option.el.find('select[name="Gender"]');

                        if (data !== null && data.statusCode == 200) {
                            var populatedData = self.genderMarkup(data.genderList);
                            $gender.html(populatedData);
                            $gender.selectric();
                        }
                    })
                    .catch(function(error) {
                        // console.log("error", error);

                    });

            },
            genderMarkup: function(data) {
                //markup for country options
                var dropdown = "",
                    gender = getCookie(GENDER_CK_NAME); /* eslint-disable-line */

                $.each(data, function(key, val) {

                    if (val.GenderCode == gender) {
                        dropdown += '<option selected="selected" value="' + val.GenderCode + '">' + val.GenderDescription + '</option>';
                    } else {
                        dropdown += '<option value="' + val.GenderCode + '">' + val.GenderDescription + '</option>';
                    }

                });

                return dropdown;
            },

            populateFieldValue: function() {

                //load edit form data from cookie
                /* eslint-disable */
                var fname = getCookie(USER_FIRST_NAME_CK_NAME),
                    lname = getCookie(USER_LAST_NAME_CK_NAME),
                    country = getCookie(USER_COUNTRYCODE_CK_NAME),
                    address1 = getCookie(USER_ADDRESSLINE1_CK_NAME),
                    address2 = getCookie(USER_ADDRESSLINE2_CK_NAME),
                    city = getCookie(USER_CITY_CK_NAME),
                    state = getCookie(USER_STATECODE_CK_NAME),
                    zip = getCookie(USER_POSTALCODE_CK_NAME),
                    dob = getCookie(DOB_CK_NAME),
                    gender = getCookie(GENDER_CK_NAME),
                    addressId = getCookie(USER_ADDRESSID_CK_NAME);
                /* eslint-enable */

                var dob = moment(dob).format("MM-DD-YYYY");  

                this.option.el.find('input[name="FirstName"]').val(fname);
                this.option.el.find('input[name="LastName"]').val(lname);
                this.option.el.find('input[name="AddressLine1"]').val(address1);
                this.option.el.find('input[name="AddressLine2"]').val(address2);
                this.option.el.find('input[name="City"]').val(city);
                this.option.el.find('input[name="PostalCode"]').val(zip);
                this.option.el.find('input[name="BirthDate"]').val(dob);
                this.option.el.find('input[name="AddressId"]').val(addressId);
            },

            validateForm: function() {
                var self = this;
                // profile form validation 
                $('#profileInfo').validator().on('submit', function(e) {
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
                    stateValue = (self.option.el.find('select[name="StateCode"]').val()) ? self.option.el.find('select[name="StateCode"]').val() : "",
                    addressId = self.option.el.find('input[name="AddressId"]').val(),
                    addressLine1 = self.option.el.find('input[name="AddressLine1"]').val(),
                    addressLine2 = self.option.el.find('input[name="AddressLine2"]').val(),
                    city = self.option.el.find('input[name="City"]').val(),
                    stateCode = stateValue,
                    countryCode = self.option.el.find('select[name="CountryCode"]').val(),
                    postalCode = self.option.el.find('input[name="PostalCode"]').val(),
                    firstName = self.option.el.find('input[name="FirstName"]').val(),
                    lastName = self.option.el.find('input[name="LastName"]').val(),
                    gender = self.option.el.find('select[name="Gender"]').val(),
                    birthDate = self.option.el.find('input[name="BirthDate"]').val();

                var data = {
                    "AddressData": JSON.stringify({
                            "AddressId": addressId,
                            "AddressLine1": addressLine1,
                            "AddressLine2": addressLine2,
                            "City": city,
                            "StateCode": stateCode,
                            "CountryCode": countryCode,
                            "PostalCode": postalCode
                    }),

                    "ProfileData": JSON.stringify({
                        "FirstName": firstName,
                        "LastName": lastName,
                        "Gender": gender,
                        "BirthDate": birthDate
                    }),

                    "EmailData": "",
                    "PhoneData": ""
                };

                var country = {
                    url: self.option.el.attr('action'),
                    type: self.option.el.attr('method'),
                    data: data
                };

                   var $gender = self.option.el.find('select[name="Gender"]'),
                       $errorWrapper = $('.edit-personal-info .generic__error'),
                       $errorInner = $('.edit-personal-info .profileInfo__inner');

                //post edit profile data
                globalUtils.sendData(country) /* eslint-disable-line */
                    .then(function(data) {
						hideLoader();

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
                        //console.log("error", error);
                        	$errorWrapper.find('span').html('<p>' + $errorInner.data('generic-error') + '</p>');
                            $errorWrapper.removeClass('d-none');
                            self.option.el.closest('.modal').scrollTop(0);

                        	hideLoader();

                    });


            }

        };



        if ($('#profileInfo').length > 0) {

            editPerlInfo.init();

        }


    });

})();