<%@ page language="java" pageEncoding="UTF-8"%>
<style>
<!--
.dlgfontsize{font-size:14px;color: red;}
-->
</style>
<div style="height:30px;">
<form target=datalist name="queryform" method="post"
	action="../dsp_search.shtml?method=getdata&withinfo=true&key=0122">
	<input type="hidden" name="tablename" id="tablename"> <input
		type="text" name="tablename_zh" id="tablename_zh"> <select
		name="tablecol" id="tablecol">

	</select> <select name="operator" id="operator">
		<option value="=">=</option>
		<option value=">">></option>
		<option value=">=">>=</option>
		<option value="<"><</option>
		<option value="<="><=</option>
		<option value="!=">!=</option>
		<option value=" like ">like</option>
		<option value=" not like ">not like</option>
		<option value=" in ">in</option>
		<option value=" not in ">not in</option>

	</select> <input type="text" value="1" id="queryvalue" taile="请输入查询条件">
	<input type="hidden" id="value1" />
	<input type="hidden" id="value2" />
	<input type="hidden" name="where" id="where"> <input
		type="button" onclick="query()" tile="查询" value="快速查询"> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-search'" style="width: 100px; height: 22px;" onclick="$('#dlgquery').dialog('open')"tile="query by query">自定义查询</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" style="width: 100px; height: 22px;"onclick="$('#dlg').dialog('open')">提取数据到表</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" style="width: 100px; height: 22px;"onclick="$('#getdata').dialog('open')">直接提取</a>
		<!--  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" style="width: 100px; height: 22px;"onclick="$('#dlgsavers').dialog('open')">保存该提取</a>-->
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" style="width: 100px; height: 22px;" id="buttonok">确定</a>
</form>
</div>
<div id="dlg" class="easyui-window" title="提取数据到表" closed=true
	data-options="modal:true,iconCls:'icon-save'"
	style="width: 480px; height: 250px; padding: 2px;">
	<form target=returnmesg name="saveDataByQueryform"
		action="../dsp_search.shtml?method=createTableByQuery&key=0123&rstype=table&forward=ajaxBlankCreate"
		method=post>
		<table border=0 style="height: 100%; margin: 0px">
			<tr style="height: 170px">
				<Td><textarea rows="6" cols="62" name="querysql"
						id="createquerysql" readonly=true></textarea> <br> 输入表名<input
					type="text" value="TEMP_" style="width: 50px" readonly="true">
					<input type="text" name="tablename" id="temptablename">
					<!-- <input type="text" title="表中文名称" class="temp_tablename_zh" name="tablename_zh" id="tablename_zh"> -->
					<input type="text"  name="tablename_zh" id="tablename_zh_1">
					<input type="hidden" name="table_id" class="table_id">
					<input type="hidden" name="super_id" class="super_id">
					<input type="button" onclick="createTableByQuery();" value="创建表"><input
					type="button" value="设置"> <input type="button"
					onclick="saveDataByQuery();" value="开始提取">
					<!-- <input type="button" style="width:80px" onclick= "$('#dlg').window('close');" value="关闭"> -->
					<input type="button" style="width:80px" onclick= "gettablename();" value="关闭">
					<br></Td>
			</tr>
			<Tr>
				<td><iframe style="height: 40px" width=100% frameborder="no"
						border="0" marginwidth="0" marginheight="0" id="returnmesg"
						name="returnmesg"></iframe></td>
			</Tr>
		</table>


	</form>
</div>

<div id="getdata" class="easyui-window" title="直接提取" closed=true
	data-options="modal:true,iconCls:'icon-save'"
	style="width: 655px; height: 365px; padding: 5px;">
	<div style="width:545px;height:300px;float: left;border: 0px solid red; ">
      <div style="width:525px;height:80px;float: left;padding: 5px;">
       <table>
          <tr>
          	<td>要提取的记录：</td>
          	<td style="width:25px;"><input type="radio" id="all"  name="scope" checked="checked" value="all" /></td>
          	<td>全部(A)</td>
          	<td>&nbsp;&nbsp;&nbsp;</td>
          	<td>首记录号码(S)</td>
          	<td><input type="text" size="10" id="firstNum" name="firstNum" disabled="disabled" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');"/></td>
          </tr>
          <tr>
          	<td>&nbsp;</td>
          	<td style="width:25px;"><input type="radio" id="scope" name="scope" value="scope"/></td>
          	<td>范围(R)</td>
          	<td>&nbsp;&nbsp;&nbsp;</td>
          	<td>末记录号码(E)</td>
          	<td><input type="text" size="10" id="endNum" name="endNum" disabled="disabled" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');"/></td>
          </tr>
          <tr>
          	<td>数据库顺序(O):</td>
          	<td colspan="5">
          		<select style="width:430px;">
          			<option>无索引</option>
          			<option>交易金额/升</option>
          			<option>交易日期+交易金额/升</option>
          		</select>
          	</td>
          </tr>
       </table>
      </div>
      
      <div style="width:525px;float: left; padding: 5px">
      	   <table style="width: inherit; background-color: gray;" border="0" cellpadding="0" cellspacing="1">
      	   		<tr style="height:20px;background-color: #fff;">
      	   			<td>序号</td>
      	   			<td>文件名</td>
      	   			<td>&nbsp;</td>
      	   			<td>标准</td>
      	   		</tr>
      	   		<tr style="height:20px;background-color: #FFFF99;">
      	   			<td style="width:30px;height: 30px;">1</td>
      	   			<td style="width:270px;height: 30px;">数据提取1</td>
      	   			<td style="width:30px;height: 30px;">
      	   				<img alt="" src="../images/condition.png" width="30" height="30" onmouseover="this.style.cursor='hand'" onclick="$('#dlgquery').dialog('open')"/> 
      	   			</td>
      	   			<td><input type="text" id="condition" name="condition" style="height: 28px;width: 200px;" value=""/> </td>
      	   		</tr>
      	   		<tr style="height:20px;background-color: #fff;">
      	   			<td>2</td>
      	   			<td>&nbsp;</td>
      	   			<td style="width:30px;height: 30px;">&nbsp;</td>
      	   			<td>&nbsp;</td>
      	   		</tr>
      	   		<tr style="height:20px;background-color: #fff;">
      	   			<td>3</td>
      	   			<td>&nbsp;</td>
      	   			<td style="width:30px;height: 30px;">&nbsp;</td>
      	   			<td>&nbsp;</td>
      	   		</tr>
      	   		<tr style="height:20px;background-color: #fff;">
      	   			<td>4</td>
      	   			<td>&nbsp;</td>
      	   			<td style="width:30px;height: 30px;">&nbsp;</td>
      	   			<td>&nbsp;</td>
      	   		</tr>
      	   		<tr style="height:20px;background-color: #fff;">
      	   			<td>5</td>
      	   			<td>&nbsp;</td>
      	   			<td style="width:30px;height: 30px;">&nbsp;</td>
      	   			<td>&nbsp;</td>
      	   		</tr>
      	   </table>
      </div>
    </div>
    <div style="width:80px;height:300px;float: left;margin-left: 1px">
      <table>
      	<tr>
      		<td>
      			<input type="button" name="submitGetData" onclick="submitGetData()" value=" 确 定 "/>
      		</td>
        </tr>
        <tr>
      		<td>
      			<input type="hidden" name="selectColumn" value=""/>
      			<input type="button" name="getColumn1" onclick="$('#dlgselectcolumn').dialog('open')" value=" 字 段 "/>
      		</td>
        </tr>
      </table>
    </div>
