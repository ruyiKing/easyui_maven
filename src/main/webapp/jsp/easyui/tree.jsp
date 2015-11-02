<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<%@include file="/jsp/public.jsp"%>
		<script type="text/javascript">
			function append() {
				var t = $('#tt');
				var node = t.tree('getSelected');
				t.tree('append', {
					parent : (node ? node.target : null),
					data : [ {
						text : 'new item1'
					}, {
						text : 'new item2'
					} ]
				});
			}
			function removeit() {
				var node = $('#tt').tree('getSelected');
				$('#tt').tree('remove', node.target);
			}
			function collapse() {
				var node = $('#tt').tree('getSelected');
				$('#tt').tree('collapse', node.target);
			}
			function expand() {
				var node = $('#tt').tree('getSelected');
				$('#tt').tree('expand', node.target);
			}
		</script>
	</head>

	<body>
		<h2>Tree Context Menu</h2>
		<p>Right click on a node to display context menu.</p>
		<div style="margin: 20px 0;"></div>
		<div class="easyui-panel" style="padding: 5px">
			<ul id="tt" class="easyui-tree"
				data-options="
					url: 'tree_data1.json',
					method: 'get',
					animate: true,
					onContextMenu: function(e,node){
						e.preventDefault();
						$(this).tree('select',node.target);
						$('#mm').menu('show',{
							left: e.pageX,
							top: e.pageY
						});
					}
				"></ul>
		</div>
		<div id="mm" class="easyui-menu" style="width: 120px;">
			<div onclick="append()" data-options="iconCls:'icon-add'">Append</div>
			<div onclick="removeit()" data-options="iconCls:'icon-remove'">Remove</div>
			<div class="menu-sep"></div>
			<div onclick="expand()">Expand</div>
			<div onclick="collapse()">Collapse</div>
		</div>
	
	</body>
</html>