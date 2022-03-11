// 设置置顶
function topAdd() {
    token_ajax("/t/top/add",{bokeId:b_boke_2.bokeId},function () {
        $(".to_top_e").text("[取消置顶]");
        $(".to_top_e").attr("onclick","topCancel()");
    })
}
// 取消置顶
function topCancel() {
    token_ajax("/t/top/cancel",{bokeId:b_boke_2.bokeId},function () {
        $(".to_top_e").text("[设置置顶]");
        $(".to_top_e").attr("onclick","topAdd()");
    })
}

// 关闭窗口是否提示当前未保存的状态
var is_confirm = false;
// 是否是文章页
var is_boke_page = b_boke_2.editorType;
(function(){
    // 设置浏览量
    let bokexzzt = get_item(boke_look + id);
    if (!bokexzzt) {
        jcAjax_notf_noParam("/setLookup/" + id,function () {
            set_item(boke_look + id);
        });
    }
    // 关闭窗口时弹出确认提示
    $(window).bind('beforeunload', function(){
        // 只有在标识变量is_confirm不为false时，才弹出确认提示
        if(window.is_confirm !== false) {
            let bId = b_boke_2.bokeId;
            let html = editor.txt.html().trim();
            let h_re = html.replace("<p>","").replace("</p>","").replace("<br/>","").replace("&nbsp;","").trim();
            if (h_re.length > 0 && bId > 0) {
                return not_save;
            }
        }
    })
})();