</div>


<div id="dlgquery" class="easyui-window" title="sql语句查询(输入sql语句)"
	closed=true data-options="modal:true,border:false,iconCls:'icon-save'"
	style="width: 600px; height: 540px; padding: 1px;">
	
	<div class="easyui-layout" data-options="fit:true,border:false" style="border:0px">
	<form target="datalist" style="padding: 0px; margin: 0px" name="querysqlform" action="../dsp_search.shtml?method=getdata&key=0123&withinfo=true" method="post">
	
	  <div data-options="region:'north'" split="false" style="height:30px; padding: 0px">
			<input type="button" onclick="$('#querysql').val('');" value="clear"> 
			<input type="button" onclick="createsql('\'\'')" value="''"> 
			<input type="button" onclick="createsql('=')" value="=">
			<input type="button" onclick="createsql('>')" value=">">
			<input type="button" onclick="createsql('>=')" value=">=">
			<input type="button" onclick="createsql('<')" value="<">
			<input type="button" onclick="createsql('<=')" value="<=">
			<input type="button" onclick="createsql('!=')" value="!="> 
			<input type="button" onclick="createsql('AND')" value="AND"> 
			<input type="button" onclick="createsql('OR')" value="OR"> 
			<input type="button" onclick="createsql('IN()')" value="IN"> 
			<input type="button" onclick="createsql('like')" value="like"> 
			<select><option>NOT like</option><option>NOT in</option></select>
		</div>
		<div data-options="region:'west'" split="false" style="width: 130px; padding: 0px" title="数据字段">
				<select id="tablecollist" size=14
					style="height: 100%;width:128px">
	
				</select>
		</div>		
		<div data-options="region:'center'" split="false" style="padding: 0px" title="查询设计">
			<textarea  style="margin:0px;height: 98%;width:98%"  name="querysql" id="querysql"></textarea>
		</div>
		<div data-options="region:'east'" split="false" style="padding: 0px;width:130px" title="系统函数" >
				<ul id="querylib" class="easyui-tree" >
	
				</ul>
		</div>
		<div data-options="region:'south'" style="height:130px" split="false" style="padding: 0px">
			
					<div style="max-height: 100px; overflow: auto">
						<!--  -->
						<table id="querycolname" border=1 class='clcnapitable'
							style="width: 100%">
							<tr id='clcnapihr'> 
								<th>字段</th>
								<th>物理字段</th> 
								<th>字段类型</th>
								<th>字段长度</th>
							</tr>
						</table>
					</div>
					
					<input type="button" style="width:80px" value="确定" onclick="setcondition()"> 
					<input type="submit" style="width:80px" value="查询"> 
					<input type="reset" style="width:80px"value="重置">
					<input type="reset" style="width:80px" onclick= "$('#dlgquery').window('close');" value="关闭">
					<a href="../index.html" target="dlgquerydes"
					style="width: 100px; height: 20px; color: blue"
					onclick="$('#dlgquerydes').dialog('open')">自定义sql设计</a>
				
			</div>
			</form>
	</div>
		
</div>

<div id="dlgselectcolumn" class="easyui-window" title="字段"
	closed=true data-options="modal:true,border:false,iconCls:'icon-save'"
	style="width: 450px; height: 250px; padding: 10px;">
	<div style="width: 340px; height: 190px; padding: 1px; float: left;">
	要包括的字段为：
	<br/>
	<select name="tableselectcolumn" id="tableselectcolumn" multiple="multiple" style="width:330px;height: 170px;">
	</select>
	</div>
	<div style="width: 60px; height: 190px; padding: 1px; float: left;">
		<input type="button" name="" onclick="$('#dlgselectcolumn').dialog('close')" value=" 确 定 "/>
	</div>
</div>


