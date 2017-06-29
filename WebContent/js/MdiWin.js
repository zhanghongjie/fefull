/*
功    能: 多窗口功能实现
创建时间: 2008-01-15
创 建 人: 喻涛林 QQ:405121224
版    权: 中软数码 chinaspc.com
*/

if(typeof HTMLElement!="undefined" && !HTMLElement.prototype.insertAdjacentElement) 
{ 
     HTMLElement.prototype.insertAdjacentElement = function(where,parsedNode) 
     { 
        switch (where) 
        { 
            case 'beforeBegin': 
                this.parentNode.insertBefore(parsedNode,this) 
                break; 
            case 'afterBegin': 
                this.insertBefore(parsedNode,this.firstChild); 
                break; 
            case 'beforeEnd': 
                this.appendChild(parsedNode); 
                break; 
            case 'afterEnd': 
             if (this.nextSibling) this.parentNode.insertBefore(parsedNode,this.nextSibling); 
                    else this.parentNode.appendChild(parsedNode); 
                break; 
         } 
     }

     HTMLElement.prototype.insertAdjacentHTML = function (where,htmlStr) 
     { 
         var r = this.ownerDocument.createRange(); 
         r.setStartBefore(this); 
         var parsedHTML = r.createContextualFragment(htmlStr); 
         this.insertAdjacentElement(where,parsedHTML) 
     }

     HTMLElement.prototype.insertAdjacentText = function (where,txtStr) 
     { 
         var parsedText = document.createTextNode(txtStr) 
         this.insertAdjacentElement(where,parsedText) 
     } 
} 



