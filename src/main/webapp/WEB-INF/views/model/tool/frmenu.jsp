<!DOCTYPE html>
<html>
<%@ page pageEncoding="UTF-8"%>
<head>
<title></title>

	<link rel="stylesheet" href="<c:url value='/ps/uitl/jmenu/css/styles.css'/>" type="text/css" />
	<link rel="stylesheet" href="<c:url value='/ps/uitl/jmenu/css/jquery-tool.css'/>" type="text/css" />
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${eaUIPath}/themes/bootstrap/easyui.css'/>" />
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${eaUIPath}/themes/icon.css'/>" /> 
	<script type="text/javascript" src="<c:url value='${eaUIPath}/jquery.min.js'/>"></script> 
	<script type="text/javascript" src="<c:url value='/ps/uitl/jmenu/js/jquery.tools.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ps/uitl/jmenu/js/main.js'/>"></script>
	
	

<script type="text/javascript">
/* window.parent.document.getElementById("ppppp").value= "<fmt:message key="navigation.dataFill.dataFilledit" />"; */

function addPanel(tname,url){
	window.parent.window.addPaneltab(tname,url);
}
</script>
<style>html body{overflow:auto;scrolling:auto;font-family:"Microsoft YaHei",å¾®è½¯éé»,"Microsoft JhengHei",åæç»é»,STHeiti,MingLiu}
* {
font-family: "Microsoft YaHei" ! important;
}

</style>

</head>
<body>
	
	<div id="menucontent" class="menucontent">
	
		<ul id="expmenu-freebie">
			<li>
				<ul class="expmenu" style="width:180px">
    			
					<li>
						<div class="jmenuheader">
						<span class="arrow up"></span>
						<span class="label" style="background-image: url(<c:url value='/ps/uitl/jmenu/images/messages.png'/>);">探索分析</span> 
							 
						<ul class="jmenumenu">
						
						<li class="submenu" onclick="addPanel('新建模型','exploreAnalyse.action')"><a target="mainFrame">新建模型</a></li>
						
						</ul>	
						</div>
					</li>
					
							
				</ul>
			</li>
		</ul>
	</div>
</body>
</html>