<div id="dlgquery1" class="easyui-window" title="sql语句查询(输入sql语句)"
	closed=true data-options="modal:true,border:false,iconCls:'icon-save'"
	style="width: 600px; height: 540px; padding: 1px;">
	
	<div class="easyui-layout" data-options="fit:true,border:false" style="border:0px">
	<form target="datalist" style="padding: 0px; margin: 0px" name="querysqlform" action="../dsp_search.shtml?method=getdata&key=0123&withinfo=true" method="post">
	
	  <div data-options="region:'north'" split="false" style="height:30px; padding: 0px">
			<input type="button" onclick="$('#querysql').val('');" value="clear"> 
			<input type="button" onclick="createsql('\'\'')" value="''"> 
			<input type="button" onclick="createsql('=')" value="=">
			<input type="button" onclick="createsql('>')" value=">">
			<input type="button" onclick="createsql('>=')" value=">=">
			<input type="button" onclick="createsql('<')" value="<">
			<input type="button" onclick="createsql('<=')" value="<=">
			<input type="button" onclick="createsql('!=')" value="!="> 
			<input type="button" onclick="createsql('AND')" value="AND"> 
			<input type="button" onclick="createsql('OR')" value="OR"> 
			<input type="button" onclick="createsql('IN()')" value="IN"> 
			<input type="button" onclick="createsql('like')" value="like"> 
			<select><option>NOT like</option><option>NOT in</option></select>
		</div>
		<div data-options="region:'west'" split="false" style="width: 130px; padding: 0px" title="数据字段">
				<select id="tablecollist" size=14
					style="height: 100%;width:128px">
	
				</select>
		</div>		
		<div data-options="region:'center'" split="false" style="padding: 0px" title="查询设计">
			<textarea  style="margin:0px;height: 98%;width:98%"  name="querysql" id="querysql"></textarea>
		</div>
		<div data-options="region:'east'" split="false" style="padding: 0px;width:130px" title="系统函数" >
				<ul id="querylib" class="easyui-tree">
	
				</ul>
		</div>
		<div data-options="region:'south'" style="height:130px" split="false" style="padding: 0px">
			
					<div style="max-height: 100px; overflow: auto">
						<!--  -->
						<table id="querycolname" border=1 class='clcnapitable'
							style="width: 100%">
							<tr id='clcnapihr'> 
								<th>字段</th>
								<th>物理字段</th> 
								<th>字段类型</th>
								<th>字段长度</th>
							</tr>
						</table>
					</div>
					
					<input type="submit" style="width:80px" value="查询"> 
					<input type="reset" style="width:80px"value="重置">
					<input type="reset" style="width:80px" onclick= "$('#dlgquery').window('close');" value="关闭">
					<a href="../index.html" target="dlgquerydes"
					style="width: 100px; height: 20px; color: blue"
					onclick="$('#dlgquerydes').dialog('open')">自定义sql设计</a>
				
			</div>
			</form>
	</div>
		
</div>


<div id="dlgquerydis" class="easyui-window" title="sql语句查询(输入sql语句)"
	closed=true data-options="modal:true,border:false,iconCls:'icon-save'"
	style="width: 600px; height: 440px; padding: 1px;">
	
	<div class="easyui-layout" data-options="fit:true,border:false" style="border:0px">
	<form target="datalist" style="padding: 0px; margin: 0px" name="querydisform" action="../dsp_search.shtml?method=getdata&key=0123&withinfo=true" method="post">
	
	  <div data-options="region:'north'" split="false" style="height:30px; padding: 0px">
	  	    <input type="hidden" class="tablename" name="tablename" id="tablenamedis">
			<input type="radio" name="distype" value="0"><a title="剔除重复记录，提取剩余记录">去除重复</a>
			<input type="radio" name="distype" value="1"><a title="提取完全重复的数据记录">提取重复</a>
			(<select style="border:0px;width:35px" title="重复记录数" type="text" name="groupcount">
			 <option value="1">1</option>
			 <option value="2">2</option>
			 <option value="3">3</option>
			 <option value="4">4</option>
			 </select>)
			<input type="radio" name="distype" checked value="2"><a title="保留重复记录中的一条，加上非重复记录，生成新记录">保留重复</a>
			
		</div>
		<div data-options="region:'west'" split="false" style="width: 130px; padding: 0px" title="数据字段">
				<select id="tablecollistdis" size=14 class="columnselect" style="height: 100%;width:128px">
	
				</select>
		</div>		
		<div data-options="region:'center'" split="false" style="padding: 0px" title="查询字段">

						<table id="querycolnamedisselect" border=1 class='clcnapitable'
							style="width: 100%">
							<tr> 
								<th>字段</th>
								<th>物理字段</th>
								<th>字段类型</th>
								<th>字段长度</th>
							</tr>
						</table>
					
		</div>
		<div data-options="region:'south'" style="height:140px" split="false" style="padding: 0px">
			<input type="button" onclick="postDisForm()" value="开始去重"> <input type="reset" value="重置">
		<textarea rows="4" style="width: 485px" name="querysql"
						id="guerysqldissub"></textarea> 
				
			</div>
		
		</form>
	</div>
		
</div>

<div id="dlgqueryorder" class="easyui-window" title="sql语句查询(输入sql语句)"
	closed=true data-options="modal:true,border:false,iconCls:'icon-save'"
	style="width: 600px; height: 440px; padding: 1px;">
	
	<div class="easyui-layout" data-options="fit:true,border:false" style="border:0px">
	<form target="datalist" style="padding: 0px; margin: 0px" name="queryorderform" action="getparamsorder.jsp" method="post">
	
		<div data-options="region:'west'" split="false" style="width: 130px; padding: 0px" title="提取字段">
				<select id="tablecollistorder" size=14 class="columnselect"
					style="height: 100%;width:128px">
	
				</select>
				<input type="hidden" name="tablename" class="tablename">
		</div>		
		<div data-options="region:'center'" split="false" style="padding: 0px" title="查询字段">

						<table id="querycolnameorderselect" border=1 class='clcnapitable'
							style="width: 100%">
							<tr> 
								<th>字段</th>
								<th>物理字段</th>
								<th>字段类型</th>
								<th>字段长度</th>
							</tr>
						</table>
					
		</div>
		<div data-options="region:'east'" split="false" style="padding: 0px;width:130px" title="系统函数" >
				<ul id="querylibdis" class="easyui-tree querylib">
	
				</ul>
		</div>
		<div data-options="region:'south'" split="false" style="padding: 0px;width:40px">
		 <input type="submit" onclick = "" value="开始排序">
		</div>
		</form>	
	</div>
		
