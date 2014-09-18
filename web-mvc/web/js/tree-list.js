/**
 * Created by mbondarenko on 17.09.2014.
 */
/**
 *    usage : <p/>
 *    var t = new List({
            url: 'REGULATOR',
            baseUrl: 'http://localhost/page/taxonomy/',
            rootElement: $('#userPrefsModel  div[tree-list="1"]')
        });
 t.change = function(e){
            //do work
        }
 * @param sett
 * @returns {{self: List, render: _render, selected: number, change: onChange}}
 * @constructor
 */
function List(sett) {
    var self = this;
    var settings = $.extend({
        //concrete url for data
        url: '',
        //base url of REST
        baseUrl: '',
        //element to attach this List
        rootElement: '',
        // template of row
        templateText: '<li data-uid="-1" data-name="-"><div><label>&mdash;</label></div></li> ' +
            '<% _.each(list, function (d) { %>' +
            '  <li data-uid="<%=d.id%>" data-name="<%=d.name%>">' +
            '   <div tabindex="-1"><label><%=d.name%></label></div>' +
            '</li> ' +
            '<% });%>',
        //template of dropdowns main div
        divTemplate: '<div class="list dropdown">' +
            '<div data-toggle="dropdown" class="" ><label uid="name">&mdash;</label></div> ' +
            '<ul class="dropdown-menu" role="menu" ></ul>' +
            '</div>'
    }, sett);
    //for prod use ''
    var forUserFriendly = '';
    //list of allowed tags in list
    var isAllowedTag = function (target) {
        return target === 'LABEL' || target === 'DIV' || target === 'LI' || target === "UL";
    };
    //controller
    var ctrl = {
        //Get data by base url + additional url params
        _getData: function (dataUrl) {
            $.getJSON(settings.baseUrl + settings.url, function (data) {
                model = data;
                view._render();
            })
        },

        getData: function () {
            ctrl._getData(settings.url);
        },

        init: function () {
            view.init();
            ctrl.getData();
        },

        getRoot: function () {
            return view.ul;
        },

        doOnClick: function (ev) {
            var e = ev || window.event;
            var target = e.target || e.srcElement;
            if (!isAllowedTag(target.tagName)) {
                return;
            }

            var $parent;
            //dirty hack. user can click on label or on her parent div
            if (target.tagName === 'LABEL') {
                $parent = $(target).parent().parent();
            } else {
                $parent = $(target).parent();
            }
            //change label
            view._setSelected($parent);
            view._changeFaceLabel($parent.attr('data-name'));
            var selectedValue = $parent.attr('data-uid');
            if (selectedId != selectedValue)
                ctrl.onChange(selectedValue);
            selectedId = selectedValue;
            //log if presented
            if (console) {
                console.log('selected ' + selectedValue)
            }
        },


        onChange: function (id) {
            api.change(id);
        }
    };
    //view
    var view = {
        //generator
        templater: _.template(settings.templateText),
        ul: 'content list ',
        face: ' name of component',
        dropdown: 'div that is dropdown',
        _render: function () {
            ctrl.getRoot().html(view.templater({list: model}));
        },
        _changeFaceLabel: function (val) {
            view.face.text(val)
        },
        _setSelected: function (el) {
            view.ul.find('li').removeClass('selected');
            el.addClass('selected');

        },
        init: function () {
            settings.rootElement.append($.parseHTML(settings.divTemplate));
            this.ul = $('ul.dropdown-menu', settings.rootElement);
            this.face = $('label[uid="name"]', settings.rootElement);
            this.dropdown = $('div.dropdown', settings.rootElement);
            //init bootstrap dropdown
            $('div[data-toggle="dropdown"]', settings.rootElement).dropdown();
            //on click behavior
            this.ul.on('click', ctrl.doOnClick)
        }
    };

    var model = {};
    //default selected value
    var selectedId = -1;

    ctrl.init();

    var api = {
        self: self,
        render: function () {
            view._render();
        },
        selected: selectedId,
        change: ctrl.onChange
    };
    return api;
}


