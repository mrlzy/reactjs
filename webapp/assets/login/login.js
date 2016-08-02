
function refreshValiCode(){
	$("#validCodeImg").attr("src", rootPath+'/image?'+Math.random());
}

function showErrMsg(errMsg){
	$('#errorContainer').html(errMsg);
	$('#errorContainer').css("display","block");
}

function loading(){
	$('#loading-mask').css("display","block");
	$('#loading').css("display","block");
}

function hidloading(){
	$('#loading-mask').css("display","none");
	$('#loading').css("display","none");
}

function login(){
	if($("#myform input[name='username']").val()==""){
		showErrMsg('用户帐号不能为空');
		return;
	}
	if($("#myform input[name='password']").val()==""){
		showErrMsg('用户密码不能为空');
		return;
	}
	if($("#myform input[name='validCode']").val()==""){
		showErrMsg('验证码不能为空');
		return;
	}
	
	loading();

	$("#myform").submit();

}


$(
	function () {

		if(!$('#errorContainer').text()==''){
			$('#errorContainer').css("display","block");
		}


	}

    	
); 

