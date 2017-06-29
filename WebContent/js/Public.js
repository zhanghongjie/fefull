//父页面刷新按钮ID
var refreshBtnId = "lbtnSearch";
//父页面iframeID
var parentIFId = null;

///调用center页面中MdiWin.js中的方法
function refreshParentWin() {
    //保存当前页面父窗体IFRAMid
    parentIFId = getQueryString("parentId");
    if (refreshBtnId != null && parentIFId != null) {
        parent.refreshWin(parentIFId, refreshBtnId);
    }

}




//js获取url参数值
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}


//==========================================================================================
//
// 代码描述：显示当前日期及星期几
//
// 返回参数：字符
//
// 修改记录：姓名                日期                 内容
//           chensheng           2006/07/25           创建
//
//==========================================================================================
function ShowDateTime() {
    var enabled = 0; today = new Date();
    var day; var date;
    if (today.getDay() == 0) day = "星期天"
    if (today.getDay() == 1) day = "星期一"
    if (today.getDay() == 2) day = "星期二"
    if (today.getDay() == 3) day = "星期三"
    if (today.getDay() == 4) day = "星期四"
    if (today.getDay() == 5) day = "星期五"
    if (today.getDay() == 6) day = "星期六"
    document.fgColor = "000000";
    date = (today.getYear()) + '年' + (today.getMonth() + 1) + '月' + today.getDate() + '日 ' + day;
    document.write(date);
}
//==========================================================================================
//
// 代码描述：清除下拉菜单控件的所有数据
// ctrl：控件ID
//
// 修改记录：姓名                日期                 内容
//           chensheng           2006/07/28           创建
//
//==========================================================================================
function DropDownClear(ctrl) {
    var i;
    var len = ctrl.options.length;

    for (i = 0; i < len; i++) {
        ctrl.options.remove(0);
    }
}
//==========================================================================================
// 代码描述：用于在DataGrid控件里面实现全选、取消、至少选择一项。编写：chensheng
//==========================================================================================
function selectAll() {
    var len = document.Form1.elements.length;
    var i;
    for (i = 0; i < len; i++) {
        if (document.Form1.elements[i].type == "checkbox") {
            var sname = document.Form1.elements[i].name;
            //alert(sname);
            var cutname = sname.substr(sname.lastIndexOf(":") + 1);
            if (cutname == "cboxiID" || cutname == "cboxHotelID") {
                document.Form1.elements[i].checked = true;

                //setItemBackColor("item90",document.Form1.elements[i]);
                //alert(document.Form1.elements[i].checked);
            }
        }
    }
    //设置全选时所有行背景色变化
    var Curr_TR = document.all.tags("tr");
    for (var i = 1; i < Curr_TR.length; i++) {
        var itemid = Curr_TR[i].id;
        if (itemid.lastIndexOf("_") == -1 && itemid.substr(0, 4) == "item")
            Curr_TR[i].style.backgroundColor = "#999999";
    }
}

