var emojiJson = "https://gitee.com/magerlu/source/raw/master/editor/wangeditor/emoji/giteeemoji.json";
// var emojiWangEditor = "/editor/wangeditor/emoji/giteeemoji.json";
var emojiWangEditor = "/api/getJson?url=" + emojiJson;

function getEmoji() {
    let e_datae = null;
    $.ajaxSettings.async = false;
    $.getJSON(emojiWangEditor,function (data) {
        e_datae = data;
    });
    $.ajaxSettings.async = true;
    return e_datae;
}

/**
 * 清除localStorage某一项
 * 如果没传item则清空所有
 * @param item
 */
function clear_item(item) {
    if (item) {
        if (get_item(item)) {
            localStorage.removeItem(item);
        }
    } else {
        localStorage.clear();
    }

}
/**
 * getlocalStorage某一项 获取本地缓存
 * @param item
 */
function get_item(item) {
    return localStorage.getItem(item);
}
/**
 * 设置localStorage某一项
 * 先清空该值再设置
 * 如果all 是true 清空所有
 * @param item
 */
function set_item(item,v,b) {
    if (b) {
        clear_item(item);
    }
    localStorage.setItem(item, v);
}
// s设置本地缓存
function set_item2(item,v) {
    set_item(item, v,false);
}
function set_item3(item,v) {
    set_item(item, v,true);
}

