/**
 * -------ǰ̨js������---------
 * 1.��������
 * openWindow(herf,width,height);
 * 2.ת��json
 * strToJson(str);
 * 3.������ϸ����
 * alertAllPrpos(obj);
 * 4.�ж����������
 * getOs()
 * 5.���ı��������¼�
 * txtOnChange(id,fun)
 */


//���ڴ򿪶Ի���
function openWindow(herf,width,height){
	var sFeatures = "dialogHeight:"+height+"px;dialogWidth:"+width+"px;"
								+ "center:yes;resizable:no;scroll:no;status:no;edge:sunken;unadorned:no;help:no";
	window.open(herf,window,sFeatures);
}

//ת��json
function strToJson(str){
	//str = str.replace(/\r\n/ig,"\\r\\n");
	var json = eval('(' + str + ')');
	return json;
}
//������ϸ����
function alertAllPrpos(obj) {
    // �����������е��������ƺ�ֵ
    var props = "";
    // ��ʼ����
    for(var p in obj){
        // ����
        if(typeof(obj[p])=="function"){
            //obj[p]();
        }else{
            // p Ϊ�������ƣ�obj[p]Ϊ��Ӧ���Ե�ֵ
            props += p + "=" + obj[p] + ";  ";
        }
    }
    // �����ʾ���е�����
    alert(props);
 }
function getOs(){//�ж����������
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
//���ı��������¼�
function txtOnChange(id,fun){
	if(getOs()=="MSIE"){
		document.getElementById(id).attachEvent("onpropertychange",fun);
	}else{
  		document.getElementById(id).addEventListener("input",fun,false);
	}
}
