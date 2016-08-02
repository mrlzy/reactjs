var Router = ReactRouter; // 由于是html直接引用的库，所以 ReactRouter 是以全局变量的形式挂在 window 上
var Route = ReactRouter.Route;
var RouteHandler = ReactRouter.RouteHandler;
var Link = ReactRouter.Link;
var StateMixin = ReactRouter.State;


/**
 * 图书列表组件
 */
var Books = React.createClass({displayName: "Books",
    render: function() {
        return (
            React.createElement("ul", null,
                React.createElement("li", {key: 1}, React.createElement(Link, {to: "book", params: {id: 1}}, "活着")),
                React.createElement("li", {key: 2}, React.createElement(Link, {to: "book", params: {id: 2}}, "挪威的森林")),
                React.createElement("li", {key: 3}, React.createElement(Link, {to: "book", params: {id: 3}}, "从你的全世界走过")),
                React.createElement(RouteHandler, null)
            )
        );
    }
});

/**
 * 单本图书组件
 */
var Book = React.createClass({displayName: "Book",
    mixins: [StateMixin],

    render: function() {
        return (
            React.createElement("article", null,
                React.createElement("h1", null, "这里是图书 id 为 ", this.getParams()['id'], " 的详情介绍")
            )
        );
    }
});

/**
 * 电影列表组件
 */
var Movies = React.createClass({displayName: "Movies",
    render: function() {
        return (
            React.createElement("ul", null,
                React.createElement("li", {key: 1}, React.createElement(Link, {to: "movie", params: {id: 1}}, "煎饼侠")),
                React.createElement("li", {key: 2}, React.createElement(Link, {to: "movie", params: {id: 2}}, "捉妖记")),
                React.createElement("li", {key: 3}, React.createElement(Link, {to: "movie", params: {id: 3}}, "西游记之大圣归来"))
            )
        );
    }
});

/**
 * 单部电影组件
 */
var Movie = React.createClass({displayName: "Movie",
    mixins: [StateMixin],

    render: function() {
        return (
            React.createElement("article", null,
                React.createElement("h1", null, "这里是电影 id 为 ", this.getParams().id, " 的详情介绍")
            )
        );
    }
});






// 应用入口
var App = React.createClass({displayName: "App",
    render: function() {
        return (
            React.createElement("div", {className: "app"},
                React.createElement("nav", null,
                    React.createElement("a", {href: "#"}, React.createElement(Link, {to: "movies"}, "电影")),
                    React.createElement("a", {href: "#"}, React.createElement(Link, {to: "books"}, "图书"))
                ),
                React.createElement("section", null,
                    React.createElement(RouteHandler, null)
                )
            )
        );
    }
});


// 定义页面上的路由
var routes = (
    React.createElement(Route, {handler: App},
        React.createElement(Route, {name: "movies", handler: Movies}),
        React.createElement(Route, {name: "movie", path: "/movie/:id", handler: Movie}),
        React.createElement(Route, {name: "books", handler: Books}),
        React.createElement(Route, {name: "book", path: "/book/:id", handler: Book})
    )
);


Router.run(routes, Router.HashLocation, function(Root){
    React.render(<Root />, document.body);
});