<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  String webContext=request.getContextPath(); %>
<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="utf-8">
        <title>系统资源管理平台</title>
		<link href="<%=webContext%>/assets/login/login.css" rel="stylesheet">
        <script src="<%=webContext%>/assets/common/js/jquery-1.10.2.min.js"></script>
        <script src="<%=webContext%>/assets/common/js/cookie.js"></script>      
        <script src="<%=webContext%>/assets/login/login.js"></script>
      
    </head>
    <script>
        var rootPath ='<%=webContext%>';
          
    </script>
    <body>
 	<!--[if lte IE 6]>
    	<div class="alert alert-danger ie6">您所使用的浏览器版本太低，无法正常浏览器本站。请升级或更换IE8及以上版本或谷歌火狐等浏览器。</div>
    <![endif]-->
        <!--头部 开始-->
        <div class="header">
	        <div class="top-line"></div>
	        <div class="wrapper">
		        <div class="top">资源管理平台&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;
                温州移动</div>
                <div class="nav clearfix">
                    <ul class="main-nav">
                        <li><a href="#">首页</a></li>
                        <li><a href="#">帮助中心</a></li>
                        <li><a href="#">关于我们</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <!--头部 结束-->
<!--中部内容区 开始-->
<form  id="myform"  name="myform" action="" method="post"  >
<div class="login-wrap">
    <div class="wrapper">
        <div class="login-txt"></div>
        <div class="login">
            <h2 class="form-title">欢迎您 , 请登录</h2>
            <div class="form-cnt">
                <div id="errorContainer"  class="form-error" >${error}</div>
                <div class="form-item">
                    <label class="label">帐号:</label>
                    <input type="text"class="inp-txt" value=""  name="username" placeholder="请输入用户名" />
                </div>
                <div class="form-item">
                    <label class="label">密码:</label>
                    <input type="password" name="password" class="inp-txt pass"   placeholder="请输入密码" />
                </div>
                <div class="form-item form-code">
                    <label class="label">验证码:</label>
                    <input type="text" class="inp-txt" placeholder="验证码" value="" maxlength="4" name="validCode"
                        value="" />
                    <span>
                        <img id="validCodeImg"  onclick="refreshValiCode()" style="cursor: pointer;"  src="<%=webContext%>/image"  alt="验证码" title="看不清,换一张" />
                        <a href="javascript:refreshValiCode()" title="看不清,换一张"  >刷新</a> </span>
                </div>
                <div class="form-item form-txt">
                    <span class="fl">
                        <input type="checkbox" id="autoLogin"   name="rememberMe"  value="1">记住帐号</span>
                        <a href="<%=webContext%>/login/register?flag=1" class="fr" >忘记密码？</a>
                </div>
                <div class="form-item">
                    <a href="javascript:void();" class="btn-big" onclick="login()" >登&nbsp;录</a>
                </div>
            </div>
        </div>
    </div>
</div>
</form>
<!--中部内容区 结束 -->
<div class="footDiv black">
    <div class="wal">
        <ul>
            <li>
                <h5>
                    <a href="#" target="_blank">####</a></h5>
                <dl>
                    <dd> <a href="#" target="_blank">####</a></dd>
                </dl>
            </li>
            <li>
                <h5>
                    <a href="#" target="_blank">####</a></h5>
                <dl>
                    <dd> <a href="#" target="_blank">####</a></dd>
                </dl>
            </li>
            <li>
                <h5>
                    <a href="#" target="_blank">####</a></h5>
                <dl>
                    <dd> <a href="#" target="_blank">####</a></dd>
                </dl>
            </li>
            <li>
                <h5>
                    <a href="#" target="_blank">####</a></h5>
                <dl>
                    <dd> <a href="#" target="_blank">####</a></dd>
                </dl>
            </li>
        </ul>
    </div>
</div>
        <div class="footer" id="footer" style="position: fixed;bottom: 0px;">
	            <p><a href="#" target="_blank">建议</a>
	            &copy;使用ie8及以上浏览器</p>
        </div>
        

<DIV id=loading-mask style="display: none;"></DIV> 
<DIV id=loading style="display: none;">
<DIV class=loading-indicator >
<IMG style="MARGIN-RIGHT: 8px"
	height=32
	src="<%=webContext%>/assets/login/img/ajax1.gif"
	width=36 align=absMiddle>正在初始化,请稍等...</DIV>
</DIV>
        
    </body>
</html>
