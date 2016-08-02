var Router = ReactRouter; // 由于是html直接引用的库，所以 ReactRouter 是以全局变量的形式挂在 window 上
var Route = ReactRouter.Route;
var RouteHandler = ReactRouter.RouteHandler;
var Link = ReactRouter.Link;
var DefaultRoute  = ReactRouter.DefaultRoute;

var StateMixin = ReactRouter.State;
var Redirect = ReactRouter.Redirect;

var NevbarLeft=React.createClass({displayName: "NevbarLeft",
    render: function() {
        return (
            React.createElement("div", {className: "navbar-header pull-left"}, 
                React.createElement("a", {href: "#", className: "navbar-brand"}, 
                    React.createElement("small", null, 
                        React.createElement("i", {className: "fa fa-leaf"}), 
                        this.props.title
                    )
                )
            )
        );
    }
});


var NevbarUserToggle=React.createClass({displayName: "NevbarUserToggle",
    render: function() {
        return (
            React.createElement("li", {className: "light-blue"}, 
                React.createElement("a", {"data-toggle": "dropdown", href: "#", className: "dropdown-toggle"}, 
                    React.createElement("img", {className: "nav-user-photo", src: "assets/ace/avatars/user.jpg", alt: "Jason's Photo"}), 
								React.createElement("span", {className: "user-info"}, 
									React.createElement("small", null, "Welcome,"), 
                                    this.props.name
								), 
                    React.createElement("i", {className: "ace-icon fa fa-caret-down"})
                ), 

                React.createElement("ul", {className: "user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close"}, 
                    React.createElement("li", null, 
                        React.createElement("a", {href: "#"}, 
                            React.createElement("i", {className: "ace-icon fa fa-cog"}), 
                            "修改密码"
                        )
                    ), 

                    React.createElement("li", null, 
                        React.createElement("a", {href: "profile.html"}, 
                            React.createElement("i", {className: "ace-icon fa fa-user"}), 
                            "个人信息"
                        )
                    ), 


                    React.createElement("li", {className: "divider"}), 

                    React.createElement("li", null, 
                        React.createElement("a", {href: "logout"}, 
                            React.createElement("i", {className: "ace-icon fa fa-power-off"}), 
                            "注销"
                        )
                    )
                )
            )
        );
    }
});

var NevbarRight=React.createClass({displayName: "NevbarRight",
    render: function() {
        return (
            React.createElement("div", {className: "navbar-buttons navbar-header pull-right"}, 
                React.createElement("ul", {className: "nav ace-nav"}, 
                    React.createElement(NevbarUserToggle, {name: this.props.name})
                )
            )
        );
    }
});

var Navbar= React.createClass({displayName: "Navbar",
    render: function() {
        return (
            React.createElement("div", {id: "navbar", className: "navbar navbar-default"}, 
                React.createElement("div", {className: "navbar-container", id: "navbar-container"}, 
                    React.createElement(NevbarLeft, {title: "资源管理系统"}), 
                    React.createElement(NevbarRight, {name: "admin"})
                )
            )
        );
    }
});


var MenuItem = React.createClass({displayName: "MenuItem",
    render: function() {

        if(!this.props.leaf){
            return  (
                React.createElement(MenuSub, {id: this.props.id, icon: this.props.icon, name: this.props.name})
            );
        }
        var icon="menu-icon fa "+this.props.icon||'';
        var url=this.props.url||'template';
        var name=this.props.name||'';

        if(url.startWith("open:")){
            url=url.replace(/open:/,'');
            return (
                React.createElement("li", null, 
                    React.createElement("a", {href: url}, 
                        React.createElement("i", {className: icon}), 
                        React.createElement("span", {className: "menu-text"}, " ", name)
                    ), 
                    React.createElement("b", {className: "arrow"})
                )
            )
        }else{
            url=encodeURIComponent(url);
            return  (
                React.createElement("li", null, 
                    React.createElement(Link, {to: "webmove", params: {url: url}}, 
                        React.createElement("i", {className: icon}), 
                        React.createElement("span", {className: "menu-text"}, " ", name)
                    ), 


                    React.createElement("b", {className: "arrow"})
                )
            );
        }


    }
});



