//重置form表单数据
function _reset(formId){
	$(':input','#'+formId) 
	.not(':button, :submit, :reset, :hidden') 
	.val('') 
	.removeAttr('checked') 
	.removeAttr('selected');
}

//设置form表单为disabled
function _disabled(formId){
	$(':input','#'+formId).attr("disabled", true);
}