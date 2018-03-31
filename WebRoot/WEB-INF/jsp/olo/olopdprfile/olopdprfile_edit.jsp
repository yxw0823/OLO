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
	/* 		if($("#FILE_PATH").val()==""){
			$("#FILE_PATH").tips({
				side:3,
	            msg:'请输入文件路径',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#FILE_PATH").focus();
			return false;
		}
		if($("#FILETYPE").val()==""){
			$("#FILETYPE").tips({
				side:3,
	            msg:'请输入文件类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#FILETYPE").focus();
			return false;
		}
		if($("#MENU").val()==""){
			$("#MENU").tips({
				side:3,
	            msg:'请输入对应目录',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#MENU").focus();
			return false;
		}
		if($("#SORTING").val()==""){
			$("#SORTING").tips({
				side:3,
	            msg:'请输入排序',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SORTING").focus();
			return false;
		}
		if($("#GOODS_ID").val()==""){
			$("#GOODS_ID").tips({
				side:3,
	            msg:'请输入商品ID',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#GOODS_ID").focus();
			return false;
		}
		if($("#SEALED").val()==""){
			$("#SEALED").tips({
				side:3,
	            msg:'请输入封存文件  默认 0  不封存 1 封存',
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
		var textFilePath = $("#textFilePath");
		if(textFilePath !=null && textFilePath.val() !='' && textFilePath.val() !=null ){
			$("#FILE_PATH").val(textFilePath.val());
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="olopdprfile/${msg }.do" name="Form" id="Form" method="post"  enctype="multipart/form-data">
		<input type="hidden" name="FILE_ID" id="FILE_ID" value="${pd.FILE_ID}"/>
		<input type="hidden" name="FILE_PATH" id="FILE_PATH" value="${pd.FILE_PATH}" placeholder="这里输入图片路径" title="图片路径"/>
				
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">文件名称:</td>
				<td><input type="text" name="SPREAD1" id="SPREAD1" value="${pd.SPREAD1}" maxlength="50" placeholder="这里输入文件名称" title="排序"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">文件:</td>
				<td id="FILEPATHtr">
				<input type="file" name="file"  id="id-input-file-1">
				</td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">文件类型:</td>
				<td>
					<select  name="FILETYPE" id="FILETYPE" title="请选择文件类型"  >
							<option value="0" <c:if test="${pd.FILETYPE == '0' || pd.FILETYPE == 0 }">selected</c:if> >图片</option>
							<option value="1" <c:if test="${pd.FILETYPE == '1' || pd.FILETYPE == 1 }">selected</c:if> >视频</option>
							<option value="2" <c:if test="${pd.FILETYPE == '2' || pd.FILETYPE == 2 }">selected</c:if> >文件</option>
							<option value="3" <c:if test="${pd.FILETYPE == '3' || pd.FILETYPE == 3 }">selected</c:if> >文本</option>
					</select>
				</td>
			</tr>
			<tr style="display: none;">
				<td style="width:70px;text-align: right;padding-top: 13px;">对应目录:</td>
				<td><input type="text" name="MENU" id="MENU" value="${pd.MENU}" maxlength="32" placeholder="这里输入对应目录" title="对应目录"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">排序:</td>
				<td><input type="text" name="SORTING" id="SORTING" value="${pd.SORTING}" maxlength="32" placeholder="这里输入排序" title="排序"/></td>
			</tr>
			<tr style="display: none;">
				<td style="width:70px;text-align: right;padding-top: 13px;">商品ID:</td>
				<td><input type="text" name="GOODS_ID" id="GOODS_ID" value="${pd.GOODS_ID}" maxlength="32" placeholder="这里输入商品ID" title="商品ID"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">封存文件:</td>
				<td>
				<select  name="SEALED" id="SEALED" title="请选择是否封存"  >
						<option value="0" <c:if test="${pd.SEALED == '0' || pd.SEALED == 0 }">selected</c:if> >启用</option>
						<option value="1" <c:if test="${pd.SEALED == '1' || pd.SEALED == 1 }">selected</c:if> >封存</option>
				</select>
				</td>
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
		var filetype ='${pd.FILETYPE}';
		if(filetype ==  '3'){
			$("#FILEPATHtr").html('<textarea rows="3" cols="5" id="textFilePath">${pd.FILE_PATH}</textarea>');
		}
		if(filetype ==  '1'){
			$("#FILEPATHtr").html('<input type="text"  id="textFilePath" value="${pd.FILE_PATH}" placeholder="这里输入视频的VID" title=""/>');
		}
		$('#FILETYPE').on('change',function(){
			//alert($(this).val());
			var  value =$(this).val();
			if(value =='3'){
				$("#FILEPATHtr").html('<textarea rows="3" cols="5" id="textFilePath"></textarea>');
				return;
			}
			
			if(value =='1'){
				$("#FILEPATHtr").html('<input type="text"  id="textFilePath" value="" placeholder="这里输入视频的VID" title=""/>');
				return;
			}
				$("#FILEPATHtr").html('<input type="file" name="file"  id="id-input-file-1">');
			
			
		})
		</script>
</body>
</html>