function mywin() {
    this.winlist = new Array();   //窗口列表
    this.maxWins = 20; 			//最大窗口数
    this.tagTitleWidth = 112; 	//标签宽度
    this.indentWidth = 0; 		//标签缩进宽度
    this.currentwin = null;
    this.addwin = addwin;                        //新建窗口方法
    this.removewin = removewin;                        //移除窗体
    this.removeall = removeall;                        //移除所有窗体
    this.activewin = activewin;                        //激活窗口
    this.container = container;
    this.padLeft = padLeft; 				// 标题离左边缘的距离
    this.padRight = padRight; 			// 标题离右边缘的距离
    this.scrollWidth = scrollWidth;
    this.checkTagToClose = checkTagToClose; //关闭窗体
    this.winIndex = 1;
    this.tbHeight = "100%";

    this.corentIndex = 0; //当前选中的

    function container(url, title) {
        var realURL = "";
        if (url.indexOf("iframId") > 0)
            url = url.substr(0, url.indexOf("iframId"));
        for (var i = 0; i < this.winlist.length; i++) {
            if (this.winlist[i].title == title) {
                realURL = this.winlist[i].url;
                if (realURL.indexOf("iframId") > 0)
                    realURL = realURL.substr(0, realURL.indexOf("iframId"));
                if (realURL == url)
                    return i;
            }
        }
        return -1;
    }
    
    function activewin(oEl)                 //激活窗口
    {
        if (oEl == null) {
            this.currentwin = null;
            return
        }
        var tempzindex = this.currentwin.style.zIndex;
        this.currentwin.wintitle.style.zIndex = this.currentwin.index;
        this.currentwin.style.display = "none";
        this.currentwin.wintitle.style.backgroundImage = 'url(../images/tab1.jpg)';
        this.currentwin.wintitle.className = "wintitle";

        oEl.wintitle.style.zIndex = tempzindex;
        oEl.style.display = "";
        oEl.rows[0].cells[0].height = this.tbHeight;
        oEl.wintitle.style.backgroundImage = 'url(../images/tab2.jpg)';
        oEl.wintitle.className = "wintitle_active";
        this.currentwin = oEl;
        //如果不在显示区域内
        var clientwidth = titlelist.parentElement.clientWidth; //宽度
        var count = Math.floor(clientwidth / this.tagTitleWidth); //可以容纳几个
        var atleft = true; //是否为前半部分，且隐藏（默认为前半部分）  false则为后半部分，在第一个显示之后
        var index = -1;
        var showcount = 0;
        var i = 0;
        for (; i < this.winlist.length; i++) {
            //当前显示个数
            if (index == -1 && this.winlist[i].tabtitle.style.display == "")
                atleft = false; //只要之前有一个显示，说明之可能在后半部分
            if (this.winlist[i] == oEl) {
                this.winlist[i].tabtitle.style.display == ""
                index = i;

            }
            //为前半部分
            if (atleft) {
                //且有找到要激活的Tab，则往后count个显示即可
                if (index != -1) {
                    if (i >= index && i < index + count) {
                        this.winlist[i].tabtitle.style.display = "";
                    }
                    else
                        this.winlist[i].tabtitle.style.display = "none";
                }
            }
            //后半部分，记录后面显示了几个,向前显示，或隐藏
            else if (index != -1 && this.winlist[i].tabtitle.style.display == "") {
                showcount++;
            }

        }
        //后半部分，则往前显示count个，之前的也需要隐藏
        if (!atleft) {
            //从当前位置往前
            for (var i = index; i >= 0; i--) {
                if (index - i <= (count - showcount))//(count - showcount)扣除TAB之后已经显示了的个数
                    this.winlist[i].tabtitle.style.display = "";
                else
                    this.winlist[i].tabtitle.style.display = "none";
            }

        }

        //        //最后一个
        //        if (index == this.winlist.length - 1) {
        //            for (var i = this.winlist.length - count; i < this.winlist.length && i>=0; i++) {
        //                if (this.winlist[i].tabtitle.style.display != "") {
        //                    this.winlist[i].tabtitle.style.display = "";
        //                }
        //            }
        //        }



        focusOnfirst();
    }

    function padLeft(oEl) {
        var padleft = oEl.index * this.tagTitleWidth - this.indentWidth * (oEl.index - 1);
        return padleft;
    }

    function padRight(oEl) {
        var count = (this.winlist.length - oEl.index) + 1;
        var padright = this.tagTitleWidth * count - this.indentWidth * (count - 1);
        return padright;
    }


    function addwin(url, title, showClose, parentID)                                  //方法的具体实现
    {
        var iframID = (new Date()).getMilliseconds();
        if (url.indexOf("?") > 0) {
            url += "&iframId=" + iframID;
        }
        else
            url += "?iframId=" + iframID;
        if (parentID != null) {
            url += "&parentId=" + parentID;
        }
        
        
        var con = this.container(url, title);
        if (con > -1) {
            this.activewin(this.winlist[con]);
            return;
        }
        if (this.winlist.length >= this.maxWins) {
            this.removewin(this.winlist[1]); 	//超过最大窗口数限制后,先关闭最先开的窗口.喻涛林修改于:08-09-15
            //	alert("超过最大窗口数限制（"+this.maxWins+"），请先关闭部分窗口");
            //	return false;
        }

        oDIV = window.document.createElement("TABLE"); //窗体对象
        var oTitle = window.document.createElement("SPAN"); //TAB标签对象
        oTitle.win = oDIV;
        oDIV.tabtitle = oTitle;

        this.winlist[this.winlist.length] = oDIV;                //往列表内添加窗体对象
        oDIV.url = url;
        oDIV.title = title;
        oDIV.index = this.winlist.length;
        oDIV.className = "win";
        oDIV.width = "100%";
        //oDIV.height = this.tbHeight;
        oDIV.cellSpacing = 0;
        oDIV.insertRow().insertCell().innerHTML = "<iframe id='if" + iframID + "' src='" + url + "' scrolling='auto' name='tab_win" + oDIV.index + "' class = 'win1' width='100%' height='100%' frameborder='0'></iframe>";
        oDIV.rows[0].cells[0].height = this.tbHeight;

        oTitle.className = 'wintitle_active';
        oTitle.style.cursor = 'pointer';
        oTitle.style.valign = 'middle';
        oTitle.style.width = this.tagTitleWidth;
        oTitle.style.backgroundImage = 'url(../images/tab2.jpg)';
        //oTitle.style.left = this.winlist.length == 1 ? 0 : this.winlist[this.winlist.length-2].wintitle.style.pixelLeft - this.indentWidth;
        oTitle.title = title;
        title = subStr(title, 12);
        var str_k = "&nbsp;";
        if (title.length == 6) str_k = "&nbsp;";
        if (title.length < 5) str_k = "&nbsp;&nbsp;&nbsp;"
        if (showClose != null && showClose == false)
            oTitle.innerHTML = title == null ? "未知" : "<table width='100%'　 ><tr><td ><div class=\"divwintitle\">" + title + "</div></td><td></td></tr></table>"; //标签：不添加关闭
        else {

            oTitle.innerHTML = title == null ? "未知" : "<table width='100%'　 onDblClick=\"win.removewin(win.currentwin)\"><tr><td ><div class=\"divwintitle\">" + title + "</div></td><td><img border=\"0\"  src=\"../images/close_o.png\"  onmouseover=\"this.src='../images/close_n.png';\"  onmouseout=\"this.src='../images/close_o.png';\"  title=\"关闭\" class=\"imgwintitle\" /></td></tr></table>"; //标签：双击关闭窗口

        }
        oTitle.onclick = new Function("win.activewin(this.win);win.checkTagToClose(event);")

        if (this.currentwin != null) {
            this.currentwin.wintitle.style.backgroundImage = 'url(../images/tab1.jpg)';
            this.currentwin.style.display = "none";
            this.currentwin.wintitle.style.zIndex = this.currentwin.index;
            this.currentwin.wintitle.className = "wintitle";
        }
        this.winIndex = this.winIndex + 1;
        oDIV.style.zIndex = this.winIndex;
        oTitle.style.zIndex = this.winIndex;
        oDIV.wintitle = oTitle;
        titlelist.insertAdjacentElement("beforeEnd", oTitle);

        var clientwidth = titlelist.parentElement.clientWidth; //宽度
        var count = Math.floor(clientwidth / this.tagTitleWidth); //可以容纳几个
        //隐藏前面部分
        if (this.winlist.length > count) {
            for (var i = 0; i < this.winlist.length - count; i++) {
                this.winlist[i].tabtitle.style.display = "none";
            }
        }
        //		var scrollwidth = this.scrollWidth();
        //		if(scrollwidth > titlelist.parentElement.clientWidth)
        //		{
        //			titlelist.style.marginLeft = titlelist.parentElement.clientWidth - scrollwidth;
        //		}
        mywindows.insertAdjacentElement("beforeEnd", oDIV);

        this.currentwin = oDIV;
        return oDIV;
    }


    function imgclick(img) {
        img.parentNode.parentNode.parentNode.parentNode.parentNode.click();
        win.removewin(this.currentwin)
    }


    function checkTagToClose(e) {
        e = e ? e : window.event;
        var obj = e.target ? e.target : e.srcElement;    //.srcElement为ie下的属性
        if (obj.tagName == "IMG") {
            win.removewin(win.currentwin);
        }
    }

    function scrollWidth() {
        var n = this.winlist.length;
        var scrollwidth = this.tagTitleWidth * n - this.indentWidth * (n - 1);
        return scrollwidth;
    }

    function removewin(obj)        //移除窗体
    {
        if (obj == null || this.winlist.length == 1) return; //
        var temparr = new Array();
        var afterwin = false;
        var lastwin = false;
        var index = 0;
        for (var i = 0; i < this.winlist.length; i++) {
            //if (afterwin) this.winlist[i].wintitle.style.left = this.winlist[i].wintitle.style.pixelLeft + this.indentWidth;
            if (this.winlist[i] != obj) {
                temparr[temparr.length] = this.winlist[i];
            }
            else {
                afterwin = true;
                index = i;
                if (i == this.winlist.length - 1)
                    lastwin = true;
            }
        }

        this.winlist = temparr;
        if (this.winlist.length > 0) {
            //如果前面有隐藏的，则激活前面的
            if (this.winlist[0].tabtitle.style.display == "none") {
                this.activewin(this.winlist[index - 1]);
            }
            else {
                if (!lastwin) //不是最后一个
                {
                    this.activewin(this.winlist[index]);
                }
                else //最后一个
                {
                    this.activewin(this.winlist[this.winlist.length - 1]);
                }
            }
        }


        //		if(this.currentwin == obj){
        //			this.activewin(this.winlist[this.winlist.length-1]);
        //		}
        //        obj.wintitle.removeNode(true);
        //        obj.removeNode(true);
        obj.wintitle.parentNode.removeChild(obj.wintitle);
        obj.parentNode.removeChild(obj);
        // obj = null;
        if (titlelist.offsetLeft < 0) {
            titlelist.style.left = titlelist.offsetLeft + this.tagTitleWidth;
        }
        focusOnfirst();

    }

    function removeall()        //移除所有窗体
    {
        var wincount = this.winlist.length;
        for (var i = wincount - 1; i >= 0; i--)
            this.removewin(this.winlist[i]);
    }
}

