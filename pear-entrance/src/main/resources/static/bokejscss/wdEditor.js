
jQuery.fn.extend({
    getCurPos: function(){
        var e=$(this).get(0);
        e.focus();
        if(e.selectionStart){    //FF  
            return e.selectionStart;
        }
        if(document.selection){    //IE  
            var r = document.selection.createRange();
            if (r == null) {
                return e.value.length;
            }
            var re = e.createTextRange();
            var rc = re.duplicate();
            re.moveToBookmark(r.getBookmark());
            rc.setEndPoint('EndToStart', re);
            return rc.text.length;
        }
        return e.value.length;
    },
    setCurPos: function(pos) {
        var e=$(this).get(0);
        e.focus();
        if (e.setSelectionRange) {
            e.setSelectionRange(pos, pos);
        } else if (e.createTextRange) {
            let range = e.createTextRange();
            range.collapse(true);
            range.moveEnd('character', pos);
            range.moveStart('character', pos);
            range.select();
        }
    }
});
// 获取光标所在位置
function getSelectionCoords(win) {
    win = win || window;
    var doc = win.document;
    var sel = doc.selection, range, rects, rect;
    var x = 0, y = 0;
    if (sel) {
        if (sel.type != "Control") {
            range = sel.createRange();
            range.collapse(true);
            x = range.boundingLeft;
            y = range.boundingTop;
        }
    } else if (win.getSelection) {
        sel = win.getSelection();
        if (sel.rangeCount) {
            range = sel.getRangeAt(0).cloneRange();
            if (range.getClientRects) {
                range.collapse(true);
                rects = range.getClientRects();
                if (rects.length > 0) {
                    rect = rects[0];
                }
                // 光标在行首时，rect为undefined
                if(rect){
                    x = rect.left;
                    y = rect.top;
                }
            }
            if ((x == 0 && y == 0) || rect === undefined) {
                var span = doc.createElement("span");
                if (span.getClientRects) {
                    // Ensure span has dimensions and position by
                    // adding a zero-width space character
                    span.appendChild( doc.createTextNode("\u200b") );
                    range.insertNode(span);
                    rect = span.getClientRects()[0];
                    x = rect.left;
                    y = rect.top;
                    var spanParent = span.parentNode;
                    spanParent.removeChild(span);

                    // Glue any broken text nodes back together
                    spanParent.normalize();
                }
            }
        }
    }
    // console.log({ left: x, top: y })
    return { left: x, top: y };
}
// 编辑器工具栏高度
// 标题工具栏高度
var titlte_h = $(".wd_header").outerHeight();
var b_h = $(document.body).height();
var d_h = $(window).height();
var mm_h = 66;
var height1 = d_h - b_h - titlte_h - mm_h - 26;
// 判断是否是手机
if (jQuery.browser.mobile) {
    // 编辑器工具栏，初始化之后的高度是42 padding20
    height1 = d_h - titlte_h - 42 - 20;
}

const E = window.wangEditor
const editor = new E("#wangEdit")
// 或者 const editor = new E(document.getElementById('div1'))
// 设置编辑区域高度为 500px
editor.config.height = height1
// editor.config.height = 800
editor.config.zIndex = 0
// 取消自动 focus
editor.config.focus = false
// editor.config.placeholder = '' // 不想使用 placeholder ，赋值为空字符串即可
// 可使用 editor.txt.clear() 清空编辑器内容。
// 使用 editor.txt.text() 获取 text 文本。
// 使用 editor.txt.html() 获取 html
// 还可以修改历史记录的步数。默认 30 步
editor.config.historyMaxSize = 150 // 修改为 50 步
//配置 onchange 函数之后，用户操作（鼠标点击、键盘打字等）导致的内容变化之后，会自动触发 onchange 函数执行。
// 粘贴之后滚动到光标所在位置！！！！ 实在整不了
editor.config.onchange = function (newHtml) {
    //模拟按下回车键
    var e = jQuery.Event("keydown");//模拟一个键盘事件
    e.keyCode = 13;//keyCode=13是回车
    $(".w-e-text").trigger(e);//模拟页码框按下回车
}
// 默认情况下，显示所有菜单
editor.config.menus = wd_menus
// 自定义表情
editor.config.emotions = getEmoji();
// 挂载highlight插件
editor.highlight = hljs
//插入代码语言配置
editor.config.languageType = wd_language
// 可以通过配置 editor.config.pasteIgnoreImg = true 来忽略粘贴的图片
// 可使用 base64 格式保存图片。即，可选择本地图片，编辑器用 base64 格式显示图片。
// editor.config.uploadImgShowBase64 = true
editor.config.customUploadImg = function (resultFiles, insertImgFn) {
    // resultFiles 是 input 中选中的文件列表
    var daw = new FormData();
    var index = 0;
    var maxLen = 5 * 1024 * 1024;// M
    var minLen = 0;
    for(var i=0;i<resultFiles.length;i++){
        var file = resultFiles[i];
        if (file.size <= maxLen && file.size > minLen) {
            daw.append("files",resultFiles[i]);
            index ++;
        }
    }
    if (index == 0) {
        show_l_m("没有可上传的文件~",5);
        return false;
    }
    wd_upload_img(daw,insertImgFn);
}
// editor.config.onCatalogChange = function (headList) {}
editor.create()

let b_skin = get_item(lo_skin);
if (typeof b_skin === "undefined" || isNull_O(b_skin) || "light_white" === b_skin) {
    $("#reading").remove();
    $("#darkly").remove();
} else if ("furvous" === b_skin) {
    $("#reading").remove();
    createResources("link","/css/darkly.css","darkly");
} else if ("reading" === b_skin) {
    $("#darkly").remove();
    createResources("link","/css/reading.css","reading");
}

let focus = getSelectionCoords().top + 50;
$('.w-e-text').animate({
    scrollTop: focus
});

function showTxtLen() {
    let ttt = editor.txt.text().trim();
    $("#showTextLen").html("当前已输入：" + ttt.length + "个字~");
}