</div>

<div id="dlggroup" class="easyui-window" title="数据汇总" closed=true
	data-options="modal:true,iconCls:'icon-save'"
	style="width: 620px; height: 480px; padding: 0px;">
	<form target="datalist" style="padding: 0px; margin: 0px" name="querygroupform" action="getparams.jsp" method="post">
		<table style="width: 100%; margin: 0px" border=0>
			<Tr>
				<td valgin=top style="width: 150px"><select
					id="tablecollistgroup" size=14 style="height: 300px; width: 150px"
					border=1 class='clcnapitable'>

				</select></td>


				<td valign="top">
					<div class='clcnapitable' style="height: 24px; background: #fff; padding: 0px" border=1>

						<input type="hidden" name="tablename" id="tablenamegroup">

						<select id="groupfn" style="width: 150px">
							<option value="">强选择汇总方式</option>
							<option value="COUNT">COUNT</option>
							<option value="SUM">SUM</option>
							<option value="MAX">MAX</option>
							<option value="MIN">MIN</option>
						</select>
						 <input type="button" value="添加汇总字段" id="addgroup">
						 <input type="button" value="清除" id="cleargroup">
					</div>
					<div style="height: 140px; width: 100%; overflow: auto">
						
						<table id="querycolnamegroupfled" border=0 class='clcnapitable' style="width: 100%">
							<tr>
								<Td>汇总计算</td>
								<Td>字段</Td>
								<Td>结果别名</Td>
							</tr>
						</table>
					</div>
					<div border=1 class='clcnapitable' style="max-height: 30px; background: #fff">
						分组条件<input type="button" value="添加分组条件" id="addgroupby">
						 	 <input type="button" value="清除" id="cleargroupby">
					</div>
					<div style="max-height: 120px; width: 100%; overflow: auto">
						<table id="querycolnamegroup" border=1 class='clcnapitable'
							style="width: 100%">
							<tr id='clcnapihr'>
								<th>字段</th>
								<th>物理字段</th>
								<th>字段类型</th>
								<th>字段长度</th>
								<th>取消</th>
							</tr>
						</table>
					</div>


				</Td>

			</tr>
			<tr>
				<td colspan=2 style="background-color: #EAEAEA"><textarea
						rows="4" style="width: 485px" name="querysql"
						id="guerysqlgroupsub"></textarea> <!--  <input type="hidden" name="guerysql" id="guerysqlgroupsub">-->

				</td>
			</tr>
			<tr>
				<td colspan=2 style="background-color: #EAEAEA"><input
					type="button" onclick="postGroupForm()" value="开始汇总"> <input
					type="reset" value="重置"></td>

			</tr>


		</table>
	</form>
</div>

<div id="dlglayer" class="easyui-window" title="数据分层" closed=true
	data-options="modal:true,iconCls:'icon-save'"
	style="width: 580px; height: 480px; padding: 0px;">
	<form target="datalist" style="padding: 0px; margin: 0px" name="querylayerform" action="getparamslayercount.jsp" method="post">
		<table style="width: 100%; margin: 0px" border=0>
			<Tr>
				<td valgin=top style="width: 150px"><select
					id="tablecollistlayer" class="columnselect" size=15
					style="height: 280px; width: 150px" border=1 class='clcnapitable'>

				</select>
				<input type="hidden" name="tablename" class="tablename">
				
				</td>


				<td valign="top">
					
					<div style="height: 250px; width: 100%; overflow: auto">
						
						<table id="querycolnamelayerfled" border=0 class='pagetable'
							style="width: 100%">
							<tr>
								<Th>字段</th>
								<Th>分层设置</Th>
								<Th>操作</Th>
							</tr>
						</table>

					</div>
					<div style="padding:3px">
						
						<a href="#" class="easyui-linkbutton" id="addlayer" data-options="iconCls:'icon-add'">添加分层字段及条件</a>
					</div>

				</Td>

			</tr>
			<tr>
				<td colspan=2 style="background-color: #EAEAEA">
					<div style="max-height: 120px; width: 100%; overflow: auto">
						
						<table id="querycolnamelayer" border=1 class='dsptable'
							style="width: 100%">
							<tr id='clcnapihr'>
								<th>字段</th>
								<th>物理字段</th>
								<th>字段类型</th>
								<th>字段长度</th>
								<th>取消</th>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan=2 style="background-color: #EAEAEA"><input
					type="submit" onclick="" value="开始分层" style="width:80px;height:28px"> <input
					type="reset" value="重置" style="width:80px;height:28px"></td>

			</tr>


		</table>
	</form>
</div>

<div id="dlgquerydes" class="easyui-window" title="sql语句查询(输入sql语句)"
	closed=true data-options="modal:true,iconCls:'icon-save'"
	style="width: 800px; height: 600px; padding: 0px; top: 60px; left: 100px">
	<iframe height=100% style="margin: 0px" width=100% name="dlgquerydes"></iframe>
