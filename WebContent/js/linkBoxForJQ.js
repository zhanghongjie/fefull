	//级联控件
	//2012-09-29
	//cyw

	//级联对象 要展示的div 控件名数据 宽度
	//init 初始化方法
	//creat 创建方法
	//setData 设置数据 index第几个控件，键值对数组对象
	//setCallBack 设置回调函数
	//getData 得到数据

	//级联对象 要展示的div 控件名数据 宽度 控件排列类型：1普通横向排列 2 紧密横向排列 3. 紧密坚竹炭排列
	function LinkBox(div,title,width,type){
		if(div==null || title==null || title.length<=0){
			alert('setLinkBox-传入参数错误！');
			return false;
		}
		this._div = div;//要展示的div
		this._title = title//控件名数据
		this._width = width;//宽度
		this._type = type;//排列类型
		this._idArray = new Array();//select控件 id数组
		this._idObjArray = new Array();//select控件对象数组
		this._callback = function(){alert("未定义回调函数！")};
		this._keyName = "key";
		this._valueName = "value";
	};
	//初始化方法
	LinkBox.prototype.init = function(){
		if(this._width==null){
			this._width='100px';
		}
		this._divObj = $('#'+this._div);
	}

	//数据适配，默认为key:value
	LinkBox.prototype.dataAdapter = function(key,value){
		this._keyName = key;
		this._valueName = value;
	}

	//初始化数据，可选择置灰前几级
	LinkBox.prototype.initDate = function(list,num){
		 if(list!=null && list.length>0){
			for(var i=0,len=list.length;i<len;i++){
				this.setData(i,list[i]);
				this.selectDate(i,1);
				if(i<num){
					if(document.getElementById(this._idArray[i])!=null){
						document.getElementById(this._idArray[i]).disabled = "disabled";
					}
				}
			}
		 }
	}
	//
	LinkBox.prototype.selectDate = function(objIndex,index){
		//alert(objIndex);
		if($('#'+this._idArray[objIndex])!=null && $('#'+this._idArray[objIndex]).get(0)!=null && $('#'+this._idArray[objIndex]).get(0).selectedIndex!=null){
			$('#'+this._idArray[objIndex]).get(0).selectedIndex = index;
		}
	}
	//创建方法
	LinkBox.prototype.creat = function(){
		if(this._type==null || this._type=='' ||this._type==1){//普通横向排列
			this._divObj.append('<table id=linkBox ><tr id=linkBox_aim_'+this._div+'_td></tr><table>');
			var disabled;
			for(var i=0;i<this._title.length;i++){
				if(i==0){
					disabled = "";
				}else{
					disabled = "";
					//disabled = "disabled='disabled'";
				}
				this._idObjArray.push($('#linkBox_'+this._div+i+'_id'));
				this._idArray.push('linkBox_'+this._div+i+'_id');
				$('#linkBox_aim_'+this._div+'_td')
				.append('<td>'+this._title[i]+'<select id=linkBox_'+this._div+i+'_id style=width:'+this._width+' '+disabled+'><option>    ---请选择---</option></select></td>');
			}
		}else if(this._type==2 || this._type==3){//普通紧密排列,坚向排列
			this._divObj.append('<ul id=linkBox_aim_'+this._div+'_td style="list-style-type:none;"><ul>');
			var floatType = '';
			if(this._type==2){
				floatType='style=float:left';
			}
			var disabled;
			for(var i=0;i<this._title.length;i++){
				this._idObjArray.push($('#linkBox_'+this._div+i+'_id'));
				this._idArray.push('linkBox_'+this._div+i+'_id');
				$('#linkBox_aim_'+this._div+'_td')
				.append('<li '+floatType+'>'+this._title[i]+'<select id=linkBox_'+this._div+i+'_id style=width:'+this._width+' '+disabled+'><option value="-999999">    ---请选择---</option></select></li>');
			}
		}
		for (var i=0,length=this._idArray.length; i < length-1; i++){
			$("#" + this._idArray[i]).bind("change", {id:$("#" + this._idArray[i]),index:i,obj:this},this._onChange);
		}

	}
	//设置数据 index第几个控件，键值对数组对象
	LinkBox.prototype.setData = function(index,map){
		var selectObject = $('#'+this._idArray[index]);
		this._delData(selectObject);
		this._addData(selectObject,map);
	}
	//设置回调函数
	LinkBox.prototype.setCallBack = function(fun){
		this._callback = fun;
	}
	//增加数据
	LinkBox.prototype._addData = function(selectObject,map){
		for(var i=0,length=map.length;i<length;i++){
			selectObject.append('<option value='+map[i][this._keyName]+'>'+map[i][this._valueName]+'</option>');
		}
	}
	//删除数据
	LinkBox.prototype._delData = function(selectObject){
		selectObject.empty();
		selectObject.append('<option value="-999999">---请选择---</option>');
	}
	//清空选择值
	LinkBox.prototype.reValue = function(index){
		if(index >=this._idArray.length){
			alert("传入的参数大于下拉控件的总数！");
			return;
		}
		//选中第一个值
		$('#'+this._idArray[index-1]).get(0).selectedIndex=0;
	}
	//清空
	LinkBox.prototype.clear = function(index){
		if(index >=this._idArray.length){
			alert("传入的参数大于下拉控件的总数！");
			return;
		}
		this._delData($('#'+this._idArray[index]));
	}
	//控件改变选项事件
	LinkBox.prototype._onChange = function(e){
		//this._callback = function(){alert(1)};
		if(e.data.obj._callback==null){
			alert("请设置下拉框ocChange事件的回调函数！");
			return false;
		}
		//清空节点后的值 this._idObjArray
		for(var i=e.data.index+1,length=e.data.obj._idArray.length;i<length;i++){
			//父结点为请选择，或者不是直接子节点则置灰
			if(i-e.data.index==1 && $('#'+e.data.obj._idArray[i-1]).get(0).selectedIndex !=0){
				document.getElementById(e.data.obj._idArray[i]).disabled = "";
			}else{
				document.getElementById(e.data.obj._idArray[i]).disabled = "disabled";
			}
			//清空
			e.data.obj._delData($('#'+e.data.obj._idArray[i]));
		}
		//选中请选择时不调用回调函数
		if(e.data.id.val()=='-999999'){
			return;
		}
		//向回调函数传当前select控件的选中的节点值
		e.data.obj._callback(e.data.id.val(),parseInt(e.data.index)+1);
	}
	//得到数据
	LinkBox.prototype.getData = function(){
		for(var i=this._idArray.length-1;i>=0;i--){
			//alert($('#'+this._idArray[i]).attr("disabled")!='disabled');
			if($('#'+this._idArray[i]).get(0).selectedIndex !=0 && $('#'+this._idArray[i]).attr("disabled")!='disabled'){
				return $('#'+this._idArray[i]).val();
				//return $('#'+this.obj._idArray[i]).get(0).selectedIndex;
			}
		}
		return '';
	}
	LinkBox.prototype.setSelectItem = function(index,value){
		$("#"+this._idArray[index]+" option[value='"+value+"']").attr("selected","true")
	}
	//得到第几个控件选中值
	LinkBox.prototype.getLevel = function(){
		for(var i=this._idArray.length-1;i>=0;i--){
			if($('#'+this._idArray[i]).get(0).selectedIndex !=0){
				return i+1;
				//return $('#'+this.obj._idArray[i]).get(0).selectedIndex;
			}
		}
		return '';
	}