// 创建标签元素
function createLabelBox() {
    let labIds = b_boke_2.labelIds;
    let labNames = b_boke_2.labNames;
    if (labIds && labIds.length > 0) {
        let lbox = $("#labels_box");
        let lstr = "标签：";
        let len = labIds.length;
        for (let i = 0; i < len; i++) {
            lstr += '<a href="/?navTitle=标签：'+labNames[i]+'&lId='+labIds[i]+'">'+labNames[i]+'</a>';
            if (len - 1 !== i) {
                lstr += '<b>,</b>';
            }
        }
        lbox.append(lstr);
    }
}
$(function () {
    showLoad()
    setCodeTheme(is_boke_page);
    createLabelBox();
    //复制分享按钮
    let sps = $(".b_c_s");
    if (sps.length) {
        sps.each(function () {
            let href = get_item("b_host") + "/bokexq/" + id;
            let text = $("#cb_post_title_url span").text().trim();
            text = href + "\n" + text + cpoy_boke_name;
            setCopyText(text,$(this),id);
        })
    }

    // 博客分两种情况，一种是富文本一种是markdown语法
    if (b_boke_2.editorType == 2) {
        $("#md_msg").text(b_boke_2.mdContent)
        // editormd.markdownToHTML("cnblogs_post_body");
        testEditor = editormd.markdownToHTML("cnblogs_post_body", {//注意：这里是上面DIV的id
            htmlDecode: "style,script,iframe",
            emoji: true,
            taskList: true,
            // 代码美化
            previewCodeHighlight : true,
            path    : "/editor/editormd/lib/",
            tex: true, // 默认不解析
            flowChart: true, // 默认不解析
            sequenceDiagram: true, // 默认不解析
            codeFold: true,
        });
        // 添加_blank属性
        addA_target();
    } else {
        $("#cnblogs_post_body").html(b_boke_2.bokeCont)
        editormd.markdownToHTML("cnblogs_post_body",{previewCodeHighlight : false});
    }

    // 评论内容显示
    let comm_cos = $(".comm_count");
    let comm_count = 1;
    comm_cos.each(function (){
        editormd.markdownToHTML("comm_count" + comm_count);
        comm_count ++;
    })
    let rep_cos = $(".reply_count");
    let rep_count = 1;
    rep_cos.each(function (){
        editormd.markdownToHTML("reply_count" + rep_count);
        rep_count ++;
    })

    // 给代码添加复制功能
    addCodeCopy(b_boke_2.editorType === 2 ? 'md' : 'wd');

    if (get_item(lo_userId) == b_userId) {
        // $(".to_edit_e").show();
        $(".to_top_e").show();
        $(".to_top_e").text(b_boke_2.topOrder === 0 ? '[设置置顶]' : '[取消置顶]');
        $(".to_top_e").attr("onclick",b_boke_2.topOrder === 0 ? 'topAdd()' : 'topCancel()');
    }

    let imgs = $('#cnblogs_post_body img:not(.clippy)');
    // layer弹窗方式预览图片
    // initImgopen(imgs)
    // 使用插件
    if (imgs.length > 0 ) {
        // 获取第一个图片为分享是的图片
        let first_src = imgs[0].src;
        $("#firstImg").attr("src",first_src);

        let arrimg = [];
        imgs.each(function () {
            arrimg.push({
                url: $(this).attr("src"),
                angle: 0
            })
        })
        // zx-image-viewer.min.js插件方式预览图片
        zxShowImg(imgs,arrimg);
    }
    // 创建目录
    create_ml();
    // var b_he = document.documentElement.clientHeight;
    // 给目录增加滚动跟随
    let h_h = $("#header").height();
    let csh_top = 50;
    $(".blogs_ml").css("top",csh_top+ "px")
    /*监听页面滚动 mulu 滚动*/
    $(window).scroll(function(){
        var s_top = Number($(this).scrollTop());  // 获取滚动条，滚动刻度
        if (s_top > 346) {
            $(".blogs_ml").css("top",(s_top-h_h + csh_top) + "px")
        }else {
            $(".blogs_ml").css("top",csh_top + "px")
        }
    })
    // 给复制按钮增加横向滚动跟随的效果
    let pres = $("#cnblogs_post_body pre");
    pres.each(function (index,e) {
        let t = $(this).width();
        let l = $(".clippy").css("left")
        let ln = Number(l.replace('px',''));
        // $(".clippy").css("left",t + "px")
        console.log("原本的" + ln)
        $(this).scroll(function(){
            let sLeft = Number($(this).scrollLeft());  // 获取滚动条，滚动刻度
            console.log('向右滚动距离：' + sLeft)
            console.log('向右总偏移量：' + (ln + sLeft))
            // $(".clippy").css("left",(ln + sLeft) + "px")
            $(this).find('.clippy').css("right",(- sLeft) + "px")
        })
    })

    // 判断目录是否展开
    ml_open_state();
    // 目录点击事件 ml_state = 1 关闭状态 0 是打开状态
    $(".zkml_btn").click(function (){
        let state = get_item("ml_state");
        // console.log("toggle" + this.toggle + "~state " + state)
        if (!isNull_O(state) && Number(state) === 1) {
            set_item2("ml_state",0);
            $(".zkml_btn").text("打开")
            $("#blogs_ml").hide();
        }else{
            set_item2("ml_state",1);
            $(".zkml_btn").text("收起")
            $("#blogs_ml").show();
        }
    })
    // 分享按钮
    setShareBtn();

    // 初始化视频播放器大小
    initIframVideo();
    // 移除元素
    $(".header-link").remove();
    closeLoad();

//    获取分享，浏览，喜欢的数量
    getSLLNum();
})

// 初始化视频大小
function initIframVideo() {
    let videos = $("#cnblogs_post_body iframe");
    videos.each(function (index,e) {
        $(this).css("width","800px");
        $(this).css("height","600px");
        $(this).parent().css("display","flex");
        $(this).parent().css("justify-content","center");
        $(this).click(function () {
            setShareNum($('#boke_fenxiang' + i).attr("vlt"));
        });
    })
}
// 渲染分享按钮
function setShareBtn() {
    let shares = $(".wzShare");
    if (shares) {
        let b_t = $("#cb_post_title_url").text();
        /*let b_x_imgs = $("#cnblogs_post_body img");
        let f_i_s = share_img;
        if (b_x_imgs.length > 0) {
            // f_i_s = b_x_imgs[0].attr("src");
            f_i_s = b_x_imgs[0].src;
        }
        $("#firstImg").attr("src",f_i_s);*/
        let f_i_s = $("#firstImg").attr("src");
        for (let i = 1; i < shares.length + 1; i++) {
            $('#boke_fenxiang' + i).share({
                description         : b_t, // 描述, 默认读取head标签：<meta name="description" content="PHP弱类型的实现原理分析" />
                image               : f_i_s,
                sites               : ['qzone', 'qq', 'weibo','wechat'] // 启用的站点
            });
            let a_t_s = $('#boke_fenxiang' + i + ' a');
            a_t_s.each(function (index,e) {
                $(this).click(function () {
                    setShareNum($('#boke_fenxiang' + i).attr("vlt"));
                });
            })
        }
    }
}

