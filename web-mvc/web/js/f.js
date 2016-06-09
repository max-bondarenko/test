/**
 * Created by mbondarenko on 27.08.2014.
 */
'use strict';
var M = (function () {
    var _ = {};
    _.gAttr = function (e, at) {
        if (typeof e === 'object') {
            if (at && typeof at === 'string') {
                return e.getAttribute(at)
            }
        }

    };

    _.map = function (element, func) {
        if (element && toString.call(element) === '[object Array]') {
            if (typeof func === 'function') {
                var ret = [];
                for (var i = 0; i < element.length; i++) {
                    var items = func(element[i]);
                    if (items) ret.push(items)
                }
                return ret;
            }
        }
    };

    return _;

})();