</div>
<div id="dlgsavers" class="easyui-window" title="保存该提取" closed=true
	data-options="modal:true,iconCls:'icon-save',inline:true"
	style="width: 360px; height: 250px; padding: 0px; top: 60px; left: 500px; margin: 0 auto">
	<table>
		<tr>
			<td width=40>分组:</td>
			<td><select>
					<option>请选择保存组</option>
			</select> <a href="">创建新组</a></td>
		</tr>
		<tr>
			<td>编码:</td>
			<td><input type="text" title="前缀标识" value="DG_" readonly="true"
				style="width: 30px" /> <input type="text" title="序号" value="2"
				style="width: 30px" /> <input type="text" style="width: 220px" /></td>
		</tr>
		<tr>
			<td>标题:</td>
			<td><input type="text" style="width: 300px" /></td>
		</tr>
		<tr>
			<td>描述:</td>
			<td><textarea rows=4 style="width: 300px"></textarea></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" value="保存"><input type="reset"
				value="重置"></td>
		</tr>
	</table>

</div>

<div id="framedlg" class="easyui-window" title="系统窗口" closed=true
	data-options="modal:true,iconCls:'icon-save'"
	style="width: 680px; height: 580px; padding: 0px;">
</div>




	<script>


var tridseq=0;

var sqlcount=0;


function gettablename(){
	var temptablename = $("#temptablename").val();
	var tablename_zh = $("#tablename_zh_1").val();
	$('#value1').val(temptablename);
	$('#value2').val(tablename_zh);
	$('#dlg').window('close');
}

function setvaluebyid(inputid,values)
{
	$("#"+inputid).val(values);
}


function setvalue(tablename)
{
//alert("set");



$("#tablename").val(tablename);	
$(".tablename").val(tablename);			
$("#tablenamegroup").val(tablename);	
$("#temptablename").val(tablename);	
$("#querysql").val("select * from "+tablename);
var url="<c:out value='${hostPrefix}'/>/dpgapimanage/dsp_search.shtml?method=getdata&key=0192&rstype=json&table="+$("#tablename").val()+"&ram="+Math.round(Math.random()*10000);
var url1="<c:out value='${hostPrefix}'/>/dpgapimanage/dsp_search.shtml?method=getdata&key=01841&tablename=T_LOG_DS";
//alert(url);
$.getJSON(url,function (data) {

    //对请求返回的JSON格式进行分解加载
    var tablename_zh;
    var table_id;
    var super_id;
    
    clear();
  
    $(data).each(function () {
    	//alert(this.COLUMN_NAME);
        $("#tablecol").append($("<option/>").text(this.column_name_zh).attr("value",this.column_name));
        //$("#tableselectcolumn").append($("<option/>").text(this.column_name_zh).attr("value",this.column_name));
        $("#tablecollist").append($("<option/>").text(this.column_name_zh).attr("value",this.column_name));
        $("#tablecollistgroup").append($("<option/>").text(this.column_name_zh).attr("value",this.column_name));
        $(".columnselect").append($("<option/>").text(this.column_name_zh).attr("value",this.column_name));
       // var tr = "<tr><td>"+this.column_name_zh+"</td><td>"+this.column_name+"</td><td>"+this.data_type+"</td><td>"+this.data_length+"</td></tr>"
       // $("#querycolname").append(tr);
        tablename_zh = this.table_name_zh;
        table_id = this.table_id;
        super_id = this.super_id;

		//默认选中所有字段
		//$('#tableselectcolumn option').attr('selected','selected');
    });
    $("#tablename_zh").val(tablename_zh);
    $(".temp_tablename_zh").val(tablename_zh);
    $(".table_id").val(table_id);
    $(".super_id").val(table_id);

});

$.getJSON(url1,function (data) {
	$(data).each(function () {
		$("#tableselectcolumn").append($("<option/>").text(this.field_alias).attr("value",this.id));
	});
	$('#tableselectcolumn option').attr('selected','selected');
});
}





function clear()
{

	$("#tablecol").empty();
    $("#tablecollist").empty();
    $(".columnselect").empty();
    $("#tablecollistgroup").empty();
}


function setcolvalue(tablename,colname)
{

var url="../dsp_search.shtml?method=getdata&key=0192b&rstype=json&column_name="+colname+"&table="+$("#tablename").val()+"&ram="+Math.round(Math.random()*10000);
//alert(url);
$.getJSON(url,function (data) {

    //对请求返回的JSON格式进行分解加载
      
    $(data).each(function () {
    	//alert(this.COLUMN_NAME);
    	var tr = "<tr><td>"+this.column_name_zh+"</td><td>"+this.column_name+"</td><td>"+this.data_type+"</td><td>"+this.data_length+"</td></tr>"
        $("#querycolname").append(tr);
       
    });
   

});
}

function setcolvalueGroup(tablename,colname)
{

var url="../dsp_search.shtml?method=getdata&key=0192b&rstype=json&column_name="+colname+"&table="+$("#tablename").val()+"&ram="+Math.round(Math.random()*10000);

//alert(url);
$.getJSON(url,function (data) {

    //对请求返回的JSON格式进行分解加载
      
    $(data).each(function () {
    	//alert(this.COLUMN_NAME);
    	tridseq++;
    	var _len =$("#querycolnamegroup tr").length; 
    	_len=tridseq;
    	
    	var tr = "<tr id="+_len+"><td><input type='hidden' value='"+this.column_name+"' name='group_"+_len+"'>"+this.column_name_zh+"</td><td>"+this.column_name+"</td><td>"+this.data_type+"</td><td>"+this.data_length+"</td><td><img onclick=\'deltr(\"querycolnamegroup\","+_len+")\' height=14 src='../ps/framework/images/del.gif'></td></tr>";
        $("#querycolnamegroup").append(tr);
        
       
    });
   

});
}



function closewin(dlgid)
{
	alert(2);
	$("#"+dlgid).dialog('close');	
}

function setsqlvalue(sql)
{
//alert(sql);

	$("#querysql").val(sql);
	$("#createquerysql").val(sql);
	$("#querysqlgroup").val(sql);
	$('#dlgquerydes').dialog('close');
	sqlcount++;
	var tr = "<tr title='编辑 撤销'><td nowrap><input type='checkbox' id='sqlstep_"+sqlcount+"'/>"+sqlcount+"</td><td>"+sql+"<div>执行&nbsp;&nbsp;编辑&nbsp;&nbsp;撤销</div></td></tr>";
	$("#hissql").append(tr);
	
}

