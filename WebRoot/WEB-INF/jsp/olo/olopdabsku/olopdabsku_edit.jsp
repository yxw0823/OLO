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
			if($("#A_ID").val()==""){
			$("#A_ID").tips({
				side:3,
	            msg:'请输入特性ID',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#A_ID").focus();
			return false;
		}
		if($("#PROPERTY_ID").val()==""){
			$("#PROPERTY_ID").tips({
				side:3,
	            msg:'请输入属性ID(红色，蓝色等)',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PROPERTY_ID").focus();
			return false;
		}
		if($("#CREATE_TIME").val()==""){
			$("#CREATE_TIME").tips({
				side:3,
	            msg:'请输入创建时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CREATE_TIME").focus();
			return false;
		}
		if($("#SORTING").val()==""){
			$("#SORTING").tips({
				side:3,
	            msg:'请输入排序号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SORTING").focus();
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
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="olopdabsku/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="SKU_ID" id="SKU_ID" value="${pd.SKU_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">特性ID:</td>
				<td><input type="text" name="A_ID" id="A_ID" value="${pd.A_ID}" maxlength="32" placeholder="这里输入特性ID" title="特性ID"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">属性ID(红色，蓝色等):</td>
				<td><input type="text" name="PROPERTY_ID" id="PROPERTY_ID" value="${pd.PROPERTY_ID}" maxlength="32" placeholder="这里输入属性ID(红色，蓝色等)" title="属性ID(红色，蓝色等)"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">创建时间:</td>
				<td><input type="text" name="CREATE_TIME" id="CREATE_TIME" value="${pd.CREATE_TIME}" maxlength="32" placeholder="这里输入创建时间" title="创建时间"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">排序号:</td>
				<td><input type="text" name="SORTING" id="SORTING" value="${pd.SORTING}" maxlength="32" placeholder="这里输入排序号" title="排序号"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">封存商品 默认 0  不封存 1 封存:</td>
				<td><input type="text" name="SEALED" id="SEALED" value="${pd.SEALED}" maxlength="32" placeholder="这里输入封存商品 默认 0  不封存 1 封存" title="封存商品 默认 0  不封存 1 封存"/></td>
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