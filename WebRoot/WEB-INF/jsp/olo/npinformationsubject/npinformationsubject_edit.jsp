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
			if($("#TITLE").val()==""){
			$("#TITLE").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TITLE").focus();
			return false;
		}
		if($("#AUTHOR").val()==""){
			$("#AUTHOR").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#AUTHOR").focus();
			return false;
		}
		if($("#URL").val()==""){
			$("#URL").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#URL").focus();
			return false;
		}
		if($("#BACKGROUND_IMG").val()==""){
			$("#BACKGROUND_IMG").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#BACKGROUND_IMG").focus();
			return false;
		}
		if($("#CONTENT").val()==""){
			$("#CONTENT").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CONTENT").focus();
			return false;
		}
		if($("#SEO_KEYWORD").val()==""){
			$("#SEO_KEYWORD").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SEO_KEYWORD").focus();
			return false;
		}
		if($("#SEO_DESC").val()==""){
			$("#SEO_DESC").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SEO_DESC").focus();
			return false;
		}
		if($("#IS_SHOW").val()==""){
			$("#IS_SHOW").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#IS_SHOW").focus();
			return false;
		}
		if($("#DELFLAG").val()==""){
			$("#DELFLAG").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#DELFLAG").focus();
			return false;
		}
		if($("#CREATE_USER_ID").val()==""){
			$("#CREATE_USER_ID").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CREATE_USER_ID").focus();
			return false;
		}
		if($("#CREATE_DATE").val()==""){
			$("#CREATE_DATE").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CREATE_DATE").focus();
			return false;
		}
		if($("#UPDATE_USER_ID").val()==""){
			$("#UPDATE_USER_ID").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#UPDATE_USER_ID").focus();
			return false;
		}
		if($("#UPDATE_DATE").val()==""){
			$("#UPDATE_DATE").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#UPDATE_DATE").focus();
			return false;
		}
		if($("#TEMP1").val()==""){
			$("#TEMP1").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TEMP1").focus();
			return false;
		}
		if($("#TEMP2").val()==""){
			$("#TEMP2").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TEMP2").focus();
			return false;
		}
		if($("#TEMP3").val()==""){
			$("#TEMP3").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TEMP3").focus();
			return false;
		}
		if($("#TEMP4").val()==""){
			$("#TEMP4").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TEMP4").focus();
			return false;
		}
		if($("#TEMP5").val()==""){
			$("#TEMP5").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TEMP5").focus();
			return false;
		}
		if($("#MOBILE_CONTENT").val()==""){
			$("#MOBILE_CONTENT").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#MOBILE_CONTENT").focus();
			return false;
		}
		if($("#MOBILE_SORT").val()==""){
			$("#MOBILE_SORT").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#MOBILE_SORT").focus();
			return false;
		}
		if($("#MOBILE_TITLE_IMG").val()==""){
			$("#MOBILE_TITLE_IMG").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#MOBILE_TITLE_IMG").focus();
			return false;
		}
		if($("#RELEASE_TIME").val()==""){
			$("#RELEASE_TIME").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#RELEASE_TIME").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="npinformationsubject/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="SUBJECT_ID" id="SUBJECT_ID" value="${pd.SUBJECT_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="TITLE" id="TITLE" value="${pd.TITLE}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="AUTHOR" id="AUTHOR" value="${pd.AUTHOR}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="URL" id="URL" value="${pd.URL}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="BACKGROUND_IMG" id="BACKGROUND_IMG" value="${pd.BACKGROUND_IMG}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="CONTENT" id="CONTENT" value="${pd.CONTENT}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="SEO_KEYWORD" id="SEO_KEYWORD" value="${pd.SEO_KEYWORD}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="SEO_DESC" id="SEO_DESC" value="${pd.SEO_DESC}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="IS_SHOW" id="IS_SHOW" value="${pd.IS_SHOW}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="DELFLAG" id="DELFLAG" value="${pd.DELFLAG}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="CREATE_USER_ID" id="CREATE_USER_ID" value="${pd.CREATE_USER_ID}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="CREATE_DATE" id="CREATE_DATE" value="${pd.CREATE_DATE}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="UPDATE_USER_ID" id="UPDATE_USER_ID" value="${pd.UPDATE_USER_ID}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="UPDATE_DATE" id="UPDATE_DATE" value="${pd.UPDATE_DATE}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="TEMP1" id="TEMP1" value="${pd.TEMP1}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="TEMP2" id="TEMP2" value="${pd.TEMP2}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="TEMP3" id="TEMP3" value="${pd.TEMP3}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="TEMP4" id="TEMP4" value="${pd.TEMP4}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="TEMP5" id="TEMP5" value="${pd.TEMP5}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="MOBILE_CONTENT" id="MOBILE_CONTENT" value="${pd.MOBILE_CONTENT}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="MOBILE_SORT" id="MOBILE_SORT" value="${pd.MOBILE_SORT}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="MOBILE_TITLE_IMG" id="MOBILE_TITLE_IMG" value="${pd.MOBILE_TITLE_IMG}" maxlength="32" placeholder="这里输入" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">:</td>
				<td><input type="text" name="RELEASE_TIME" id="RELEASE_TIME" value="${pd.RELEASE_TIME}" maxlength="32" placeholder="这里输入" title=""/></td>
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