function submitquerysqlform()
{
	
	document.querysqlform.action="../dsp_search.shtml?method=getdata&key=0123&withinfo=true";
	document.querysqlform.submit();
}

function sethisvalue(sql)
{
	sqlcount++;
	var tr = "<tr title='编辑 撤销' id='sqlstep_"+sqlcount+"'><td nowrap valign=top><input type='checkbox' />"+sqlcount+"</td><td>"+sql+"<div>执行&nbsp;&nbsp;编辑&nbsp;&nbsp;<a onclick=\'deltr(\"hissql\",\"sqlstep_"+sqlcount+"\")\'>撤销</a></div></td></tr>";
	$("#hissql").append(tr);
}
	
function setsqlvaluegroup(sql)
{
	//alert(sql);
	
	$("#guerysqlgroupsub").val(sql);
	//sqlcount++;
	var tr = "<tr title='编辑 撤销'><td nowrap><input type='checkbox' id='sqlstep_"+sqlcount+"'/>"+sqlcount+"</td><td>"+sql+"<div>执行&nbsp;&nbsp;编辑&nbsp;&nbsp;撤销</div></td></tr>";
	$("#hissql").append(tr);
	document.querygroupform.action="../dsp_search.shtml?method=getdata&key=0123&withinfo=true";
	//alert("submit");
	//document.querygroupform.submit();
}


function setsqlvaluedis(sql)
{
	//alert(sql);
	
	$("#guerysqldissub").val(sql);
	//sqlcount++;
	var tr = "<tr title='数据去重'><td nowrap><input type='checkbox' id='sqlstep_"+sqlcount+"'/>"+sqlcount+"</td><td>"+sql+"<div>执行&nbsp;&nbsp;编辑&nbsp;&nbsp;撤销</div></td></tr>";
	$("#hissql").append(tr);
	document.querydisform.action="../dsp_search.shtml?method=getdata&key=0123&withinfo=true";
	//alert("submit");
	document.querydisform.submit();
}

function query()
{
	//alert($("#queryvalue").val());
	var where;
	where = $("#tablecol").val()+$("#operator").val()+$("#queryvalue").val();
	$("#where").val(encodeURIComponent(where));
	alert(where);
	document.queryform.submit();
}

function saveDataByQuery()
{
	var a=confirm("确定要保存该语句查询结果吗?");
	 if(a==true)
	 {
		 //salert(a);
		 //document.saveDataByQueryform.target="returnmesg";
		 $("#returnmesg").attr("src","loading.jsp");
		 document.saveDataByQueryform.action="../dsp_search.shtml?method=saveDataByQuery&key=0123&rstype=table&forward=ajaxBlankCreate";;
		 document.saveDataByQueryform.submit(); 
	 }
	 else
	{return false;}
    

}
function createTableByQuery()
{
	var a=confirm("确定要保存当前临时表吗?");
	 if(a==true)
	 {
		 //alert(a);
		 //document.saveDataByQueryform.target="returnmesg";
		 

		 document.saveDataByQueryform.action="../dsp_search.shtml?method=createTableByQuery&key=0193&withinfo=true&rstype=table&forward=ajaxBlankCreate";;
		 document.saveDataByQueryform.submit(); 
	 }
	 else
	{return false;}
    

}

(function($){
	  $.fn.extend({
	  insertAtCaret: function(myValue){
	  var $t=$(this)[0];
	  if (document.selection) {
	  this.focus();
	  sel = document.selection.createRange();
	  sel.text = myValue;
	  this.focus();
	  }
	  else
	  if ($t.selectionStart || $t.selectionStart == '0') {
	  var startPos = $t.selectionStart;
	  var endPos = $t.selectionEnd;
	  var scrollTop = $t.scrollTop;
	  $t.value = $t.value.substring(0, startPos) + myValue + $t.value.substring(endPos, $t.value.length);
	  this.focus();
	  $t.selectionStart = startPos + myValue.length;
	  $t.selectionEnd = startPos + myValue.length;
	  $t.scrollTop = scrollTop;
	  }
	  else {
	  this.value += myValue;
	  if(myValue!=" '' ")
	  this.focus();
	  }
	  }
	  })
	})(jQuery);
	
	
 function reloadtablelist()
 {
	 $("#tabletree").tree("reload");
 }
	
 $("#tablecollist").change(function(){
	 
	 //alert($("#tablecollist").val());
	 
	 createsql(" "+$("#tablecollist").find("option:selected").text());
	 setcolvalue('',$("#tablecollist").find("option:selected").val());
 
 });
 
 $("#tablecollistgroup").change(function(){
	 
	 //alert($("#tablecollist").val());
	 
	
	 //setcolvalueGroup('',$("#tablecollistgroup").find("option:selected").val());
 
 });
 
$("#tablecollistlayer").change(function(){
	var colname =  $("#tablecollistlayer").val();
	
	 var url="<c:out value='${hostPrefix}'/>/dpgapimanage/dsp_search.shtml?method=getdata&key=0192b&rstype=json&column_name="+colname+"&table="+$("#tablename").val()+"&ram="+Math.round(Math.random()*10000);

	// alert(colname);
	 $.getJSON(url,function (data) {

	     //对请求返回的JSON格式进行分解加载
	       
	     $(data).each(function () {
	     	//alert(this.COLUMN_NAME);
	     	tridseq++;
	     	var _len; 
	     	_len=tridseq;
	     	
	     	var tr = "<tr id="+_len+"><td><input type='hidden' value='"+this.column_name+"' name='group_"+_len+"'>"+this.column_name_zh+"</td><td>"+this.column_name+"</td><td>"+this.data_type+"</td><td>"+this.data_length+"</td><td><img onclick=\'deltr(\"querycolnamelayer\","+_len+")\' height=14 src='../ps/framework/images/del.gif'></td></tr>";
	        // alert(tr);
	     	$("#querycolnamelayer").append(tr);
	         
	        
	     });
	    

	 });
	 
 
 });
 
 
