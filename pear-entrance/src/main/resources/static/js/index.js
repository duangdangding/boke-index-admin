// 水果
var c = {
    cuteicon: ["icon-caomei", "icon-boluo", "icon-huolongguo", "icon-chengzi", "icon-hamigua", "icon-lizhi", "icon-mangguo", "icon-liulian",
        "icon-lizi", "icon-lanmei", "icon-longyan", "icon-shanzhu", "icon-pingguo", "icon-mihoutao", "icon-niuyouguo", "icon-xigua", "icon-putao", 
        "icon-xiangjiao", "icon-ningmeng", "icon-yingtao", "icon-taozi", "icon-shiliu", "icon-ximei", "icon-shizi"]
}

var pageNumber = 1;
//标题
var title = "";
// 分类
var cateId = null;
// 归档
var createDate = "";

$(function () {
    // 分类
    getCateCount();
    // 标签
    createLabels();
    // 归档日期
    getCreates();
    // 浏览排行
    getLookups();

    // loadBlogCalendar();
    // 下方浏览量
    // getWebCount();
})
// 创建标签页
function createLabels() {
    noParam_sucfun_get("/label/get/labelRank",function (labels) {
        let r_len = labels.length;
        if (r_len > 0) {
            let more = "";
            if (r_len >= 40) {
                more = '<a style="float: right;margin-right: 10px" href="/label/labelPage">更多>>></a>'
            }
            let title = "<h3 class='catListTitle'>标  签"+more+"</h3>";
            let la_ul = '<ul class="tag-list">';
            let e = $("#sidebar_postLabel");
            for (let i = 0; i < r_len; i++) {
                let label = labels[i];
                let name = label.labelName;
                let id = label.labelId;
                la_ul += '<li><a href="/?navTitle=标签：'+name+'&lId='+id+'">'+name+'</a></li>';
            }
            la_ul += '</ul>';
            e.append(title + la_ul);
        }
    });
}

// 总访问量
function getWebCount() {
    noParam_sucfun_get("/web/count",function (count) {
        $("#web_day").text(count.dayCount);
        $("#web_count").text(count.webCount);
    });
}

var navTitle;

/**
 * 分类
 */
function getCateCount() {
    $.ajax({
        url : "/getCateCount",
        success : function (data) {
            let res = data.result;
            let r_len = res.length;
            if (r_len > 0) {
                let ele = $("#sidebar_postcategory");
                let counts = 0;
                let ul = "<ul>"
                let dn = '';
                for (let i = 0; i < r_len; i++) {
                    let entry = res[i];
                    let count = entry.count;
                    counts += count;
                    let name = entry.cateName;
                    // onclick=\"qforCate(" + entry.cateId + ",'" + name +"')\"
                    if (i > 14) {
                        dn = 'display:none';
                    } 
                    // ul += "<li style='" + dn + "'><a target='' href='/?navTitle=分类：" + name + "&cateId=" + entry.cateId + "' >" + name +
                    // "(" + count + ")</a></li>";
                    ul += "<li style='" + dn + "'><a target='' href='/?navTitle=分类：" + name + "&cateId=" + entry.cateId + "' >" + name + "</a></li>";
                }
                if (r_len > 15) {
                    ul += "<li class='type_more'><a href='javascript:void(0)' onclick='showTypeMore()'>查看更多...</a></li></ul>";
                } else {
                    ul += "</ul>";
                }
                // var title = "<h3 class='catListTitle'>随笔分类<span>(" + counts + ")</span></h3>";
                let title = "<h3 class='catListTitle'>随笔分类</h3>";
                ele.append(title + ul);
            }
        }
    })
}
/* 显示更多分类 */
function showTypeMore() {
    $("#sidebar_postcategory li").css("display","block")
    $(".type_more").hide()
}
/**
 * 归档日期
 */
function getCreates() {
    $.ajax({
        url : "/archive/get",
        success : function (data) {
            let res = data.result;
            let r_len = res.length;
            if (r_len > 0) {
                let ele = $("#sidebar_postarchive");
                let ul = "<ul>";
                let dn = '';
                for (let i = 0; i < r_len; i++) {
                    let entry = res[i];
                    let date_str = entry.dateStr;
                    if (i > 4) {
                        dn = 'display: none';
                    }
                    ul += "<li style='" + dn + "' data-category-list-item-visible='true'><a href='/?navTitle=归档：" + date_str +
                        "&createDate=" + date_str + "' target=''>" + date_str + "</a></li>";
                }
                if (r_len > 5) {
                    ul += "<li class='gd_more'><a href='javascript:void(0)' onclick='showGdMore()'>查看更多...</a></li></ul>";
                } else {
                    ul += "</ul>";
                }
                let title = "<h3 class='catListTitle'>随笔归档</h3>";
                ele.append(title + ul);
            }
        }
    })
}
function showGdMore() {
    $("#sidebar_postarchive li").css("display","block")
    $(".gd_more").hide()
}
/**
 * 浏览量排名
 */