// 添加——blank 新标签页打开
function addA_target() {
    let links = $("#cnblogs_post_body a:not(.copy-btn)");
    for (let i = 0; i < links.length; i++) {
        links[i].target = '_blank';
    }
}

// 设置全局目录打开状态
function ml_open_state() {
    let state = get_item("ml_state");
    if (isNull_O(state) || Number(state) === 0) {
        $(".zkml_btn").text("打开")
        $("#blogs_ml").hide();
    } else {
        $(".zkml_btn").text("收起")
        $("#blogs_ml").show();
    }
}

// 生成目录 )
function create_ml() {
    let mlbox = $("#blogs_ml");
    let ul = "<ul>"
    //查找h1-h6 获取所有的标题
    $("#cnblogs_post_body :header").each(function(){
        let title = $(this).text();
        if (b_boke_2.editorType == 1) {
            let a = '<a name="'+ title +'" class="reference-link"></a>';
            $(this).append(a);
        }
        let tagName = $(this)[0].tagName;
        let marLe = (Number(tagName.substring(1)) - 1) * 10;
        ul += "<li style='margin-left: "+ marLe +"px'><a href='#"+ title +"' target='_self'>"+ title +"</a></li>"
    });
    ul += "</ul>";
    if (ul !== "<ul></ul>") {
        mlbox.append(ul)
        $(".blogs_ml").show();
    } else {
        $(".blogs_ml").hide();
    }
}

const E = window.wangEditor
const editor = new E("#wangEdit")
editor.config.height = 200
// editor.config.zIndex = 500
// 取消自动 focus
editor.config.focus = false
editor.config.placeholder = '' // 不想使用 placeholder ，赋值为空字符串即可
// 还可以修改历史记录的步数。默认 30 步
editor.config.historyMaxSize = 50 // 修改为 50 步
// 默认情况下，显示所有菜单
// editor.config.menus.fullscreen = false
editor.config.menus = [
    'bold',
    'foreColor',
    'link',
    'quote',
    'image',
    'code',
    'emoticon',  // 表情
]
// 自定义表情
editor.config.emotions = getEmoji();

