/**
 * 动态生成select选项
 * @param selectId
 * @param parentId
 * @returns
 */
function initSelectOptions(selectId, url,rows,key,value) {
    let selectObj = $("#" + selectId);
    $.ajax({
        url : url,
        async : false,
        type : "GET",
        success : function(result) {
            let configs = result[rows];
            selectObj.find("option:not(:first)").remove();
            for (let i in configs) {
                let addressConfig = configs[i];
                let optionValue = addressConfig[value];
                let optionText = addressConfig[key];
               //  var op = "<option value='" + optionValue + "'>" + optionText + "</option>"
                selectObj.append(new Option(optionValue, optionText));
                // selectObj.append(op);
            }
            // 刷新select
            // selectObj.selectpicker('refresh');
        },
        error : function(result) {
            // toastr.error('获取信息失败，原因：' + result.errorMessage);
        }
    });// ajax
}
function initSelectOptions3(selectId, url,key,value) {
    let selectObj = $("#" + selectId);
    $.ajax({
        url : url,
        async : false,
        type : "GET",
        success : function(result) {
            let configs = result.rows;
            selectObj.find("option:not(:first)").remove();
            for (let i in configs) {
                let addressConfig = configs[i];
                let optionValue = addressConfig[value];
                let optionText = addressConfig[key];
                selectObj.append(new Option(optionValue, optionText));
            }
        },
        error : function(result) {
            // toastr.error('获取信息失败，原因：' + result.errorMessage);
        }
    });// ajax
}
function initSelectOptions2(selectId, url,key,value) {
    let selectObj = $("#" + selectId);
    $.ajax({
        url : url,
        async : false,
        type : "GET",
        success : function(result) {
            let options = result.rows;
            selectObj.empty();
            for (var i in options) {
                // console.log(result)
                let option = options[i];
                let key2 = option[key];
                let text = option[value];
                // var op = "<option value='" + key + "'>" + text + "</option>"
                let op = "<li class='on' value='" + key2 + "'>" + text + "</li>"
                selectObj.append(op);
            }
            selectObj.append("<p>无匹配项</p>");
            // 刷新select
            // selectObj.selectpicker('refresh');
        },
        error : function(result) {
            // toastr.error('获取信息失败，原因：' + result.errorMessage);
        }
    });// ajax
}
function initSelects(selectId, url) {
    let selectObj = $("#" + selectId);
    $.ajax({
        url : url,
        type : "GET",
        success : function(result) {
            var options = result;
            selectObj.empty();
            for (var i in options) {
                // console.log(result)
                let option = options[i];
                let key2 = option['key'];
                let text = option['value'];
                // var op = "<option value='" + key + "'>" + text + "</option>"
                let op = "<option value='" + key2 + "'>" + text + "</option>"
                selectObj.append(op);
            }
        },
        error : function(result) {
            // toastr.error('获取信息失败，原因：' + result.errorMessage);
        }
    });// ajax
}

/**
 * 保留第一个
 * @param selectId
 * @param url
 * @param b 是否保留第一个
 */
function initSelectsKeepFirst(selectId, url,b) {
    let selectObj = $("#" + selectId);
    $.ajax({
        url : url,
        type : "GET",
        async:false,
        success : function(result) {
            let options = result;
            selectObj.empty();
            if (b) {
                selectObj.append("<option value=''></option>");
            }
            for (let i in options) {
                let option = options[i];
                let key2 = option['key'];
                let text = option['value'];
                let op = "<option value='" + key2 + "'>" + text + "</option>"
                selectObj.append(op);
            }
        },
        error : function(result) {
            // toastr.error('获取信息失败，原因：' + result.errorMessage);
        }
    });// ajax
}
/**
 * min - max 之间的随机数
 * @param min
 * @param max
 * @returns {*}
 */
function ranNUm(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
}
// 动态创建select
function initCategory() {
    initSelectOptions2("cateItems", "/getAllCates","cateId","cateName");
}

/**
 * 设置代码主题样式
 */
function setCodeTheme(t) {
    if (t === 1) {
        $("#themcss").remove();
        let skin = get_item("skin");
        if (isNull_O(skin) || skin === "light_white") {
            createResources("link","/htjs/styles/a11y-light.css","themcss","head");
        } else if (skin === "furvous") {
            createResources("link","/htjs/styles/a11y-dark.css","themcss","head");
        } else if (skin === "reading") {
            createResources("link","/htjs/styles/purebasic.css","themcss","head");
        }
    } else {
        $("pre").attr("background","#f6f6f6")
    }
}

function clearSongBox() {
    $("#metingBox").html('');
}

/**
 * 动态创建songBox
 * @param res
 */
function createSongBox(res,userId) {
    set_item(songList + userId,JSON.stringify(res))
    // let type = res.songType;
    let autoplay = res.aotoPaly;
    let id = res.songNo;
    // let theme = res.songTheme;
    let fixed = res.leftFixed;
    let server = res.songServer;
    let mb_ = $("meting-js");
    if (mb_.length > 0) {
        mb_.attr("server",server);
        // mb_.attr("type",type);
        mb_.attr("id",id);
        mb_.attr("fixed",fixed);
        // mb_.attr("theme",theme);
        mb_.attr("autoplay",autoplay);
    } else {
        $("#metingBox").html('<meting-js server="'+server+'"' +
            // '        type="'+type+'"' +
            '        type="playlist"' +
            '        id="'+id+'"' +
            '        fixed="'+fixed+'"' +
            // '        theme="'+theme+'"' +
            '        autoplay="'+autoplay+'"' +
            '        loop="all"' +
            '        order="random"' +
            '        preload="auto"' +
            '        list-folded="ture"' +
            '        list-max-height="500px"></meting-js>');
    }
    // 设置CSS
    // $(".aplayer .aplayer-miniswitcher").css("background",theme);
    // $(".aplayer.aplayer-fixed .aplayer-body").css("background",theme);
    // $(".aplayer").css("background",theme);
    
}
// wdeditor 上传图片
function wd_upload_img(daw,insertImgFn) {
    // insertImgFn 是获取图片 url 后，插入到编辑器的方法
    upload_file("/t/kodo/wd",daw,function (urlMaps) {
        for (let i = 0; i < urlMaps.length; i++) {
            insertImgFn(urlMaps[i].imgUrl);
        }
    });
}

function yesOrNo(v) {
    return v===1?'是':'否';
}
