var Router = ReactRouter; // 由于是html直接引用的库，所以 ReactRouter 是以全局变量的形式挂在 window 上
var Route = ReactRouter.Route;
var RouteHandler = ReactRouter.RouteHandler;
var Link = ReactRouter.Link;
var DefaultRoute  = ReactRouter.DefaultRoute;

var StateMixin = ReactRouter.State;
var Redirect = ReactRouter.Redirect;

var NevbarLeft=React.createClass({
    render: function() {
        return (
            <div className="navbar-header pull-left">
                <a href="#" className="navbar-brand">
                    <small>
                        <i className="fa fa-leaf"></i>
                        {this.props.title}
                    </small>
                </a>
            </div>
        );
    }
});


var NevbarUserToggle=React.createClass({
    render: function() {
        return (
            <li className="light-blue">
                <a data-toggle="dropdown" href="#" className="dropdown-toggle">
                    <img className="nav-user-photo" src="assets/ace/avatars/user.jpg" alt="Jason's Photo" />
								<span className="user-info">
									<small>Welcome,</small>
                                    {this.props.name}
								</span>
                    <i className="ace-icon fa fa-caret-down"></i>
                </a>

                <ul className="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                    <li>
                        <a href="#">
                            <i className="ace-icon fa fa-cog"></i>
                            修改密码
                        </a>
                    </li>

                    <li>
                        <a href="profile.html">
                            <i className="ace-icon fa fa-user"></i>
                            个人信息
                        </a>
                    </li>


                    <li className="divider"></li>

                    <li>
                        <a href="logout">
                            <i className="ace-icon fa fa-power-off"></i>
                            注销
                        </a>
                    </li>
                </ul>
            </li>
        );
    }
});

var NevbarRight=React.createClass({
    render: function() {
        return (
            <div className="navbar-buttons navbar-header pull-right" >
                <ul className="nav ace-nav">
                    <NevbarUserToggle  name={this.props.name} />
                </ul>
            </div>
        );
    }
});

var Navbar= React.createClass({
    render: function() {
        return (
            <div    id="navbar" className="navbar navbar-default">
                <div className="navbar-container" id="navbar-container">
                    <NevbarLeft title="资源管理系统" />
                    <NevbarRight name="admin" />
                </div>
            </div>
        );
    }
});


var MenuItem = React.createClass({
    render: function() {

        if(!this.props.leaf){
            return  (
                <MenuSub  id={this.props.id}  icon={this.props.icon} name={this.props.name}  />
            );
        }
        var icon="menu-icon fa "+this.props.icon||'';
        var url=this.props.url||'template';
        var name=this.props.name||'';

        if(url.startWith("open:")){
            url=url.replace(/open:/,'');
            return (
                <li>
                    <a href={url}>
                        <i className={icon}></i>
                        <span className="menu-text"> {name}</span>
                    </a>
                    <b className="arrow"></b>
                </li>
            )
        }else{
            url=encodeURIComponent(url);
            return  (
                <li>
                    <Link to="webmove" params={{url: url}}>
                        <i className={icon}></i>
                        <span className="menu-text"> {name}</span>
                    </Link>


                    <b className="arrow"></b>
                </li>
            );
        }


    }
});



var MenuSub= React.createClass({

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
            <li>
                <a href="#"  className="dropdown-toggle">
                    <i className={icon}></i>
                    <span className="menu-text"> {name}</span>
                    <b className="arrow fa fa-angle-down"></b>
                </a>
                <b className="arrow"></b>

                <ul className="submenu">
                    {
                        this.state.items.map(function (item) {
                            return     <MenuItem  id={item.menu_id} leaf={item.leaf}  url={item.url} icon={item.icon_cls}  name={item.menu_name} />
                        })
                    }

                </ul>
            </li>
        );
    }
});



var Menu = React.createClass({
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
            <ul className="nav nav-list">
                    {this.state.items.map(
                        function (item) {
                            return     <MenuItem  id={item.menu_id} leaf={item.leaf}  url={item.url} icon={item.icon_cls}  name={item.menu_name} />

                        }
                    )}
            </ul>
        );
    }
});