function show_errMsg(msg) {
    $(".error_msg").addClass("err_c");//添加样式
    $(".error_msg").removeClass("suc_c");//移除样式
    showMsg(msg);
    return false;
}
function show_sucMsg(msg) {
    $(".error_msg").addClass("suc_c");//添加样式
    $(".error_msg").removeClass("err_c");//移除样式
    showMsg(msg);
}
function showMsg(msg) {
    closeLoad(0);
    $("#msg_to").text(msg);
    layer.msg(msg)
}
// 显示加载层
var load_index;
function showLoad() {
    load_index = layer.msg('请稍等...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false,offset: 'auto', time:100000});
    return load_index;
}
// 关闭加载层
function closeLoad() {
    // 关闭所有的加载层
    layer.closeAll('loading');
    layer.close(load_index);
}

function toeditor() {
    showLoad()
    var login = false;
    $.post({
        url : "/t/to/to",
        dataType : "json",
        async:false,
        // data:{title:title,bokeCont:html,introduction:text,cateId:cateId,priPub:val,editorType:1,bokeId:boke_id},
        //再次添加头部信息
        beforeSend: function(request) {
            requestToken(request,get_item(lo_token));
        },
        success : function (data) {
            let his_url = window.location.href;
            set_item2("edit_pre",his_url);
            login = true;
        },
        complete:closeLoad
    })
    return login;
}

function logout() {
    loginHttp({
        url: '/to/to',
        type: 'post',
        // data:{userName:username,password:passord,userEmail:email},
        beforeSend: function () { // 可选
            // loading 显示
            showLoad();
        },
        success: function (res) {
            clear_item();
            window.location.href = "/t/logout";
        },
        complete: function () { // 可选
            // loading 关闭
            closeLoad(0)
        }
    })
}

/**
 * 获取用户信息
 */
function getInfo(suibi_index) {
    let userId = get_item(lo_userId);
    if (isNull_O(userId)) {
        userId = 0;
    }
    $.ajax({
        url:"/userinfo/" + userId,
        async:false,
        success:function (data) {
            let user = data.result;
            $("#portrait img").attr("src",user.avatar)
            $(".github_c a").attr("href",user.githubUrl);
            $(".weixin_c a").attr("title",user.wxUrl);
            // $(".QQ_c a").attr("title",user.qqUrl);
            $(".csdn_c a").attr("href",user.csdnUrl);
            $(".bili_c a").attr("href",user.biliUrl);
            $("#uuuuu").text(user.userName)
            
            $("#user_login_a").hide();
        }
    })
}
$(function (){
    $("#info_table td.weixin_c").mouseover(function (){
        $("#info_table td.weixin_c .wx_img").show();
    }).mouseout(function (){
        $("#info_table td.weixin_c .wx_img").hide();
    })
    $("#info_table td.QQ_c").mouseover(function (){
        $("#info_table td.QQ_c .qq_img").show();
    }).mouseout(function (){
        $("#info_table td.QQ_c .qq_img").hide();
    })

})
/**
 * zx-image-viewer.min.js插件预览图片
 */
function zxShowImg(e,imgs) {
    let len = e.length;
    if (len > 0) {
        // 初始化参数
        let _config = {
            // 分页mouseover切换图片
            paginationable: true,
            // 显示关闭按钮
            showClose: true,
            // 显示上一张/下一张箭头
            showSwitchArrow: true,
            // 显示分页导航栏
            showPagination: true,
            // 显示工具栏
            showToolbar: true,
            // 缩放
            scalable: true,
            // 旋转
            rotatable: true,
            // 移动
            movable: true,
            // 键盘配置
            keyboard: {
                // scale: ['equal', 'minus']
            },
            // maskBackground: 'rgba(255, 100, 0, 0.5)'
        }
        // initImgopen(imgs)
        let ziv = new ZxImageViewer(_config,imgs);
        // 查看第几张
        // var $images = $el.querySelectorAll('.item');
        for (var i = 0; i < len; i++) {
            (function (index) {
                e[i].addEventListener('click', function () {
                    /*console.log(index+"~"+len)
                    if (index == len) {
                        layer.msg("当前最后一张")
                    }*/
                    ziv.view(index);
                })
            }(i))
        }
    }
}


/*图片弹窗,layerui弹窗*/
function initImgopen(imgs) {
    var b_h = $(window).height() //浏览器时下窗口可视区域高度   
    var b_w = $(window).width() //浏览器时下窗口可视区域高度   
    // console.log(b_w+"~"+b_h)
    imgs.each(function () {
        // $(this).attr("class","imgZoom");
        $(this).click(function () {
            // var img = "<div style='display: flex;align-items: center;width: 100%;height: 100%;text-align: center'>" +
            //     "<img style='align-items: center;justify-content: center' onclick='closelayer()' src='" + $(this).attr("src") + "' /></div>";
            // var img = $("<img style='align-items: center;justify-content: center' width='100%' height='100%'
            // onclick='closelayer()' src='" + $(this).attr("src") + "' />");
            // 得到图片的原始大小
            var old = new Image();
            old.src = $(this).attr("src");
            //页面层-
            var i_h = old.height;
            var i_w = old.width;
            var h,w;
            if (i_h > b_h) {
                h = b_h;
            } else {
                h = 'auto';
            }
            if (i_w > b_w) {
                w = b_w;
            } else {
                w = 'auto';
            }
            // 重新设置图片大小
            var img2 = "<img style='align-items: center;justify-content: center' width='"+w+"' height='"+h+"' " +
                "onclick='closelayer()' src='" + $(this).attr("src") + "' />";
            layer.open({
                type: 1,//1:本地  2:url  
                title: false,
                shadeClose: true,//是否点击遮罩关闭
                closeBtn: 0, //不显示关闭按钮
                shade: 0,//遮罩层
                // resize:false,// 是否允许拉伸
                // maxmin: true, //开启最大化最小化按钮
                area:[0,0],
                // area: [img.width + 'px', img.height+'px'],
                anim:-1,//动画  -1不显示
                time: 150, //2秒后自动关闭
                // content: img,
                end: function(){ //此处用于演示
                    layer.open({
                        type: 1,//1:本地  2:url  
                        title: false,
                        shadeClose: true,//是否点击遮罩关闭
                        shade: 0.6,//遮罩层
                        move: ".layui-layer-content img",
                        moveType: 1,
                        resize:false,// 是否允许拉伸
                        maxmin: true, //开启最大化最小化按钮
                        fixed:false,//即鼠标滚动时，层是否固定在可视区域。如果不想，设置fixed: false即可
                        // area:['auto','auto'],
                        // area: [img.width + 'px', img.height+'px'],
                        area: [w + 'px', h+'px'],
                        anim:0,//动画  -1不显示
                        content: img2,
                    });
                }
            });
        })
    })
}

/**
 * 添加复制按--博客详情页代码元素
 * @param t md则加一个逻辑
 */
function addCodeCopy(t) {
    let pres = $("pre");
    if (pres.length) {
        pres.each(function() {
            let strs = "";
            if (!isNull_O(t) && t === "md") {
                let lis = $(this)
                    .children("ol").children("li");
                lis.each(function () {
                    strs += $(this).text() + "\n";
                })
            } else {
                strs = $(this).text();
            }
            /*前一个复制按钮*/
            let btn = $('<a class="copy-btn" target="_self" data-clipboard-snippet>' +
                '<img class="clippy" src="../imgs/copy.gif" title="复制代码"></a>').attr(
                "data-clipboard-text",
                strs
            );
            $(this).prepend(btn);

            let c = new ClipboardJS(btn[0]);
            c.on("success", function() {
                show_suc_msg(copy_suc);
            });
            c.on("error", function() {
                show_err_msg(copy_err);
            });
            if($(this).height()>400) {
                // 后一个复制按钮
                let btn2 = $('<a class="copy-btn2" style="margin-top: -10px" data-clipboard-snippet>' +
                    '<img class="clippy" style="margin-top: -10px" src="../imgs/copy.gif" title="复制代码"></a>').attr(
                    "data-clipboard-text",
                    strs
                );
                $(this).append(btn2);
                let c2 = new ClipboardJS(btn2[0]);
                c2.on("success", function() {
                    show_suc_msg(copy_suc);
                });
                c2.on("error", function() {
                    show_err_msg(copy_err);
                });
            }
        });
    }
}

/*layer 消息窗*/
// 0叹号，i=1对号,i=2 错误，3问号，4锁，5不开心，6开心
function show_l_m(m,i) {
    layer.msg(m,{icon: i,time:1000})
}
function show_err_msg(m) {
    show_l_m(m,5)
}
function show_suc_msg(m) {
    show_l_m(m,6)
}
/**
 * 公共token 提交
 * 1,弹出消息2，运行函数3，既弹消息又运行函数
 */
function token_ajax(url,data,fun,o,p) {
    if (!o) {o = 3;}
    if (!p) {p = 1;}
    if (!data) {data = {};}
    showLoad()
    $.ajax({
        url:url,
        type:"post",
        data:data,
        dataType:"json",
        //再次添加头部信息
        beforeSend: function(request) {
            // requestToken(request,token);
        },
        success:function (data) {
            if (data.status === -1) {
                jcAjax_result(data.msg,fun,p,5);
            } else {
                jcAjax_result(data.result,fun,o,6);
            }
        },
        complete:closeLoad,
        error:serverErr
    })
}
function token_ajax2(url,fun,o,p) {
    token_ajax(url,{},fun,o,p);
}
function serverErr() {
    layer.msg(server_err);
}
/* 文件上传公共方法 */
function token_file(url,data,fun,o,p) {
    showLoad()
    if (!o) {o = 2;}
    if (!p) {p = 1;}
    $.ajax({
        type: 'post',
        url: url, //txy上传文件的请求路径必须是绝对路劲
        data: data,
        cache: false,
        // async:false,
        processData: false,
        contentType: false,
        //再次添加头部信息
        beforeSend: function(request) {
            // requestToken(request,token);
        },
        success:function (data) {
            if (data.status === -1) {
                jcAjax_result(data.msg,fun,o,2);
            } else {
                jcAjax_result(data.result,fun,p,6);
            }
        },
        complete:closeLoad,
        error:serverErr
    });
}
// 上传文件 上传图片
// 1,弹出消息2，运行函数3，既弹消息又运行函数
function upload_file(url,data,fun,o,p) {
    showLoad()
    if (!o) {o = 1;}
    if (!p) {p = 2;}
    $.ajax({
        type: 'post',
        url: url, //txy上传文件的请求路径必须是绝对路劲
        data: data,
        cache: false,
        // async:false,
        processData: false,
        contentType: false,
        success:function (data) {
            if (data.status === -1) {
                jcAjax_result(data.msg,fun,o,2);
            } else {
                jcAjax_result(data.result,fun,p,6);
            }
        },
        complete:closeLoad,
        error:serverErr
    });
}

/**
 * 打开窗口
 * */
function openWind(t,e,wi,hi,fun) {
    let w = wi ? wi : 'auto';
    let h = hi ? hi : 'auto';
    layer.open({
        skin: 'layui-layer-rim', //加上边框
        area: [w, h], //宽高
        title: t,
        type:1,
        content:$("#" + e),
        cancel: function(){
            if (fun) fun()
        }
    })
}
function openWind2(t,e,wi,fun) {
    openWind(t,e,wi,'auto',fun);
}
function openWind3(t,e,fun) {
    openWind(t,e,'auto','auto',fun);
}
function openHtmlWin(t,e,wi,hi,fun) {
    let w = wi ? wi : '50%';
    let h = hi ? hi : '60%';
    layer.open({
        skin: 'layui-layer-rim', //加上边框
        area: [w, h], //宽高
        title: t,
        shadeClose: true,//是否点击遮罩关闭
        type:2,
        content:e,
        cancel: function(){
            if (fun) fun()
        }
    })
}
function openHtmlWin_fun(t,e,fun) {
    openHtmlWin(t,e,'50%','70%',fun);
}
function openHtmlWin_fun3(t,e,w,fun) {
    openHtmlWin(t,e,w,'70%',fun);
}
function openHtmlWin_fun2(t,e,h,fun) {
    openHtmlWin(t,e,'50%',h,fun);
}

/**
 * 关闭layui弹出层
 * @param fun 执行的函数
 * @param type 默认page
 */
function closelayer(fun,type){
    let t = type ? type : 'page';
    layer.closeAll(t); //关闭所有页面层
    if (fun) fun()
}

/**
 * 关闭当前layer弹窗iframe
 */
function closeIframe() {
    //当你在iframe页面关闭自身时
    let index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}

/**
 * ajax异步基础提交
 * @param u 链接
 * @param data 参数
 * @param ty 类型 get post
 * @param fun 执行成功之后要运行的函数
 * @param o 失败 1,弹出消息2，运行函数3，既弹消息又运行函数
 * @param p 成功 1,弹出消息2，运行函数3，既弹消息又运行函数
 */
function jcAjax(u,data,ty,fun,o,p) {
    o = o ? o : 3;
    p = p ? p : 1;
    showLoad()
    $.ajax({
        url: u,
        data: data,
        type: ty,
        dataType:'JSON',
        success:function (data) {
            let showMsg = data.result ? data.result : data.msg;
            let r = data.status === -1 ? o : p;
            let icon = data.status === -1 ? 5 : 6;
            jcAjax_result(showMsg,fun,r,icon);
        },
        complete:closeLoad
    })
}

/**
 * ajax同步基础提交
 * @param u 链接
 * @param data 参数
 * @param ty 类型 get post
 * @param fun 执行成功之后要运行的函数
 * @param o 失败 1,弹出消息2，运行函数3，既弹消息又运行函数
 * @param p 成功 1,弹出消息2，运行函数3，既弹消息又运行函数
 */
function asyncAjax(u,data,ty,fun,o,p) {
    o = o ? o : 3;
    p = p ? p : 1;
    showLoad()
    $.ajax({
        url: u,
        data: data,
        type: ty,
        async:false,
        dataType:'JSON',
        success:function (data) {
            let showMsg = data.result ? data.result : data.msg;
            let r = data.status === -1 ? o : p;
            let icon = data.status === -1 ? 5 : 6;
            jcAjax_result(showMsg,fun,r,icon);
        },
        complete:closeLoad
    })
}
// 不加载loadding层
function jcAjax_noLoad(u,data,ty,fun,o,p) {
    o = o ? o : 3;
    p = p ? p : 1;
    $.ajax({
        url: u,
        data: data,
        type: ty,
        dataType:'JSON',
        success:function (data) {
            let showMsg = data.result ? data.result : data.msg;
            let r = data.status === -1 ? o : p;
            let icon = data.status === -1 ? 5 : 6;
            jcAjax_result(showMsg,fun,r,icon);
        },
    })
}
// 不加载lodding层 不弹窗
function jcAjax_notf(u,data,ty,fun) {
    let t = ty ? ty : "get";
    let d = data ? data : {};
    $.ajax({
        url: u,
        data: d,
        type: t,
        dataType:'JSON',
        success:function (result) {
            if (fun) fun(result)
        }
    })
}
function jcAjax_notf_noParam(u,fun) {
    jcAjax_notf(u, {},"get",fun);
}
function jcAjax_notf_get(u,data,fun) {
    jcAjax_notf(u, data,"get",fun);
}
// 1,弹出消息2，运行函数3，既弹消息又运行函数
function jcAjax_result(data,fun,o,i) {
    if (o === 1) {
        show_l_m(data,i)
    } else if (o === 2) {
        if (fun) fun(data)
    } else if (o === 3) {
        show_l_m(data,i)
        if (fun) fun(data)
    }
}
// 无参请求成功，执行函数
function noParam_sucfun_get(u,fun) {
    noParam_get(u, fun,1,2);
}
// 有参get请求成功，执行函数
function param_sucfun_get(u,data,fun) {
    param_get(u,data,fun,1,2);
}
function param_sucAndfun_get(u,data,fun) {
    param_get(u,data,fun,1,3);
}
// 有参get请求成功，弹出消息
function param_suc_get(u,data,fun) {
    param_get(u,data,fun,1,1);
}

/**
 * 有参get请求方法
 * @param u
 * @param data
 * @param fun
 * @param o
 * @param p
 */
function param_get(u,data,fun,o,p) {
    jcAjax(u,data,'get',fun,o,p)
}
/**
 * 不带请求参数的get方法
 * @param u
 * @param fun
 * @param o
 * @param p
 */
function noParam_get(u,fun,o,p) {
    jcAjax(u,{},'get',fun,o,p)
}
// 有参post请求
function param_post(u,data,fun,o,p) {
    jcAjax(u,data,'post',fun,o,p)
}
// 有参执行成功，执行函数
function param_suc_post(u,data,fun) {
    jcAjax(u,data,'post',fun,1,2)
}
// 刷新页面
function reloadjsonh() {
    location.reload(true);
    window.location.reload(true);
}

// 设置copy内容
function setCopyText(text,e,id) {
    let btn = $("<a class='u_btn' data-clipboard-snippet>[复制链接]</a>").attr("data-clipboard-text",text);
    // $(this).prepend(btn);
    btn.click(function () {
        setShareNum(id);
    });
    e.prepend(btn);
    let c = new ClipboardJS(btn[0]);
    c.on("success", function() {
        show_suc_msg(copy_suc);
    });
    c.on("error", function() {
        show_err_msg(copy_err);
    });
}

function setShareNum(id) {
    jcAjax_notf_noParam("/setShareNum/" + id);
}

// 判断对象是不是null
function isNull_O(o) {
    // console.log("isNull_O(" + o)
    // console.log("isNull_O(" + typeof o)
    return o == null || o === "null" || typeof o === "undefined" || false || o === "undefined" || o === "";
}

// 设置select 不可用
function disabled_select(o) {
    let e = getElementByO(o);
    e.attr("disabled","disabled");
}

function remove_disabled(o) {
    let e = getElementByO(o);
    e.removeAttr("disabled");
}

function getElementByO(o) {
    let e;
    if (typeof o === "string") {
        if (o.indexOf("#") >= 0) {
            o = o.subString(1);
        }
        e = $("#" + o);
    } else if (typeof o === "object") {
        e = $(o);
    }
    return e;
}

/**
 * 创建资源元素
 * @param type link,script
 * @param url
 * @param id
 * @param location body,header
 */
function createResources(type,url,id,location) {
    if (!location) {
        location = "body";
    }
    let e = document.createElement(type);
    if (type === "link") {
        e.rel = "stylesheet";
        e.href = url;
    } else if (type === "script") {
        e.type = "text/javascript";
        e.src = url;
    }
    e.id = id;
    if (location === "body") {
        document.body.appendChild(e);
    } else if (location === "head") {
        document.head.appendChild(e);
    }
}

/**
 * 判断该页面是否被iframe
 */
function isframe() {
    return self.frameElement && self.frameElement.tagName === "IFRAME" ;
}

/**
 * 移除关于fish的元素
 */
function clearFishCommont() {
    $("#fish_script").remove();
    $("#fish_type").remove();
    $("#fishCXontainer").remove();
    // $("#jsi-flying-fish-container").html("");
}

function requestToken(request,token) {
    request.setRequestHeader(lo_token, token);
}

/**
 * 今天还剩多少秒
 * @returns {number}
 */
function todayScond() {
    let timenow = new Date();
    let second = timenow.getHours() * 3600; //小时
    second += timenow.getMinutes() * 60;//分钟
    second += timenow.getSeconds(); //秒
    return 86400 - second; //剩余秒
}

/**
 * 将以base64的图片url数据转换为Blob
 * @param urlData
 *            用url方式表示的base64图片数据
 */
function convertBase64UrlToBlob(urlData){
    var bytes=window.atob(urlData.split(',')[1]);        //去掉url的头，并转换为byte  

    //处理异常,将ascii码小于0的转换为大于0  
    var ab = new ArrayBuffer(bytes.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < bytes.length; i++) {
        ia[i] = bytes.charCodeAt(i);
    }
    return new Blob( [ab] , {type : 'image/png'});
}

/**
 * base64转FIle
 * @param base64
 * @returns {File}
 */
function convertBase64UrlToFile(base64){
    let bstr = atob(base64.split(',')[1]);// atob是将base64编码解码，去掉data:image/png;base64,部分
    let n = bstr.length;
    let u8arr = new Uint8Array(n);

    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
// 写法同上
    return new File([u8arr], Date.now() + '.jpg', {type: 'image/jpg'})
}

// 复制文本
function copyText(id) {
    let input = document.getElementById(id);
    input.value = input.value;
    input.select(); // 选中文本
    document.execCommand("copy"); // 执行浏览器复制命令
    layer.msg("复制成功~");
}