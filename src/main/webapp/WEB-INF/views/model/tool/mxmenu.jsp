<%@ page import="java.util.*,com.krm.pd.dsp.util.HttpUtil,net.sf.json.JSONArray,net.sf.json.JSONObject" %>
<%
String realPath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
String rurl_mx = realPath + "/dsp_search.shtml?method=getdata&key=0124&rstype=json";
String jo_mx = HttpUtil.get(rurl_mx);
JSONArray jc_mx_3 = JSONArray.fromObject(jo_mx);
//response.getWriter().write(jc.toString());
for(int i=0;i<jc_mx_3.size();i++)
{
JSONObject jt_mx = jc_mx_3.getJSONObject(i);
//response.getWriter().write("<li class='submenu' onclick='addPanel('"+jt.get("dic_name")+"','dsp_sysDicAction.shtml?method=newQueryForm&from=frame&htop=none')''><a target='mainFrame''>"+jt.get("dic_name")+"</a></li>");
%>
<li class="submenu" onclick="addPanel('<%=jt_mx.get("mx_name")%>','<%=jt_mx.get("mx_href")%>')"><a target="mainFrame"><%=jt_mx.get("mx_name")%></a></li>
<%
}
%>

