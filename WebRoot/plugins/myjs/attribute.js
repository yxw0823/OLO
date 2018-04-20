String.prototype.replaceAll = function (FindText, RepText) { 
	regExp = new RegExp(FindText, 'g'); 
	return this.replace(regExp, RepText); 
}
var template = '<tr id="${gz}tr">'
	template += '<td >${gzname} <input type="hidden" id="fjcs${gz}" class="SkuPlzhValuecs" value="${SkuPlzhValuecs}"/>  <input type="hidden" data_gzname="${gzname}" id="${gz}" class="SkuPlzhValue" value="${value}"/> </td>'
	template += '<td>'
	template += ' <input type="number" class="${gz}"  c_name="PRICE"  onblur="attribute.dataSorting(\'${gz}\')"  value="${PRICE}" maxlength="32" placeholder="这里输入价格" title="价格"/>'
	template += '</td>'
	template += '	<td>'
	template += '<input type="text" class="${gz}" c_name="PRODUCT_CODE" onblur="attribute.dataSorting(\'${gz}\')"  value="${PRODUCT_CODE}"  placeholder="这里输入编码" title="编码"/>'
	template += '</td>'
	template += '<td ><input type="number" class="${gz}"  c_name="STORE" onblur="attribute.dataSorting(\'${gz}\')"  value="${STORE}" maxlength="32" placeholder="这里输入库存" title="库存"/></td>'
	template += '<td ><a style="cursor:pointer;" title="删除SKU" onclick="javascript:attribute.del(\'${gz}tr\');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-trash"></i></span></a></td>'
			
	template += '</tr>';
var Base64 = {

    // private property
    _keyStr: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",

    // public method for encoding
    encode: function(input) {
        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;

        input = Base64._utf8_encode(input);

        while (i < input.length) {

            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);

            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;

            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }

            output = output + this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) + this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);

        }

        return output;
    },

    // public method for decoding
    decode: function(input) {
        var output = "";
        var chr1, chr2, chr3;
        var enc1, enc2, enc3, enc4;
        var i = 0;

        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

        while (i < input.length) {

            enc1 = this._keyStr.indexOf(input.charAt(i++));
            enc2 = this._keyStr.indexOf(input.charAt(i++));
            enc3 = this._keyStr.indexOf(input.charAt(i++));
            enc4 = this._keyStr.indexOf(input.charAt(i++));

            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;

            output = output + String.fromCharCode(chr1);

            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }

        }

        output = Base64._utf8_decode(output);

        return output;

    },

    // private method for UTF-8 encoding
    _utf8_encode: function(string) {
        string = string.replace(/\r\n/g, "\n");
        var utftext = "";

        for (var n = 0; n < string.length; n++) {

            var c = string.charCodeAt(n);

            if (c < 128) {
                utftext += String.fromCharCode(c);
            } else if ((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            } else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }

        }

        return utftext;
    },

    // private method for UTF-8 decoding
    _utf8_decode: function(utftext) {
        var string = "";
        var i = 0;
        var c = c1 = c2 = 0;

        while (i < utftext.length) {

            c = utftext.charCodeAt(i);

            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            } else if ((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i + 1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            } else {
                c2 = utftext.charCodeAt(i + 1);
                c3 = utftext.charCodeAt(i + 2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }

        }

        return string;
    }

}


