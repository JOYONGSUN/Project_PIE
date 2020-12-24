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
 		$('#join_btn').click(function() {
			console.log("회원가입 버튼 클릭!");
			if($('#email').val().trim()== "") {
				alert('아이디를 입력해주세요');
				$('#email').val(""); 
                $('#email').focus();
			}else if($('#pwd').val().trim()=="") {
				alert('비밀번호를 입력해주세요');
				$('#pwd').val(""); 
				$('#pwd').focus();
			}else if($("#pwd").val()!= $("#pwd2").val()) {
				alert("비밀번호가 일치하지 않습니다.");
			}else{
				$("#frm").submit();
				alert("회원가입이 완료되었습니다");
			}
		});
});
</script>
<style type="text/css">
	#header {
		background-color: #3399ff;
		box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.3);
	}
	#join_btn {
		width: 20%;
		background-color: #3399ff;
		box-shadow: 1px 1px 5px #000;
		color: white;
	}
	#cancel {
		width: 20%;
		background-color: #ff1a1a;
		box-shadow: 1px 1px 5px #000;
		color: white;
	}
	#joinbox {
		text-align:left;
		margin-top: 10%;
		box-shadow: 1px 1px 5px #000;
		background-color: #e6e6e6;
		width: 60%;
		height: 45%;
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
            <div class="well join-box" id="joinbox">
                <form action="join.htm" method="post" id="frm">
                    <legend>Join</legend><hr>
                    <div class="form-group">
                        <label for="username-email">UserId</label>
                        <input id="email" name="email"  placeholder="Please enter your ID" type="text" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="pwd" name="pwd"  placeholder="Please enter your Paasword" type="password" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label for="password">Password check</label>
                        <input type="password" id="pwd2" name="pwd2"  placeholder="Please enter your Paasword" type="password" class="form-control" />
                    </div>
                    <br>
                    <br>
                    <div class="form-group text-center">
                        <input type="button" id="join_btn" class="btn btn-success btn-login-submit" value="Join" style="float:left">
                        <input type="reset" id="cancel" value="Cancel" class="btn btn-danger btn-cancel-action" style="float:right">
                        <!-- <input type="submit" class="btn btn-success btn-login-submit" value="join" /> -->

                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>