var Tree = function () {
	//树对应的Json值
	/*
	[
    {
        "id": 1, 
        "name": "父节点1",
        "hasChild": true,
        "children": [
            {
                "id": 11, 
                "name": "子节点1",
                "hasChild": false
            }, 
            {
                "id": 12, 
                "name": "子节点2", 
                "hasChild": true,
                "children": [
                    {
                        "id": 121, 
                        "name": "子节点21",
                        "hasChild": false
                    }
                ]
            }
        ]
    }, 
    {
        "id": 2, 
        "name": "父节点2", 
        "hasChild": true,
        "children": [
            {
                "id": 21, 
                "name": "子节点21",
                "hasChild": false
            }, 
            {
                "id": 22, 
                "name": "子节点22",
                "hasChild": false
            }
        ]
    }
	]
	*/
	var data=[];
	
	//项目路径
	var contextPath="";
	
	//树结点点击事件（通过页面传入方法赋值）
	var nodeClick;
	
	//点击树结点，获得其子集内容
	var nodeClickFunction=function () {
		$("span").removeClass('treeChecked');
		$(this).parent('span').addClass('treeChecked');
		
		var node={id:$(this).parent('span').attr('id'),name:$(this).text()};
		nodeClick(node);
		e.stopPropagation();
	};
	
	var triggerFunction = function () {
		var span = $(this).parent('span');
		var children = $(this).parent('span').parent('li.parent_li').find(' > ul > li');
		if (children.is(":visible")) {
			children.hide('fast');
			$(this).addClass('fa-plus-square').removeClass('fa-minus-square');
		} else {
			if(children.parent().is('ul')){
				children.show('fast');
			}else{
			jQuery.ajax({
				url: contextPath+"/department/asyn_children",
				data:{"nodeid":span.attr('id')},
				type: "post",
				dataType: "json",
				success: function(msg) {
					var treeStr=treeChildren(msg,"");
					span.after(treeStr);
					span.parent('li').addClass('parent_li').find(' > span');
					$('.tree li > span >text').on('click', nodeClickFunction);
					$('.tree li > span > i').on('click', triggerFunction);
				},
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                }
			});
			}
			$(this).addClass('fa-minus-square').removeClass('fa-plus-square');
		}
		e.stopPropagation();
	};

	//递归获取子节点，组成页面显示的树形结构
	var treeChildren = function(e,treeStr){
		treeStr=treeStr+'<ul>';
		$.each(e, function (i, n) {
			treeStr=treeStr+'<li><span id='+n.id+'>';
			if(n.hasChild){
				treeStr=treeStr+'<i class="fa fa-plus-square" style="margin-right: 5px;margin-left: 5px;"></i>';
			}
			treeStr=treeStr+'<text>'+n.name+'</text></span>';
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
				treeStr=treeStr+'<text>'+n.name+'</text></span>';
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
			$('.tree li > span >text').on('click', nodeClickFunction);
			
			//树收起和折叠事件
			$('.tree li > span > i').on('click', triggerFunction);

		},
		
		setData: function (d) {
            data = d;
        },

		onClick:function(e){
			nodeClick = e;
		},
		
		setPath: function (path) {
			contextPath = path;
        },

	};
}();