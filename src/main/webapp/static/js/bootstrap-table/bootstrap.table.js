var Table = function () {
	/* 表格对应列的Json值
	 * 对应 org.springframework.data.domain.Page 的 Json 格式化 
	 * 注意：Page中 hasNextPage、hasPreviousPage 在JSON格式化后，是不存在的，可以使用isFirstPage、isLastPage处理
	*/
	var data={};
	
	//列对应的Title（thead > th）
    var thead={};
    
    //分页显示的页数
    var paginationSize=5;
    
    //访问路径
    var contextPath='';
    
    // 条件
    var conditions='';

    //生成Table列表.
	var createTable = function(){
		//Start表头
		var tHeadHTML='';
		tHeadHTML=tHeadHTML+'<tr>';
		$.each(thead.columns, function (i, c) {
			tHeadHTML=tHeadHTML+'<th>'+c.title+'</th>';
		});
		tHeadHTML=tHeadHTML+'<th>管理</th></tr>';
		$(".table-page").children("thead").html(tHeadHTML);
		//END 表头
		
		//列表
		var tBodyHTML='';
		$.each(data.content, function (i, n) {
			tBodyHTML=tBodyHTML+'<tr>';
			$.each(thead.columns, function (i, c) {
				tBodyHTML=tBodyHTML+'<td>'+n[c.field]+'</td>';
			});
			
			tBodyHTML=tBodyHTML+'<td><a class="text-info" href="'+contextPath+'/update/'+n[thead.idField]+conditions+'">修改</a> / <a class="text-danger" href="'+contextPath+'/delete/'+n[thead.idField]+conditions+'" onclick="return confirm(\'是否删除？\')">删除</a></td>';
			tBodyHTML=tBodyHTML+'</tr>';
		});
		$(".table-page").children("tbody").html(tBodyHTML);
		//END 列表
		
		//分页条
		var current=data.number+1;
		var begin=Math.max(1,Math.ceil(current - paginationSize/2));
		var end=Math.min(begin + (paginationSize - 1), data.totalPages);
		var pagination='<ul class="pagination" style="margin-top: 0px;">';
		//判断当前是否第一页（禁止上一页、首页的按钮点击）
		if(data.firstPage){
			pagination=pagination+'<li class="disabled"><a href="#">&lt;&lt;</a></li>';
			pagination=pagination+'<li class="disabled"><a href="#">&lt;</a></li>';
		}else{
			pagination=pagination+'<li><a href="javascript:page(1)">&lt;&lt;</a></li>';
			pagination=pagination+'<li><a href="javascript:page('+(current-1)+')">&lt;</a></li>';
		}

		for(var i=begin;i<=end;i++){
			if(i==current){
				pagination=pagination+'<li class="active"><a href="javascript:page('+i+')">'+i+'</a></li>';
			}else{
				pagination=pagination+'<li><a href="javascript:page('+i+')">'+i+'</a></li>';
			}
		}
		
		//判断当前是否最后一页（禁止下一页、尾页的按钮点击）
		if(data.lastPage){
			pagination=pagination+'<li class="disabled"><a href="#">&gt;</a></li>';
			pagination=pagination+'<li class="disabled"><a href="#">&gt;&gt;</a></li>';
		}else{
			pagination=pagination+'<li><a href="javascript:page('+(current+1)+')">&gt;</a></li>';
			pagination=pagination+'<li><a href="javascript:page('+data.totalPages+')">&gt;&gt;</a></li>';
		}
		
		pagination=pagination+'</ul>';
		$("#pagination").html(pagination);
		//END 分页条
	};
	
	//从后台获取Table数据.
	var loadDate=function(url){
		jQuery.ajax({
			url: url,
			type: "post",
			dataType: "json",
			success: function(msg) {
				data=msg;
				//调用列表的生成方法.
				createTable();
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
		});
	};
	
	return{
		load: function (url) {
			if(url.indexOf("?")>0){
				var str = url.split("?");
				contextPath=str[0];
				conditions="?"+str[1];
			}else{
				contextPath=url;
			}
			loadDate(url);
		},
		
		reLoad:function(url){
			loadDate(url);
		},
		
		setHead: function (d) {
			thead = d;
        },
        
        setPaginationSize: function (p) {
        	paginationSize = p;
        },

	};
}();