(function($) {
	/*var _onchange = function(event) {
		var $ref = $("#" + event.data.ref);
		if(event.data.ref =='two_select'){
			 $("#three_select").empty();
			 $("#four_select" ).empty();
		}
		if(event.data.ref =='three_select'){
			 $("#four_select" ).empty();		
		}
		if ($ref.size() == 0)
			return false;

		var refUrl = event.data.refUrl;
		
		var value = encodeURIComponent(event.data.$this.val());
		if(refUrl.indexOf("queryMenua") == -1  && (value =='null' || null == value || '' == value || ''== refUrl)){
			return false
		}
		//YUNM.debug(value);

		$.ajax({
			type : 'POST',
			dataType : "json",
			url : refUrl.replace("{value}", value),
			cache : false,
			data : {},
			success : function(response) {
				$ref.empty();
				 addHtml(response, $ref);
				// $ref.trigger('change').combox()
			},
			 error: function(XMLHttpRequest, textStatus, errorThrown) {
				 console.log(XMLHttpRequest.status);
				 console.log(XMLHttpRequest.readyState);
				 console.log(textStatus);
			}
		});

	};
	var addHtml = function(response, $this) {
		var json ={};
		 try {
			 json=  JSON.parse(response);
		    } catch (e) {
		    	json =response;
		    }
		
		if (!json)
			return;
		json = json.result
		var html = '';
		if(json.length >0){
			html = '<option >请选择</option>';
		}
		
		$.each(json, function(i) {
			if (json[i]) {

				html += '<option value="' +json[i].M_NAME+"," + json[i].ID + '"';

				if (json[i].selected) {
					html += ' selected="' + json[i].M_NAME;
				}

				html += '">' + json[i].M_NAME + '</option>';
			}
		});

		$this.html(html);
		//$this.trigger("change").combox();
	};*/

	$.extend($.fn, {
		combox : function() {
			return /*this.each(function(i) {
				var $this = $(this);

				var value = $this.val() || '';
				var ref = $this.attr("ref");

				var refUrl = $this.attr("refUrl") || "";
				var value =encodeURIComponent(value);
				if (refUrl) {
					refUrl = refUrl.replace("{value}", encodeURIComponent(value));
				}
				if(refUrl.indexOf("queryMenua") == -1  && (value =='null' || null == value || '' == value || ''== refUrl)){
					return false
				}
				if (refUrl) {
					$.ajax({
						type : 'POST',
						dataType : "json",
						url : refUrl,
						cache : false,
						data : {},
						success : function(response) {
							addHtml(response, $this);

							if (ref && $this.attr("refUrl")) {
								$this.unbind("change", _onchange).bind("change", {
									ref : ref,
									refUrl : $this.attr("refUrl"),
									$this : $this,
								}, _onchange).trigger("change");
							}
						},
						 error: function(XMLHttpRequest, textStatus, errorThrown) {
							 console.log(XMLHttpRequest.status);
							 console.log(XMLHttpRequest.readyState);
							 console.log(textStatus);
						}
					});
				}

			});*/
		}
	});
})(jQuery);


