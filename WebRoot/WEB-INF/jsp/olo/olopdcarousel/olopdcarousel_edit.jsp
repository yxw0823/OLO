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
			/* if($("#SRC").val()==""){
			$("#SRC").tips({
				side:3,
	            msg:'请输入图片路径',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SRC").focus();
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
		if($("#CLASSIFY_ID").val()==""){
			$("#CLASSIFY_ID").tips({
				side:3,
	            msg:'请输入分类ID',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CLASSIFY_ID").focus();
			return false;
		}
		if($("#LOCATION").val()==""){
			$("#LOCATION").tips({
				side:3,
	            msg:'请输入0 头部滚动，1页面底部展示',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#LOCATION").focus();
			return false;
		}
		if($("#REMARKS").val()==""){
			$("#REMARKS").tips({
				side:3,
	            msg:'请输入描述',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#REMARKS").focus();
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
		} */
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="olopdcarousel/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">图片路径:</td>
				<td>
				
				<input type="hidden" name="SRC" id="SRC" value="${pd.SRC}" maxlength="32" placeholder="这里输入图片路径" title="图片路径"/>
				<img src="${pd.imgBasePath}${pd.SRC}"/>
				
				</td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">排序号:</td>
				<td><input type="number" name="SORTING" id="SORTING" value="${pd.SORTING}" maxlength="32" placeholder="这里输入排序号" title="排序号"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">描述:</td>
				<td><input type="text" name="REMARKS" id="REMARKS" value="${pd.REMARKS}" maxlength="32" placeholder="这里输入描述" title="描述"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">是否封存:</td>
				<td>
				<select  name="SEALED" id="SEALED" title="请选择是否封存"  >
						<option value="0" <c:if test="${pd.SEALED == '0' || pd.SEALED == 0 }">selected</c:if> >启用</option>
						<option value="1" <c:if test="${pd.SEALED == '1' || pd.SEALED == 1 }">selected</c:if> >封存</option>
					</select>
			   </td>
			</tr>
			<%-- <tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">商品编码:</td>
				<td><input type="text" name="SPREAD1" id="SPREAD1" value="${pd.SPREAD1}" maxlength="32" placeholder="这里输入Spread1" title="Spread1"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">商品ID:</td>
				<td><input type="text" name="SPREAD2" id="SPREAD2" value="${pd.SPREAD2}" maxlength="32" placeholder="这里输入Spread2" title="Spread2"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">分类名称:</td>
				<td><input type="text" name="SPREAD3" id="SPREAD3" value="${pd.SPREAD3}" maxlength="32" placeholder="这里输入Spread3" title="Spread3"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">Spread4:</td>
				<td><input type="text" name="SPREAD4" id="SPREAD4" value="${pd.SPREAD4}" maxlength="32" placeholder="这里输入Spread4" title="Spread4"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">Spread5:</td>
				<td><input type="text" name="SPREAD5" id="SPREAD5" value="${pd.SPREAD5}" maxlength="32" placeholder="这里输入Spread5" title="Spread5"/></td>
			</tr> --%>
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