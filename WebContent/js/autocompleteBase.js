/**
 * 联想功能公共函数
 * 需要在页面引入jquery+jquery-ui-autocomplete.js
 * @param objId 需要联想的输入框id
 * @param service 后台响应方法: 如/app/getTravelAgency
 * @param imgId 滚动图id: 默认objId+_img
 * @param keyId  联想功能返回值id: 默认objId+_key
 * @param backFun  回调功能
 */
function autoComplete(objId,service,backFun,imgId,keyId){
	if(!$("#"+objId)){
		alert("联想功能公共函数找不到对应的ID:"+objId);
		return;
	}
	if(!imgId){
		imgId = objId+"_img";
	}
	if(!keyId){
		keyId = objId+"_key";
	}
	var objPa = $("#"+objId).parent();
	objPa.css("position","absolute");
	objPa.css("border","0px");
	//旅行社
	$("#"+objId).autocomplete({
		source:service
		,minLength: 1
		,delay:500
		,appendTo:objPa
		,select:function(e, ui) {
			if(!ui.item.id==false){
				$('#'+keyId).val(ui.item.id);
			}
			if(backFun){
				backFun(ui.item.id);
			}
		},
		search: function() {
			$('#'+imgId).show();
		},
		open:function(){
			//联想框宽度
			var list = ['200','300'];
			$('.ui-autocomplete').each(function(i){
				$(this).css('width',list[i]);
			});
		},
		funBack:function(){
			$('#'+imgId).hide();
		}
 	});
}