function unSelectAll() {
    var len = document.Form1.elements.length;
    var i;
    for (i = 0; i < len; i++) {
        if (document.Form1.elements[i].type == "checkbox") {
            var sname = document.Form1.elements[i].name;
            //alert(sname);
            var cutname = sname.substr(sname.lastIndexOf(":") + 1);
            if (cutname == "cboxiID" || cutname == "cboxHotelID")
                document.Form1.elements[i].checked = false;
        }
    }
    //设置取消时所有行背景色变化
    var Curr_TR = document.all.tags("tr");
    for (var i = 1; i < Curr_TR.length; i++) {
        var itemid = Curr_TR[i].id;
        if (itemid.lastIndexOf("_") == -1 && itemid.substr(0, 4) == "item")
            Curr_TR[i].style.backgroundColor = "";
    }
}
function selectOneAndMore(type, selectcount) {
    //alert(type);
    var len = document.Form1.elements.length;
    var i; j = 0
    for (i = 0; i < len; i++) {
        if (document.Form1.elements[i].type == "checkbox") {
            var sname = document.Form1.elements[i].name;
            //alert(sname);
            var cutname = sname.substr(sname.lastIndexOf(":") + 1);
            if (cutname == "cboxiID" || cutname == "cboxHotelID") {
                if (document.Form1.elements[i].checked == true)
                    j++;
            }
        }
    }
    if (j > 0) {
        if (selectcount == null) {
            if (j > 20) {
                alert("最多只能选择20项！");
                return false;
            }
            else {
                if (type == null)
                    return confirm('确定要做该操作吗?');
            }
        }
        else {
            if (j > parseInt(selectcount, 10)) {
                alert("最多只能选择" + selectcount + "项！");
                return false;
            }
            else {
                if (type == null)
                    return confirm('确定要做该操作吗?');
            }
        }
    }
    else {
        alert("请至少选择一项！");
        return false
    }
}
//=======================================================================================================================================
// 代码描述：显示表格的单击事件后的子页内容，sid 参数值，strURL 页面URL及参数名：test.aspx?i_user_id=，编写：chensheng
//=======================================================================================================================================
var RootPath = "/PNTour";
var syspath = "/PNTour";
function ViewClick() {
    var i, strURL, sid;
    for (i = 0; i < arguments.length; i++) {
        if (i == 0) { strURL = arguments[0]; }
        if (i == 1) { sid = arguments[1]; }
    }
    //alert("strURL="+strURL+ "sid=" +sid);
    //alert(strURL);
    var str, trdisplay, mytdname, mytrname, myimgname;
    mytdname = "item" + sid + "_newtd";
    mytrname = "item" + sid + "_newtr";
    myimgname = "item" + sid + "_newimg";

    try {
        trdisplay = document.all("item" + sid + "_newtr").style.display;
        if (trdisplay == "block") {
            document.all("item" + sid + "_newtr").style.display = "none";
            document.all(myimgname).src = syspath + "/HLDsysmanage/images/plusik.gif";
        }
        else {
            document.all("item" + sid + "_newtr").style.display = "block";
            document.all(mytdname).innerHTML = "Please Waiting...";
            document.all(myimgname).src = syspath + "/HLDsysmanage/images/minus.gif";
            var flowsn = sid.split("_")[0];
            //alert(flowsn);
            str = "<iframe name=iframe1 src='" + strURL + flowsn + "&parentID=" + sid + "' frameborder=0></iframe>";
            //alert(str);
            document.all("div_iframe").innerHTML = str;
        }
    }
    catch (e) {
    }
}
//=========================================================================================
//
//  代码描述：打开一个新的没有状态栏、工具栏、菜单栏、定位栏，不能改变大小，且位置居中的新窗口
//  传入参数：pageURL - 传递链接
//            innerWidth - 传递需要打开新窗口的宽度
//            innerHeight - 传递需要打开新窗口的高度
//  修改记录：姓名              日期                 内容
//           think           2005/04/11             创建
//
//=========================================================================================
function OpenWindow(pageURL, innerWidth, innerHeight, winName) {
    var name = "";
    if (typeof (winName) != "undefined")
        name = winName;
    var ScreenWidth = screen.availWidth
    var ScreenHeight = screen.availHeight
    var StartX = (ScreenWidth - innerWidth) / 2
    var StartY = (ScreenHeight - innerHeight) / 2
    var args = 'left=' + StartX + ', top=' + StartY + ', Width=' + innerWidth + ', height=' + innerHeight + ', resizable=yes, scrollbars=yes, status=yes, toolbar=no, menubar=yes, location=no';
    if (innerWidth == null && innerHeight == null)
        window.open(pageURL, name).focus();
    else {
        window.open(pageURL, name, args).focus();
    }
}
function OpenPage(pageURL) {
    window.location.href = pageURL;
}
//==========================================================================================
// 代码描述：打开模式窗口函数，打开一个模式窗口不包含菜单、状态条、工具条、定位栏，不刷新父窗口
// 修改记录：姓名                日期                 内容
//           chensheng           2006/10/26           创建
//==========================================================================================
function OpenModalWindow(pageURL, innerWidth, innerHeight) {
    window.showModalDialog(pageURL, window, 'dialogWidth:' + innerWidth + 'px;dialogHeight:' + innerHeight + 'px;help:no;unadorned:no;resizable:no;status:no');
    return false;
}
function OpenModalWindowParentLoad(pageURL, innerWidth, innerHeight) {
    //var sReturn;
    window.showModalDialog(pageURL, window, 'dialogWidth:' + innerWidth + 'px;dialogHeight:' + innerHeight + 'px;help:no;unadorned:no;resizable:no;status:no');
    return true;
}
//==========================================================================================
//
// 代码描述：返回模式窗体选择中的值
//
// 传入参数： name 值名称,value 值
//
// 返回参数：无
//
// 修改记录：姓名                      日期                       内容
//           think                    2005/04/15                   创建
//
//==========================================================================================
function SelectItem(name, value) {
    var retval = new Array();

    retval[0] = name;
    retval[1] = value;

    window.returnValue = retval;
    window.opener = null;
    parent.close();
}