function getLookups() {
    $.ajax({
        url : "/getLookups",
        success : function (data) {
            let res = data.result;
            let r_len = res.length;
            if (r_len > 0) {
                let ul = "<ul>";
                let ele = $("#sidebar_topviewedposts");
                for (let i = 0; i < r_len; i++) {
                    let entry = res[i];
                    // ul += "<li data-category-list-item-visible='true'><a href='/boke/" + entry.bokeId + "' target=''>" + entry.title + 
                    // "(" + entry.lookNum + ")</a></li>";
                    ul += "<li data-category-list-item-visible='true'><a href='/boke/" + entry.bokeId + "' target=''>" + entry.title + "</a></li>";
                }
                ul += "</ul></div>";
                let title = "<div class='catListView'><h3 class='catListTitle'>阅读排行榜</h3>";
                ele.append(title + ul);
            }
        }
    })
}

/**
 * 搜索 按enter键搜索
 */
function zzk_go_enter(n) {
    if (n.keyCode === 13) return zzk_go(), !1
}
function zzk_go() {
    let s = $("#q_title").val();
    if (s.trim().length > 0) {
        cateId = null;
        createDate = "";
        navTitle = "搜索：" + s;
        // setNavTitle("搜索：" + s,s);
        title = s;
        window.location.href = "/?navTitle=" + navTitle + "&title=" + title;
    }
    // init_bolg();
}

/**
 * 动态组装日历BOX
 */
var cur_daY;
function loadBlogCalendar(param) {
    let t_calendar = $("#cal_body");
    t_calendar.empty();
    let cur_date;
    if (param) {
        var a = param.split("-");
        cur_date = new Date(a[0],a[1],0);
    } else {
        cur_date = new Date();
        cur_daY = cur_date.getDate();
    }
    //封装日期数组
    let year = cur_date.getFullYear(); //年
    let month = cur_date.getMonth(); //月
    let date = cur_date.getDate(); //日
    let pre_month = year + "-" + month;
    let fix_month = year + "-" + (month + 2);
    let car_title_date = year + "年" + (month + 1) + "月";
    // tbody 标题
    let cal_title = "<tr><td colspan='7' align='center'><a style='margin-right: 20px;font-weight: bold' href='javascript:void(0);' " +
        "onclick=\"loadBlogCalendar('" + pre_month + "'); return false;\">&lt;</a><span id='date_title'>" + car_title_date + "</span>" +
        "<a style='margin-left: 20px;font-weight: bold' href='javascript:void(0);'" +
        " onclick=\"loadBlogCalendar('" + fix_month + "'); return false;\">&gt;</a></td></tr>"
    // tbody 星期
    let cal_weed = "<tr><td align='center'>日</td><td align='center'>一</td><td align='center'>二</td><td align='center'>三</td>" +
        "<td align='center'>四</td><td align='center'>五</td><td align='center'>六</td></tr>";
    t_calendar.append(cal_title + cal_weed);
    let date2 = new Date(year, month + 1, 0);
    //本月第一天的星期
    let dayOfTheMonth = new Date(year, month).getDay();
    //当月的天数
    let dates = date2.getDate();
    // 行数
    let rows = Math.ceil((dates + dayOfTheMonth) / 7);
    let allDay = 1;
    for (let i = 0; i < rows; i++) {
        var tr = $("<tr></tr>");
        if (i == 0) {
            if (dayOfTheMonth < 7) {
                for (let j = 0; j < 7; j++) {
                    let td;
                    if (j < dayOfTheMonth) {
                        td = "<td class='CalOtherMonthDay' align='center'></td>";
                    } else {
                        if (allDay == cur_daY) {
                            td = "<td class='CalTodayDay' align='center'>" + allDay + "</td>";
                        } else {
                            td = "<td align='center'>" + allDay + "</td>";
                        }
                        allDay += 1;
                    }
                    tr.append(td);
                }
            } 
        } else if (i == rows - 1) {
            for (let j = 1; j < 8; j++) {
                if (allDay <= dates) {
                    let td;
                    if (allDay == cur_daY) {
                        td = "<td class='CalTodayDay' align='center'>" + allDay + "</td>";
                    } else {
                        td = "<td align='center'>" + allDay + "</td>";
                    }
                    allDay += 1;
                } else {
                    td = "<td class='CalOtherMonthDay' align='center'></td>";
                }
                tr.append(td);
            }
        } else {
            for (let j = 1; j < 8; j++) {
                let td;
                if (allDay == cur_daY) {
                    td = "<td class='CalTodayDay' align='center'>" + allDay + "</td>";
                } else {
                    td = "<td align='center'>" + allDay + "</td>";
                }
                allDay += 1;
                tr.append(td);
            }
        }
        t_calendar.append(tr);
    }
}
