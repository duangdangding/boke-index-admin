// 关闭窗口是否提示当前未保存的状态
var is_confirm = false;
(function(){
    // 关闭窗口时弹出确认提示
    $(window).bind('beforeunload', function(){
        // 只有在标识变量is_confirm不为false时，才弹出确认提示
        if(window.is_confirm !== false) {
            let html ;
            if (boke_type === 1) {
                html = editor.txt.html().trim();
            } else {
                html = testEditor.getMarkdown().trim();
            }
            let title = $("#title").val().trim();
            if (html.length > 0 || title.length > 0) {
                return not_save;
            }
        }
    }).bind('mouseover mouseleave', function(event){
        // mouseleave mouseover事件也可以注册在body、外层容器等元素上
        is_confirm = event.type == 'mouseleave';
    });
})();
var boke_id;
let cg_item = boke_type === 1 ? wd_cg : md_cg;
$(function () {
    initCategory();
    let cur_boke;
    if (boke) {
        cur_boke = boke;
    } else {
        let cg_bk = get_item(cg_item);
        if (cg_bk) {
            cur_boke = JSON.parse(cg_bk);
        }
        // 每隔10s保存一次草稿
        setInterval(function () {
            // 当文章内容长度大于100，自动保存草稿
            let html = boke_type === 1 ? editor.txt.html().trim() : testEditor.getMarkdown().trim();
            if (html.length > 100) {
                savecg(html);
            }
        }, 60000);
    }
    if (cur_boke) {
        boke_id = cur_boke.bokeId;
        $("#title").val(cur_boke.title);
        $("#label_t").val(cur_boke.labelNames);
        $("#label_vles").val(cur_boke.labelId);
        setcategoryV(cur_boke.cateId,cur_boke.cateName);
        $("input[name='cheakRadios'][value='" + cur_boke.priPub + "']").prop("checked", true);
        if (boke_type === 2) {
            $("#toMDMMD").val(cur_boke.mdContent);
        } else {
            editor.txt.html(cur_boke.bokeCont);
        }
    }
    $(".ySearchSelect").ySearchSelect(); //调用自定义 高亮插件

});

/**
 * 保存草稿 点击按钮保存草稿，提示消息
 * */
function saveCGBtn() {
    let html = boke_type === 1 ? editor.txt.html().trim() : testEditor.getMarkdown().trim();
    savecg(html);
    // show_suc_msg("于" + new Date().Format("yyyy-MM-dd HH:mm:ss") + "的时候自动保存草稿~");
}
let isSaveCG = true;
function savecg(html) {
    if (isSaveCG) {
        let val=$('input[name="cheakRadios"]:checked').val();
        // let html = boke_type === 1 ? editor.txt.html().trim() : testEditor.getMarkdown().trim();
        let title = $("#title").val().trim();
        let cateId = $("#cateitemSelect").val();
        let cName = $("#cate_v_b").text();
        let labelId = $("#label_vles").val();
        let labelNames = $("#label_t").val();
        let boke_m2 = {
            bokeId: boke_id,
            title: title,
            bokeCont: html,
            cateId: cateId,
            labelId:labelId,
            labelNames:labelNames,
            priPub: val,
            cateName: cName
        };
        if (boke_type !== 1) {
            boke_m2.mdContent = html;
        }
        set_item(cg_item, JSON.stringify(boke_m2));
        save_cg_m();
    }
}
// 按下Ctrl + Enter 提交博客
$(document).keyup(function (e) {
    if (e.ctrlKey && e.keyCode === 13){
        submitBoke();
    }
});
/**
 * 发布文章
 */
