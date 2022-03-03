// 创建分页按钮
function createPbtn() {
    loadshuiguo();
    cur_page = parseInt($("#ref_cur").val().trim());
    all_page = parseInt($("#ref_all").val().trim());
    if (all_page === 0) {
        let html = '<div class="day"><div class="postTitle" style="border: 0">没有搜索到数据~';
        let seachTitle = $("#seachTitle").text();
        if (seachTitle.indexOf('>>>搜索：') !== -1) {
            let seachkey = seachTitle.replace('>>>搜索：','');
            html += '   建议直接百度：<a style="text-decoration:underline !important;" target="_blank" href="https://www.baidu.com/s?wd='+seachkey+'">'+seachkey+'</a>';
        }
        html +=         '</div><div class="clear"></div></div>';
        $('#bolg_contents').html(html);
    }
    // console.log(all_page)
    let ul = $(".page_li");
    ul.empty();
    let all_show = 6;

    let end = cur_page + 2;
    if (end > all_page) {
        end = all_page;
    }
    let star = cur_page - 2;
    if (end - star <= 3) {
        star = all_page - 4;
    }
    if (cur_page > 5 && end <= all_page) {
        for (let i = star; i < end + 1; i++) {
            if (cur_page === i) {
                ul.append("<li class='active'>" + i + "</li>");
            } else {
                ul.append("<li onclick='toPage(" + i + ")'>" + i + "</li>");
            }
        }
    } else {
        if (all_show > all_page) {
            all_show = all_page + 1;
        }
        for (let i = 1; i < all_show; i++) {
            if (cur_page === i) {
                ul.append("<li class='active'>" + i + "</li>");
            } else {
                ul.append("<li onclick='toPage(" + i + ")'>" + i + "</li>");
            }
        }
    }
    // console.log($(".day").length)
    if (all_page > 1) {
        $(".page_container").show();
        if ($(".day").length > 4) {
            $("#twoPagebtn").show();
        } else {
            $("#twoPagebtn").hide();
        }
    } else {
        $(".page_container").hide();
    }
    groupShare();
    createRowLabel();
}
// 加载水果
function loadshuiguo() {
    let c ={
        cuteicon: ["icon-caomei", "icon-boluo", "icon-huolongguo", "icon-chengzi", "icon-hamigua", "icon-lizhi", "icon-mangguo", "icon-liulian", "icon-lizi", "icon-lanmei", "icon-longyan", "icon-shanzhu", "icon-pingguo", "icon-mihoutao", "icon-niuyouguo", "icon-xigua", "icon-putao", "icon-xiangjiao", "icon-ningmeng", "icon-yingtao", "icon-taozi", "icon-shiliu", "icon-ximei", "icon-shizi"],
    }
    let le = $(".postTitle2").length;
    for (var i = 0; i < le; i++) {
        var num = Math.floor(Math.random() * c.cuteicon.length);
        $(".postTitle2").eq(i).before('<svg class="icon" aria-hidden="true"><use xlink:href="#' + c.cuteicon[num] + '"></use></svg>');
    }
}
var pageUrl = "/page?" + navUserid + condition + "pageNumber=";
// 跳转至几页
function toPage(i) {
    loadPage(pageUrl + i);
}
function loadPage(url) {
    showLoad()
    $('#bolg_contents').load(url,function (response,status) {
        if (status === "success") {
            createPbtn();
            $("body,html").animate({scrollTop: $("#header").height() - 50}, 600);
        } else {
            show_l_m("服务器错误~",0);
        }
        closeLoad();
    });
}
$(function (){
    let msg = showLoad();
    createPbtn();
    $(".prev").click(function () {
        if (cur_page > 1) {
            loadPage(pageUrl + (cur_page - 1));
        }
    });
    $(".next").click(function () {
        if (cur_page < all_page) {
            loadPage(pageUrl + (cur_page + 1));
        }
    });
    closeLoad(msg)
})

function groupShare() {
    shareBtn()
    copyFX()
}
// 复制链接按钮
function copyFX() {
    // 在每个span里面添加复制分享按钮
    let sps = $(".b_c_s");
    if (sps.length) {
        sps.each(function (index,e) {
            let href = get_item("b_host") + $("#bok_url" + index).attr("href").trim();
            let text = $("#bok_url" + index).text().trim();
            text = href + "\n" + text + cpoy_boke_name;
            setCopyText(text,$(this),$(this).attr("vlt"));
        })
    }
}
// 分享按钮
function shareBtn() {
    let shares = $(".wzShare");
    if (shares) {
        for (let i = 0; i < shares.length; i++) {
            let u = get_item("b_host") + $("#bok_url" + i).attr("href").trim();
            let b_t = $("#bok_url" + i).text().trim();
            let t = "苏喂苏喂喔 - " + b_t;
            // let d = $("#bok_desc" + i).text().trim().substring(0,100);
            $('#wz_share_' + i).share({
                url                 : u, // 网址，默认使用 window.location.href
                // source              : '', // 来源（QQ空间会用到）, 默认读取head标签：<meta name="site" content="http://overtrue" />
                title               : t, // 标题，默认读取 document.title 或者 <meta name="title" content="share.js" />
                // origin              : '', // 分享 @ 相关 twitter 账号
                description         : b_t, // 描述, 默认读取head标签：<meta name="description" content="PHP弱类型的实现原理分析" />
                // image               : '/imgs/a.png', // 图片, 默认取网页中第一个img标签
                sites               : ['qzone', 'qq', 'weibo','wechat'], // 启用的站点
                disabled            : ['google', 'facebook', 'twitter'], // 禁用的站点
                wechatQrcodeTitle   : '微信扫一扫：分享', // 微信二维码提示文字
                wechatQrcodeHelper  : '<p>微信里点“发现”，扫一下</p><p>二维码便可将本文分享至朋友圈。</p>'
            })
            let a_t_s = $('#wz_share_' + i + ' a');
            a_t_s.each(function (index,e) {
                $(this).click(function () {
                    setShareNum($('#wz_share_' + i).attr("vlt"));
                });
            })
        }
    }
}

// 创建标签
function createRowLabel() {
    let labels = $(".labels_box");
    if (labels.length > 0) {
        labels.each(function (index,e) {
            let lb = $("#labels_box_" + index);
            let lid_t = lb.attr("labelids");
            let lnamet = lb.attr("labelnames");
            if (lid_t !== "0" && lnamet) {
                let lids = lid_t.split(",");
                let lnames = lnamet.split(",");
                let lstr = "标签：";
                let len = lids.length;
                for (let i = 0; i < len; i++) {
                    lstr += '<a href="/?navTitle=标签：'+lnames[i]+'&lId='+lids[i]+'">'+lnames[i]+'</a>';
                    if (len - 1 !== i) {
                        lstr += '<b>,</b>';
                    }
                }
                $(this).append(lstr);
            }
        })
    }
}