var attribute = {
	_attributeInitParameter :"",//已经选中属性集合
	_isInit:false,
	_listSku:null,
	init:function(attributeInitParameter,listsku){
		if(attributeInitParameter !=null){
			this._attributeInitParameter = JSON.parse(attributeInitParameter);
		}
		if(listsku !=null){
			this._listSku= JSON.parse(listsku);
		}
		$('#widget-main').html("");//
		$('#sku_tbody').html("");//
		var $this = this;
		$.each($('.SKUAtributeCheckbox'),function(){
              if(this.checked){
            	  $this._isInit=true;
            	  $this.add($(this).val(),'widget-main','sku_tbody')
              }
          });
	
	
	},
	add : function(id,innerhtmlId,tablInerHtmlId) {
		var $this =$('#'+id);
		var divID ='skuDivID'+ $this.val()
		var divValueID ='skuValueDivID'+ $this.val()
		attrbuteHtml = '<div class="row-fluid" id="'+divID+'">';
		attrbuteHtml += '<div class="widget-header header-color-grey">';
		attrbuteHtml += '<h5 class="bigger lighter">'+ $this.attr("c_name")+'</h5>';
		attrbuteHtml += '	</div>';
		attrbuteHtml +=  '<div id="'+divValueID+'">'
		attrbuteHtml +=  '</div >'
		attrbuteHtml += '</div>';
		attrbuteHtml += '	<hr />';
		console.log(attrbuteHtml)
		var dom = document.getElementById(innerhtmlId)
		if(! $this.is(":checked")){
			$("#"+divID).remove();
			return;
		}
		dom.innerHTML += attrbuteHtml;
		this.autoComplete($this.val(),divValueID, $this.attr("c_name"),tablInerHtmlId);
	},
	autoComplete : function(id,innerhtmlId,o_name,tablInerHtmlId) {
		var $this =this;
		var children =[];
		for(var j =0;j<$this._attributeInitParameter.length;j++){
						if($this._attributeInitParameter[j].A_ID==id){
								children =$this._attributeInitParameter[j].children;
								break;
						}
					
		}	
			// 加载对应的属性
		$.ajax({
			type: "POST",
			url: path+'olopdppval/listAllByAID.do?tm='+new Date().getTime(),
	    	data: {O_ID1:id},
			cache: false,
			success: function(data){
				var listAll1 = data.listAll
				var html='';
				for(var i=0;i<listAll1.length;i++){
					var checstr='' 
					for(var j =0;j<children.length;j++){
						if(children[j].PROPERTY_ID ==listAll1[i].PROPERTY_ID ){
								checstr ='checked="checked"';
								break;
						}
						
					}	
					html+='<div style="width:15%; line-height:30px; float: left;  "><input  onclick="attribute.addSku(\''+tablInerHtmlId+'\')" '+checstr+' class="ace-checkbox-2  skuValClass" oid="'+id+'" o_name="'+o_name+'" value="'+listAll1[i].PROPERTY_ID+'" c_name="'+listAll1[i].PROPERTY_NAME+'"  type="checkbox" /><span class="lbl">'+listAll1[i].PROPERTY_NAME+'</span></div>';
				}


				$("#"+innerhtmlId).append(html);
				if($this._isInit){
    				$this.addSku(tablInerHtmlId);
    				//$this._isInit =false;
				} 
			}
		});
	},
	addSku:function(tablInerHtmlId){
		  
		  var json ={};
		  $.each($('.skuValClass'),function(){
              if(this.checked){
            	 
            	   var cjson ={};
        		   cjson.value=$(this).val()
        		   cjson.oid =$(this).attr("oid")
        		   cjson.oname =$(this).attr("o_name");
        		   cjson.c_name=$(this).attr("c_name");
            	   if(json[$(this).attr("oid")] !=null){
            		 
            		   json[$(this).attr("oid")][json[$(this).attr("oid")].length]=cjson;
            		   
            	   }else{
            		   json[$(this).attr("oid")]=[];
            		   json[$(this).attr("oid")][0]=cjson;
            	   }
              }
          });
		  // 排列组合
		  this.plzh(json,tablInerHtmlId)
	},
	del:function(id){
		$("#"+id).remove()
	},
	dataSorting :function(id){
		var cjson ={};
		 $.each($('.'+id),function(){
     		   cjson[$(this).attr("c_name")]=$(this).val();
         });
		 $('#fjcs'+id).val(Base64.encode(JSON.stringify(cjson)));
		 console.log($('#fjcs'+id).val())
	},
	getSkuValue:function(){
		var skuValueJson=[];
		var i=0;
		 $.each($('.SkuPlzhValue'),function(){
			 var json={}
			 json.Base64Value=$(this).val();
			 json.gzName=$(this).attr("data_gzname");
			 json.Base64id=$(this).attr("id");
			 json.Base64OtherAttributes = $("#fjcs"+$(this).attr("id")).val();
			 skuValueJson[i++]=json;
       });
		 return Base64.encode(JSON.stringify(skuValueJson))
	},
	plzh:function (json,tablInerHtmlId){
		var temparr=[]
		var p =0;

		for(var prop in json){
		// test1(json[prop])
			var tempJson = json[prop];
			var mycars=[]
			for(var i=0;i<tempJson.length;i++){
				mycars[i] =Base64.encode(JSON.stringify(tempJson[i]))
			}
			temparr[p++]  =mycars
		}
		 var ret = this.doExchange(temparr);  
		//  console.log("共有：" + ret.length + "种组合！<br/>");
		  $("#"+tablInerHtmlId).html("");
	    for (var i = 0; i < ret.length; i++) { 
	    	
	    	this.analysis(ret[i],tablInerHtmlId);
	    	
	    }  

},
sumAscll:function(a){
	var num =0;
	while(a.length!=0)
  {
        $a=a.substr(0,1);
        num+=$a.charCodeAt();
        a=a.substr(1,a.length);
  }
  return num;
},
analysis:function (str,tablInerHtmlId){

	
	/**
 	* 解析字符串
 	*/
	var strs = str.split(',');
	var gz='';
	for(var i =0 ;i< strs.length;i++){
		var temp =JSON.parse(Base64.decode(strs[i]));
		if(i == strs.length -1){
			gz += temp.c_name;
			break;
		}
		gz += temp.c_name+"+";
	}
	var obj= {};
	var flage =false;
	for(var i=0;i<this._listSku.length;i++){
		if(this.sumAscll(this._listSku[i].SPECIFICATIONS)==this.sumAscll(gz)){
			flage =true;
			obj =this._listSku[i];
			break;
		}
	}
	if(this._listSku !=null && this._listSku.length >0 && !flage&& !this._isInit){
		return;
	}
	if(!flage){
		obj.PRICE ='';
		obj.PRODUCT_CODE='';
		obj.STORE='';
	}
	var templateCopy = template;
	templateCopy =templateCopy.replaceAll('\\$\\\{gzname\\}', gz);
	templateCopy =templateCopy.replaceAll('\\$\\\{gz\\}', Base64.encode(gz).replaceAll("=","1").replaceAll("\\+","1").replaceAll("/","2"));
	templateCopy =templateCopy.replaceAll('\\$\\{value\\}', str);
	templateCopy =templateCopy.replaceAll('\\$\\{PRICE\\}', obj.PRICE);
	templateCopy =templateCopy.replaceAll('\\$\\{PRODUCT_CODE\\}', obj.PRODUCT_CODE);
	templateCopy =templateCopy.replaceAll('\\$\\{STORE\\}', obj.STORE);
	templateCopy =templateCopy.replaceAll('\\$\\{SkuPlzhValuecs\\}', obj.SPREAD2);
	$("#"+tablInerHtmlId).append(templateCopy);
},
doExchange:function (doubleArrays) {  
    var len = doubleArrays.length;  
    if (len >= 2) {  
        var len1 = doubleArrays[0].length;  
        var len2 = doubleArrays[1].length;  
        var newlen = len1 * len2;  
        var temp = new Array(newlen);  
        var index = 0;  
        for (var i = 0; i < len1; i++) {  
            for (var j = 0; j < len2; j++) {  
                temp[index] = doubleArrays[0][i] + "," +doubleArrays[1][j];  
                index++;  
            }  
        }  
        var newArray = new Array(len- 1);  
        newArray[0] = temp;  
        if (len > 2) {  
           var _count = 1;  
           for(var i=2;i<len;i++)  
           {  
               newArray[_count] = doubleArrays[i];  
               _count ++;  
           }  
        }                          
        return this.doExchange(newArray);  
    }  
    else {  
        return doubleArrays[0];  
    }  
}  
	
}



/*var tempValue ='';
$(".sorting").focus(function(){
	tempValue = this.value;
	
}).blur(function(){
	if(tempValue == this.value || this.value==''){
		return;
	}
	$.ajax({
		type: "POST",
		url: '<%=basePath%>olopdproduct/editSorting.do?tm='+new Date().getTime(),
    	data: {GOODS_ID:this.id,SORTING:this.value},
		cache: false,
		success: function(data){
		}
	})
})*/