//==========================================================================================
//
// 代码描述：得到URL中的参数值
//
// 传入参数： url URL地址, sName 参数名称
//
// 返回参数：指定参数的值
//
// 修改记录：姓名                      日期                       内容
//           think                    2005/04/15                   创建
//
//==========================================================================================
function QueryString(url, sName) {
    var sSource = url;
    var sReturn = "";
    var sQUS = "?";
    var sAMP = "&";
    var sEQ = "=";
    var iPos;

    iPos = sSource.indexOf(sQUS);

    var strQuery = sSource.substr(iPos, sSource.length - iPos);
    var strLCQuery = strQuery.toLowerCase();
    var strLCName = sName.toLowerCase();

    iPos = strLCQuery.indexOf(sQUS + strLCName + sEQ);
    if (iPos == -1) {
        iPos = strLCQuery.indexOf(sAMP + strLCName + sEQ);
        if (iPos == -1)
            return "";
    }

    sReturn = strQuery.substr(iPos + sName.length + 2, strQuery.length - (iPos + sName.length + 2));
    var iPosAMP = sReturn.indexOf(sAMP);

    if (iPosAMP == -1)
        return sReturn;
    else {
        sReturn = sReturn.substr(0, iPosAMP);
    }

    return sReturn;
}
//=======================================================================================================================================
// 代码描述：控制ASPX页面刷新的土办法JS，编写：chensheng
//=======================================================================================================================================
function reload() {
    if (document.Form1.cboxOnload.checked) {
        document.Form1.cboxOnload.checked = false;
        __doPostBack('cboxOnload', '');
    }
}
//设置父窗口的值 
function setFather(obj) {
    obj.document.getElementById("cboxOnload").checked = true;
}
//=======================================================================================================================================
// 代码描述：实现DataGrid控件中，鼠标移到某一行时的背景色、字体色的设置，进行一些颜色交替变换 编写：chensheng
//=======================================================================================================================================
function MouseOut(obj) {
    if (obj.style.backgroundColor == "#999999")
        obj.style.backgroundColor = "#999999";
    else {
        obj.style.backgroundColor = obj.getAttribute('BKC');
        obj.style.backgroundColor = '';
    }

}
function MouseOver(obj) {
    if (obj.style.backgroundColor == "#999999")
        obj.setAttribute('BKC', "#999999");
    else {
        obj.setAttribute('BKC', obj.style.backgroundColor);
        obj.style.backgroundColor = '#B0EDFF';
    }
}
function setItemBackColor(itemID, obj) {
    if (obj.checked) {
        document.all[itemID].style.backgroundColor = "#999999";
    }
    else
        document.all[itemID].style.backgroundColor = "";
}
function onclickCheckBox(itemID, obj, type, sText, sValue) {
    if (obj.checked) {
        //document.all[itemID].style.backgroundColor="#999999";
        //alert(type);
        if (type != "") {
            SelectItem(sText, sValue);
        }
    }
    else
        document.all[itemID].style.backgroundColor = "";
}
//=======================================================================================================================================
// 查看多语言信息的时候，如果栏目有图片，则设置图片的DIV。Code:chensheng
//=======================================================================================================================================
function ShowHideImg(type, i) {
    //alert(src);
    var div = document.all["div_" + i];
    if (type == "1") {
        div.style.display = "block";
    }
    else {
        div.style.display = "none";
    }
}
//==========================================================================================
//
// 代码描述：限制文本框只能输入数值
//
// 传入参数：maxLen 数值长度 ; dotLen 小数字位
//
// 返回参数：无
//
// 修改记录：姓名                      日期                       内容
//           think                    2005/04/11                   创建
//
//==========================================================================================
function LimitNumber(maxLen, dotLen) {
    var obj = window.event.srcElement;
    var dotnum = obj.value.indexOf('.');
    var retval = (event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode == 46 && obj.value.indexOf('.') == -1);

    //禁止非法字符和判断是否可以输入小数点
    if (!retval || (dotLen == 0 && event.keyCode == 46)) {
        event.keyCode = 0;
        return false;
    }

    //如果小数点出现在第零位，在前面补一个零
    if (dotnum == 0) {
        obj.value = 0 + obj.value;
    }

    if (dotLen == 0) {
        if (obj.value.length < maxLen) {
            return true;
        }
    }
    else {
        if (obj.value.substr(dotnum + 1, obj.value.length - dotnum).length < dotLen) {
            return true;
        }

        if (dotnum == -1 && obj.value.length < (maxLen - dotLen)) {
            return true;
        }
    }

    event.keyCode = 0;
    return false;
}
//==========================================================================================
// 代码描述：消除js代码产生的错误
//==========================================================================================