function SearchList(sett) {
    var self = this;
    var settings = $.extend({
        //concrete url for data
        url: '',
        //base url of REST
        baseUrl: '',
        //element to attach this List
        rootElement: '',
        // template of row
        templateText: '<li data-uid="-1" data-name="-"><div><label>&dash;</label></div></li> ' +
            '<% _.each(list, function (d) { %>' +
            '  <li data-uid="<%=d.id%>" data-name="<%=d.name%>">' +
            '   <div tabindex="-1"><label><%=d.name%></label></div>' +
            '</li> ' +
            '<% });%>',
        //template of dropdowns main div
        divTemplate: '<div class="search-list dropdown">' +
            '<div data-toggle="dropdown" class="" ><label uid="name">&dash;</label></div> ' +
            '<div class="dropdown-menu"> ' +
            '<label><input class="form-control" type="text" /></label>' +
            '<ul  role="menu" ></ul></div>' +
            '</div>'
    }, sett);
    //for prod use ''
    var forUserFriendly = '';
    //list of allowed tags in list
    var isAllowedTag = function (target) {
        return target === 'LABEL' || target === 'DIV' || target === 'LI' || target === "UL";
    };
    //controller
    var ctrl = {
        //Get data by base url + additional url params
        _getData: function (dataUrl) {
            $.getJSON(settings.baseUrl + settings.url, function (data) {
                model = data;
                view._render();
            })
        },

        getData: function () {
            ctrl._getData(settings.url);
        },

        init: function () {
            view.init();
            ctrl.getData();
        },

        getRoot: function () {
            return view.ul;
        },
        filter: function (input) {
            var st = model;
            input = input || '';
            input = input.toLowerCase();
            var filtered = _.filter(st, function (el) {
                if (el.name === 'undefined') {
                    return false;
                }
                return  (el.name.toLowerCase().search(input) > -1);
            });
            view._render(filtered);
        },

        doOnClick: function (ev) {
            var e = ev || window.event;
            var target = e.target || e.srcElement;
            if (!isAllowedTag(target.tagName)) {
                return;
            }

            var $parent;
            //dirty hack. user can click on label or on her parent div
            if (target.tagName === 'LABEL') {
                $parent = $(target).parent().parent();
            } else {
                $parent = $(target).parent();
            }
            //change label
            view._setSelected($parent);
            view._changeFaceLabel($parent.attr('data-name'));
            var selectedValue = $parent.attr('data-uid');
            if (selectedId != selectedValue)
                ctrl.onChange(selectedValue);
            selectedId = selectedValue;
            //log if presented
            if (console) {
                console.log('selected ' + selectedValue)
            }
        },
        doShowHide: function (ev) {
            var e = ev || window.event;
            var target = e.target || e.srcElement;
            //ignore on input
            if (target.tagName === 'INPUT') {
                return false;
            }
            if (view.dropdown.hasClass('open')) {
                view.hideDDown();
            }
            else {
                view.showDDown();
            }

        },
        doOnInput: function (ev) {
            var val = view.input.val();
            if (val === '') {
                view._render();
            }
            ctrl.filter(val);

            if (console) {
                console.log('val ' + val);
            }
        },
        //on change selected value
        onChange: function (id) {
            api.change(id);
        }
    };
    //view
    var view = {
        //generator
        templater: _.template(settings.templateText),
        ul: 'content list ',
        face: ' name of component',
        dropdown: 'div that is dropdown',
        input: 'input',
        _render: function (newModel) {
            if (newModel && _.isArray(newModel)) {
                ctrl.getRoot().html(view.templater({list: newModel}));
            } else {
                ctrl.getRoot().html(view.templater({list: model}));
            }
        },
        _changeFaceLabel: function (val) {
            view.face.text(val)
        },
        _setSelected: function (el) {
            view.ul.find('li').removeClass('selected');
            el.addClass('selected');

        },
        hideDDown: function () {
            settings.rootElement.removeClass('open')
            view.input.attr('disabled', 'disabled');
        },
        showDDown: function () {
            settings.rootElement.addClass('open');
            view.input.removeAttr('disabled');
        },
        init: function () {
            settings.rootElement.append($.parseHTML(settings.divTemplate));
            view.ul = $('ul[role="menu"]', settings.rootElement);
            view.face = $('label[uid="name"]', settings.rootElement);
            view.dropdown = $('div.dropdown', settings.rootElement);
            view.input = $('input', settings.rootElement);

            //on click behavior
            view.input.on('keyup', ctrl.doOnInput);
            view.dropdown.on('click', ctrl.doShowHide);
            view.ul.on('click', ctrl.doOnClick)
        }
    };

    var model = {};
    //default selected value
    var selectedId = -1;

    ctrl.init();

    var api = {
        self: self,
        render: function () {
            view._render();
        },
        selected: selectedId,
        change: ctrl.onChange
    };
    return api;
}