//插入代码语言配置
editor.config.languageType = wd_language
editor.config.customUploadImg = function (resultFiles, insertImgFn) {
    // resultFiles 是 input 中选中的文件列表
    let daw = new FormData();
    let index = 0;
    let maxLen = 5 * 1024 * 1024;// M
    let minLen = 0;
    for(let i=0;i<resultFiles.length;i++){
        let file = resultFiles[i];
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
editor.create()

// 创建评论元素
function createplTab(pl) {
    let face = get_item(lo_face);
    let userName = get_item(lo_username);
    if (!face) {
        face = face_def;
    }
    if (!userName) {
        userName = user_name_def;
    }
    let index = $(".one_comm").length + 1;
    let plBox = $("#comm_listbox");
    let plTab = "<div class='one_comm'><table><tbody><tr>" +
        "<td class='cm_td_img'><img width='150' src='"+ face +"' alt='"+ userName +"' /><p>"+userName+"</p></td>" +
        "<td class='cm_td_cont'><div class='comm_title'>发表于：<span>" + pl.commTime + "</span></div>" +
        "<div class='comm_count' id='comm_count"+ index +"'>" + pl.commTont + "</div><ul id='replyUl"+ index +"'></ul></td>" +
        "</tr><tr><td class='cm_td_img'></td><td class='cm_td_hf' id='' onclick='openReplyBox("+ pl.commId +","+ index +")'>回复</td></tr></tbody></table></div>";
    plBox.append(plTab);
}
// 创建回复元素
function createReplyBox(reply) {
    let face = get_item(lo_face);
    let userName = get_item(lo_username);
    if (!face) {
        face = face_def;
    }
    if (!userName) {
        userName = user_name_def;
    }
    let repBox = $("#replyUl" + replyB_id);
    let index = $("#replyUl" + replyB_id + " li").length + 1;
    let liStr = "<li><div class='rep_count'><img width='40' height='40' alt='"+ userName +"' src='"+ face +"' />" +
        "<div class='cm_td_rp_sp'><span>回复：</span><span>"+ reply.replyTime +"</span></div></div>" +
        "<div class='reply_count' id='reply_count"+ index +"'>"+ reply.replyContent +"</div></li>";
    repBox.append(liStr);
}
// 评论
function pinglun() {
    let p_count = 0;
    if(!get_item(lo_token)) {
        p_count = ls.get("pinglun");
        if (p_count) {
            p_count = parseInt(p_count);
            if (p_count >= 10) {
                layer.msg("每天只能评论10次呐，如有需要请联系管理员~")
                return false;
            }
        } else {
            p_count = 0;
        }
    }
    let bId = b_boke_2.bokeId;
    let text = editor.txt.text().trim();
    let html = editor.txt.html().trim();

    let h_re = html.replace("<p>","").replace("</p>","").replace("<br/>","").replace("&nbsp;","").trim();
    if (h_re.length > 0 && bId > 0) {
        showLoad()
        is_confirm = true;
        let c_p_userId = get_item(lo_userId);
        if (ccc_tore) {
            param_post("/reply/to",{replyContent:html,bokeId:bId,commentId:ccc_tore,userId:c_p_userId},function (data) {
                show_l_m("回复成功~",6);
                // 清空内容
                editor.txt.clear()
                keydd = true;
                closeRelAf();
                ls.set("pinglun",p_count + 1,todayScond() * 1000);
                createReplyBox(data);
            },1,2)
        } else {
            param_post("/comment/to",{commTont:html,bokeId:bId,userId:c_p_userId},function (data) {
                show_l_m("评论成功~",6);
                // 清空内容
                editor.txt.clear()
                ls.set("pinglun",p_count + 1,todayScond() * 1000)
                createplTab(data)
            },1,2)
        }
    } else {
        layer.msg("请先输入内容~")
        return false;
    }
}

let keydd = true;
// 记录评论id
let ccc_tore ;
// 记录回复id
let replyB_id;
function openReplyBox(id,r_b_i) {
    ccc_tore = id;
    keydd = false;
    replyB_id = r_b_i;
    let width = 700;
    let left = '20%';
    let sceenw = document.documentElement.clientWidth;
    if (sceenw < 700) {
        width = sceenw;
        left = '-5%';
    }
    $("#repl_ca_btn").show();
    // 滚动条当前中间位置距离顶部高度
    // var y = ScollPostion().top + $(window).height()/2;
    // var y = ScollPostion().top;
    layer.open({
        type: 1,//1:本地  2:url  
        title: "回复窗口",
        // shadeClose: true,//是否点击遮罩关闭
        shade: 0.6,//遮罩层
        // move: ".layui-layer-content #editbox",
        moveType: 1,
        resize:false,// 是否允许拉伸
        // maxmin: true, //开启最大化最小化按钮
        fixed:false,//即鼠标滚动时，层是否固定在可视区域。如果不想，设置fixed: false即可
        area:[width + 'px','320px'],
        // offset: y + 'px',
        offset: ['0',left],
        // offset: '20%',
        // area: [img.width + 'px', img.height+'px'],
        anim:0,//动画  -1不显示
        content: $("#editbox"),
        cancel: function(){
            closeRelAf();
        }
    });
}

// 点击取消回复窗口
function closeRelAf() {
    keydd = true;
    ccc_tore = null;
    // replyB_id = null;
    ccc_tore = null;
    closelayer()
    $("#repl_ca_btn").hide();
}

$(document).ready(function () {
}).keydown(function (e) {
    if (keydd) {
        if (e.which === 27) {
            closelayer()
        }
    }
});

/**
 * 喜欢
 * @param id
 */
function setLikeNum() {
    if (!get_item("like" + id)) {
        noParam_sucfun_get("/setLikeNum/" + id,function () {
            set_item("like" + id, id)
            layer.msg("谢谢您的支持~~")
            let lb = $("#b_ln");
            lb.text(lb.val() + 1)
        })
    } /*else {
        layer.msg("已经喜欢该文章~~")
    }*/
}

function getSLLNum() {
    noParam_sucfun_get("/SLLNum/" + id,function (res) {
        $("#b_ll").text(res.LOOKNUM);
        $("#b_ln").text(res.LIKENUM);
        $("#b_sn").text(res.SHARENUM);
    })
}