function tabScroll(direction) {
    tabScrollStop();
    direction == "right" ? tabMoveRight() : tabMoveLeft();
}

function tabMoveRight() {
    tabMove("right", 8);
    timer = setTimeout(tabMoveRight, 10);
}

function tabMoveLeft() {
    tabMove("left", 8);
    timer = setTimeout(tabMoveLeft, 10);
}

function tabScrollStop() {
    clearTimeout(timer);
    timer = null;
}

function tabMove(direction, speed) {
    var mleft = parseInt(titlelist.style.marginLeft);
    if (isNaN(mleft))
        mleft = 0;
    if (direction == "right") {
        if (titlelist.parentElement.clientWidth >= titlelist.parentElement.scrollWidth) {
            tabScrollStop();
            return;
        }
        else {
            titlelist.style.marginLeft = mleft - speed;
        }
    }
    else {
        if (mleft + speed >= 0) {
            titlelist.style.marginLeft = 0;
            tabScrollStop();
            return;
        }
        else {
            titlelist.style.marginLeft = mleft + speed;
        }
    }
}
var timer = null;
var win = null;
var wins = new Array();

function init() {
    win = new mywin();                        //新建对象
}

function AddWin(Url, Title, showClose) {
	//alert(Url);
    wins[wins.length] = win.addwin(Url, Title, showClose);                        //添加窗体；
    return wins[wins.length - 1];
}

function subStr(str, len) {
    var strlength = 0;
    var newstr = "";
    for (var i = 0; i < str.length; i++) {
        if (str.charCodeAt(i) >= 1000)
            strlength += 2;
        else
            strlength += 1;
        if (strlength > len) {
            newstr += "..";
            break;
        }
        else {
            newstr += str.substr(i, 1);
        }
    }
    return newstr;
}


function closeWin() {
    win.removewin(win.currentwin)
    focusOnfirst();
}


///刷新页面，出发页面按钮点击事件
function refreshWin(ifID, btnID) {
    //$("#if" + ifID).contents().find("#" + btnID).click();
    var parentWin = window.document.getElementById("if" + ifID).contentWindow;
    if (parentWin) {
        var btn = parentWin.document.getElementById(btnID);
        if (btn)
            btn.click();
    }

}