<%@ page language="java" pageEncoding="utf-8" %>
<%
String giftId = request.getParameter("giftId");
	if(giftId==null)
		giftId ="0";
String code = request.getParameter("code");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>刮刮卡</title>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/wScratchPad.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/prefixfree.min.js"></script>
    <style type="text/css">
		
		body
        {
           width: 100%;
           margin :0px;
        }
        .container
        {
            position: relative;
            width: 100%;
           /* height: 960;*/
            margin: 0px auto 0;
            background: url("${pageContext.request.contextPath}/images/bg.png") no-repeat 0 0;
            background-size: 100% 100%;
        }
        .form {
		  height: 18.75em;
		  width: 25em;
		  background: #fff;
		  position: absolute;
		  top: 50%;
		  left: 50%;
		  transform: translate(-50%,-50%);
		  text-transform: uppercase;
		  font-family: "Bebas Neue", Arial;
		  color: #fff;
		}
		
		.form > div {
		  height: 6.25em;
		  width: 100%;
		}
		
		.username {
		  background-color: #4daf7c;
		  background: url("${pageContext.request.contextPath}/assets/images/btn_bg.png") no-repeat 0 0;
		}
		
		.username::after {
		  content: "";
		  width: 0px;
		  height: 0px;
		  position: absolute;
		  border-style: solid;
		  border-width: 0.5em 0.469em 0 0.469em;
		  border-color: #4daf7c transparent transparent transparent;
		  top: 6.25em;
		  left: 50%;
		  margin-left: -0.496em;
		}
		
		.login {
		  background-color: #e9c85d;
		  display: table;
		  left: 50%;
		}
		
		.login span {
		  display: table-cell;
		  vertical-align: middle;
		  text-align: center;
		  font-size:3em;
		  cursor: pointer;
		}
		
		input {
		  height: 62px;
		  width: 263px;
		  font-size: 32px;
		  text-align:center;
		  border: 0;
		  outline: 0;
		  color: #fff;
		  background: transparent;
		  border:0.033em #fff solid;
		  margin-left: 40px;
		  margin-top: 16px;
		  font-family: "Bebas Neue", Arial;
		}
		
		
		::placeholder {
		  color: #fff;
		}
		
		::-moz-placeholder {
		  color: #fff;
		}
		
		:-ms-input-placeholder {
		  color: #fff;
		}
		
		::-webkit-input-placeholder {
		  color: #fff;
		}        
        #wScratchPad
        {
            position: absolute;
            width: 57%;
            height:131;
            left: 22%;
            top: 36%;
            -margin-left: 143px;
            -margin-top: 353px;
            height: auto;
            border-radius: 5px;
            -border: 1px solid #444;
        }
        #inputPad
        {
            position: absolute;
            width: 57%;
            left: 22%;
            top: 36%;
            -margin-left: 143px;
            -margin-top: 200px;
            height: 131px;
            border-radius: 5px;
            border: 1px solid #444;
            visibility:hidden;
        }
        

    </style>

    
</head>
<body>
	<div class="container">
		<div id="wScratchPad" style="display:inline-block; position:absolute; ">
		</div>
		<div id="inputPad" style="display:inline-block; position:absolute; border:solid black 0px;">
		</div>
		
	</div>
    
    
    <script type="text/javascript">
    
	$(function(){	
        $("#wScratchPad").wScratchPad({
            cursor:'',			
            image:'${pageContext.request.contextPath}/assets/images/<%=giftId%>.png',
            scratchMove: function(e, percent)
            {
            	//console.log("1"+percent);
            },
            scratchDown: function(e, percent){
            	//console.log("2"+percent);
            },
            scratchUp: function(e, percent){
            	//console.log("3"+percent);
				if(percent > 40){
                	this.clear();
                	var type =<%=giftId%>;
                	if(type>=0){
                		$('#inputPad').css('visibility',"visible");
                	}
                }
            }
        });
	});		
    </script>
</body>
</html>