function ListTree(sett) {
    var self = this;
    var settings = $.extend({
        //concrete url for data
        url: '',
        //base url of REST
        baseUrl: '',
        //element to attach this List
        rootElement: '',
        // template of row
        templateText: '<li data-uid="-1" data-name="-"><div><label>&mdash;</label></div></li> ' +
            '<% _.each(list, function (d) { %>' +
            '  <li data-uid="<%=d.id%>" data-name="<%=d.name%>">' +
            '   <div tabindex="-1"><label><%=d.name%></label></div>' +
            '</li> ' +
            '<% });%>',
        //template of dropdowns main div
        divTemplate: '<div class="search-tree-list dropdown">' +
            '<div data-toggle="dropdown" class="" ><label uid="name">&mdash;</label></div> ' +
            '<ul class="dropdown-menu" role="menu" ></ul>' +
            '</div>'
    }, sett);
    //for prod use ''
    var forUserFriendly = '';
    //list of allowed tags in list
    var isAllowedTag = function (target) {
        return target === 'LABEL' || target === 'DIV' || target === 'LI' || target === "UL";
    };
    //controller
    var ctrl = {
        //Get data by base url + additional url params
        _getData: function (dataUrl) {
            $.getJSON(settings.baseUrl + settings.url, function (data) {
                model = data;
                view._render();
            })
        },

        getData: function () {
            ctrl._getData(settings.url);
        },

        init: function () {
            view.init();
            ctrl.getData();
        },

        getRoot: function () {
            return view.ul;
        },

        doOnClick: function (ev) {
            var e = ev || window.event;
            var target = e.target || e.srcElement;
            if (!isAllowedTag(target.tagName)) {
                return;
            }

            var $parent;
            //dirty hack. user can click on label or on her parent div
            if (target.tagName === 'LABEL') {
                $parent = $(target).parent().parent();
            } else {
                $parent = $(target).parent();
            }
            //change label
            view._setSelected($parent);
            view._changeFaceLabel($parent.attr('data-name'));
            var selectedValue = $parent.attr('data-uid');
            if (selectedId != selectedValue)
                ctrl.onChange(selectedValue);
            selectedId = selectedValue;
            //log if presented
            if (console) {
                console.log('selected ' + selectedValue)
            }
        },


        onChange: function (id) {
            api.change(id);
        }
    };
    //view
    var view = {
        //generator
        templater: _.template(settings.templateText),
        ul: 'content list ',
        face: ' name of component',
        dropdown: 'div that is dropdown',
        _render: function () {
            ctrl.getRoot().html(view.templater({list: model}));
        },
        _changeFaceLabel: function (val) {
            view.face.text(val)
        },
        _setSelected: function (el) {
            view.ul.find('li').removeClass('selected');
            el.addClass('selected');

        },
        init: function () {
            settings.rootElement.append($.parseHTML(settings.divTemplate));
            this.ul = $('ul.dropdown-menu', settings.rootElement);
            this.face = $('label[uid="name"]', settings.rootElement);
            this.dropdown = $('div.dropdown', settings.rootElement);
            //init bootstrap dropdown
            $('div[data-toggle="dropdown"]', settings.rootElement).dropdown();
            //on click behavior
            this.ul.on('click', ctrl.doOnClick)
        }
    };

    var model = {};
    //default selected value
    var selectedId = -1;

    ctrl.init();

    var api = {
        self: self,
        render: function () {
            view._render();
        },
        selected: selectedId,
        change: ctrl.onChange
    };
    return api;
}




