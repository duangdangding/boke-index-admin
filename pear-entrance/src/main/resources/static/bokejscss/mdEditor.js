
var testEditor;
// $("#divId").outerHeight();//包括内边距和边框
// var d_h = $(document.body).height()
// console.log(d_h)
var h_h = $("#md_header").outerHeight()
// console.log(h_h)
$(function() {
    testEditor = editormd("test-editormd", {
        width   : "100%",
        height  : $(window).height() - h_h - 20,
        // watch   : false,
        emoji   : true,
        theme : "dark", //主题
        previewTheme : "dark", // 预览区主题
        editorTheme : "pastel-on-dark", // 编辑区主题
        taskList: true,
        saveHTMLToTextarea : true,
        previewCodeHighlight : true,
        // markdown : md,
        codeFold : true,
        tocm: true,         // Using [TOCM]
        tex: true,                   // 开启科学公式TeX语言支持，默认关闭
        flowChart: true,             // 开启流程图支持，默认关闭
        sequenceDiagram: true,       // 开启时序/序列图支持，默认关闭,
        syncScrolling : "single",
        imageUpload: true,//图片上传功能
        imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        // imageUploadURL : "/upload/ftp/t/md",
        // imageUploadURL : "/t/qny/upload/md",
        imageUploadURL : "/t/kodo/md",
        onload : function() {
            initPasteDragImg(this); //允许粘贴和拖拉图片到editormd
        },
        path    : "/editor/editormd/lib/",
        toolbarIcons : function() {
            // Or return editormd.toolbarModes[name]; // full, simple, mini
            // Using "||" set icons align right.
            return md_menus;
        }
    });
});

function initPasteDragImg(Editor){
    var doc = document.getElementById(Editor.id)
    doc.addEventListener('paste', function (event) {
        var items = (event.clipboardData || window.clipboardData).items;
        var file = null;
        if (items && items.length) {
            // 搜索剪切板items
            for (let i = 0; i < items.length; i++) {
                if (items[i].type.indexOf('image') !== -1) {
                    file = items[i].getAsFile();
                    break;
                }
            }
        } else {
            console.log("当前浏览器不支持");
            return;
        }
        if (!file) {
            console.log("粘贴内容非图片");
            return;
        }
        uploadImg(file,Editor);
    });
    var dashboard = document.getElementById(Editor.id)
    dashboard.addEventListener("dragover", function (e) {
        e.preventDefault()
        e.stopPropagation()
    })
    dashboard.addEventListener("dragenter", function (e) {
        e.preventDefault()
        e.stopPropagation()
    })
    dashboard.addEventListener("drop", function (e) {
        e.preventDefault()
        e.stopPropagation()
        var files = this.files || e.dataTransfer.files;
        uploadImg(files[0],Editor);
    })
}
var maxLen = 5 * 1024 * 1024;// M
var minLen = 0;
function uploadImg(file,Editor){
    let formData = new FormData();
    let fileName=new Date().getTime()+"."+file.name.split(".").pop();
    // console.log("文件大小"+file.size)
    if (file.size > maxLen || file.size == 0) {
        show_l_m("文件大小必须在5M以内~",5)
        return false;
    }
    formData.append('editormd-image-file', file, fileName);
    $.ajax({
        url: Editor.settings.imageUploadURL,
        type: 'post',
        data: formData,
        // async:false,
        processData: false,
        contentType: false,
        dataType: 'json',
        //再次添加头部信息
        beforeSend: function(request) {
            // showLoad()
            // requestToken(request,token);
        },
        success: function (msg) {
            var success=msg['success'];
            if(success==1){
                var url=msg["url"];
                if(/\.(png|jpg|jpeg|gif|bmp|ico)$/.test(url)){
                    Editor.insertValue("![]("+url+")");
                }else{
                    Editor.insertValue("[下载附件]("+url+")");
                }
            }else{
                show_l_m("上传失败",5)
                // alert("上传失败");
            }
        },
        complete:closeLoad
    });
    
}
function showTxtLen() {
    let bokeCont = testEditor.getPreviewedHTML();
    let ttt = $(bokeCont).text();
    $("#showTextLen").html("当前已输入：" + ttt.length + "个字~");
}
// 页面加载完之后执行
window.onload = function () {
    $(".aplayer.aplayer-fixed .aplayer-body").css("left","auto");
    $(".aplayer.aplayer-fixed").css("left","auto");
}