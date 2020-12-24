<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>   
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/animate.css/3.1.1/animate.css">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
$(document).ready( function(){
		$(document).on("click", "#join_btn", function(){
		$('#frm').submit();
	});
		
 		$('#loginbtn').click(function() {
			console.log("로그인 버튼 클릭!");
			if($('#email').val().trim()== "") {
				alert('아이디를 입력해주세요');
				$('#email').val(""); 
                $('#email').focus();
			}else if($('#pwd').val().trim()=="") {
				alert('비밀번호를 입력해주세요');
				$('#pwd').val(""); 
				$('#pwd').focus();   
			}else{
				$("#login").submit();
			}
		});
	
});
	
</script>
<style type="text/css">
	#header {
		background-color: #3399ff;
		box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.3);
	}
	#loginimg {
		height: 20px;
		width: 20px;
	}
	#loginbtn {
		width: 25%;
		background-color: #3399ff;
		box-shadow: 1px 1px 5px #000;
		color: white;
	}
	#cancel {
		width: 25%;
		background-color: #ff1a1a;
		box-shadow: 1px 1px 5px #000;
		color: white;
	}
	
	#loginbox {
		text-align: left;
		margin-top: 15%;
		box-shadow: 1px 1px 5px #000;
		background-color: #e6e6e6;
		width: 50%;
		height: 44%;
	}

	
</style>
</head>
<body>

<nav class="navbar navbar-default" id="header">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="index.htm" style="color:white">WebSocket</a>
    </div>
  </div>
</nav>

<div class="container">
    <div class="row">
        <div class="col-md-12" align="center">
            <div class="well login-box" id="loginbox">
                <form action="login.htm" method="post" id="login">
                    <legend>Login</legend><hr>
                    <div class="form-group">
                        <label for="username-email">Email</label>
                        <input id="email" name="email"  placeholder="Please enter your ID" type="text" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input id="pwd" name="pwd"  placeholder="Please enter your Paasword" type="password" class="form-control" />
                    </div>
                    <div class="form-group text-center">
                    <div class="row">
                    	 <a href="join.htm">회원가입이 필요하신가요?</a>
                    </div>
                    <br>
                    <div>
                    	<input type="button" id="loginbtn" class="btn btn-success btn-login-submit" value="login" style="float:left"/>
                        <input type="reset" id="cancel" class="btn btn-danger btn-cancel-action" value="cancel" style="float:right">
                     </div>   
                    </div>
                </form>
                <form action="join.htm" id="frm" method="get">
            	</form>
            </div>
        </div>
    </div>
</div>

</body>
</html>