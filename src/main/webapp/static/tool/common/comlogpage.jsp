<!DOCTYPE html>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>plat login</title>


<script type="text/javascript" src="jqueryTab/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="jqueryTab/execute.js"></script>
<script language="javascript" type="text/javascript" src="<c:url value='${util}/My97DatePicker/WdatePicker.js'/>"></script>

<style>
* {
	margin:0; padding:0;
}
html{height:100%;}
body {
 position:relative;
 height:100%;
 background:#fff url('') left top repeat-x;
 font-family:Tahoma, Arial, sans-serif;
}

img {
	border:0;
}

#container {
	width:100%;
	margin:0 auto;
	position:fixed; 
}

#container .logo {
	width:230px;
	margin:240px auto 0;
}

#container #box {
 clear:both;
 float:none;
 width:100%;
 margin:0px auto 0;
}

p.main label {
 float:left;
 padding:5px;
 display:inline;
 margin-left:40px;
 font-size:12px;
 color:#000;
 margin-right:10px;
}

#box p {
 clear:both;
 float:none;
 width:100%;
}
.inputclass{
 background:url('../images/input.png') 0 0 repeat-x;
 border:1px solid #d3d3d3;
 color:#555;
 padding:5px;
 float:left;
 width:200px;
}



span {
 font-size:12px;
 color:#666;
}

.space {
 padding-top:15px;
}

span input {
 margin-left:125px;
 margin-right:5px;
 border:1px solid #111;
 background:#444;
 color:#fff;
}
		
#nav-shadow {
	margin: 40px auto 0px auto;
	padding: 0px 0 0 0px;
	width: 60%;
	min-height: 130px;
	text-align: center;
	background:white,
	list-style: none;
}
#nav-shadow li {
	margin-right: 0px;
	width: 182px;
	height: 207px;
	position: relative;
	float: left;
}
#nav-shadow a, #nav-shadow a:visited, #nav-shadow a, #nav-shadow a:hover {
	margin: 0 auto;
	width: 182px;
	height: 207px;
	text-indent: -9999px;
	overflow: hidden;
	background: url(jqueryTab/icons_east.png) no-repeat;
	display: block;
	position: relative;
	z-index: 2;
}
/* Button Colors */
		
#nav-shadow li.button-color-east a {
	/*background-position: -3px -3px;*/
	background: url(jqueryTab/icons_east.png) no-repeat;
}
#nav-shadow li.button-color-2 a {
	/*background-position: -92px -3px;*/
}
#nav-shadow li.button-color-3 a {
	/*background-position: -181px -3px;*/
}
#nav-shadow li.button-color-4 a {
	/*background-position: -270px -3px;*/
}
#nav-shadow li.button-color-5 a {
	/*background-position: -270px -3px;*/
}
#nav-shadow li.button-color-6 a {
	background-position: -270px -3px;
}
#nav-shadow li.button-color-7 a {
	background-position: -270px -3px;
}
#nav-shadow li.button-color-8 a {
	background-position: -270px -3px;
}
#nav-shadow li.button-color-9 a {
	background-position: -270px -3px;
}
#nav-shadow li.button-color-10 a {
	background-position: -270px -3px;
}
/* Button Shadow */
		
	#nav-shadow li img.shadow {
	margin: 0 auto;
	position: absolute;
	bottom: 0;
	left: 0;
	z-index: 1;
}

</style>
</head>
<body style="overflow:hidden;" sroll="no">
	<div id="container">
		<div
			style="height: 50px; overflow-y: hidden; overflow-x: hidden;background-color: #1ea4d2;">
			<div style="float: left; padding-left: 10px; width: 800px;">
				<div style="float: left; width: 180px; color: #ffffff; height: 50px; line-height: 50px;">
					
				</div>
			</div>

		</div>


		<!-- 平台登录-->
		<div id="box" style="font-size: 12px;">

			<div style="background-color: #1ea4d2; height: 200px; overflow-y: hidden; top: 0; left: 0; xoverflow-x: hidden;text-algin:center">
				
			</div>
		    
			<div style="width: 300px; background: #ffffff; border: 1px solid #d3d3d3; margin: 0 auto; margin-top: -70px; padding: 40px;">
				<form method="post" action="loginAction.do?method=login"
					name="form1">
					<div style="width: 400px; margin: 0 auto">

						<table>
							<Tr>
								<td align="right"><label for="user_name"><fmt:message
											key="usermanage.sysadmin.label.username" /></label></td>
								<td><input name="logonname" id="user_name" type="text"
									style="width: 100; height: 25" class="inputclass" /></td>

							</Tr>
							<tr>
								<td align="right"><label for="user_password"><fmt:message
											key="usermanage.sysadmin.label.password" /></label></td>
								<td><input name="password" id="user_password"
									type="password" style="width: 100; height: 25"
									class="inputclass" /></td>
							</tr>
							<tr>
								<td align="right"><label for="user_password"><fmt:message
											key="logpage.date" /></label></td>
								<Td><input id="logindate" name="logindate" type="text"
									style="width: 100; height: 25" readonly="true"
									onClick="WdatePicker()" class="inputclass" /></td>
							</tr>
							<tr>
								<td></td>
								<td><input type="button" onclick="getlogin();"
									value="<fmt:message key='denglu'/>"
									style="height: 30px; width: 120px"></td>
							</tr>
							<tr>
								<td></td>
								<td><input type="checkbox"> Remember name</td>
							</tr>
						</table>
					</div>
				</form>
			</div>
		</div>
		<!-- 平台系统权限选择 -->
		<div id="b22" style="height: 400px; visibility: hidden; display: none">
			
			<div style="font-size: 12px; width: 60%; background: #ffffff url('../../framework/images/bubble.png') no-repeat 10px center; padding: 10px; padding-left: 40px; margin: 5px auto; border: 1px dashed #CAE1FF;">
				Welcome! admin</div>
			<ul id="nav-shadow" style="list-style: none">
				<li class="button-color-east"><a href="#" onclick="login();"
					title="East">East</a></li>
				<li class="button-color-3"><a href="#" title="My fancy link">Link
						Text</a></li>
				<li class="button-color-4"><a href="#" title="My fancy link">Link
						Text</a></li>
				<li class="button-color-4"><a href="#" title="My fancy link">Link
						Text</a></li>
				<li class="button-color-4"><a href="#" title="My fancy link">Link
						Text</a></li>
				<li class="button-color-4"><a href="#" title="My fancy link">Link
						Text</a></li>
			</ul>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
function getlogin(){
	if($("#user_name").val()==""){
		alert('<fmt:message key="logpage.xname"/>');
		return false;
	}
	var param={"logname":$("#user_name").val(),"logpasd":$("#user_password").val(),"logdate":$("#logindate").val()};
	$.post("<c:out value='${contextPath}'/>/loginAction.do?method=getlogin",param,function(data){
		var datasta = eval(data)[0].status;
		if(datasta==1){
			//alert("test");
			$("#box").slideUp();
			$("#b22").slideDown();
			$("#b22").attr({"style":"visibility:visible"});
		}else if(datasta==2){
			 alert("<fmt:message key="user.userpassword"/>");
			 document.forms[0].logonname.focus();
			 return false;
		}
	});
}

function login(systemurl){
	document.form1.action="<c:out value='${contextPath}'/>/loginAction.do?method=login";
	document.form1.submit();
}

</script>