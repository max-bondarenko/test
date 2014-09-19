/**
 * Created by mbondarenko on 17.09.2014.
 */
/**
 * example : <p/>
 *  var t = new ListTree({
 *           url: 'poi',
 *           baseUrl: 'http://localhost/',
 *           rootElement: $('#userPrefsModel  div[tree-list="1"]')
 *       });
 *          t.onChange = function(e){
 *           //do work
 *       }

 * @param sett : settings
 * @returns {{reRender: reRender, filter: filter, fetchNewData: fetchNewData, selected: number, onChange: undefined}}
 */
function ListTree(sett) {
    var settings = $.extend({
        //concrete url for data
        url: '',
        //base url of REST
        baseUrl: 'taxonomy/',
        //element to attach this list
        rootElement: '',
        //empty el
        emptyElName: '-',
        emptyValue: '',
        //is show nodes collapsed when generating
        showCollapsed: false,
        //does need search
        //adds input and behavior
        hasSearch: false,
        //as tree or only firs level tree
        parseChildren: true,
        attachedInput: undefined

    }, sett);
    if (_.isEmpty(settings.rootElement) || !_.isObject(settings.rootElement)) {
        throw new Error('no element set');
    }
    //controller
    var ctrl = {
        //Get data by base url + additional url params
        _getData: function (dataUrl) {
            //roller can be added here
            $.getJSON(
                    settings.baseUrl + (_.isUndefined(dataUrl) ? settings.url : dataUrl)
                , function (data) {
                    model = data;
                    view._render();
                    ctrl.changeFace(view._findSelected(ctrl.getAttachedVal()));
                })
        },
        init: function () {
            if (settings.hasSearch) {
                //change filter strategy here
                ctrl.filter = settings.parseChildren
                    ? ctrl._filter(ctrl._filterByNameInLiefs)
                    : ctrl._filter(ctrl._filterOnlyFirstLevel);
            }
            if (!settings.attachedInput) {
                var attribute = settings.rootElement.attr('tree-list');
                if (attribute)
                    settings.attachedInput = $('#' + attribute)

            }
            view.init();
            ctrl._getData();
        },
        getAttachedVal: function () {
            return settings.attachedInput.val();
        },
        getRoot: function () {
            return view.ul;
        },
        filter: function (input) {
        },
        _comparator: function (elName, search) {
            if (_.isUndefined(elName) || _.isUndefined(search)) return false;
            return elName.toLowerCase().search(search) > -1;
        },
        _filterOnlyFirstLevel: function (src, searchString) {
            return _.filter(src, function (el) {
                return  ctrl._comparator(el.name, searchString);
            });
        },
        _filterByNameInLiefs: function (src, searchStr) {
            function rec(e) {
                var r ,
                    retE,
                    i;
                var item;
                if (e.children && e.children.length > 0) {
                    r = [];
                    for (i = 0; i < e.children.length; i++) {
                        item = rec(e.children[i]);
                        if (!_.isNull(item)) {
                            r.push(item)
                        }
                    }

                    if (r.length > 0) {
                        retE = {};
                        retE.children = r;
                        retE.id = e.id;
                        retE.name = e.name;
                        return retE;
                    }
                } else {
                    if (ctrl._comparator(e.name, searchStr)) {
                        return e;
                    }
                }
                return null;
            }

            var ret = [];
            var item;
            for (var i = 0; i < src.length; i++) {
                item = rec(src[i]);
                if (!_.isNull(item))
                    ret.push(item);
            }
            return ret;
        },
        _filter: function (filterStrategy) {
            return function (input) {
                var sourceModel = model;
                input = input || '';
                input = input.toLowerCase();

                var filtered = filterStrategy(sourceModel, input);
                view._render(filtered);
            }
        },
        changeFace: function (target) {
            var $div = $(target);
            //change label
            view._setSelected($div.parent());
            view._changeFaceLabel($div.attr('data-name'));
            var selectedValue = $div.attr('data-uid');
            if (selectedId != selectedValue) {
                selectedId = selectedValue;
                ctrl.onChange(selectedValue);
            }
            //log if presented
            if (console) {
                console.log('selected ' + selectedValue)
            }
        },
        clear: function () {
            ctrl.changeFace(view.first());
        },
        doOnClick: function (ev) {
            var e = ev || window.event;
            var target = e.target || e.srcElement;
            var name = target.tagName;

            if (!(name === 'LABEL'
                || name === 'DIV')) {
                return false;
            }
            //dirty hack. user can click on label or on her parent div
            if (name === 'LABEL')
                target = target.parentNode;
            //open/close children
            if (!target.hasAttribute('data-uid')) {
                view.openCloseChildren(target);
                return false;
            }
            ctrl.changeFace(target);
        },
        doShowHide: function (ev) {
            var e = ev || window.event;
            var target = e.target || e.srcElement;
            var name = target.tagName;
            //ignore on input or arrow or empty space in list
            if (name === 'INPUT'
                || name === 'SPAN'
                || name === 'UL') {
                return false;
            }

            if (view.dDownVisible()) {
                view.hideDDown();
            } else {
                view.showDDown();
            }
        },
        doOnInput: function (ev) {
            var val = view.input.val();
            var timer;
            //do delay on input
            if (timer) {
                clearTimeout(timer);
            }
            timer = setTimeout(function () {
                if (val === '') {
                    view._render();
                }
                ctrl.filter(val);
            }, 350);
            //end do delay on input
        },
        onChange: function (id) {
            if (api.onChange)
                api.onChange(id);
            if (settings.attachedInput)
                settings.attachedInput.val(id);
        },
        preventSubmitOnInput: function (ev) {
            ev = ev || window.event;
            var code = ev.keyCode || ev.which;
            if (code == 13) {
                ev.preventDefault();
                return false;
            }
        }
    };
    //view
    var view = {
        emptyTmpl: _.template('<li class="selected" ><div first data-uid="' + settings.emptyValue + '" data-name="<%=obj%>"><label><%=obj%></label></div></li>'),
        noChildrenTmpl: _.template('<li><div data-uid="<%=d.id%>" data-name="<%=d.name%>" tabindex="-1"><label><%=d.name%></label></div></li>'),
        hasChidrenTmpl: _.template(
                '<li class="has-child ' + (settings.showCollapsed ? 'child-hidden' : '') + '">' +
                '<div tabindex="-1" data-uid="<%=d.id%>" data-name="<%=d.name%>" >' +
                '<div class="arrow left">' +
                '   <span></span>' +
                '</div>' +
                '<label><%=d.name%></label>' +
                '</div>' +
                '<ul><% _.each(children,function(child){%><%=child%><%}); %></ul>' +
                '</li>'
        ),
        //template of drop-downs main div
        divTemplate: '<div class="search-tree-list dropdown">' +
            '<div data-toggle="dropdown"><label uid="name">' + settings.emptyElName + '</label></div>' +
            '<div class="dropdown-menu"> ' +
            (settings.hasSearch ? '<label><input class="form-control" type="text"/></label>' : '' ) +
            '<div>' +
            '<ul role="menu"></ul>' +
            '</div>' +
            '</div>' +
            '</div>',
        //control structures
        ul: 'content list ',
        face: ' name of component',
        dropdown: 'div that is dropdown',
        first: function () {
            return $('div[first]', view.ul);
        },
        //functions
        _render: function (newModel) {
            var arr;

            function recursion(m) {
                var i;
                var s = [];
                _.each(m, function (el) {
                    if (settings.parseChildren && el.children && el.children.length > 0) {
                        s.push(view.hasChidrenTmpl({d: el, children: recursion(el.children)}));
                    } else {
                        // no children
                        s.push(view.noChildrenTmpl({d: el}));
                    }
                });
                return s;
            }

            arr = recursion(newModel ? newModel : model);
            arr.unshift(view.emptyTmpl(settings.emptyElName));
            ctrl.getRoot().html(arr.join(""));
        },
        _changeFaceLabel: function (val) {
            view.face.text(val)
        },
        _setSelected: function ($el) {
            view.ul.find('li').removeClass('selected');
            $el.addClass('selected');

        },
        _findSelected: function (dataUid) {
            return $('div[data-uid="' + dataUid + '"]', view.ul);
        },
        dDownVisible: function () {
            return view.dropdown.hasClass('open')
        },
        hideDDown: function () {
            settings.rootElement.removeClass('open')
            if (settings.hasSearch)
                view.input.attr('disabled', 'disabled');
        },
        showDDown: function () {
            settings.rootElement.addClass('open');
            if (settings.hasSearch)
                view.input.removeAttr('disabled');
        },
        //arrow rolled by css styles
        openCloseChildren: function (div) {
            var $li = $(div.parentNode.parentNode);
            var $div = $(div);
            if ($li.hasClass('child-hidden')) {
                $li.removeClass('child-hidden')
            } else {
                $li.addClass('child-hidden');
            }
        },
        //init
        init: function () {
            settings.rootElement.append($.parseHTML(view.divTemplate));
            view.ul = $('ul[role="menu"]', settings.rootElement);
            view.face = $('label[uid="name"]', settings.rootElement);
            view.dropdown = $('div.dropdown', settings.rootElement);
            if (settings.hasSearch) {
                view.input = $('input', settings.rootElement);
                //on click behavior
                view.input.on('keyup', ctrl.doOnInput);

                view.input.on('keydown', ctrl.preventSubmitOnInput);
            }
            view.dropdown.on('click', ctrl.doShowHide);
            view.ul.on('click', ctrl.doOnClick)
        }
    };

    var model = {};
    //default selected value
    var selectedId = -1;

    ctrl.init();

    var api = {
        //redraw current model
        reRender: function () {
            view._render();
        },
        clear: function () {
            ctrl.clear();
        },
        //filter by given string
        filter: function (s) {
            var srch = s || '';
            ctrl.filter(srch);
        },
        //get new data from server
        //use empty string if U needs just to refetch
        // by current ulr
        fetchNewData: function (newDataUrl) {
            if (newDataUrl && newDataUrl.search) {  //is string
                settings.url = newDataUrl;
            }
            ctrl._getData();
        },
        //get currently selected id
        //if no selection returns -1
        selected: selectedId,
        //behavior on real change data
        onChange: undefined
    };
    return api;
}




