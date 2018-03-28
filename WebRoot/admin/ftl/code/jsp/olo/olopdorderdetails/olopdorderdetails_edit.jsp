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
			if($("#ORDER_ID").val()==""){
			$("#ORDER_ID").tips({
				side:3,
	            msg:'请输入订单ID',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ORDER_ID").focus();
			return false;
		}
		if($("#USER_ID").val()==""){
			$("#USER_ID").tips({
				side:3,
	            msg:'请输入人员ID',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#USER_ID").focus();
			return false;
		}
		if($("#SKU_ID").val()==""){
			$("#SKU_ID").tips({
				side:3,
	            msg:'请输入物品ID',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SKU_ID").focus();
			return false;
		}
		if($("#CODE").val()==""){
			$("#CODE").tips({
				side:3,
	            msg:'请输入商品编码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CODE").focus();
			return false;
		}
		if($("#TITLE").val()==""){
			$("#TITLE").tips({
				side:3,
	            msg:'请输入商品名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TITLE").focus();
			return false;
		}
		if($("#NUMBER").val()==""){
			$("#NUMBER").tips({
				side:3,
	            msg:'请输入数量',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#NUMBER").focus();
			return false;
		}
		if($("#PRICE").val()==""){
			$("#PRICE").tips({
				side:3,
	            msg:'请输入价格',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PRICE").focus();
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
		if($("#CREATION_PEOPLE_ID").val()==""){
			$("#CREATION_PEOPLE_ID").tips({
				side:3,
	            msg:'请输入创建人ID',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CREATION_PEOPLE_ID").focus();
			return false;
		}
		if($("#UPDATE_TIME").val()==""){
			$("#UPDATE_TIME").tips({
				side:3,
	            msg:'请输入更新时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#UPDATE_TIME").focus();
			return false;
		}
		if($("#UPDATE_PEOPLE_ID").val()==""){
			$("#UPDATE_PEOPLE_ID").tips({
				side:3,
	            msg:'请输入更新人ID',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#UPDATE_PEOPLE_ID").focus();
			return false;
		}
		if($("#SPREAD1").val()==""){
			$("#SPREAD1").tips({
				side:3,
	            msg:'请输入Spread1',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SPREAD1").focus();
			return false;
		}
		if($("#SPREAD2").val()==""){
			$("#SPREAD2").tips({
				side:3,
	            msg:'请输入Spread2',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SPREAD2").focus();
			return false;
		}
		if($("#SPREAD3").val()==""){
			$("#SPREAD3").tips({
				side:3,
	            msg:'请输入Spread3',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SPREAD3").focus();
			return false;
		}
		if($("#SPREAD4").val()==""){
			$("#SPREAD4").tips({
				side:3,
	            msg:'请输入Spread4',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SPREAD4").focus();
			return false;
		}
		if($("#SPREAD5").val()==""){
			$("#SPREAD5").tips({
				side:3,
	            msg:'请输入Spread5',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SPREAD5").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="olopdorderdetails/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">订单ID:</td>
				<td><input type="text" name="ORDER_ID" id="ORDER_ID" value="${pd.ORDER_ID}" maxlength="32" placeholder="这里输入订单ID" title="订单ID"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">人员ID:</td>
				<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.USER_ID}" maxlength="32" placeholder="这里输入人员ID" title="人员ID"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">物品ID:</td>
				<td><input type="text" name="SKU_ID" id="SKU_ID" value="${pd.SKU_ID}" maxlength="32" placeholder="这里输入物品ID" title="物品ID"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">商品编码:</td>
				<td><input type="text" name="CODE" id="CODE" value="${pd.CODE}" maxlength="32" placeholder="这里输入商品编码" title="商品编码"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">商品名称:</td>
				<td><input type="text" name="TITLE" id="TITLE" value="${pd.TITLE}" maxlength="32" placeholder="这里输入商品名称" title="商品名称"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">数量:</td>
				<td><input type="text" name="NUMBER" id="NUMBER" value="${pd.NUMBER}" maxlength="32" placeholder="这里输入数量" title="数量"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">价格:</td>
				<td><input type="text" name="PRICE" id="PRICE" value="${pd.PRICE}" maxlength="32" placeholder="这里输入价格" title="价格"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">创建时间:</td>
				<td><input type="text" name="CREATE_TIME" id="CREATE_TIME" value="${pd.CREATE_TIME}" maxlength="32" placeholder="这里输入创建时间" title="创建时间"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">创建人ID:</td>
				<td><input type="text" name="CREATION_PEOPLE_ID" id="CREATION_PEOPLE_ID" value="${pd.CREATION_PEOPLE_ID}" maxlength="32" placeholder="这里输入创建人ID" title="创建人ID"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">更新时间:</td>
				<td><input type="text" name="UPDATE_TIME" id="UPDATE_TIME" value="${pd.UPDATE_TIME}" maxlength="32" placeholder="这里输入更新时间" title="更新时间"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">更新人ID:</td>
				<td><input type="text" name="UPDATE_PEOPLE_ID" id="UPDATE_PEOPLE_ID" value="${pd.UPDATE_PEOPLE_ID}" maxlength="32" placeholder="这里输入更新人ID" title="更新人ID"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">Spread1:</td>
				<td><input type="text" name="SPREAD1" id="SPREAD1" value="${pd.SPREAD1}" maxlength="32" placeholder="这里输入Spread1" title="Spread1"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">Spread2:</td>
				<td><input type="text" name="SPREAD2" id="SPREAD2" value="${pd.SPREAD2}" maxlength="32" placeholder="这里输入Spread2" title="Spread2"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">Spread3:</td>
				<td><input type="text" name="SPREAD3" id="SPREAD3" value="${pd.SPREAD3}" maxlength="32" placeholder="这里输入Spread3" title="Spread3"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">Spread4:</td>
				<td><input type="text" name="SPREAD4" id="SPREAD4" value="${pd.SPREAD4}" maxlength="32" placeholder="这里输入Spread4" title="Spread4"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">Spread5:</td>
				<td><input type="text" name="SPREAD5" id="SPREAD5" value="${pd.SPREAD5}" maxlength="32" placeholder="这里输入Spread5" title="Spread5"/></td>
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