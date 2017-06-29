(function($){
	$.fn._$_ = {
		_id: "_ajax_status",
		_info: "操作中,请稍候...",
		_t: function(){
			return '<img style="MARGIN: 3px" height="50" src="./images/wait.gif" width="50" align="left"/>'
		}
	};
	$.fn.element = [];
	$.fn._div_$_id = "_ajax_div_";
	$.fn._run_$ = false;
	$.fn.$_bg_div = null;
	$.fn.$_msg_div = null;
$.extend({
			ajaxShow:function(_top,_left,position,info){
				var position = position || "absolute";
				var $div = $("#"+$.fn._$_._id);
				if(null == $div.html()){
					$div = document.createElement("div");
					$div.id = $.fn._$_._id;
					document.body.appendChild($div);
					$div = $("#"+$.fn._$_._id);
					//$div.attr("id",$.fn._$_._id);
					$div.css("position",position);
					$div.css("display","block");
					$div.css("top",_top);
					$div.css("left",_left);
					$div.css("zIndex","20001");
					$div.append($.fn._$_._t());
					//$(document.body).append($div);
				}else{
					$div.css("top",_top);
					$div.css("left",_left);
				}
				//var elements = $("input,select",document.body);
				var inputEls = document.getElementsByTagName("input");
				var selectEls = document.getElementsByTagName("select");
				
				for(var i=0;i<inputEls.length;i++){
					var element = inputEls[i];
					var disabled = element.disabled;
					if(disabled == 'undefined' ||!disabled){
						element.disabled = true;
						$.fn.element[$.fn.element.length] = element;
					}
				}
				for(var i=0;i<selectEls.length;i++){
					var element = selectEls[i];
					var disabled = element.disabled;
					if(disabled == 'undefined' ||!disabled){
						element.disabled = true;
						$.fn.element[$.fn.element.length] = element;
					}
				}
				//$($_div_$).show();
				//$("#"+$.fn._$_._id).show();
			},
			ajaxHide:function(){
				var $div = $("#"+$.fn._$_._id);
				$div.css("display","none");
			},
			showBgDiv:function(innerHTML,width,height,_top,_left){
			//背景层（大小与窗口有效区域相同，即当弹出对话框时，背景显示为放射状透明灰色）
			  var top = _top || "20%";
			  var left = _left || "20%";
			  $.fn.$_bg_div=document.createElement("div");//创建一个div对象（背景层）
			  var bgAttr = $.fn.$_bg_div.style.setAttribute;
			  bgAttr('id','bgDiv');
			  bgAttr('position','absolute');
			  bgAttr('top','0');
			  bgAttr('background','#777');
			  bgAttr('filter','progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75');
			  bgAttr('opacity','0.6');
			  bgAttr('left','0');
			  bgAttr('width','100%');
			  bgAttr('height','100%');
			  bgAttr('zIndex','10000');		  
			  var frm = document.createElement("iframe");
			  var frmAttr = frm.style.setAttribute;
			  frmAttr('position','absolute');
			  frmAttr('width','100%');
			  frmAttr('height','100%');
			  frmAttr('scroll','no');
			  frmAttr('background','#777');
			  frmAttr('filter','progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75');
			  frmAttr('opacity','0.6');
			  $.fn.$_bg_div.appendChild(frm);
			  document.body.appendChild($.fn.$_bg_div);//在body内添加该div对象	
			  
			  $.fn.$_msg_div = document.createElement("div");
			  var msgAttr = $.fn.$_msg_div.style.setAttribute;
			  msgAttr('id','msgDiv');
			  msgAttr('position','absolute');
			  msgAttr('top',top);
			  msgAttr('left',left);
			  msgAttr('background','#ffffff');
			  msgAttr("border","1px solid #336699");
			  msgAttr('width',width);
			  msgAttr('height',height);
			  msgAttr("textAlign", "center");
			  msgAttr("lineHeight","25px");
			  msgAttr('zIndex','10001');			
			  
			  var titleDiv=document.createElement("div");
			  var titleDivAttr = titleDiv.style.setAttribute;
			  //titleDiv.setAttribute("align","right");
			  titleDivAttr("id","msgTitle");
			  titleDivAttr("margin","0");
			  titleDivAttr("padding","3px");
			  titleDivAttr("background","#336699");
			  titleDivAttr("filter","progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);");
			  titleDivAttr("opacity","0.75");
			  titleDivAttr("border","1px solid #336699");
			  titleDivAttr("height","18px");
			  titleDivAttr("color","white");
			  titleDivAttr("textAlign","right");
			  
			  var closeT=document.createElement("span");
			  closeT.onclick = function(){
			  	document.body.removeChild($.fn.$_bg_div);//删除背景层Div
				document.body.removeChild($.fn.$_msg_div);//删除信息层Div
			  };
			  closeT.innerHTML="关闭";
			  var closeTAttr = closeT.style.setAttribute;
			  closeTAttr("cursor","pointer");
			  closeTAttr("font","12px Verdana, Geneva, Arial, Helvetica, sans-serif");
			  closeTAttr("align","right");
			  
			  titleDiv.appendChild(closeT);
			  $.fn.$_msg_div.appendChild(titleDiv);
			  var div =  document.createElement("div");
			  div.innerHTML = innerHTML;
			  $.fn.$_msg_div.appendChild(div);
			 //$.fn.$_msg_div.appendChild(document.getElementById(divId));
			  document.body.appendChild($.fn.$_msg_div);//在body内添加该div对象	
		},
		hideBgDiv:function(){
			document.body.removeChild($.fn.$_bg_div);//删除背景层Div
			document.body.removeChild($.fn.$_msg_div);//删除msg层Div
		}
	});
	
	$.fn.optionToArray = function(option){
		var v = [];
		this.find("option").each(
			function()
			{
				var obj_ = new Object();
				eval("obj_."+option.value+"='"+this.value+"'");
				eval("obj_."+option.text+"='"+this.text+"'");
				v[v.length] = obj_;
			}
		);
		return v;
	};
})(jQuery);