$("#tablecollistdis").change(function(){
	 
	var colname =  $("#tablecollistdis").val();
	
	 var url="<c:out value='${hostPrefix}'/>/dpgapimanage/dsp_search.shtml?method=getdata&key=0192b&rstype=json&column_name="+colname+"&table="+$("#tablename").val()+"&ram="+Math.round(Math.random()*10000);

	// alert(colname);
	 $.getJSON(url,function (data) {

	     //对请求返回的JSON格式进行分解加载
	       
	     $(data).each(function () {
	     	//alert(this.COLUMN_NAME);
	     	tridseq++;
	     	var _len; 
	     	_len=tridseq;
	     	
	     	var tr = "<tr id="+_len+"><td><input type='hidden' value='"+this.column_name+"' name='fnfield_"+_len+"'><input type='text' value='"+this.column_name_zh+"' name='fnfieldas_"+_len+"'>"
	     	 +"<select style='width:60px' type='text' name='fnorder_"+_len+"'>"
             +"<option value=''>不排序</option><option value='ASC'>正序</option><option value='desc'>倒序</option></select>"
	     	 +"</td>"
	     	 +"<td>"+this.column_name
	     	 
	     	 +"</td><td>"+this.data_type+"</td>"
	     	 +"<td>"+this.data_length+"</td><td><img onclick=\'deltr(\"querycolnamedisselect\","+_len+")\' height=18 src='../ps/framework/images/del.gif'>"
	         
	     	 +"<a href=\"javascript:void(0)\" onClick=\"moveUp(this)\"><img src='../images/up_16.png'></a><a href=\"javascript:void(0)\" onClick=\"moveDown(this)\"><img src='../images/down_16.png'></a></td></tr>";
		      
	     	
	     	// alert(tr);
	     	$("#querycolnamedisselect").append(tr);
	         
	        
	     });
	    

	 });
});

$("#tablecollistorder").change(function(){
	 
	var colname =  $("#tablecollistorder").val();
	
	 var url="<c:out value='${hostPrefix}'/>/dpgapimanage/dsp_search.shtml?method=getdata&key=0192b&rstype=json&column_name="+colname+"&table="+$("#tablename").val()+"&ram="+Math.round(Math.random()*10000);

	// alert(colname);
	 $.getJSON(url,function (data) {

	     //对请求返回的JSON格式进行分解加载
	       
	     $(data).each(function () {
	     	//alert(this.COLUMN_NAME);
	     	tridseq++;
	     	var _len; 
	     	_len=tridseq;
	     	
	     	var tr = "<tr id="+_len+"><td><input type='hidden' value='"+this.column_name+"' name='fnfield_"+_len+"'>"+this.column_name_zh+"</td><td>"+this.column_name+"</td><td>"+this.data_type+"</td><td>"+this.data_length+"</td><td><img onclick=\'deltr(\"querycolnameorderselect\","+_len+")\' height=18 src='../ps/framework/images/del.gif'>"
	     	 +"<select style='width:60px' type='text' name='fnorder_"+_len+"'>"
             +"<option value=''>不排序</option><option value='ASC'>正序</option><option value='desc'>倒序</option></select>"
  			 +"<a href=\"javascript:void(0)\" onClick=\"moveUp(this)\"><img src='../images/up_16.png'></a><a href=\"javascript:void(0)\" onClick=\"moveDown(this)\"><img src='../images/down_16.png'></a></td></tr>";
		      
	     	
	     	// alert(tr);
	     	$("#querycolnameorderselect").append(tr);
	         
	        
	     });
	    

	 });
});
	
function createsql(text)
{
		$("#querysql").insertAtCaret(text+" ");
}
	
	
function postGroupForm(){    
	   /* var formParam = $("#querygroupform").serialize();//序列化表格内容为字符串   
	    alert(formParam);
	    $.ajax({    
	        type:'post',        
	        url:'getparams.jsp',    
	        data:formParam,    
	        cache:false,    
	        dataType:'json',    
	        success:function(data){ 
	        	alert(2);
	        }    
	    });  
	    */
	    //alert(2);
	    document.querygroupform.action="getparamsgroup.jsp";
	    document.querygroupform.submit();
}

function postDisForm(){ 
	
	    //alert("去重");
	    document.querydisform.action="getparamsdist.jsp";
	    document.querydisform.submit();
}

function postOrderForm(){ 
	
    alert("排序");
    document.queryOrderform.action="getparamsorder.jsp";
    document.queryOrderform.submit();
}
	
