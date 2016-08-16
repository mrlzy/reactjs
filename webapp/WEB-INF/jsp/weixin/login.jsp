<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>渠道微信平台</title>
    <link rel="stylesheet" href="../assets/weixin/style/weui.css"/>
    <link rel="stylesheet" href="../assets/weixin/example/example.css"/>

    <!-- custom js -->
    <script src="../assets/common/js/share.js" ></script>

    <!--[if !IE]> -->
    <script type="text/javascript">
        window.jQuery || document.write("<script src='../assets/ace/js/jquery.js'>"+"<"+"/script>");
    </script>

    <!-- <![endif]-->

    <!--[if IE]>
    <script type="text/javascript">
        window.jQuery || document.write("<script src='../assets/ace/js/jquery1x.js'>"+"<"+"/script>");
    </script>
    <![endif]-->

    <script type="text/javascript">
        var error='${error}';

        var dd=0;

        
        

        setInterval(
                function(){
                    if(dd!=0){
                        dd=dd-3;
                        if(dd<0) dd=0;
                    }
                },
                3000);
        
        function submitForm() {
            if(!$('#sub_btn').hasClass('weui_btn_disabled')){
                document.myform.submit();
            }
        }

        function showAlert(msg,time){
            time=time||3000;
            $('.js_tooltips').text(msg);
            $('.js_tooltips').show();
            setTimeout(function (){
                $('.js_tooltips').hide();
            }, time);
        }



        function selectCode() {
            var username=document.myform.username.value;

            if(username==null){
                showAlert('帐号不能为空');
                return ;
            }
            if(username.length>20){
                showAlert('帐号不存在');
                return ;
            }
            if(dd!=0){
                showAlert('请'+dd+'秒后获取');
                return ;
            }


            $.ajax({
                url: 'selectCode',
                data: 'username='+username,
                type: 'POST',
                dataType: 'json',
                success : function(menudata) {
                     if(menudata.success){
                         showAlert('已向手机号为['+menudata.msg+']发送验证码');
                         dd=60;
                         $('#sub_btn').removeClass('weui_btn_disabled').removeClass("weui_btn_default").addClass("weui_btn_primary");
                     }else{
                         showAlert(menudata.msg);

                     }
                },
                error: function(response) {
                    //console.log(response);
                }
            })
        }

    </script>
</head>
<body>
    <div class="hd">
        <h1 class="page_title">温州移动</h1>
        <p class="page_desc">渠道微信平台身份认证</p>
    </div>
    <div class="bd">
        <div class="weui_toptips weui_warn js_tooltips">${error}</div>
        <form action="#" method="post"  name="myform" >
        <div class="weui_cells weui_cells_form">
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">渠道账号</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" type="text" name="username"    pattern="[a-zA-z0-9_]{5,20}"  title="帐号信息有误" placeholder="请输入账号"/>
                </div>
            </div>
            <div class="weui_cell weui_vcode">
                <div class="weui_cell_hd"><label class="weui_label">验证码</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input"  name="code" type="number"  pattern="[0-9]{6}"  title="验证码有误" placeholder="请输入验证码"/>
                </div>
                <div class="weui_cell_bd">
                    <a href="javascript:selectCode();" class="weui_btn weui_btn_plain_default">获取验证码</a>
                </div>
            </div>
            <div class="weui_btn_area">
                <a id="sub_btn" href="javascript:submitForm();" class="weui_btn weui_btn_disabled weui_btn_default">登录</a>
            </div>

        </div>
        </form>
    </div>
    <script type="text/javascript">
            if(error!=''){
                showAlert(error,5000);
            }

    </script>
</body>
