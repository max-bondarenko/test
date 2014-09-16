/**
 * Created by add on 29.08.2014.
 */
"use strict";
var M = {};
M.textRx = /[\d\w]+/;
M.isText = function (el) {
    if (typeof el === 'string') {
        var r = M.textRx;
        return r.test(el);
    }
    return false;
};
M.findEl = function (el) {
    for (var i = 0; i < el.children.length; i++) {
        var e = el.children[i];
        if (e.hasAttribute('data-list')) {
            return  e;
        }
    }
    return el;
};
M.spannify = function (el) {
    var self = this;

    function allowedElement(obj) {
        return obj.tagName === 'LI' || obj.tagName === 'UL';
    }

    var fu = function (el) {
        if (el.childNodes.length > 0) {
            if (el.children.length > 0) {
                for (var i = 0; i < el.children.length; i++) {
                    var obj = el.children[i];
                    if (allowedElement(obj)) {
                        fu(obj);
                    }
                }
            }
            if (el.firstChild
                && el.firstChild.nodeType == 3
                && M.isText(el.firstChild.nodeValue)) {
                var sp = document.createElement('span');
                el.insertBefore(sp, el.firstChild);
                sp.appendChild(sp.nextSibling)
            }
        }
    };
    fu(el);
};
M.doOnClick = function (el, func) {
    el.onclick = function (ev) {
        var e = ev || window.event;
        var target = e.target || e.srcElement;
        if (target.tagName !== 'SPAN') {
            return;
        }
        if (func) {
            func(target);
        }

        var li = target.parentNode;
        var n = li.getElementsByTagName('ul')[0];
        if (!n) {
            return;
        }
        n.style.display = n.style.display ? '' : 'none';
    };
};

function Menu(el) {
    var self = this;
    var self_el = el;

    function defaultOnCLick() {
        self_el.style.backgroundColor = self_el.style.backgroundColor ? '' : 'green';
    }

    if (typeof el === 'object' && el.tagName === 'UL') {
        this.onClick = defaultOnCLick;
        if (el.hasAttribute('data-list')) {
            M.spannify(el);
            M.doOnClick(el, function (t) {
                self.onClick(t);
            });
            el.setAttribute('data-menu', 'true');
            this.binded = true;
        }
    }

    return this;
}



