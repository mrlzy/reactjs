<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mrlzy.shiro.weixin.WeiXinConfig" %>
<%@ page import="com.mrlzy.shiro.weixin.WeiXinException" %>
<%@ page import="com.mrlzy.shiro.session.ShiroSessionUtils" %>
<%@ page import="com.mrlzy.shiro.weixin.client.WeiXinWebClient" %>
<%@ page import="org.apache.shiro.web.util.WebUtils" %>
<%
      String[]  ss=null;
      WeiXinWebClient client = ShiroSessionUtils.getWeiXinWebClient();
      boolean b=false;
      String msg="";
      String url=request.getRequestURL().toString().replace("/WEB-INF/jsp","").replace(".jsp","");
      try{
          ss=WeiXinConfig.authorCreate(url);
      }catch (WeiXinException e){
           b=true;
           msg=e.getMessage();
      }


%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>主要政策</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
    <link rel="stylesheet" href="../../assets/weixin/style/weui.css"/>
    <link rel="stylesheet" href="../../assets/weixin/example/example.css"/>

    <!-- custom js -->
    <script src="../../assets/common/js/share.js" ></script>
    <script src="../../assets/weixin/jweixin-1.1.0.js"></script>

</head>
<body ontouchstart="">
<% if(b){ %>
  <div>页面错误:<%=msg%></div>
<%}else{%>
<div>
    小页面，求转发



</div>
<%if(client.getOp_id().equals(request.getParameter("last_id"))){%>
    <a href="#">有小惊喜请点击</a>

<%}else{%>

        <script>
         wx.config({
                debug: true,
                appId: '<%=WeiXinConfig.WEIXIN_APPID%>',
                timestamp: <%=ss[0]%>,
                nonceStr: '<%=ss[1]%>',
                signature: '<%=ss[2]%>',
                jsApiList: [
                    'onMenuShareTimeline',
                    'onMenuShareAppMessage',
                    'onMenuShareQQ',
                    'onMenuShareWeibo',
                    'onMenuShareQZone'
                ]
            });

            wx.ready(function(){
                var title='我的文章';
                var op_id='<%=client.getOp_id()%>';
                var url='<%=url%>';
                var last_id='<%=request.getParameter("last_id")%>';

                //分享到朋友圈
                wx.onMenuShareTimeline({
                    title: title,
                    desc: op_id+'转发的', // 分享描述
                    link: url+'?last_id='+op_id,
                    success: function (res) {
                        alert('些处插入:['+op_id+','+last_id+','+url+',onMenuShareTimeline]')
                    },
                    cancel: function (res) {
                        alert('已取消');
                    },
                    fail: function (res) {
                        alert(JSON.stringify(res));
                    }
                });
                //分享给朋友
                wx.onMenuShareAppMessage({
                    title: title, // 分享标题
                    desc: op_id+'转发的', // 分享描述
                    link: url+'?last_id='+op_id, // 分享链接
                    success: function () {
                           alert('些处插入:['+op_id+','+last_id+','+url+',onMenuShareAppMessage]')
                    },
                    cancel: function () {

                    }
                });

                wx.onMenuShareQQ({
                    title: title, // 分享标题
                    desc: op_id+'转发的', // 分享描述
                    link: url+'?last_id='+op_id, // 分享链接
                    success: function () {
                        alert('些处插入:['+op_id+','+last_id+','+url+',onMenuShareQQ]')

                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
                    }
                });

                wx.onMenuShareWeibo({
                    title: title, // 分享标题
                    desc: op_id+'转发的', // 分享描述
                    link: url+'?last_id='+op_id, // 分享链接
                    success: function () {
                        alert('些处插入:['+op_id+','+last_id+','+url+',onMenuShareWeibo]')

                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
                    }
                });

                wx.onMenuShareQZone({
                    title: title, // 分享标题
                    desc: op_id+'转发的', // 分享描述
                    link: url+'?last_id='+op_id, // 分享链接
                    success: function () {
                        alert('些处插入:['+op_id+','+last_id+','+url+',onMenuShareQZone]')

                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
                    }
                });


            });






        </script>
    <%}%>

<%}%>
</body>
</html>

