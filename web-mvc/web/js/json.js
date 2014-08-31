/**
 * Created by add on 29.08.2014.
 */
var testObj = {
    id: '100',
    name: 'name',
    ch: [
        {
            id: '1',
            name: 'name 1'
        },
        {
            id: '2',
            name: 'name 2'
        },
        {
            id: '3',
            name: 'name 3',
            ch: [
                {
                    id: '31',
                    name: 'name 31'
                },
                {
                    id: '32',
                    name: 'name 32'
                }
            ]
        },
        {
            id: '1',
            name: 'name 1'
        }
    ]
};

var testObj2 = [
    { name: 'parent',
        id: 0,
        ch: [
            {
                id: 10,
                name: 'parent_ch',
                ch: [
                    {
                        id: 110,
                        name: 'ch_ch'
                    }
                ]

            },
            {
                id: 11,
                name: 'parent_ch',
                ch: [
                    {
                        id: 111,
                        name: 'ch_ch'
                    }
                ]

            }
        ]
    },
    {
        id: 1,
        name: 'parent_ch2'
    },
    {
        id: 2,
        name: 'parent ch3',
        ch: [
            {
                id: 12,
                name: 'ch3 ch'
            },
            {
                id: 22,
                name: 'parent_c3',
                ch: [
                    {
                        id: 122,
                        name: 'ch3_ch'
                    }
                ]

            }
        ]

    }

];

var testObj3 = {
    ch: [
        {
            id: 55, name: 'jgyjghjh'
        },
        {
            id: 56, name: 'jdgs'
        },
        {
            id: 57, name: 'dsfdfs'
        }
    ]
}
//lib Name
var menu = (function () {
    var s = this;

    function createElement(st) {
        return document.createElement(st);
    }

    var textRgx = /[\d\w]+/;

    function isText(str) {
        if (typeof str === 'string') {
            var r = M.textRx;
            return r.test(str);
        }
        return false;
    }

    function allowedElement(obj) {
        return obj.tagName === 'LI' || obj.tagName === 'UL';
    }

    function spannify(el) {
        var self = this;


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
    }

    function onClickDelegate(el, func) {
        el.onclick = function (ev) {
            var e = ev || window.event;
            var target = e.target || e.srcElement;
            if (target.tagName !== 'SPAN') {
                return;
            }
            if (func) {
                func(target);
            }
        };
    }

    function defaultCollapse(target) {
        var li = target.parentNode;
        var n = li.getElementsByTagName('ul')[0];
        if (!n) {
            return;
        }
        n.style.display = n.style.display ? '' : 'none';
    }

    function Menu(el) {
        var self = this;
        var self_el = el;

        function defaultOnCLick() {
            self_el.style.backgroundColor = self_el.style.backgroundColor ? '' : 'green';
        }

        if (typeof el === 'object' && el.tagName === 'UL') {
            this.onClick = defaultOnCLick;
            if (el.hasAttribute('data-list')) {
                onClickDelegate(el, function (t) {
                    self.onClick(t);
                });
                el.setAttribute('data-menu', 'true');
                this.binded = true;
            }
        }

        return this;
    }


    function buildStructure(dataObject, element) {
        if (typeof dataObject === 'object' && element.nodeType && element.nodeType == 1) {
            var span = createElement('span');
            element.setAttribute(ATTR_DATA_ID, dataObject.id);
            span.appendChild(document.createTextNode(dataObject.name));
            element.appendChild(span);

            if (dataObject.ch && dataObject.ch.length > 0) {
                var ul = createElement('ul');

                for (var i = 0; i < dataObject.ch.length; i++) {
                    var obj1 = dataObject.ch[i];
                    var li = createElement('li');
                    buildStructure(obj1, li);
                    ul.appendChild(li);
                }
                element.appendChild(ul);
            }
        }
    }

    var ATTR_DATA_LIST = 'data-list';
    var ATTR_DATA_ID = 'data-list';

    function addMenuAttr(el) {
        el.setAttribute(ATTR_DATA_LIST, 'menu');
    };

    function ElMenu(obj, elToInsert) {
        var canInsert = elToInsert && elToInsert.nodeType && elToInsert.nodeType == 1;
        var self = this;
        this.rootUL = '';
        /**
         * function
         *
         * @type {string}
         */
        this.onClick = '';

        if (obj && typeof obj === 'object') {
            var isArray = Array.isArray(obj);
            var isHasOnlyChildren = !obj.id && !!obj.ch && Array.isArray(obj.ch) && obj.ch.length > 0;
            var ul = createElement('ul');
            this.rootUL = ul;
            addMenuAttr(ul);

            if (isArray || isHasOnlyChildren) {
                var arr = isArray ? obj : obj.ch;

                for (var i = 0; i < arr.length; i++) {
                    var ch = arr[i];
                    var li = createElement('li');
                    buildStructure(ch, li);
                    ul.appendChild(li);
                }
            } else {
                buildStructure(obj, ul);
            }
            onClickDelegate(this.rootUL, function (t) {
                var li = t.parentNode;
                self.onClick(t, li.getAttribute(ATTR_DATA_ID))
            });
            if (canInsert) {
                elToInsert.appendChild(this.rootUL);
            }
        }
        return this;

    }

    return  {
        Menu: Menu,
        ElMenu: ElMenu
    }
})();



