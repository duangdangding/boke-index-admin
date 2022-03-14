function isMobile() {
    var userAgentInfo = navigator.userAgent;
    var mobileAgents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"];
    var mobile_flag = false;
    for (var v = 0; v < mobileAgents.length; v++) {
        if (userAgentInfo.indexOf(mobileAgents[v]) > 0) {
            mobile_flag = true;
            break
        }
    }
    var screen_width = window.screen.width;
    var screen_height = window.screen.height;
    if (screen_width < 500 && screen_height < 800) {
        mobile_flag = true
    }
    return mobile_flag
}

var mobile_flag = isMobile();
var blogTitle = $("#blogTitle").html();
$("#blogTitle").html("<div class='vertical'>" + blogTitle + "</div>");

// 浅白色系
function light_white() {
    $("#reading").remove();
    $("#darkly").remove();
    clearFishCommont();
    if (!mobile_flag) {
        let s = get_item(switchFish);
        if (isNull_O(s) || s === 'on') {
            createResources("script","/js/fish.js","fish_type");
        }
    }
}
// 暗黑主题
function furvous() {
    $("#reading").remove();
    clearFishCommont();
    createResources("link","/css/darkly.css","darkly");
    if (!mobile_flag) {
        let s = get_item(switchFish);
        if (isNull_O(s) || s === 'on') {
            createResources("script","/js/fish2.js","fish_type");
        }
    }
}
// 阅读模式
function reading() {
    $("#darkly").remove();
    clearFishCommont();
    createResources("link","/css/reading.css","reading");
    if (!mobile_flag) {
        let s = get_item(switchFish);
        if (isNull_O(s) || s === 'on') {
            createResources("script","/js/fish3.js","fish_type");
        }
    }
}
if (!mobile_flag) {
    $("#footer").before('<div id="jsi-flying-fish-container" class="container"></div>')
}
let skins = localStorage.getItem("skin");
if (typeof skins === "undefined" || isNull_O(skins) || "light_white" === skins) {
    light_white()
} else if ("furvous" === skins) {
    furvous()
} else if ("reading" === skins) {
    reading()
}

function miluframe(setting) {
    var c = {
        cuteicon: ["icon-caomei", "icon-boluo", "icon-huolongguo", "icon-chengzi", "icon-hamigua", "icon-lizhi", "icon-mangguo", "icon-liulian", "icon-lizi", "icon-lanmei", "icon-longyan", "icon-shanzhu", "icon-pingguo", "icon-mihoutao", "icon-niuyouguo", "icon-xigua", "icon-putao", "icon-xiangjiao", "icon-ningmeng", "icon-yingtao", "icon-taozi", "icon-shiliu", "icon-ximei", "icon-shizi"],
        isGratuity: true
    };
    $.extend(c, setting);
    allpage(c)
}

function allpage(c) {
    let custom = "";
    for (let i = 0; i < c.custom.length; i++) {
        let co = c.custom[i];
        let h = co.html_str;
        let id = co.id_str;
        let tar_str = "";
        let id_str = "";
        if (id) {
            id_str = id;
        }
        if (co.istarget) {
            tar_str = '_black';
        }
        custom = custom + '<li id="' + id_str + '"><a target="' + tar_str + '" href="' + c.custom[i].link + '">' + c.custom[i].name + "</a>";
        if(h) {
            custom += h;
        }
        custom += "</li>";
    }
    $("#navList").html(custom);
    /* 更换皮肤 */
    let skin = '<div class="select_skin"><ul style="width: 85px"><li class="light_white">浅白色系</li><li class="furvous">暗黑色系</li><li class="reading">阅读模式</li></ul></div>';
    tippy(".skin_btn", {
        content: skin,
        theme: "tomato",
        allowHTML: true,
        animation: "scale",
        duration: 500,
        arrow: true,
        hideOnClick: "false",
        interactive: true
    });

}
$(window).resize(function () {
    var w = document.body.clientWidth;
    if (w > 1300) {
    } else {
        if (960 < w) {
            if ($("#mainContent").css("width") != "90%") {
                $("#mainContent").css("width", "90%")
            }
        } else {
            if (0 < w) {
                if ($("#mainContent").css("width") != "90%") {
                    $("#mainContent").css("width", "90%")
                }
            }
        }
    }
});
// 导航栏跟随
var json = $("#navigator").offset();
var oTop = json.top;
var sTop = 0;
var sTop2 = 0;
$(window).scroll(function () {
    sTop = $(this).scrollTop();
    if (sTop >= oTop) {
        // console.log("sTop2:" +sTop2+ "-sTop:" + sTop+ "-oTop:" + oTop)
        if (sTop2 <= sTop && sTop2 < oTop) {
            $("#navigator").css({"position": "static"});
            $("#blogTitle").css({"margin-bottom": "0"})
        } else {
            $("#navigator").css({"position": "fixed", "top": "0"});
            $("#blogTitle").css({"margin-bottom": '100px'})
        }
    } else {
        $("#navigator").css({"position": "static"});
        $("#blogTitle").css({"margin-bottom": "0"})
    }
    sTop2 = sTop;
    $(".fly_top").css("display", "inline-block");
    // $(".catalogueMain").attr("style", "position:fixed;top:10px;width:230px;height:calc(100% - 100px);overflow:auto");
    // $("#sideBarMain").attr("style", "position:relative;width:230px;overflow-y:auto !important")
});
// $("#sideBar").before('<div id="catalogue" ><div class="catalogueMain"></div><div>');

