var globalUtils = (function() {

    return {
        // debounce function, can be used for scroll and resize
        debounce: function(func, wait, immediate) {
            var timeout;
            return function() {
                var context = this,
                    args = arguments;
                var later = function() {
                    timeout = null;
                    if (!immediate) func.apply(context, args);
                };
                var callNow = immediate && !timeout;
                clearTimeout(timeout);
                timeout = setTimeout(later, wait);
                if (callNow) func.apply(context, args);
            };

            // how to use debounce function, can be used for scroll and resize
            // var myEfficientFn = debounce(function() {
            // 	// All the taxing stuff you do
            // }, 250);
            // window.addEventListener('resize', myEfficientFn);
        },

        // get browser query param
        getQueryParam: function(field, url) {
            var href = url ? url : window.location.href,
                reg = new RegExp('[?&]' + field + '=([^&#]*)', 'i'),
                string = reg.exec(href);

            return string ? string[1] : null;
        },

        // Check if the element is in viewport
        isScrolledIntoView: function(elem) {
            var $elem = $(elem),
                $window = $(window),

                docViewTop = $window.scrollTop(),
                docViewBottom = docViewTop + $window.height(),

                elemTop = $elem.offset().top,
                elemBottom = elemTop + $elem.height();

            return ((elemBottom <= docViewBottom) && (elemTop >= docViewTop));


            // how to use this function eg:
            // globalUtils.isScrolledIntoView('#header')
        },

        // Prase json using try catch
        parseJson: function(str) {
            try {
                return JSON.parse(str);
            } catch (e) {
                return false;
            }
        },
        //get Cookie
        getCookie: function(name) {
            var v = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
            return v ? v[2] : null;
        },
        //set Cookie
        setCookie: function(name, value, days) {
            var d = new Date;
            d.setTime(d.getTime() + 24 * 60 * 60 * 1000 * days);
            document.cookie = name + "=" + value + ";path=/;expires=" + d.toGMTString();
        },
        //delete Cookie
        deleteCookie: function(name) {
            setCookie(name, '', -1);
        },

        // scroll to specific element 
        scrollToElm: function(elm, speed) {
            $('html, body').animate({
                scrollTop: $(elm).offset().top
            }, speed);
        },

        fetchData:function(options){

            return new Promise((resolve, reject) => {
                $.ajax({
                    url: options.url,
                    type: options.type,
                    dataType: "json",
                    data: options.data,
                    success: function(data) {
                        resolve(data)
                    },
                    error: function(error) {
                        reject(error)
                    }
                })
            });
        },

        sendData: function(options){

            return new Promise((resolve, reject) => {
                $.ajax({
                    url: options.url,
                    type: options.type,
                    dataType: "json",
                    data: options.data,
                    success: function(data) {
                        resolve(data)
                    },
                    error: function(error) {
                        reject(error)
                    }
                })
            });
        },

        animateScrollToElm : function(ele, speed){

             $('html, body').animate({
                 scrollTop: $(ele).offset().top
             }, speed);
        }
    };

})();