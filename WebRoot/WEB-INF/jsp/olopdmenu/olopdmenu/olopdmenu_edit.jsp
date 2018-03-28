<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
<script type="text/javascript">
	
	
	//保存
	function save(){
			if($("#PARENT_ID").val()==""){
			$("#PARENT_ID").tips({
				side:3,
	            msg:'请输入记录父节点',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PARENT_ID").focus();
			return false;
		}
		if($("#PATH").val()==""){
			$("#PATH").tips({
				side:3,
	            msg:'请输入节点全路径(1/2/3)',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PATH").focus();
			return false;
		}
		if($("#TYPE").val()==""){
			$("#TYPE").tips({
				side:3,
	            msg:'请输入节点类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TYPE").focus();
			return false;
		}
		if($("#SEALED").val()==""){
			$("#SEALED").tips({
				side:3,
	            msg:'请输入封存商品 默认 0  不封存 1 封存',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SEALED").focus();
			return false;
		}
		if($("#NAME").val()==""){
			$("#NAME").tips({
				side:3,
	            msg:'请输入节点名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#NAME").focus();
			return false;
		}
		if($("#ENGLISHNAME").val()==""){
			$("#ENGLISHNAME").tips({
				side:3,
	            msg:'请输入节点英文名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ENGLISHNAME").focus();
			return false;
		}
		if($("#SRC").val()==""){
			$("#SRC").tips({
				side:3,
	            msg:'请输入图片路径',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SRC").focus();
			return false;
		}
		if($("#REMARKS").val()==""){
			$("#REMARKS").tips({
				side:3,
	            msg:'请输入说明',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#REMARKS").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="olopdmenu/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">记录父节点:</td>
				<td><input type="text" name="PARENT_ID" id="PARENT_ID" value="${pd.PARENT_ID}" maxlength="32" placeholder="这里输入记录父节点" title="记录父节点"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">节点全路径(1/2/3):</td>
				<td><input type="text" name="PATH" id="PATH" value="${pd.PATH}" maxlength="32" placeholder="这里输入节点全路径(1/2/3)" title="节点全路径(1/2/3)"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">节点类型:</td>
				<td><input type="text" name="TYPE" id="TYPE" value="${pd.TYPE}" maxlength="32" placeholder="这里输入节点类型" title="节点类型"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">封存商品 默认 0  不封存 1 封存:</td>
				<td><input type="text" name="SEALED" id="SEALED" value="${pd.SEALED}" maxlength="32" placeholder="这里输入封存商品 默认 0  不封存 1 封存" title="封存商品 默认 0  不封存 1 封存"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">节点名称:</td>
				<td><input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="32" placeholder="这里输入节点名称" title="节点名称"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">节点英文名:</td>
				<td><input type="text" name="ENGLISHNAME" id="ENGLISHNAME" value="${pd.ENGLISHNAME}" maxlength="32" placeholder="这里输入节点英文名" title="节点英文名"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">图片路径:</td>
				<td><input type="text" name="SRC" id="SRC" value="${pd.SRC}" maxlength="32" placeholder="这里输入图片路径" title="图片路径"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">说明:</td>
				<td><input type="text" name="REMARKS" id="REMARKS" value="${pd.REMARKS}" maxlength="32" placeholder="这里输入说明" title="说明"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		</script>
</body>
</html>