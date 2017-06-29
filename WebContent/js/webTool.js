/**
 * -------前台js帮助类---------
 * 1.弹出窗口
 * openWindow(herf,width,height);
 * 2.转换json
 * strToJson(str);
 * 3.弹出详细内容
 * alertAllPrpos(obj);
 * 4.判断浏览器类型
 * getOs()
 * 5.绑定文本框输入事件
 * txtOnChange(id,fun)
 */


//用于打开对话框
function openWindow(herf,width,height){
	var sFeatures = "dialogHeight:"+height+"px;dialogWidth:"+width+"px;"
								+ "center:yes;resizable:no;scroll:no;status:no;edge:sunken;unadorned:no;help:no";
	window.open(herf,window,sFeatures);
}

//转换json
function strToJson(str){
	//str = str.replace(/\r\n/ig,"\\r\\n");
	var json = eval('(' + str + ')');
	return json;
}
//弹出详细内容
function alertAllPrpos(obj) {
    // 用来保存所有的属性名称和值
    var props = "";
    // 开始遍历
    for(var p in obj){
        // 方法
        if(typeof(obj[p])=="function"){
            //obj[p]();
        }else{
            // p 为属性名称，obj[p]为对应属性的值
            props += p + "=" + obj[p] + ";  ";
        }
    }
    // 最后显示所有的属性
    alert(props);
 }
function getOs(){//判断浏览器类型
    var OsObject = "";
   if(navigator.userAgent.indexOf("MSIE")>0) {
        return "MSIE";
   }
   if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){
        return "Firefox";
   }
   if(isSafari=navigator.userAgent.indexOf("Safari")>0) {
        return "Safari";
   }
   if(isCamino=navigator.userAgent.indexOf("Camino")>0){
        return "Camino";
   }
   if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){
        return "Gecko";
   }
}
//绑定文本框输入事件
function txtOnChange(id,fun){
	if(getOs()=="MSIE"){
		document.getElementById(id).attachEvent("onpropertychange",fun);
	}else{
  		document.getElementById(id).addEventListener("input",fun,false);
	}
}