var xmlHttp = null;
function createXMLHttpRequest() {
	if(null == xmlHttp){
		if (window.XMLHttpRequest) {//Mozilla浏览器 	
			xmlHttp = new XMLHttpRequest();
	  	}else {   		
	  		if (window.ActiveXObject) {// IE浏览器
	    		try {
	  				xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
	  			}
	  			catch (e) {
	  				try {
	  				 	xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	  				}
	          catch (e) {
	          }
	        }
	      }
		}
		if (xmlHttp.overrideMimeType) {
			xmlHttp.overrideMimeType("text/xml");
		}   
		if (!xmlHttp) {
			return null;
		}
	}	
	return xmlHttp;
}
var _satusBar = {top:"160",left:"40%",info:"操作中,请稍候..."};

function showDownLoadStatus(request,statusBar,flag,$j){
	var xmlHttp = createXMLHttpRequest();
	if(null == xmlHttp){
		window.alert("您的浏览器不支持ajax,请检查！");
		return;
	}
	if(!request) return;
	var $statusBar = statusBar || _satusBar;
	var $top = $statusBar.top || _satusBar.top;
	var $left = $statusBar.left || _satusBar.left;
	var $info = $statusBar.info || _satusBar.info;	
	var $flag = flag || false;
	var jQuery = $j || $;
	var type = request.type || 'POST';
	var sURL = request.url+'?'+request.params;
	if(!$flag) jQuery.ajaxShow($top,$left,null,$info);
	xmlHttp.open(type,sURL,true);
	if('POST' == type)
		xmlHttp.setRequestHeader("content-type","application/x-www-form-urlencoded");
	else ;
	xmlHttp.onreadystatechange= function(){
		if(xmlHttp.readyState == 4){
			if(xmlHttp.status == 200){
				var ret = xmlHttp.responseText.replace(/(^\s*)|(\s*$)/g, "");
				if(ret.indexOf("true")>=0){
					jQuery.ajaxHide();
				}else{
					showDownLoadStatus(request,statusBar,true,jQuery);
				}
			}
		}
	};
	xmlHttp.send("");
}