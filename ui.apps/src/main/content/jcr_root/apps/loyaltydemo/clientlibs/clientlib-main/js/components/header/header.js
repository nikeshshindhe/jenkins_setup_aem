(function () {
	"use strict";
	// header component specific code here…
	$('.navbar-collapse-container__navbar-collapse-header .navbar-collapse-container__navbar-collapse-header--close').on('click', function() {
		$('.navbar-toggler').trigger('click');
	});

	$('.navbar-collapse-container__search-form--search-input').on('focusin', function() {
		$(this).next().addClass('navbar-collapse-container__search-form--search-action-show');
	});

	$('.navbar-collapse-container__search-form--search-input').on('focusout', function() {
        var origin = $(this);
        window.setTimeout(function() {
            origin.next().removeClass('navbar-collapse-container__search-form--search-action-show');
            origin.val('');
        }, 200);
    });
	$('.signout-link').off().on('click', function(e) {
		e.preventDefault();
		clearUserSessionData();
		if($(this).attr('href') !== '') {
			window.location.href = $(this).attr('href');
		} else {
			window.location.reload();
		}
	});
	$('.signin-link').on('click', function(e) {
	    setSessionStorageItem( LOGIN_REDIRECT_KEY, window.location.pathname);
	});
	$('#headerSearch').off().on('click', function(e) {
        e.preventDefault();
        if($(this).attr('href') !== '') {
            var url = $(this).attr('href') + '?q=' + $(this).prev().val();
            window.location.href = url;
        }
    });
})();