var MenuSub= React.createClass({displayName: "MenuSub",

    getInitialState:function(){
        return {
            items:[]
        };
     },

    componentDidMount  :function(){
        $.post("/reactjs/getMenu",{menu_id:this.props.id},function(result){
            this.setState({items : result});
        }.bind(this),"json")
    },

    render: function() {
        var icon="menu-icon fa "+this.props.icon||'fa-caret-right';
        var name=this.props.name||'';
        return  (
            React.createElement("li", null, 
                React.createElement("a", {href: "#", className: "dropdown-toggle"}, 
                    React.createElement("i", {className: icon}), 
                    React.createElement("span", {className: "menu-text"}, " ", name), 
                    React.createElement("b", {className: "arrow fa fa-angle-down"})
                ), 
                React.createElement("b", {className: "arrow"}), 

                React.createElement("ul", {className: "submenu"}, 
                    
                        this.state.items.map(function (item) {
                            return     React.createElement(MenuItem, {id: item.menu_id, leaf: item.leaf, url: item.url, icon: item.icon_cls, name: item.menu_name})
                        })
                    

                )
            )
        );
    }
});



var Menu = React.createClass({displayName: "Menu",
    getInitialState:function(){
        return {
            items:[]
        };
    },

    componentDidMount  :function(){
        $.post("/reactjs/getRootMenu",{},function(result){
            this.setState({items : result});
        }.bind(this),"json")
    },


    render: function() {
        return (
            React.createElement("ul", {className: "nav nav-list"}, 
                    this.state.items.map(
                        function (item) {
                            return     React.createElement(MenuItem, {id: item.menu_id, leaf: item.leaf, url: item.url, icon: item.icon_cls, name: item.menu_name})

                        }
                    )
            )
        );
    }
});

var ShortcutsMin= React.createClass({displayName: "ShortcutsMin",
    render: function() {
        return (
                React.createElement("div", {className: "sidebar-shortcuts-mini", id: "sidebar-shortcuts-mini"}, 
                    React.createElement("span", {className: "btn btn-success"}), 

                    React.createElement("span", {className: "btn btn-info"}), 

                    React.createElement("span", {className: "btn btn-warning"}), 

                    React.createElement("span", {className: "btn btn-danger"})
                )
        );
    }
});

var ShortcutsLarge= React.createClass({displayName: "ShortcutsLarge",
    render: function() {
        return (
                React.createElement("div", {className: "sidebar-shortcuts-large", id: "sidebar-shortcuts-large"}, 
                    React.createElement("button", {className: "btn btn-success"}, 
                        React.createElement("i", {className: "ace-icon fa fa-signal"})
                    ), 
                    React.createElement("i", null, " "), 
                    React.createElement("button", {className: "btn btn-info"}, 
                        React.createElement("i", {className: "ace-icon fa fa-pencil"})
                    ), 
                    React.createElement("i", null, " "), 
                    React.createElement("button", {className: "btn btn-warning"}, 
                        React.createElement("i", {className: "ace-icon fa fa-users"})
                    ), 
                    React.createElement("i", null, " "), 

                    React.createElement("button", {className: "btn btn-danger"}, 
                        React.createElement("i", {className: "ace-icon fa fa-cogs"})
                    )


                )
        );
    }
});



var Shortcuts= React.createClass({displayName: "Shortcuts",
    render: function() {
        return (
           React.createElement("div", {className: "sidebar-shortcuts", id: "sidebar-shortcuts"}, 
                React.createElement(ShortcutsLarge, null), 
               React.createElement(ShortcutsMin, null)
            )
        );
    }
});


var SideBarCollapse = React.createClass({displayName: "SideBarCollapse",
    render: function() {
        return (
            React.createElement("div", {className: "sidebar-toggle sidebar-collapse", id: "sidebar-collapse"}, 
                React.createElement("i", {className: "ace-icon fa fa-angle-double-left", "data-icon1": "ace-icon fa fa-angle-double-left", "data-icon2": "ace-icon fa fa-angle-double-right"})
            )
        );
    }
});

