function deleteState(state) {
    return state === 1 ? '可用' : '不可用';
}

//重新渲染表单函数
function renderForm() {
    layui.use('form', function() {
        let form = layui.form; //高版本建议把括号去掉，有的低版本，需要加()
        form.render();
    });
}