function submitBoke() {
    isSaveCG = false;
    showLoad()
    savecg();
    let val=$('input[name="cheakRadios"]:checked').val();
    let introduction;
    let bokeCont;
    if (boke_type === 1) {
        introduction = editor.txt.text().trim();
        bokeCont = editor.txt.html().trim();
    } else {
        // 获取预览窗口里的 HTML，在开启 watch 且没有开启 saveHTMLToTextarea 时使用
        bokeCont = testEditor.getPreviewedHTML();
        introduction = $(bokeCont).text();
    }
    if (introduction.length > 160) {
        introduction = introduction.substring(0,160);
    }
    let title = $("#title").val().trim();
    let cateId = $("#cateitemSelect").val();
    let labelId = $("#label_vles").val();
    let labelNames = $("#label_t").val();
    let h_re = bokeCont.replace("<p>","").replace("</p>","").replace("<br/>","").replace("&nbsp;","").trim();
    // console.log("样本大小为："+ html.length);
    if (title.length > 0 && h_re.length > 0 && cateId > 0 && labelId.length > 0 && labelNames.length > 0) {
        if (title.length > 100) {
            show_err_msg(title_tolong);
            isSaveCG = true;
            return false
        }
        is_confirm = false;
        showLoad();
        let param = {title:title,bokeCont:bokeCont,introduction:introduction,cateId:cateId,priPub:val,editorType:boke_type,bokeId:boke_id,
            labelNames:labelNames,labelId:labelId,userId:get_item(lo_userId)};
        if (boke_type === 2) {
            param.mdContent = testEditor.getMarkdown().trim();
        }
        $.post({
            url : "/t/addBoke",
            dataType : "json",
            data:param,
            //再次添加头部信息
            beforeSend: function(request) {
                // requestToken(request,token);
            },
            success : function (data) {
                if (data.status === -1) {
                    show_l_m(data.msg,5)
                    return false;
                }
                if (!boke) {
                    clear_item(cg_item)
                }
                show_l_m(data.result,6);
                is_confirm = false;
                window.location.href = "/";
            },
            complete:function () {
                closeLoad()
                isSaveCG = true;
            }
        })
    } else {
        show_l_m("要输入内容呐~",0)
        isSaveCG = true;
        return false;
    }
}

// 创建一个数组 存放标签value
var ck_arr = new Array();
// 创建一个数组 存放标签name
var ck_w_arr = new Array();
// 显示labellist
function showLabelist() {
    getByAjax();
    $("#label_list_box").show();
    // 加入一个遮罩层 然后给遮罩层加一个点击事件
    // 点击隐藏label_list
    $("#zzc").show();
}

// 选择标签
function chossLabel(id) {
    let e_ck = document.getElementById("label_b_" + id);
    let ck_o = $("#label_b_" + id);
    let ln = $("#label_f_" + id).text();
    // checkbox是否选中
    let ck = ck_o.is(':checked');
    if (ck) {
        if (ck_arr.length < 3) {
            // 数组添加元素
            ck_arr.push(id);
            ck_w_arr.push(ln);
        } else {
            // 超过3个取消选中
            show_err_msg("最多只能选择3个~");
            e_ck.checked=false;
        }
    } else {
        // 数组移除元素
        ck_arr.forEach(function(item, index, arr) {
            if(item == id) {
                arr.splice(index, 1);
            }
        });
        ck_w_arr.forEach(function(item, index, arr) {
            if(item == ln) {
                arr.splice(index, 1);
            }
        });
    }
    setLabelVs();
}
// 隐藏labellist
function hideLabelist() {
    $("#label_list_box").hide();
    // 加入一个遮罩层 然后给遮罩层加一个点击事件 
    // 点击隐藏label_list
    $("#zzc").hide();
}

