<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String datetime = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
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
<link rel="stylesheet" type="text/css"
	href="plugins/webuploader/webuploader.css" />
<link rel="stylesheet" type="text/css"
	href="plugins/webuploader/style.css" />

<!--[if IE 7]>
		  <link rel="stylesheet" href="css/font-awesome-ie7.min.css" />
		<![endif]-->
<!-- page specific plugin styles -->

<link rel="stylesheet" href="static/css/colorbox.css" />
<!-- ace styles -->
<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />
<!--[if lt IE 9]>
		  <link rel="stylesheet" href="css/ace-ie.min.css" />
		<![endif]-->
<script type="text/javascript">
	
	
	
	
</script>
</head>
<body>
	<form action="olopdproduct/${msg }.do" name="Form" id="Form"
		method="post">
		<input type='hidden' name="SKU" value="" id="SKU">
		<input type="hidden" name="GOODS_ID" id="GOODS_ID"
			value="${pd.GOODS_ID}" />
		<div id="zhongxin">
			<div id="wrapper">
				<div id="container">
					<!--头部，相册选择和格式选择-->

					<div id="uploader">
						<div class="queueList">
							<div id="dndArea" class="placeholder">
								<div id="IMAGE_URL"></div>

								<p>或将照片拖到这里，单次最多可选300张</p>
							</div>
						</div>
						<div class="statusBar" style="display: none;">
							<div class="progress">
								<span class="text">0%</span> <span class="percentage"></span>
							</div>
							<div class="btns" style="display: inline">
								<div style="display: inline" id="IMG_TOP_SMALL"></div>
								<div style="display: inline" id="IMG_BOTTOM"></div>
								<div style="display: inline" id="IMG_LIST"></div>
								<div style="display: inline" id="IMAGE_URL1"></div>
								<div class="uploadBtn">开始上传</div>
							</div>
						</div>
						<div id="imagesDiv" style="display: none">
							<!-- 图片上成功后图片写入input type= hidden name = IMAGE_URL value =图片路径   -->
						</div>
					</div>
				</div>
			</div>
			<div>
				<div class="row-fluid">
					<ul class="ace-thumbnails">
						<c:forEach var="img" items="${pd.imgs}">
							<li id="${img.ID}"><a href="${pd.imgBasePath }${img.SRC }"
								title="Photo Title" data-rel="colorbox" class="cboxElement">
									<img alt="150x150" src="${pd.imgBasePath }${img.SRC }"
									style="width: 150px; height: 150px">
									<div class="tags">
										<c:if test="${img.LOCATION == 1 && img.SORTING == 0}">
											<span class="label label-info">列表图片</span>
										</c:if>
										<c:if test="${img.LOCATION == 1 && img.SORTING != 0}">
											<span class="label label-info">顶部大图</span>
										</c:if>
										<c:if test="${img.LOCATION == 2}">
											<span class="label label-info">顶部小图</span>
										</c:if>
										<c:if test="${img.LOCATION == 3}">
											<span class="label label-info">底部图片</span>
										</c:if>
									</div>
							</a>
								<div class="tools tools-bottom">
									<a href="javascript:delImg('${img.ID}')"><i
										class="icon-remove red"></i></a>
								</div></li>
						</c:forEach>

					</ul>
				</div>
			</div>
			<table id="table_report"
				class="table table-striped table-bordered table-hover">
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">标题:</td>
					<td><input type="text" name="TITLE" id="TITLE"
						value="${pd.TITLE}" maxlength="32" placeholder="这里输入商品标题"
						title="商品标题" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">短标题:</td>
					<td><input type="text" name="SHORT_TITLE" id="SHORT_TITLE"
						value="${pd.SHORT_TITLE}" maxlength="32" placeholder="这里输入商品短标题"
						title="商品短标题" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">标签:</td>
					<td><select multiple class="chzn-select"
						id="form-field-select-4" data-placeholder="请选择标签。。。" name="LABEL">
							<option value=""></option>
							<c:forEach var="bq" items="${pd.bq}">
								<option value="${bq.ZD_ID }" <c:if test="${fn:indexOf(pd.LABEL, bq.ZD_ID) != -1 }">selected</c:if>>${bq.NAME }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">尺寸:</td>
					<td><input type="text" name="DIMENSION" id="DIMENSION"
						value="${pd.DIMENSION}" maxlength="32" placeholder="这里输入尺寸"
						title="尺寸" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">编码:</td>
					<td><input type="text" name="CODE" id="CODE"
						value="${pd.CODE}" maxlength="32" placeholder="这里输入编码" title="编码" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">底价:</td>
					<td><input type="number" name="FLOOR_PRICE" id="FLOOR_PRICE"
						value="${pd.FLOOR_PRICE}" maxlength="32" placeholder="这里输入底价"
						title="底价" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">价格:</td>
					<td><input type="number" name="PRICE" id="PRICE"
						value="${pd.PRICE}" maxlength="32" placeholder="这里输入价格" title="价格" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">单位:</td>
					<td><input type="text" name="COMPANY" id="COMPANY"
						value="${pd.COMPANY}" maxlength="32" placeholder="这里输入单位"
						title="单位" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">颜色:</td>
					<td><input type="text" name="COLOUR" id="COLOUR"
						value="${pd.COLOUR}" maxlength="32" placeholder="这里输入颜色"
						title="颜色" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">商品分类:</td>
					<td><select name="ONE_SELECT" val="${pd.ONE_SELECT}"
						class="form-control combox" style="width: 140px" ref="two_select"
						refUrl="<%=basePath%>/olopdmenu/queryMenu.do?queryID=queryMenua&M_FID={value}&queryIDMFid=queryMenub">
					</select> <select name="TWO_SELECT" val="${pd.TWO_SELECT}" id="two_select"
						style="width: 140px" ref="three_select"
						refUrl="<%=basePath%>/olopdmenu/queryMenu.do?queryID=queryMenub&M_FID={value}&queryIDMFid=queryMenuc"
						class="form-control combox">
					</select> <select name="THREE_SELECT" val="${pd.THREE_SELECT}"
						id="three_select" style="width: 140px" ref="four_select"
						refUrl="<%=basePath%>/olopdmenu/queryMenu.do?queryID=queryMenuc&M_FID={value}&queryIDMFid=queryMenud"
						class="form-control combox">
					</select> <select name="FOUR_SELECT" val="${pd.FOUR_SELECT}"
						refUrl="<%=basePath%>/olopdmenu/queryMenu.do?queryID=queryMenud&M_FID={value}"
						ref="" class="form-control combox" id="four_select"
						style="width: 140px">
					</select> <%-- 					<input type="text" name="CATEGORY" id="CATEGORY" value="${pd.CATEGORY}" maxlength="32" placeholder="这里输入商品分类(橱柜/款式/颜色)" title="商品分类(橱柜/款式/颜色)"/>
 --%></td>
				</tr>

				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;"
						var="${pd.SPREAD5 }">建议搭配风格:</td>
					<td><c:forEach items="${pd.styles}" var="var" varStatus="vs">
							<input name="styles" class="ace-checkbox-2"
								<c:if test="${fn:indexOf(pd.SPREAD5,var.ID) != -1 }"> checked="checked"</c:if>
								value="${var.ID }" type="checkbox" />
							<span class="lbl"> ${var.NAME }</span>
						</c:forEach></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">生效时间:</td>
					<td><input class=" date-picker" type="text"
						name="EFFECTIVE_TIME" id="EFFECTIVE_TIME"
						value="${pd.EFFECTIVE_TIME}" maxlength="32"
						data-date-format="yyyy-mm-dd" readonly="readonly"
						placeholder="这里输入生效时间（商品展示生效时间）" title="生效时间（商品展示生效时间）" /></td>
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
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">排序号:</td>
					<td><input type="number" name="SORTING" id="SORTING"
						value="${pd.SORTING}" maxlength="32" placeholder="这里输入颜色"
						title="颜色" /></td>
				</tr>

				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">说明:</td>
					<td><textarea rows="3" cols="" name="REMARKS"
							style="width: 80%">${pd.REMARKS}</textarea></td>
				</tr>
			</table>
			<div class="form-actions center">
					&nbsp;&nbsp;<a class="btn btn-small btn-success" onclick="tjdp('${pd.GOODS_ID}');">推荐搭配</a>
					&nbsp;&nbsp;<a class="btn btn-small btn-success" onclick="addPdppval();">新增属性</a>
			</div>
			
			<div >
			<h3 class="header smaller lighter blue">相关分类</h3>
					<c:forEach items="${pd.SPXQLM}" var="var" varStatus="vs">
						<div  class="widget-box collapsed xgflClass" id='${var.ZD_ID}'>
							<div class="widget-header widget-header-small header-color-blue">
							 	<h6> ${var.NAME }</h6>
				             <div class="widget-toolbar">
									<a  onclick="addXgfl('${var.ZD_ID}')" ><i style="color: #ffffff;">新增</i></a>
									<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
							</div>
			                  <div class="widget-body">
										<div class="widget-main no-padding">
											<table id="table_bug_report"
												class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th class="center">文件名称</th>
														<th class="center">文件类型</th>
														<th class="center">排序</th>
														<th class="center">创建时间</th>
														<th class="center">操作</th>
													</tr>
												</thead>
												<tbody id="xgfl${var.ZD_ID }">

												</tbody>
											</table>
										</div>
								 </div>
								</div>
			                </div>
		               	 </div>
					</c:forEach>
	                
	              
	             
		</div><!--/span-->
			
			
			
			<!-- 推荐搭配-->
			<div>
								<div class="widget-box">
									<div class="widget-header header-color-blue">
										<h5 class="bigger lighter">
											<i class="icon-table"></i>推荐搭配
										</h5>
									</div>

									<div class="widget-body">
										<div class="widget-main no-padding">
											<table id="table_bug_report"
												class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th class="center">商品标题</th>
														<th class="center">商品短标题</th>
														<th class="center">编码</th>
														<th class="center">价格</th>
														<th class="center">商品分类</th>
														<th class="center">操作</th>
													</tr>
												</thead>
												<tbody id="tjdp_tbody">

												</tbody>
											</table>
										</div>
									</div>
								</div>
			</div><!--/span-->
			
			<br/>
			<table id="table_report"
				class="table table-striped table-bordered table-hover">
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">新增属性:</td>
					<td id="XZSKU"><c:forEach items="${pd.pdabval}" var="var" varStatus="vs">
						<div style="width:20%">
							<input id="${var.A_ID }" name="SKUAtribute"
								onclick="attribute.add('${var.A_ID }','widget-main','sku_tbody')"
								class="ace-checkbox-2 SKUAtributeCheckbox"
								<c:forEach items="${pd.skuAtribute}" var="varc" varStatus="vsc">
									<c:if test="${fn:indexOf(varc.A_ID,var.A_ID) != -1 }"> checked="checked"</c:if>
								</c:forEach>
								<%-- <c:if test="${fn:indexOf(pd.SPREAD5,var.A_ID) != -1 }"> checked="checked"</c:if> --%>
								value="${var.A_ID }" c_name="${var.A_NAME }" type="checkbox" />
							<span class="lbl"> ${var.A_NAME }</span>
						</div>
						</c:forEach>
						 
						</td>
				</tr>
				<tr>
					
				</tr>
			</table>
			
			<!--  动态属性管理 开始 -->
			<div>
				<div>
					<div class="widget-box">
						<div class="widget-header">
							<h4>属性管理</h4>
							<span class="widget-toolbar"> <a href="#"
								data-action="collapse"><i class="icon-chevron-up"></i></a> <a
								href="#" data-action="close"><i class="icon-remove"></i></a>
							</span>
						</div>
						<div class="widget-body">
							<div class="widget-main" id="widget-main"></div>
							<div>
								<div class="widget-box">
									<div class="widget-header header-color-blue">
										<h5 class="bigger lighter">
											<i class="icon-table"></i> SKU
										</h5>
									</div>

									<div class="widget-body">
										<div class="widget-main no-padding">
											<table id="table_bug_report"
												class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th>规格</th>
														<th>价格</th>
														<th>编码</th>
														<th>库存</th>
														<th>操作</th>
													</tr>
												</thead>
												<tbody id="sku_tbody">

												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<!--/span-->
							<!-- <div class="form-actions center">
								<button onclick="return false;"
									class="btn btn-small btn-success">
									确认 <i class="icon-arrow-right icon-on-right"></i>
								</button>
							</div> -->
						</div>


					</div>

				</div>
				<!--/span-->

			</div>


			<!--  动态属性管理 结束 -->
			<div
				style="text-align: center; padding: 0px 0px 15px 0px; display: none;">
				<a class="btn btn-mini btn-primary"
					onclick="attribute.add('table_report',1)">新增属性</a> <a
					class="btn btn-mini btn-primary" onclick="alert(1)">新增SKU属性</a>
			</div>
			<table class="table table-striped table-bordered table-hover">
				<textarea name="DETAIL" id="DETAIL" style="display: none">${pd.DETAIL}</textarea>
				<tr>
					<td id="nr"><script id="editor" type="text/plain"
							style="width: 930px; height: 259px;">${pd.DETAIL}</script></td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="10"><a
						class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
						class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
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
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<!-- 编辑框-->
	<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
	<script type="text/javascript" charset="utf-8"
		src="plugins/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8"
		src="plugins/ueditor/ueditor.all.js"></script>
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	<!-- 确认窗口 -->
	<!-- 圖片上傳 -->
	<script type="text/javascript" src="plugins/webuploader/webuploader.js"></script>
	<script type="text/javascript"
		src="plugins/webuploader/product_upload.js"></script>
	<!-- 下拉联动 -->
	<script type="text/javascript" src="plugins/yunm.combox.js"></script>

	<script type="text/javascript"
		src="plugins/myjs/bootstrap-typeahead.js"></script>
	<script type="text/javascript" src="plugins/myjs/attribute.js?2212223312"></script>
	<script type="text/javascript" src="static/js/jquery.colorbox-min.js"></script>
	<script type="text/javascript">

		var path='<%=path%>/'
		attribute.init('${pd.skuAtributeJson}','${pd.listSku}');
		$(top.hangge());
		setTimeout("ueditor()",500);
		function ueditor(){
			var ue = UE.getEditor('editor');
			var locat = (window.location+'').split('/'); 
			UE.Editor.prototype._bkGetActionUrl=UE.Editor.prototype.getActionUrl;
	        UE.Editor.prototype.getActionUrl=function(action){
	            if (action == 'uploadimage'){
	                return '<%=basePath%>/pictures/save.do';    /* 这里填上你自己的上传图片的action */
	            }else{
	                return this._bkGetActionUrl.call(this, action);
	            }
	        };
		}
		$(function() {
			 yxwCombox.init();
			 tjdpShuaXin();
			 initShuxinXgfl();
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();

			var colorbox_params = {
					reposition:true,
					scalePhotos:true,
					scrolling:false,
					previous:'<i class="icon-arrow-left"></i>',
					next:'<i class="icon-arrow-right"></i>',
					close:'&times;',
					current:'{current} of {total}',
					maxWidth:'100%',
					maxHeight:'100%',
					onOpen:function(){
						document.body.style.overflow = 'hidden';
					},
					onClosed:function(){
						document.body.style.overflow = 'auto';
					},
					onComplete:function(){
						$.colorbox.resize();
					}
			};
			$('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
			$(window).on('resize.colorbox', function() {
				try {
					$.fn.colorbox.load();//to redraw the current frame
				} catch(e){}
			});
			
		});
		
		function test(id,innerhtmlId){
			var $this =$('#'+id);
			var divID ='skuDivID'+ $this.val()
			attrbuteHtml = '<div class="row-fluid" id="'+divID+'">';
			attrbuteHtml += '<div class="widget-header header-color-grey">';
			attrbuteHtml += '<h5 class="bigger lighter">'+ $this.attr("c_name")+'</h5>';
			attrbuteHtml += '	</div>';
			attrbuteHtml += '</div>';
			attrbuteHtml += '	<hr />';
			console.log(attrbuteHtml)
			var dom = document.getElementById(innerhtmlId)
			if(! $this.is(":checked")){
				$("#"+divID).remove();
				return;
			}
			dom.innerHTML += attrbuteHtml;
		}

		function delImg(id){
			bootbox.confirm("确定要删除选中的图片吗?", function(result) {
				if(result) {
					
					$.ajax({
					type: "POST",
					url: '<%=basePath%>olopdcarousel/delete.do?tm=' + new Date().getTime(),
						data : {
							ID : id
						},
						cache : false,
						success : function(data) {
							$("#" + id).hide()
							bootbox.alert("删除成功!");
						}
					});
				}
			});

		}
		
		
		//保存
		function save(){
				$("#DETAIL").html(UE.getEditor('editor').getContent());
				/* if($("#SHORT_TITLE").val()==""){
				$("#SHORT_TITLE").tips({
					side:3,
		            msg:'请输入商品短标题',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SHORT_TITLE").focus();
				return false;
			}
			if($("#SUB_TITLE").val()==""){
				$("#SUB_TITLE").tips({
					side:3,
		            msg:'请输入副标题',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SUB_TITLE").focus();
				return false;
			}
			if($("#BRAND_NAME").val()==""){
				$("#BRAND_NAME").tips({
					side:3,
		            msg:'请输入品牌名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BRAND_NAME").focus();
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
			if($("#FLOOR_PRICE").val()==""){
				$("#FLOOR_PRICE").tips({
					side:3,
		            msg:'请输入底价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#FLOOR_PRICE").focus();
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
			if($("#COLOUR").val()==""){
				$("#COLOUR").tips({
					side:3,
		            msg:'请输入颜色',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COLOUR").focus();
				return false;
			}
			if($("#STORE").val()==""){
				$("#STORE").tips({
					side:3,
		            msg:'请输入库存',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STORE").focus();
				return false;
			}
			if($("#CATEGORY").val()==""){
				$("#CATEGORY").tips({
					side:3,
		            msg:'请输入商品分类(橱柜/款式/颜色)',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CATEGORY").focus();
				return false;
			}
			if($("#SUGGEST_PRICE").val()==""){
				$("#SUGGEST_PRICE").tips({
					side:3,
		            msg:'请输入商品指导价格',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SUGGEST_PRICE").focus();
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
			if($("#STORAGE").val()==""){
				$("#STORAGE").tips({
					side:3,
		            msg:'请输入仓库',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STORAGE").focus();
				return false;
			}
			if($("#LAST_DATE").val()==""){
				$("#LAST_DATE").tips({
					side:3,
		            msg:'请输入最后更新日期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#LAST_DATE").focus();
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
			if($("#DETAIL").val()==""){
				$("#DETAIL").tips({
					side:3,
		            msg:'请输入详情页',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DETAIL").focus();
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
			if($("#TITLE").val()==""){
				$("#TITLE").tips({
					side:3,
		            msg:'请输入商品标题',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TITLE").focus();
				return false;
			} */
			$("#SKU").val(attribute.getSkuValue());
			console.log(attribute.getSkuValue())
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		
		var _olopdproductdiag=null;
		//新增
		function addPdppval(){
				top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>olopdppval/goOtherAdd.do';
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.CancelEvent = function(){ //关闭事件
				 shuaxinSku()
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						top.jzts(); 
						//setTimeout("location.reload()",100);
					}
				diag.close();
			 };
			diag.show();
			_olopdproductdiag =  diag;
			 
		}
		
		function shuaxinSku(){
			
			$.ajax({
					type: "POST",
					url: '<%=basePath%>olopdproduct/getPdabval.do?tm=' + new Date().getTime(),
						data : {GOODS_ID:$('#GOODS_ID').val()},
						cache : false,
						success : function(data) {
							console.log(data)
							var temphtml='';
							var pdabval = data.pdabval;
							var skuAtributeJson =data.skuAtributeJson
							var checked =""
							for(var i=0;i<pdabval.length;i++){
								for(var j=0;j<skuAtributeJson.length;j++){
									if(skuAtributeJson[j].A_ID ==  pdabval[i].A_ID){
										checked = 'checked="checked"';
										break;
									}
								}
							
								 temphtml +='<input id="'+pdabval[i].A_ID+'" name="SKUAtribute" class="ace-checkbox-2 SKUAtributeCheckbox" '
								 temphtml +='	onclick="attribute.add(\''+pdabval[i].A_ID+'\',\'widget-main\',\'sku_tbody\')"'
								 temphtml +='	'+checked+'	 	value="'+pdabval[i].A_ID+'" c_name="'+pdabval[i].A_NAME+'" type="checkbox" />'
								 temphtml +='	<span class="lbl"> '+pdabval[i].A_NAME+'</span>';
								 checked ='';
							}
							$("#XZSKU").html(temphtml)
							setTimeout("attribute.init()",100);
						}
					});
			
		}
		
		
		//关联
		function tjdp(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="推荐搭配";
			 diag.URL = '<%=basePath%>olopdproduct/goTjsp.do?GOODS_ID='+Id;
			 diag.Width = 800;
			 diag.Height = 555;
			 diag.CancelEvent = function(){ //关闭事件
				 tjdpShuaXin()
				
				diag.close();
			 };
			 diag.show();
		}
		
		function tjdpShuaXin(){
			
			var template = '<tr id="%{CODE}tr">'
				template += '<td >%{TITLE} </td>'
				template += '<td>'
				template += ' %{SHORT_TITLE}'
				template += '</td>'
				template += '	<td>'
				template += '%{CODE}'
				template += '</td>'
				template += '<td >%{PRICE}</td>'
				template += '<td >%{CATEGORY}</td>'
				template += '<td style="width: 30px;" class="center" >';
				template += ' <a style="cursor:pointer;" title="解除推荐" onclick="javascript:jctj(\'%{GOODS_ID}\');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class=" icon-unlock"></i></span></a>'
				template += '</td>';
				template += '</tr>';
			
			$.ajax({
				type: "POST",
				url: '<%=basePath%>olopdproduct/listTjsp.do?tm=' + new Date().getTime(),
					data : {GOODS_ID:$('#GOODS_ID').val()},
					 dataType: 'json', //添加这一条语句
					cache : false,
					success : function(data) {
						var templateCopy = template;
						var str ="";
						for(var i=0;i<data.varList.length;i++){
							 str +=JsonTemplate(data.varList[i],template)
						}
						$("#tjdp_tbody").html(str)
					}
				});
		}
		/**
		* 解除推荐搭配
		**/
		function jctj(id){
			$.ajax({
				type: "POST",
				url: '<%=basePath%>olopdproduct/delTjdp.do?tm=' + new Date().getTime(),
					data : {GOODS_ID:$('#GOODS_ID').val(),O_ID2:id},
					cache : false,
					success : function(data) {
						tjdpShuaXin();
					}
				});
		}
		
		function JsonTemplate(json,template){
		  	//简单的注入模板
		  	for(var key in json){
		  		if(json[key] == 'null' ||json[key] == null){
		  			json[key] =''
		  		}
		        template = template.replace(new RegExp("%\\{"+key+"\\}",'gm'), json[key])
		        
		  	}
			return template;
		}
		
		/**
		* 新增相关分类
		**/
		function addXgfl(zid){
			var tbody = 'xgfl'+zid;
			var GOODS_ID= $('#GOODS_ID').val();
			top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增分类";
			 diag.URL = '<%=basePath%>/olopdprfile/goAdd.do?GOODS_ID='+GOODS_ID+'&MENU='+zid;
			 diag.Width = 500;
			 diag.Height = 355;
			 diag.CancelEvent = function(){ //关闭事件
				 shuxingXgfl(zid)
				diag.close();
			 };
			 diag.show();
		}
		function edtXgfl(FILE_ID,GOODS_ID,zid){
			var tbody = 'xgfl'+zid;
			var GOODS_ID= $('#GOODS_ID').val();
			top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增分类";
			 diag.URL = '<%=basePath%>olopdprfile/goEdit.do?FILE_ID='+FILE_ID+'&GOODS_ID='+GOODS_ID+'&MENU='+zid;
			 diag.Width = 500;
			 diag.Height = 355;
			 diag.CancelEvent = function(){ //关闭事件
				 shuxingXgfl(zid)
				diag.close();
			 };
			 diag.show();
		}
		function delXgfl(FILE_ID,GOODS_ID,zid){
			 bootbox.confirm("确定删除吗?", function(result) {
					if(result) {
						var url = '<%=basePath%>/olopdprfile/delete.do?FILE_ID='+FILE_ID+'&GOODS_ID='+GOODS_ID+'&MENU='+zid+'&tm='+new Date().getTime();
						$.get(url,function(data){
							 shuxingXgfl(zid)
						});
					}
				});
		}
		
		function initShuxinXgfl(){
			 $.each($('.xgflClass'),function(){
				 shuxingXgfl($(this).attr("id"));
	       });
		}
		/**
		* 刷新相关分类列表
		**/
		function shuxingXgfl(zid){
			var tbody = 'xgfl'+zid;
			var template = '<tr id="%{FILE_ID}tr" style="color:#000000">'
				template += '<td >%{SPREAD1} </td>'
				template += '<td>'
				template += ' %{FILETYPE}'
				template += '</td>'
				template += '	<td>'
				template += '%{SORTING}'
				template += '</td>'
				template += '<td >%{CREATE_TIME}</td>'
				template += '<td style="width: 30px;" class="center" >';
				template += '<div class="hidden-phone  btn-group">'
				template += '<div class="inline position-relative">';
				template += '<button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>';
				template += '<ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">';
				template += '<li><a style="cursor:pointer;" title="编辑" onclick="edtXgfl(\'%{FILE_ID}\',\'%{GOODS_ID}\',\'%{MENU}\');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>';
				template += '<li><a style="cursor:pointer;" title="删除" onclick="delXgfl(\'%{FILE_ID}\',\'%{GOODS_ID}\',\'%{MENU}\');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>';
				template += '</ul></div></div>';
				template += '</td>';
				template += '</tr>';
			
			$.ajax({
				type: "POST",
				url: '<%=basePath%>olopdprfile/getFiles.do?tm=' + new Date().getTime(),
					data : {GOODS_ID:$('#GOODS_ID').val(),MENU:zid},
					 dataType: 'json', //添加这一条语句
					cache : false,
					success : function(data) {
						var templateCopy = template;
						var str ="";
						for(var i=0;i<data.varList.length;i++){
							if(data.varList[i].FILETYPE == '0'){
								data.varList[i].FILETYPE = '图片';
							}
							if(data.varList[i].FILETYPE == '1'){
								data.varList[i].FILETYPE = '视频';
							}
							if(data.varList[i].FILETYPE == '2'){
								data.varList[i].FILETYPE = '文件';
							}
							if(data.varList[i].FILETYPE == '3'){
								data.varList[i].FILETYPE = '文本';
							}
							 str +=JsonTemplate(data.varList[i],template)
						}
						console.log(str);
						$("#"+tbody).html(str)
					}
				});
		}
	</script>
</body>
</html>