var yxwCombox = {
		 /**
		  * 选中的控件的值
		  * @param strs
		  */
		 init:function(){
		 	//获取所有class为combox的select选中框
			var selects= this.getClass('select','combox');
			var FID='';
			var value = ''
			var onValue=''
			for (var i =0; i < selects.length; i++) {
				var select =selects[i]
				
				if(select.attributes['val']){
					value =select.attributes['val'].value
					if(i==0){
						onValue =value;
					}
				}

				if(value ==''|| value =='请选择'){
					if(select.attributes['ref'] &&select.attributes['refUrl']){
							var e = $(select)
							 e.unbind("change", this._onchange).bind("change", {
								ref : e.attr("ref"),
								refUrl : e.attr("refUrl"),
								$this :e,
							}, this._onchange);
							//select.addEventListener("change",this._onchange.bind(select),false);  
					}
					
					continue;
				}
				//console.log(value);

				var values = value.split(",");
				var id = values[1];
				var text= values[0];
				this.combox(id,select,FID)
				FID =id;

			}
			if ($.fn.combox && value =="" && FID =="" ) {
				this.combox('',selects[0],'')
		 	}else{
		 		if( onValue==""){
		 			this.combox('',selects[0],'')
				}
		 	}
			

		},
		 _onchange :function(event) {
			var $ref = $("#" + event.data.ref);
			if(event.data.ref =='two_select'){
				 $("#three_select").empty();
				 $("#four_select" ).empty();
			}
			if(event.data.ref =='three_select'){
				 $("#four_select" ).empty();		
			}
			if ($ref.size() == 0)
				return false;

			var refUrl = event.data.refUrl;
			
			var value = encodeURIComponent(event.data.$this.val());
			var _this = this;
			$.ajax({
				type : 'POST',
				dataType : "json",
				url : refUrl.replace("{value}", value),
				cache : false,
				data : {},
				success : function(response) {
					$ref.empty();
					yxwCombox._addHtml(response, $ref);
					// $ref.trigger('change').combox()
				},
				 error: function(XMLHttpRequest, textStatus, errorThrown) {
					 console.log(XMLHttpRequest.status);
					 console.log(XMLHttpRequest.readyState);
					 console.log(textStatus);
				}
			});

		},
		 _addHtml : function(response, $this) {
		var json ={};
		 try {
			 json=  JSON.parse(response);
		    } catch (e) {
		    	json =response;
		    }
		
		if (!json)
			return;
		json = json.result
		var html = '';
		if(json.length >0){
			html = '<option >请选择</option>';
		}
		
		$.each(json, function(i) {
			if (json[i]) {

				html += '<option value="' +json[i].M_NAME+"," + json[i].CID + '"';

				/*if (json[i].selected) {
					html += ' selected="' + json[i].M_NAME;
				}*/

				html += '">' + json[i].M_NAME + '</option>';
			}
		});

		$this.html(html);
		//$this.trigger("change").combox();
		},
		getClass: function (tagName,className) //获得标签名为tagName,类名className的元素 
     	{ 
	        if(document.getElementsByClassName) //支持这个函数 
	        {    return document.getElementsByClassName(className); 
	        } 
	        else 
	        {    var tags=document.getElementsByTagName(tagName);//获取标签 
	          var tagArr=[];//用于返回类名为className的元素 
	          for(var i=0;i < tags.length; i++) 
	          { 
	            if(tags[i].class == className) 
	            { 
	              tagArr[tagArr.length] = tags[i];//保存满足条件的元素 
	            } 
	          } 
	          return tagArr; 
	        } 
        },
       combox : function(id,e,fid) {
				var $this = e;
				var ref  =null;
				try{

					 ref = $this.attributes["ref"].value;
				}catch(e){


				}
				

				var refUrl = $this.attributes["refUrl"].value || "";
				var _refUrl = refUrl;
				var value =encodeURIComponent(fid);
				if (refUrl) {
					refUrl = refUrl.replace("{value}", encodeURIComponent(fid));
					if(refUrl.indexOf("queryIDMFid")!=-1){
							refUrl = refUrl.substring(0,refUrl.lastIndexOf('&'));
					}
				
					console.log(refUrl)
				}

				var _this = this;
				if (refUrl) {
					$.ajax({
						type : 'POST',
						dataType : "json",
						url : refUrl,
						cache : false,
						data : {},
						success : function(response) {
							_this.addHtml(response, $this,id,ref,_refUrl);
						},
						 error: function(XMLHttpRequest, textStatus, errorThrown) {
							 console.log(XMLHttpRequest.status);
							 console.log(XMLHttpRequest.readyState);
							 console.log(textStatus);
						}
					});
				}
		},
		addHtml:function(response, $this,id,ref,_refUrl) {
		var json ={};
		 try {
			 json=  JSON.parse(response);
		    } catch (e) {
		    	json =response;
		    }
		
		if (!json)
			return;
		json = json.result
		var html = '';
		if(json.length >0){
			html = '<option >请选择</option>';
		}
		
		$.each(json, function(i) {
			if (json[i]) {

				html += '<option value="' +json[i].M_NAME+"," + json[i].CID + '"';

				if (json[i].CID ==id) {
					html += ' selected="selected' ;
				}

				html += '">' + json[i].M_NAME + '</option>';
			}
		});
		$this.innerHTML=html;
	
		//$this.addEventListener("change",this._onchange.bind($this),false);  
		var e = $($this)
		e.unbind("change", this._onchange).bind("change", {
			ref : ref,
			refUrl : e.attr("refUrl"),
			$this :e,
		}, this._onchange);
		/*$($this).unbind("change", _onchange).bind("change", {
			ref : ref,
			refUrl : $this.attr("refUrl"),
			$this : $this,
		}, _onchange).trigger("change");*/
		//$this.trigger("change").combox();
	}

}