var ShortcutsMin= React.createClass({
    render: function() {
        return (
                <div className="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                    <span className="btn btn-success"></span>

                    <span className="btn btn-info"></span>

                    <span className="btn btn-warning"></span>

                    <span className="btn btn-danger"></span>
                </div>
        );
    }
});

var ShortcutsLarge= React.createClass({
    render: function() {
        return (
                <div className="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                    <button className="btn btn-success">
                        <i className="ace-icon fa fa-signal"></i>
                    </button>
                    <i>&nbsp;</i>
                    <button className="btn btn-info">
                        <i className="ace-icon fa fa-pencil"></i>
                    </button>
                    <i>&nbsp;</i>
                    <button className="btn btn-warning">
                        <i className="ace-icon fa fa-users"></i>
                    </button>
                    <i>&nbsp;</i>

                    <button className="btn btn-danger">
                        <i className="ace-icon fa fa-cogs"></i>
                    </button>


                </div>
        );
    }
});



var Shortcuts= React.createClass({
    render: function() {
        return (
           <div className="sidebar-shortcuts" id="sidebar-shortcuts">
                <ShortcutsLarge />
               <ShortcutsMin />
            </div>
        );
    }
});


var SideBarCollapse = React.createClass({
    render: function() {
        return (
            <div className="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
                <i className="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
            </div>
        );
    }
});

var Sidebar = React.createClass({
    render: function() {
        return (
            <div id="sidebar" className="sidebar  responsive">
                <Shortcuts />
                <Menu />
                <SideBarCollapse />
            </div>
        );
    }
});

var Footer = React.createClass({
    render: function() {
        return (
            <div className="footer">
                <div className="footer-inner">
                    <div className="footer-content">
						<span className="bigger-120">
							<span className="blue bolder">Ace</span>
							Application &copy; 2016-2017
						</span>
                        &nbsp; &nbsp;
						<span className="action-buttons">
							<a href="#">
								<i className="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
							</a>
							<a href="#">
								<i className="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
							</a>
							<a href="#">
								<i className="ace-icon fa fa-rss-square orange bigger-150"></i>
							</a>
						</span>
                    </div>
                </div>
            </div>
        );
    }
});

var PageContent=React.createClass({
    render: function() {
        return (
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <RouteHandler />
                    </div>
                </div>
            </div>
        );
    }
});


var BreadCrumbs= React.createClass({
    render: function() {
        return (
            <div className="breadcrumbs" id="breadcrumbs">
                <ul className="breadcrumb">
                    <li>
                        <i className="ace-icon fa fa-home home-icon"></i>
                        <a href="#">Home</a>
                    </li>
                    <li className="active">Dashboard</li>
                </ul>
            </div>
        );
    }
});

var MainContent = React.createClass({
    render: function() {
        return (
            <div className="main-content">
                <div className="main-content-inner">
                    <BreadCrumbs />
                   <PageContent/>
                    <Footer />
                </div>
            </div>
        );
    }
});


var Container = React.createClass({
    render: function() {
        return (
            <div className="main-container" id="main-container">
                <Sidebar />
                <MainContent />
            </div>
        );
    }
});

var WebMove = React.createClass({
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
        return  <div></div>;
        ;
    }
});






var App = React.createClass({
    render: function() {
        return (
            <div>
                <Navbar />
                <Container/>
            </div>


        );
    }
});



var Index = React.createClass({
    mixins: [StateMixin],

    render: function() {
        return  <div>这是我们的主页面，到时候自己分离哦</div>;
        ;
    }
});


// 定义页面上的路由
var routes = (
    <Route handler={App}>
          <Route name="webmove" path="webmove/:url" handler={WebMove} />
          <DefaultRoute handler={Index} />
    </Route>
);


// 将匹配的路由渲染到 DOM 中

Router.run(routes, Router.HashLocation, function(Root){
    React.render(<Root />, document.body);
});












