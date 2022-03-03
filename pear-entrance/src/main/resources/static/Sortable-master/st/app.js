(function () {
	'use strict';

	var byId = function (id) { return document.getElementById(id); }

	// Editable list
	Sortable.create(byId('editable'), {
		animation: 150,
		filter: '.js-remove-f',
        handle: '.drag-handle',
		onFilter: function (evt) {
            layer.confirm('确定要移除此分类下所有网址吗？', function(index){
                evt.item.parentNode.removeChild(evt.item);
                layer.close(index);
            });
		}
	});
	let items = $(".frant_items");
    for (let i = 0; i < items.length; i++) {
        let iii = items[i].attr('id');
        console.log(iii)
        Sortable.create(byId('editable' + i), {
            animation: 150,
            filter: '.js-remove-z',
            handle: '.drag-handle',
            onFilter: function (evt) {
                layer.confirm('确定要移除此网址吗？', function(index){
                    evt.item.parentNode.removeChild(evt.item);
                    layer.close(index);
                });
            }
        });
    }


})();