// 新增
function addLabel() {
    let v = $("#addb_v").val().trim();
    if (v.length > 15) {
        show_err_msg("长度不能超过15~");
        return false;
    }
    if (v.length > 0 && v.length < 15) {
        token_ajax("/label/add",{labelName:v},function (lId) {
            // 添加到某个元素的前面$("#first_la").after(
            // let lId = l.labelId;
            let checked = "";
            // 如果Label的数量小于3则，选中
            if (ck_arr.length < 3) {
                checked = "checked";
                // 如果不包含则添加进去
                if (!ck_arr.includes(lId)) {
                    ck_arr.push(lId);
                }
                if (!ck_w_arr.includes(v)) {
                    ck_w_arr.push(v);
                }
                setLabelVs();
            }
            // 如果已经存此label则直接选择
            let c_lvs = allLabels.includes(v);
            if (c_lvs) {
                if (ck_arr.length <= 3) {
                    let e_ck = document.getElementById("label_b_" + lId);
                    e_ck.checked=true;
                }
            } else {
                // 如果不存在此label，新增之后再放进数组
                // 在标签开始的地方添加
                $("#label_list").prepend('<li><input onchange="chossLabel('+lId+')" type="checkbox" '+checked+' id="label_b_'+lId+'" value="'+lId+'">' +
                    '<label id="label_f_'+lId+'" for="label_b_'+lId+'">'+v+'</label><div class="del-icon" onclick="delLabel('+lId+')"></div></li>');
                allLabels.push(v);
            }
            $("#addb_v").val("");
        },2);
    }
}
let allLabels;
// 选中已选择的标签
function getByAjax() {
    $("#label_list").html('');
    jcAjax_notf_noParam("/label/get/0",function (data) {
        let labels = data.result;
        // 赋值全部的labels
        if (labels.length > 0) {
            ck_arr = new Array();
            ck_w_arr = new Array();
            allLabels = [];
            let l_v = $("#label_vles").val();
            let labestrs = "";
            for (let i = 0; i < labels.length; i++) {
                let l = labels[i];
                let l_id = l.labelId;
                let l_name = l.labelName;
                allLabels.push(l_name);
                let checked = "";
                if (l_v.length > 0) {
                    let lvs = l_v.split(",");
                    // 判断  数组是否包含某个值 如果是数字要加""
                    let c_lvs = lvs.includes(l_id + "");
                    if (c_lvs) {
                        checked = "checked";
                        // 如果存在 则选中之后 存进数组
                        ck_arr.push(l_id);
                        ck_w_arr.push(l_name);
                    }
                }
                labestrs += '<li ><input onchange="chossLabel('+l_id+')" type="checkbox" '+checked+' id="label_b_'+l_id+'" value="'+l_id+'">' +
                    '<label id="label_f_'+l_id+'" for="label_b_'+l_id+'">'+l_name+'</label><div class="del-icon" onclick="delLabel('+l_id+')"></div></li>';
            }
            $("#label_list").append(labestrs);
            setLabelVs();
        }
    })
}

// 删除标签
function delLabel(id) {
    token_ajax2("/label/del/" + id,function () {
        // 如果删除的时候选中了，则移除选中的
        let ck_o = $("#label_b_" + id);
        // checkbox是否选中
        let ck = ck_o.is(':checked');
        if (ck) {
            let ln = $("#label_f_" + id).text();
            // 数组移除元素
            ck_arr.forEach(function(item, index, arr) {
                if(item == id) {
                    arr.splice(index, 1);
                }
            });
            ck_w_arr.forEach(function(item, index, arr) {
                if(item == ln) {
                    arr.splice(index, 1);
                }
            });
            setLabelVs();
        }
        //    移除元素父标签
        ck_o.parent().remove();
    },2);
}
// 设置Label的值
function setLabelVs() {
    // 数组转字符串 以,分隔
    $("#label_vles").val(ck_arr.join(','));
    $("#label_t").val(ck_w_arr.join(','));
}
// 清空草稿
function save_cg_m() {
    let msg = "于" + new Date().Format("yyyy-MM-dd HH:mm:ss") + "的时候自动保存草稿~";
    // show_suc_msg("于" + new Date().Format("yyyy-MM-dd HH:mm:ss") + "的时候自动保存草稿~");
    $("#showsaveCG").text(msg);
}

/**
 * 判断标题是否存在
 */
function checkb_title(o) {
    let e = getElementByO(o);
    let v = e.val().trim();
    if (v.length > 0) {
        jcAjax_notf_get("/check/title",{title:v});
    }
}

// 添加分类 并显示
function b_addcate() {
    let item = $("#cateItem_add").val().trim();
    if (item.length > 0) {
        token_ajax("/addCate",{cateName:item},function (id) {
            initCategory();
            setcategoryV(id,item);
            $("#cateItem_add").val("");
        },2);
    }
}
// 设置分类的值
function setcategoryV(k,v) {
    $("#cateitemSelect").val(k);
    $("#cate_v_b").text(v);
}
/**
 * 清空草稿箱
 */
function clearcg() {
    clear_item(boke_type === 1 ? wd_cg : md_cg);
    show_suc_msg(clear_cgx);
//    刷新页面
    reloadjsonh();
}
// 按enter键添加或者选择分类
function addCategory_enter(n) {
    if (n.keyCode === 13) return b_addcate(), !1
}
// 按enter键添加或者选择标签
function addLabel_enter(n) {
    if (n.keyCode === 13) return addLabel(), !1
}