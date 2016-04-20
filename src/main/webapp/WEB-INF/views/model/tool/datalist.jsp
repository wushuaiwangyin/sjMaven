<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${eaUIPath}/themes/default/easyui.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${eaUIPath}/themes/icon.css'/>" />
<%-- <script type="text/javascript" src="<c:url value='${eaUIPath}/searchTree.js'/>"></script>  --%>
<link rel="stylesheet" type="text/css" href="../styles/table.css" />
	
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<title></title>
<script type="text/javascript">
function testaaa(msg){
	alert(msg);
}

	function loaddata(){
		$('#datalist').datagrid({
            iconCls:'icon-save', 
            nowrap: true, 
            autoRowHeight: false, 
            striped: true, 
            url: 'showtable.action?tablename=T_LOG_DS&pagenum=1&pagesize=50',
            collapsible:true, 
            remoteSort: false,
            columns: columns,
            pagination: true,  //分页控件
            rownumbers: true,  //行号
            pageSize: 50
        }); 
	}
	
	function test(){
		alert('aaaa');
	}
</script>
</head>
<body>
<table id="datalist">
</table>
</body>
</html>