var Tree = function () {
	//树对应的Json值
	/*
	[
    {
        "id": 1, 
        "name": "父节点1", 
        "children": [
            {
                "id": 11, 
                "name": "子节点1"
            }, 
            {
                "id": 12, 
                "name": "子节点2", 
                "children": [
                    {
                        "id": 121, 
                        "name": "子节点21"
                    }
                ]
            }
        ]
    }, 
    {
        "id": 2, 
        "name": "父节点2", 
        "children": [
            {
                "id": 21, 
                "name": "子节点21"
            }, 
            {
                "id": 22, 
                "name": "子节点22"
            }
        ]
    }
	]
	*/
	var data=[];
	
	//树结点点击事件（通过页面传入方法赋值）
	var nodeClick;

	//递归获取子节点，组成页面显示的树形结构
	var treeChildren = function(e,treeStr){
		treeStr=treeStr+'<ul>';
		$.each(e, function (i, n) {
			treeStr=treeStr+'<li><span id='+n.id+'>';
			if(n.children!=null){
				treeStr=treeStr+'<i class="fa fa-minus-square" style="margin-right: 5px;margin-left: 5px;"></i>';
			}
			treeStr=treeStr+''+n.name+'</span>';
			if(n.children!=null){
				treeStr=treeChildren(n.children,treeStr);
			}
			treeStr=treeStr+'</li>';
		});
		treeStr=treeStr+'</ul>';

		return treeStr;
	};

	return{
		init: function (t) {
			var tree = $(t);
			var treeStr='<ul>';
			$.each(data, function (i, n) {
				treeStr=treeStr+'<li style="margin-left:-40px;"><span id='+n.id+'>';
				if(n.children!=null){
					treeStr=treeStr+'<i class="fa fa-minus-square" style="margin-right: 5px;margin-left: 5px;"></i>';
				}
				treeStr=treeStr+''+n.name+'</span>';
				if(n.children!=null){
					treeStr=treeChildren(n.children,treeStr);
				}
				treeStr=treeStr+'</li>';
			});
			treeStr=treeStr+'</ul>';
			
			tree.html(treeStr);
			tree.addClass('tree');
			$('.tree li:has(ul)').addClass('parent_li').find(' > span');

			//触发点击事件()
			$('.tree li > span').on('click', function (e) {
				$("span").removeClass('treeChecked');
				$(this).addClass('treeChecked');
				var node={id:$(this).attr('id'),name:$(this).text()};
				nodeClick(node);
				e.stopPropagation();
			});
			
			//树收起和折叠事件
			$('.tree li > span > i').on('click', function (e) {
				var children = $(this).parent('span').parent('li.parent_li').find(' > ul > li');
				if (children.is(":visible")) {
					children.hide('fast');
					$(this).addClass('fa-plus-square').removeClass('fa-minus-square');
				} else {
					children.show('fast');
					$(this).addClass('fa-minus-square').removeClass('fa-plus-square');
				}
				e.stopPropagation();
			});

		},
		
		setData: function (d) {
            data = d;
        },

		onClick:function(e){
			nodeClick = e;
		},
	};
}();