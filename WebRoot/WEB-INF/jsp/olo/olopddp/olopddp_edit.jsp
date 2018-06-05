<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
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

<link rel="stylesheet" href="static/css/datepicker.css" />
<!-- 日期框 -->
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>

<script type="text/javascript">
	//保存
	function save() {
		/* if($("#NAME").val()==""){
		$("#NAME").tips({
			side:3,
		    msg:'请输入名称',
		    bg:'#AE81FF',
		    time:2
		});
		$("#NAME").focus();
		return false;
		}
		if($("#CODE").val()==""){
		$("#CODE").tips({
			side:3,
		    msg:'请输入编码',
		    bg:'#AE81FF',
		    time:2
		});
		$("#CODE").focus();
		return false;
		}
		if($("#DIMENSION").val()==""){
		$("#DIMENSION").tips({
			side:3,
		    msg:'请输入尺寸',
		    bg:'#AE81FF',
		    time:2
		});
		$("#DIMENSION").focus();
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
		if($("#IMAGE_URL").val()==""){
		$("#IMAGE_URL").tips({
			side:3,
		    msg:'请输入图片地址（列表展示）',
		    bg:'#AE81FF',
		    time:2
		});
		$("#IMAGE_URL").focus();
		return false;
		}
		if($("#IMAGE_BUG_URL").val()==""){
		$("#IMAGE_BUG_URL").tips({
			side:3,
		    msg:'请输入大图URL',
		    bg:'#AE81FF',
		    time:2
		});
		$("#IMAGE_BUG_URL").focus();
		return false;
		}
		if($("#ISDETAILS").val()==""){
		$("#ISDETAILS").tips({
			side:3,
		    msg:'请输入是否有详情 0 没有详情，1有详情',
		    bg:'#AE81FF',
		    time:2
		});
		$("#ISDETAILS").focus();
		return false;
		}
		if($("#EFFECTIVE_TIME").val()==""){
		$("#EFFECTIVE_TIME").tips({
			side:3,
		    msg:'请输入生效时间（商品展示生效时间）',
		    bg:'#AE81FF',
		    time:2
		});
		$("#EFFECTIVE_TIME").focus();
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
		if($("#SORTING").val()==""){
		$("#SORTING").tips({
			side:3,
		    msg:'请输入排序号',
		    bg:'#AE81FF',
		    time:2
		});
		$("#SORTING").focus();
		return false;
		} */
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
</script>
</head>
<body>
	<form action="olopddp/${msg }.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}" />
		<div id="zhongxin">
			<table id="table_report"
				class="table table-striped table-bordered table-hover">
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">名称:</td>
					<td><input type="text" name="NAME" id="NAME"
						value="${pd.NAME}" maxlength="32" placeholder="这里输入名称" title="名称" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">标签:</td>
					<td><select multiple class="chzn-select"
						id="form-field-select-4" data-placeholder="请选择标签。。。" name="LABEL">
							<option value=""></option>
								<c:forEach var="bq" items="${pd.bq}">
									<option value="${bq.NAME }" <c:if test="${fn:indexOf(pd.LABEL, bq.NAME) != -1 }">selected</c:if>>${bq.NAME }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">编码:</td>
					<td><input type="text" name="CODE" id="CODE"
						value="${pd.CODE}" maxlength="32" placeholder="这里输入编码" title="编码" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">尺寸:</td>
					<td><input type="text" name="DIMENSION" id="DIMENSION"
						value="${pd.DIMENSION}" maxlength="32" placeholder="这里输入尺寸"
						title="尺寸" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">价格:</td>
					<td><input type="text" name="PRICE" id="PRICE"
						value="${pd.PRICE}" maxlength="32" placeholder="这里输入价格" title="价格" /></td>
				</tr>
				<tr>
					<td style="width:70px;text-align: right;padding-top: 13px;">单位:</td>
					<td><input type="text" name="COMPANY" id="COMPANY" value="${pd.COMPANY}" maxlength="32" placeholder="这里输入单位" title="单位"/></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">说明:</td>
					<td><%-- <input type="text" name="REMARKS" id="REMARKS"
						value="${pd.REMARKS}" maxlength="32" placeholder="这里输入说明"
						title="说明" /> --%>
						<textarea name="REMARKS" id="REMARKS" maxlength="1000"  placeholder="这里输入说明">${pd.REMARKS}</textarea>
						</td>
				</tr>
				<tr>
					<td style="width:70px;text-align: right;padding-top: 13px;">门板分类:</td>
					<td>
					 	<select name="ONE_SELECT" val="${pd.ONE_SELECT}" class="form-control combox" style="width: 140px" ref="two_select" refUrl="<%=basePath%>/olopdmenu/queryMenu.do?queryID=queryMenua&M_FID={value}&queryIDMFid=queryMenub">
						</select>
						<select name="TWO_SELECT" val="${pd.TWO_SELECT}" id="two_select"  style="width: 140px" ref="three_select" refUrl="<%=basePath%>/olopdmenu/queryMenu.do?queryID=queryMenub&M_FID={value}&queryIDMFid=queryMenuc" class="form-control combox">
						</select>
						<select name="THREE_SELECT" val="${pd.THREE_SELECT}" id="three_select" style="width: 140px"  ref="four_select" refUrl="<%=basePath%>/olopdmenu/queryMenu.do?queryID=queryMenuc&M_FID={value}&queryIDMFid=queryMenud" class="form-control combox">
						</select>
						<select name="FOUR_SELECT" val="${pd.FOUR_SELECT}" refUrl="<%=basePath%>/olopdmenu/queryMenu.do?queryID=queryMenud&M_FID={value}" ref="" class="form-control combox" id="four_select" style="width: 140px" >
						</select>
					</td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">封存商品:</td>
					<td><select name="SEALED" id="SEALED" title="请选择是否封存">
							<option value="0"
								<c:if test="${pd.SEALED == '0' || pd.SEALED == 0 }">selected</c:if>>启用</option>
							<option value="1"
								<c:if test="${pd.SEALED == '1' || pd.SEALED == 1 }">selected</c:if>>封存</option>
					</select></td>
				</tr>
				<tr style="display: none">
					<td style="width: 70px; text-align: right; padding-top: 13px;">图片地址（列表展示）:</td>
					<td><input type="hidden" name="IMAGE_URL" id="IMAGE_URL"
						value="${pd.IMAGE_URL}" maxlength="32"
						placeholder="这里输入图片地址（列表展示）" title="图片地址（列表展示）" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">图片:</td>
					<td><input type="hidden" name="IMAGE_BUG_URL"
						id="IMAGE_BUG_URL" value="${pd.IMAGE_BUG_URL}" maxlength="32"
						placeholder="这里输入图片路径" title="图片路径" /> 
						<c:if test="${pd.IMAGE_BUG_URL != '' && pd.IMAGE_BUG_URL != null }">
						<img src="${pd.imgBasePath}/${pd.IMAGE_BUG_URL}" />
						</c:if>
						<input type="file" name="file"  id="id-input-file-1">
				</tr>
				<tr >
					<td style="width: 70px; text-align: right; padding-top: 13px;">是否有详情:</td>
					<td><select name="ISDETAILS" id="ISDETAILS" title="请是否有详情">
							<option value="0"
								<c:if test="${pd.ISDETAILS == '0' }">selected</c:if>>没有</option>
							<option value="1"
								<c:if test="${pd.ISDETAILS == '1'  }">selected</c:if>>
								有</option>
					</select> <%-- 	<input type="text" name="ISDETAILS" id="ISDETAILS" value="${pd.ISDETAILS}" maxlength="32" placeholder="这里输入是否有详情 0 没有详情，1有详情" title="是否有详情 0 没有详情，1有详情"/> --%>
					</td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">生效时间:</td>
					<td><input class=" date-picker" type="text"
						name="EFFECTIVE_TIME" id="EFFECTIVE_TIME"
						value="${pd.EFFECTIVE_TIME}" maxlength="32"
						placeholder="这里输入生效时间（商品展示生效时间）" title="生效时间（商品展示生效时间）" /></td>
				</tr>
				<%-- 	<tr>
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
			</tr> --%>
				<%-- <tr>
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
			</tr> --%>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">排序号:</td>
					<td><input type="number" name="SORTING" id="SORTING"
						value="${pd.SORTING}" maxlength="32" placeholder="这里输入排序号"
						title="排序号" /></td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="10"><a
						class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
						class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
						<a
						class="btn btn-mini btn-primary" onclick="addPdppval();">addPdppval()</a>
					</td>
				</tr>
			</table>
		</div>

		<div id="zhongxin2" class="center" style="display: none">
			<br />
			<br />
			<br />
			<br />
			<br />
			<img src="static/images/jiazai.gif" /><br />
			<h4 class="lighter block green">提交中...</h4>
		</div>

	</form>


	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 下拉联动 -->
	<script type="text/javascript" src="plugins/yunm.combox.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript">
		$(top.hangge());
		$(function() {
			 yxwCombox.init();
			//单选框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			//日期框
			$('.date-picker').datepicker();

		});
		
		
	</script>
</body>
</html>