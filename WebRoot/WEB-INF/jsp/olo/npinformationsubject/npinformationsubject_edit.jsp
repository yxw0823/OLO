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
		
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="npinformationsubject/${msg }.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="SUBJECT_ID" id="SUBJECT_ID" value="${pd.SUBJECT_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">标题:</td>
				<td><input type="text" name="TITLE" id="TITLE" value="${pd.TITLE}" maxlength="32" placeholder="这里输入标题" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">封面图片:</td>
				<td>
					<div id="tpFIle" style="<c:if test="${pd != null && pd.TEMP2 != '' && pd.TEMP2 != null }">display: none</c:if>">
							<input type="file" id="tp" name="file" onchange="fileType(this)"/>
					</div>
					<div id="tpImge" style="<c:if test="${pd == null || pd.TEMP2 == '' || pd.TEMP2 == null }">display: none</c:if>">
						<a href="${pd.TEMP2}" target="_blank"><img src="${pd.TEMP2}" width="210"/></a>
						<input type="button" class="btn btn-mini btn-danger" value="删除" onclick="delP('tpImge','tpFIle');" />
						<input type="hidden" name="TEMP2" id="TEMP2" value="${pd.TEMP2 }"/>
					</div>
				</td>
			</tr>
			
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">分类:</td>
				<td>
					<select name="TYPE">
						<c:forEach  items="${pd.CPGGLE}" var="var" varStatus="vs">
							<option value="${var.P_BM }" <c:if test="${var.P_BM == pd.TYPE }">selected</c:if> >${var.NAME }</option>
						</c:forEach>
					</select>

				</td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">详情跳转地址:</td>
				<td><input type="text" name="URL" id="URL" value="${pd.URL}"  placeholder="这里输入详情跳转地址" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">排序:</td>
				<td><input type="number" name="SORT" id="SORT" value="${pd.SORT}"   placeholder="这里输入排序号" title=""/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">详情:</td>
				<td><script id="editor" name="CONTENT" type="text/plain" style="width:700px;height:300px;">${pd.CONTENT}</script></td>
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
		<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
    	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.min.js"> </script>
		<script type="text/javascript">
		$(top.hangge());
		var ue = UE.getEditor('editor');
		$(function() {
			UE.Editor.prototype._bkGetActionUrl=UE.Editor.prototype.getActionUrl;
	        UE.Editor.prototype.getActionUrl=function(action){
	            if (action == 'uploadimage'){
	                return '<%=basePath%>/pictures/save.do';    /* 这里填上你自己的上传图片的action */
	            }else{
	                return this._bkGetActionUrl.call(this, action);
	            }
	        };
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			//上传
			$('#tp').ace_file_input({
				no_file:'请选择图片 ...',
				btn_choose:'选择',
				btn_change:'更改',
				droppable:false,
				onchange:null,
				thumbnail:false, //| true | large
				whitelist:'gif|png|jpg|jpeg',
				//blacklist:'gif|png|jpg|jpeg'
				//onchange:''
				//
			});
		});
		//过滤类型
		function fileType(obj){
			var fileType=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
		    if(fileType != '.gif' && fileType != '.png' && fileType != '.jpg' && fileType != '.jpeg'){
		    	$("#tp").tips({
					side:3,
		            msg:'请上传图片格式的文件',
		            bg:'#AE81FF',
		            time:3
		        });
		    	$("#tp").val('');
		    	document.getElementById("tp").files[0] = '请选择图片';
		    }
		}
		
		//删除图片
		function delP(tpImge,tpFile){
			 if(confirm("确定要删除图片？")){
				 $("#"+tpImge).hide();
				 $("#"+tpFile).show();
				 $("#IMG_PATH").val("");
			} 
		}
		</script>
</body>
</html>