var Sidebar = React.createClass({displayName: "Sidebar",
    render: function() {
        return (
            React.createElement("div", {id: "sidebar", className: "sidebar  responsive"}, 
                React.createElement(Shortcuts, null), 
                React.createElement(Menu, null), 
                React.createElement(SideBarCollapse, null)
            )
        );
    }
});

var Footer = React.createClass({displayName: "Footer",
    render: function() {
        return (
            React.createElement("div", {className: "footer"}, 
                React.createElement("div", {className: "footer-inner"}, 
                    React.createElement("div", {className: "footer-content"}, 
						React.createElement("span", {className: "bigger-120"}, 
							React.createElement("span", {className: "blue bolder"}, "Ace"), 
							"Application © 2016-2017"
						), 
                        "   ", 
						React.createElement("span", {className: "action-buttons"}, 
							React.createElement("a", {href: "#"}, 
								React.createElement("i", {className: "ace-icon fa fa-twitter-square light-blue bigger-150"})
							), 
							React.createElement("a", {href: "#"}, 
								React.createElement("i", {className: "ace-icon fa fa-facebook-square text-primary bigger-150"})
							), 
							React.createElement("a", {href: "#"}, 
								React.createElement("i", {className: "ace-icon fa fa-rss-square orange bigger-150"})
							)
						)
                    )
                )
            )
        );
    }
});

var PageContent=React.createClass({displayName: "PageContent",
    render: function() {
        return (
            React.createElement("div", {class: "page-content"}, 
                React.createElement("div", {class: "row"}, 
                    React.createElement("div", {class: "col-xs-12"}, 
                        React.createElement(RouteHandler, null)
                    )
                )
            )
        );
    }
});


var BreadCrumbs= React.createClass({displayName: "BreadCrumbs",
    render: function() {
        return (
            React.createElement("div", {className: "breadcrumbs", id: "breadcrumbs"}, 
                React.createElement("ul", {className: "breadcrumb"}, 
                    React.createElement("li", null, 
                        React.createElement("i", {className: "ace-icon fa fa-home home-icon"}), 
                        React.createElement("a", {href: "#"}, "Home")
                    ), 
                    React.createElement("li", {className: "active"}, "Dashboard")
                )
            )
        );
    }
});

var MainContent = React.createClass({displayName: "MainContent",
    render: function() {
        return (
            React.createElement("div", {className: "main-content"}, 
                React.createElement("div", {className: "main-content-inner"}, 
                    React.createElement(BreadCrumbs, null), 
                   React.createElement(PageContent, null), 
                    React.createElement(Footer, null)
                )
            )
        );
    }
});


var Container = React.createClass({displayName: "Container",
    render: function() {
        return (
            React.createElement("div", {className: "main-container", id: "main-container"}, 
                React.createElement(Sidebar, null), 
                React.createElement(MainContent, null)
            )
        );
    }
});

var WebMove = React.createClass({displayName: "WebMove",
    mixins: [StateMixin],
    doResponse:function(){
        url=decodeURIComponent(this.getParams().url);
        $(this.getDOMNode()).load(url, {}, function(){
        });
    },

    componentDidUpdate :function () {
        this.doResponse();
    },
    componentDidMount  :function(){
        this.doResponse();
    },

    render: function() {
        return  React.createElement("div", null);
        ;
    }
});






var App = React.createClass({displayName: "App",
    render: function() {
        return (
            React.createElement("div", null, 
                React.createElement(Navbar, null), 
                React.createElement(Container, null)
            )


        );
    }
});



var Index = React.createClass({displayName: "Index",
    mixins: [StateMixin],

    render: function() {
        return  React.createElement("div", null, "这是我们的主页面，到时候自己分离哦");
        ;
    }
});


// 定义页面上的路由
var routes = (
    React.createElement(Route, {handler: App}, 
          React.createElement(Route, {name: "webmove", path: "webmove/:url", handler: WebMove}), 
          React.createElement(DefaultRoute, {handler: Index})
    )
);


// 将匹配的路由渲染到 DOM 中

Router.run(routes, Router.HashLocation, function(Root){
    React.render(React.createElement(Root, null), document.body);
});