$("#catalogue").css("display", "none");
$("#catalogue #navCategory ul li a").click(function () {
    var w = $(window).width();
    if (w < 767) {
        $("#catalogue").attr("style", "visibility: hidden; clip-path: circle(30px at calc(100%) 100%); transition: all 0.5s ease-in-out 0s;");
        setTimeout(function () {
            $("#catalogue").css("display", "none")
        }, 500)
    }
});
$(window).scroll(function () {
    var le = $("._labBox").length;
    // var scrollTop = $(window).scrollTop() + 80;
    for (var i = 0; i < le; i++) {
        var oftop = $("._labBox").eq(i).offset().top;
        if ($(document).scrollTop() >= oftop) {
            var name = $("._labBox").eq(i).find("a").attr("name");
            $("#catalogue").find("li").attr("style", "");
            $("#catalogue").find("a").attr("style", "");
            $("." + name).parent().attr("style", "background:#2daebf;");
            $("." + name).parent().find("a").attr("style", "color:#fff")
        }
    }
});
var catalogue = localStorage.getItem("catalogue");
var catalog = localStorage.getItem("catalog");
// console.log("catalogue：" + catalogue + "~~~catalog:" + catalog)
var w = $(window).width();
if (catalogue) {
    if (catalogue === "show") {
        if (w > 767) {
            $("#sideBar").css("display", "inline-block");
            $("#sideBar").attr("style", "visibility: visible; clip-path: circle(100% at 50% 50%); transition: all 0.3s ease-in-out 0s;display:inline-block");
            $("#catalogue").attr("style", "visibility: hidden; clip-path: circle(100% at 50% 50%); transition: all 0.3s ease-in-out 0s;display:none")
        }
    } else {
        if (catalogue === "none") {
            $("#sideBar").attr("style", "visibility: hidden; clip-path: circle(30px at calc(100%) 100%); transition: all 0.5s ease-in-out 0s;");
            setTimeout(function () {
                $("#sideBar").css("display", "none")
            }, 500)
        }
    }
}
if (catalog) {
    if (catalog === "show") {
        if (w > 767) {
            $("#catalogue").css("display", "inline-block");
            $("#catalogue").attr("style", "visibility: visible; clip-path: circle(100% at 50% 50%); transition: all 0.3s ease-in-out 0s;");
            $("#sideBar").attr("style", "visibility: hidden; clip-path: circle(30px at calc(100%) 100%); transition: all 0.5s ease-in-out 0s;display:none")
        }
    } else {
        if (catalog === "none") {
            $("#catalogue").attr("style", "visibility: hidden; clip-path: circle(30px at calc(100%) 100%); transition: all 0.5s ease-in-out 0s;");
            setTimeout(function () {
                $("#catalogue").css("display", "none")
            }, 500)
        }
    }
}
// 点击浅白色系
$(document).on("click", ".light_white", function () {
    localStorage.setItem("skin", "light_white");
    if (typeof is_boke_page !== "undefined") {
        setCodeTheme(is_boke_page);
    }
    light_white();
});
// 点击暗黑主题
$(document).on("click", ".furvous", function () {
    localStorage.setItem("skin", "furvous");
    if (typeof is_boke_page !== "undefined") {
        setCodeTheme(is_boke_page);
    }
    furvous();
});
// 点击阅读模式
$(document).on("click", ".reading", function () {
    localStorage.setItem("skin", "reading");
    if (typeof is_boke_page !== "undefined") {
        setCodeTheme(is_boke_page);
    }
    reading();
});
// 点击回到顶部 
$(document).on("click", ".fly_top", function () {
    $("body,html").animate({scrollTop: 0}, 600)
});
// 乱飞的小白点 如果没有引用conver2.js就不用下面这个
// $(".artice_recommend").click(function () {
//     $(".artice_recommend").attr("onclick", "")
// });
$(".catalogue").click(function () {
    var w = $(window).width();
    if ($("#sideBar").css("visibility") == "hidden") {
        $("#sideBar").css("display", "inline-block");
        $("#sideBar").attr("style", "visibility: visible; clip-path: circle(100% at 50% 50%); transition: all 0.3s ease-in-out 0s;")
        localStorage.setItem("catalogue", "show");
        localStorage.setItem("catalog", "none");
        $("#catalogue").attr("style", "visibility: hidden; clip-path: circle(30px at calc(100%) 100%); transition: all 0.5s ease-in-out 0s;display:none")
    } else {
        $("#sideBar").attr("style", "visibility: hidden; clip-path: circle(30px at calc(100%) 100%); transition: all 0.5s ease-in-out 0s;");
        setTimeout(function () {
            $("#sideBar").css("display", "none")
        }, 500);
        localStorage.setItem("catalogue", "none")
    }
    if (w <= 1300) {
        $("#mainContent").css("width", "90%")
    } else {
        $("#mainContent").css("width", "calc(100% - 250px)")
    }
    return false
});