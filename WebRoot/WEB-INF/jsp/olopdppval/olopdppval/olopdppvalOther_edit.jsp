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
			if($("#PROPERTY_NAME").val()==""){
			$("#PROPERTY_NAME").tips({
				side:3,
	            msg:'请输入属性值',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PROPERTY_NAME").focus();
			return false;
		}
		/* if($("#PROPERTY_CONTENT").val()==""){
			$("#PROPERTY_CONTENT").tips({
				side:3,
	            msg:'请输入备注',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PROPERTY_CONTENT").focus();
			return false;
		}
		if($("#INSERT_TIME").val()==""){
			$("#INSERT_TIME").tips({
				side:3,
	            msg:'请输入插入时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#INSERT_TIME").focus();
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
		} */
		/* $("#zhongxin").hide();
		 $("#zhongxin2").show(); */
		 	
		var msg ='${msg}';
		$.ajax({
			type: "POST",
			url: '<%=basePath%>olopdppval/'+msg+'.do?tm=' + new Date().getTime(),
				data : $("#Form").serialize(),
				cache : false,
				success : function(data) {
					top.Dialog.close();
				}
			});
		
	}
	
</script>
	</head>
<body>
	<form action="olopdppval/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="PROPERTY_ID" id="PROPERTY_ID" value="${pd.PROPERTY_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
				<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">特性:</td>
				<td>
						<input type="text" id="searchWords" name="A_NAME" class="form-control" data-provide="typeahead" autocomplete="off" placeholder="请输入要搜索关键词"/>
						<input type="hidden" id="A_ID" name="A_ID"/>
				</td>
			</tr>
			
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">属性值:</td>
				<td><input type="text" name="PROPERTY_NAME" id="PROPERTY_NAME" value="${pd.PROPERTY_NAME}" maxlength="32" placeholder="这里输入属性值" title="属性值"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">备注:</td>
				<td><input type="text" name="PROPERTY_CONTENT" id="PROPERTY_CONTENT" value="${pd.PROPERTY_CONTENT}" maxlength="32" placeholder="这里输入备注" title="备注"/></td>
			</tr>
			
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">封存:</td>
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
		
		<script type="text/javascript" src="plugins/myjs/attribute.js"></script>
		<script type="text/javascript">
		
		var autoComplete = {
		    init: function() {
		        jQuery('#searchWords').typeahead({
		            source: function(query, process) {
		                //query是输入值
		                jQuery.getJSON('<%=path%>/olopdabval/findByName', { "A_NAME": query }, function(data) {
							var json = data.data;                
							var resultList =  [];          
							for(var i =0 ;i<json.length;i++){
									var item = json[i];
									var aItem = { id: item.A_ID, name: item.A_NAME };
									resultList[i]=JSON.stringify(aItem);
							}
						
		                    process( resultList);
		                });
		            },
		            updater: function(item) {
						var json = JSON.parse( item)
						document.getElementById("A_ID").value=json.id;
		                return json.name; //这里一定要return，否则选中不显示
		            },
					sorter: function (items) {          
      				  	var beginswith = [], caseSensitive = [], caseInsensitive = [], item;
				        while (aItem = items.shift()) {
				            var item = JSON.parse(aItem);
				            if (!item.name.toLowerCase().indexOf(this.query.toLowerCase()))     
				                beginswith.push(JSON.stringify(item));
				            else if (~item.name.indexOf(this.query)) caseSensitive.push(JSON.stringify(item));
				            else caseInsensitive.push(JSON.stringify(item));
				        }
       					return beginswith.concat(caseSensitive, caseInsensitive)
  					  },
					highlighter: function (obj) {
       					 var item = JSON.parse(obj);
       					 var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')
        				 return item.name.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
           				 return '<strong>' + match + '</strong>'
       				 })
    				},
		            items: 8, //显示8条
		            delay: 500 //延迟时间
		        });
		    }
		}
		autoComplete.init();
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