function killErrors() { return true; }
//window.onerror = killErrors; 
//==========================================================================================
// 代码描述：图片的SHU标移入移出背景色变换效果
//==========================================================================================
function ImageMouse(obj, type) {
    if (type == "1")
        obj.style.backgroundColor = '';
    else
        obj.style.backgroundColor = "#FFCC00";
}
//==========================================================================================
// 代码描述：按钮SHU标移入移出图片背景色变换,1表示移入，2移出
//==========================================================================================
function ButtomBgMouse(obj, type, oldbg, newbg) {
    if (type == "1")
        obj.style.background = "url(" + newbg + ")";
    else
        obj.style.background = "url(" + oldbg + ")";
}
//==========================================================================================
// 代码描述：下级菜单的显示隐藏
//==========================================================================================
function ClickByParent(id) {
    try {
        trdisplay = document.all(id).style.display;
        if (trdisplay == "block") {
            document.all(id).style.display = "none";
        }
        else {
            document.all(id).style.display = "block";
        }
    }
    catch (e) {
    }
}





//==========================================================================================
// 代码描述：以TAB页面形式打开页面
//==========================================================================================
function AddNewWin(URL, Title) {
    if (typeof (Trim) != "undefined") {
        URL = Trim(URL);
        Title = Trim(Title);
    }


    var iframId = getQueryString("iframId"); //当前窗体的ID作为子页面的父窗体ID
    if (iframId != null)
        URL += "&parentId=" + iframId;


    if (window.AddWin)
        window.AddWin(URL, Title);
    else if (window.parent.AddWin)
        window.parent.AddWin(URL, Title);
    else if (window.parent.parent.AddWin) {
        window.parent.parent.AddWin(URL, Title);
    }
    else if (window.parent.parent.parent.AddWin) {
        window.parent.parent.parent.AddWin(URL, Title);
    }
    else if (window.opener.parent.AddWin) {
        window.opener.parent.AddWin(URL, Title);
    }
}




//找到第一个可见的文本框，使其获得焦点
function focusOnFirstInput() {
    //    var input1 = window.parent.document.getElementById("testin");
    //    if (input1)
    //        input1.focus();
    var inputs = window.parent.document.getElementsByTagName("input");
    if (inputs && inputs.length) {
        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].type == "text" && inputs[i].style.display != "none") {
                inputs[i].focus();
                break;
            }
        }
    }
}

function focusOnfirst() {
    var inputs = window.document.getElementsByTagName("input");
    if (inputs && inputs.length) {
        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].type == "text" && inputs[i].style.display != "none") {
                inputs[i].focus();
                break;
            }
        }
    }
}

window.onload = focusOnFirstInput;


if (typeof window.event != 'undefined') {
    document.onkeydown = function() {
        var type = event.srcElement.type;
        var code = event.keyCode;
        if (code == 8) {
            if (type == 'text' || type == 'textarea') {
                if (event.srcElement.getAttribute("contentEditable") == "false" || event.srcElement.getAttribute("readonly") == true || event.srcElement.getAttribute("readonly") == "readonly")
                    return false;
                else
                    return true;
            }
        }
        else if (code == 116) {
            alert('系统禁用了F5刷新功能，如需刷新页面，请使用鼠标右键刷新。');
            event.keyCode = 0;
            event.cancelBubble = true;
            event.returnValue = false;
            return false;
        }
    }
}
else { // FireFox/Others
    document.onkeypress = function(e) {

        var type = e.target.localName.toLowerCase();
        var code = e.keyCode;
        if (code == 8) {
            if (type == 'text' || type == 'textarea') {
                if (event.srcElement.getAttribute("contentEditable") == "false" || event.srcElement.getAttribute("readonly") == "true")
                    return false;
                else
                    return true;
            }
        }
        else if (code == 116) {
            return false;
        }
    }
}