$(document).ready(function(){
	
	
			$('#querylib').tree({
				onDblClick: function(node){
				//	alert(node.text);  // alert node text property when clicked
				}
			});

			$('#tabletree').tree({
				
				onDblClick: function(node){
					//alert(node.name);  // alert node text property when clicked
					$("#selecttable").val(node.name);
					document.tableform.submit();
				},
                onContextMenu: function(e,node){
                    e.preventDefault();
                    $(this).tree('select',node.text);
                    $('#tablemm').menu('show',{
                        left: e.pageX,
                        top: e.pageY
                    });
                }
			});
	 
	        $("#addgroup").click(function(){
	        	
	        	var tableid="querycolnamegroupfled";
	        	var fieldtext= $("#groupfn").val();
	        	var filename = $("#tablecollistgroup").find("option:selected").text();
	        	//alert(fieldtext+":"+filename);
	            var _len = $("#"+tableid+" tr").length; 
	            tridseq++;
	            _len=tridseq;
	           
	            $("#"+tableid).append("<tr id="+_len+" align='center'>"
	                                +"<td><input style='width:60px' type='text' name='fn_"+_len+"' value="+fieldtext+" ></td>"
	                                +"<Td><input style='width:100px' readonly=true type='text' name='fnfield_"+_len+"'  value="+filename+"></td>"
	                                +"<td><input style='width:100px' type='text' name='fnfieldas_"+_len+"' value="+filename+"><select style='width:60px' type='text' name='fnorder_"+_len+"'>"
	                                +"<option value=''>不排序</option><option value='ASC'>正序</option><option value='desc'>倒序</option></select>"
	                                +"<input type='button' value='x' onclick=\'deltr(\"querycolnamegroupfled\","+_len+")\'>"
	                                +"<a href=\"javascript:void(0)\" onClick=\"moveUp(this)\"><img src='../images/up_16.png'></a><a href=\"javascript:void(0)\" onClick=\"moveDown(this)\"><img src='../images/down_16.png'></a></td>"
	                            +"</tr>"); 
	           // alert(3);
	            //$("#"+tableid).html().appendTo($("#querygroupform"));
	          
	        }); 
	        
			$("#addgroupby").click(function(){
	        	
				 setcolvalueGroup('',$("#tablecollistgroup").find("option:selected").val());
	          
	        }); 
			$("#addlayer").click(function(){
	        	
				var tableid="querycolnamelayerfled";
				var filed = $("#tablecollistlayer").find("option:selected").val();
	        	var filename = $("#tablecollistlayer").find("option:selected").text();
	        	//alert(fieldtext+":"+filename);
	            var _len = $("#"+tableid+" tr").length; 
	            tridseq++;
	            _len=tridseq;
	           
	            $("#"+tableid).append("<tr id="+_len+" align='center'>"
	                                +"<td><input style='width:100px' type='hidden' name='fnlayer_"+_len+"' value='"+filed+"'>"+filename+"</td>"
	                                +"<Td><input style='width:100px' type='text' name='fnlayerfrom_"+_len+"'>"
	                                +"至<input style='width:100px' type='text' name='fnlayerto_"+_len+"'>"
	                                +"</td><td><select style='width:60px' type='text' name='fnlayerorder_"+_len+"'>"
	                                +"<option value=''>不排序</option><option value='ASC'>正序</option><option value='desc'>倒序</option></select>"
	                                +"<input type='button' value='x' onclick=\'deltr(\"querycolnamelayerfled\","+_len+")\'></td>"
	                            +"</tr>"); 
	           // alert(3);
	          
	        });
	    });
	    
	    //删除<tr/>
 var deltr =function(tableid,index)
	{
	       //alert(":"+index);
	        $("#"+tableid+" tr[id='"+index+"']").remove();//删除当前行
	         
	        
	 };
	
	
	 function moveUp(obj)  
	 {  
	     var current=$(obj).parent().parent();  
	     var prev=current.prev();  
	     if(current.index()>1)  
	     {  
	         current.insertBefore(prev);  
	     }  
	 }  
	 function moveDown(obj)  
	 {  
	     var current=$(obj).parent().parent();  
	     var next=current.next();  
	     if(next)  
	     {  
	         current.insertAfter(next);  
	     }  
	 }  
	 
	 $("#buttonok").bind("click", function() {
			alert($("#createquerysql").val());
			window.returnValue = $("#createquerysql").val()+"|"+$("#value1").val()+"|"+$("#value2").val();
			window.close();
		});
	 
	 $("#scope").bind("click", function() {
		 //$("#firstNum").disabled="enabled";
		 //$("#endNum").disabled="enabled";
		 $("#endNum").removeAttr("disabled");
		 $("#firstNum").removeAttr("disabled");
		});
	 $("#all").bind("click", function() {
		 //$("#firstNum").disabled="enabled";
		 //$("#endNum").disabled="enabled";
		 $("#endNum").attr("disabled","disabled");
		 $("#firstNum").attr("disabled","disabled");
		});
	 
	 function setcondition(){
		 var convalue=$("#querysql").val();
		 $("#condition").val(convalue);
		 $('#dlgquery').dialog('close');
	 }
	 
	 function submitGetData(){
		 var field= '[';
		 var condition=$('#condition').val();
		 //提取用记选择的显示字段
		 var selectobj = document.getElementById("tableselectcolumn");
		 for(var i=0;i<selectobj.length;i++){
			 if(selectobj.options[i].selected == true){
				 var value = selectobj.options[i].value;
				 var text = selectobj.options[i].text;
				 if(i!=selectobj.length-1){
					 field = field + "{\"id\":'"+value+"',\"fieldalias\":'"+text+"'},";
				 }else{
					 field = field + "{\"id\":'"+value+"',\"fieldalias\":'"+text+"'}";
				 }
				 
			 }
		 }
		 field = field + "]";
		 var action_data = "{\"modelid\":1,\"parentflowid\":0,\"srctable\":\"T_LOG_DS\",\"field\":"+field+"}";
		 var submitJson = "[{\"action_id\":\"getdata\",\"action_data\":"+action_data+",\"condition\":\""+condition+"\",\"firstNum\":\""+getFirstVlaue()+"\",\"endNum\":\""+getEndVlaue()+"\"}]";
		 alert(submitJson);
	 }
	 
	 function submitGetData1111(){
		 alert("dddddd");
	 }
	 
	//判断是用是否选择范围提取数据
	 function getFirstVlaue(){
		 if($('input:radio[name="scope"]:checked').val()=="scope"){
			 var firstNum = $('#firstNum').val();
			 return firstNum;
		 }else{
			 return "";
		 }
	 }
	 
	 function getEndVlaue(){
		 if($('input:radio[name="scope"]:checked').val()=="scope"){
			 var endNum = $('#endNum').val();
			 return endNum;
		 }else{
			 return "";
		 }
	 }
	 
     
</script>