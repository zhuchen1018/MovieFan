<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@page import="com.myapp.servlet.FBLogin.FBConnection"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
<meta charset="UTF-8">
<%
	FBConnection fbConnection = new FBConnection();
%>
<title>Login</title>
<style>
body {
    background: url('/images/login_bg3.jpg') no-repeat fixed center center;
    background-size: cover;
    font-family: Montserrat;
}

.logo {
	position:absolute;
	left: 10%;
    width: 600px;
    height: 100px;
    background: url("/images/login_logo1.png") no-repeat;
    margin: 30px auto;
}

.login-block {
	position: absolute;
	top: 200px;
	left: 14%;
    width: 320px;
    padding: 20px;
    background: #fff;
    border-radius: 5px;
    border-top: 5px solid #ff656c;
    display: inline-block
}

.login-block h1 {
    text-align: center;
    color: #000;
    font-size: 18px;
    text-transform: uppercase;
    margin-top: 0;
    margin-bottom: 20px;
}

.login-block input {
    width: 100%;
    height: 42px;
    box-sizing: border-box;
    border-radius: 5px;
    border: 1px solid #ccc;
    margin-bottom: 20px;
    font-size: 14px;
    font-family: Montserrat;
    padding: 0 20px 0 50px;
    outline: none;
}

.login-block input#username {
    background: #fff url('http://i.imgur.com/u0XmBmv.png') 20px top no-repeat;
    background-size: 16px 80px;
}

.login-block input#username:focus {
    background: #fff url('http://i.imgur.com/u0XmBmv.png') 20px bottom no-repeat;
    background-size: 16px 80px;
}

.login-block input#password {
    background: #fff url('http://i.imgur.com/Qf83FTt.png') 20px top no-repeat;
    background-size: 16px 80px;
}

.login-block input#password:focus {
    background: #fff url('http://i.imgur.com/Qf83FTt.png') 20px bottom no-repeat;
    background-size: 16px 80px;
}

.login-block input:active, .login-block input:focus {
    border: 1px solid #ff656c;
}

.login-block button {
    width: 100%;
    height: 40px;
    background: #ff656c;
    box-sizing: border-box;
    border-radius: 5px;
    border: 1px solid #e15960;
    color: #fff;
    font-weight: bold;
    text-transform: uppercase;
    font-size: 14px;
    font-family: Montserrat;
    outline: none;
    cursor: pointer;
}

.login-block button:hover {
    background: #ff7b81;
}


.signup-block {
	position: absolute;
	top: 200px;
	left: 60%;
    width: 320px;
    padding: 20px;
    background: #fff;
    border-radius: 5px;
    border-top: 5px solid #ff656c;
    display: inline-block
}

.signup-block h1 {
    text-align: center;
    color: #000;
    font-size: 18px;
    text-transform: uppercase;
    margin-top: 0;
    margin-bottom: 20px;
}

.signup-block input {
    width: 100%;
    height: 42px;
    box-sizing: border-box;
    border-radius: 5px;
    border: 1px solid #ccc;
    margin-bottom: 20px;
    font-size: 14px;
    font-family: Montserrat;
    padding: 0 20px 0 50px;
    outline: none;
}

.signup-block input#username {
    background: #fff url('http://i.imgur.com/u0XmBmv.png') 20px top no-repeat;
    background-size: 16px 80px;
}

.signup-block input#username:focus {
    background: #fff url('http://i.imgur.com/u0XmBmv.png') 20px bottom no-repeat;
    background-size: 16px 80px;
}

.signup-block input#password {
    background: #fff url('http://i.imgur.com/Qf83FTt.png') 20px top no-repeat;
    background-size: 16px 80px;
}

.signup-block input#password:focus {
    background: #fff url('http://i.imgur.com/Qf83FTt.png') 20px bottom no-repeat;
    background-size: 16px 80px;
}

.signup-block input:active, .login-block input:focus {
    border: 1px solid #ff656c;
}

.signup-block button {
    width: 100%;
    height: 40px;
    background: #ff656c;
    box-sizing: border-box;
    border-radius: 5px;
    border: 1px solid #e15960;
    color: #fff;
    font-weight: bold;
    text-transform: uppercase;
    font-size: 14px;
    font-family: Montserrat;
    outline: none;
    cursor: pointer;
}

.signup-block button:hover {
    background: #ff7b81;
}



</style>
</head>

<body>
<form action= "/login" method="post" style="width: 1px; height: 1px;">
<div class="logo"></div>
<div class="login-block">
    <h1>Login</h1>
    <input type="text" value="" placeholder="Username" id="username" name="USERNAME" />
    <input type="password" value="" placeholder="Password" id="password" name="PASSWORD" />
    <button>Log in</button>
</div>
</form>

<div
		style="height: 360px; width: 610px;">
		<a href="<%=fbConnection.getFBAuthUrl()%>"> <img
			style="margin-top: 435px; margin-left:230px;" src="/images/fbLoginButton.png" />
		</a>
</div>

<form action= "/register" method="post" style="width: 1px; height: 1px;">
<div class="signup-block">
    <h1>New here? Sign Up</h1>
    <input type="text" value="" placeholder="Username" id="username" name="USERNAME" />
    <input type="password" value="" placeholder="Password" id="password"  name="PASSWORD"/>
    <button>Sign up</button>
</div>
</form>
</body>

</html>
