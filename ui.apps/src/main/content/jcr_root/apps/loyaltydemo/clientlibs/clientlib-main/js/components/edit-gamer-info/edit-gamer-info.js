(function($) {
    "use strict";
    // edit-gamer-info component specific code here…


    var editGamerInfo = {

        option: {

            el: $('.edit-gamer-info')
        },

        init: function() {

            this.validateForm();
            this.showModal();
            this.hideModal();
            this.cancelModal();
        },

        populateName: function() {
            var gamerName = getCookie(GAMER_ALIASL_CK_NAME); /* eslint-disable-line */
            $('.edit-gamer-info #name').val(gamerName);
        },

        showModal: function() {
            var self = this;

            this.option.el.closest('.modal').on('show.bs.modal', function() {

                self.populateName();
            });

        },

        hideModal: function() {
            var self = this;

            this.option.el.closest('.modal').on('hide.bs.modal', function() {
                $('.edit-gamer-info #name').val("");

                if ($('.edit-gamer-info').find('.generic__error').hasClass('success--msg')) {
                    window.location.reload();
                }
            });

        },
        cancelModal: function() {
            var self = this,
                cancelBtn = this.option.el.find('button.cancel');

            $(document).on('click', '.edit-gamer-info .cancel', function() {
                self.option.el.closest('.modal').modal('hide');
            });

        },

        validateForm: function() {
            var self = this;
            // Edit Contact info form validation 
            $('#editgamertInfo').validator().on('submit', function(e) {
                var $form = $('#editgamertInfo');
                if (e.isDefaultPrevented()) {
                    // handle the invalid form...
                } else {
                    e.preventDefault();
                    e.stopPropagation();

                    showLoader(); /* eslint-disable-line */
                    var action = $(this).attr('action'),
                        type = $(this).attr('method');

                    var option = {
                        url: action,
                        type: type,
                        data: { "gamerAlias": $('.edit-gamer-info #name').val() }
                    };

                    var $errorWrapper = $('.edit-gamer-info .generic__error'),
                        $errorInner = $('.edit-gamer-info .edit-gamer-info__inner');

                    globalUtils.sendData(option) /* eslint-disable-line */
                        .then(function(data) {
                            hideLoader(); /* eslint-disable-line */



                            if (data !== null && data.statusCode == 200) {

                                $errorWrapper.find('span').html("<p>" + $errorInner.data('success-msg') + "</p>");
                                $errorWrapper.addClass('success--msg');
                                $errorWrapper.removeClass('d-none');
                                self.option.el.closest('.modal').scrollTop(0);

                                setCookie(GAMER_ALIASL_CK_NAME, $('.edit-gamer-info #name').val()); /* eslint-disable-line */

                            } else if (data !== null && data.Message !== undefined && data.Message !== "") {
                                $errorWrapper.find('span').html("<p>" + data.Message + "</p>");
                                $errorWrapper.removeClass('d-none');
                                self.option.el.closest('.modal').scrollTop(0);
                            } else {
                                $errorInner.find('.generic__error span').html('<p>' + $errorInner.data('generic-error') + '</p>');
                                $errorWrapper.removeClass('d-none');
                                self.option.el.closest('.modal').scrollTop(0);

                            }
                        })
                        .catch(function(error) {
                            $errorInner.find('.generic__error span').html('<p>' + $errorInner.data('generic-error') + '</p>');
                            $errorWrapper.removeClass('d-none');
                            self.option.el.closest('.modal').scrollTop(0);
                            hideLoader(); /* eslint-disable-line */
                        });


                }
            });
        }


    };



    $(document).ready(function() {
        if ($('#editgamertInfo').length > 0) {
            editGamerInfo.init();
        }

    });

})(jQuery);